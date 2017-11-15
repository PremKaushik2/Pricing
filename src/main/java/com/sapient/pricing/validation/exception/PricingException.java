package com.sapient.pricing.validation.exception;

public class PricingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int partId;

	public PricingException(String message, int partId) {
		super(message);
		this.partId = partId;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String getMessage() {
		return super.getMessage() + " for productId :" + partId;
	}

}
