import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class intregate {
    public static void main(String[] args) throws IOException {
        Operation operate = new Operation();
        /* SENDER */
        Elgamal Alice = new Elgamal();
        Alice.setP(operate.findPrime("Bob is here", 28));
        Alice.setG();
        Alice.setU();
        Alice.setY();
        FileWriter writePK_Alice = new FileWriter("Alice.key");
        writePK_Alice.write(Alice.getP()+"\n"+Alice.getG()+"\n"+Alice.getY());
        writePK_Alice.close();
        System.out.println("Alice : "+Alice.toString());
        

        /* RECIVER */
        Elgamal Bob = new Elgamal();
        Bob.setP(operate.findPrime("Hello World", 28));
        Bob.setG();
        Bob.setU();
        Bob.setY();
        FileWriter writePK_Bob = new FileWriter("Bob.key");
        writePK_Bob.write(Bob.getP()+"\n"+Bob.getG()+"\n"+Bob.getY());
        writePK_Bob.close();
        System.out.println("Bob : "+Bob.toString());

        System.out.println("Encrypt");
        FileInputStream readBob = new FileInputStream("Bob.key");  
        Scanner sc = new Scanner(readBob);
        Long P = Long.parseLong(sc.nextLine());    
        Long G = Long.parseLong(sc.nextLine());
        Long Y = Long.parseLong(sc.nextLine());
        Pair cipher = Alice.Encrypt(P,G,Y, operate.encode("BOO"));
        System.out.println("cipher pair : "+cipher);
        String encode_cipher = operate.encode(cipher, 28);

        FileWriter writeMsg = new FileWriter("Message.txt");
        writeMsg.write(encode_cipher);
        writeMsg.close();
        System.out.println("cipher message : "+encode_cipher);

        /* DECRYPT*/
        System.out.println("Decrypt");
        FileReader reader = new FileReader("Message.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String readCipher_alice = bufferedReader.readLine();
        bufferedReader.close();

        Pair cipher_alice = operate.decodeMessage(readCipher_alice);
        System.out.println("sub cipher :"+cipher_alice);
        long msg = Bob.Decrypt(Bob.getP(), Bob.getU(), cipher_alice);
        // String plaintext_alice = operate.decodeToPlaintext(msg);
        // System.out.println("plaintext Alice :"+plaintext_alice);

    }
}
