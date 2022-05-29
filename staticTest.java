import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class staticTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Alice_to_Bob.out");
        Operation opt = new Operation();
        Elgamal Alice = new Elgamal();
        Alice.setP(11);
        Alice.setG(2);
        Alice.setY(8);
        Alice.setU(3);
        long X = opt.hashFunction("001100011011100011001100001110011001000110101100");
        
        // SignedMessage <Long,Pair> signmsg_Alice = Alice.signMessage(9);
        // System.out.println(Alice.Verify(signmsg_Alice));

        // Elgamal Bob = new Elgamal();
        // Bob.setP(9489407);
        // Bob.setG(8432495);
        // Bob.setY(4127182);
        // Bob.setU(267451);

        // System.out.println(Bob.Verify(signmsg_Alice));
        
        // System.out.println(X);

        // SignedMessage <Long,Pair> signmsg_Alice = PKC.signMessage(X);

        // Scanner sc = new Scanner(file);
        // int N = Integer.parseInt(sc.next());
        // int B = Integer.parseInt(sc.next());
        // int M = Integer.parseInt(sc.next());
        // MediaType type =  MediaType.valueOf(sc.next());
        // sc.nextLine();
        // String msg = sc.nextLine();
        // Pair[] cipher = opt.readCipherText(msg,M,24); 
        // sc.nextLine();
        // Pair rs = new Pair(Long.parseLong(sc.next()), Long.parseLong(sc.next()));
        // SignedMessage <Long,Pair> signmsg = new SignedMessage(msg,rs);
        // EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        // boolean verify = PKC.Verify(1, 1, 1, signmsg);
        // if()
        // long [] plainDec =  PKC.DecryptMessage(cipher, M);
        // System.out.println(opt.decodeToMessage(plainDec,encMsg));
        

        // System.out.println(N+" "+B+" "+M+" "+type+"\n"+cipher);


    }
}
