package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Stock.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Stock 
{	
    private int _iId;
    private int _iId_Articulo;
    private int _iId_Color;
    private int _iId_Talla;
    private int _iExistencias;
    private boolean _bIsDeleted;

    private String _sMarca;
    private String _sCategoria;
    private String _sArticulo;
    private String _sColor;
    private String _sTalla;

    //GET
    public int getId() {return _iId;}
    public int getId_Articulo() {return _iId_Articulo;}	
    public int getId_Color() {return _iId_Color;}
    public int getId_Talla() {return _iId_Talla;}
    public int getExistencias() {return _iExistencias;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    public String getNombreMarca() {return _sMarca;}
    public String getNombreCategoria() {return _sCategoria;}
    public String getNombreArticulo(){return _sArticulo;}
    public String getNombreColor(){return _sColor;}
    public String getNombreTalla(){return _sTalla;}

    //SET
    public void setExistencias(int iExistencias) {_iExistencias = iExistencias;}

    /**
     * Constructor a partir de un Id obtiene el Stock de la base de datos.
     * @param iId Id del Stock.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Stock(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT "
                            + "Id_Articulo, Id_Color, Id_Talla, Existencias "
                            + "FROM Stock "
                            + "WHERE "
                            + "Id = " + iId+";");
            rs.next();

            _iId = iId;
            _iId_Articulo = rs.getInt("Id_Articulo");
            _iId_Color = rs.getInt("Id_Color");
            _iId_Talla = rs.getInt("Id_Talla");
            _iExistencias = rs.getInt("Existencias");

            _sMarca = new Marca(new Categoria(
                    new Articulo(_iId_Articulo).getId_Categoria())
                    .getId_Marca()).getNombre();
            _sCategoria = new Categoria(
                    new Articulo(_iId_Articulo).getId_Categoria()).getNombre();
            _sArticulo = new Articulo(_iId_Articulo).getNombre();
            _sColor = new Color(_iId_Color).getNombre();
            _sTalla = new Talla(_iId_Talla).getNombre();
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }
	
    /**
     * Devuelve un String con la información más importante.
     * @return Stock convertido en String.
     */
    @Override
    public String toString() 
    {
        return getId_Articulo() + "-" + getId_Color() + "-" + getId_Talla(); 
    }
	
    /**
     * Inserta un Stock en la base de datos.
     * 
     * @param iId_Articulo Id del artículo
     * @param iId_Color Id del color
     * @param iId_Talla Id de la talla
     * @return devuelve el Stock insertado.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public static Stock Create(int iId_Articulo, int iId_Color, int iId_Talla) 
            throws SQLException  
    {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("INSERT INTO Stock "
                            + "(Id_Articulo, Id_Color, Id_Talla, Existencias)"
                            + " VALUES (" + iId_Articulo + ", " 
                            + iId_Color + ", "
                            + iId_Talla + ",0);");

            return new Stock(Data.LastId(con));
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina un Stock de la base de datos y marcamos la variable 
    * _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminado o 
    * si hay un error en la conexión.
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("DELETE FROM Stock WHERE "
                                    + "Id = " + _iId + ";");
            _bIsDeleted = true;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores 
    * de las variables privadas.
    * 
    * @throws Exception Lanza una excepción si está eliminado o 
    * si hay error en la conexión
    */
   public void Update() throws Exception 
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("UPDATE Stock "
                            + "SET Existencias = " + _iExistencias
                            + " WHERE "
                             + "Id = " + _iId + ";");
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Realiza una consulta SELECT a la base de datos con los 
    * parámetros de búsqueda indicados.
    * 
    * @param iId_Articulo Id del artículo.
    * @param iId_Color Id del color.
    * @param iId_Talla Id de la talla.
    * @param iExistencias Número de existencias de este Stock.
    * @return devuelve una lista de los Stocks que coincidan con los 
    * parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Stock> Select(Integer iId_Articulo, 
           Integer iId_Color, Integer iId_Talla, Integer iExistencias) 
           throws SQLException 
   {
        ArrayList<Stock> aStock = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id FROM Stock"
                    + Where(iId_Articulo, iId_Color, iId_Talla, iExistencias));

            while(rs.next()) 
                    aStock.add(new Stock(rs.getInt("Id")));

            return aStock;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
   }

   /**
    * Comprueba los parámetros recibidos como no nulos y añade 
    * la búsqueda a la consulta where
    * 
    * @param iId_Articulo Id del artículo.
    * @param iId_Color Id del color.
    * @param iId_Talla Id de la talla.
    * @param iExistencias Número de existencias de este Stock.
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(Integer iId_Articulo, Integer iId_Color, 
           Integer iId_Talla, Integer iExistencias) 
   {
        String sWhere = "";

        if(iId_Articulo != null) 
                sWhere = " WHERE Id_Articulo = "+ iId_Articulo;

        if(iId_Color != null) 
        {
            if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "Id_Color = "+ iId_Color;
        }

        if(iId_Talla != null) 
        {
            if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "Id_Talla = "+ iId_Talla;
        }

        if(iExistencias != null) 
        {
            if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "Existencias = "+ iExistencias;
        }

        return sWhere;
   }
   
   /**
    * Realiza una búsqueda en la tabla Stock según el nombre del artículo.
    * 
    * @param sArticulo Nombre del artículo a buscar.
    * @return Devuelve una lista de Stock que contengan el artículo.
    * @throws java.sql.SQLException Hay un error en la conexión.
   */
   public static ArrayList<Stock> Search(String sArticulo) throws SQLException 
   {
        ArrayList<Stock> aStock = new ArrayList<>();

        ArrayList<Articulo> aArticulo = 
                Articulo.Select(sArticulo, null, null, null);
        
        for(Articulo articulo : aArticulo)
            aStock.addAll(Stock.Select(articulo.getId(), null, null, null));

        return aStock;          
   }
}
