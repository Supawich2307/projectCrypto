import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class Operation {
    public long powerModFast(long n, long x, long m){
        long res = 1; 
 
        n = n % m; 
    
        if (n == 0)
        return 0; // In case x is divisible by p;
    
        while (x > 0)
        {
            if ((x & 1) != 0)
                res = (res * n) % m;
        
            x = x >> 1; // x = x/2
            n = (n * n) % m;
        }
        return res;
    }

    public long powerMod(long n, long x, long m){
        long answer = 1;
        for(long i = 0; i < x; i++) {
            answer *= n;
            answer %= m;
        }

        return answer;
    }

    public long findInverse(long n1, long n2) {

        long r = n1%n2;   // r
        long q = n1/n2;   // q
        long a1 = 1, b1 = 0, a2 = 0, b2 = 1;       //b2
        long res = findInverse(n1, n2, r, q, a1, b1, a2, b2);
        if ( res == 0)
            return -1;
        else 
            return Math.floorMod(res, n1);
    }

    public long findInverse(
        long n1, long n2, long r, long q, long a1, long b1, long a2, long b2
    ) {
        
        if(r == 0) { // n1 divisible by n2 
            if(n2 == 1) {
                return b2;
            } else {
                return 0;
            }
        } else if(r > 0) {
            long temp = a2;
            a2 = a1 - (q*a2);
            a1 = temp;
            temp = b2;
            b2 = b1 - (q*b2);
            b1 = temp;
            n1 = n2;
            n2 = r;
            r = n1%n2;
            q = n1/n2;
        }

        return findInverse(n1, n2, r, q, a1, b1, a2, b2);
    }

    public long findPrime(File f, int n)  throws IOException{
        String plaintext = readFile(f);
        int index = plaintext.indexOf("1");
        String s = plaintext.substring(index, index+n);
        long m = binaryToDec(Long.parseLong(s));

        return findPrime(m);
    }

    public long findPrime(String S, int n) throws UnsupportedEncodingException{
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
        long m = binaryToDec(Long.parseLong(blockStr));
        // System.out.print(m);
        return findPrime(m);
    }

    public long findPrime(long m){
        
        
        if(m % 2 ==0 && m != 2) { // check if even and not two
            m++;
        }
        if(LahmenTest(m) && LahmenTest((m-1)/2)){
            return m;
        }else{
            System.out.println(m + " not safe prime ");
            if(m == 2) return findPrime(m+1);                       // ==2
            else return findPrime(m + 2);                         // !=2
        }
    }

    public Boolean LahmenTest(long n){
        
        for(int i = 0;i < 100;i++){
            long a = (long)(Math.random()*n);
            a = a % n;
            if(a < 2){
                a = a + 2;
            }
            //System.out.println("round: " + i + " a:"+a+" n > "+n);
            
            long gcd = findInverse(a, n);
            
            if(gcd == -1){ // cannot find inverse which mean gcd != 1
                return false;
            }else{
                long exponent = (n-1)/2;
                //System.out.println(a+" power > "+exponent);
                long x = powerMod(a, exponent, n);
                //System.out.println(a+" power > "+exponent+" "+x);
                System.out.println("round: " + i + " a: "+a+" check n: "+n+" exponent " + exponent+" result : " + x);
                if(x==1 || x == (n-1)){ // x = 1 || x == n-1
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

    public long binaryToDec(long num) {
        long base = 1;
        long temp = num;
        long dec_value = 0;
        while (temp > 0) {
            long last_digit = temp % 10;
            temp = temp / 10;
 
            dec_value += last_digit * base;
 
            base = base * 2;
        }
        return dec_value;
    }

}
