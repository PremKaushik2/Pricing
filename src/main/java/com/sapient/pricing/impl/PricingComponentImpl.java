package com.sapient.pricing.impl;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sapient.pricing.PricingComponent;
import com.sapient.pricing.auditing.AuditingComponent;
import com.sapient.pricing.model.DataStore;
import com.sapient.pricing.model.Part;
import com.sapient.pricing.model.Pricing;
import com.sapient.pricing.validation.exception.OperationNotAllowedException;

public class PricingComponentImpl implements PricingComponent {

	private AuditingComponent auditor;
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);

	public Double getPartPrice(int partId) {
		Part part = DataStore.PRICESTORE.getPartPriceMap().get(partId);
		return part != null ? part.getPricing().getPrice() : null;
	}

	public void setAuditingComponent(AuditingComponent auditor) {
		this.auditor = auditor;
	}

	public  void setPartPrice(final int partNo, final Double price) throws OperationNotAllowedException {
		Part part = DataStore.PRICESTORE.getPartPriceMap().get(partNo);
		if (part != null) {
			
				part.getPricing().setPrice(price);
				part.getPricing().setDatePriced(new Date());
		} else {
			if (DataStore.PRICESTORE.getPartPriceMap().size() >= DataStore.PRICESTORE.getMaxParts()) {
				throw new OperationNotAllowedException(
						"Cannot price more than " + DataStore.PRICESTORE.getMaxParts() + " parts!");
			}
			part = new Part(partNo, new Pricing(price, new Date()));
			DataStore.PRICESTORE.getPartPriceMap().put(partNo, part);
		}

		// ExecutorService executorService =
		// Executors.newSingleThreadExecutor();
		Runnable task = new Runnable() {
			public void run() {

				auditor.recordPartPriceUpdate(partNo, price);

			}
		};
		// executorService.execute(task);
		// executorService.shutdown();
		threadPool.execute(task);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		threadPool.shutdown();
	}
}
