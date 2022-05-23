import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    private String name;

    public Elgamal() {
        this.name ="unknown";
    }

    public Elgamal(String name) {
        this.name = name;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    @Override
    public String toString(){
        return "\nP is "+p+"\nG is "+g+"\nU is "+u+"\nY is "+y;
    }
    
    public void genKey(String source, int key_size) throws UnsupportedEncodingException {
        this.p = calculate.findPrime(source, key_size);
        setG();
        setU();
        setY();
        setN(key_size);
    }

    public void genKey(File source, int key_size) throws IOException {
        this.p = calculate.findPrime(source, key_size);
        setG();
        setU();
        setY();
        setN(key_size);
    }

    public void writePublicKey (String filename) throws IOException{
        PrintWriter out = new PrintWriter(filename);
        out.write(name+" ");
        out.write( n+"\n");
        out.write(calculate.paddingZero(Long.toBinaryString(p), n)+"\n");
        out.write(calculate.paddingZero(Long.toBinaryString(g), n)+"\n");
        out.write(calculate.paddingZero(Long.toBinaryString(y), n)+"");
        out.close();
    }

    public void writePrivateKey () throws IOException{
        PrintWriter out = new PrintWriter(name+".pri");
        out.write(calculate.paddingZero(Long.toBinaryString(u), n)+"");
        out.close();
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
        // for (int i = 0; i < b.size(); i++) {
        //     System.out.print(" "+b.get(i));
        // }
        return new Pair(a,b);
    }

    public EncryptedMessage encryptMessage (String plaintext, PublicKey<Long> receiver) 
    throws UnsupportedEncodingException { // Send message
        int block_size = calculate.findLog(receiver.p);

        System.out.println("plaintext is"+plaintext);
        String binaryText = calculate.encodeToBinary(plaintext);
        System.out.println("text to binary is"+binaryText);
        int actual_size = binaryText.length();
        System.out.println("actual size of plaintext is "+actual_size);

        String[] blocks = calculate.encodeToBlock(binaryText, block_size);
        int M = blocks.length;      // M is number of blocks
        Pair[] cipher_dec = new Pair[M];
        for(int i = 0; i < M; i++) {
            System.out.println("message block"+ (i+1) +"is"+blocks[i]);
            cipher_dec[i] = Encrypt(receiver.p, receiver.g, receiver.y, calculate.binaryToDec(blocks[i]));
        }

        block_size = receiver.key_size;

        EncryptedMessage real_cipher = new EncryptedMessage();
        real_cipher.setM(M);
        real_cipher.setN(actual_size);
        real_cipher.setCipher(cipher_dec);
        real_cipher.setType(MediaType.PLAINTEXT);
        real_cipher.setB(block_size);
        
        return real_cipher;

    }
    
    public long Decrypt(long p, long u, Pair cipherText) {
        long a = cipherText.a;
        long b = cipherText.b;
        

        // X = b * a^(p-1-u)

        long difRes = calculate.powerModFast(a, (p-1-u), p);

        long X = b*difRes %p;

        return X;
    }

    public long DigitalSignature(){

        return 0;
    }
    public boolean Verify(long p, long y,long g,SignedMessage <Long ,Pair> msg ){
        long sender = calculate.powerModFast(g, msg.message, p);
        long signature = (calculate.powerModFast(y, msg.signature.a , p) * calculate.powerModFast(msg.signature.a, msg.signature.b,p) ) % p;
        System.out.println(sender+" "+signature);
        if(sender == signature){
            return true;
        }else{
            return false;
        }
    }
//long X,long r ,long s
    
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

    public long[] DecryptMessage(Pair[] Message,int numBlock){ //Pair long to []long
        //convert
        long[] plaintext = new long [numBlock];
        for(int i = 0;i < plaintext.length;i++){
            Pair msg = Message[i];
            plaintext[i] = Decrypt(this.p, this.u,msg);
            System.out.println(plaintext[i]);
        }
        
        return plaintext;
    }
}

