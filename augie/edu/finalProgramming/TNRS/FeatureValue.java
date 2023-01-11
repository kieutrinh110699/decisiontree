package augie.edu.finalProgramming.TNRS;
import java.util.Objects;
import java.util.stream.Stream;

public class FeatureValue {
    private String name; // Declare a variable to store the name of the feature value
    private int occurences; // Declare a variable to store the number of occurences of the feature value

    public FeatureValue(String name) {
        this.name = name; // Set the name of the feature value to the specified value
    }

    public String getName() {
        return name; // Return the name of the feature value
    }

    public int getOccurences() {
        return occurences; // Return the number of occurences of the feature value
    }

    public void setOccurences(int occurences) {
        this.occurences = occurences; // Set the number of occurences of the feature value to the specified value
    }

    public int hashCode() {
        return name.hashCode(); // Return the hash code of the feature value's name
    }


    public boolean equals(Object F) {
        // Use a Stream to check if the specified object is equal to this object
        return Stream.of(F)
                .filter(f -> f != null && getClass() == f.getClass())// Filter for non-null objects of the same class as this object
                .map(o -> Objects.equals(name, ((FeatureValue) o).name))// Map the objects to their name equality with this object's name
                .findFirst()// Return the first element in the stream (if it exists)
                .orElse(false);// Else return false
    }

    public String toString() {
        return name; // Return the name of the feature value
    }

}

