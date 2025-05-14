package com.bank.model.orders;

import java.time.LocalDate;
import java.util.UUID;

public abstract class StandingOrder {
    
    protected String orderId;
    protected String title;
    public String description;
    public Boolean isActive = true;
    protected LocalDate startingDate;
    protected LocalDate endingDate;


    public StandingOrder(String title, String description, LocalDate startingDate, LocalDate endingDate){
        this.orderId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    
    public String getOrderId() {
        return orderId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }


    //pulic void activewithinperiod()
    public abstract void execute();

    public abstract String marshal();
    public abstract void unmarshal(String data);


}
