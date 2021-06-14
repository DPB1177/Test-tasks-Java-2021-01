package tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Task2 {

    // Reading data.
    static int numberTest;
    static int amountOfCities;
    static int[][] couplingMatrix;
    static ArrayList<String> listCities = new ArrayList<String>();
    static int amountOfNodeLinks;
    static int numberOfPaths;
    static String[] coupleOfCities;
    static ArrayList<String[]> coupleList = new ArrayList<String[]>();
    static ArrayList<Integer[]> coupleIntegerList = new ArrayList<Integer[]>();

    public static void main(String[] args) {
        // Reading data from file.
        inputParser("src/main/resources/input_file.txt");
        // Visual Check of reading data.
        dataEntryValidation();
        // Calculation.
        AlgorithmOfDijkstra();
    }

    /*
    Algorithm
     */
    private static void AlgorithmOfDijkstra() {
        System.out.println("Output: ");
        for (Integer[] couple : coupleIntegerList) {
            // minimum distance (travel cost)
            int[] minimumDistance = new int[amountOfCities];
            // visited nodes
            boolean[] visitedNode = new boolean[amountOfCities];
            // auxiliary variables
            int temp, minIndex, min;
            // point start for finding resolve
            int beginIndex = couple[0];
            // coupling matrix
            int[][] a = new int[amountOfCities][amountOfCities];
            // Initializing coupling matrix for Algorithm.
            for (int i = 0; i < couplingMatrix.length; i++) {
                System.arraycopy(couplingMatrix[i], 0, a[i], 0, couplingMatrix[i].length);
            }
            // Initializing nodes and distances (travel costs).
            for (int i = 0; i < amountOfCities; i++) {
                minimumDistance[i] = Integer.MAX_VALUE;
                visitedNode[i] = false;
            }
            minimumDistance[beginIndex] = 0;
            // Algorithm
            do {
                minIndex = Integer.MAX_VALUE;
                min = Integer.MAX_VALUE;
                for (int i = 0; i < amountOfCities; i++) { // If node has not yet been bypassed and weight is less than min.
                    // Reassigning values.
                    if ((!visitedNode[i]) && (minimumDistance[i] < min)) {
                        min = minimumDistance[i];
                        minIndex = i;
                    }
                }
                // Append weight to the found minimum current weight nodes and compare with the current minimum weight nodes.
                if (minIndex != Integer.MAX_VALUE) {
                    for (int i = 0; i < amountOfCities; i++) {
                        if (a[minIndex][i] > 0) {
                            temp = min + a[minIndex][i];
                            if (temp < minimumDistance[i]) {
                                minimumDistance[i] = temp;
                            }
                        }
                    }
                    visitedNode[minIndex] = true;
                }
            } while (minIndex < Integer.MAX_VALUE);
            // Output.
            System.out.println(minimumDistance[couple[1]]);
        }
    }

    /*
    Input data.
     */
    private static void inputParser(String nameFile) {
        try {
            File file = new File(nameFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            // Read: number of test.
            String line = reader.readLine();
            numberTest(line);
            // Read: amount of cities.
            line = reader.readLine();
            int n = amountOfCities(line);
            // Read: information about the city.
            couplingMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                // Read: name of city.
                line = reader.readLine();
                System.out.println(line);
                city(line);
                // Read: amount of links.
                line = reader.readLine();
                int m = amountOfNodeLinks(line);
                // Read: data of cites (nodes).
                for (int j = 0; j < m; j++) {
                    line = reader.readLine();
                    int[] indexes = matrixFilling(line);
                    couplingMatrix[i][indexes[0] - 1] = indexes[1];
                    couplingMatrix[indexes[0] - 1][i] = indexes[1];
                }
            }
            // Read: How many searches.
            line = reader.readLine();
            n = amountOfPaths(line);
            // Read: Between which cites (nodes) search.
            for (int i = 0; i < n; i++) {
                line = reader.readLine();
                coupleList.add(coupleOfCities(line));
            }
            pairConversion();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Reading data of input: Number Test.
    */
    private static int numberTest(String data) {
        try {
            int value = Integer.parseInt(data);
            numberTest = value;
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Error input: 'Number Test.'");
            System.exit(1);
        }
        return -1;
    }

    /*
    Reading data of input: Amount Of City(nodes).
    */
    private static int amountOfCities(String data) {
        try {
            int value = Integer.parseInt(data);
            amountOfCities = value;
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Error input: 'Amount Of City(nodes).'");
            System.exit(1);
        }
        return -1;
    }

    /*
    Reading data of input: Name Of City(nodes).
    */
    private static String city(String data) {
        if (data == null || data.equals("")) {
            System.out.println("Error input: 'Name Of City(nodes). Data equals NULL or empty.'");
            System.exit(1);
        }
        Scanner scanner = new Scanner(data);
        String validationResult = scanner.findInLine("[A-Za-z]+");
        if (validationResult != null) {
            listCities.add(data.replaceAll("\\s+", ""));
        } else {
            System.out.println("Error input: 'Name Of City(nodes). String contains no letters.'");
            System.exit(1);
        }
        return data;
    }

    /*
    Reading data of input: Amount of links.
    */
    private static int amountOfNodeLinks(String data) {
        try {
            int value = Integer.parseInt(data);
            amountOfNodeLinks = value;
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Error input: 'Amount of links.'");
            System.exit(1);
        }
        return -1;
    }

    /*
    Reading data of input: Values for paths.
    */
    private static int[] matrixFilling(String data) {
        int[] indexes = new int[2];
        String[] arrayDates = data.split(" ");
        if (arrayDates.length != 2) {
            System.out.println("Error input: 'matrixFilling. Array of string did not equal 2.'");
            System.exit(1);
        }
        for (int i = 0; i < indexes.length; i++) {
            try {
                indexes[i] = Integer.parseInt(arrayDates[i]);
            } catch (NumberFormatException e) {
                System.out.println("Error input: 'matrixFilling.'");
                System.exit(1);
            }
        }
        return indexes;
    }

    /*
    Reading data of input: Number Of Paths.
    */
    private static int amountOfPaths(String data) {
        try {
            int value = Integer.parseInt(data);
            numberOfPaths = value;
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Error input: 'Amount of paths.'");
            System.exit(1);
        }
        return -1;
    }

    /*
    Reading data of input: Couple Of Cities(nodes).
    */
    private static String[] coupleOfCities(String data) {
        if (data == null || data.equals("")) {
            System.out.println("Error input: 'Couple of cities. Data equals NULL or empty.'");
            System.exit(1);
        }
        String[] arrayDates = data.split(" ");
        if (arrayDates.length != 2) {
            System.out.println("Error input: 'Couple of cities. Array of string did not equal 2.'");
            System.exit(1);
        }
        coupleOfCities = arrayDates;
        return arrayDates;
    }

    /*
    Translation of names of cities(nodes) in the matrix indexes of the (cities)nodes.
    */
    private static void pairConversion() {
        System.out.println("coupleList.size():" + coupleList.size());
        for (String[] couple : coupleList) {
            Integer[] coupleInteger = new Integer[couple.length];
            for (int j = 0; j < couple.length; j++) {
                for (int i = 0; i < listCities.size(); i++) {
                    if (couple[j].equals(listCities.get(i))) {
                        coupleInteger[j] = i;
                    }
                }
            }
            for (Integer integer : coupleInteger) {
                if (integer == null) {
                    System.out.println("Error translation: 'Array of coupleInteger contains null'");
                    System.exit(1);
                }
            }
            coupleIntegerList.add(coupleInteger);
        }
    }

    /*
    Visual check of data entry.
     */
    private static void dataEntryValidation() {
        System.out.println("Number Test: " + numberTest);
        System.out.println("Amount of Cities: " + amountOfCities);
        System.out.println("Coupling Matrix (~Adjacency matrix): ");
        for (int i = 0; i < amountOfCities; i++) {
            for (int j = 0; j < amountOfCities; j++)
                if (couplingMatrix[i][j] < 10) System.out.print("   " + couplingMatrix[i][j]);
                else if (couplingMatrix[i][j] >= 10) System.out.print("  " + couplingMatrix[i][j]);
            System.out.println();
        }
        System.out.println("Amount Of Node Links: " + amountOfNodeLinks);
        System.out.println("Amount Of Paths: " + numberOfPaths);
        System.out.println("List Of Cities: ");
        for (String city :
                listCities) {
            System.out.println(city);
        }
        System.out.println("List Of Couple Cities: ");
        for (String[] couple :
                coupleList) {
            for (String s : couple) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
        System.out.println("List Of Couple: ");
        for (Integer[] couple :
                coupleIntegerList) {
            for (Integer i : couple) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
