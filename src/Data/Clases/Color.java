package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Color.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Color 
{
    private int _iId;
    private String _sNombre;
    private boolean _bIsDeleted;

    //GET
    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    //SET
    public void setNombre(String sNombre) {_sNombre = sNombre;}

    /**
     * Constructor a partir de un Id obtiene el color de la base de datos
     * @param iId Id del color
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Color(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id, Nombre "
                            + "FROM color "
                            + "WHERE Id = " + iId + ";");
            rs.next();

            _iId = iId;
            _sNombre = rs.getString("Nombre");
        }
        catch (SQLException e) { throw e; }
        finally {
                if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }

    /**
     * Devuelve un String con el nombre del color
     * @return Color convertido en String
     */
    @Override
    public String toString() {
        return  getNombre(); 
    }
	
    /**
     * Inserta un color en la base de datos
     * 
     * @param sNombre Nombre del color.
     * @return devuelve el color insertado
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public static Color Create(String sNombre) throws SQLException  
    {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                "INSERT INTO color (Nombre)"
                + " VALUES (" + Data.String2Sql(sNombre, true, false) + ");");

            return new Color(Data.LastId(con));
        }
        catch (SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina un color de la base de datos y marcamos la variable 
    * _bIsDeleted a true.
    * 
    * @throws Exception Hay un error en la conexión o ya ha sido eliminada.
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();

            if(con.createStatement().executeQuery("SELECT * "
                    + "FROM articulo_color "
                    + "WHERE Id_Color = "+_iId).next())
                throw new Exception(){
                    @Override
                    public String toString(){
                        return "Este color está asociado a un artículo.";
                    }
                };

            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color WHERE Id_Color = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color_imagen WHERE Id_Color = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM stock WHERE Id_Color = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM color WHERE Id = " + _iId);

            ResultSet rs = con.createStatement().executeQuery("SELECT Id_Imagen FROM Articulo_Color_Imagen "
                    + "WHERE Id_Color = "+_iId);
            while(rs.next())
                new Imagen(rs.getInt("Id_Imagen")).Delete();

            _bIsDeleted = true;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores de las 
    * variables privadas.
    * 
    * @throws Exception Hay un error en la conexión o ya ha sido eliminada.
    */
   public void Update() throws Exception 
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("UPDATE color "
                    + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                    + " WHERE Id = " + _iId);
        }
        catch (SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Realiza una consulta SELECT a la base de datos con los parámetros 
    * de búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
    * 
    * @param sNombre Nombre del color
    * @return devuelve una lista de los colores que coincidan con los 
    * parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Color> Select(
                   String sNombre) throws SQLException
   {
        ArrayList<Color> aColores = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
                con = Data.Connection();
                rs = con.createStatement().executeQuery("SELECT Id FROM color"
                                + Where(sNombre)+
                                 " ORDER BY Nombre");

                while(rs.next()) 
                        aColores.add(new Color(rs.getInt("Id")));

                return aColores;
        }
        catch (SQLException e) { throw e; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
   }

   /**
    * Comprueba los parámetros recibidos como no nulos y añade la búsqueda 
    * a la consulta where
    * 
    * @param sNombre Nombre del color
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre) 
   {
        String sWhere = "";

        if(sNombre != null) 
                sWhere = " WHERE Nombre LIKE "
                        + Data.String2Sql(sNombre, true, true);

        if(sWhere.equals(""))
                 sWhere = " WHERE ";
         else 
                 sWhere += " AND ";

         sWhere += "Id != -1";

        return sWhere;
   }
}


