import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        PrintStream fileOut = new PrintStream("./Test_26_Alice.out");
        System.setOut(fileOut);
        
        String S = "Hello World";
        byte[] bytes = S.getBytes("TIS-620");  
        StringBuilder binaryStr = new StringBuilder();
        for(byte b : bytes){
            // System.out.println(b);
            int val = b;
            for(int i = 0;i < 8;i++){
                binaryStr.append((val & 128) == 0 ? 0:1);
                val <<=1;
            }
        }
        //System.out.println("<<< "+binaryStr);

        System.out.println(new Operation().powerModFast(7, 6, 11));
        System.out.println(new Operation().calculateInverse(4, 11));
        System.out.println(new Operation().powerModFast(7, 4, 11));
        //System.out.println(new Operation().isGenerator(6, 11));



        Elgamal Alice = new Elgamal();
        Alice.setP(35996459);
        //Alice.setG(new Operation().findGenerator(Alice.getP()));
        //Alice.set
        
        //System.out.println("P is => "+Alice.getP()+" \n G is => "+Alice.getG());
        //System.out.println("U is => "+Alice.getU()+" \n Y is => "+Alice.getY());
    
        Elgamal Bob = new Elgamal();

        Bob.setP(66307261);
        Bob.setG(28939543);
        Bob.setU(27915150);
        Bob.setY();

        System.out.println("P is => "+Bob.getP()+" \n G is => "+Bob.getG());
        System.out.println("U is => "+Bob.getU()+" \n Y is => "+Bob.getY());

        System.out.println(Bob.Decrypt(Bob.getP(), Bob.getU(), new Pair(11838109, 6382093)));

    }
}

