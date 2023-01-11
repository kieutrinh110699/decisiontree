package augie.edu.finalProgramming.TNRS;
import java.util.*;
import java.util.stream.IntStream;

public class Feature {
    private String name = null; // Initialize the name variable to null
    private HashSet<FeatureValue> featureValues = new HashSet<FeatureValue>(); // Initialize a HashSet to store FeatureValue objects

    Feature(String[][] data, int column) {
        this.name = data[0][column]; // Set the name of the feature to the value at the specified column in the first row of the data

        // Loop through the remaining rows in the data, starting at the second row (index 1)
        for (int row = 1; row < data.length; row++) {
            featureValues.add(new FeatureValue(data[row][column])); // Add a new FeatureValue object to the HashSet using the value at the specified column in the current row
        }

        // Loop through each FeatureValue object in the HashSet
        for (FeatureValue featureValue : featureValues) {
            // Use an IntStream to count the number of rows in which the current FeatureValue's name is equal to the value at the specified column
            int counter = (int) IntStream.range(1, data.length)
                    .filter(row -> featureValue.getName().equals(data[row][column]))
                    .count();
            featureValue.setOccurences(counter); // Set the occurences of the current FeatureValue to the count obtained from the IntStream
        }
    }



    public String getName() {
        return name; // Return the name of the feature
    }

    public HashSet<FeatureValue> getFeatureValues() {
        return featureValues; // Return the HashSet containing the FeatureValue objects for this feature
    }

    public String toString() {
        return name; // Return the name of the feature
    }

}
