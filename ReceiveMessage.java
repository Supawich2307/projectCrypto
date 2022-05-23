import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReceiveMessage {
    public static void main(String[] args) throws FileNotFoundException , IOException{
        File file = new File("Alice_Uraiwan_Message.out");
        Operation opt = new Operation();

        Elgamal PKC = new Elgamal();
        PKC.setKeyFromFile("Uraiwan.pri");
        
        Scanner sc = new Scanner(file);
        int N = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        MediaType type =  MediaType.valueOf(sc.next());
        sc.nextLine();
        //read from file to (a,b)
        Pair[] cipher = opt.readCipherText(sc.nextLine(),M,24); 

        // convert file to object 
        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);

        //decrypt each block 
        long [] plainDec =  PKC.DecryptMessage(cipher, M);

        //decode long to message
        System.out.println(opt.decodeToMessage(plainDec,encMsg));

        // System.out.println(N+" "+B+" "+M+" "+type+"\n"+cipher);
    }
}
