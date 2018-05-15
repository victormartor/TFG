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
public class Color {
	
	private int _iId;
	private String _sNombre;
        private boolean _bIsDeleted;
	
	public int getId() {return _iId;}	
	public String getNombre() {return _sNombre;}
        public boolean getIsDeleted() {return _bIsDeleted;}
	public void setNombre(String sNombre) {_sNombre = sNombre;}
	
	public Color(int iId) throws Exception {
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
	 	catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
	 	    if (con != null) con.close();
		}
	}
	
	public String toString() {
            return  getNombre(); 
        }
	
    /**
     * Inserta una talla en la base de datos
     * 
     * @param sNombre
     * @param bEs_Numero
     * @return devuelve la talla insertada
     * @throws Exception
     */
    public static Color Create(String sNombre) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO color (Nombre)"
					+ " VALUES (" + Data.String2Sql(sNombre, true, false) + ");");
			
			return new Color(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally {
	 	    if (con != null) con.close();
		}
	}
    
    /**
    * Elimina una talla de la base de datos y marcamos la variable _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o si hay un error en la conexión.
    */
   public void Delete() throws Exception{
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate("DELETE FROM articulo_color WHERE Id_Color = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM articulo_color_imagen WHERE Id_Color = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM stock WHERE Id_Color = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM color WHERE Id = " + _iId);
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
                   con.createStatement().executeUpdate("UPDATE color "
                                   + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
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
     * @param Es_Numero
    * @return devuelve una lista de las marcas que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Color> Select(
                   String sNombre) throws Exception{
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
    * @param iId_Imagen Id_Imagen del luchador a buscar, si es nulo no se busca por el Id_Imagen
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre) {
           String sWhere = "";

           if(sNombre != null) 
                   sWhere = " WHERE Nombre LIKE "+ Data.String2Sql(sNombre, true, true);
           
           if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "Id != -1";
            
           return sWhere;
   }
}


