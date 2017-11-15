package com.sapient.pricing.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sapient.pricing.PricingComponent;
import com.sapient.pricing.auditing.AuditingComponent;
import com.sapient.pricing.auditing.Impl.AuditingComponentImpl;
import com.sapient.pricing.model.DataStore;
import com.sapient.pricing.validation.exception.OperationNotAllowedException;

public class PricingComponentTest {

	private PricingComponent pricingComponent;

	private AuditingComponent auditingComponent;

	public PricingComponentTest() {
	}

	@Before
	public void setUp() {
		pricingComponent = new PricingComponentImpl();
		auditingComponent = new AuditingComponentImpl();
		pricingComponent.setAuditingComponent(auditingComponent);
	}

	@After
	public void tearDown() {
		pricingComponent = null;
		auditingComponent = null;
		DataStore.PRICESTORE.clearDataStore();
	}

	/**
	 * Test of getPartPrice and setPartPrice method, of class PricingComponentImpl.
	 */
	@Test
	public void testSetAndGetPartPrice() {
		System.out.println("testSetAndGetPartPrice");
		int partId = 21321;
		Double expResult = 2324.324;
		try {
			pricingComponent.setPartPrice(partId, expResult);
		} catch (OperationNotAllowedException e) {
			e.printStackTrace();
		}
		Double result = pricingComponent.getPartPrice(partId);
		assertEquals(expResult, result);
	}

	/**
	 * Test of updating part price.
	 */
	@Test
	public void testUpdatePartPrice() {

		System.out.println("testUpdatePartPrice");
		int partId = 21321;
		Double price = 2324.324;
		try {
			pricingComponent.setPartPrice(partId, price);
		} catch (OperationNotAllowedException ex) {
			Logger.getLogger(PricingComponentTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		assertEquals(price, pricingComponent.getPartPrice(partId));
		Double updatedPrice = 45924.324;
		try {
			pricingComponent.setPartPrice(partId, updatedPrice);
		} catch (OperationNotAllowedException ex) {
			Logger.getLogger(PricingComponentTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertEquals(updatedPrice, pricingComponent.getPartPrice(partId));

	}

	/**
	 * Test of adding multiple parts in Multithreaded environment.
	 */
	@Test
	public void testMultiThreadedEnv() {
		System.out.println("testMultiThreadedEnv");
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<String>> statusList = new ArrayList<Future<String>>();
		for (int i = 1; i <= 250; i++) {
			Future<String> future = executorService.submit(new PricerRequest(i, i * 2.5, pricingComponent));
			statusList.add(future);
		}
		for (Future<String> fut : statusList) {
			try {
				System.out.println(System.currentTimeMillis() + "::" + fut.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executorService.shutdown();
		Integer expectedSizeAfter = 250;
		Integer resultsizeAfter = DataStore.PRICESTORE.getPartPriceMap().size();
		assertEquals(expectedSizeAfter, resultsizeAfter);

		// check price of any part
		for (int i = 1; i <= 250; i++) {
			assertEquals(new Double(i * 2.5), pricingComponent.getPartPrice(i));
		}

	}

	/**
	 * Test of consistency of part pricing, when updating same part using multiple
	 * threads of class PricingComponentImpl.
	 */
	@Test
	public void testConsistancyInMultiThreadedEnv() {
		System.out.println("testConsistancyInMultiThreadedEnv");
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		List<Future<String>> statusList = new ArrayList<Future<String>>();
		statusList.add(executorService.submit(new PricerRequest(1, 3.5, pricingComponent)));
		statusList.add(executorService.submit(new PricerRequest(2, 435.5, pricingComponent)));
		statusList.add(executorService.submit(new PricerRequest(3, 87.5, pricingComponent)));
		statusList.add(executorService.submit(new PricerRequest(2, 871.5, pricingComponent)));
		for (Future<String> fut : statusList) {
			try {
				System.out.println(System.currentTimeMillis() + "::" + fut.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executorService.shutdown();

		assertEquals(new Double(435.5), pricingComponent.getPartPrice(2));
		assertEquals(new Double(87.5), pricingComponent.getPartPrice(3));

	}

	/**
	 * Test of boundary condition of Part Map, of class PricingComponentImpl.
	 */
	@Test(expected = OperationNotAllowedException.class)
	public void testBoundaryOfMap() {
		System.out.println("testBoundaryOfMap");
		for (int i = 1000; i <= 251000; i++) {

			pricingComponent.setPartPrice(i, i * 1.5);

		}

	}

	@Test
	public void testBoundaryConditionForSize() {

		try {
			for (int i = 1; i <= 250000; i++) {

				pricingComponent.setPartPrice(i, i * 1.5);

			}

		} catch (OperationNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer expectedSizeAfter = 250000;
		Integer resultsizeAfter = DataStore.PRICESTORE.getPartPriceMap().size();
		assertEquals(expectedSizeAfter, resultsizeAfter);
	}

	private class PricerRequest implements Callable<String> {
		private int partNo;
		private Double pricing;
		private PricingComponent pricingComp;

		public PricerRequest(int partNo, Double price, PricingComponent pricingComp) {
			this.partNo = partNo;
			this.pricing = price;
			this.pricingComp = pricingComp;
		}

		public String call() {
			try {
				pricingComp.setPartPrice(partNo, pricing);
			} catch (OperationNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Added:" + this.partNo;

		}

	}
}
