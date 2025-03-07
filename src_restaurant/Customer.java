package src_restaurant;

public class Customer {
    private String paymentMethod;
    private String foodType;
    private int minTimeEat;
    private int maxTimeEat;
    private int currentActionTimeLeft;
    private String name;

    public Customer(String name,String paymentMethod, String foodType, int minTimeEat, int maxTimeEat, int currentActionTimeLeft) {
        this.name=name;
        this.paymentMethod = paymentMethod;
        this.foodType = foodType;
        this.minTimeEat = minTimeEat;
        this.maxTimeEat = maxTimeEat;
        this.currentActionTimeLeft = currentActionTimeLeft;
    }

    // Getters and setters
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getMinTimeEat() {
        return minTimeEat;
    }

    public void setMinTimeEat(int minTimeEat) {
        this.minTimeEat = minTimeEat;
    }

    public int getMaxTimeEat() {
        return maxTimeEat;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setMaxTimeEat(int maxTimeEat) {
        this.maxTimeEat = maxTimeEat;
    }

    public int getCurrentActionTimeLeft(){
        return this.currentActionTimeLeft;
    }

    public void setCurrentActionTimeLeft(int currentActionTimeLeft){
        this.currentActionTimeLeft = currentActionTimeLeft;
    }

    public void decreaseCurrentActionTimeLeft(){
        if(this.currentActionTimeLeft > 0){
            this.currentActionTimeLeft--;
        }else{
            this.currentActionTimeLeft = 0;
        }
    }
}

