
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

    public void corregirTraducirSimple() { // NO funciona
        String batCheck = "check.bat";
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", batCheck, this.sourcePath.toString());

        String batTranslate = "translate.bat";
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", batTranslate, this.language);

        try (
                // 1er Proceso --> Recibe archivo mal y lo corrige
                Process p1 = pb1.start();
                // 2do Proceso --> Recibe archivo bien y lo traduce
                Process p2 = pb2.start();

                BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(destinationPath.toString())));) {
;
            String linea;
            // Escribiendo en el Proceso 2 el texto bien
            while ((linea = br1.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();

            // Leyendo salida Proceso 2
            while ((linea = br2.readLine()) != null) {
                //System.out.println(linea);
                bw2.write(linea);
                bw2.newLine();
            }

        } catch (Exception e) {
        }
    }

    public void corregirTraducirSimple2() {
        String batCheck = "check.bat";
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", batCheck, this.sourcePath.toString());

        String batTranslate = "translate.bat";
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", batTranslate, this.language);

        try {
            Process p1 = pb1.start();
            Process p2 = pb2.start();

            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()))) {
                
                String linea;
                while ((linea = br1.readLine()) != null) {
                    bw.write(linea);
                    bw.newLine();
                }
                bw.flush();
            } // <--- Al salir de este bloque, 'bw' SE CIERRA AUTOMÁTICAMENTE.
            //      El script 'translate.bat' recibe la señal EOF (End of File) y procesa la salida.

            try (BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(destinationPath.toFile()))) {
                
                String linea;
                while ((linea = br2.readLine()) != null) {
                    bw2.write(linea);
                    bw2.newLine();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void corregirTraducirPipeLine() {
        String f1= sourcePath.toString();
        File file1= new File(f1);
        String batCheck = "check.bat";
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", batCheck);
        pb1.redirectInput(file1);

        String f2= destinationPath.toString(); 
        File file2 = new File(f2); 
        // File file2 = new File(destinationPath.toString()); // File file2 = destination.Path.toFile();
        String batTranslate = "translate.bat";
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
