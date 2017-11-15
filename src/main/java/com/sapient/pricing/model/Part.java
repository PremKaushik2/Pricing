package com.sapient.pricing.model;

	
	public class Part {

		private int partNo;
		private Pricing pricing;

		public Part(int partNo, Pricing price) {
			this.partNo = partNo;
			this.pricing = price;
		}

		/**
		 * @return the partNo
		 */
		public int getPartNo() {
			return partNo;
		}

		/**
		 * @param partNo
		 *            the partNo to set
		 */
		public void setPartNo(int partNo) {
			this.partNo = partNo;
		}

		public Pricing getPricing() {
			return pricing;
		}

		public void setPricing(Pricing pricing) {
			this.pricing = pricing;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + partNo;
			result = prime * result + ((pricing == null) ? 0 : pricing.hashCode());
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
			Part other = (Part) obj;
			if (partNo != other.partNo)
				return false;
			if (pricing == null) {
				if (other.pricing != null)
					return false;
			} else if (!pricing.equals(other.pricing))
				return false;
			return true;
		}

	}



