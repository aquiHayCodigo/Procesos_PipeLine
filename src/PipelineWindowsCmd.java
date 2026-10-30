import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class PipelineWindowsCmd {
    public static void main(String[] args) {
        try {
            // Primer proceso: Listar el directorio actual usando cmd /c dir
            ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "dir");

            // Segundo proceso: Filtrar las líneas que contengan "txt" usando findstr
            ProcessBuilder pb2 = new ProcessBuilder("cmd.exe", "/c", "findstr", "txt");

            // Ejecutamos el pipeline con la lista de ProcessBuilders
            List<Process> processes = ProcessBuilder.startPipeline(List.of(pb1, pb2));

            // Leemos el resultado del último proceso de la cadena
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