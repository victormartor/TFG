package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Categoria.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Categoria 
{	
    private int _iId;
    private String _sNombre;
    private int _iId_Imagen;
    private int _iId_Marca;
    private boolean _bIsDeleted;

    //GET
    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public int getId_Imagen() {return _iId_Imagen;}
    public int getId_Marca() {return _iId_Marca;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    //SET
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setId_Imagen(int iId_Imagen) {_iId_Imagen = iId_Imagen;}
    public void setId_Marca(int iId_Marca) {_iId_Marca = iId_Marca;}

    /**
     * Constructor a partir de un Id obtiene la categoría de la base de datos
     * @param iId Id de la categoría
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Categoria(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery(
                    "SELECT Id, Nombre, Id_Imagen, Id_Marca "
                    + "FROM Categoria WHERE Id = " + iId + ";");
            rs.next();

            _iId = iId;
            _sNombre = rs.getString("Nombre");
            _iId_Imagen = rs.getInt("Id_Imagen");
            _iId_Marca = rs.getInt("Id_Marca");
        }
        catch (SQLException e) { throw e; }
        finally {
                if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }

    /**
     * Devuelve un String con los datos más importantes de la categoría
     * @return Categoría convertida en String
     */
    @Override
    public String toString() 
    {
        return getId() + ":" + getNombre() + ":" + getId_Imagen() + ":" 
                + getId_Marca(); 
    }
	
    /**
     * Inserta una categoria en la base de datos
     * 
     * @param sNombre Nombre de la categoría
     * @param iId_Imagen Id de la imagen asociada
     * @param iId_Marca Id de la marca a la que pertenece
     * @return devuelve la categoria insertada
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public static Categoria Create(String sNombre, int iId_Imagen, 
            int iId_Marca) throws SQLException 
    {
        Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate(
                    "INSERT INTO Categoria (Nombre, Id_Imagen, Id_Marca)"
                    + " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                    + iId_Imagen + ", "
                    + iId_Marca + ");");

                return new Categoria(Data.LastId(con));
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina una categoria de la base de datos y marcamos la variable 
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
            con.createStatement().executeUpdate(
                    "DELETE FROM Categoria WHERE Id = " + _iId);
            if(_iId_Imagen != -1) new Imagen(_iId_Imagen).Delete();
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
            con.createStatement().executeUpdate("UPDATE Categoria "
                + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                + ", Id_Imagen = " + _iId_Imagen
                + ", Id_Marca = " + _iId_Marca
                + " WHERE Id = " + _iId);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Realiza una consulta SELECT a la base de datos con los parámetros de 
    * búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
    * 
    * @param sNombre Nombre de la categoría
    * @param iId_Imagen Id de la imagen asociada
    * @param iId_Marca Id de la marca a la que pertenece
    * @return devuelve una lista de las categorias que coincidan con 
    * los parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Categoria> Select(
            String sNombre, Integer iId_Imagen, Integer iId_Marca) 
           throws SQLException           
   {
        ArrayList<Categoria> aCategorias = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id FROM Categoria"
                            + Where(sNombre, iId_Imagen, iId_Marca));

            while(rs.next()) 
                    aCategorias.add(new Categoria(rs.getInt("Id")));

            return aCategorias;
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
    * @param sNombre Nombre de la categoría
    * @param iId_Imagen Id de la imagen asociada
    * @param iId_Marca Id de la marca a la que pertenece
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, Integer iId_Imagen, 
           Integer iId_Marca) 
   {
        String sWhere = "";

        if(sNombre != null) 
                sWhere = " WHERE Nombre LIKE "
                        + Data.String2Sql(sNombre, true, true);

        if(iId_Imagen != null) {
                if(sWhere.equals(""))
                        sWhere = " WHERE ";
                else 
                        sWhere += " AND ";

                sWhere += "Id_Imagen = "+ iId_Imagen;
        }

        if(iId_Marca != null) {
                if(sWhere.equals(""))
                        sWhere = " WHERE ";
                else 
                        sWhere += " AND ";

                sWhere += "Id_Marca = "+ iId_Marca;
        }

        return sWhere;
   }
}

