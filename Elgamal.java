import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public void setY(long y){
        this.y = y;
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

    public void writePublicKey () throws IOException{
        FileWriter  outb = new FileWriter (name+".pub");  
        outb.write(name+" ");
        outb.write(n+"\n");
        outb.write(p+"\n");
        outb.write(g+"\n");
        outb.write(y+"\n");
        outb.close();
    }

    public void writePrivateKey () throws IOException{
        FileWriter  outb = new FileWriter (name+".pri");  
        outb.write(name+" ");
        outb.write(n+"\n");
        outb.write(p+"\n");
        outb.write(g+"\n");
        outb.write(u+"\n");
        outb.write(y+"\n");
        outb.close();
    }

    public void setKeyFromFile (String filename) throws IOException
    {
        Scanner fileIn = new Scanner(new File(filename));
        this.name = fileIn.next();
        this.n = fileIn.nextInt();
        this.p = fileIn.nextLong();
        this.g = fileIn.nextLong();
        this.u = fileIn.nextLong();
        this.y = fileIn.nextLong();
        fileIn.close();
        System.out.println("Set "+name+" key already");
    }

    public  Pair Encrypt(long p, long g, long y, long message) throws UnsupportedEncodingException{
        
        //System.out.println("p : "+p+"\ng : "+g+"\ny : "+y+"\nm : "+message);
        long k,gcd,a,b1;

        //gen K
        do{
            k = (long)(Math.random()*p) - 1;
            gcd = calculate.calculateGCD(k, p-1);
            //System.out.println("k is "+k+" GCD is "+gcd);
        }
        while(gcd != 1);
        // ArrayList<Long> b = new ArrayList<Long>();
        a = calculate.powerModFast(g, k, p);  //find a = g^k mod p
        b1 = calculate.powerModFast(y,k,p);


        long b = (b1 *(message % p)) % p; //b = ((y^k mode p) * (x mod p)) mod p
       
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
    
    public boolean Verify(SignedMessage<String, Pair> signedMessage, PublicKey<Long> sender ){
        long X = Math.floorMod(calculate.hashFunction(signedMessage.message), (sender.p-1));
        return Verify(X, signedMessage.signature, sender);
    }

    public boolean VerifyCipher(SignedMessage<EncryptedMessage, Pair> signedMessage, PublicKey<Long> sender ){
        StringBuilder ciphertext = new StringBuilder();
        for(int i = 0; i < signedMessage.message.getM() ; i++){
            ciphertext.append(calculate.encode(signedMessage.message.getCipher()[i], signedMessage.message.getB()));
        }
        long X = Math.floorMod(calculate.hashFunction(ciphertext.toString()), (sender.p-1));
        return Verify(X, signedMessage.signature, sender);
    }

    public boolean Verify(Long msg, Pair signature , PublicKey<Long> sender ){
        System.out.println("msg "+msg+" r "+signature.a+" s "+signature.b);
        long res_msg = calculate.powerModFast(sender.g, msg, sender.p);  //calculate from message
        long y_pow_r = calculate.powerModFast(sender.y, signature.a , sender.p);
        long r_pow_s = calculate.powerModFast(signature.a, signature.b,sender.p);
        System.out.println(" y pow r: " + y_pow_r+" r pow s: "+r_pow_s);
        long res_sign = (y_pow_r * r_pow_s) % sender.p; //calculate from signature
        System.out.println(res_msg+" compare to "+res_sign);
        if(res_msg == res_sign){
            return true;
        }else{
            return false;
        }
    }
//long X,long r ,long s
    
    public SignedMessage signMessage(String msg) {
        long X =  Math.floorMod(calculate.hashFunction(msg), (this.p - 1)) ;
        Pair signature = getSignature(X);
        SignedMessage<String, Pair> signedMessage = new SignedMessage<>(msg, signature);
        return signedMessage;
    }

    public SignedMessage signMessage(EncryptedMessage msg) {
        StringBuilder ciphertext = new StringBuilder();
        for(int i = 0; i < msg.getM() ; i++){
            ciphertext.append(calculate.encode(msg.getCipher()[i], msg.getB()));
        }
        long X =  Math.floorMod(calculate.hashFunction(ciphertext.toString()), (this.p - 1));
        Pair signature = getSignature(X);
        SignedMessage<EncryptedMessage, Pair> signedMessage = new SignedMessage<>(msg, signature);
        return signedMessage;
    }

    public Pair getSignature(long msg) {
        System.out.println("Sigining ... ");
        long k, gcd;
        msg = msg % (this.p-1);
        //gen K
        do{
            k = (long)(Math.random()*(p-1));
            if (k==0)
                k++;
            System.out.println("random "+k);
            gcd = calculate.calculateGCD(p-1, k);
        }
        while(gcd != 1);
        System.out.println("k is "+k+" GCD is "+gcd);

        long r = calculate.powerModFast(this.g, k, this.p);  
               //compute r = g^k mod p
        long inverseK = calculate.calculateInverse((this.p-1), k);  //K inverse mod p-1
        System.out.println("inverseK "+ inverseK+" p -1 "+(this.p-1));   
        long cal_msg = Math.floorMod(msg-((this.u*r)%(this.p-1)), (this.p-1));
        long s = inverseK*(cal_msg) % (this.p - 1);                           // inverseK * (X - x*r)
        System.out.println("msg "+msg+" r "+r+" s "+s);

        
        System.out.println("-- Sign finished -- ");
        return new Pair(r,s);
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

    public EncryptedMessage encryptMessage(File f, PublicKey<Long> receiver) 
    throws IOException{

        int block_size = calculate.findLog(receiver.p);
        System.out.println("p is "+receiver.p+" block_size is "+block_size);
        
        String binaryText = calculate.readFile(f);
        System.out.println("size is "+binaryText.length()+"text to binary is"+binaryText);
        int actual_size = binaryText.length();
        System.out.println("actual size of plaintext is "+actual_size);

        
        String[] blocks = calculate.encodeToBlock(binaryText, block_size);
        int M = blocks.length;      // M is number of blocks
        Pair[] cipher_dec = new Pair[M];
        for(int i = 0; i < M; i++) {
            //System.out.println("message block"+ (i+1) +"is"+blocks[i]);
            cipher_dec[i] = Encrypt(receiver.p, receiver.g, receiver.y, calculate.binaryToDec(blocks[i]));
        }
        System.out.println("no of block is "+M);

        block_size = receiver.key_size;

        EncryptedMessage real_cipher = new EncryptedMessage();
        real_cipher.setM(M);
        real_cipher.setN(actual_size);
        real_cipher.setCipher(cipher_dec);
        real_cipher.setType(MediaType.FILE);
        real_cipher.setB(block_size);
        
        return real_cipher;
    }
}

