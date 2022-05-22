import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        PrintStream fileOut = new PrintStream("./Alice_BOB.log");
        System.setOut(fileOut);

        Operation opt = new Operation();

        //.out.println(opt.binaryToDec("10001100010001011001110"));
        //                                      100011000100010110011100

        Elgamal Alice = new Elgamal("Alice");
        Alice.genKey("Alice is coming", 24);
        Alice.writePublicKey("Alice.pub");

        Elgamal Bob = new Elgamal("Bob");
        Bob.genKey("Hello world", 24);
        Bob.writePublicKey("Bob.pub");

        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "Bob.pub");
        opt.addPublicKey(pubKeyList, "Alice.pub");
        //Alice.setP(calculator.fin);
        //Alice.setG(new Operation().findGenerator(Alice.getP()));
        //Alice.set
        
        //System.out.println("P is => "+Alice.getP()+" \n G is => "+Alice.getG());
        //System.out.println("U is => "+Alice.getU()+" \n Y is => "+Alice.getY());
    


        System.out.println("P is => "+Bob.getP()+" \n G is => "+Bob.getG());
        System.out.println("U is => "+Bob.getU()+" \n Y is => "+Bob.getY());

        //long msg = Bob.Decrypt(Bob.getP(), Bob.getU(), new Pair(6847727, 19107768));
        //System.out.println(msg);
        
        /*System.out.println(calculator.decodeToPlaintext(msg));*/
        
        for (String name : pubKeyList.keySet()) {
            System.out.println("Name is "+name+ " public key is "+pubKeyList.get(name));
        }

        EncryptedMessage messageAlice = Alice.encryptMessage(
                        "Hello, Bob!", 
                        pubKeyList.get("Bob"));

        System.out.println(messageAlice);

        opt.writeMessage(messageAlice, "Alice_Bob_Message.out");


    }
}

