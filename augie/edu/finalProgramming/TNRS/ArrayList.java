package augie.edu.finalProgramming.TNRS;
public class ArrayList<E> {
    // The initial capacity of the list
    private static final int I = 16;

    // The underlying array to store the elements of the list
    private Object[] data;

    // The number of elements in the list
    private int size;

    public ArrayList() {
        // Initialize the underlying array with the initial capacity
        data = new Object[I];
    }

    // Add an element to the end of the list
    public void add(E element) {
        // Check if the underlying array is full and needs to be resized
        if (size == data.length) {
            // Create a new array with double the size of the old array
            Object[] newData = new Object[data.length * 2];
            // Copy the elements from the old array to the new array
            System.arraycopy(data, 0, newData, 0, data.length);
            // Set the new array as the underlying array
            data = newData;
        }

        // Add the element to the end of the array
        data[size] = element;
        // Increment the size of the list
        size++;
    }

    // Get the element at the specified index
    public E get(int index) {
        // Check if the index is out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // Cast the element at the specified index to the correct type and return it
        return (E) data[index];
    }

    // Get the number of elements in the list
    public int size() {
        return size;
    }

    // Convert the list to an array of strings
    public String[][] toArray(String[][] array) {
        // Check if the specified array has the correct dimensions
        if (array.length < size || array[0].length < 1) {
            throw new IllegalArgumentException();
        }

        // Copy the elements of the list to the array
        System.arraycopy(data, 0, array, 0, size);
        // Return the array
        return array;
    }
}
