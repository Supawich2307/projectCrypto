import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class staticTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("CipherText.txt");
        Operation opt = new Operation();
        Scanner sc = new Scanner(file);
        int N = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        MediaType type =  MediaType.valueOf(sc.next());
        sc.nextLine();
        Pair[] cipher = opt.readCipherText(sc.nextLine(),M,B); 

        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);


        // System.out.println(N+" "+B+" "+M+" "+type+"\n"+cipher);
    }
}
