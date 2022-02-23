
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author asbot
 */
public class assignment4 {

    public static void main(String args[]) throws Exception {

        List<int[]> arrays = new ArrayList<>();

        //loads file with saved ArrayList if it exists
        try {
            FileInputStream fileInputStream = new FileInputStream("data.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            arrays = (List) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        //will create a new file if one does not exist
        generateArrays(arrays);

        /*
        for (int i = 0; i < 10000; i++) {

            System.out.print("before: " + Arrays.toString(arrays.get(i)) + "\n");
        }
        */
        for (int i = 0; i < 10000; i++) {
            mergeSort(arrays.get(i));
            System.out.println("after: " + i + " " + Arrays.toString(arrays.get(i)));
        }

    }

    static void mergeSort(int[] array) {
        if (array.length < 2) {
            return;
        }
        int mid = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, mid);
        mergeSort(left);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(right);

        // Merge the two arrays
        merge(array, left, right);
    }

    static void merge(int[] nums, int[] left, int[] right) {
        int pL = 0, pR = 0, index = 0;
        while (pL < left.length && pR < right.length) {
            if (left[pL] < right[pR]) {
                nums[index++] = left[pL++];
            } else {
                nums[index++] = right[pR++];
            }
        }
        while (pL < left.length) {
            nums[index++] = left[pL++];
        }
        while (pR < right.length) {
            nums[index++] = right[pR++];
        }
    }

    static List<int[]> generateArrays(List<int[]> arrays) throws Exception {

        if (arrays.isEmpty()) {

            int[] a = new int[1000];

            for (int k = 0; k < 10000; k++) {
                for (int i = 0; i < a.length; i++) {

                    a[i] = (int) (Math.random() * 100);
                }
                arrays.add(k, a.clone());
            }

            try {
                FileOutputStream fos = new FileOutputStream("data.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(arrays);
                oos.close();
                fos.close();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }

        return arrays;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* This function takes last element as pivot, places
   the pivot element at its correct position in sorted
   array, and places all smaller (smaller than pivot)
   to left of pivot and all greater elements to right
   of pivot */
    static int partition(int[] arr, int low, int high) {

        // pivot
        int pivot = arr[high];

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr[j] < pivot) {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
          arr[] --> Array to be sorted,
          low --> Starting index,
          high --> Ending index
     */
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
