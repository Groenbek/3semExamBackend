///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package entities;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
///**
// *
// * @author groen
// */
//@Entity
//public class Opportunity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private int amount;
//    private String name;
//    private String closeDate;
//    
//    private Contact contact;
//
//    public Contact getContact() {
//        return contact;
//    }
//
//    public void setContact(Contact contact) {
//        this.contact = contact;
//    }
//    
//    
//    public Opportunity() {
//    }
//
//    public Opportunity(int amount, String name, String closeDate) {
//        this.amount = amount;
//        this.name = name;
//        this.closeDate = closeDate;
//    }
//    
//    
//
//    
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCloseDate() {
//        return closeDate;
//    }
//
//    public void setCloseDate(String closeDate) {
//        this.closeDate = closeDate;
//    }
//
//
//}
