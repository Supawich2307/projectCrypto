import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

public class ReceiveMessage {
    public static void main(String[] args) throws FileNotFoundException , IOException{
        //PrintStream fileOut = new PrintStream("./RecieveMessageA_B_1.log");
        //System.setOut(fileOut);

        File file = new File("Alice_Uraiwan_Message.out");
        Operation opt = new Operation();
        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "Bob.pub");
        opt.addPublicKey(pubKeyList, "Alice.pub");
        opt.addPublicKey(pubKeyList, "Uraiwan.pub");
        

        Elgamal Uraiwan = new Elgamal();
        Uraiwan.setKeyFromFile("Uraiwan.pri");

        // convert file to object 
        EncryptedMessage encMsg = opt.readMessage("Alice_Uraiwan_Message.out");

        //decrypt each block 
        long [] plainDec =  Uraiwan.DecryptMessage(encMsg.cipher, encMsg.M);

        //decode long to message
        System.out.println(opt.decodeToMessage(plainDec,encMsg));

        // System.out.println(N+" "+B+" "+M+" "+type+"\n"+cipher);

        SignedMessage<EncryptedMessage, Pair> signedMessage = opt.readSingedCipher("Alice_Uraiwan_SignMessage.out");
    
        System.out.println(Uraiwan.VerifyCipher(signedMessage, pubKeyList.get("Alice")));
    }
}
