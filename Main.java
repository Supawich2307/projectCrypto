import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        PrintStream fileOut = new PrintStream("./Encrypttest.out");
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
        int n = 0;
        Operation cryptPrim = new Operation();
        // Pair<Long,Long> cipherNum =  PKC.Encrypt( 66307261, 28939543, 25488675,4345666 );
        // cryptPrim.convertNumtoString(cipherNum.getKey(), cipherNum.getValue());
        switch (in) {
            case "1":
                System.out.print("Enter message : ");
                message = sc.nextLine();
                System.out.println();
                System.out.print("Enter n (less than equal 31 bits) : ");
                n = sc.nextInt(); 
                System.out.println();
                PKC.setP(cryptPrim.findPrime(message, n));
                // System.out.println("Prime is " + );
                break;
            case "2":
                System.out.print("Enter file name : ");
                filename = sc.next();
                File f = new File(filename);
                System.out.print("Enter n (less than equal 31 bits) : ");
                n = sc.nextInt();
                // PKC.setP(crypt.findPrime(f, n));
                PKC.setP(cryptPrim.findPrime(f, n));
                
                // System.out.println("Prime is " + cryptPrim.findPrime(f, n));
            default:
                break;
        }

        
        System.out.println("P is => "+PKC.getP()+" \nG is => "+PKC.getG());
        System.out.println("U is => "+PKC.getU()+" \nY is => "+PKC.getY());
        System.out.println("Big Integer Prime is " + PKC.getP());

        System.out.println("Please choose function");
        System.out.println("1. Encryption ");
        System.out.println("2. Decryption");
        System.out.print("Enter number 1 or 2: ");
        int func = sc.nextInt();
        sc.nextLine();
        String plaintext = "";
        System.out.println("Please choose plaintext type");
        System.out.println("1. Message ");
        System.out.println("2. File");
        System.out.print("Enter number 1 or 2: ");
        
        int plain_in = sc.nextInt();
        sc.nextLine();
        switch (plain_in) {
            case 1:
                System.out.print("Enter message : ");
                plaintext = sc.nextLine();
                // long plaintextArray [];
                // plaintextArray = cryptPrim.getPlainText(plaintext, n);
                Pair cipherNum =  PKC.Encrypt( 66307261, 28939543, 25488675,4345666 );
                System.out.println(cryptPrim.decToBinary(cipherNum.a, cipherNum.b));
                break;
            case 2:
                System.out.print("Enter file name : ");
                filename = sc.next();
                File f_plain = new File(filename);
                plaintext = cryptPrim.readFile(f_plain);
                // System.out.println("Prime is " + cryptPrim.findPrime(f, n));
            default:
                break;
        }


    }
}
