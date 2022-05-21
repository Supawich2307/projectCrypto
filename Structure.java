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
    MediaType type;     //
    Pair[] cipher;    // 

}