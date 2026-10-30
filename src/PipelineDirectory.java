import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class PipelineDirectory {
    public static void main(String[] args) {
        try {
            // Definimos la carpeta donde queremos que se ejecuten los comandos
            File carpetaTrabajo = new File("C:\\Users\\claud\\Downloads");

            // 1. Primer proceso: cmd /c dir (hereda la carpeta gracias a .directory())
            ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "dir")
                    .directory(carpetaTrabajo); // <--- AQUÍ ESTABLECES LA RUTA

            // 2. Segundo proceso: cmd /c findstr para filtrar
            ProcessBuilder pb2 = new ProcessBuilder("cmd.exe", "/c", "findstr", "pdf")
                    .directory(carpetaTrabajo); // <--- (Opcional, pero recomendado que compartan directorio)

            // Ejecutamos el pipeline
            List<Process> processes = ProcessBuilder.startPipeline(List.of(pb1, pb2));

            // Leemos el resultado del último proceso
            Process ultimoProceso = processes.get(processes.size() - 1);
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(ultimoProceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Esperar a que terminen
            for (Process p : processes) {
                p.waitFor();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}