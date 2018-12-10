package cliente;

import java.io.*; 
import java.net.*;

class HiloRecibeCliente extends Thread{ 
	static BufferedReader entradaDatos=null;
	
	public HiloRecibeCliente(BufferedReader entrada2){
		entradaDatos = entrada2;
		start();
	}

	public void run(){
		
   		String linea="";
		try{
			while( (linea = entradaDatos.readLine()) != null ){ //escucha mensajes del servidor
				FormCliente.salida(2,linea);
				if(linea.equals("6Usted a sido desconectado por el servidor."))	break;
				if(linea.equals("6Usted se ha desconectado correctamente.")) break;
			}
		}
		catch(Exception e){ 
   			FormCliente.salida(1,e.getMessage());
   			if(entradaDatos!=null){
   				try{ entradaDatos.close();}
   				catch(Exception er){}
   			}
   		}
	}
}