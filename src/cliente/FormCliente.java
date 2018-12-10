package cliente;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.net.*;

public class FormCliente extends JFrame {
	static SimpleAttributeSet at = new SimpleAttributeSet();
	static DefaultListModel us =new DefaultListModel();
	static Date hora;
	static String horaActual;
	String datoEscrito;
	static JButton botonEnviar;
	static JButton desconectarme;
	static JButton conectarme;
    public JLabel jLabel1;
    public JLabel jLabel2;
    public JLabel jLabel3;
    public JLabel jLabel4;
    public JLabel jLabel5;
    public JList usuarios;
    public JScrollPane scConversacion;
    public JScrollPane scUsuarios;
    public JScrollPane scEnvioDatos;
    static JTextField muestraIp;
    static JTextField puertoServ;
    static JTextPane conversacion;
    static JTextPane envioDatos;
    
    public FormCliente() {
        initComponents();
        setSize(600,450);       
    }

    private void initComponents() {
        scConversacion = new JScrollPane();
        scUsuarios = new JScrollPane();
        scEnvioDatos = new JScrollPane();
        conversacion = new JTextPane();
        envioDatos = new JTextPane();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        botonEnviar = new JButton();
        desconectarme = new JButton();
		conectarme = new JButton();
		muestraIp = new JTextField();
		puertoServ = new JTextField();
        usuarios = new JList(us);
        
        getContentPane().setLayout(null);

        setTitle("CHAT (cliente)");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });

		envioDatos.setEditable(true);
        scEnvioDatos.setViewportView(envioDatos);		
        getContentPane().add(scEnvioDatos);
        scEnvioDatos.setBounds(30, 352, 330, 60);        

        conversacion.setEditable(false);
        scConversacion.setViewportView(conversacion);
        getContentPane().add(scConversacion);
        scConversacion.setBounds(30, 130, 330, 210);

        jLabel1.setFont(new Font("MS Sans Serif", 1, 24));
        jLabel1.setForeground(new Color(204, 102, 0));
        jLabel1.setText("CLIENTE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(220, 10, 130, 30);

        jLabel2.setFont(new Font("MS Sans Serif", 1, 14));
        jLabel2.setText("IP Servidor:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 70, 100, 20);

        getContentPane().add(muestraIp);
        muestraIp.setBounds(120, 70, 100, 20);
        
        jLabel5.setFont(new Font("MS Sans Serif", 1, 14));
        jLabel5.setText("puerto:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(230, 70, 60, 20);
        
        getContentPane().add(puertoServ);
        puertoServ.setBounds(290, 70, 40, 20);
        
        conectarme.setFont(new Font("MS Sans Serif", 1, 12));
        conectarme.setForeground(new Color(255,20,20));
        conectarme.setText("Conectarme!");
        getContentPane().add(conectarme);
        conectarme.setBounds(340, 70, 120, 20);
		conectarme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                conectarmeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new Font("MS Sans Serif", 1, 14));
        jLabel3.setText("Conversación:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 100, 110, 20);

        scUsuarios.setViewportView(usuarios);
        usuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getContentPane().add(scUsuarios);
        scUsuarios.setBounds(380, 132, 180, 180);

        jLabel4.setFont(new Font("MS Sans Serif", 1, 12));
        jLabel4.setText("Clientes en el chat:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(380, 110, 250, 16);

        botonEnviar.setFont(new Font("MS Sans Serif", 1, 16));
        botonEnviar.setForeground(new Color(204, 102, 0));
        botonEnviar.setText("ENVIAR !");
        botonEnviar.setEnabled(false);
        getContentPane().add(botonEnviar);
        botonEnviar.setBounds(380, 352, 180, 60);
		botonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonEnviarActionPerformed(evt);
            }
        });
                
        desconectarme.setFont(new Font("MS Sans Serif", 1, 10));
        desconectarme.setForeground(new Color(255,20,20));
        desconectarme.setText("Desconectarme del chat!");
        desconectarme.setEnabled(false);
        getContentPane().add(desconectarme);
        desconectarme.setBounds(380, 315, 180, 25);
		desconectarme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                desconectarmeActionPerformed(evt);
            }
        });
        setResizable(false);
        pack();
    }
        
    private void exitForm(WindowEvent evt) {
    	try{
    		InicioCliente.salidaC.println("2"); //"2" significa q me desconécte
    	}
    	catch (Exception e) {
    		salida(1,e.getMessage());
    	}
    	
    	if(InicioCliente.sckt != null){ //cerramos el socket
    		try{ InicioCliente.sckt.close(); }
    		catch(Exception e){InicioCliente.sckt=null;}
    	}
    	if(InicioCliente.salidaC != null){ //cerramos buffer de salida
    		try{ InicioCliente.salidaC.close(); }
    		catch(Exception e){InicioCliente.salidaC=null;}
    	}
        System.exit(0);
    }
    
    private void desconectarmeActionPerformed(ActionEvent evt) {
    	int resp=2;
    	resp=JOptionPane.showConfirmDialog(null,"Esta seguro que desea abandonar el chat?","Confirmación",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
    	if(resp==0){
    		try{
    			InicioCliente.salidaC.println("2"); //"2" significa q me desconécte
    		}
    		catch (Exception e) {
    			salida(1,e.getMessage());
    		}
    	}    	
    }
    
    private void conectarmeActionPerformed(ActionEvent evt) {
    	InicioCliente.arrancarCliente(muestraIp.getText(),Integer.parseInt(puertoServ.getText()));    	
    	InicioCliente.procesarMensajes();
    }
    
    private void botonEnviarActionPerformed(ActionEvent evt) {
    	datoEscrito=envioDatos.getText().toString(); //capturamos lo q se escribio
    	
    	if( datoEscrito.equals("") == false){ //se envia si escribe algo
    		try{
    			InicioCliente.salidaC.println("1"+datoEscrito);
    			salida(3,InetAddress.getLocalHost().getHostName()+" dice:\n"+datoEscrito);
    		}
    		catch (Exception e) {
    			salida(1,e.getMessage());
    		}
    	}
    	envioDatos.setText("");
    	envioDatos.requestFocus();
    }
    
    public static void salida(int objeto, String dato){
    	hora = new Date();
    	horaActual = hora.getHours()+":"+hora.getMinutes()+":"+hora.getSeconds()+" ";
    	
    	switch(objeto){
    		case 1: //algun tipo de error
    			try{
    				StyleConstants.setForeground(at,Color.red);
					StyleConstants.setBold(at,true);
					if(dato.equals("Connection reset")){
						dato="El servidor se ha caido.";
						us.removeAllElements(); //se elimina toda la lista de clientes
						botonEnviar.setEnabled(false);
						desconectarme.setEnabled(false);
						muestraIp.setEditable(true);
						puertoServ.setEditable(true);
						conectarme.setEnabled(true);
						InicioCliente.sckt.close(); //cerramos el socket
						InicioCliente.salidaC.close(); //cerramos el buffer salida
					}
					else if(dato.equals("Connection refused: connect")){
						dato="Conexion denegada: Corrija la IP y/o el puerto.";
					}
					else if(dato.equals("Connection timed out: connect")){
						dato="Tiempo de espera agotado: Corrija la IP y/o el puerto, o tal vez el servidor no esté activo.";
					}
					else if(dato.equals("No route to host: connect")){
						dato="No existe ruta al host: Corrija la IP y/o el puerto.";
					}
				
    				conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato+"\n",at);
					conversacion.setCaretPosition(conversacion.getDocument().getLength());
				}
				catch (Exception e) {System.err.println(e);}
				break;
				
    		case 2: //mensajes entrantes
    			try{
					StyleConstants.setForeground(at,Color.blue.darker());
					StyleConstants.setBold(at,false);
					if(dato.charAt(0)=='1'){ // de quien recibimos el mensaje
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato.substring(1)+"\n",at);
					}
					else if(dato.charAt(0)=='2'){ //mensaje q recibimos
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),dato.substring(1)+"\n",at);
					}
					else if(dato.charAt(0)=='3'){ //nos informan quien es el nuevo cliente
						StyleConstants.setForeground(at,Color.green.darker().darker());
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato.substring(1),at);
						us.addElement(dato.substring(1));
					}
					else if(dato.charAt(0)=='4'){ //nos informan de nuevo cliente
						StyleConstants.setForeground(at,Color.green.darker().darker());
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),dato.substring(1)+"\n",at);
					}
					else if(dato.charAt(0)=='5'){ //nos informan q nos conectamos
						StyleConstants.setForeground(at,Color.green.darker().darker());
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato.substring(1)+"\n",at);
						us.addElement(InicioCliente.sckt.getInetAddress().getHostName()+" (Local).");
						conectarme.setEnabled(false);
    					muestraIp.setEditable(false);
    					puertoServ.setEditable(false);
    					desconectarme.setEnabled(true);
    					botonEnviar.setEnabled(true);
					}
					else if(dato.charAt(0)=='6'){ //nos desconecto el servidor o nos desconectamos
						StyleConstants.setForeground(at,Color.red);
						if(dato.equals("6Usted se ha desconectado correctamente.")){
							StyleConstants.setForeground(at,Color.green.darker().darker());
						}
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato.substring(1)+"\n",at);
						us.removeAllElements(); //se elimina toda la lista de clientes
						botonEnviar.setEnabled(false);
						desconectarme.setEnabled(false);
						muestraIp.setEditable(true);
						puertoServ.setEditable(true);
						conectarme.setEnabled(true);
						InicioCliente.sckt.close(); //cerramos el socket
						InicioCliente.salidaC.close(); //cerramos el buffer salida
					}
					else if(dato.charAt(0)=='7'){ //nos dicen quienes estan conectados
						us.addElement(dato.substring(1));
					}
					else if(dato.charAt(0)=='8'){ //nos informan quien ha sido desconectado
						StyleConstants.setForeground(at,Color.green.darker().darker());
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato.substring(1),at);
						int a=0;
						while(a<us.getSize()){ //para eliminarlo de la lista
							if(us.getElementAt(a).toString().equals(dato.substring(1))){
								us.removeElementAt(a);
								break;
							}
							else a++;
						}
					}
					else if(dato.charAt(0)=='9'){ //nos informan alguien ha sido desconectado
						StyleConstants.setForeground(at,Color.green.darker().darker());
						StyleConstants.setBold(at,true);
						conversacion.getDocument().insertString(conversacion.getDocument().getLength(),dato.substring(1)+"\n",at);
					}
					conversacion.setCaretPosition(conversacion.getDocument().getLength());
				}
				catch (Exception e) {System.err.println(e);}
				break;
				
			case 3: //mensajes salientes
    			try{
					StyleConstants.setForeground(at,Color.blue.darker().darker());
					StyleConstants.setBold(at,true);
    				conversacion.getDocument().insertString(conversacion.getDocument().getLength(),horaActual+dato+"\n",at);
					conversacion.setCaretPosition(conversacion.getDocument().getLength());
				}
				catch (BadLocationException e) {System.err.println(e);}
				break;
    	}    	
    }
}