package com.bank.bill;

import java.util.ArrayList;
import java.util.List;

public class BillManager {
    private List<Bill> bills;

    public BillManager() {
        this.bills = new ArrayList<>();
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getAllBills() {
        return bills;
    }

    public List<Bill> getUnpaidBills() {
        List<Bill> unpaid = new ArrayList<>();
        for (Bill b : bills) {
            if (!b.isPaid()) {
                unpaid.add(b);
            }
        }
        return unpaid;
    }

    public Bill getBillById(String billId) {
        for (Bill b : bills) {
            if (b.getBillId().equals(billId)) {
                return b;
            }
        }
        return null;
    }

    public boolean markBillAsPaid(String billId) {
        Bill bill = getBillById(billId);
        if (bill != null && !bill.isPaid()) {
            bill.markAsPaid();
            return true;
        }
        return false;
    }
}
