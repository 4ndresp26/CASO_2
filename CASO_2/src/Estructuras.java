public class Estructuras {

    
    private int numeroPaginas;
    private int numeroMarcos;
    private String[] tablaPaginas;
    private String[] memoriaReal;
    private int fallosPagina;
    private int indiceMarco;
    private int indicePagina;
    private int[] bitReferencia;
    private byte[] contador;
    private int numeroRegistros;
    private int num;
    

    public Estructuras(int numeroPaginas, int numeroMarcos, int numeroRegistros){

        this.numeroPaginas = numeroPaginas;
        this.numeroMarcos = numeroMarcos;
        this.tablaPaginas = new String[numeroPaginas];
        this.memoriaReal = new String[numeroMarcos];
        this.fallosPagina = 0;
        this.indiceMarco = 0;
        this.indicePagina = 0;
        this.bitReferencia = new int[numeroMarcos];
        this.contador = new byte[numeroMarcos];
        this.numeroRegistros = numeroRegistros;
        this.num = 0;
    }



    public int encontrarPagina(int numeroPagina) {
        // busca la página en la tabla de páginas
        if (tablaPaginas[numeroPagina] == null){
            // página no encontrada
            return -1;
        }
        else{
            // página encontrada
            return Integer.valueOf(tablaPaginas[numeroPagina]);
        }
    }

    public int encontrarMarcoLibre(){

        for(int i = 0; i<numeroMarcos; i++){
            if (memoriaReal[i] == null){
                return i;
            }
        }
        return -1;
    } 

    public void actualizarTablaPaginas(int marco, int pagina){

        tablaPaginas[pagina]= String.valueOf(marco);

    }

    public void actualizarMarco(int marco, int pagina){
        
        memoriaReal[marco]= String.valueOf(pagina);

    }

    public void eliminarReferencia(int pagina){

        tablaPaginas[pagina]= null;
    }
    
    public synchronized void accesoPagina(int numeroPagina){

        num++;
      
        int marco = encontrarPagina(numeroPagina);
        if (marco == -1){
            fallosPagina++;
            marco = encontrarMarcoLibre();
            if (marco == -1){
                // No hay marco libre
                bitReferencia[indiceMarco] = 1;
                eliminarReferencia(indicePagina);
                actualizarMarco(indiceMarco, numeroPagina);
                actualizarTablaPaginas(indiceMarco, numeroPagina);

               
                contador[0] = 0b0000_0000;

            }
            else{
                // Hay un marco libre
                bitReferencia[marco] = 1;
                actualizarMarco(marco, numeroPagina);
                actualizarTablaPaginas(marco, numeroPagina);
                
                

            }
        }
        else{
            bitReferencia[marco] = 1;
        }
    }


    public synchronized void Aging_Algorithm(){
        
        for(int i = 0; i<numeroMarcos; i++){
            int bitR = bitReferencia[i];
            if (bitR == 1){
                byte byteR = contador[i];
                byteR = (byte) (byteR >>1);
                byteR |= (byte) 0b1000_0000;
                contador[i]= byteR;
            } 
            else{
                byte byteR = contador[i];
                byteR = (byte) (byteR >>1);
                contador[i]= byteR;
            }
        }

        int minByte = contador[0];
        int indicePag = 0;
        int marco = 0;
        for (int i = 0; i<numeroMarcos; i++ ){
            if(Math.abs(contador[i])<Math.abs(minByte) && memoriaReal[i] != null ){
                minByte = contador[i];
                marco = i;
                indicePag = Integer.parseInt(memoriaReal[i]);
            }
        }
        
        bitReferencia = new int[numeroMarcos];
        indicePagina = indicePag;
        indiceMarco = marco;
        
    }

    public synchronized boolean registrosfinalizados(){
    
        if (num == numeroRegistros){
            return true;
        }
        return false;
    }

    public int getFallosPagina(){

        return fallosPagina;
    }

    
}