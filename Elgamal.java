class Elgamal {
    private long p;   // public key
    private long g;   // public key
    private long y;   // public key
    private long u;   // private key
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
        calculate.findGenerator(p);
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

    public long getU() {
        return u;
    }

    public String Encrypt(long p, long g, long y, String plainText) {
        return null;
    }

    
    public long Decrypt(long p, long u, Pair cipherText) {
        long a = cipherText.a;
        long b = cipherText.b;

        // X = b * a^(p-1-u)

        long difRes = calculate.powerModFast(a, (p-1-u), p);

        long X = b*difRes %p;

        return X;
    }

    
}

