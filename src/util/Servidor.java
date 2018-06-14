package util;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Clases.Talla;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de proporcionar los métodos para interactuar con la 
 * aplicación android.
 * 
 * @author Víctor Martín Torres - 12/06/2018
 * @see SocketStream
 */
public class Servidor
{
    private boolean _bEncendido;
    private ServerSocket _SocketConexion;
    private SocketStream _SocketDatos;
    
    /**
     * Indica si el servidor está encendido o no.
     * @return El servidor está encendido
     */
    public boolean encendido() {return _bEncendido;}
    
    /**
     * Método para encender el servidor
     * @throws java.io.IOException Error al encender el servidor
     */
    public void encenderServidor() throws IOException
    {
        _bEncendido = true;
        _SocketConexion =  new ServerSocket(5000);     
    }
    
    /**
     * Realizar una conexión 
     * @throws java.io.IOException Error en la conexión
     */
    public void conectar() throws IOException 
    {
      _SocketDatos = new SocketStream (_SocketConexion.accept( ));            
    }
    
    /**
     * Método para apagar el servidor 
     * @throws java.io.IOException Error al apagar el servidor
     */
    public void apagarServidor() throws IOException
    {
        _bEncendido = false;
 
        if(_SocketDatos != null)
        {
            _SocketDatos.closeServer();
            _SocketDatos.close();
        }

        _SocketConexion.close();
         
    }
    
    /**
     * Método para obtener un mensaje del cliente.
     * @return Devuelve el mensaje en forma de String.
     * @throws IOException Error en la conexión
     */
    public String obtenerMensaje() throws IOException
    {
        return _SocketDatos.recibeMensaje();
    }
    
    /**
     * Método para enviar un mensaje al cliente
     * @param sMensaje El mensaje a enviar.
     * @throws IOException Error en la conexión
     */
    public void enviarMensaje(String sMensaje) throws IOException
    {
        _SocketDatos.enviaMensaje(sMensaje);    
    }
    
    /**
     * Método para enviar la lista de marcas al cliente
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarMarcas() throws SQLException, IOException 
    {
        ArrayList<Marca> aMarcas = Marca.Select(null, null);
        for(Marca marca : aMarcas)
        {
            _SocketDatos.enviaMensaje(marca.toString());
            Imagen imagen = new Imagen(marca.getId_Imagen());
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinMarcas");
    }
    
    /**
     * Método para enviar una marca al cliente.
     * @param iId_Marca El Id de la Marca
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarMarca(int iId_Marca) throws SQLException, IOException 
    {    
        Marca marca = new Marca(iId_Marca);

        _SocketDatos.enviaMensaje(marca.toString());
        Imagen imagen = new Imagen(marca.getId_Imagen());
        _SocketDatos.enviaMensaje(imagen.getNombre());
    }
    
    /**
     * Método para enviar la lista de categorías al cliente
     * @param iId_Marca El Id de la marca a la que pertenecen las categorías
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarCategorias(int iId_Marca) throws SQLException, IOException
    {
        ArrayList<Categoria> aCategorias = 
                Categoria.Select(null, null, iId_Marca);
        
        for(Categoria categoria : aCategorias)
        {
            _SocketDatos.enviaMensaje(categoria.toString());
            Imagen imagen = new Imagen(categoria.getId_Imagen());
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinCategorias");
    }
    
    /**
     * Método para enviar una lista de artículos al cliente
     * @param iId_Categoria El Id de la categoría a la que pertenecen los 
     * artículos
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarArticulos(int iId_Categoria) throws SQLException, IOException
    {
        ArrayList<Articulo> aArticulos = 
                Articulo.Select(null, null, iId_Categoria, null);
        
        for(Articulo articulo : aArticulos)
        {
            String sArticulo = articulo.toString();
            _SocketDatos.enviaMensaje(sArticulo);
            String sPartes[] = sArticulo.split(":");
            Imagen imagen = new Imagen(Integer.parseInt(sPartes[2]));
            _SocketDatos.enviaMensaje(imagen.getNombre());
        }
        _SocketDatos.enviaMensaje("FinArticulos"); 
    }
    
    /**
     * Método paraa enviar un artículo al cliente
     * @param iId_Articulo El Id del artículo
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarUnArticulo(int iId_Articulo) 
            throws SQLException, IOException
    { 
        Articulo articulo = new Articulo(iId_Articulo);
        _SocketDatos.enviaMensaje(articulo.toString());
    }
    
    /**
     * Método para enviar la lista de colores que pertenecen a un artículo al
     * cliente.
     * @param iId_Articulo El Id del artículo.
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public void enviarColoresArticulo(int iId_Articulo) 
            throws SQLException, IOException
    {
        Articulo articulo = new Articulo(iId_Articulo);
        
        for(Integer i : articulo.getColores())
        {
            Color color = new Color(i);
            String sColor = color.getId()+":"+color.getNombre();
            _SocketDatos.enviaMensaje(sColor);
            
            for(Imagen imagen : articulo.Get_Imagenes_Color(color.getId()))
            {
                _SocketDatos.enviaMensaje(imagen.getNombre());
            }
            _SocketDatos.enviaMensaje("FinImagenes");
        }
        _SocketDatos.enviaMensaje("FinColores");
    }
    
    /**
     * Método para enviar la lista de tallas que pertenecen a un artículo al 
     * cliente.
     * @param iId_Articulo El Id del artículo.
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión.
     */
    public void enviarTallasArticulo(int iId_Articulo) 
            throws SQLException, IOException
    {    
        Articulo articulo = new Articulo(iId_Articulo);
        ArrayList<Talla> aTallas = 
                Talla.Select(null, articulo.getTalla_Es_Numero());
        
        for(Talla talla : aTallas)
        {
            if(articulo.getTallas().contains(talla.getId()))
            {
                String sTalla = talla.getId()+":"+talla.getNombre();
                _SocketDatos.enviaMensaje(sTalla);
            }
        }
        _SocketDatos.enviaMensaje("FinTallas"); 
    }
    
    /**
     * Método para enviar las combinaciones que pertenecen a un artículo al
     * cliente.
     * @param iId_Articulo El Id del artículo
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión.
     */
    public void enviarCombinacionesArticulo(int iId_Articulo) 
            throws SQLException, IOException
    {  
        Articulo articulo = new Articulo(iId_Articulo);
        
        for(Integer i : articulo.getCombinaciones())
        {
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
    
    /**
     * Método para obtener el pedido del cliente.
     * @return Devuelve el pedido en forma de String.
     * @throws SQLException Error al acceder a la base de datos
     * @throws IOException Error en la conexión
     */
    public String obtenerPedido() throws SQLException, IOException
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
} 

