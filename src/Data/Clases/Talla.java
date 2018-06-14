package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Talla.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Talla 
{	
    private int _iId;
    private String _sNombre;
    private Boolean _bEs_Numero;
    private boolean _bIsDeleted;

    //GET
    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public Boolean getEs_Numero() {return _bEs_Numero;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    //SET
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setEs_Numero(Boolean bEs_Numero){_bEs_Numero = bEs_Numero;}

    /**
     * Constructor a partir de un Id obtiene la talla de la base de datos.
     * @param iId Id de la talla.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Talla(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery(
                    "SELECT Id, Nombre, Es_Numero "
                    + "FROM talla "
                    + "WHERE Id = " + iId + ";");
            rs.next();

            _iId = iId;
            _sNombre = rs.getString("Nombre");
            _bEs_Numero = rs.getBoolean("Es_Numero");
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }

    /**
     * Devuelve un String con la información más importante.
     * @return Nombre de la Talla.
     */
    @Override
    public String toString() 
    {
        return getNombre(); 
    }
	
    /**
     * Inserta una talla en la base de datos
     * 
     * @param sNombre Nombre de la talla.
     * @param bEs_Numero La talla es un número.
     * @return devuelve la talla insertada
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public static Talla Create(String sNombre, boolean bEs_Numero) 
            throws SQLException  
    {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                    "INSERT INTO Talla (Nombre, Es_Numero)"
                    + " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                    + Data.Boolean2Sql(bEs_Numero) + ");");

            return new Talla(Data.LastId(con));
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina una talla de la base de datos y marcamos la variable
    * _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o 
    * si hay un error en la conexión.
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                    "DELETE FROM Talla WHERE Id = " + _iId);
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
            con.createStatement().executeUpdate("UPDATE Talla "
                    + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                    + ", Es_Numero = " + Data.Boolean2Sql(_bEs_Numero)
                    + " WHERE Id = " + _iId);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Realiza una consulta SELECT a la base de datos con los parámetros 
    * de búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
    * 
    * @param sNombre Nombre de la talla.
    * @param Es_Numero La talla es un número.
    * @return devuelve una lista de las tallas que coincidan con 
    * los parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Talla> Select(String sNombre, Boolean Es_Numero) 
           throws SQLException 
   {
        ArrayList<Talla> aTallas = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
            String sQuery = "SELECT Id FROM Talla" + Where(sNombre, Es_Numero);
            if(Es_Numero) sQuery += " ORDER BY Nombre";
            
            con = Data.Connection();
            rs = con.createStatement().executeQuery(sQuery);

            while(rs.next()) 
                    aTallas.add(new Talla(rs.getInt("Id")));

            return aTallas;
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
    * @param sNombre Nombre de la talla.
    * @param Es_Numero La talla es un número.
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, Boolean Es_Numero) 
   {
        String sWhere = "";

        if(sNombre != null) 
            sWhere =" WHERE Nombre LIKE "+ Data.String2Sql(sNombre, true, true);

        if(Es_Numero != null)
        {
            if(sWhere.equals(""))
                 sWhere = " WHERE ";
             else 
                 sWhere += " AND ";

             sWhere += "Es_Numero = "+ Data.Boolean2Sql(Es_Numero);
        }

        return sWhere;
   }
}

