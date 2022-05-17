import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import javafx.util.Pair;

public class Operation {
    private long inverse;
    private long gcd;

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

    public void extendedGCD(long n1, long n2) {

        long r = n1%n2;   // r
        long q = n1/n2;   // q
        long a1 = 1, b1 = 0, a2 = 0, b2 = 1;       //b2
        long res = extendedGCD(n1, n2, r, q, a1, b1, a2, b2);
        if ( res == 0)
            this.inverse = -1;
        else 
            this.inverse = Math.floorMod(res, n1);
    }

    public long extendedGCD(
        long n1, long n2, long r, long q, long a1, long b1, long a2, long b2
    ) {
        
        if(r == 0) { // n1 divisible by n2 
            this.gcd = n2;
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

        return extendedGCD(n1, n2, r, q, a1, b1, a2, b2);
    }

    public long calculateInverse (long n1, long n2) {
        extendedGCD(n1, n2);
        return this.inverse;
    }

    public long calculateGCD (long n1, long n2) {
        extendedGCD(n1, n2);
        return this.gcd;
    }

    public long findPrime(File f, int n)  throws IOException{
        String plaintext = readFile(f);
        int index = plaintext.indexOf("1");
        String s = plaintext.substring(index, index+n);
        long m = binaryToDec(s);
        System.out.println("Subtring is "+s+" dec val is "+m);
        //System.out.println("Subtring is "+plaintext+" dec val is "+m);
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
        long m = binaryToDec(blockStr);
        System.out.println("Subtring is "+blockStr+" dec val is "+m);
        return findPrime(m);
    }

    public long findPrime(long m){
        
        
        if(m % 2 ==0 && m != 2) { // check if even and not two
            m++;
        }
        if(LahmenTest(m) && LahmenTest((m-1)/2)){
            return m;
        }else{
            // System.out.println(m + " not safe prime ");
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
            
            long gcd = calculateGCD(a, n);
            
            if(gcd > 1){ // cannot find inverse which mean gcd != 1
                return false;
            }else{
                long exponent = (n-1)/2;
                //System.out.println(a+" power > "+exponent);
                long x = powerMod(a, exponent, n);
                //System.out.println(a+" power > "+exponent+" "+x);
                // System.out.println("round: " + i + " a: "+a+" check n: "+n+" exponent " + exponent+" result : " + x);
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

    public long binaryToDec(String num) {
        long base = 1;
        long dec_value = 0;
        for(int i = num.length()-1; i >-1 ;  i--) {
            long last_digit = Character.getNumericValue(num.charAt(i));
 
            dec_value += last_digit == 1? base: 0;
 
            base = base * 2;
        }
        return dec_value;
    }

    public long findGenerator(long p){
        // System.out.println("P is "+p);
        long start = (long)(Math.random()*p);
        for(long g = (start%p); g < p ;g++){
            if(isGenerator(g,p)) 
                return g;
        }
        if (isGenerator(2,p)) return 2;
        return -1;
    }

    public boolean isGenerator(long g,long p){
        System.gc();
        TreeSet<Long> output = new TreeSet<>();
        long res = 1 ;
        // System.out.println("G is "+g);
        for(long i =1;i < p; i++){
            res = (res * g) % p;
            System.out.println("round = "+i+" res => "+res);
            if(output.contains(res)){
                return false;
            }
            else  {
                output.add(res);
            }
        }
        return true;
        
    }
    public long [] getPlainText(String S,int n) throws UnsupportedEncodingException{
        byte[] Pbytes = S.getBytes("US-ASCII"); 
        StringBuilder binaryStr = new StringBuilder();
        for (byte b : Pbytes) {
            int val = b;
            for(int i = 0;i < 8;i++){
                binaryStr.append((val & 128) == 0 ? 0:1);
                val <<=1;
            }
        }
        System.out.print(binaryStr.toString());
        long msg [] = new long[(int) (Math.ceil(binaryStr.length()/(double) n))];
        for(int i = 0; i< msg.length;i++){
            String binary = binaryStr.substring(i*n,(i*n)+n);
            msg[i] = binaryToDec(binary);
        }
        return msg;
    }
    public String convertNumtoString(Long a,Long b){

        String a_binary = Long.toBinaryString(a);
        String b_binary = Long.toBinaryString(b);
        System.out.println(a_binary+""+b_binary);
        String cipherBinary = a_binary.concat(b_binary);
        System.out.println(cipherBinary.length());
        if(cipherBinary.length() % 8 != 0){
            int padCounter = cipherBinary.length() % 8;
            String padding = "";
            for (int i = 8;i  > padCounter ;i--) {
                padding += "0";
            }
            System.out.println(padding);
            cipherBinary = padding.concat(cipherBinary);
        } 
        System.out.println(cipherBinary.length());
        String res = "";
        for (int i = 0; i < cipherBinary.length(); i += 8) {
            int decimal_value = Integer.parseInt(cipherBinary.substring(i, i+8));
            res += (char)(decimal_value);
        }
        System.out.print(res);
        return null;
    }
    
}
