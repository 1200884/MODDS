package src_restaurant;

import java.util.List;
import java.util.Random;

public class Table {
    private int tableNum;
    private boolean isAvailable;
    private List<Customer> customersUsingTable;
    boolean isServed;
    boolean needsToBePreparedForNextCustomers;
    boolean isAlreadyBeingCleaned;
    boolean isTableBeingUsedAsSecondTable;
    int minTimeToOrder;
    int maxTimeToOrder;
    int actualTimeToOrder;
    boolean isReadyToOrder;
    boolean hasOrdered;
    private Random random = new Random();


    public Table(int tableNum, boolean isAvailable, List<Customer> customersUsingTable, boolean isServed, boolean needsToBePreparedForNextCustomers, boolean isAlreadyBeingCleaned, boolean isTableBeingUsedAsSecondTable, int minTimeToOrder, int maxTimeToOrder, int actualTimeToOrder, boolean isReadyToOrder, boolean hasOrdered) {
        this.tableNum = tableNum;
        this.isAvailable = isAvailable;
        this.customersUsingTable = customersUsingTable;
        this.isServed = isServed;
        this.needsToBePreparedForNextCustomers = needsToBePreparedForNextCustomers;
        this.isAlreadyBeingCleaned = isAlreadyBeingCleaned;
        this.isTableBeingUsedAsSecondTable = isTableBeingUsedAsSecondTable;
        this.minTimeToOrder = minTimeToOrder;
        this.maxTimeToOrder = maxTimeToOrder;
        this.actualTimeToOrder = actualTimeToOrder;
        this.isReadyToOrder = isReadyToOrder;
        this.hasOrdered = hasOrdered;
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

    public int getMinTimeToOrder(){
        return this.minTimeToOrder;
    }

    public void setMinTimeToOrder(int minTimeToOrder){
        this.minTimeToOrder = minTimeToOrder;
    }

    public int getMaxTimeToOrder(){
        return this.maxTimeToOrder;
    }

    public void setMaxTimeToOrder(int maxTimeToOrder){
        this.maxTimeToOrder = maxTimeToOrder;
    }

    public int getActualTimeToOrder(){
        return this.actualTimeToOrder;
    }

    public void setActualTimeToOrder(int actualTimeToOrder){
        this.actualTimeToOrder = actualTimeToOrder;
    }

    public boolean isReadyToOrder() {
        return this.isReadyToOrder;
    }

    public void setIsReadyToOrder(boolean isReadyToOrder) {
        this.isReadyToOrder = isReadyToOrder;
    }

    public boolean hasOrdered() {
        return this.hasOrdered;
    }

    public void setHasOrdered(boolean hasOrdered) {
        this.hasOrdered = hasOrdered;
    }

    public void checkReadiness() {
        if (this.actualTimeToOrder >= this.minTimeToOrder && this.actualTimeToOrder <= this.maxTimeToOrder) {
            // Calcular a probabilidade proporcional
            double probability = (double)(this.actualTimeToOrder - this.minTimeToOrder) / (this.maxTimeToOrder - this.minTimeToOrder);

            // Gerar um número aleatório entre 0 e 1
            double randomValue = random.nextDouble();

            // Definir isReadyToOrder baseado na probabilidade
            this.isReadyToOrder = randomValue <= probability;
        }
    }
}

