package src_restaurant;

public class Dish {
    private String foodType;
    private int minTimeEat;
    private int maxTimeEat;

    public Dish(String foodType, int minTimeEat, int maxTimeEat) {
        this.foodType = foodType;
        this.minTimeEat = minTimeEat;
        this.maxTimeEat = maxTimeEat;
    }

    // Getters and setters
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

    public void setMaxTimeEat(int maxTimeEat) {
        this.maxTimeEat = maxTimeEat;
    }
}

