package util;

import java.awt.Font;
import java.util.Scanner;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Servidor
{
    private String mensaje;
    private boolean encendido;
    private ServerSocket miSocketConexion;
    private SocketStream miSocketDatos;
    
    //GET
    public String getMensaje(){return mensaje;}
    public boolean encendido() {return encendido;}
    
    public void encenderServidor()
    {
        encendido = true;
        try
        {
            miSocketConexion =  new ServerSocket(5000);
            
        }catch(IOException e) 
        {
            e.printStackTrace();
            apagarServidor();
        }
    }
    
    public void apagarServidor()
    {
        encendido = false;
        try
        {
            if(miSocketDatos != null)
            {
                miSocketDatos.closeServer();
                miSocketDatos.close();
            }
            
            miSocketConexion.close();
        }catch(IOException e){e.printStackTrace();}
         
    }
    
    public String obtenerMensaje()
    {
        try
        {
            miSocketDatos = new SocketStream   (miSocketConexion.accept( ));         
            mensaje = miSocketDatos.recibeMensaje();
        
        }catch(IOException e)
        {
            apagarServidor();
        }
        
        return mensaje;
    }
    
  public String conectar()
  {
     
    int puertoServidor = 5000; // puerto por defecto
    
       mensaje = "";

    
    try {
      // instancia un socket stream para aceptar las conexiones
      ServerSocket miSocketConexion =  new ServerSocket(puertoServidor);
      
      SocketStream miSocketDatos = new SocketStream   (miSocketConexion.accept( ));

      mensaje += miSocketDatos.recibeMensaje();
      miSocketDatos.close( );
      miSocketConexion.close();
    }catch (Exception ex) 
    {
      ex.printStackTrace( );
    }
    
    return mensaje;

  }

} // fin de class

