package src_restaurant;

public class Cooker {
    private String name;
    private String foodType;
    private int minTimeCook;
    private int maxTimeCook;
    private int currentActionTimeLeft;
    private boolean isAvailable;

    public Cooker(String name, String foodType, int minTimeCook, int maxTimeCook, int currentActionTimeLeft, boolean isAvailable) {
        this.name = name;
        this.foodType = foodType;
        this.minTimeCook = minTimeCook;
        this.maxTimeCook = maxTimeCook;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getMinTimeCook() {
        return minTimeCook;
    }

    public void setMinTimeCook(int minTimeCook) {
        this.minTimeCook = minTimeCook;
    }

    public int getMaxTimeCook() {
        return maxTimeCook;
    }

    public void setMaxTimeCook(int maxTimeCook) {
        this.maxTimeCook = maxTimeCook;
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