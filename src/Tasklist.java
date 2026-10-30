import java.io.IOException;

public class Tasklist {

    public void t1() {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "tasklist");
        //pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.inheritIO();
        try (Process p = pb.start();) {
            p.waitFor();
        } catch (InterruptedException e) {
            System.out.println("INTERRUPTED EXCEPPPTION " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO EXCEPPPTION " + e.getMessage());
        }

    }

    public void t2(){

    }
}
