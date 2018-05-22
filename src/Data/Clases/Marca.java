/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data.Data;
import Data.Data;
import java.util.ArrayList;

public class Marca {
	
	private int _iId;
	private String _sNombre;
	private int _iId_Imagen;
        private boolean _bIsDeleted;
	
	public int getId() {return _iId;}	
	public String getNombre() {return _sNombre;}
	public int getId_Imagen() {return _iId_Imagen;}
        public boolean getIsDeleted() {return _bIsDeleted;}
	public void setNombre(String sNombre) {_sNombre = sNombre;}
	public void setId_Imagen(int iId_Imagen) {_iId_Imagen = iId_Imagen;}
	
	public Marca(int iId) throws Exception {
            Connection con = null;
	    ResultSet rs = null;
	 	try {
	 		con = Data.Connection();
	 		rs = con.createStatement().executeQuery("SELECT Id, Nombre, Id_Imagen "
	 				+ "FROM Marca "
	 				+ "WHERE Id = " + iId + ";");
	 		rs.next();
	 		
	 		_iId = iId;
	 		_sNombre = rs.getString("Nombre");
	 		_iId_Imagen = rs.getInt("Id_Imagen");
	    }
	 	catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
	 	    if (con != null) con.close();
		}
	}
	
	public String toString() {
		super.toString();
		
		String sMensaje = getId() + ":" + getNombre() + ":" + getId_Imagen(); 
		
        return sMensaje; 
    }
	
    /**
     * Inserta una marca en la base de datos
     * 
     * @param sNombre
     * @param iId_Imagen
     * @return devuelve la marca insertada
     * @throws Exception
     */
    public static Marca Create(String sNombre, int iId_Imagen) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Marca (Nombre, Id_Imagen)"
					+ " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                                        + iId_Imagen + ");");
			
			return new Marca(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally {
	 	    if (con != null) con.close();
		}
	}
    
    /**
    * Elimina una marca de la base de datos y marcamos la variable _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o si hay un error en la conexión.
    */
   public void Delete() throws Exception{
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate("DELETE FROM Marca WHERE Id = " + _iId);
                if(_iId_Imagen != -1) new Imagen(_iId_Imagen).Delete();
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
                   con.createStatement().executeUpdate("UPDATE Marca "
                                   + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                                   + ", Id_Imagen = " + _iId_Imagen
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
    * @return devuelve una lista de las marcas que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Marca> Select(
                   String sNombre, Integer iId_Imagen) throws Exception{
           ArrayList<Marca> aMarcas = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Marca"
                                   + Where(sNombre, iId_Imagen));

                   while(rs.next()) 
                           aMarcas.add(new Marca(rs.getInt("Id")));

                   return aMarcas;
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
   private static String Where(String sNombre, Integer iId_Imagen) {
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

           return sWhere;
   }
}
