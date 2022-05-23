import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class staticTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Alice_to_Bob.out");
        Operation opt = new Operation();
        Elgamal PKC = new Elgamal();
        Scanner sc = new Scanner(file);
        int N = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        MediaType type =  MediaType.valueOf(sc.next());
        sc.nextLine();
        Pair[] cipher = opt.readCipherText(sc.nextLine(),M,24); 

        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        long [] plainDec =  PKC.DecryptMessage(cipher, M);
        System.out.println(opt.decodeToMessage(plainDec,encMsg));

        // System.out.println(N+" "+B+" "+M+" "+type+"\n"+cipher);
    }
}
