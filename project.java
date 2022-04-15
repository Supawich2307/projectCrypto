package project;


import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
//import javafx.util.converter.BigIntegerStringConverter;


public class project {
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        // String str = "hello world";
        // int n = 14;
        // System.out.print(findPrime(str,n));
        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        System.out.println(findPrime(BigInteger.valueOf(10000000)));
        // LahmenTest(BigInteger.valueOf(1000));
        // ExtendedGCD g = new ExtendedGCD(BigInteger.valueOf(78),BigInteger.valueOf(29));
        // System.out.println(g.getGCD().compareTo(BigInteger.ONE));
    }
    // public static BigInteger findPrime(File f, BigInteger n){
    //     return findPrime(m);
    // }
    public static BigInteger findPrime(String S, int n) throws UnsupportedEncodingException{
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
        // System.out.print(m);
        return findPrime(m);
    }

    public static BigInteger findPrime(BigInteger m){
        
        
        if(m.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0  // 0=1 -> 1 | 1=0 -> -1 | 0=0 -> 0 if even
        && m.compareTo(BigInteger.valueOf(2)) != 0){ // check is it not two
            m = m.add(BigInteger.ONE);
        }
        if(LahmenTest(m) && LahmenTest(m.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)))){
            return m;
        }else{
            System.out.println("not safe prime > "+m);
            if(m.compareTo(BigInteger.valueOf(2)) == 0) return findPrime(m.add(BigInteger.ONE)); // ==2
            else return findPrime(m.add(BigInteger.valueOf(2)));                         // !=2
        }
    }

    public static Boolean LahmenTest(BigInteger n){
        Random randNum = new Random();
        int len = n.subtract(BigInteger.ONE).bitLength();
        
        for(int i = 0;i < 100;i++){
            BigInteger a = new BigInteger(len, randNum);
            a = a.mod(n);
            if(a.compareTo(BigInteger.valueOf(2)) == -1){
                a = a.add(BigInteger.valueOf(1));
            }
            System.out.println("a > "+a+" n > "+n);
            
            ExtendedGCD gcd = new ExtendedGCD(a,n);
            System.out.println(a+" gcd >> "+gcd.getGCD());
            if(gcd.getGCD().compareTo(BigInteger.ONE) == 1){ // gcd(a,n) > 1
                // System.out.println(a+" gcd >> "+gcd.getGCD());
                return false;
            }else{
                int exponent = ((n.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2))).intValue();
                System.out.println(a+" power > "+exponent);
                BigInteger x = a.pow(exponent).mod(n);
                System.out.println(a+" power > "+exponent+" "+x);
                if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0){ // x = 1 || x == n-1
                    continue;
                }else return false;
            }
        }
        return true;
    }
}

class ExtendedGCD{
    private BigInteger table[] = new BigInteger [8];
    private BigInteger g = new BigInteger("0"),a = new BigInteger("0"),b = new BigInteger("0");;
    private BigInteger n2_init;
    private BigInteger n1_init;
    ExtendedGCD(BigInteger n1,BigInteger n2){
        this.n1_init = n1;
        this.n2_init = n2;
        table[0] = n1;  //n1
        table[1] = n2;  //n2
        table[2] = n1.mod(n2); //r
        table[3] = (BigInteger) n1.divide(n2);  //q
        table[4] = new BigInteger("1"); //a1
        table[5] = new BigInteger("0"); //b1
        table[6] = new BigInteger("0"); //a2
        table[7] = new BigInteger("1"); //b2
        // printTable();
        findGCD();
        
    }
    public void printTable(){
        for(int i = 0;i<8;i++){
            System.out.print(table[i]+" ");
        }
        System.out.println();
    }
    public void findGCD(){
        
        BigInteger x = new BigInteger("0");
        if(table[2].compareTo(x) == 0){
            this.g = table[1];
            this.a = table[6];
            this.b = table[7];
            // System.out.println("g = "+g+" , "+"a = "+a+" , "+"b = "+b);
        }else if(table[2].compareTo(x) == 1){
            
            BigInteger t = table[6];
            table[6] = table[4].subtract(table[3].multiply(table[6])); //a2 = a1-q*a1
            table[4] = t; //a1 = a2
            t = table[7]; 
            table[7] = table[5].subtract(table[3].multiply(table[7])); //b2 = b1-q*b1
            table[5] = t; //b1 = b2
            table[0] = table[1]; //n1 = n2
            table[1] = table[2]; //n2 = r
            table[2] = table[0].mod(table[1]); //r = n1 mode n2
            table[3] = (BigInteger) (table[0].divide(table[1]));  // n2 = (int) n1/n2
            // printTable();
            findGCD();
        }
    }
    public BigInteger getGCD(){
        return this.g.mod(this.n2_init);
    }
    public BigInteger getinvert(){
        return b.mod(this.n2_init);
        // if(b.compareTo(x) == -1){
        //     b = n2_init.add(b); 
        //     return b.mod(this.n2_init);
        // }else{
        //     return b.mod(this.n2_init);
        // }
    }
}
