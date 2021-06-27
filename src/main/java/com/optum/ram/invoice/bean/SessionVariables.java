
package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionVariables implements Serializable
{

    private String xHasuraRole;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6875165617565984067L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SessionVariables() {
    }

    /**
     * 
     * @param xHasuraRole
     */
    public SessionVariables(String xHasuraRole) {
        super();
        this.xHasuraRole = xHasuraRole;
    }

    public String getXHasuraRole() {
        return xHasuraRole;
    }

    public void setXHasuraRole(String xHasuraRole) {
        this.xHasuraRole = xHasuraRole;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
