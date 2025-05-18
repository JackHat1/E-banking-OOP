package com.bank.model.orders;

import java.time.LocalDate;
import java.util.UUID;

import com.bank.manager.AccountManager;
import com.bank.manager.BillManager;
import com.bank.manager.TransactionManager;
import com.bank.model.users.User;
import com.bank.storage.Storable;

public abstract class StandingOrder implements Storable {
    
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


    public abstract void execute(LocalDate date, BillManager billMan, AccountManager accountMan, TransactionManager transMan, User user);

    public abstract String marshal();
    public abstract void unmarshal(String data);


}
