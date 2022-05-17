import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
class Elgamal {
    private long p;   // public key
    private long g;   // public key
    private long y;   // public key
    private long u;   // private key
    private int n;  //key size
    private Operation calculate = new Operation();
    public long getP() {
        return p;
    }
    public void setP(long p) {
        this.p = p;
        this.setG(calculate.findGenerator(p));
        this.u = (long)(Math.random()*p) - 1;
        //System.out.println("U is " + this.u);
        this.setY();
    }

    public long getG() {
        return g;
    }
    public void setG(long g) {
        this.g = g;
    }
    public long getY() {
        return y;
    }
    public void setY() {
        //System.out.println("Y ==> G is "+g);
        //System.out.println("Y ==> U is "+u);
        this.y = calculate.powerModFast(this.g, this.u, this.p); 
        //System.out.println("Y is "+y);
    }
    public long getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }

    public long getU() {
        return u;
    }
    
    public Pair<Long,Long> Encrypt(long p, long g, long y, long message) throws UnsupportedEncodingException{
        
        System.out.println("p : "+p+"\ng : "+g+"\ny : "+y+"\nm : "+message);
        long k,gcd,a,b1;

        //gen K
        do{
            k = (long)(Math.random()*p) - 1;
            gcd = calculate.calculateGCD(k, p-1);
            System.out.println("k is "+k+" GCD is "+gcd);
        }
        while(gcd != 1);
        // ArrayList<Long> b = new ArrayList<Long>();
        a = calculate.powerModFast(g, k, p);  //find a = g^k mod p
        b1 = calculate.powerModFast(y,k,p);


        // //find B
        // for(int i = 0;i < Pbytes.length;i+=n){
            
            // System.out.println("text "+pb);
        long b = (b1 *(message % p)) % p; //b = ((y^k mode p) * (x mod p)) mod p
        // System.out.println("b_value >>"+b_value);
        // b.add(b_value);
        // }

        System.out.print("a is "+a+"\nb is "+b+"\n");
        // for (int i = 0; i < b.size(); i++) {
        //     System.out.print(" "+b.get(i));
        // }
        
        return new Pair<Long,Long>(a,b);
    }

    
    public String Decrypt(String cipherText) {
        return null;
    }

    
}