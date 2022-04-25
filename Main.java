import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {

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
        System.out.println();
        sc.nextLine();
        String message;
        String filename;
        int n ;
        Operation cryptPrim = new Operation();
        switch (in) {
            case "1":
                System.out.print("Enter message : ");
                message = sc.nextLine();
                System.out.println();
                System.out.print("Enter n (less than equal 31 bits) : ");
                n = sc.nextInt(); 
                System.out.println();
                PKC.setP(crypt.findPrime(message, n));
                System.out.println("Prime is " + cryptPrim.findPrime(message, n));
                break;
            case "2":
                System.out.print("Enter file name : ");
                filename = sc.next();
                File f = new File(filename);
                System.out.print("Enter n (less than equal 31 bits) : ");
                n = sc.nextInt();
                PKC.setP(crypt.findPrime(f, n));
                System.out.println("Prime is " + cryptPrim.findPrime(f, n));
            default:
                break;
        }

        System.out.println("Big Integer Prime is " + PKC.getP());
    }
}
