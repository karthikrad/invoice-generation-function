
package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements Serializable
{
   
    private SessionVariables session_variables;
    private String op;
    private Data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4844186820106307128L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Event() {
    }

    /**
     * 
     * @param session_variables
     * @param op
     * @param data
     */
    public Event(SessionVariables session_variables, String op, Data data) {
        super();
        this.session_variables = session_variables;
        this.op = op;
        this.data = data;
    }

    public SessionVariables getSessionVariables() {
        return session_variables;
    }

    public void setSessionVariables(SessionVariables session_variables) {
        this.session_variables = session_variables;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
