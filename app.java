import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Scanner;

public class app {
    static Scanner in = new Scanner(System.in);
    static Operation opt = new Operation();
    static String me_name;
    static Hashtable<String, PublicKey<Long>> pubKeyList = new Hashtable<>();
    public static void main(String[] args) throws IOException {
        int menu;
        boolean running = true;
        // System.out.print("Please Enter username :");
        // me_name = in.next();
        // Elgamal me = new Elgamal(me_name);
        // setKey(me);
        // System.out.println("Hello "+me_name+" your key are generated !!");
        Elgamal me = new Elgamal("B");
        me.setKeyFromFile("B.pri");
        while(running){
            System.out.print(
            "Please select menu : \n"+
            "1 : Encryption\n"+
            "2 : Encryption with Sign\n"+
            "3 : Decryption\n"+
            "4 : Decryption with Verify\n"+
            "5 : Sign\n"+
            "6 : verify\n"+
            "7 : Generate Key\n"+
            "8 : Add other user key\n"+
            "9 : Exit\n"
            );
            menu = in.nextInt();
            if(menu >= 1 && menu <= 9){
                switch(menu){
                    case 1: encrypt(me); break;

                    case 2: encrypt(me);
                            sign(me);
                            break;

                    case 3: decrypt(me); break;

                    case 4: decrypt_Verify(me);
                            break;

                    case 5: sign(me); break;

                    case 6: verify(me); break;

                    case 7: setKey(me); break;

                    case 8: System.out.print("Pelase Enter other username :");
                            String other_name = in.next();
                            opt.addPublicKey(pubKeyList, other_name+".pub");
                            break;

                    case 9: running = false;
                            File pub = new File(me_name+".pub");
                            pub.delete();
                            File pri = new File(me_name+".pri");
                            pri.delete();
                            System.out.println("Your key is termainated \nThank you");
                            break;

                } 
            }else{
                System.out.println("Please select menu again !");
                menu = in.nextInt();
            }
            
        } 
    }
    private static void verify(Elgamal me) throws IOException {
        System.out.print("Enter sender name :"); 
        String sender_name = in.next(); 
        System.out.print("Enter file ciptertext :"); 
        String Cfile_sender = in.next();

        SignedMessage<EncryptedMessage, Pair> signedcipher = opt.readCipherSignature(Cfile_sender,(int) me.getN());
        EncryptedMessage enc = signedcipher.message;
        System.out.print(signedcipher);
        System.out.println(me.VerifyCipher(signedcipher, pubKeyList.get(sender_name)));
    }
    
    private static void sign(Elgamal me) throws IOException {
    
        System.out.print("Enter file ciptertext : "); in.nextLine();
        String Cfile = in.nextLine();
        EncryptedMessage cipher = opt.readCipher(Cfile,(int) me.getN());
        System.out.println("main "+cipher);
        SignedMessage<EncryptedMessage, Pair> sign =  me.signMessage(cipher);
        System.out.println("main "+sign.toString());
        opt.writeSignedCipher(sign, "sign.out");
    }
    
    private static void setKey(Elgamal me) throws IOException{
        System.out.println("Please choose type of plaintext");
        System.out.println("1. String message ");
        System.out.println("2. File");
        System.out.print("Enter number 1 or 2: ");
        String  typekey = in.next(); in.nextLine();
        System.out.println();
        String message;
        String filename;
        int n = 0;
        switch (typekey) {
            case "1":
                System.out.print("Enter message : ");
                message = in.nextLine();
                System.out.print("Enter n (less than equal 28 bits) : ");
                n = in.nextInt(); 
                System.out.println();
                me.genKey(message, n);
                break;
            case "2":
                System.out.print("Enter file name : ");
                filename = in.next();
                File f = new File(filename);
                System.out.print("Enter n (less than equal 28 bits) : ");
                n = in.nextInt();
                me.genKey(f, n);
            default:
                break;
        }
        me.writePublicKey();
        me.writePrivateKey();

        System.out.println("P is => "+me.getP()+" \nG is => "+me.getG());
        System.out.println("U is => "+me.getU()+" \nY is => "+me.getY());
    }
    
    private static void encrypt(Elgamal me) throws IOException{
        int typeMsg ;
        EncryptedMessage messageAlice;
        System.out.print("Enter reciver name :"); 
        String reciver_name = in.next(); 
        System.out.println("Please select type of Plaintext \n1 : Text\n2 : File");
        typeMsg = in.nextInt();
        
        switch(typeMsg){
            case 1 : 
                    System.out.print("Enter Message : "); in.nextLine();
                    String msg = in.nextLine();
                    System.out.print(msg);
                    messageAlice = me.encryptMessage(msg,pubKeyList.get(reciver_name));
                    System.out.println(messageAlice);
                    opt.writeMessage(messageAlice, me.getName()+"_"+reciver_name+"_Message.out");
                    break;
                    
            case 2 : 
                    System.out.print("Enter File name : "); 
                    in.nextLine();
                    String file = in.nextLine();
                    messageAlice = me.encryptMessage(file,pubKeyList.get(reciver_name));
                    System.out.println(messageAlice);
                    opt.writeMessage(messageAlice, file+".enc");
                    break;
        }
    }
    
    private static void decrypt(Elgamal me) throws IOException{
        System.out.println("Please select media type \n1 : Text\n2 : File");
        int typeMsg = in.nextInt();
        System.out.print("Enter file ciptertext :"); 
        String filename = in.next(); 

        EncryptedMessage encMsg = opt.readCipher(filename,(int) me.getN());
        System.out.println(encMsg);
        long [] plainDec =  me.DecryptMessage(encMsg.cipher, encMsg.M);
        
        switch(typeMsg){
            case 1 : System.out.println(opt.decodeToMessage(plainDec,encMsg));
                     break;
            case 2 : 
                     break;
        }
        
    }
    private static void decrypt_Verify(Elgamal me) throws IOException{
        System.out.println("Please select media type \n1 : Text\n2 : File");
        int typeMsg = in.nextInt();
        System.out.print("Enter file ciptertext :"); 
        String filename = in.next(); 
        System.out.print("Enter sender name :"); 
        String sender_name = in.next(); 
        SignedMessage<EncryptedMessage,Pair>  sign_cipher = opt.readCipherSignature(filename,(int) me.getN());
        EncryptedMessage encMsg = sign_cipher.message;
        System.out.println(encMsg);
        long [] plainDec =  me.DecryptMessage(encMsg.cipher, encMsg.M);
        System.out.println(me.VerifyCipher(sign_cipher, pubKeyList.get(sender_name)));
        switch(typeMsg){
            case 1 : System.out.println(opt.decodeToMessage(plainDec,encMsg));
                     break;
            case 2 : 
                     break;
        }
        
    }

}
