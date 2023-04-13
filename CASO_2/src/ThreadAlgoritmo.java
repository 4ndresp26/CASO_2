
public class ThreadAlgoritmo extends Thread {


    private Estructuras estructuras;

    public ThreadAlgoritmo(Estructuras estructuras) {

        this.estructuras = estructuras;
        
    }

    public void run(){

        while (!estructuras.registrosfinalizados()){
            
            estructuras.Aging_Algorithm();

            try {
                sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        }

    }


    
}
