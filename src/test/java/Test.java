public class Test {
    public static void main(String[] args) {
        displayStuff(4);
    }

    public static void displayStuff(int num) {
        if (num >= 0) {
            displayStuff(num - 1);
            for (int i = 1; i <= num; i++) {
                System.out.print(num + " ");
            }
            System.out.println("");
        }
    }
}
