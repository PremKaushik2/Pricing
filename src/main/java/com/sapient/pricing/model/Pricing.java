package com.sapient.pricing.model;

import java.util.Date;

public class Pricing {

	
	private Double price;
    private Date datePriced;

    public Pricing(Double price, Date datePriced) {
        this.price = price;
        this.datePriced = datePriced;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the datePriced
     */
    public Date getDatePriced() {
        return datePriced;
    }

    /**
     * @param datePriced the datePriced to set
     */
    public void setDatePriced(Date datePriced) {
        this.datePriced = datePriced;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePriced == null) ? 0 : datePriced.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pricing other = (Pricing) obj;
		if (datePriced == null) {
			if (other.datePriced != null)
				return false;
		} else if (!datePriced.equals(other.datePriced))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
    
    
}


