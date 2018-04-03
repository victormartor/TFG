/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class Categoria {
	
    private int _iId;
    private String _sNombre;
    private int _iId_Imagen;
    private int _iId_Marca;
    private boolean _bIsDeleted;

    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public int getId_Imagen() {return _iId_Imagen;}
    public int getId_Marca() {return _iId_Marca;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setId_Imagen(int iId_Imagen) {_iId_Imagen = iId_Imagen;}
    public void setId_Marca(int iId_Marca) {_iId_Marca = iId_Marca;}

    public Categoria(int iId) throws Exception {
        Connection con = null;
        ResultSet rs = null;
            try {
                    con = Data.Connection();
                    rs = con.createStatement().executeQuery("SELECT Id, Nombre, Id_Imagen, Id_Marca "
                                    + "FROM Categoria "
                                    + "WHERE Id = " + iId + ";");
                    rs.next();

                    _iId = iId;
                    _sNombre = rs.getString("Nombre");
                    _iId_Imagen = rs.getInt("Id_Imagen");
                    _iId_Marca = rs.getInt("Id_Marca");
        }
            catch (SQLException ee) { throw ee; }
            finally {
                    if (rs != null) rs.close();
                if (con != null) con.close();
            }
    }

    public String toString() {
        super.toString();
        String sMensaje = getId() + ":" + getNombre() + ":" + getId_Imagen() + ":" + getId_Marca(); 
        return sMensaje; 
    }
	
    /**
     * Inserta una categoria en la base de datos
     * 
     * @param sNombre
     * @param iId_Imagen
     * @param iId_Marca
     * @return devuelve la categoria insertada
     * @throws Exception
     */
    public static Categoria Create(String sNombre, int iId_Imagen, int iId_Marca) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Categoria (Nombre, Id_Imagen, Id_Marca)"
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
    * Elimina una categoria de la base de datos y marcamos la variable _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o si hay un error en la conexión.
    */
   public void Delete() throws Exception{
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate("DELETE FROM Categoria WHERE Id = " + _iId);
                _bIsDeleted = true;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores de las variables privadas.
    * 
    * @throws Exception Lanza una excepción si está eliminado o si hay error en la conexión
    */
   public void Update() throws Exception {
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
    * Realiza una consulta SELECT a la base de datos con los parámetros de búsqueda indicados.
    * 
    * @param sNombre Nombre a buscar, si es nulo no se busca por el Nombre
    * @param iId_Imagen Id_Imagen  a buscar, si es nulo no se busca por el Id_Imagen
     * @param iId_Marca Id_Marca a buscar, si es nulo no se busca por el Id_Marca
    * @return devuelve una lista de las categorias que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Categoria> Select(
                   String sNombre, Integer iId_Imagen, Integer iId_Marca) throws Exception{
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
           catch (SQLException ee) { throw ee; }
           finally {
                   if (rs != null) rs.close();
               if (con != null) con.close();
           }
   }

   /**
    * Comprueba los parámetros recibidos como no nulos y añade la búsqueda a la consulta where
    * 
    * @param sNombre Nombre a buscar, si es nulo no se busca por el Nombre
    * @param iId_Imagen Id_Imagen a buscar, si es nulo no se busca por el Id_Imagen
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, Integer iId_Imagen, Integer iId_Marca) {
           String sWhere = "";

           if(sNombre != null) 
                   sWhere = " WHERE Nombre LIKE "+ Data.String2Sql(sNombre, true, true);

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

