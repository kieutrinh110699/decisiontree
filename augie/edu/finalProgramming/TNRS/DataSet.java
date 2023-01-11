package augie.edu.finalProgramming.TNRS;
import java.util.*;
import java.util.stream.IntStream;
import static java.lang.Math.log;

// Declares a class named DataSet
public class DataSet {
    // Declares a variable named name of type String
    private String name;
    // Declares a 2D array named data of type String
    private String[][] data = null;
    // Declares a variable named entropy of type double, initializes to 0
    private double entropy = 0;
    // Declares a HashMap named infoGains of type <Feature, Double>
    private HashMap<Feature, Double> infoGains = new HashMap<Feature, Double>();
    // Declares a variable named splitOnFeature of type Feature
    private Feature splitOnFeature = null;

    // Declares a constructor for the DataSet class
    public DataSet(String name, String[][] data) {// Takes in two arguments, a string named name and a 2D array of strings named data
        // Initializes the instance variables with the arguments passed in
        this.name = name;
        this.data = data;
        // Calls the calcuateEntropy, calculateInfoGains, and findSplitOnFeature methods
        calcuateEntropy().calculateInfoGains().findSplitOnFeature();
    }


    double negativePlog2(double p) {
        // Initialize result to 0
        double result = 0;

        // If p is not equal to 0
        if (p != 0) {
            // Calculate (-1) * p * log(p) / log(2) and assign it to result
            result = (-1) * p * log(p) / log(2);
        }

        // Return result
        return result;
    }

    DataSet calcuateEntropy() {//
        // For each feature value in the list of feature values for the feature
        // at the last index in the data
        new Feature(data, data[0].length - 1)
                .getFeatureValues()
                .stream()
                .forEach(featureValue ->
                        // Add (-1) * p * log(p) / log(2) to entropy, where
                        // p is the ratio of the number of occurrences of the
                        // feature value to the number of rows in the data minus 1
                        entropy += negativePlog2(
                                (double) featureValue.getOccurences() / (data.length - 1)
                        )
                );

        // Return this instance of DataSet
        return this;
    }

    DataSet calculateInfoGains() {
        // loop through all columns in the dataset, excluding the last one (which is the target)
        IntStream.range(0, data[0].length - 1)
                .forEach(column -> {
                    // create a new Feature object for the current column
                    Feature feature = new Feature(data, column);

                    // initialize an ArrayList to store the DataSets created for each feature value
                    ArrayList<DataSet> dataSets = new ArrayList<>();

                    // loop through each feature value and create a new DataSet
                    feature.getFeatureValues()
                            .stream()
                            .forEach(featureValue ->
                                    dataSets.add(
                                            createDataSet(feature, featureValue, data)
                                    )
                            );

                    // calculate the summation for the current feature
                    double summation = 0;
                    for (int i = 0; i < dataSets.size(); i++) {
                        summation += (
                                // calculate the weight for the current DataSet
                                (double) (dataSets.get(i).getData().length - 1) /
                                        (data.length - 1)
                        ) * dataSets.get(i).getEntropy();
                    }

                    // store the information gain for the current feature in the infoGains map
                    infoGains.put(feature, entropy - summation);
                });

        return this;
    }

    public DataSet findSplitOnFeature() {
        // Initialize an iterator for the key set of the infoGains map
        Iterator<Feature> iterator = infoGains.keySet().iterator();

        // Iterate over all the keys in the infoGains map
        while (iterator.hasNext()) {
            // Get the current key
            Feature feature = iterator.next();

            // If splitOnFeature is not set or the infoGain of the current key is greater than the infoGain of splitOnFeature,
            // set the splitOnFeature to the current key
            if (splitOnFeature == null || infoGains.get(splitOnFeature) < infoGains.get(feature))
                splitOnFeature = feature;
        }

        // Return this DataSet instance
        return this;
    }


    public DataSet createDataSet(Feature feature, FeatureValue featureValue, String[][] data) {
        // Get the column number of the given feature
        int column = getColNumb(feature.getName());

        // Create a new 2D array with one extra row for the column labels
        String[][] returnData = new String[featureValue.getOccurences() + 1][data[0].length];

        // Copy the column labels from the original data to the new array
        returnData[0] = data[0];

        // Keep track of the number of rows that match the given feature value
        int counter = 1;

        // Start at the second row (first row is column labels)
        int row = 1;

        // Loop through all rows in the original data
        while (row < data.length) {
            // If the current row matches the given feature value,
            // add it to the new array
            if (data[row][column].equals(featureValue.getName())) {
                returnData[counter++] = data[row];
            }
            row++;
        }

        // Create and return a new DataSet with the filtered data
        // and a modified name that includes the given feature value
        return new DataSet(
                feature.getName() + ": " + featureValue.getName(),
                deleteColumn(returnData, column)
        );
    }


    String[][] deleteColumn(String[][] data, int toDeleteColumn) {
        // Get the number of rows and columns in the original data
        int rows = data.length;
        int cols = data[0].length - 1;

        // Create a new 2D array with one fewer column than the original data
        String[][] returnData = new String[rows][cols];

        // Loop through all rows in the original data
        for (int row = 0; row < rows; row++) {
            // Keep track of the current column in the new array
            int columnCounter = 0;

            // Loop through all columns in the current row of the original data
            for (int column = 0; column < data[row].length; column++) {
                // If the current column is not the one we want to delete,
                // copy it to the new array
                if (column != toDeleteColumn) {
                    returnData[row][columnCounter++] = data[row][column];
                }
            }
        }

        // Return the new array with the specified column deleted
        return returnData;
    }


    int getColNumb(String colName) {
        // Set the default return value to -1 (not found)
        int returnValue = -1;

        // Loop through all columns in the data
        for (int column = 0; column < data[0].length - 1; column++) {
            // If the current column has the given name,
            // set the return value to the column number and break out of the loop
            if (data[0][column].equals(colName)) {
                returnValue = column;
                break;
            }
        }

        // Return the column number
        return returnValue;
    }

    // Get the data in the DataSet
    public String[][] getData() {
        return data;
    }

    // Get the entropy of the DataSet
    public double getEntropy() {
        return entropy;
    }

    // Get the information gains for each feature in the DataSet
    public HashMap<Feature, Double> getInfoGains() {
        return infoGains;
    }

    // Get the feature on which the DataSet was split
    public Feature getSplitOnFeature() {
        return splitOnFeature;
    }

    // Convert the DataSet to a string representation
    public String toString() {
        return name;
    }
}
