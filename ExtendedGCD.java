import java.math.BigInteger;

class ExtendedGCD{
    private BigInteger table[] = new BigInteger [8];
    private BigInteger g = new BigInteger("0"),a = new BigInteger("0"),b = new BigInteger("0");;
    private BigInteger n2_init;
    private BigInteger n1_init;
    ExtendedGCD(BigInteger n1,BigInteger n2){
        this.n1_init = n1;
        this.n2_init = n2;
        table[0] = n1;  //n1
        table[1] = n2;  //n2
        table[2] = n1.mod(n2); //r
        table[3] = (BigInteger) n1.divide(n2);  //q
        table[4] = new BigInteger("1"); //a1
        table[5] = new BigInteger("0"); //b1
        table[6] = new BigInteger("0"); //a2
        table[7] = new BigInteger("1"); //b2
        // printTable();
        findGCD();
        
    }
    public void printTable(){
        for(int i = 0;i<8;i++){
            System.out.print(table[i]+" ");
        }
        System.out.println();
    }
    public void findGCD(){
        
        BigInteger x = new BigInteger("0");
        if(table[2].compareTo(x) == 0){
            this.g = table[1];
            this.a = table[6];
            this.b = table[7];
            // System.out.println("g = "+g+" , "+"a = "+a+" , "+"b = "+b);
        }else if(table[2].compareTo(x) == 1){
            
            BigInteger t = table[6];
            table[6] = table[4].subtract(table[3].multiply(table[6])); //a2 = a1-q*a1
            table[4] = t; //a1 = a2
            t = table[7]; 
            table[7] = table[5].subtract(table[3].multiply(table[7])); //b2 = b1-q*b1
            table[5] = t; //b1 = b2
            table[0] = table[1]; //n1 = n2
            table[1] = table[2]; //n2 = r
            table[2] = table[0].mod(table[1]); //r = n1 mode n2
            table[3] = (BigInteger) (table[0].divide(table[1]));  // n2 = (int) n1/n2
            // printTable();
            findGCD();
        }
    }
    public BigInteger getGCD(){
        return this.g.mod(this.n2_init);
    }
    public BigInteger getinvert(){
        return b.mod(this.n2_init);
        // if(b.compareTo(x) == -1){
        //     b = n2_init.add(b); 
        //     return b.mod(this.n2_init);
        // }else{
        //     return b.mod(this.n2_init);
        // }
    }
}