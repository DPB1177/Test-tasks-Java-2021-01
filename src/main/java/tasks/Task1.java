package tasks;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        int N = input();
        if (N > 0) {
            enumeration(N);
        } else {
            printError();
        }
    }

    /*
    Input N.
     */
    private static int input() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter N: ");
        int number = scan.nextInt();
        scan.close();
        System.out.println("N = " + number);
        return number;
    }

    /*
    Iterate through all possible options in the array.
    Where "1" is "(".
    Where "0" is ")".
     */
    private static void enumeration(int N) {
        N *= 2;
        int[] array = new int[N];
        while (true) {
            int i = 0;
            a:
            while (true) {
                while (true) {
                    if (array[i] == 1) {
                        array[i] = 0;
                        i++;
                        break;
                    } else if (array[i] == 0) {
                        array[i] = 1;
                        break a;
                    }
                }
            }
            // For check all the options.
            // printEnumeration(array); // For to display only the result, you can not include
            int[] bufferArray = new int[N];
            System.arraycopy(array, 0, bufferArray, 0, N);
            analyzer(bufferArray);
            boolean exitValue = true;
            for (int k : array) {
                if (k == 0) {
                    exitValue = false;
                    break;
                }
            }
            if (exitValue) break;
        }
    }

    /*
    Selecting the correct options when searching.
     */
    private static void analyzer(int[] array) {
        int[] replacedArray = replacement(array);
        int sum = 0;
        for (int j : replacedArray) {
            sum += j;
            if (sum < 0) {
                break;
            }
        }
        if (sum == 0) {
            print(replacedArray);
        }
    }

    /*
    Replacing the value 0 in the array with -1. For ease of analysis.
    */
    private static int[] replacement(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = -1;
            }
        }
        return array;
    }

    /*
    Output the correct options.
     */
    private static void print(int[] array) {
        for (int j : array) {
            if (j == 1) {
                System.out.print("(");
            } else {
                System.out.print(")");
            }
        }
        System.out.println();
    }

    /*
    Test output of the array for checking the iteration.
     */
    private static void printEnumeration(int[] massive) {
        for (int value : massive) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /*
    Output error.
     */
    private static void printError() {
        System.out.println("You entered the wrong number.");
    }
}




