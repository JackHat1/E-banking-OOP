package com.bank.manager;

//import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.orders.PaymentOrder;
import com.bank.model.orders.StandingOrder;
import com.bank.model.orders.TransferOrder;
//import com.bank.model.transactions.Transaction;
import com.bank.model.users.User;
import com.bank.storage.CsvStorageManager;

public class StandingOrderManager {

    private final List<StandingOrder> orders= new ArrayList<>();
    private final String filePath = ".data/orders/active.csv";
    private final CsvStorageManager storage= new CsvStorageManager();

    public StandingOrderManager(){
        
    }


    public void createOrder(StandingOrder order){
        orders.add(order);
    }

    public void deleteOrder(StandingOrder order){
        if(orders.contains(order)){

            orders.remove(order);
        }

    }

    public StandingOrder getOrderById(String orderID){

        for(StandingOrder order: orders){
            if(order.getOrderId().equals(orderID)){
                return order;
            }
        }
        return null;
    }


    public void saveOrders(){
        storage.saveAll(orders,filePath, false);
    }


    public void loadOrders(){

        List<String> lines= storage.loadLines(filePath);

        for(String line: lines){
            String[] parts = line.split(",");
                String typeKey= parts[0];

                StandingOrder order;
                if(typeKey.equals("PaymentOrder")){
                    order= new PaymentOrder("", "", null, null, 0.0, LocalDate.now(), LocalDate.now());

                }else if (typeKey.equals("TransferOrder")){
                    order= new TransferOrder("", "", null, null, 0.0, 0, 0, LocalDate.now(), LocalDate.now());

                }else{
                    continue;
                }

                order.unmarshal(line);
                orders.add(order);
        }
        
        
    }

    public void executeAllOrders(LocalDate date, BillManager billMan, AccountManager accountMan, TransactionManager transactionMan, User user){
        for(StandingOrder order: orders){
            order.execute(date, billMan, accountMan, transactionMan, user);
            //Transaction transaction = order.createTransaction();

        }
    }


    public List<StandingOrder> getAllOrders(){
        return new ArrayList<>(orders);
    }

    
}
