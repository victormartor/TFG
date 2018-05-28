package util;

import Data.Clases.Imagen;
import Data.Clases.Marca;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Servidor
{
    private String _sMensaje;
    private boolean _bEncendido;
    private ServerSocket _SocketConexion;
    private SocketStream _SocketDatos;
    
    //GET
    public String getMensaje(){return _sMensaje;}
    public boolean encendido() {return _bEncendido;}
    
    public void encenderServidor()
    {
        _bEncendido = true;
        try
        {
            _SocketConexion =  new ServerSocket(5000);
            
        }catch(IOException e) 
        {
            e.printStackTrace();
            apagarServidor();
        }      
    }
    
    public void apagarServidor()
    {
        _bEncendido = false;
        try
        {
            if(_SocketDatos != null)
            {
                _SocketDatos.closeServer();
                _SocketDatos.close();
            }
            
            _SocketConexion.close();
        }catch(IOException e){e.printStackTrace();}
         
    }
    
    public String obtenerMensaje()
    {
        try
        {
            _SocketDatos = new SocketStream   (_SocketConexion.accept( ));         
            _sMensaje = _SocketDatos.recibeMensaje();
        
        }catch(IOException e)
        {
            apagarServidor();
        }
        
        return _sMensaje;
    }
    
    public void enviarMensaje(String sMensaje)
    {
        try
        {     
            _SocketDatos.enviaMensaje(sMensaje);
        
        }catch(IOException e)
        {
            apagarServidor();
        } 
    }
    
    public void enviarMarcas()
    {
        try
        {     
            ArrayList<Marca> aMarcas = Marca.Select(null, null);
            for(Marca marca : aMarcas){
                _SocketDatos.enviaMensaje(marca.toString());
                Imagen imagen = new Imagen(marca.getId_Imagen());
                _SocketDatos.enviaMensaje(imagen.getNombre());
            }
            _SocketDatos.enviaMensaje("FinMarcas");
        }catch(Exception ex)
        {
            apagarServidor();
        } 
    }
    
    /*
  public String conectar()
  {
     
    int puertoServidor = 5000; // puerto por defecto
     _sMensaje = "";

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
*/

} // fin de class

