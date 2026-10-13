
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class Trabajo {
    private Path sourcePath;
    private String language;
    private Path destinationPath;

    public Trabajo(Path rOrigen, String idioma, Path rDestino){
        this.sourcePath=rOrigen;
        this.language= idioma;
        this.destinationPath= rDestino;
    }

    public void corregirTraducir(){
        
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "chek.bat", "textoMal.txt");
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "translate.bat");

        try (Process p1 = pb1.start();
            BufferedReader br1= new BufferedReader(new InputStreamReader(p1.getInputStream()));

            Process p2= pb2.start();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
            BufferedReader br2= new BufferedReader(new InputStreamReader(p2.getInputStream()));
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
