import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

public class Calculator {
    public BigInteger findPrime(File f, int n)  throws IOException{
        String plaintext = readFile(f);
        int index = plaintext.indexOf("1");
        String s = plaintext.substring(index, index+n);
        BigInteger m = new BigInteger(s, 2);

        System.out.println("sub string binary is "+ s+" convert to decimal "+m);
        return findPrime(m);
    }

    public BigInteger findPrime(String S, int n) throws UnsupportedEncodingException{
        byte[] bytes = S.getBytes("US-ASCII");      
        StringBuilder binaryStr = new StringBuilder();
        for(byte b : bytes){
            // System.out.println(b);
            int val = b;
            for(int i = 0;i < 8;i++){
                binaryStr.append((val & 128) == 0 ? 0:1);
                val <<=1;
            }
        }
        // System.out.println("<<< "+binaryStr);
        String blockStr = "";
        for(int i = 0;i < binaryStr.length();i++){
            if(binaryStr.charAt(i) == '1'){
                blockStr = binaryStr.substring(i,i+n);
                // System.out.println(">> "+blockStr+" "+(i)+" "+(i+n));
                break;
            }
        }
        BigInteger m = new BigInteger(blockStr,2);
        System.out.println("Sub message is "+blockStr+ " convert to decimal " + m);
        return findPrime(m);
    }

    public BigInteger findPrime(BigInteger m){
        
        
        if(m.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0  // 0=1 -> 1 | 1=0 -> -1 | 0=0 -> 0 if even
        && m.compareTo(BigInteger.valueOf(2)) != 0){ // check is it not two
            m = m.add(BigInteger.ONE);
        }
        if(LehmanTest(m) && LehmanTest(m.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)))){
            System.out.println(m + " is safe prime ");
            return m;
        }else{
            System.out.println(m + " is not safe prime");
            if(m.compareTo(BigInteger.valueOf(2)) == 0) return findPrime(m.add(BigInteger.ONE)); // ==2
            else return findPrime(m.add(BigInteger.valueOf(2)));                         // !=2
        }
    }

    public Boolean LehmanTest(BigInteger n){
        Random randNum = new Random();
        int len = n.subtract(BigInteger.ONE).bitLength();
        
        for(int i = 0;i < 100;i++){
            BigInteger a = new BigInteger(len, randNum);
            a = a.mod(n);
            if(a.compareTo(BigInteger.valueOf(2)) == -1){
                a = a.add(BigInteger.valueOf(2));
            }
            //System.out.println("round: " + i + " a:"+a+" n > "+n);
            
            ExtendedGCD gcd = new ExtendedGCD(a,n);
            //System.out.println(a+" gcd >> "+gcd.getGCD());
            if(gcd.getGCD().compareTo(BigInteger.ONE) == 1){ // gcd(a,n) > 1
                System.out.println(n+" is not prime. gcd >> "+gcd.getGCD());
                return false;
            }else{
                BigInteger exponent = ((n.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)));
                //System.out.println(a+" power > "+exponent);
                BigInteger x = a.modPow(exponent, n);
                //System.out.println(a+" power > "+exponent+" "+x);
                System.out.println(" check n: "+n+" round: " + (i+1) + " a: "+a+" exponent " + exponent+" result : " + x);
                if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0){ // x = 1 || x == n-1
                    continue;
                }else return false;
                
            }
            
        }
        return true;
    }

    public String readFile(File f) throws IOException {
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
                //System.out.println("byte " + a + " bi "+b);
                plaintext.append(b);
            }
            catch(EOFException eof) {
                System.out.println ("End of File");
                break;
            }
        }

        
        return plaintext.toString(); 
    }
}
