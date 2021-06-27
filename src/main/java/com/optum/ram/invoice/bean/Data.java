package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Data implements Serializable
{

    private MemberData old;
    private MemberData _new;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 4365064941359329930L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param old
     * @param _new
     */
    public Data(MemberData old, MemberData _new) {
        super();
        this.old = old;
        this._new = _new;
    }

    public MemberData getOld() {
        return old;
    }

    public void setOld(MemberData old) {
        this.old = old;
    }

    public MemberData getNew() {
        return _new;
    }

    public void setNew(MemberData _new) {
        this._new = _new;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
