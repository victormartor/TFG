package util;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Clases.Talla;
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
            System.out.println("Error al encender el Servidor. "+e.toString());
        }      
    }
    
    public void conectar()
    {
        try
        {
            _SocketDatos = new SocketStream   (_SocketConexion.accept( ));
            
        }catch(IOException e) 
        {
            System.out.println("Error al conectar. "+e.toString());
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
        }catch(IOException e){
            System.out.println("Error al apagar el Servidor. "+e.toString());
        }
         
    }
    
    public String obtenerMensaje()
    {
        try
        {                    
            _sMensaje = _SocketDatos.recibeMensaje();
        
        }catch(IOException e)
        {
            System.out.println("Error al obtener el mensaje. "+e.toString());
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
            System.out.println("Error al enviar el mensaje. "+e.toString());
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
            System.out.println("Error al enviar las marcas. "+ex.toString());
        } 
    }
    
    public void enviarMarca(int iId_Marca)
    {
        try
        {     
            Marca marca = new Marca(iId_Marca);
            
            _SocketDatos.enviaMensaje(marca.toString());
            Imagen imagen = new Imagen(marca.getId_Imagen());
            _SocketDatos.enviaMensaje(imagen.getNombre());

        }catch(Exception ex)
        {
            System.out.println("Error al enviar la marca. "+ex.toString());
        } 
    }
    
    public void enviarCategorias(int iId_Marca)
    {
        try
        {     
            ArrayList<Categoria> aCategorias = Categoria.Select(null, null, iId_Marca);
            for(Categoria categoria : aCategorias){
                _SocketDatos.enviaMensaje(categoria.toString());
                Imagen imagen = new Imagen(categoria.getId_Imagen());
                _SocketDatos.enviaMensaje(imagen.getNombre());
            }
            _SocketDatos.enviaMensaje("FinCategorias");
        }catch(Exception ex)
        {
            System.out.println("Error al enviar las categorias. "+ex.toString());
        } 
    }
    
    public void enviarArticulos(int iId_Categoria)
    {
        try
        {     
            ArrayList<Articulo> aArticulos = Articulo.Select(null, null, iId_Categoria, null);
            for(Articulo articulo : aArticulos){
                String sArticulo = articulo.toString();
                _SocketDatos.enviaMensaje(sArticulo);
                String sPartes[] = sArticulo.split(":");
                Imagen imagen = new Imagen(Integer.parseInt(sPartes[2]));
                _SocketDatos.enviaMensaje(imagen.getNombre());
            }
            _SocketDatos.enviaMensaje("FinArticulos");
        }catch(Exception ex)
        {
            System.out.println("Error al enviar las categorias. "+ex.toString());
        } 
    }
    
    public void enviarUnArticulo(int iId_Articulo)
    {
        try
        {     
            Articulo articulo = new Articulo(iId_Articulo);
            _SocketDatos.enviaMensaje(articulo.toString());
        }catch(Exception ex)
        {
            System.out.println("Error al enviar el artículo. "+ex.toString());
        } 
    }
    
    public void enviarColoresArticulo(int iId_Articulo)
    {
        try
        {     
            Articulo articulo = new Articulo(iId_Articulo);
            for(Integer i : articulo.getColores()){
                Color color = new Color(i);
                String sColor = color.getId()+":"+color.getNombre();
                _SocketDatos.enviaMensaje(sColor);
                for(Imagen imagen : articulo.Get_Imagenes_Color(color.getId())){
                    _SocketDatos.enviaMensaje(imagen.getNombre());
                }
                _SocketDatos.enviaMensaje("FinImagenes");
            }
            _SocketDatos.enviaMensaje("FinColores");
        }catch(Exception ex)
        {
            System.out.println("Error al enviar los colores. "+ex.toString());
        } 
    }
    
    public void enviarTallasArticulo(int iId_Articulo)
    {
        try
        {     
            Articulo articulo = new Articulo(iId_Articulo);
            for(Integer i : articulo.getTallas()){
                Talla talla = new Talla(i);
                String sTalla = talla.getId()+":"+talla.getNombre();
                _SocketDatos.enviaMensaje(sTalla);
            }
            _SocketDatos.enviaMensaje("FinTallas");
        }catch(Exception ex)
        {
            System.out.println("Error al enviar las tallas. "+ex.toString());
        } 
    }

} // fin de class

