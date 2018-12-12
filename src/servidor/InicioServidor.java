package servidor;

import java.io.*; 
import java.net.*;
import java.util.*;
  
public class InicioServidor {
	static String ipLocal = "";
	static String nombreLocal="";
	static ServerSocket socketServ = null; 
    
    public static void main(String args[]) {
    	
        new FormServidor().setVisible(true);
        
        try{ 
        	ipLocal = InetAddress.getLocalHost().getHostAddress().toString();
        	nombreLocal = InetAddress.getLocalHost().getHostName().toString();
   			FormServidor.muestraIp.setText(ipLocal);
   		
   			socketServ = new ServerSocket(7777);
   			FormServidor.salida(2, "Socket creado, escuchando en puerto 7777.");
   		   		
   			while(true){
   				Socket s=null;   				
   				s = socketServ.accept(); 
   				
   				FormServidor.us.addElement(s.getInetAddress().getHostName()+"/"+s.getInetAddress().getHostAddress()+":"+s.getPort());
   				FormServidor.salida(2, "Se ha conectado "+s.getInetAddress().getHostName());
   				
   				new HiloRecibe(s); //nuevo hilo q atendera los mensajes de este cleinte
   			}
   		}
   		catch(java.net.BindException e){
   			FormServidor.salida(1,"No se puede arrancar el servidor: puerto 7777 ocupado.");
   		}
   		catch(java.lang.SecurityException e){
   			FormServidor.salida(1,"No se puede arrancar el servidor: Hay restricciones de seguridad.");
   		}
   		catch(Exception e){
   			FormServidor.salida(1,e.getMessage());
   		}
    }
    
   


}