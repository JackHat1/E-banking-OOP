package com.bank.manager;

import java.util.ArrayList;
import java.util.List;
import com.bank.model.statements.StatementEntry;

public class StatementManager {

    private final List<StatementEntry> statements = new ArrayList<>();

    public void addStatement(StatementEntry entry){
        statements.add(0, entry);
        
    }

    
}
