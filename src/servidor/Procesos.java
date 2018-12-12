package servidor;
import java.io.*; 
import java.net.*;
import java.util.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Procesos extends Thread{
	public Procesos() {
		start();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	while(true) {
		java.util.Iterator it;
		
		
	it=HiloRecibe.hilos.iterator();
	
		System.out.println("Hilo:"+it.hasNext());
		
	
			
	
		
	}
		
			//FormServidor.procesos.getDocument().insertString(FormServidor.procesos.getDocument().getLength(), "Hol", FormServidor.at);
		
		
	}
			
	}






