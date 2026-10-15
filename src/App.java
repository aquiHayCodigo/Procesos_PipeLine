import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Menú ejercicios procesos usando PipeLine: ");
        boolean menu = true;

        while (menu) {
            System.out.println("1. Proceso Guardián");
            System.out.println("2. Contador Apariciones");
            System.out.println("3. Menú interactivo");
            System.out.println("4. Suma de cuadrados");
            System.out.println("5. Chapi Chapuzas");

            System.out.print("Ejecutar ejercicio: ");
            int opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:

                    break;

                case 4:
                    boolean ok = false;
                    while (!ok) {
                        System.out.print("Ejecución (S)imple o con (P)ipeline: ");
                        String version = sc.nextLine();
                        version.trim();

                        if (version.equalsIgnoreCase("s")) {
                            sumaCuadrados();
                            ok = true;
                        } else if (version.equalsIgnoreCase("p")) {
                            sumaCuadradosV2();
                            ok = true;
                        } else {
                            System.out.println("Esa letra de ké, vuelve a escribir la letra...");
                        }
                    }

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
        // File f1= new File("./bats/cuadrados.bat");
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "cuadrados.bat", "1","2", "3", "4");
        // File f2= new File("./bats/suma.bat");
        ProcessBuilder pb2 = new ProcessBuilder("suma.bat");

        try (   
            Process p1 = pb1.start();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));

            Process p2 = pb2.start();
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            ) {
            String linea;
            while ((linea = br1.readLine()) != null) {
                bw2.write(linea);
                bw2.newLine();
            }
            bw2.close();

            while ((linea = br2.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (Exception e) {
        }
    }

    public static void sumaCuadradosV2() {
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "cuadrados.bat", "1","2", "3", "4");
        pb1.redirectOutput();
        ProcessBuilder pb2 = new ProcessBuilder("suma.bat");
        pb2.redirectInput();
        List<ProcessBuilder> pbs = List.of(pb1, pb2);
        try {
            List<Process> procesos = ProcessBuilder.startPipeline(pbs);
            Process last = procesos.getLast();
            last.waitFor();
            InputStream iStream = last.getInputStream();
            InputStreamReader input = new InputStreamReader(iStream);
            BufferedReader bReader = new BufferedReader(input);

            String linea;
            while ((linea = bReader.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void chapuzas() {
        boolean m = true;
        while (m) {
            System.out.print("Ejecución (S)imple o con (P)ipeline: ");
            String version = sc.nextLine();

            String archvOrigen = "archivosTxt/textoMal.txt";
            Path rutaOrigen = Paths.get(archvOrigen);
            String archvDestino = "archivosTxt/textoBien.txt";
            Path rutaDestino = Paths.get(archvDestino);

            if (version == "S") {
                Trabajo t1 = new Trabajo(rutaOrigen, "en", rutaDestino);
                t1.corregirTraducirSimple();
            } else {
                Trabajo t1 = new Trabajo(rutaOrigen, "en", rutaDestino);
                t1.corregirTraducirPipeLine();
            }
        }

    }

}
