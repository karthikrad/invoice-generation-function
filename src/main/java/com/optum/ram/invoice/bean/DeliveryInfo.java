
package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeliveryInfo implements Serializable
{

    private int maxRetries;
    private int currentRetry;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -676359421128507447L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeliveryInfo() {
    }

    /**
     * 
     * @param currentRetry
     * @param maxRetries
     */
    public DeliveryInfo(int maxRetries, int currentRetry) {
        super();
        this.maxRetries = maxRetries;
        this.currentRetry = currentRetry;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getCurrentRetry() {
        return currentRetry;
    }

    public void setCurrentRetry(int currentRetry) {
        this.currentRetry = currentRetry;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
