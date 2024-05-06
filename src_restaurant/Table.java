package src_restaurant;

import java.util.List;

public class Table {
    private int tableNum;
    private boolean isAvailable;
    private List<Customer> customersUsingTable;
    boolean isServed;
    boolean needsToBePreparedForNextCustomers;
    boolean isAlreadyBeingCleaned;
    boolean isTableBeingUsedAsSecondTable;

    public Table(int tableNum, boolean isAvailable, List<Customer> customersUsingTable, boolean isServed, boolean needsToBePreparedForNextCustomers, boolean isAlreadyBeingCleaned, boolean isTableBeingUsedAsSecondTable) {
        this.tableNum = tableNum;
        this.isAvailable = isAvailable;
        this.customersUsingTable = customersUsingTable;
        this.isServed = isServed;
        this.needsToBePreparedForNextCustomers = needsToBePreparedForNextCustomers;
        this.isAlreadyBeingCleaned = isAlreadyBeingCleaned;
        this.isTableBeingUsedAsSecondTable = isTableBeingUsedAsSecondTable;
    }

    // Getters and setters
    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public List<Customer> getCustomersUsingTable(){
        return customersUsingTable;
    }

    public void setCustomersUsingTable(List<Customer> customersUsingTable){
        this.customersUsingTable = customersUsingTable;
    }

    public boolean isServed() {
        return this.isServed;
    }

    public void setServed(boolean isServed) {
        this.isServed = isServed;
    }

    public boolean needsToBePreparedForNextCustomers() {
        return this.needsToBePreparedForNextCustomers;
    }

    public void setNeedsToBePreparedForNextCustomers(boolean needsToBePreparedForNextCustomers) {
        this.needsToBePreparedForNextCustomers = needsToBePreparedForNextCustomers;
    }

    public boolean isAlreadyBeingCleaned() {
        return this.isAlreadyBeingCleaned;
    }

    public void setIsAlreadyBeingCleaned(boolean isAlreadyBeingCleaned) {
        this.isAlreadyBeingCleaned = isAlreadyBeingCleaned;
    }

    public boolean isTableBeingUsedAsSecondTable() {
        return this.isTableBeingUsedAsSecondTable;
    }

    public void setIsTableBeingUsedAsSecondTable(boolean isTableBeingUsedAsSecondTable) {
        this.isTableBeingUsedAsSecondTable = isTableBeingUsedAsSecondTable;
    }
}

