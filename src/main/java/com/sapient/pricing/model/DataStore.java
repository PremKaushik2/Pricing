package com.sapient.pricing.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataStore {

	    PRICESTORE();
	    private final Map<Integer, Part> partPriceMap;
	    private final Map<Integer, List<Pricing>> priceUpdateLog;
	    private final int maxParts;

	    private DataStore() {
	        priceUpdateLog = new ConcurrentHashMap<Integer, List<Pricing>>();
	        partPriceMap = new ConcurrentHashMap<Integer, Part>();
	        maxParts = 250000;
	    }

	    /**
	     * @return the partPriceMap
	     */
	    public Map<Integer, Part> getPartPriceMap() {
	        return partPriceMap;
	    }

	    /**
	     * @return the priceUpdateLog
	     */
	    public Map<Integer, List<Pricing>> getPriceUpdateLog() {
	        return priceUpdateLog;
	    }

	    /**
	     * @return the maxParts
	     */
	    public int getMaxParts() {
	        return maxParts;
	    }
	    
	    public void clearDataStore(){
	    	priceUpdateLog.clear();
	    	partPriceMap.clear();
	    }

	}



