public class Main {
    public static void main(String[] args) {
        String sampleString = "string";
        //Pri x treba špecifikovať dátový typ
        for (char x : sampleString.toCharArray()) {
            System.out.print(x);
        }
    }
}