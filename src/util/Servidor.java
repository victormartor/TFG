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
    
    public void conectar() throws Exception
    {
      _SocketDatos = new SocketStream   (_SocketConexion.accept( ));            
    }
    
    public void apagarServidor() throws Exception
    {
        _bEncendido = false;
 
        if(_SocketDatos != null)
        {
            _SocketDatos.closeServer();
            _SocketDatos.close();
        }

        _SocketConexion.close();
         
    }
    
    public String obtenerMensaje() throws Exception
    {
        return _SocketDatos.recibeMensaje();
    }
    
    public void enviarMensaje(String sMensaje) throws Exception
    {
        _SocketDatos.enviaMensaje(sMensaje);    
    }
    
    public void enviarMarcas() throws Exception
    {
        ArrayList<Marca> aMarcas = Marca.Select(null, null);
        for(Marca marca : aMarcas){
            _SocketDatos.enviaMensaje(marca.toString());
            Imagen imagen = new Imagen(marca.getId_Imagen());
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinMarcas");
    }
    
    public void enviarMarca(int iId_Marca) throws Exception
    {    
        Marca marca = new Marca(iId_Marca);

        _SocketDatos.enviaMensaje(marca.toString());
        Imagen imagen = new Imagen(marca.getId_Imagen());
        _SocketDatos.enviaMensaje(imagen.getNombre());
    }
    
    public void enviarCategorias(int iId_Marca) throws Exception
    {
        ArrayList<Categoria> aCategorias = Categoria.Select(null, null, iId_Marca);
        for(Categoria categoria : aCategorias){
            _SocketDatos.enviaMensaje(categoria.toString());
            Imagen imagen = new Imagen(categoria.getId_Imagen());
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinCategorias");
    }
    
    public void enviarArticulos(int iId_Categoria) throws Exception
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
    }
    
    public void enviarUnArticulo(int iId_Articulo) throws Exception
    { 
        Articulo articulo = new Articulo(iId_Articulo);
        _SocketDatos.enviaMensaje(articulo.toString());
    }
    
    public void enviarColoresArticulo(int iId_Articulo) throws Exception
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
    }
    
    public void enviarTallasArticulo(int iId_Articulo) throws Exception
    {    
        Articulo articulo = new Articulo(iId_Articulo);
        ArrayList<Talla> aTallas = Talla.Select(null, articulo.getTalla_Es_Numero());
        for(Talla talla : aTallas){
            if(articulo.getTallas().contains(talla.getId())){
                String sTalla = talla.getId()+":"+talla.getNombre();
                _SocketDatos.enviaMensaje(sTalla);
            }
        }
        _SocketDatos.enviaMensaje("FinTallas"); 
    }
    
    public void enviarCombinacionesArticulo(int iId_Articulo) throws Exception
    {  
        Articulo articulo = new Articulo(iId_Articulo);
        for(Integer i : articulo.getCombinaciones()){
            Articulo art = new Articulo(i);
            String sArticulo = art.toString();
            Categoria categoria = new Categoria(art.getId_Categoria());
            sArticulo += ":"+categoria.getId_Marca()+":"+categoria.getId();
            _SocketDatos.enviaMensaje(sArticulo);
            String sPartes[] = sArticulo.split(":");
            Imagen imagen = new Imagen(Integer.parseInt(sPartes[2]));
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinComb");
    }
    
    public String obtenerPedido() throws Exception
    {  
        String sMensaje;
        String sPedido = "";
        sMensaje = _SocketDatos.recibeMensaje();
        while(!sMensaje.equals("FinTicket")){
            sPedido += sMensaje+"\n";
            sMensaje = _SocketDatos.recibeMensaje();
        }
        sPedido += "FinTicket";
        return sPedido;
    }

} // fin de class

