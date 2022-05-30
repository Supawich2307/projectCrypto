import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        PrintStream fileOut = new PrintStream("./picTest1.log");
        PrintStream fileOut2 = new PrintStream("./picTest2.log");
        System.setOut(fileOut2);

        Operation opt = new Operation();

        //Elgamal Uraiwan = new Elgamal();
        //Alice.genKey("Alice is coming", 24);
        //Alice.writePublicKey("Alice.pub");

       //Elgamal Bob = new Elgamal("Bob");
        //Bob.genKey("Hello world", 24);
        //Bob.writePublicKey("Bob.pub");
        //System.out.println(new Operation().powerMod(106, 37, 143));
        //System.out.println(new Operation().powerMod(28, 58, 143));
        //System.out.println(new Operation().calculateInverse(143, 37));
/*
        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "Bob.pub");
        opt.addPublicKey(pubKeyList, "Alice.pub");
        opt.addPublicKey(pubKeyList, "Uraiwan.pub");
        //Alice.setP(calculator.fin);
        //Alice.setG(new Operation().findGenerator(Alice.getP()));
        //Alice.set
        
        //System.out.println("P is => "+Alice.getP()+" \n G is => "+Alice.getG());
        //System.out.println("U is => "+Alice.getU()+" \n Y is => "+Alice.getY());
    


        //System.out.println("P is => "+Bob.getP()+" \n G is => "+Bob.getG());
        //System.out.println("U is => "+Bob.getU()+" \n Y is => "+Bob.getY());

        //long msg = Bob.Decrypt(Bob.getP(), Bob.getU(), new Pair(6847727, 19107768));
        //System.out.println(msg);
        
        /*System.out.println(calculator.decodeToPlaintext(msg));*/
     /*   
        for (String name : pubKeyList.keySet()) {
            System.out.println("Name is "+name+ " public key is "+pubKeyList.get(name));
        }

        EncryptedMessage messageAlice = Alice.encryptMessage(
                        "Hello, Uraiwan!", 
                        pubKeyList.get("Uraiwan"));

        System.out.println(messageAlice);

        opt.writeMessage(messageAlice, "Alice_Uraiwan_Message.out");
*/
/*
        Alice.setP(8575187);
        Alice.setG(5840173);
        Alice.setY(3844290);
        Alice.setU(8575187);
        */
        /*
        Uraiwan.setKeyFromFile("Uraiwan.pri");
        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "Bob.pub");
        opt.addPublicKey(pubKeyList, "Alice.pub");
        opt.addPublicKey(pubKeyList, "Uraiwan.pub");
        String X = "001100011011100011001100001110011001000110101100";
        
        //SignedMessage <String,Pair> signmsg_Uraiwan = Uraiwan.signMessage(X);
        EncryptedMessage enc_msg = opt.readMessage("Alice_Uraiwan_Message.out");
        SignedMessage <EncryptedMessage, Pair> signmsg_Uraiwan = Uraiwan.signMessage(enc_msg);

//        System.out.println(Bob.Verify(signmsg_Alice, 
//          new PublicKey<Long>((long)8575187, (long)5840173, 
 //                               (long)3844290, 24)));
        System.out.println(signmsg_Uraiwan);
        System.out.println(Bob.VerifyCipher(signmsg_Uraiwan, pubKeyList.get("Uraiwan")));
        */
        //writeToFile("testpic.png", 255, 3);
        //Alice_Bob_Message (2).out
        //readFileTest(new File("Test.jpg"));
        //readFileTest(new File("Alice_Bob.out"));

        
        Elgamal A = new Elgamal();
        A.setKeyFromFile("A.pri");
        A.setU(18140132);
        A.setY(9311260);
        Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
        opt.addPublicKey(pubKeyList, "A.pub");
        opt.addPublicKey(pubKeyList, "B.pub");
        //EncryptedMessage enc = opt.readCipher("B_A_Message.out", (int)A.getN());
        
        //System.out.println(enc);
        /*System.out.println("P is => "+A.getP()+" \n G is => "+A.getG());
        System.out.println("U is => "+A.getU()+" \n Y is => "+A.getY());
*/
        String filename = "picTest2.jpg.enc";
        

        //SignedMessage<EncryptedMessage, Pair> sign_cipher 
        //    = opt.readCipherSignature(filename, (int)A.getN());
        EncryptedMessage enc = opt.readCipher(filename, (int)A.getN());
        //EncryptedMessage enc = sign_cipher.message;

        long [] plainDec =  A.DecryptMessage(enc.cipher, enc.M);

        System.out.println(enc);
        
        //System.out.println(opt.decodeToMessage(plainDec,enc));
        filename = filename.replace(".", "/");
        String filestructure[] = filename.split("/");
        System.out.println("file structure"+filestructure.length+" name "+ filestructure[0]);
        String file_dec_name = filestructure[0]+"_dec."+filestructure[1];
        opt.decodeToFile(plainDec, enc, "data/"+file_dec_name);
        //System.out.println(A.VerifyCipher(sign_cipher, pubKeyList.get("B")));
        
        //decode long to message

        /*EncryptedMessage messageAlice = A.encryptMessage(
                        new File("Test.jpg"), 
                        pubKeyList.get("B"));

        System.out.println(messageAlice);*/
        //String data = opt.readFile(new File("picTest.jpg"));
        //System.out.println("data length"+data.length());
        //opt.createFile(data, "data/picTest_dec.jpg");
    }

    public static String readFileTest(File f) throws IOException {
        DataInputStream data_in = new DataInputStream(
            new BufferedInputStream(
                new FileInputStream(f)));

        StringBuilder plaintext = new StringBuilder(); 
        int a = 0;
        while(a != -1) {
            try {
                    a = data_in.read();
                    if(a == -1) {
                        break;
                    }
                String b = String.format("%8s", Integer.toBinaryString(a)).replace(' ', '0');
                System.out.println("byte " + a + " bi "+b);
                plaintext.append(b);
            }
            catch(EOFException eof) {
                System.out.println ("End of File");
                break;
            }
        }

        
        return plaintext.toString(); 
    }

    private static void writeToFile(String filePath, int data, int position) 
    throws IOException {  
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");  
        file.seek(position);  
        file.writeInt(data);
        file.close();  
    }  
}

