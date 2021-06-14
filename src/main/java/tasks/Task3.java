package tasks;

import java.math.BigInteger;

public class Task3 {
    public static void main(String[] args) {
        System.out.print("Factorial 100!: ");
        // Calculating factorial and translating it into String.
        String mStr = "" + factorial(100);
        System.out.println(mStr);
        // Finding sum of factorial digits.
        char[] chars = mStr.toCharArray();
        int sum = 0;
        for (char aChar : chars) {
            int charValue = Character.getNumericValue(aChar);
            sum += charValue;
        }
        // Output
        System.out.println("Sum: " + sum);
    }

    /*
    Calculating factorial.
     */
    static BigInteger factorial(int n) {
        BigInteger r = BigInteger.ONE;
        for (int i = 2; i <= n; ++i) {
            String iString = String.valueOf(i);
            BigInteger a = new BigInteger(iString);
            r = r.multiply(a);
        }
        return r;
    }
}
