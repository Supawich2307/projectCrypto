import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    }

    public long getG() {
        return g;
    }
    public void setG(long g) {
        this.g = g;
    }

    public void setG() {
        this.g = calculate.findGenerator(p);
    }

    public long getY() {
        return y;
    }
    public void setY() {
        this.y = calculate.powerModFast(this.g, this.u, this.p);
    }

    public void setU(long u) {
        this.u = u;
    }

    public void setU() {
        this.u = (long)(Math.random()*p) - 1;
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
    
    public  Pair Encrypt(long p, long g, long y, long message) throws UnsupportedEncodingException{
        
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
        return new Pair(a,b);
    }

    
    public long Decrypt(long p, long u, Pair cipherText) {
        long a = cipherText.a;
        long b = cipherText.b;

        // X = b * a^(p-1-u)

        long difRes = calculate.powerModFast(a, (p-1-u), p);

        long X = b*difRes %p;

        return X;
    }

    public SignedMessage<Long, Pair> signMessage(long msg) {
        long k, gcd;
        //gen K
        do{
            k = (long)(Math.random()*p) - 1;
            gcd = calculate.calculateGCD(k, p-1);
        }
        while(gcd != 1);
        k = 7;
        System.out.println("k is "+k+" GCD is "+gcd);

        long r = calculate.powerModFast(this.g, k, this.p);  
        System.out.println("r "+r);       //compute r = g^k mod p
        long inverseK = calculate.calculateInverse((this.p-1), k);  //K inverse mod p-1
        System.out.println("inverseK "+ inverseK+" p -1 "+(this.p-1));   
        long s = inverseK*((msg-((this.u*r)%(this.p-1)))%(this.p-1)) % (this.p - 1);                           // inverseK * (X - x*r)
        
        return new SignedMessage<Long, Pair>(msg, new Pair(r,s));
    }
}

