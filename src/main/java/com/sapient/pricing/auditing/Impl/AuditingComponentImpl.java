package com.sapient.pricing.auditing.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sapient.pricing.auditing.AuditingComponent;
import com.sapient.pricing.model.DataStore;
import com.sapient.pricing.model.Pricing;

public class AuditingComponentImpl implements AuditingComponent {

	public void recordPartPriceUpdate(int partId, Double partPrice) {
		List<Pricing> partPricing = DataStore.PRICESTORE.getPriceUpdateLog().get(partId);
		if (partPricing == null) {
			synchronized (this) {
				partPricing = new ArrayList<Pricing>();
			}
		}
		synchronized (partPricing) {
			partPricing.add(new Pricing(partPrice, new Date()));
		}

		DataStore.PRICESTORE.getPriceUpdateLog().put(partId, partPricing);
	}

}
