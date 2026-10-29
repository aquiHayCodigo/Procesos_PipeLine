import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Menú ejercicios: ");
        boolean menu = true;
        boolean ok = false;

        while (menu) {
            System.out.println("- - - - - - - - - - - - - -");
            System.out.println("1. Proceso Guardián");
            System.out.println("2. Contador Apariciones");
            System.out.println("3. Menú interactivo");
            System.out.println("4. Suma de cuadrados");
            System.out.println("5. Chapi Chapuzas");
            System.out.println("6. Contador lineas tasklist");
            System.out.println("- - - - - - - - - - - - - -");

            System.out.print("Ejecutar ejercicio: ");
            int opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1:
                    guardian();
                    break;

                case 2:
                    ok = false;
                    while (!ok) {
                        System.out.print("Ejecución (S)imple o con (P)ipeline: ");
                        String version = sc.nextLine();
                        version.trim();

                        if (version.equalsIgnoreCase("s")) {
                            contador();
                            ok = true;
                        } else if (version.equalsIgnoreCase("p")) {
                            contador4();
                            ok = true;
                        } else {
                            System.out.println("Esa letra de ké, vuelve a escribir la letra...");
                        }
                    }
                    break;

                case 3:
                    menu();
                    break;

                case 4:
                    ok = false;
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
                    break;

                case 6:
                    tasklistSimple2();
                    break;

                default:
                    break;
            }
        }

        sc.close();
    }

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void guardian() {
        limpiarPantalla();
        navegador();
        while (true) {
            ProcessBuilder tasklist = new ProcessBuilder("cmd", "tasklist");
            ProcessBuilder find = new ProcessBuilder("cmd", "/c", "findstr duckduckgo");
            try (
                    Process p2 = tasklist.start();
                    BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));

                    Process p3 = find.start();
                    PrintWriter bw = new PrintWriter(p3.getOutputStream());
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(p3.getInputStream()));) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    bw.write(linea);
                    if (linea.equals("duckduckgo")) {
                        System.out.println("duckduckgoooooo");
                    }
                }
                String salida3 = br2.readLine();
                if (salida3 == null) {
                    navegador();
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static void navegador() {
        ProcessBuilder navegador = new ProcessBuilder("cmd", "/c",
                "start duckduckgo https://www.youtube.com/watch?v=Y4IO3GwsFPs");
        try (Process p1 = navegador.start();) {
            int exitCode = p1.waitFor();
            if(exitCode==0){
                System.out.println("Se ha abierto DuckduckGO");
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void contador() {
        limpiarPantalla();
        String bat = "contar_palabras.bat";
        String texto = "archivo.txt";
        String palabra = "robar";
        ProcessBuilder pb1 = new ProcessBuilder(bat, texto, palabra);

        String resultado = "volcado.txt";
        File file = new File(resultado);
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "more > ", resultado);
        System.out.println(file.getParentFile());
        pb2.directory(file.getParentFile());
        System.out.println("-----------");
        try (
                Process p1 = pb1.start();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                Process p2 = pb2.start();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
        // BufferedReader brFile= new BufferedReader(new FileReader(volcado));
        ) {
            String linea;
            while ((linea = br1.readLine()) != null) {
                bw.write(linea);
                bw.flush();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void contador2() { 
        limpiarPantalla();
        String bat = "contar_palabras.bat";
        String texto = "archivo.txt";
        String palabra = "robar";
        ProcessBuilder pb = new ProcessBuilder(bat, texto, palabra);

        String resultado = "resultado.txt";
        File fileR = new File(resultado);

        try (
                Process p = pb.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileR)); 
        ) {
            p.waitFor();
            String linea = br.readAllAsString();
            bw.write(linea);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void contador3 () throws Exception{
        limpiarPantalla();
        String bat = "contar_palabras.bat";
        String palabra = "que";
        List<String> archivos = List.of("archivo.txt", "archivo2.txt", "archivo3.txt");

        ProcessBuilder pb=new ProcessBuilder("cmd", "/c");
        // BUCLE EJECUTANDO VARIOS PB
        for (String archivo : archivos) {
            pb = new ProcessBuilder(bat, archivo, palabra);
        }

        String resultado = "resultado3.txt";
        File fResultado = new File(resultado);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(fResultado));
        if(fResultado.exists()){
            fResultado.delete();
        }
        Process p =pb.start();
        int exitCode = p.waitFor(); // p.exitValue();
        if(exitCode==0){
            System.err.println("El proceso finalizó con código de error: " + exitCode);
        }
        int cant = 0;
        if(fResultado.exists()){
            try (
                BufferedReader br = new BufferedReader(new FileReader(fResultado));
            ) {
                String linea = br.readLine();
                cant = Integer.parseInt(linea);
                System.out.println(cant);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void contador4(){
        File file1 = new File("archivo.txt");
        File file2 = new File("archivo2.txt");
        File file3 = new File("archivo3.txt");
        List<File> lista = List.of(file1, file2, file3);
        File result1 = new File("resultado1.txt");
        File result2 = new File("resultado2.txt");
        File result3 = new File("resultado3.txt");
        List<File> resultado = List.of(result1, result2, result3);
        int i=0;
        for (File file : lista) {
            Contador ej = new Contador(file, resultado.get(i));
            ej.contando();
            i++;
        }
    }

    public static void menu() {
        int seg = 0;
        boolean menu = true;
        while (menu) {
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            int opcion = Integer.parseInt(sc.nextLine());

            ProcessBuilder pb1;
            ProcessBuilder pb2;

            List<Process> procesos = List.of();

            switch (opcion) {
                case 1:
                    System.out.println("Cuántos segundos debe durar: ");
                    seg = Integer.parseInt(sc.nextLine());
                    if (seg > 0) {
                        pb1 = new ProcessBuilder("cmd", "/c");
                        try (
                                Process p1 = pb1.start();) {
                            procesos.add(p1);
                            p1.waitFor();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No puede durar eso");
                    }
                    break;

                case 2:
                    System.out.println("Cuántos segundos debe durar: ");
                    seg = Integer.parseInt(sc.nextLine());
                    if (seg > 0) {
                        pb2 = new ProcessBuilder("cmd", "/c");
                        try (
                                Process p2 = pb2.start();) {
                            procesos.add(p2);
                            // no hay waitFor
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No puede durar eso");
                    }
                    break;

                case 3:
                    System.out.println("Procesos activos: ");
                    for (Process proceso : procesos) {
                        if (proceso.isAlive() == true) {
                            System.out.println("Numero pid del proceso " + proceso.pid());
                        }
                    }
                    break;

                default:
                    break;
            }
        }

    }

    public static void sumaCuadrados() {
        // File f1= new File("./bats/cuadrados.bat");
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "cuadrados.bat", "1", "2", "3", "4");
        // File f2= new File("./bats/suma.bat");
        ProcessBuilder pb2 = new ProcessBuilder("suma.bat");

        try (
                Process p1 = pb1.start();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));

                Process p2 = pb2.start();
                BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));) {
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
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "cuadrados.bat", "1", "2", "3", "4");
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

    public static void sumaCuadradosV3(){
        
    }

    public static void chapuzas() {
        boolean m = true;
        while (m) {
            System.out.print("Ejecución (S)imple, con (P)ipeline o (C)oncatenando trabajos con PipeLine: ");
            String version = sc.nextLine();

            String archvOrigen = "textoMal.txt";
            Path rutaOrigen = Paths.get(archvOrigen);
            String archvDestino = "textoBien.txt";
            Path rutaDestino = Paths.get(archvDestino);

            if (version.equalsIgnoreCase("s")) {
                Trabajo t1 = new Trabajo(rutaOrigen, "-en", rutaDestino);
                t1.corregirTraducirSimple2();
                m = false;
            } else if (version.equalsIgnoreCase("p")) {
                Trabajo t1 = new Trabajo(rutaOrigen, "-en", rutaDestino);
                t1.corregirTraducirPipeLine();
                m = false;
            } else if (version.equalsIgnoreCase("c")) {
                String archvDestino1 = "textoBien1.txt";
                rutaDestino = Paths.get(archvDestino1);
                Trabajo t1 = new Trabajo(rutaOrigen, "-en", rutaDestino);
                String archvDestino2 = "textoBien2.txt";
                rutaDestino = Paths.get(archvDestino2);
                Trabajo t2 = new Trabajo(rutaOrigen, "-fr", rutaDestino);
                String archvDestino3 = "textoBien3.txt";
                rutaDestino = Paths.get(archvDestino3);
                Trabajo t3 = new Trabajo(rutaOrigen, "-it", rutaDestino);

                List<Trabajo> trabajos = new ArrayList<>();
                trabajos = List.of(t1, t2, t3);
                for (Trabajo trabajo : trabajos) {
                    trabajo.corregirTraducirPipeLine();
                }
            } else {
                System.out.println("Esa letra de ké, vuelve a escribir la letra...");
            }

        }

    }

    public static void tasklistSimple() {
        ProcessBuilder tasklist = new ProcessBuilder("cmd", "/c", "tasklist");
        File file = new File("lineas.txt");
        File file2 = new File("numero.txt");
        try (
                Process p = tasklist.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                FileWriter fw = new FileWriter(file);
                BufferedReader br2 = new BufferedReader(new FileReader(file));
                FileWriter fw2 = new FileWriter(file2);) {
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                fw.write(linea + "\n");
            }
            while ((linea = br2.readLine()) != null) {
                i++;
            }
            i = i - 3;
            fw2.write(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tasklistSimple2(){
        File file = new File("tasklist.txt");
        ProcessBuilder pb = new ProcessBuilder("tasklist");
        pb.redirectOutput(file);
        try (
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        ) {
            String linea;
            String tasks = " ";
            int i = 0;
            // Leemos línea a línea hasta que no haya más texto (null)
            while ((linea = br.readLine()) != null) {
                tasks += linea + "\n";
                i++;
            }

            for (int j = 0; j < i - 3; j++) {
                System.out.println(tasks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
