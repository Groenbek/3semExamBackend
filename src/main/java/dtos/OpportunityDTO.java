/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;
import entities.Opportunity;

/**
 *
 * @author groen
 */
public class OpportunityDTO {
    private int amount;
    private String name;
    private String closeDate;
    
    public OpportunityDTO(int amount, String name, String closeDate) {
        this.amount = amount;
        this.name = name;
        this.closeDate = closeDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    
}
