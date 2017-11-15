package com.sapient.pricing;

import com.sapient.pricing.auditing.AuditingComponent;
import com.sapient.pricing.validation.exception.OperationNotAllowedException;

public interface PricingComponent {
	/**
	 * Sets the current price of a part.
	 *
	 * @param partId
	 *            The part identifier
	 * @param price
	 *            The current price, or null if the part is no longer being
	 *            priced
	 * @throws com.sapient.pricer.exception.OperationNotAllowedException
	 */
	public void setPartPrice(int partId, Double price) throws OperationNotAllowedException;

	/**
	 * Gets the current price of a part.
	 *
	 * @param partId
	 *            The part identifier
	 * @return The current price, or null if the part is not priced
	 */
	public Double getPartPrice(int partId);

	/**
	 * Connects the external auditing component that will be used to record all
	 * price updates for future review. Only one auditing component can be
	 * connected at a time.
	 *
	 * @param auditor
	 *            A reference to the auditing component, or null if no auditing
	 *            component is to be connected
	 */
	public void setAuditingComponent(AuditingComponent auditor);

}



