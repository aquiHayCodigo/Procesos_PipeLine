import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menú ejercicios procesos usando PipeLine: ");
        boolean menu = true;

        while (menu) {
            System.out.println("1. Suma de cuadrados, versión simple");
            System.out.println("2. Suma de cuadrados, usando Files");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("6. ");

            System.out.println("Ejecutar ejercicio: ");
            int opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:
                    sumaCuadrados();
                    break;

                case 2:
                    sumaCuadradosV2();
                    break;

                default:
                    break;
            }
        }

        sc.close();
    }

    public static void sumaCuadrados() {

    }

    public static void sumaCuadradosV2() {

    }

}
