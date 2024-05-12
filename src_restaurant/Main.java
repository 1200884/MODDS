package src_restaurant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Create a FileOutputStream to write the output to a file
        FileOutputStream fos = new FileOutputStream("output.txt");
        
        // Create a PrintStream that writes to the FileOutputStream
        PrintStream ps = new PrintStream(fos);
        
        // Save the original standard output stream
        PrintStream originalOut = System.out;
        
        // Set the standard output to the PrintStream
        System.setOut(ps);
        
        // Your existing code goes here

        // Instantiate your class
        RestaurantSimulation restaurantSimulation = new RestaurantSimulation();
        
        // Run your simulation
        restaurantSimulation.simulate();

        // Restore the standard output
        System.setOut(originalOut);
        
        // Close the FileOutputStream
        fos.close();
    }
}
