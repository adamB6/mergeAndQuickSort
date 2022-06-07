
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
 * @author Adam Botens CSCI 4270 Assignment 4 Comparison between mergesort and
 * quicksort
 */
public class assignment4 {

    public static int count = 0;

    public static void main(String args[]) throws Exception {

        List<double[]> arrays = new ArrayList<>();
        double[] runTimes = new double[10000];

        // Load file with saved ArrayList if it exists
        // Change to sorted.dat for the sorted arrays
        try {
            FileInputStream fileInputStream = new FileInputStream("unsorted.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            arrays = (List) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        if (arrays.isEmpty()) {
            generateArrays(arrays);
        }
        
        //sort and print counts
        for (int i = 0; i < 10000; i++) {
            quickSort(arrays.get(i), 0, 999);
            System.out.println(i + ": " + count);
            runTimes[i] = count;
            count = 0;
        }

        mergeSort(runTimes);
        System.out.println("Minimum Runtime: " + runTimes[0]);
        System.out.println("Maximum Runtime: " + runTimes[9999]);
        System.out.println("Average Runtime: " + average(runTimes));
        System.out.println("Standard Deviation: " + standardDeviation(runTimes));

        //testing arrays
        /*
        for (int i = 0; i < 10000; i++) {
            System.out.print("before: " + Arrays.toString(arrays.get(i)) + "\n");
        }
        for (int i = 0; i < 10000; i++) {
            System.out.print("after: " + Arrays.toString(arrays.get(i)) + "\n");
        }
         */
        //run sorting algorithm and record times for all 10000
        //change mergeSort(arrays.get(i) to quickSort(arrays.get(i), 0, 999)
        //and vice versa
        /*
        for (int i = 0; i < 10000; i++) {
            double start = System.nanoTime();
            mergeSort(arrays.get(i));
            double end = System.nanoTime();
            runTimes[i] = (end - start);
        }

        mergeSort(runTimes);
        System.out.println("Minimum Runtime: " + runTimes[0]);
        System.out.println("Maximum Runtime: " + runTimes[9999]);
        System.out.println("Average Runtime: " + average(runTimes));
        System.out.println("Standard Deviation: " + calculateSD(runTimes));

         */
        //used to save sorted dat
        /*
        try {
            FileOutputStream fos = new FileOutputStream("sorted.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrays);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
         */
    }

    public static void mergeSort(double[] array) {

        if (array.length < 2) {
            return;
        }

        int mid = array.length / 2;
        double left[] = new double[mid];
        double right[] = new double[array.length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = array[i];

        }
        mergeSort(left);

        for (int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }
        mergeSort(right);

        merge(array, left, right);
    }

    public static void merge(double[] main, double[] left, double[] right) {

        int l = 0, r = 0, i = 0;

        while (l < left.length && r < right.length) {
            if (left[l] < right[r]) {
                main[i++] = left[l++];
            } else {
                main[i++] = right[r++];
            }
            count++;
        }

        while (l < left.length) {
            main[i++] = left[l++];
            count++;
        }

        while (r < right.length) {
            main[i++] = right[r++];
            count++;
        }
    }

    public static List<double[]> generateArrays(List<double[]> arrays) throws Exception {
        double[] a = new double[1000];

        for (int k = 0; k < 10000; k++) {
            for (int i = 0; i < a.length; i++) {

                a[i] = (int) (Math.random() * 1000);
            }
            arrays.add(k, a.clone());
        }

        try {
            FileOutputStream fos = new FileOutputStream("unsorted.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrays);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return arrays;
    }

    public static void quickSort(double[] array, int left, int right) {

        if (left < right) {

            double pivot = partition(array, left, right);

            quickSort(array, left, (int) (pivot - 1));
            quickSort(array, (int) (pivot + 1), right);
        }
    }

    public static double partition(double[] array, int left, int right) {
        double pivot = array[left];
        int i = left;
        int j = right;

        while (i < j) {
            while (i < j && array[j] >= pivot) {
                j--;
                count++;
            }
            if (i < j) {
                array[i++] = array[j];
            }
            while (i < j && array[i] <= pivot) {
                i++;
                count++;
            }
            if (i < j) {
                array[j--] = array[i];
            }
        }

        array[i] = pivot;
        return i;
    }

    public static void swap(double array[], int l, int r) {
        double temp = array[l];
        array[l] = array[r];
        array[r] = temp;
    }

    public static double standardDeviation(double[] runTimes) {
        double sum = 0.0, sd = 0.0;

        for (int i = 0; i < runTimes.length; i++) {
            sum += runTimes[i];
        }

        double mean = sum / runTimes.length;

        for (int i = 0; i < runTimes.length; i++) {
            sd += Math.pow((runTimes[i] - mean), 2);
        }

        return Math.sqrt(sd / runTimes.length);

    }

    public static double average(double[] runTimes) {
        double sum = 0;
        for (int i = 0; i < 10000; i++) {
            sum += runTimes[i];
        }
        return sum / 10000;
    }

}
