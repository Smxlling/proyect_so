package servidor;

import java.io.*; 
import java.net.*;
import java.util.*;

class HiloRecibe extends Thread{ 
	TimerTask tarea;
	Timer timer=new Timer();
	Socket rec;
	BufferedReader entrada;
   	PrintWriter salida;
	
	public static List hilos = new ArrayList();
		
	public HiloRecibe(Socket c){ 
		
		rec=c; 
			
		try{// Extraemos los Streams de entrada y de salida 
   			salida = new PrintWriter(rec.getOutputStream(),true);
   			entrada=new BufferedReader(new InputStreamReader(rec.getInputStream()));
   			start();
 		}
 		catch(Exception e){
 			FormServidor.salida(1,e.getMessage());
 		}
	}
	
	@Override
	public void run(){ 
		
			

		
   		String entra="";
   		Iterator it;
   		//se informa al cliente q se ha conectado correctamente
   		salida.println("5Ha entrado al chat.");
   		
   		//se informa al cliente quienes estan conectados actualmente
   		it = hilos.iterator();
   		HiloRecibe tmp;
   		while(it.hasNext()){
   			tmp = (HiloRecibe)it.next();
   			salida.println("7"+tmp.rec.getInetAddress().getHostName());
   			
   		}
   		
   		//se informa a todos los clientes q se ha conectado otro
   		it = hilos.iterator();
   		while(it.hasNext()){
   			tmp = (HiloRecibe)it.next();
   			tmp.salida.println("3"+rec.getInetAddress().getHostName());
   			tmp.salida.println("4: Ha entrado al chat.");
   		}
   		
   		hilos.add(this); //añadir a lista de clientes
   		
 		try{
 			while( (entra=entrada.readLine()) != null){
 				long inicio=System.nanoTime();
 				if(entra.equals("2")){
 					try{
    					it = hilos.iterator(); //para recorrer el array de hilos    					
    					for(int j=0; j<FormServidor.us.getSize(); j++){
    						if(this == (HiloRecibe)it.next()){
    							FormServidor.us.removeElementAt(j); //eliminar de la lista
    							break;
    						}
    					}    						
    					//se informa en la conversacion
    					FormServidor.salida(2,rec.getInetAddress().getHostName().toString()+": Ha abandonado el chat.");
    					hilos.remove(this); //eliminar del array
    					//se le informa a todos los clientes q alguien se ha desconectado
    					it = hilos.iterator();
   						while(it.hasNext()){
   							tmp = (HiloRecibe)it.next();
   							tmp.salida.println("8"+rec.getInetAddress().getHostName());
   							tmp.salida.println("9: Ha abandonado el chat.");
   						}
    					//se le informa al cliente q se ha desconectado correctamente
    					salida.println("6Usted se ha desconectado correctamente.");
    					rec.close(); //cerrar el socket
    					break; //parar este hilo
    				}
    				catch(Exception e){System.err.println(e.getMessage());}
 				}
   				FormServidor.seccionCritica(rec.getInetAddress().getHostName()+"Estoy en la secction critica");
   				FormServidor.salida(3, rec.getInetAddress().getHostName()+" dice: \n"+entra.substring(1));
   				//se reenvia el mesaje a todos los clientes "ECHO"
   				it = hilos.iterator();
   				HiloRecibe tmp2;
   				while(it.hasNext()){
   					tmp2 = (HiloRecibe)it.next();
   					if( !(tmp2.equals(this)) ){
   						tmp2.salida.println("1"+tmp2.rec.getInetAddress().getHostName()+" dice:");
   						tmp2.salida.println("2"+entra.substring(1));
   					}
   				
   					long fin=System.nanoTime();
   					long epcilon=(long) Math.E;
   					long tiempoEjecucion=(fin-inicio)*epcilon;
   					FormServidor.seccionCritica("Termine con mi tarea");
   					FormServidor.seccionCritica("Tiempo de ejecucion: "+tiempoEjecucion);
   					
   					//ServidorVentana.seccionCritica.setText("");

   					
   					
   				}
   			}
 			

   		}
   		catch(Exception e){
   			FormServidor.salida(1,e.getMessage());
   		}
	}
	
} 