import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {

        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        Scanner sc = new Scanner(System.in);


        Calculator crypt = new Calculator();
        
        Elgamal PKC = new Elgamal();

        System.out.println("Please choose type of plaintext");
        System.out.println("1. String message ");
        System.out.println("2. File");
        System.out.print("Enter number 1 or 2: ");
        String in = sc.next();
        sc.nextLine();
        String message;
        String filename;
        int n ;
        switch (in) {
            case "1":
                System.out.print("Enter message: ");
                message = sc.nextLine();
                System.out.print("Enter n: ");
                n = sc.nextInt();
                PKC.setP(crypt.findPrime(message, n));
                break;
            case "2":
                System.out.print("Enter file name: ");
                filename = sc.next();
                File f = new File(filename);
                System.out.print("Enter n: ");
                n = sc.nextInt();
                PKC.setP(crypt.findPrime(filename, n));
            default:
                break;
        }

        System.out.println(PKC.getP());
    }
}
