
package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Trigger implements Serializable
{

    private String name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3035305786943224227L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Trigger() {
    }

    /**
     * 
     * @param name
     */
    public Trigger(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
