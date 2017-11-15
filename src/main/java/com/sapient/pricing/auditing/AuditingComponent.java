package com.sapient.pricing.auditing;

public interface AuditingComponent {

	    /**
	     * Requests to audit a price update.
	     *
	     * @param partId The unique identifier of the part
	     * @param partPrice The new price (or null if the part is no longer priced)
	     */
	    void recordPartPriceUpdate(final int partId,final Double partPrice);

	}



