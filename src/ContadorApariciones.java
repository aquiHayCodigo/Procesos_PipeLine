import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContadorApariciones {
    public static void main(String[] args) {
        // 1. Definir la palabra a buscar y la lista de archivos de texto
        String palabra = "java"; // Puedes cambiarla o leerla por argumentos
        List<String> archivos = List.of("archivo1.txt", "archivo2.txt", "archivo3.txt");
        
        File resultadosFile = new File("resultados.txt");
        
        // Si el fichero resultados.txt ya existe de una ejecución anterior, lo borramos para empezar limpio
        if (resultadosFile.exists()) {
            resultadosFile.delete();
        }

        List<Process> procesos = new ArrayList<>();

        try {
            // 2. Lanzar un proceso por cada archivo
            for (String archivo : archivos) {
                // Creamos el ProcessBuilder con el script y sus argumentos
                ProcessBuilder pb = new ProcessBuilder("./contar_palabras.sh", archivo, palabra);
                
                // Redirigimos la salida estándar al fichero resultados.txt en modo "append" (añadir)
                // para que los procesos no sobrescriban los resultados de los demás.
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(resultadosFile));
                
                // Opcional: Redirigir errores estándar a la consola por si falla algún archivo
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                
                // Iniciamos el proceso y lo guardamos en la lista
                Process proceso = pb.start();
                procesos.add(proceso);
            }

            // 3. Esperar a que TODOS los procesos hayan terminado su ejecución
            for (Process proceso : procesos) {
                int exitCode = proceso.waitFor();
                if (exitCode != 0) {
                    System.err.println("Aviso: Un proceso finalizó con código de error: " + exitCode);
                }
            }

            // 4. Leer el fichero resultados.txt, sumar los números y calcular el total
            int totalApariciones = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(resultadosFile))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.isEmpty()) {
                        try {
                            totalApariciones += Integer.parseInt(linea);
                        } catch (NumberFormatException e) {
                            System.err.println("Línea no válida encontrada en resultados.txt: " + linea);
                        }
                    }
                }
            }

            // 5. Imprimir el resultado final por consola
            System.out.println("--------------------------------------------------");
            System.out.println("El número total de apariciones de la palabra '" + palabra + "' es: " + totalApariciones);
            System.out.println("--------------------------------------------------");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}