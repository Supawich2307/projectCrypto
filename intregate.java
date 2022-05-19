import java.io.UnsupportedEncodingException;

public class intregate {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Operation operate = new Operation();
        /* SENDER */
        Elgamal Alice = new Elgamal();
        Alice.setP(operate.findPrime("Bob is here", 26));
        Alice.setG();
        Alice.setU();
        Alice.setY();
        System.out.println("Alice : "+Alice.toString());
        

        /* RECIVER */
        Elgamal Bob = new Elgamal();
        Bob.setP(operate.findPrime("Hello World", 26));
        Bob.setG();
        Bob.setU();
        Bob.setY();
        System.out.println("Bob : "+Bob.toString());

        System.out.println("Encrypt");
        Pair cipher = Alice.Encrypt(Bob.getP(), Bob.getG(), Bob.getY(), operate.encode("BOB"));
        System.out.println("cipher pair : "+cipher);
        String encode_cipher = operate.encode(cipher, 26);
        System.out.println("cipher message : "+encode_cipher);

        System.out.println("Decrypt");
        Pair cipher_alice = operate.decodeMessage(encode_cipher);
        System.out.println("sub cipher :"+cipher_alice);
        long msg = Bob.Decrypt(Bob.getP(), Bob.getP(), cipher_alice);
        String plaintext_alice = operate.decodeToPlaintext(msg);
        System.out.println("plaintext Alice :"+plaintext_alice);

    }
}
