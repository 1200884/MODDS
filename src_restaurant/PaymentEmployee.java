package src_restaurant;

public class PaymentEmployee {
    private String name;
    private int minTimeExecCard;
    private int maxTimeExecCard;
    private int minTimeExecMoney;
    private int maxTimeExecMoney;
    private int currentActionTimeLeft;
    private boolean isAvailable;

    public PaymentEmployee(String name, int minTimeExecCard, int maxTimeExecCard,
                           int minTimeExecMoney, int maxTimeExecMoney, int currentActionTimeLeft, boolean isAvailable) {
        this.name = name;
        this.minTimeExecCard = minTimeExecCard;
        this.maxTimeExecCard = maxTimeExecCard;
        this.minTimeExecMoney = minTimeExecMoney;
        this.maxTimeExecMoney = maxTimeExecMoney;
        this.currentActionTimeLeft = currentActionTimeLeft;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinTimeExecCard() {
        return minTimeExecCard;
    }

    public void setMinTimeExecCard(int minTimeExecCard) {
        this.minTimeExecCard = minTimeExecCard;
    }

    public int getMaxTimeExecCard() {
        return maxTimeExecCard;
    }

    public void setMaxTimeExecCard(int maxTimeExecCard) {
        this.maxTimeExecCard = maxTimeExecCard;
    }

    public int getMinTimeExecMoney() {
        return minTimeExecMoney;
    }

    public void setMinTimeExecMoney(int minTimeExecMoney) {
        this.minTimeExecMoney = minTimeExecMoney;
    }

    public int getMaxTimeExecMoney() {
        return maxTimeExecMoney;
    }

    public void setMaxTimeExecMoney(int maxTimeExecMoney) {
        this.maxTimeExecMoney = maxTimeExecMoney;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
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
        }else if(this.currentActionTimeLeft == 0){
            setAvailable(true);
        }
    }
}