
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;

public class Trabajo {
    private Path sourcePath;
    private String language;
    private Path destinationPath;

    public Trabajo(Path rOrigen, String idioma, Path rDestino) {
        this.sourcePath = rOrigen;
        this.language = idioma;
        this.destinationPath = rDestino;
    }

    public void corregirTraducirSimple() {
        String batCheck = "bats/chek.bat";
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", batCheck, this.sourcePath.toString());

        String batTranslate = "bats/translate.bat";
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", batTranslate, this.language);

        try (
                // 1er Proceso --> Recibe archivo mal y lo corrige
                Process p1 = pb1.start();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                // 2do Proceso --> Recibe archivo bien y lo traduce
                Process p2 = pb2.start();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(destinationPath.toString())));) {
            p1.waitFor();
            String linea;

            // Escribiendo en el Proceso 2 el texto bien
            while ((linea = br1.readLine()) != null) {
                bw.write(linea);
            }

            // Leyendo salida Proceso 2
            while ((linea = br2.readLine()) != null) {
                System.out.println(linea);
                bw2.write(linea);
            }

        } catch (Exception e) {
        }
    }

    public void corregirTraducirPipeLine() {
        String f1= sourcePath.toString();
        File file1= new File(f1);
        String batCheck = "./bats/chek.bat";
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", batCheck, this.sourcePath.toString());
        pb1.redirectInput(file1);

        String f2= destinationPath.toString();
        File file2 = new File(f2); // File file2 = new File(destinationPath.toString());
        String batTranslate = "./bats/translate.bat";
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", batTranslate, this.language);
        pb2.redirectOutput(file2);

        List<ProcessBuilder> pbs = List.of(pb1,pb2);
        // PIPELINEEEEE
        try {
            List<Process> procesos= ProcessBuilder.startPipeline(pbs);
            Process last = procesos.getLast();
            last.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();        }

    }
}
