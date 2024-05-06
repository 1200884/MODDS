package src_restaurant;

public class Waitress {
    private String name;
    private int minTimeExec;
    private int maxTimeExec;
    private int currentActionTimeLeft;
    private int numTableWorkingOn;
    private boolean isAvailable;

    public Waitress(String name, int minTimeExec, int maxTimeExec, int currentActionTimeLeft, int numTableWorkingOn, boolean isAvailable) {
        this.name = name;
        this.minTimeExec = minTimeExec;
        this.maxTimeExec = maxTimeExec;
        this.currentActionTimeLeft = currentActionTimeLeft;
        this.numTableWorkingOn = numTableWorkingOn;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinTimeExec() {
        return minTimeExec;
    }

    public void setMinTimeExec(int minTimeExec) {
        this.minTimeExec = minTimeExec;
    }

    public int getMaxTimeExec() {
        return maxTimeExec;
    }

    public void setMaxTimeExec(int maxTimeExec) {
        this.maxTimeExec = maxTimeExec;
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

    public int getNumTableWorkingOn(){
        return this.numTableWorkingOn;
    }

    public void setNumTableWorkingOn(int numTableWorkingOn){
        this.numTableWorkingOn = numTableWorkingOn;
    }

    public void decreaseCurrentActionTimeLeft(){
        if(this.currentActionTimeLeft > 0){
            this.currentActionTimeLeft--;
        }
    }
}