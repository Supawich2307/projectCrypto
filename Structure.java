class Pair 
{
    long a;
    long b;
    public Pair(long value1, long value2)
    {
        this.a = value1;
        this.b = value2;
    }
    @Override
    public String toString() {
        return "Pair [a=" + a + ", b=" + b + "]";
    }
}

class SignedMessage  <T, K>
{
       T message;
       K signature;
       public SignedMessage(T messsage, K signature) {
            this.message = messsage;
            this.signature = signature;
       }
    @Override
    public String toString() {
        return "SignedMessage [message=" + message + ", signature=" + signature + "]";
    }
}

class PublicKey <T>{
    T p;
    T g;
    T y;
    int key_size;

    public PublicKey(T p, T g, T y, int key_size) {
        this.p = p;
        this.g = g;
        this.y = y;
        this.key_size = key_size;
    }

    @Override
    public String toString() {
        return "PublicKey [g=" + g + ", p=" + p + ", y=" + y + ", key_size=" +key_size+"]";
    }

}

enum MediaType {
    PLAINTEXT,
    FILE
}
class EncryptedMessage {
    int N;              //actual size of plaintext
    int M;              // block number
    int B;              // block size
    MediaType type;     //
    String cipher;    // 
    EncryptedMessage(int N,int B,int M,MediaType type,String cipher){
        this.N = N;
        this.M = M;
        this.B = B;
        this.type = type;
        this.cipher = cipher;
    }
    @Override
    public String toString(){
        return null;
    }
    public int getN(){
        return this.N;
    }  
    public int getB(){
        return this.B;
    }
    public int getM(){
        return this.M;
    }  
    public MediaType getType(){
        return this.type;
    }  
    public String getcipher(){
        return this.cipher;
    }  


}