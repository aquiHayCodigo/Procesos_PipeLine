import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            System.out.println("5. Chapi Chapuzas");
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

                case 5:
                    chapuzas();

                default:
                    break;
            }
        }

        sc.close();
    }

    public static void sumaCuadrados() {
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "cuadrados.bat", "1, 2, 3, 4");
        ProcessBuilder pb2 = new ProcessBuilder("suma.bat");

        try (Process p1 = pb1.start();
            Process p2 = pb2.start();) {

        } catch (Exception e) {
        }
    }

    public static void sumaCuadradosV2() {

    }

    public static void chapuzas() {
        // File file= new File("textoMal.txt");

        // Path rutaOrigen= Paths.get("textoMal.txt");
        // Path rutaDestino= Paths.get("textoBien.txt");
        // Trabajo t1= new Trabajo(rutaOrigen, "es", rutaDestino);
        // t1.corregirTraducir();

        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "chek.bat", "textoMal.txt");
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "translate.bat");

        try (Process p1 = pb1.start();
            BufferedReader  br1= new BufferedReader(new InputStreamReader(p1.getInputStream()));

            Process p2= pb2.start();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
            // BufferedReader br2= new BufferedReader(new InputStreamReader(p2.getInputStream()));
        ) {
            p1.waitFor();
            String linea;
            while((linea=br1.readLine())!=null){
                bw.write(linea);
            }



        } catch (Exception e) {
        } 
    }

}
