import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class KeyGeneration {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        PrintStream fileOut = new PrintStream("./Key_Generation.log");
        System.setOut(fileOut);
        
        Scanner sc = new Scanner(System.in);


        //Calculator crypt = new Calculator();


        System.out.println("Please enter your name");
        String name = sc.next();sc.nextLine();

        
        Elgamal PKC = new Elgamal(name);

        System.out.println("Please choose type of plaintext");
        System.out.println("1. String message ");
        System.out.println("2. File");
        System.out.print("Enter number 1 or 2: ");
        String in = sc.next();sc.nextLine();
        System.out.println();
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
                PKC.genKey(message, n);
                // System.out.println("Prime is " + );
                break;
            case "2":
                System.out.print("Enter file name : ");
                filename = sc.next();
                File f = new File(filename);
                System.out.print("Enter n (less than equal 31 bits) : ");
                n = sc.nextInt();
                
                PKC.genKey(f, n);
                // System.out.println("Prime is " + cryptPrim.findPrime(f, n));
            default:
                break;
        }

        PKC.writePrivateKey();
        PKC.writePublicKey(name+".pub");
        
        System.out.println("P is => "+PKC.getP()+" \n G is => "+PKC.getG());
        System.out.println("U is => "+PKC.getU()+" \n Y is => "+PKC.getY());

    }
}
