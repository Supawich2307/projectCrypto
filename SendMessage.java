import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;

public class SendMessage {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        PrintStream fileOut = new PrintStream("./SendMessageA_B_2.log");
        System.setOut(fileOut);

        Operation opt = new Operation();

        Elgamal Alice = new Elgamal();

        Alice.setKeyFromFile("Alice.pri");

        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "Bob.pub");
        opt.addPublicKey(pubKeyList, "Alice.pub");
        opt.addPublicKey(pubKeyList, "Uraiwan.pub");
        opt.addPublicKey(pubKeyList, "Supakew.pub");
        
        
        for (String name : pubKeyList.keySet()) {
            System.out.println("Name is "+name+ " public key is "+pubKeyList.get(name));
        }

        EncryptedMessage messageAlice = Alice.encryptMessage(
                        new File("Test.jpg"), 
                        pubKeyList.get("Uraiwan"));

        System.out.println(messageAlice);

        opt.writeMessage(messageAlice, "Alice_Uraiwan_Message2.out");

        SignedMessage<EncryptedMessage, Pair> signedMessageA_B = Alice.signMessage(messageAlice);

        System.out.println(signedMessageA_B);

        opt.writeSignedCipher(signedMessageA_B, "Alice_Uraiwan_SignMessage2.out");
    }
}

