import java.io.UnsupportedEncodingException;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String S = "Hello World";
        byte[] bytes = S.getBytes("TIS-620");  
        StringBuilder binaryStr = new StringBuilder();
        for(byte b : bytes){
            // System.out.println(b);
            int val = b;
            for(int i = 0;i < 8;i++){
                binaryStr.append((val & 128) == 0 ? 0:1);
                val <<=1;
            }
        }
        System.out.println("<<< "+binaryStr);
    
    }
}
