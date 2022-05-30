import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.TreeSet;



public class Operation {
    private long inverse;
    private long gcd;
    
    public int findLog(Long n){
        int ans = 0;
        while(n > 0) {
            n/=2;
            ans++;
        }
        return ans-1;
    }

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
        // System.out.println("Subtring is "+s+" dec val is "+m);
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
        // System.out.println("Subtring is "+blockStr+" dec val is "+m);
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
                long x = powerModFast(a, exponent, n);
                //System.out.println(a+" power > "+exponent+" "+x);
                // System.out.println("round: " + i + " a: "+a+" check n: "+n+" exponent " + exponent+" result : " + x);
                if(x==1 || x == (n-1)){ // x = 1 || x == n-1
                    continue;
                }else return false;
                
            }
            
        }
        // System.out.println(n + " is prime ");
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
        long resArr[] = new long[(int)p];
        long res = 1 ;
        // System.out.println("G is "+g);
        for(long i =1;i < p; i++){
            res = (res * g) % p;
            //System.out.println("round = "+i+" res => "+res);
            resArr[(int)res] ++;
            if(resArr[(int)res]>1)
                return false;
            //tree set
            /*if(output.contains(res)){
                System.out.println("round = "+i+" res => "+res);
                return false;
            }
            else  {
                output.add(res);
            }*/
        }
        System.gc();
        return true;
        
    }

    public long encode(String plaintext){ // convert plaintext to binary
        
        return binaryToDec(plaintext);
      
    }

    public String encodeToBinary(String plaintext) throws UnsupportedEncodingException{
        byte[] Pbytes = plaintext.getBytes("US-ASCII"); 
        String binaryStr = "";
        for (byte b : Pbytes) {
            int val = b;
            for(int i = 0;i < 8;i++){
                binaryStr += (val & 128) == 0 ? 0:1;
                val <<=1;
            }
        }
        return binaryStr;
    }

    public String[] encodeToBlock(String binaryText, int block_size) {
        int N = (int)Math.ceil((double)binaryText.length()/block_size);
        String [] blocks = new String[N];
        StringBuilder temp = new StringBuilder(binaryText);
        int i = 0;
        for( i = 0; i < N-1; i++) {
            blocks[i] = temp.substring(i*block_size, (i+1)*block_size);
        }
        
        blocks[N-1] = appendZero(temp.substring(i*block_size, temp.length()), block_size);    //last box must be padded 
        
        return blocks;
    }

    public String encode(Pair cipher,long key_size) { // cipher to binary
        String a_binary = Long.toBinaryString(cipher.a);
        String b_binary = Long.toBinaryString(cipher.b);
        
        if(a_binary.length() < key_size){
            int padCounter = (int) key_size - a_binary.length();
            String padding = "";
            for (int i = 1;i <= padCounter;i++) {
                padding += "0";
            }
            
            a_binary = padding.concat(a_binary);
        }
        if(b_binary.length() < key_size){
            int padCounter = (int) key_size - b_binary.length();
            String padding = "";
            for (int i = 1;i <= padCounter;i++) {
                padding += "0";
            }
            
            b_binary = padding.concat(b_binary);
        }

        String cipherBinary  = a_binary.concat(b_binary);

        

        return cipherBinary;
    }

    public String encode(SignedMessage<Long, Pair> signedMessage) { // signedMessage to binary
        return null;
    }

    public SignedMessage<Long, Pair> decodeSignedMessage(String signedMsg) { // decode from binary to object for decrypt
        return null;
    }

    public Pair[] readCipherText(String message,int numBlock,int key_size){
        String [] cipherTextRaw = message.split(" ");
        Pair [] cipherTextDec = new Pair [numBlock];
        for (int i = 0;i < numBlock;i++) {
            String cipher = appendZero(cipherTextRaw[i], key_size);
            cipherTextDec[i] = decodeMessage(cipher);
            
        }
        return cipherTextDec;
    }

    //to decode from binary to object pair for decrypt
    public Pair decodeMessage(String cipher) {     // to decode from binary to object pair for decrypt
        String a = cipher.substring(0,cipher.length()/2);
        String b = cipher.substring(cipher.length()/2,cipher.length());
        
        return new Pair(binaryToDec(a),binaryToDec(b));
    }

    public String decodeToMessage(long [] plainDec,EncryptedMessage encMsg){
        String plaintext = "";
        for (long l : plainDec) {
            String plaintextBinary = Long.toBinaryString(l);
            
            plaintext += paddingZero(plaintextBinary, encMsg.getB()-1);
            
        }
        
        plaintext = decodeToPlaintext(plaintext.substring(0, encMsg.getN()));
        
        
        return plaintext;
    }

    public String decodeToFile(long [] plainDec, EncryptedMessage encMsg, String Filename){
        String plaintext = "";
        for (long l : plainDec) {
            String plaintextBinary = Long.toBinaryString(l);
            
            plaintext += paddingZero(plaintextBinary, encMsg.getB()-1);
            
        }
        
        plaintext = plaintext.substring(0, encMsg.getN());
        
        
        return plaintext;
    }

    public void createFile(String data, String Filepath) throws IOException{
        RandomAccessFile file = new RandomAccessFile(Filepath, "rw");  
        int index = 0;
        for(int i = 0;i < data.length();i+=8){
            file.seek(index++); 
            byte cipher_dec = (byte) binaryToDec(data.substring(i, i+8));
            file.write(cipher_dec); 
        }
        file.close();  
    }

    public String decodeToPlaintext(String binary) { // convert dec to plaintext
        
        String plaintext = "";
        int i ;
        for(i = binary.length();i > 7;i -= 8){
            int ascii = (int) binaryToDec(binary.subSequence(i-8 ,i).toString());
            char c = (char) ascii;
            plaintext = c + plaintext; 
        }
        if(i < 8){
            int ascii = (int) binaryToDec(binary.subSequence( 0,i).toString());
            char c = (char) ascii;
            plaintext = c + plaintext; 
        }
        return plaintext;
    }

    public String paddingZero(String text, int size) {
        for(int i = text.length(); i < size; i++) {
            text = "0"+text;
        }
        return text;
    }

    public String appendZero(String text, int size) {
        for(int i = text.length(); i < size; i++) {
            text = text + "0";
        }
        return text;
    }

    public void addPublicKey (Hashtable<String, PublicKey<Long>> pubKeyList, String filename)
    throws IOException
    {
        Scanner fileIn = new Scanner(new File(filename));
        String name = fileIn.next();
        int key_size = fileIn.nextInt();
        long p = fileIn.nextLong();
        long g = fileIn.nextLong();
        long y = fileIn.nextLong();
        pubKeyList.put(name, new PublicKey<Long>(p, g, y, key_size));
    }

    public void writeMessage(EncryptedMessage msg, String filename) throws IOException{
        RandomAccessFile out = new RandomAccessFile(filename,"rw");
        out.writeInt(msg.getN());
        StringBuilder ciphertext = new StringBuilder();
        for(int i = 0; i < msg.getM() - 1 ; i++){
            ciphertext.append(encode(msg.getCipher()[i], msg.getB()));
        }
        ciphertext.append(encode(msg.getCipher()[msg.getM()-1], msg.getB()));
        String ciphertextpadding = appendZero(ciphertext.toString(), ciphertext.length()+(8-ciphertext.length() % 8));
        int index = 4;
        for(int i = 0;i < ciphertextpadding.length();i+=8){
            out.seek(index++); 
            byte cipher_dec = (byte) binaryToDec(ciphertextpadding.substring(i, i+8));
            out.write(cipher_dec); 
        }
        out.close();
    }

    public void writeSignedCipher(SignedMessage<EncryptedMessage, Pair> signedmsg, String filename) throws IOException{
        PrintWriter out = new PrintWriter(filename);
        EncryptedMessage msg = signedmsg.message;
        out.write(msg.getN()+" ");
        out.write(msg.getB()+" ");
        out.write(msg.getM()+" ");
        String type = msg.getType() == MediaType.FILE? "FILE": "PLAINTEXT";
        out.write(type+"\n");
        StringBuilder ciphertext = new StringBuilder();
        for(int i = 0; i < msg.getM() - 1 ; i++){
            ciphertext.append(encode(msg.getCipher()[i], msg.getB())+" ");
        }
        ciphertext.append(encode(msg.getCipher()[msg.getM()-1], msg.getB())+"\n");
        out.write(ciphertext.toString());
        out.write(encode(signedmsg.signature, msg.getB()));
        out.close();
    }
    
    public EncryptedMessage readMessage(String filename) throws IOException{
        Scanner sc = new Scanner(new File(filename));
        int N = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        MediaType type =  MediaType.valueOf(sc.next());
        sc.nextLine();
        //read from file to (a,b)
        Pair[] cipher = readCipherText(sc.nextLine(),M,B); 

        // convert file to object 
        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        return encMsg;
    }
        
    public EncryptedMessage readCipher(String filename, int key_size) throws IOException{
        DataInputStream data_in = new DataInputStream(
            new BufferedInputStream(
                new FileInputStream(new File(filename))));
        StringBuilder binary_size = new StringBuilder();
        for(int i = 0; i  < 4; i++) {
            binary_size.append(paddingZero(Integer.toBinaryString(data_in.read()), 8));
        }
        int a = 0;
        int N = (int)binaryToDec(binary_size.toString());
        int B = key_size;
        int M = 0;
        MediaType type =  MediaType.FILE;
        StringBuilder cipherText = new StringBuilder();
        
        while(a != -1) {
            try {
                    a = data_in.read();
                    if(a == -1) {
                        break;
                    }
                String b = String.format("%8s", Integer.toBinaryString(a)).replace(' ', '0');
                cipherText.append(b);
            }
            catch(EOFException eof) {
                System.out.println ("End of File");
                break;
            }
        }
        
        int cipher_size = cipherText.length() - (cipherText.length()%B);

        String actual_cipher = cipherText.substring(0, cipher_size);
        
        M = cipher_size/(B*2);

        Pair[] cipher = new Pair[M]; 
        cipherText = new StringBuilder(actual_cipher);

        for(int i = 0; i < M; i++) {
            cipher[i] = decodeMessage(cipherText.substring(i*B*2, (i+1)*B*2));
        }
        // convert file to object 
        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        return encMsg;
    }
   
    public SignedMessage<EncryptedMessage, Pair> readSingedCipher(String filename) throws IOException{
        Scanner sc = new Scanner(new File(filename));
        int N = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        MediaType type =  MediaType.valueOf(sc.next());
        sc.nextLine();
        //read from file to (a,b)
        Pair[] cipher = readCipherText(sc.nextLine(),M,B); 

        // convert file to object 
        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        Pair signature = decodeMessage(sc.next());
        return new SignedMessage<>(encMsg, signature);
    }

    public SignedMessage<EncryptedMessage, Pair> readCipherSignature(String filename, int key_size) throws IOException{
        DataInputStream data_in = new DataInputStream(
            new BufferedInputStream(
                new FileInputStream(new File(filename))));
        StringBuilder binary_size = new StringBuilder();
        for(int i = 0; i  < 4; i++) {
            binary_size.append(paddingZero(Integer.toBinaryString(data_in.read()), 8));
        }
        int a = 0;
        int N = (int)binaryToDec(binary_size.toString());
        int B = key_size;
        int M = 0;
        MediaType type =  MediaType.FILE;
        StringBuilder cipherText = new StringBuilder();
        
        while(a != -1) {
            try {
                    a = data_in.read();
                    if(a == -1) {
                        break;
                    }
                String b = String.format("%8s", Integer.toBinaryString(a)).replace(' ', '0');
                cipherText.append(b);
            }
            catch(EOFException eof) {
                System.out.println ("End of File");
                break;
            }
        }

        System.out.println("cipherText"+cipherText.toString());

        StringBuilder signature ;
        int actual_sign_length = key_size*2;
        int  sign_length = actual_sign_length + (8-actual_sign_length%8);
        int sign_start = cipherText.length()-sign_length;
        signature = new StringBuilder(cipherText.substring(sign_start, cipherText.length()));
        String actual_sign = signature.substring(0, 2*key_size);
        Pair digital_signature = decodeMessage(actual_sign);

        // trim new cipher
        cipherText = new StringBuilder(cipherText.subSequence(0, sign_start));
        int cipher_size = cipherText.length() - (cipherText.length()%B);
        String actual_cipher = cipherText.substring(0, cipher_size);
        System.out.println("cipherText trim"+cipherText.toString());
        System.out.println("signature "+actual_sign);
        
        M = cipher_size/(B*2);

        Pair[] cipher = new Pair[M]; 
        cipherText = new StringBuilder(actual_cipher);

        for(int i = 0; i < M; i++) {
            cipher[i] = decodeMessage(cipherText.substring(i*B*2, (i+1)*B*2));
        }
        // convert file to object 
        EncryptedMessage encMsg = new EncryptedMessage(N,B,M,type,cipher);
        
        SignedMessage<EncryptedMessage, Pair> res = new SignedMessage<>(encMsg, digital_signature);
        return res;
    }
    
    public long hashFunction(String msg){
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(msg.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

        return getDecimal(sha1);
    }
    
    public long getDecimal(String hex){  
        String digits = "0123456789ABCDEF";  
            hex = hex.toUpperCase();  
            long val = 0;  
            for (int i = 0; i < hex.length(); i++) {  
                char c = hex.charAt(i);  
                long d = digits.indexOf(c);  
                val = 16*val + d;  
            }  
        return val;  
    }  
}
