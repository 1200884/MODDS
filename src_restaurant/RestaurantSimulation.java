package src_restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RestaurantSimulation {
    private final int SIMULATION_DURATION = 20;
    private final int NUMBER_OF_TABLES = 10;
    private final int NUMBER_OF_WAITRESSES = 3;
    private final int NUMBER_OF_MEAT_COOKERS = 2;
    private final int NUMBER_OF_FISH_COOKERS = 2;
    private final int NUMBER_OF_PAYMENT_EMPLOYEES = 1;
    private final int MAX_CUSTOMER_QUEUE_ALLOW = 20;
    
    List<Table> tables;
    List<Waitress> waitresses;
    List<Cooker> meatCookers;
    List<Cooker> fishCookers;
    List<PaymentEmployee> paymentEmployees;
    Dish meatDish;
    Dish fishDish;
    List<Dish> meatDishOrder = new ArrayList<>();
    List<Dish> fishDishOrder = new ArrayList<>();
    List<Dish> readyMeatDishOrder = new ArrayList<>();
    List<Dish> readyFishDishOrder = new ArrayList<>();

    List<Customer> customerQueue = new ArrayList<>();
    List<Customer> customerPayingQueue = new ArrayList<>();

    Random random = new Random();

    public RestaurantSimulation() throws InterruptedException{
        getTables();

        getWaitresses();

        getCookers();

        getPaymentEmployees();

        meatDish = new Dish("Meat", 10, 20);
        fishDish = new Dish("Fish", 12, 25);
    }

    public void simulate() throws InterruptedException{
        int currentTime = 1;

        while(currentTime <= SIMULATION_DURATION){
            System.out.println();
            System.out.println("Time: " + currentTime);
            System.out.println("Tamanho da fila: " + customerQueue.size());
            System.out.println("-----EMPREGADOS DE MESA-----");
            for(Waitress waitress : waitresses){
                String status = waitress.isAvailable() ? "Disponível" : "Ocupado";
                System.out.println("Empregado de mesa " + waitress.getName() + " está " + status);
            }
            System.out.println("-----MESAS-----");
            for(Table table : tables){
                String status = table.isAvailable() ? "Disponível" : "Ocupada";
                System.out.println("Mesa " + table.getTableNum() + " está " + status);
            }
            System.out.println("-----COZINHEIROS-----");
            for(Cooker cooker : meatCookers){
                if(cooker.getCurrentActionTimeLeft() == 0){
                    System.out.println("Cozinheiro de carne " + cooker.getName() + " está Disponível");
                }else{
                    System.out.println("Cozinheiro de carne " + cooker.getName() + " está Ocupado a fazer um prato durante " + cooker.getCurrentActionTimeLeft() + " tempo");
                }
            }
            for(Cooker cooker : fishCookers){
                if(cooker.getCurrentActionTimeLeft() == 0){
                    System.out.println("Cozinheiro de peixe " + cooker.getName() + " está Disponível");
                }else{
                    System.out.println("Cozinheiro de peixe " + cooker.getName() + " está Ocupado a fazer um prato durante " + cooker.getCurrentActionTimeLeft() + " tempo");
                }
            }

            Thread.sleep(1000);

            simulateMinute();

            currentTime++;
        }
    }

    public void simulateMinute() throws InterruptedException{
        
        //Determina se chegaram ou não novos clientes
        if(customerQueue.size() < MAX_CUSTOMER_QUEUE_ALLOW){
            int numberOfCustomers = random.nextInt(5) + 1;
            if(random.nextInt(101) < 40){
                System.out.println("Chegaram " + numberOfCustomers + " clientes.");
                for(int i = 0; i < numberOfCustomers; i++){
                    Customer customer = generateCustomer();
                    customerQueue.add(customer);
                }
            }
        }else{
            if(random.nextInt(101) < 40){
                System.out.println("Chegaram novos clientes mas a fila estava enorme. Eles foram embora!");
            }
        }

        //Empregado a sentar clientes
        for(Waitress waitress : waitresses){
            if(waitress.isAvailable() && !customerQueue.isEmpty()){
                int partySize = random.nextInt(6) + 1;
                if(partySize > customerQueue.size()){
                    partySize = customerQueue.size();
                }

                int availableTablesAtTheMoment = getNumOfAvailableTablesAtTheMoment();

                if(partySize > 4 && availableTablesAtTheMoment >= 2){
                    //clientes podem entrar porque se vão juntar duas mesas para eles
                    Table table = giveTablesToClient(partySize);
                    System.out.println("Mesa para " + partySize);
                    retrieveOrdersFromTable(table);
                }else if(partySize <= 4 && availableTablesAtTheMoment >= 1){
                    Table table = giveTablesToClient(partySize);
                    System.out.println("Mesa para " + partySize);
                    retrieveOrdersFromTable(table);
                }else{
                    //Não há mesas suficientes
                }
            }else{
                //Nenhum empregado de mesa disponível
            }
        }

        //Cozinheiros a cozinhar
        if(!meatDishOrder.isEmpty()){
            for(Cooker cooker : meatCookers){
                if(cooker.isAvailable() && !meatDishOrder.isEmpty()){
                    System.out.println("Tamanho meatDishOrder: " + meatDishOrder.size());
                    meatDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook());
                }
            }
        }

        if(!fishDishOrder.isEmpty()){
            for(Cooker cooker : fishCookers){
                if(cooker.isAvailable() && !fishDishOrder.isEmpty()){
                    System.out.println("Tamanho fishDishOrder: " + fishDishOrder.size());
                    fishDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook());
                }
            }
        }

        for(Cooker cooker : meatCookers){
            if(!cooker.isAvailable()){
                cooker.decreaseCurrentActionTimeLeft();
                if(cooker.getCurrentActionTimeLeft() == 0){
                    readyMeatDishOrder.add(meatDish);
                }
            }
        }

        for(Cooker cooker : fishCookers){
            if(!cooker.isAvailable()){
                cooker.decreaseCurrentActionTimeLeft();
                if(cooker.getCurrentActionTimeLeft() == 0){
                    readyFishDishOrder.add(fishDish);
                }
            }
        }

        //Verificar se existem pedidos prontos para entregar para mesas (deve se entregar a todas as pessoas da mesa ao mesmo tempo)
        for(Table table : tables){

            if(!table.getCustomersUsingTable().isEmpty() && !table.isServed){
                int numMeatRequests = getNumMeatRequestsFromTable(table);
                int numFishRequests = getNumFishRequestsFromTable(table);

                if(numMeatRequests <= readyMeatDishOrder.size() && numFishRequests <= readyFishDishOrder.size()){
                    for(int i = 0; i < numMeatRequests; i++){
                        readyMeatDishOrder.remove(0);
                    }

                    for(int i = 0; i < numFishRequests; i++){
                        readyFishDishOrder.remove(0);
                    }

                    for(Customer customer : table.getCustomersUsingTable()){
                        int eatingTime = random.nextInt(customer.getMaxTimeEat() - customer.getMinTimeEat()) - customer.getMinTimeEat();
                        customer.setCurrentActionTimeLeft(eatingTime);
                    }
                }
            }else if(!table.getCustomersUsingTable().isEmpty() && table.isServed){
                int customersAlreadyEat = 0;

                for(Customer customer : table.getCustomersUsingTable()){
                    customer.decreaseCurrentActionTimeLeft();
                    if(customer.getCurrentActionTimeLeft() <= 0){
                        customersAlreadyEat++;
                    }
                }

                if(customersAlreadyEat == table.getCustomersUsingTable().size()){
                    table.setNeedsToBePreparedForNextCustomers(true);
                }
            }
        }

        //Empregado de pagamento a trabalhar
        for(PaymentEmployee paymentEmployee : paymentEmployees){
            if(paymentEmployee.isAvailable() && !customerPayingQueue.isEmpty()){
                startPaymentProcess(paymentEmployee, customerPayingQueue.get(0).getPaymentMethod());
                customerPayingQueue.remove(0);
            }else{
                paymentEmployee.decreaseCurrentActionTimeLeft();
            }
        }

        //verificar se existem mesas que precisam de ser limpas
        for(Waitress waitress : waitresses){
            if(waitress.isAvailable()){
                for(Table table : tables){
                    if(table.needsToBePreparedForNextCustomers()){
                        prepareTableToNextCustomers(waitress, table);
                    }
                }
            }else{
                waitress.decreaseCurrentActionTimeLeft();
                if(waitress.getCurrentActionTimeLeft()==0){
                    Table table = findTableWorkingOnPerWaitress(waitress);
                    findIfThereIsJoinedTableAndPrepareIt(table);
                }
            }
        }
    }

    public void getTables(){
        tables = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_TABLES; i++){
            List<Customer> customersUsingTable = new ArrayList<>();
            Table table = new Table(i+1, true, customersUsingTable, false, false, false);
            tables.add(table);
        }
    }

    public void getWaitresses(){
        waitresses = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_WAITRESSES; i++){
            int number = random.nextInt(5) + 1;
            Waitress waitress = new Waitress(getRandomName(), number, number + 3,0,0, true);
            waitresses.add(waitress);
        }
    }

    public void getCookers(){
        meatCookers = new ArrayList<>();
        fishCookers = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_MEAT_COOKERS; i++){
            int number = random.nextInt(5) + 1;
            Cooker cooker = new Cooker(getRandomName(), "Meat", number, number + 3,0,true);
            meatCookers.add(cooker);
        }

        for(int i = 0; i < NUMBER_OF_FISH_COOKERS; i++){
            int number = random.nextInt(5) + 1;
            Cooker cooker = new Cooker(getRandomName(), "Fish", number, number + 3,0,true);
            fishCookers.add(cooker);
        }
    }

    public void getPaymentEmployees(){
        paymentEmployees = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_PAYMENT_EMPLOYEES; i++){
            int number = random.nextInt(3) + 1;
            PaymentEmployee paymentEmployee = new PaymentEmployee(getRandomName(), number, number + 1, number + 2, number + 3, 0, true);
            paymentEmployees.add(paymentEmployee);
        }
    }

    public Customer generateCustomer(){
        int number = random.nextInt(5) + 1;
        String paymentMethod = random.nextInt(10) + 1 < 5 ? "Card" : "Money";
        String foodType = random.nextInt(10) + 1 < 5 ? "Meat" : "Fish";

        return new Customer(paymentMethod, foodType, number, number + 1,0);
    }

    public String getRandomName() {
        String[] NAMES = {"Jack", "John", "David", "Emily", "Emma", "Sophia", "Olivia", "Ava", "Isabella", "Mia", "Ethan", "Noah", "Liam", "Mason", "Lucas", "Oliver", "Elijah", "James", "Benjamin", "Alexander"};
        int index = random.nextInt(NAMES.length);
        return NAMES[index];
    }

    public int getNumOfAvailableTablesAtTheMoment(){
        int availableTablesAtTheMoment = 0;
        
        for(Table table : tables){
            if(table.isAvailable()){
                availableTablesAtTheMoment++;
            }
        }

        return availableTablesAtTheMoment;
    }

    public Table giveTablesToClient(int numCustomers){
        
        int numTables = numCustomers > 4 ? 2 : 1;

        List<Customer> customersToTable = removeCustomersFromQueue(numCustomers);

        Table tableToReturn = null;

        if(numTables == 1){
            int aux = 0;
            for(Table table : tables){
                if(table.isAvailable()){
                    table.setAvailable(false);
                    table.setCustomersUsingTable(customersToTable);
                    tableToReturn = table;
                    aux++;
                }

                if(aux == numTables){
                    break;
                }
            }
        }else{
            int aux = 0;
            for(Table table : tables){
                if(table.isAvailable()){
                    table.setAvailable(false);
                    table.setCustomersUsingTable(customersToTable);
                    aux++;
                    if(aux==1){
                        tableToReturn = table;
                    }
                }

                if(aux == numTables){
                    table.setIsTableBeingUsedAsSecondTable(true);
                    break;
                }
            }
        }

        return tableToReturn;
    }

    public List<Customer> removeCustomersFromQueue(int numCustomers){
        List<Customer> list = new ArrayList<>();

        for(Customer customer : customerQueue){
            list.add(customer);

            numCustomers--;

            if(numCustomers == 0){
                break;
            }
        }

        for(Customer customer : list){
            customerQueue.remove(customer);
        }

        return list;
    }

    public void retrieveOrdersFromTable(Table table){
        for(Customer customer : table.getCustomersUsingTable()){
            Dish dish = new Dish(customer.getFoodType(), 0,0);

            if(dish.getFoodType().equalsIgnoreCase("Meat")){
                meatDishOrder.add(dish);
            }else{
                fishDishOrder.add(dish);
            }
        }
    }

    public void startPaymentProcess(PaymentEmployee paymentEmployee, String paymentMethod){
        paymentEmployee.setAvailable(false);
        
        if(paymentMethod.equalsIgnoreCase("Card")){
            int time = random.nextInt(paymentEmployee.getMaxTimeExecCard() - paymentEmployee.getMinTimeExecCard()) - paymentEmployee.getMinTimeExecCard();
            paymentEmployee.setCurrentActionTimeLeft(time);
        }
    }

    public int getNumMeatRequestsFromTable(Table table){
        int numMeatRequestsFromTable = 0;
        
        for(Customer customer : table.getCustomersUsingTable()){
            if(customer.getFoodType().equalsIgnoreCase("Meat")){
                numMeatRequestsFromTable++;
            }
        }

        return numMeatRequestsFromTable;
    }

    public int getNumFishRequestsFromTable(Table table){
        int numFishRequestsFromTable = 0;
        
        for(Customer customer : table.getCustomersUsingTable()){
            if(customer.getFoodType().equalsIgnoreCase("Fish")){
                numFishRequestsFromTable++;
            }
        }

        return numFishRequestsFromTable;
    }

    public void prepareTableToNextCustomers(Waitress waitress, Table table){
        int workingTime = random.nextInt(waitress.getMaxTimeExec() - waitress.getMinTimeExec()) - waitress.getMinTimeExec();

        waitress.setAvailable(false);
        waitress.setCurrentActionTimeLeft(workingTime);
        waitress.setNumTableWorkingOn(table.getTableNum());
    }

    public Table findTableWorkingOnPerWaitress(Waitress waitress){
        for(Table table : tables){
            if(table.getTableNum() == waitress.getNumTableWorkingOn()){
                return table;
            }
        }

        return null;
    }

    public void findIfThereIsJoinedTableAndPrepareIt(Table table){
        for(Table tableAux : tables){
            if(tableAux != table && tableAux.isTableBeingUsedAsSecondTable && tableAux.getCustomersUsingTable().containsAll(table.getCustomersUsingTable())){
                tableAux.setAvailable(true);
                tableAux.setIsTableBeingUsedAsSecondTable(false);
                tableAux.setServed(false);
                tableAux.setCustomersUsingTable(new ArrayList<>());
                tableAux.setNeedsToBePreparedForNextCustomers(false);
            }
        }

        table.setAvailable(true);
        table.setIsTableBeingUsedAsSecondTable(false);
        table.setServed(false);
        table.setCustomersUsingTable(new ArrayList<>());
        table.setNeedsToBePreparedForNextCustomers(false);
    }

}
