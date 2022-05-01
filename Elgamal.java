class Elgamal {
    private long p;   // public key
    private long g;   // public key
    private long y;   // public key
    private long u;   // private key

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
    public long getY() {
        return y;
    }
    public void setY(long y) {
        this.y = y;
    }

    public String Encrypt(long p, long g, long y, String plainText) {
        return null;
    }

    
    public String Decrypt(String cipherText) {
        return null;
    }

    
}