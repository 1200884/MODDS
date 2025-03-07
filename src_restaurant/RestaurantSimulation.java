package src_restaurant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestaurantSimulation {

    private final int SIMULATION_DURATION = 180;
    private final int NUMBER_OF_TABLES = 10;
    private final int NUMBER_OF_WAITRESSES = 3;
    private final int NUMBER_OF_MEAT_COOKERS = 2;
    private final int NUMBER_OF_FISH_COOKERS = 2;
    private final int NUMBER_OF_PAYMENT_EMPLOYEES = 1;
    private final int MAX_CUSTOMER_QUEUE_ALLOW = 5;

    private boolean isRestaurantOpen = true;
    private boolean allTasksCompleted = false;
    private double customer_arrive_percentage;

    int currentTime = 1;
    int minute = 0;
    int hour = 19;
    int i = 0, k = 0, l = 0;
    private int moneyEarned = 0;
    private int numberOfMeatDishesOfTheDay = 0;
    private int numberOfFishDishesOfTheDay = 0;
    private int timeMaxCustomerQueue = 0;
    private int[] waitressesTimeAvailable; 
    private int[] waitressesTimeUnavailable;
    private int[] meatCookerTimeAvailable;
    private int[] meatCookerTimeUnavailable;
    private int[] fishCookerTimeAvailable;
    private int[] fishCookerTimeUnavailable;
    private int[] paymentEmployeeTimeAvailable;
    private int[] paymentEmployeeTimeUnavailable;
    private int numberofClientsServed = 0;
    DecimalFormat df = new DecimalFormat("0.0");

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
    Map<Customer, List<String>> customerPayingQueue = new HashMap<>();

    Random random = new Random();

    public RestaurantSimulation() throws InterruptedException {
        getTables();

        getWaitresses();

        waitressesTimeAvailable = new int[waitresses.size()];
        waitressesTimeUnavailable = new int[waitresses.size()];

        getCookers();

        meatCookerTimeAvailable = new int[meatCookers.size()];
        meatCookerTimeUnavailable = new int[meatCookers.size()];
        fishCookerTimeAvailable = new int[fishCookers.size()];
        fishCookerTimeUnavailable = new int[fishCookers.size()];

        getPaymentEmployees();

        paymentEmployeeTimeAvailable = new int[paymentEmployees.size()];
        paymentEmployeeTimeUnavailable = new int[paymentEmployees.size()];

        meatDish = new Dish("Meat", 10, 20, 6);
        fishDish = new Dish("Fish", 12, 25, 8);

        System.out.println("-----SIMULACAO DE RESTAURANTE-----");
        System.out.println("No dia de hoje o restaurante tem disponiveis " + tables.size() + " mesas que podem ter ate 4 clientes.");
        System.out.println("Hoje os pratos de carne custam " + meatDish.getPrice() + " euros e os pratos de peixe custam " + fishDish.getPrice() + " euros");
        System.out.println();
        System.out.println("COZINHEIROS NO RESTAURANTE");
        for (Cooker cooker : meatCookers) {
            System.out.println(cooker.getName() + " com especialidade em carne");
        }
        for (Cooker cooker : fishCookers) {
            System.out.println(cooker.getName() + " com especialidade em peixe");
        }
        System.out.println();
        System.out.println("EMPREGADOS DE MESA NO RESTAURANTE");
        for (Waitress waitress : waitresses) {
            System.out.println(waitress.getName());
        }
        System.out.println();
        System.out.println("EMPREGADOS DE PAGAMENTO NO RESTAURANTE");
        for (PaymentEmployee paymentEmployee : paymentEmployees) {
            System.out.println(paymentEmployee.getName());
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void simulate() throws InterruptedException {

        while (currentTime <= SIMULATION_DURATION || !allTasksCompleted) {
            System.out.println("--------------------------------------------------");
            if (minute == 60) {
                minute = 0;
                hour++;
            }
            System.out.println("Instante: " + currentTime);
            if (minute < 10) {
                System.out.println("Hora: " + hour + ":0" + minute);
            } else {
                System.out.println("Hora: " + hour + ":" + minute);
            }
            if (isRestaurantOpen) {
                System.out.println("Restaurante encontra-se aberto");
            } else {
                System.out.println("Restaurante encontra-se fechado");
            }
            System.out.println("Tamanho da fila: " + customerQueue.size());
            if (customerQueue.size() == 10) {
                timeMaxCustomerQueue++;
            }
            System.out.println(timeMaxCustomerQueue);
            System.out.println("-----EMPREGADOS DE MESA-----");
            i = 0;
            for (Waitress waitress : waitresses) {
                String status = waitress.isAvailable() ? "Disponivel" : "Ocupado";
                System.out.println(waitress.getName() + " esta " + status);
                if(status.equalsIgnoreCase("Disponivel")){
                    waitressesTimeAvailable[i] = waitressesTimeAvailable[i] + 1;
                }
                System.out.println(waitressesTimeAvailable[i]);
                i++;
            }



            //Thread.sleep(1000);
            simulateMinute();
            System.out.println("--------------------------------------------------");

            currentTime++;
            minute++;

            if (currentTime > SIMULATION_DURATION) {
                isRestaurantOpen = false;
                checkIfAllTasksWereCompleted();
            }
        }

        for(int i = 0; i < waitressesTimeAvailable.length; i++){
            waitressesTimeUnavailable[i] = currentTime - waitressesTimeAvailable[i];
        }

        for(int i = 0; i < meatCookerTimeAvailable.length; i++){
            meatCookerTimeUnavailable[i] = currentTime - meatCookerTimeAvailable[i];
        }

        for(int i = 0; i < fishCookerTimeAvailable.length; i++){
            fishCookerTimeUnavailable[i] = currentTime - fishCookerTimeAvailable[i];
        }

        for(int i = 0; i < waitressesTimeAvailable.length; i++){
            System.out.println("Tempo em que o " + waitresses.get(i).getName() + " esteve disponivel -> "+  waitressesTimeAvailable[i]);
            System.out.println("Tempo em que o " + waitresses.get(i).getName() + " esteve indisponivel -> "+  waitressesTimeUnavailable[0]);
            System.out.println("Percentagem de disponibilidade do " + waitresses.get(i).getName() + " ->" + df.format((float) waitressesTimeAvailable[i] / currentTime * 100) + "%");
            System.out.println("-------------------------------------------------------------");
        }

        for(int i = 0; i < meatCookerTimeAvailable.length; i++){
            System.out.println("Tempo em que o " + meatCookers.get(i).getName() + " (de carne) esteve disponivel -> "+  meatCookerTimeAvailable[i]);
            System.out.println("Tempo em que o " + meatCookers.get(i).getName() + " (de carne) esteve indisponivel -> "+  meatCookerTimeUnavailable[0]);
            System.out.println("Percentagem de disponibilidade do " + meatCookers.get(i).getName() + " ->" + df.format((float) meatCookerTimeAvailable[i] / currentTime * 100) + "%");
            System.out.println("-------------------------------------------------------------");
        }

        for(int i = 0; i < fishCookerTimeAvailable.length; i++){
            System.out.println("Tempo em que o " + fishCookers.get(i).getName() + " (de peixe) esteve disponivel -> "+  fishCookerTimeAvailable[i]);
            System.out.println("Tempo em que o " + fishCookers.get(i).getName() + " (de peixe) esteve indisponivel -> "+  fishCookerTimeUnavailable[0]);
            System.out.println("Percentagem de disponibilidade do " + fishCookers.get(i).getName() + " ->" + df.format((float) fishCookerTimeAvailable[i] / currentTime * 100) + "%");
            System.out.println("-------------------------------------------------------------");
        }

        for(int i = 0; i < paymentEmployeeTimeAvailable.length; i++){
            System.out.println("Tempo em que o " + paymentEmployees.get(i).getName() + " esteve disponivel -> "+  paymentEmployeeTimeAvailable[i]);
            System.out.println("Tempo em que o " + paymentEmployees.get(i).getName() + " esteve indisponivel -> "+  paymentEmployeeTimeAvailable[0]);
            System.out.println("Percentagem de disponibilidade do " + paymentEmployees.get(i).getName() + " ->" + df.format((float) paymentEmployeeTimeAvailable[i] / currentTime * 100) + "%");
            System.out.println("-------------------------------------------------------------");
        }

        System.out.println("Numero de clientes atendidos -> " + numberofClientsServed);
        System.out.println("-------------------------------------------------------------");
        System.out.println("Percentagem de tempo com a fila cheia -> " + df.format((float) timeMaxCustomerQueue / currentTime * 100)+ "%");
        moneyEarned = (numberOfFishDishesOfTheDay * fishDish.getPrice()) + (numberOfMeatDishesOfTheDay * meatDish.getPrice());
        System.out.println();
        System.out.println("-----DINHEIRO GANHO = " + moneyEarned + "-----");
        System.out.println("-----NUMERO DE PRATOS DE CARNE FEITOS = " + numberOfMeatDishesOfTheDay + "-----");
        System.out.println("-----NUMERO DE PRATOS DE PEIXE FEITOS = " + numberOfFishDishesOfTheDay + "-----");
    }

    public void simulateMinute() throws InterruptedException {

        double percentageOfCustomerArrive = random.nextDouble();
        updatePercentageOfCustomerArriveBasedOnTime();

        if (percentageOfCustomerArrive < customer_arrive_percentage && isRestaurantOpen) {
            int numberOfCustomers = random.nextInt(7 - 1) + 1;
            if (customerQueue.size() < MAX_CUSTOMER_QUEUE_ALLOW) {
                System.out.println("Chegaram " + numberOfCustomers + " clientes:");
                for (int i = 0; i < numberOfCustomers; i++) {
                    Customer customer = generateCustomer();
                    customerQueue.add(customer);
                    System.out.println(i + " - " + customer.getName());
                }
            } else {
                System.out.println("Chegaram novos clientes mas a fila estava enorme. Eles foram embora!");
            }
        }

        //Empregado a sentar clientes
        for (Waitress waitress : waitresses) {
            if (waitress.isAvailable() && !customerQueue.isEmpty()) {
                int partySize = random.nextInt(7 - 1) + 1;
                if (partySize > customerQueue.size()) {
                    partySize = customerQueue.size();
                }

                int availableTablesAtTheMoment = getNumOfAvailableTablesAtTheMoment();

                if (partySize > 4 && availableTablesAtTheMoment >= 2) {
                    //clientes podem entrar porque se vao juntar duas mesas para eles
                    Table table = giveTablesToClient(partySize);
                    generateTimesToOrder(table);
                    table.setActualTimeToOrder(0);
                    table.setHasOrdered(false);
                    System.out.println("Mesa para " + partySize);
                } else if (partySize <= 4 && availableTablesAtTheMoment >= 1) {
                    Table table = giveTablesToClient(partySize);
                    generateTimesToOrder(table);
                    table.setActualTimeToOrder(0);
                    table.setHasOrdered(false);
                    System.out.println("Mesa para " + partySize);
                }
            }
        }

        //verificar se empregado pode recolher pedido
        for (Table table : tables) {
            if (!table.getCustomersUsingTable().isEmpty() && !table.isServed() && !table.isTableBeingUsedAsSecondTable() && !table.isReadyToOrder() && !table.hasOrdered()) {
                table.actualTimeToOrder++;
                table.checkReadiness();
            } else if (!table.getCustomersUsingTable().isEmpty() && !table.isServed() && !table.isTableBeingUsedAsSecondTable() && table.isReadyToOrder() && !table.hasOrdered()) {
                for (Waitress waitress : waitresses) {
                    if (waitress.isAvailable()) {
                        retrieveOrdersFromTable(waitress, table);
                        table.setHasOrdered(true);
                        table.setIsReadyToOrder(false);
                        break;
                    }
                }
            }
        }

        System.out.println("-----COZINHEIROS-----");

        for (Cooker cooker : meatCookers) {
            if (!cooker.isAvailable()) {
                cooker.decreaseCurrentActionTimeLeft();
                if (cooker.getCurrentActionTimeLeft() == 0) {
                    System.out.println("Cozinheiro " + cooker.getName() + " acabou um pedido de carne");
                    readyMeatDishOrder.add(meatDish);
                    cooker.setAvailable(true);
                } else {
                    System.out.println("Cozinheiro " + cooker.getName() + " ainda vai demorar " + cooker.getCurrentActionTimeLeft() + " instantes a acabar o pedido de carne que esta a fazer");
                }
            }
        }

        //Cozinheiros a cozinhar
        if (!meatDishOrder.isEmpty()) {
            for (Cooker cooker : meatCookers) {
                if (cooker.isAvailable() && !meatDishOrder.isEmpty()) {
                    int time = random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook();
                    System.out.println("Cozinheiro " + cooker.getName() + " vai pegar num pedido de carne que vai demorar " + time + " instantes a acabar");
                    numberOfMeatDishesOfTheDay++;
                    meatDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(time);
                }
            }
        }

        k = 0;
        for (Cooker cooker : meatCookers) {
            if(cooker.isAvailable()){
                meatCookerTimeAvailable[k]++;
            }
            k++;
        }

        for (Cooker cooker : fishCookers) {
            if (!cooker.isAvailable()) {
                cooker.decreaseCurrentActionTimeLeft();
                if (cooker.getCurrentActionTimeLeft() == 0) {
                    System.out.println("Cozinheiro " + cooker.getName() + " acabou um pedido de peixe.");
                    readyFishDishOrder.add(fishDish);
                    cooker.setAvailable(true);
                } else {
                    System.out.println("Cozinheiro " + cooker.getName() + " ainda vai demorar " + cooker.getCurrentActionTimeLeft() + " instantes a acabar o pedido de peixe que esta a fazer");
                }
            }
        }

        if (!fishDishOrder.isEmpty()) {
            for (Cooker cooker : fishCookers) {
                if (cooker.isAvailable() && !fishDishOrder.isEmpty()) {
                    int time = random.nextInt(cooker.getMaxTimeCook() - cooker.getMinTimeCook()) + cooker.getMinTimeCook();
                    System.out.println("Cozinheiro " + cooker.getName() + " vai pegar num pedido de peixe que vai demorar " + time + " instantes a acabar");
                    numberOfFishDishesOfTheDay++;
                    fishDishOrder.remove(0);
                    cooker.setAvailable(false);
                    cooker.setCurrentActionTimeLeft(time);
                }
            }
        }
        l = 0;
        for (Cooker cooker : fishCookers) {
            if(cooker.isAvailable()){
                fishCookerTimeAvailable[l]++;
            }
            l++;
        }

        System.out.println("Pedidos de carne em espera: " + meatDishOrder.size());
        System.out.println("Pedidos de peixe em espera: " + fishDishOrder.size());
        System.out.println("Pedidos de carne prontos: " + readyMeatDishOrder.size());
        System.out.println("Pedidos de peixe prontos: " + readyFishDishOrder.size());

        //Verificar se existem pedidos prontos para entregar para mesas (deve se entregar a todas as pessoas da mesa ao mesmo tempo)
        for (Table table : tables) {

            if (!table.getCustomersUsingTable().isEmpty() && !table.isServed && !table.isTableBeingUsedAsSecondTable && table.hasOrdered()) {
                int numMeatRequests = getNumMeatRequestsFromTable(table);
                int numFishRequests = getNumFishRequestsFromTable(table);

                if (numMeatRequests <= readyMeatDishOrder.size() && numFishRequests <= readyFishDishOrder.size()) {
                    for (int i = 0; i < numMeatRequests; i++) {
                        readyMeatDishOrder.remove(0);
                    }

                    for (int i = 0; i < numFishRequests; i++) {
                        readyFishDishOrder.remove(0);
                    }

                    for (Customer customer : table.getCustomersUsingTable()) {
                        int eatingTime = random.nextInt(customer.getMaxTimeEat() - customer.getMinTimeEat()) + customer.getMinTimeEat();
                        customer.setCurrentActionTimeLeft(eatingTime);
                        System.out.println(customer.getName() + " acabou de receber o seu pedido e vai demorar " + eatingTime + " instantes a acaba-lo");
                    }

                    table.setServed(true);
                }
            } else if (!table.getCustomersUsingTable().isEmpty() && table.isServed && !table.isAlreadyBeingCleaned()) {
                int customersAlreadyEat = 0;
                //System.out.println("-----CLIENTES A COMER NA MESA " + table.getTableNum() + "-----");
                for (Customer customer : table.getCustomersUsingTable()) {
                    customer.decreaseCurrentActionTimeLeft();
                    //System.out.println("Faltam " + customer.getCurrentActionTimeLeft() + " instantes para o "+customer.getName()+" acabar a sua refeicao");
                    if (customer.getCurrentActionTimeLeft() <= 0) {
                        customersAlreadyEat++;
                    }
                }

                if (customersAlreadyEat == table.getCustomersUsingTable().size() && !table.isAlreadyBeingCleaned()) {
                    //System.out.println("Todas os clientes da mesa " + table.getTableNum() + " ja comeram");
                    table.setNeedsToBePreparedForNextCustomers(true);
                }
            }
        }

        //verificar se existem mesas que precisam de ser limpas
        for (Waitress waitress : waitresses) {
            if (waitress.isAvailable()) {
                for (Table table : tables) {
                    if (waitress.isAvailable) {
                        if (table.needsToBePreparedForNextCustomers()) {
                            //System.out.println(waitress.getName() + " vai comecar a limpar a mesa " + table.getTableNum());
                            prepareTableToNextCustomers(waitress, table);
                        }
                    }
                }
            } else {
                //System.out.println(waitress.getName() + " ainda vai demorar " + waitress.getCurrentActionTimeLeft() + " instantes a acabar de limpar a mesa " + waitress.getNumTableWorkingOn());
                waitress.decreaseCurrentActionTimeLeft();
                if (waitress.getCurrentActionTimeLeft() == 0) {
                    Table table = findTableWorkingOnPerWaitress(waitress);
                    findIfThereIsJoinedTableAndPrepareIt(table);
                    waitress.setAvailable(true);
                }
            }
        }

        printTablesInformation();

        //Empregado de pagamento a trabalhar
        int k = 0;
        for (PaymentEmployee paymentEmployee : paymentEmployees) {
            if (paymentEmployee.isAvailable() && !customerPayingQueue.isEmpty()) {
                System.out.println("-----EMPREGADO DE PAGAMENTO-----");
                Customer customer = customerPayingQueue.keySet().iterator().next();
                startPaymentProcess(paymentEmployee, customer.getPaymentMethod(), customerPayingQueue.get(customer));
                customerPayingQueue.remove(customer);
                System.out.println("fila de pagamento tamanho -> " + customerPayingQueue.size());
                System.out.println("O cliente " + customer.getName() + " vai comecar o processo de pagamento");
                numberofClientsServed++;
            } else if (paymentEmployee.getCurrentActionTimeLeft() != 0) {
                paymentEmployee.decreaseCurrentActionTimeLeft();
                if (paymentEmployee.getCurrentActionTimeLeft() == 0) {
                    System.out.println(paymentEmployee.getName() + " acabou o processo de pagamento");
                    paymentEmployee.setAvailable(true);
                } else {
                    System.out.println(paymentEmployee.getName() + " esta a prosseguir com o processo de pagamento e ainda vai demorar " + paymentEmployee.getCurrentActionTimeLeft() + " instantes");
                }
            }
            if (paymentEmployee.isAvailable()) {
                paymentEmployeeTimeAvailable[k]++;
            }else{
                paymentEmployeeTimeUnavailable[k]++;
            }
            k++;
        }
    }

    public void printTablesInformation() {
        for (Table table : tables) {
            System.out.println();
            System.out.println("-----MESA " + table.getTableNum() + "-----");
            if (table.getCustomersUsingTable().isEmpty()) {
                System.out.println("DISPONIVEL");
            } else if (table.isAlreadyBeingCleaned) {
                for (Waitress waitress : waitresses) {
                    if (waitress.getNumTableWorkingOn() == table.getTableNum()) {
                        System.out.println("Mesa a ser limpa pelo " + waitress.getName() + " e vai demorar " + waitress.getCurrentActionTimeLeft() + " instantes a acabar");
                    }
                }
            } else if (table.isServed()) {
                System.out.println("Mesa foi servida");
                System.out.println("Clientes na mesa:");
                int numCustomerAlreadyEat = 0;
                for (Customer customer : table.getCustomersUsingTable()) {
                    if (customer.getCurrentActionTimeLeft() == 0) {
                        System.out.println(customer.getName() + " ja comeu");
                        numCustomerAlreadyEat++;
                    } else {
                        System.out.println(customer.getName() + " vai demorar " + customer.getCurrentActionTimeLeft() + " instantes a acabar de comer");
                    }
                }
                if (numCustomerAlreadyEat == table.getCustomersUsingTable().size()) {
                    System.out.println("Todos os clientes desta mesa ja comeram");
                }
            } else if (table.isTableBeingUsedAsSecondTable()) {
                for (Table table2 : tables) {
                    if (table != table2 && table.isTableBeingUsedAsSecondTable() && table2.getCustomersUsingTable() == table.getCustomersUsingTable()) {
                        System.out.println("Esta mesa foi juntada a mesa " + table2.getTableNum());
                    }
                }
            } else {
                System.out.println(!table.hasOrdered() ? "Mesa ainda nao fez pedido" : "Mesa ainda nao foi servida");
                System.out.println("Clientes na mesa:");
                for (Customer customer : table.getCustomersUsingTable()) {
                    System.out.println(customer.getName() + " e quer comer " + customer.getFoodType());
                }
            }
        }
    }

    public void getTables() {
        tables = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TABLES; i++) {
            List<Customer> customersUsingTable = new ArrayList<>();
            Table table = new Table(i + 1, true, customersUsingTable, false, false, false, false, 0, 0, 0, false, false);
            tables.add(table);
        }
    }

    public void getWaitresses() {
        waitresses = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WAITRESSES; i++) {
            int number = random.nextInt(3 - 2) + 2;
            Waitress waitress = new Waitress("Empregado de Mesa " + getRandomName(), number, number + 3, 0, 0, true);
            waitresses.add(waitress);
        }
    }

    public void getCookers() {
        meatCookers = new ArrayList<>();
        fishCookers = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_MEAT_COOKERS; i++) {
            int number = random.nextInt(6 - 3) + 3;
            Cooker cooker = new Cooker("Cozinheiro " + getRandomName(), "Meat", number, number + 3, 0, true);
            meatCookers.add(cooker);
        }

        for (int i = 0; i < NUMBER_OF_FISH_COOKERS; i++) {
            int number = random.nextInt(6 - 3) + 3;
            Cooker cooker = new Cooker("Cozinheiro " + getRandomName(), "Fish", number, number + 3, 0, true);
            fishCookers.add(cooker);
        }
    }

    public void getPaymentEmployees() {
        paymentEmployees = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PAYMENT_EMPLOYEES; i++) {
            PaymentEmployee paymentEmployee = new PaymentEmployee("Empregado de pagamento " + getRandomName(), 1, 2, 2, 3, 0, true);
            paymentEmployees.add(paymentEmployee);
        }
    }

    public Customer generateCustomer() {
        int number = random.nextInt(5 - 2) + 2;
        String paymentMethod = random.nextInt(10) + 1 < 5 ? "Card" : "Money";
        String foodType = random.nextInt(10) + 1 < 5 ? "Meat" : "Fish";

        return new Customer("Cliente " + this.getRandomName(), paymentMethod, foodType, number, number + 1, 0);
    }

    public String getRandomName() {
        String[] NAMES = {"Jack", "John", "David", "Emily", "Emma", "Sophia", "Olivia", "Ava", "Isabella", "Mia", "Ethan", "Noah", "Liam", "Mason", "Lucas", "Oliver", "Elijah", "James", "Benjamin", "Alexander", "Alfredo", "Gervasio", "Gertrudes", "Roberto", "Jeremias", "Fernando", "Nelson"};
        int index = random.nextInt(NAMES.length);
        return NAMES[index];
    }

    public int getNumOfAvailableTablesAtTheMoment() {
        int availableTablesAtTheMoment = 0;

        for (Table table : tables) {
            if (table.isAvailable()) {
                availableTablesAtTheMoment++;
            }
        }

        return availableTablesAtTheMoment;
    }

    public Table giveTablesToClient(int numCustomers) {

        int numTables = numCustomers > 4 ? 2 : 1;

        List<Customer> customersToTable = removeCustomersFromQueue(numCustomers);

        Table tableToReturn = null;

        if (numTables == 1) {
            int aux = 0;
            for (Table table : tables) {
                if (table.isAvailable()) {
                    table.setAvailable(false);
                    table.setCustomersUsingTable(customersToTable);
                    tableToReturn = table;
                    aux++;
                }

                if (aux == numTables) {
                    break;
                }
            }
        } else {
            int aux = 0;
            for (Table table : tables) {
                if (table.isAvailable()) {
                    table.setAvailable(false);
                    table.setCustomersUsingTable(customersToTable);
                    aux++;
                    if (aux == 1) {
                        tableToReturn = table;
                        tableToReturn.setIsTableBeingUsedAsSecondTable(false);
                    }
                }

                if (aux == numTables) {
                    table.setIsTableBeingUsedAsSecondTable(true);
                    break;
                }
            }
        }

        return tableToReturn;
    }

    public List<Customer> removeCustomersFromQueue(int numCustomers) {
        List<Customer> list = new ArrayList<>();

        for (Customer customer : customerQueue) {
            list.add(customer);

            numCustomers--;

            if (numCustomers == 0) {
                break;
            }
        }

        for (Customer customer : list) {
            customerQueue.remove(customer);
        }

        return list;
    }

    public void retrieveOrdersFromTable(Waitress waitress, Table table) {
        int meatAux = 0;
        int fishAux = 0;

        for (Customer customer : table.getCustomersUsingTable()) {
            Dish dish = new Dish(customer.getFoodType(), 0, 0, 0);

            if (dish.getFoodType().equalsIgnoreCase("Meat")) {
                meatDishOrder.add(dish);
                meatAux++;

            } else {
                fishDishOrder.add(dish);
                fishAux++;
            }
        }
        System.out.println("Empregado " + waitress.getName() + " recolheu o pedido da mesa " + table.getTableNum() + ". Foram pedidos " + meatAux + " pratos de carne e " + fishAux + " pratos de peixe.");
    }

    public void startPaymentProcess(PaymentEmployee paymentEmployee, String paymentMethod, List<String> typeOfDishesToPay) {
        paymentEmployee.setAvailable(false);

        if (paymentMethod.equalsIgnoreCase("Card")) {
            int time = random.nextInt(paymentEmployee.getMaxTimeExecCard() - paymentEmployee.getMinTimeExecCard()) + paymentEmployee.getMinTimeExecCard();
            System.out.println(paymentEmployee.getName() + " vai comecar processo de pagamento de um cliente que vai pagar em Cartao. Vai demorar " + time + " instantes.");
            paymentEmployee.setCurrentActionTimeLeft(time);
        } else {
            int time = random.nextInt(paymentEmployee.getMaxTimeExecMoney() - paymentEmployee.getMinTimeExecMoney()) + paymentEmployee.getMinTimeExecMoney();
            System.out.println(paymentEmployee.getName() + " vai comecar processo de pagamento de um cliente que vai pagar em Dinheiro. Vai demorar " + time + " instantes.");
            paymentEmployee.setCurrentActionTimeLeft(time);
        }
    }

    public int getNumMeatRequestsFromTable(Table table) {
        int numMeatRequestsFromTable = 0;

        for (Customer customer : table.getCustomersUsingTable()) {
            if (customer.getFoodType().equalsIgnoreCase("Meat")) {
                numMeatRequestsFromTable++;
            }
        }

        return numMeatRequestsFromTable;
    }

    public int getNumFishRequestsFromTable(Table table) {
        int numFishRequestsFromTable = 0;

        for (Customer customer : table.getCustomersUsingTable()) {
            if (customer.getFoodType().equalsIgnoreCase("Fish")) {
                numFishRequestsFromTable++;
            }
        }

        return numFishRequestsFromTable;
    }

    public void prepareTableToNextCustomers(Waitress waitress, Table table) {
        int workingTime = random.nextInt(waitress.getMaxTimeExec() - waitress.getMinTimeExec()) + waitress.getMinTimeExec();

        waitress.isAvailable = false;
        waitress.setCurrentActionTimeLeft(workingTime);
        waitress.setNumTableWorkingOn(table.getTableNum());
        table.setNeedsToBePreparedForNextCustomers(false);
        table.setIsAlreadyBeingCleaned(true);
    }

    public Table findTableWorkingOnPerWaitress(Waitress waitress) {
        for (Table table : tables) {
            if (table.getTableNum() == waitress.getNumTableWorkingOn()) {
                return table;
            }
        }

        return null;
    }

    public void findIfThereIsJoinedTableAndPrepareIt(Table table) {
        for (Table tableAux : tables) {
            if (tableAux != table && tableAux.isTableBeingUsedAsSecondTable && tableAux.getCustomersUsingTable().get(0) == table.getCustomersUsingTable().get(0)) {
                tableAux.setAvailable(true);
                tableAux.setIsTableBeingUsedAsSecondTable(false);
                tableAux.setServed(false);
                tableAux.setCustomersUsingTable(new ArrayList<>());
                tableAux.setNeedsToBePreparedForNextCustomers(false);
                tableAux.setIsAlreadyBeingCleaned(false);
            }
        }

        List<String> typesOfDishesEated = new ArrayList<>();

        for (Customer customer : table.getCustomersUsingTable()) {
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

    public void checkIfAllTasksWereCompleted() {
        boolean checkIfItsAllOk = true;
        for (Table table : tables) {
            if (!table.getCustomersUsingTable().isEmpty() || !table.isAvailable()) {
                checkIfItsAllOk = false;
            }
        }

        for (Waitress waitress : waitresses) {
            if (!waitress.isAvailable()) {
                checkIfItsAllOk = false;
            }
        }

        if (!customerQueue.isEmpty()) {
            checkIfItsAllOk = false;
        }

        for (PaymentEmployee paymentEmployee : paymentEmployees) {
            if (!paymentEmployee.isAvailable()) {
                checkIfItsAllOk = false;
            }
        }

        if (checkIfItsAllOk) {
            allTasksCompleted = true;
        }
    }

    public void generateTimesToOrder(Table table) {
        int minTimeToOrder = random.nextInt(3 - 2) + 2;
        int maxTimeToOrder = random.nextInt(8 - 4) + 4;

        table.setMinTimeToOrder(minTimeToOrder);
        table.setMaxTimeToOrder(maxTimeToOrder);
    }

    public void updatePercentageOfCustomerArriveBasedOnTime(){
        if (this.hour < 19 || this.hour >= 22)
        {
            this.customer_arrive_percentage = 0.0;
        }

        // Intervalos de tempo para ajustar as probabilidades
        if (this.hour == 19 && this.minute <= 30) // 19:00 - 19:30
        {
            this.customer_arrive_percentage = 0.2;
        }
        else if ((this.hour == 19 && this.minute <= 30) || (this.hour == 20 && this.minute == 0)) // 19:30 - 20:00
        {
            this.customer_arrive_percentage = 0.5;
        }
        else if (this.hour == 20 || (this.hour == 21 && this.minute == 0)) // 20:00 - 21:00
        {
            this.customer_arrive_percentage = 0.8;
        }
        else // 21:00 - 22:00
        {
            this.customer_arrive_percentage = 0.3;
        }
    }

}
