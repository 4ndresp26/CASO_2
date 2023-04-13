import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException; 

public class ThreadPagina extends Thread {

    private Estructuras estructuras;
    private String archivo;

    public ThreadPagina(Estructuras estructuras, String archivo) {

        this.estructuras = estructuras;
        this.archivo = archivo;
        
    }
    
    public void run(){
        
        FileReader fr = null;
        
        try {
            fr = new FileReader ("CASO_2/data/"+archivo);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
		BufferedReader br = new BufferedReader(fr);

        int TP = 0;
        int NF = 0;
        int NC = 0;
        int NR = 0;
        
        try {

            TP =  Integer.parseInt(br.readLine().split("=")[1]);
            NF =  Integer.parseInt(br.readLine().split("=")[1]);
		    NC =  Integer.parseInt(br.readLine().split("=")[1]);
		    NR =  Integer.parseInt(br.readLine().split("=")[1]);

        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int i = 0;
        while(i< NR){
            String registro = "";

            try {
                registro = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int numeroPagina =  Integer.parseInt(registro.split(",")[1]);
            estructuras.accesoPagina(numeroPagina);

            try {
                sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i++;
        }

    }

}