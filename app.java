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
        System.out.print("Please Enter username :");
        me_name = in.next();
        Elgamal me = new Elgamal(me_name);
        setKey(me);
        System.out.println("Hello "+me_name+"\nyour key are generated !!");
        
        while(running){
            System.out.print(
            "Please select menu : \n"+
            "1 : Encryption\n"+
            "2 : Decryption\n"+
            "3 : Sign\n"+
            "4 : verify\n"+
            "5 : Generate Key\n"+
            "6 : Add other user key\n"+
            "7 : Exit\n"
            );
            menu = in.nextInt();
            if(menu >= 1 && menu <= 7){
                switch(menu){
                    case 1: System.out.print("Enter reciver name :"); 
                            String reciver_name = in.next(); 
                            encrypt(me,reciver_name);
                            break;
                    case 2: System.out.print("Enter file ciptertext :"); 
                            String file = in.next(); 
                            decrypt(me,new File(file));
                            break;
                    case 3: 
                            sign(me);
                            break;
                    case 4: System.out.print("Enter sender name :"); 
                            String sender_name = in.next(); 
                            System.out.print("Enter file ciptertext :"); 
                            String Cfile_sender = in.next();
                            verify(me,sender_name,new File(Cfile_sender));
                            break;
                    case 5: setKey(me); break;
                    case 6: System.out.print("Pelase Enter other username :");
                        
                            String other_name = in.next();
                            System.out.println(other_name);
                            // Elgamal otheruser = new Elgamal(other_name);
                            // otheruser.setKeyFromFile(other_name+".pub");
                            opt.addPublicKey(pubKeyList, other_name+".pub");
                            break;
                    case 7: running = false;
                            File pub = new File(me_name+".pub");
                            pub.delete();
                            File pri = new File(me_name+".pri");
                            pri.delete();
                            System.out.println("Your key is termainated \nThank you");
                } 
            }else{
                System.out.println("Please select menu again !");
                menu = in.nextInt();
            }
            
        } 
    }
    private static void verify(Elgamal me,String sender_name, File file) throws IOException {

        SignedMessage<EncryptedMessage, Pair> signedMessage = opt.readSingedCipher("SignMessage.out");
    
        System.out.println(me.VerifyCipher(signedMessage, pubKeyList.get(sender_name)));
    }
    private static void sign(Elgamal me) throws IOException {
    
        System.out.print("Enter file ciptertext : "); in.nextLine();
        String Cfile = in.nextLine();
        String cipherList = readfile(new File(Cfile));
        String [] list = cipherList.split("");
        int n = Integer.parseInt(list[0]);
        String cipher = list[1];
        Pair [] cipher_ab =  opt.readCipherText(cipher,n/(int) (me.getN()*2),(int) me.getN());
        // EncryptedMessage =  readfile(file);
        // SignedMessage<EncryptedMessage, Pair> signedMessageA_B = me.signMessage();

        // System.out.println(signedMessageA_B);

        // opt.writeSignedCipher(signedMessageA_B, "Alice_Bob_SignMessage.out");
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
                System.out.println();
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

        System.out.println("P is => "+me.getP()+" \n G is => "+me.getG());
        System.out.println("U is => "+me.getU()+" \n Y is => "+me.getY());
    }
    
    private static void encrypt(Elgamal me,String recive_name) throws IOException{
        int typeMsg ;
        EncryptedMessage messageAlice;
        
        System.out.println("Please select type of Plaintext \n1 : Text\n2 : File");
        typeMsg = in.nextInt();
        
        switch(typeMsg){
            case 1 : 
                    System.out.print("Enter Message : "); in.nextLine();
                    String msg = in.nextLine();
                    
                    System.out.print(msg);
                    messageAlice = me.encryptMessage(msg,pubKeyList.get(recive_name));
                    System.out.println(messageAlice);
                    opt.writeMessage(messageAlice, "Alice_Bob_Message.out");
                    break;
                    
            case 2 : 
                    System.out.print("Enter File name : "); 
                    in.nextLine();
                    String file = in.nextLine();
                    messageAlice = me.encryptFile(file,pubKeyList.get(recive_name));
                    System.out.println(messageAlice);
                    opt.writeMessage(messageAlice, "Alice_Bob_Message.out");
                    break;
        }
    }

    private static void decrypt(Elgamal me,File filename) throws IOException{

        // EncryptedMessage encMsg = opt.readMessage(readfile(filename));
        // long [] plainDec =  me.DecryptMessage(encMsg.cipher, encMsg.M);
        // System.out.println(opt.decodeToMessage(plainDec,encMsg));
    }

    private static String readfile(File file) throws IOException{
        BufferedReader read = new BufferedReader(new FileReader("Alice_Bob_Message.out")); 
        int cipher_len = Integer.parseInt(read.readLine());
        String cipher_dec = read.readLine();
        String [] cipherByte = cipher_dec.split(" ");
        String cipherbinary = "";
        for (String cipher : cipherByte) {
            cipherbinary += opt.appendZero(Long.toBinaryString(Long.parseLong(cipher)), 8);
        }
        cipherbinary = cipherbinary.substring(0, cipher_len);
        return  cipher_len+" "+cipher_dec;
    }
}
