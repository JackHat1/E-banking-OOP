package com.bank.model.accounts;

import com.bank.model.users.Individual;
import java.util.ArrayList;
import java.util.List;

public class PersonalAccount extends Account {
    private List<Individual> coOwners = new ArrayList<>();

    public PersonalAccount(Individual owner, double interestRate) {
        super(owner, interestRate);
    }

    public void addCoOwner(Individual coOwner) {
        coOwners.add(coOwner);
    }

    @Override
    public String getAccountTypeCode() {
        return "100";
    }
}
