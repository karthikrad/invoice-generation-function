package com.optum.ram.invoice.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload implements Serializable
{

    private Event event;
    private String created_at;
    private String id;
    private DeliveryInfo delivery_info;
    private Trigger trigger;
    private Table table;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 125105192921396101L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Payload() {
    }

    /**
     * 
     * @param created_at
     * @param delivery_info
     * @param id
     * @param trigger
     * @param event
     * @param table
     */
    public Payload(Event event, String created_at, String id, DeliveryInfo delivery_info, Trigger trigger, Table table) {
        super();
        this.event = event;
        this.created_at = created_at;
        this.id = id;
        this.delivery_info = delivery_info;
        this.trigger = trigger;
        this.table = table;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeliveryInfo getDeliveryInfo() {
        return delivery_info;
    }

    public void setDeliveryInfo(DeliveryInfo delivery_info) {
        this.delivery_info = delivery_info;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
