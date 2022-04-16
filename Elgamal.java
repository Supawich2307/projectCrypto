import java.math.BigInteger;

class Elgamal {
    private BigInteger p;   // public key
    private BigInteger g;   // public key
    private BigInteger y;   // public key
    private BigInteger u;   // private key

    public BigInteger getP() {
        return p;
    }
    public void setP(BigInteger p) {
        this.p = p;
    }
    public BigInteger getG() {
        return g;
    }
    public void setG(BigInteger g) {
        this.g = g;
    }
    public BigInteger getY() {
        return y;
    }
    public void setY(BigInteger y) {
        this.y = y;
    }

    public String Encrypt(BigInteger p, BigInteger g, BigInteger y, String plainText) {
        return null;
    }

    
    public String Decrypt(String cipherText) {
        return null;
    }

    
}