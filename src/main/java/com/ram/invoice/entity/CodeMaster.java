package com.ram.invoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "ram_code_master", schema = "invoice")
public class CodeMaster  extends PanacheEntityBase implements Serializable {
    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "code_value")
    public Long codeValue;
    @Id
    @Column(name = "code_type")
    public String codeType;
    @Column(name = "code_desc")
    public String codeDesc;    
    @Column(name = "long_desc")
    public String longDesc;
   
    public CodeMaster(){
        
    }
    public CodeMaster(Long codeValue, String codeDesc, String codeType, String longDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
        this.codeType = codeType;
        this.longDesc = longDesc;
    }
    

}