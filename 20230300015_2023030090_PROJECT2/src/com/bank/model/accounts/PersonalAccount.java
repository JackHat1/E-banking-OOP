package com.bank.model.accounts;

import com.bank.model.users.Customer;
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
    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder();
        sb.append("type:PersonalAccount");
        sb.append(",iban:").append(getIban());
        sb.append(",primaryOwner:").append(getOwner().getVat());
        sb.append(",dateCreated:").append(dateCreated.toString());
        sb.append(",rate:").append(getInterestRate());
        sb.append(",balance:").append(getBalance());

        for (Customer co : coOwners) {
            sb.append(",coOwner:").append(co.getVat());
        }

        return sb.toString();
    }


}
