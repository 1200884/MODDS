package src_restaurant;

public class Dish {
    private String foodType;
    private int minTimeEat;
    private int maxTimeEat;
    private int price;

    public Dish(String foodType, int minTimeEat, int maxTimeEat, int price) {
        this.foodType = foodType;
        this.minTimeEat = minTimeEat;
        this.maxTimeEat = maxTimeEat;
        this.price = price;
    }

    // Getters and setters
    public String getFoodType() {
        return this.foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getMinTimeEat() {
        return this.minTimeEat;
    }

    public void setMinTimeEat(int minTimeEat) {
        this.minTimeEat = minTimeEat;
    }

    public int getMaxTimeEat() {
        return this.maxTimeEat;
    }

    public void setMaxTimeEat(int maxTimeEat) {
        this.maxTimeEat = maxTimeEat;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

