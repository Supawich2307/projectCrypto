import java.util.Arrays;

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
    Pair cipher[];      // 
    EncryptedMessage () {

    }
    EncryptedMessage(int N,int B,int M,MediaType type, Pair[] cipher){
        this.N = N;
        this.M = M;
        this.B = B;
        this.type = type;
        this.cipher = cipher;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public Pair[] getCipher() {
        return cipher;
    }

    public void setCipher(Pair[] cipher) {
        this.cipher = cipher;
    }
    public int getB() {
        return B;
    }
    public void setB(int b) {
        B = b;
    }
    @Override
    public String toString() {
        String ciphertext = "";
        for (Pair pair : cipher) {
            ciphertext += pair.toString()+"\n";
        }
        return "EncryptedMessage [M=" + M + ", N=" + N  + ", type=" + type+"]\n"+ciphertext;

    }
   
}