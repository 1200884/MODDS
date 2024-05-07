package src_restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestaurantSimulation {
    private final int SIMULATION_DURATION = 5;
    private final int NUMBER_OF_TABLES = 2;
    private final int NUMBER_OF_WAITRESSES = 3;
    private final int NUMBER_OF_MEAT_COOKERS = 2;
    private final int NUMBER_OF_FISH_COOKERS = 2;
    private final int NUMBER_OF_PAYMENT_EMPLOYEES = 1;
    private final int MAX_CUSTOMER_QUEUE_ALLOW = 20;

    private boolean isRestaurantOpen = true;
    private boolean allTasksCompleted = false;

    int currentTime = 1;
    private int moneyEarned = 0;
    private int numberOfMeatDishesOfTheDay = 0;
    private int numberOfFishDishesOfTheDay = 0;
    
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
    Map<Customer,List<String>> customerPayingQueue = new HashMap<>();

    Random random = new Random();

    public RestaurantSimulation() throws InterruptedException{
        getTables();

        getWaitresses();

        getCookers();

        getPaymentEmployees();

        meatDish = new Dish("Meat", 10, 20, 6);
        fishDish = new Dish("Fish", 12, 25, 8);

        System.out.println("-----SIMULAÇÃO DE RESTAURANTE-----");
        System.out.println("No dia de hoje o restaurante tem disponíveis " + tables.size() + " mesas que podem ter até 4 clientes.");
        System.out.println("Hoje os pratos de carne custam " + meatDish.getPrice() + " euros e os pratos de peixe custam " + fishDish.getPrice() + " euros");
        System.out.println();
        System.out.println("COZINHEIROS NO RESTAURANTE");
        for(Cooker cooker : meatCookers){
            System.out.println(cooker.getName() + " com especialidade em carne");
        }
        for(Cooker cooker : fishCookers){
            System.out.println(cooker.getName() + " com especialidade em peixe");
        }
        System.out.println();
        System.out.println("EMPREGADOS DE MESA NO RESTAURANTE");
        for(Waitress waitress : waitresses){
            System.out.println(waitress.getName());
        }
        System.out.println();
        System.out.println("EMPREGADOS DE PAGAMENTO NO RESTAURANTE");
        for(PaymentEmployee paymentEmployee : paymentEmployees){
            System.out.println(paymentEmployee.getName());
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void simulate() throws InterruptedException{

        while(currentTime <= SIMULATION_DURATION || !allTasksCompleted){
            System.out.println("--------------------------------------------------");
            System.out.println("Time: " + currentTime);
            if(isRestaurantOpen){
                System.out.println("Restaurante encontra-se aberto");
            }else{
                System.out.println("Restaurante encontra-se fechado");
            }
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

            Thread.sleep(1000);

            simulateMinute();
            System.out.println("--------------------------------------------------");

            currentTime++;

            if(currentTime > SIMULATION_DURATION){
                isRestaurantOpen = false;
                checkIfAllTasksWereCompleted();
            }
        }

        System.out.println();
        System.out.println("-----DINHEIRO GANHO = " + moneyEarned + "-----");
        System.out.println("-----NÚMERO DE PRATOS DE CARNE FEITOS = " + numberOfMeatDishesOfTheDay + "-----");
        System.out.println("-----NÚMERO DE PRATOS DE PEIXE FEITOS = " + numberOfFishDishesOfTheDay + "-----");
    }

    /*
    public void printInstantInformation(){
        System.out.println("Instante: " + currentTime);
        System.out.println("Número de pessoas na fila: " + customerQueue.size());
        System.out.println();
        System.out.println("-----EMPREGADOS DE MESA-----");
        for(Waitress waitress : waitresses){
            if(!waitress.isAvailable()){
                System.out.println("Empregado de mesa " + waitress.getName() + " está a limpar a mesa " + waitress.getNumTableWorkingOn() + " e ainda vai demorar " + waitress.getCurrentActionTimeLeft() + " instantes a acabar.");
            }else{
                System.out.println("Empregado de mesa " + waitress.getName() + " está Disponível.");
            }
        }
        System.out.println();
        System.out.println("----COZINHEIROS-----");
        for(Cooker cooker : meatCookers){
            if(!cooker.isAvailable()){
                System.out.println("Cozinheiro " + cooker.getName() + " está a preparar um pedido de carne que vai demorar " + cooker.getCurrentActionTimeLeft() + " instantes a acabar.");
            }else{
                System.out.println("Cozinheiro " + cooker.getName() + " está Disponível");
            }
        }
        System.out.println();
        System.out.println("-----PEDIDOS-----");
        System.out.println("Pedidos de carne em espera: " + meatDishOrder.size());
        System.out.println("Pedidos de peixe em espera: " + fishDishOrder.size());
        System.out.println("Pedidos de carne prontos: " + readyMeatDishOrder.size());
        System.out.println("Pedidos de carne prontos: " + readyFishDishOrder.size());
        System.out.println();
        System.out.println("-----EMPREGADOS DE PAGAMENTO-----");
        for(PaymentEmployee paymentEmployee : paymentEmployees){
            if(!paymentEmployee.isAvailable()){
                System.out.println("Empregado de pagamento " + paymentEmployee.getName() + " está a realizar um processo de pagamento. Vai demorar " + paymentEmployee.getCurrentActionTimeLeft() + " instantes a acabar.");
            }
        }
        System.out.println();
        System.out.println("-----MESAS-----");
        for(Table table : tables){
            if(table.isAvailable()){
                System.out.println("Mesa " + table.getTableNum() + " está Disponível");
            }else if(table.isAlreadyBeingCleaned){
                System.out.println("Mesa " + table.getTableNum() + " está a ser Limpa");
            }else{
                System.out.println("Mesa " + table.getTableNum() + " está Ocupada");
            }
        }
        System.out.println();
    }
    */

    public void simulateMinute() throws InterruptedException{
        
        //Determina se chegaram ou não novos clientes
        if(customerQueue.size() < MAX_CUSTOMER_QUEUE_ALLOW && isRestaurantOpen){
            int numberOfCustomers = random.nextInt(5) + 1;
            if(random.nextInt(101) < 40){
                System.out.println("Chegaram " + numberOfCustomers + " clientes.");
                for(int i = 0; i < numberOfCustomers; i++){
                    Customer customer = generateCustomer();
                    customerQueue.add(customer);
                }
            }
        }else{
            if(random.nextInt(101) < 40 && isRestaurantOpen){
                System.out.println("Chegaram novos clientes mas a fila estava enorme. Eles foram embora!");
            }
        }

        //Empregado a sentar clientes
        for(Waitress waitress : waitresses){
            if(waitress.isAvailable() && !customerQueue.isEmpty()){
                int partySize = random.nextInt(4) + 1;
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

        System.out.println("-----COZINHEIROS-----");

        for(Cooker cooker : meatCookers){
            if(!cooker.isAvailable()){
                cooker.decreaseCurrentActionTimeLeft();
                if(cooker.getCurrentActionTimeLeft() == 0){
                    System.out.println("Cozinheiro " + cooker.getName() + " acabou um pedido de carne");
                    readyMeatDishOrder.add(meatDish);
                    cooker.setAvailable(true);
                }else{
                    System.out.println("Cozinheiro " + cooker.getName() + " ainda vai demorar " + cooker.getCurrentActionTimeLeft() + " instantes a acabar o pedido de carne que está a fazer");
                }
            }
        }

        //Cozinheiros a cozinhar
        if(!meatDishOrder.isEmpty()){
            for(Cooker cooker : meatCookers){
                if(cooker.isAvailable() && !meatDishOrder.isEmpty()){
                    int time = random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook();
                    System.out.println("Cozinheiro " + cooker.getName() + " vai pegar num pedido de carne que vai demorar " + time + " instantes a acabar");
                    numberOfMeatDishesOfTheDay++;
                    meatDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(time);
                }
            }
        }

        for(Cooker cooker : fishCookers){
            if(!cooker.isAvailable()){
                cooker.decreaseCurrentActionTimeLeft();
                if(cooker.getCurrentActionTimeLeft() == 0){
                    System.out.println("Cozinheiro " + cooker.getName() + " acabou um pedido de peixe.");
                    readyFishDishOrder.add(fishDish);
                    cooker.setAvailable(true);
                }else{
                    System.out.println("Cozinheiro " + cooker.getName() + " ainda vai demorar " + cooker.getCurrentActionTimeLeft() + " instantes a acabar o pedido de peixe que está a fazer");
                }
            }
        }

        if(!fishDishOrder.isEmpty()){
            for(Cooker cooker : fishCookers){
                if(cooker.isAvailable() && !fishDishOrder.isEmpty()){
                    int time = random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook();
                    System.out.println("Cozinheiro " + cooker.getName() + " vai pegar num pedido de peixe que vai demorar " + time + " instantes a acabar");
                    numberOfFishDishesOfTheDay++;
                    fishDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(time);
                }
            }
        }

        System.out.println("Pedidos de carne em espera: " + meatDishOrder.size());
        System.out.println("Pedidos de peixe em espera: " + fishDishOrder.size());
        System.out.println("Pedidos de carne prontos: " + readyMeatDishOrder.size());
        System.out.println("Pedidos de peixe prontos: " + readyFishDishOrder.size());

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
                        int eatingTime = random.nextInt(customer.getMaxTimeEat() - customer.getMinTimeEat()) + customer.getMinTimeEat();
                        customer.setCurrentActionTimeLeft(eatingTime);
                    }

                    table.setServed(true);
                }
            }else if(!table.getCustomersUsingTable().isEmpty() && table.isServed && !table.isAlreadyBeingCleaned()){
                int customersAlreadyEat = 0;
                System.out.println("-----CLIENTES A COMER NA MESA " + table.getTableNum() + "-----");
                for(Customer customer : table.getCustomersUsingTable()){
                    customer.decreaseCurrentActionTimeLeft();
                    System.out.println("Faltam " + customer.getCurrentActionTimeLeft() + " tempos");
                    if(customer.getCurrentActionTimeLeft() <= 0){
                        customersAlreadyEat++;
                    }
                }

                if(customersAlreadyEat == table.getCustomersUsingTable().size() && !table.isAlreadyBeingCleaned()){
                    System.out.println("Todas as pessoas da mesa " + table.getTableNum() + " já comeram");
                    table.setNeedsToBePreparedForNextCustomers(true);
                }
            }
        }

        //verificar se existem mesas que precisam de ser limpas
        for(Waitress waitress : waitresses){
            if(waitress.isAvailable()){
                for(Table table : tables){
                    if(table.needsToBePreparedForNextCustomers()){
                        System.out.println("Empregado de mesa " + waitress.getName() + " vai começar a limpar a mesa " + table.getTableNum());
                        prepareTableToNextCustomers(waitress, table);
                    }
                }
            }else{
                System.out.println("Empregado de mesa " + waitress.getName() + " ainda vai demorar " + waitress.getCurrentActionTimeLeft() + " instantes a acabar de limpar a mesa");
                waitress.decreaseCurrentActionTimeLeft();
                if(waitress.getCurrentActionTimeLeft()==0){
                    Table table = findTableWorkingOnPerWaitress(waitress);
                    findIfThereIsJoinedTableAndPrepareIt(table);
                    waitress.setAvailable(true);
                }
            }
        }

        //Empregado de pagamento a trabalhar
        for(PaymentEmployee paymentEmployee : paymentEmployees){
            if(paymentEmployee.isAvailable() && !customerPayingQueue.isEmpty()){
                System.out.println("-----EMPREGADO DE PAGAMENTO-----");
                Customer customer = customerPayingQueue.keySet().iterator().next();
                startPaymentProcess(paymentEmployee, customer.getPaymentMethod(), customerPayingQueue.get(customer));
                customerPayingQueue.remove(customer);
            }else if(paymentEmployee.getCurrentActionTimeLeft() != 0){
                paymentEmployee.decreaseCurrentActionTimeLeft();
                System.out.println("Empregado de pagamento está a prosseguir com o processo de pagamento e ainda vai demorar " + paymentEmployee.getCurrentActionTimeLeft() + " instantes");
                if (paymentEmployee.getCurrentActionTimeLeft() == 0) {
                    paymentEmployee.setAvailable(true);
                }
            }
        }
    }

    public void getTables(){
        tables = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_TABLES; i++){
            List<Customer> customersUsingTable = new ArrayList<>();
            Table table = new Table(i+1, true, customersUsingTable, false, false, false, false);
            tables.add(table);
        }
    }

    public void getWaitresses(){
        waitresses = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_WAITRESSES; i++){
            int number = random.nextInt(3-2) + 2;
            Waitress waitress = new Waitress(getRandomName(), number, number + 3,0,0, true);
            waitresses.add(waitress);
        }
    }

    public void getCookers(){
        meatCookers = new ArrayList<>();
        fishCookers = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_MEAT_COOKERS; i++){
            int number = random.nextInt(6-3) + 3;
            Cooker cooker = new Cooker(getRandomName(), "Meat", number, number + 3,0,true);
            meatCookers.add(cooker);
        }

        for(int i = 0; i < NUMBER_OF_FISH_COOKERS; i++){
            int number = random.nextInt(6-3) + 3;
            Cooker cooker = new Cooker(getRandomName(), "Fish", number, number + 3,0,true);
            fishCookers.add(cooker);
        }
    }

    public void getPaymentEmployees(){
        paymentEmployees = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_PAYMENT_EMPLOYEES; i++){
            int number = random.nextInt(3-2) + 2;
            PaymentEmployee paymentEmployee = new PaymentEmployee(getRandomName(), number, number + 1, number + 2, number + 3, 0, true);
            paymentEmployees.add(paymentEmployee);
        }
    }

    public Customer generateCustomer(){
        int number = random.nextInt(5-2) + 2;
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
        int meatAux = 0;
        int fishAux = 0;
        
        for(Customer customer : table.getCustomersUsingTable()){
            Dish dish = new Dish(customer.getFoodType(), 0,0,0);

            if(dish.getFoodType().equalsIgnoreCase("Meat")){
                meatDishOrder.add(dish);
                meatAux++;
            }else{
                fishDishOrder.add(dish);
                fishAux++;
            }
        }

        System.out.println(meatAux + " pedidos de carne");
        System.out.println(fishAux + " pedidos de peixe");
    }

    public void startPaymentProcess(PaymentEmployee paymentEmployee, String paymentMethod, List<String> typeOfDishesToPay){
        paymentEmployee.setAvailable(false);
        
        if(paymentMethod.equalsIgnoreCase("Card")){
            int time = random.nextInt(paymentEmployee.getMaxTimeExecCard() - paymentEmployee.getMinTimeExecCard()) + paymentEmployee.getMinTimeExecCard();
            System.out.println("Empregado de pagamento vai começar processo de pagamento de um cliente que vai pagar em Cartão. Vai demorar " + time + " instantes.");
            paymentEmployee.setCurrentActionTimeLeft(time);
        }else{
            int time = random.nextInt(paymentEmployee.getMaxTimeExecMoney() - paymentEmployee.getMinTimeExecMoney()) + paymentEmployee.getMinTimeExecMoney();
            System.out.println("Empregado de pagamento vai começar processo de pagamento de um cliente que vai pagar em Dinheiro. Vai demorar " + time + " instantes.");
            paymentEmployee.setCurrentActionTimeLeft(time);
        }

        for(String dish : typeOfDishesToPay){
            if(dish.equalsIgnoreCase("Meat")){
                moneyEarned = moneyEarned + meatDish.getPrice();
            }else{
                moneyEarned = moneyEarned + fishDish.getPrice();
            }
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
        int workingTime = random.nextInt(waitress.getMaxTimeExec() - waitress.getMinTimeExec()) + waitress.getMinTimeExec();

        waitress.setAvailable(false);
        waitress.setCurrentActionTimeLeft(workingTime);
        waitress.setNumTableWorkingOn(table.getTableNum());
        table.setNeedsToBePreparedForNextCustomers(false);
        table.setIsAlreadyBeingCleaned(true);
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
            if(tableAux != table && tableAux.isTableBeingUsedAsSecondTable && tableAux.getCustomersUsingTable().get(0) == table.getCustomersUsingTable().get(0)){
                tableAux.setAvailable(true);
                tableAux.setIsTableBeingUsedAsSecondTable(false);
                tableAux.setServed(false);
                tableAux.setCustomersUsingTable(new ArrayList<>());
                tableAux.setNeedsToBePreparedForNextCustomers(false);
                tableAux.setIsAlreadyBeingCleaned(false);
            }
        }

        List<String> typesOfDishesEated = new ArrayList<>();

        for(Customer customer : table.getCustomersUsingTable()){
            typesOfDishesEated.add(customer.getFoodType());
        }

        customerPayingQueue.put(table.getCustomersUsingTable().get(0), typesOfDishesEated);

        table.setAvailable(true);
        table.setIsTableBeingUsedAsSecondTable(false);
        table.setServed(false);
        table.setCustomersUsingTable(new ArrayList<>());
        table.setNeedsToBePreparedForNextCustomers(false);
        table.setIsAlreadyBeingCleaned(false);
    }

    public void checkIfAllTasksWereCompleted(){
        boolean checkIfItsAllOk = true;
        for(Table table : tables){
            if(!table.getCustomersUsingTable().isEmpty()){
                checkIfItsAllOk = false;
            }
        }

        for(Waitress waitress : waitresses){
            if(!waitress.isAvailable()){
                checkIfItsAllOk = false;
            }
        }

        if(!customerQueue.isEmpty()){
            checkIfItsAllOk = false;
        }

        for(PaymentEmployee paymentEmployee : paymentEmployees){
            if(!paymentEmployee.isAvailable()){
                checkIfItsAllOk = false;
            }
        }

        if(checkIfItsAllOk){
            allTasksCompleted = true;
        }
    }

}
