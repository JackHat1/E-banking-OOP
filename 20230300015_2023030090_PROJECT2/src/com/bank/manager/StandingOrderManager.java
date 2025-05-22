package com.bank.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.orders.PaymentOrder;
import com.bank.model.orders.StandingOrder;
import com.bank.model.orders.TransferOrder;
import com.bank.model.users.User;
import com.bank.storage.CsvStorageManager;
import com.bank.utilities.GlobalClock;

public class StandingOrderManager {

    private final List<StandingOrder> orders= new ArrayList<>();
    private final String activeFilePath = ".data/orders/active.csv";
    //private final String activeFilePath = ".data/orders/";
    private final CsvStorageManager storage= new CsvStorageManager();

    private final String expiredFilePath= ".data/orders/expired.csv";
    private final String failedFilePath= ".data/orders/failed.csv";

    public StandingOrderManager(){
        loadOrders();
    }


    public void createOrder(StandingOrder order){
        orders.add(order);
    }

    public void deleteOrder(StandingOrder order){
 
        orders.remove(order);

    }

    public StandingOrder getOrderById(String orderID){

        for(StandingOrder order: orders){
            if(order.getOrderId().equals(orderID)){
                return order;
            }
        }
        return null;
    }


    public void saveOrders(List<StandingOrder> orders){
        storage.saveAll(orders,activeFilePath, false);
    }

    public void saveExpiredOrders(List<StandingOrder> expiredOrders){
        storage.saveAll(expiredOrders, expiredFilePath, false);
    }

    public void saveFailedOrders(List<StandingOrder> failedOrders){
        storage.saveAll(failedOrders, failedFilePath, false);
    }


    public void loadOrders(){

        List<String> lines= storage.loadLines(activeFilePath);

        for(String line: lines){
            String[] parts = line.split(",");
                String typeKey= parts[0];

                StandingOrder order;
                if(typeKey.equals("PaymentOrder")){
                    order= new PaymentOrder("", "", null, null, 0.0, GlobalClock.getDate(), GlobalClock.getDate());

                }else if (typeKey.equals("TransferOrder")){
                    order= new TransferOrder("", "", null, null, 0.0, 0, 0, GlobalClock.getDate(), GlobalClock.getDate());

                }else{
                    continue;
                }

                order.unmarshal(line);
                orders.add(order);
        }
        
        
    }

    public void executeAllOrders(LocalDate date, BillManager billMan, AccountManager accountMan, TransactionManager transactionMan, User user){
        List<StandingOrder> stillActive= new ArrayList<>();
        List<StandingOrder> expiredOrders= new ArrayList<>();
        List<StandingOrder> failedOrders= new ArrayList<>();


        for(StandingOrder order: orders){
            order.execute(date, billMan, accountMan, transactionMan, user);
            
            if(order.isExpired()){
                expiredOrders.add(order);
                
            }else if(order.isFailed()){
                failedOrders.add(order);

            }else{
                stillActive.add(order);
            }

        }

        saveOrders(stillActive);
        saveExpiredOrders(expiredOrders);
        saveFailedOrders(failedOrders);

        orders.clear();
        orders.addAll(stillActive);

    }


    public List<StandingOrder> getAllOrders(){
        return new ArrayList<>(orders);
    }

    
}
