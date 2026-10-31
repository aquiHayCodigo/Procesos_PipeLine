import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Tasklist {

    public void t1() {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "tasklist");
        // pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.inheritIO();
        try (Process p = pb.start();) {
            p.waitFor();
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED EXCEPPPTION " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO EXCEPPPTION " + e.getMessage());
        }

    }

    public void t2() {
        String ruta = "C:\\Users\\claud\\Downloads";
        File file = new File(ruta);
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c", "dir");
        pb1.directory(file);
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c", "findstr pdf");
        pb2.directory(file);
        List<ProcessBuilder> pbs = List.of(pb1, pb2);

        try {
            List<Process> procesos = ProcessBuilder.startPipeline(pbs);
            Process last = procesos.getLast();
            int exitCode = last.waitFor();
            if (exitCode == 0) {
                System.out.println("El último proceso con pid -" + last.pid() + "- se ha ejecutado");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(last.getInputStream()));
            ) {
                String linea; 
                while((linea=br.readLine())!=null){
                    System.out.println(linea);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
