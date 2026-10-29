import java.io.File;

public class Contador {
    private File ruta;
    private File resultado;

    public Contador(File ruta, File resultado) {
        this.ruta = ruta;
        this.resultado = resultado;
    }
    
    public File getRuta() {
        return ruta;
    }

    public File getResultado() {
        return resultado;
    }

    public void contando(){
        String bat = "contar_palabras.bat";
        String palabra = "que";
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", bat, this.ruta.toString(), palabra);
        pb.redirectOutput(this.resultado);
        if(this.resultado.exists()){
            this.resultado.delete();
        }
        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            if(exitCode==0){
                System.out.println("El proceso se ha ejecutado");
            }
            if(this.resultado.exists()){
                System.out.println("El resultado se ha creado con éxito en " + this.getResultado() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

}
