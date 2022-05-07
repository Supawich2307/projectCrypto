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
        this.setG(calculate.findGenerator(p));
        this.u = (long)(Math.random()*p) - 1;
        //System.out.println("U is " + this.u);
        this.setY();
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
    public void setY() {
        //System.out.println("Y ==> G is "+g);
        //System.out.println("Y ==> U is "+u);
        this.y = calculate.powerModFast(this.g, this.u, this.p);
        //System.out.println("Y is "+y);
    }



    public long getU() {
        return u;
    }
    
    public String Encrypt(long p, long g, long y, String plainText) {
        return null;
    }

    
    public String Decrypt(String cipherText) {
        return null;
    }

    
}