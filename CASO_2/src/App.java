import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.lang.Thread;

public class App {
    public static void main(String[] args) throws Exception {

		System.out.println("\n------------------------------- ");
		System.out.println("Iniciando el Programa...");
		System.out.println("------------------------------- ");


		Scanner scanner = new Scanner(System.in);
		boolean ejecutar = true;

		while (ejecutar) {
			printMenu();
			System.out.println("Digite el número de la opción que desea ejecutar o 'salir' para terminar:");
			String opcion = scanner.nextLine();

			if (opcion.equalsIgnoreCase("salir")) {
				ejecutar = false;
			} else if (opcion.equals("1")) {
				modo1(scanner);
			} else if (opcion.equals("2")) {
				modo2(scanner);
			} else {
				System.out.println("Opción inválida, por favor intente nuevamente");
			}
		}
		scanner.close();

    }

	public static void printMenu(){

		System.out.println("\n1. Modo 1 (Generar referencias) ");
		System.out.println("2. Modo 2 (Generar referencias y simular paginación)");
		System.out.println("3. Salir\n");

	}


	public static void modo1(Scanner entrada) throws NumberFormatException, IOException{
		
		System.out.println("\nIndique el nombre del archivo de lectura: ");
		String archivo = entrada.next();
		
		System.out.println("\nIndique el nombre del archivo donde desea guardar las referencias: ");
		String archivoSalida = entrada.next();
		
		FileReader fr = new FileReader ("CASO_2/data/"+archivo);
		BufferedReader br = new BufferedReader(fr);

		int TP =  Integer.parseInt(br.readLine().split("=")[1]);
        int NF =  Integer.parseInt(br.readLine().split("=")[1]);
		int NC =  Integer.parseInt(br.readLine().split("=")[1]);
		int TE =  Integer.parseInt(br.readLine().split("=")[1]);
		int MP =  Integer.parseInt(br.readLine().split("=")[1]);
		
		int copiaTP = TP;

		FileWriter fichero = new FileWriter("CASO_2/data/"+archivoSalida);
		PrintWriter pw = new PrintWriter(fichero);

		pw.println("TP="+TP);
		pw.println("NF="+NF);
		pw.println("NC="+NC);
		pw.println("RF="+NC*NF);
		
		
		//En caso de que al finalizar la pagina no se pueda guardar por completo un entero de la matriz
        if (TP % TE != 0){
			
			TP = TP - (TP % TE);
			
		}

		//Algoritmo de generación de referencias 
		int celda = 0;
		for(int i = 0; i < NF; i++) {
			for(int j = 0; j < NC; j++) {
				
				
				int paginaA = (celda*TE)/TP;
				int desplazamientoA = ((celda*TE)%TP);
				int paginaB = (celda*TE+(NF*NC*TE))/TP;
				int desplazamientoB = ((celda*TE+(NF*NC*TE))%TP);
				int paginaC = (celda*TE+(NF*NC*TE*2))/TP;
				int desplazamientoC = ((celda*TE+(NF*NC*TE*2))%TP);
				String referenciaA = "[A-"+i+"-"+j+"],"+paginaA+","+desplazamientoA;
				String referenciaB = "[B-"+i+"-"+j+"],"+paginaB+","+desplazamientoB;
				String referenciaC = "[C-"+i+"-"+j+"],"+paginaC+","+desplazamientoC;
				
				pw.println(referenciaA);
				pw.println(referenciaB);
				pw.println(referenciaC);
				
				celda++;
				
			}
		}

		fichero.close();
		System.out.println("Se guardaron "+NC*NF+" referencias, puede verlas en el archivo "+archivoSalida);
	
	}



	public static void modo2(Scanner entrada) throws NumberFormatException, IOException, InterruptedException{
		
		System.out.println("\nIndique el nombre del archivo de lectura: ");
		String archivo = entrada.next();
		
		System.out.println("\nIndique el nombre del archivo donde desea guardar las referencias: ");
		String archivoSalida = entrada.next();

		FileReader fr = new FileReader ("CASO_2/data/"+archivo);
		BufferedReader br = new BufferedReader(fr);

		int TP =  Integer.parseInt(br.readLine().split("=")[1]);
        int NF =  Integer.parseInt(br.readLine().split("=")[1]);
		int NC =  Integer.parseInt(br.readLine().split("=")[1]);
		int TE =  Integer.parseInt(br.readLine().split("=")[1]);
		int MP =  Integer.parseInt(br.readLine().split("=")[1]);
		
		int copiaTP = TP;

		FileWriter fichero = new FileWriter("CASO_2/data/"+archivoSalida);
		PrintWriter pw = new PrintWriter(fichero);
		
		pw.println("TP="+TP);
		pw.println("NF="+NF);
		pw.println("NC="+NC);
		pw.println("RF="+NC*NF*3);
		
	    int numeroPaginas = 0;
		
		//En caso de que al finalizar la pagina no se pueda guardar por completo un entero de la matriz
        if (TP % TE != 0){
			
			TP = TP - (TP % TE);
			
		}

		//Algoritmo de generación de referencias 
		int celda = 0;
		for(int i = 0; i < NF; i++) {
			for(int j = 0; j < NC; j++) {
				
				
				int paginaA = (celda*TE)/TP;
				int desplazamientoA = ((celda*TE)%TP);
				int paginaB = (celda*TE+(NF*NC*TE))/TP;
				int desplazamientoB = ((celda*TE+(NF*NC*TE))%TP);
				int paginaC = (celda*TE+(NF*NC*TE*2))/TP;
				int desplazamientoC = ((celda*TE+(NF*NC*TE*2))%TP);
				String referenciaA = "[A-"+i+"-"+j+"],"+paginaA+","+desplazamientoA;
				String referenciaB = "[B-"+i+"-"+j+"],"+paginaB+","+desplazamientoB;
				String referenciaC = "[C-"+i+"-"+j+"],"+paginaC+","+desplazamientoC;
				
				pw.println(referenciaA);
				pw.println(referenciaB);
				pw.println(referenciaC);
				
				celda++;

				if (celda == NF*NC-1){
					numeroPaginas = paginaC+1;
				}

				
			}
		}

		fichero.close();

		//Creación de las estructuras de paginación (monitor para el los threads del manejo de paginas y el algoritmo)
        Estructuras estructuras = new Estructuras(numeroPaginas, MP, NF*NC*3);
        //Creación de los threads
		ThreadPagina threadPagina = new ThreadPagina(estructuras, archivoSalida);
        ThreadAlgoritmo threadAlgoritmo = new ThreadAlgoritmo(estructuras);
        //Ejecución de los threads
		threadPagina.start();
        threadAlgoritmo.start();
		//Espera que ambos threads terminen antes de que continue el main
        threadPagina.join();
        threadAlgoritmo.join();
        //Respuesta
		System.out.println("------------------------------- \n");
        System.out.println("Matriz: "+NF+"x"+NC);
		System.out.println("Tamaño de página: "+copiaTP);
		System.out.println("Tamaño de entero: "+TE);
		System.out.println("Numero de marcos: "+ MP);
		System.out.println("Para la anterior configuracion se encontraron "+estructuras.getFallosPagina()+" fallos de página\n");
		System.out.println("------------------------------- \n");
	}
    
}