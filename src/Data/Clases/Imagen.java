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
import java.io.IOException;
import java.util.ArrayList;

public class Imagen {
	
    private int _iId;
    private String _sNombre;
    private String _sRuta;
    private boolean _bIsDeleted;

    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public String getRuta() {return _sRuta;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setRuta(String sRuta) {_sNombre = sRuta;}

    public Imagen(int iId) throws Exception {
        Connection con = null;
        ResultSet rs = null;
            try {
                    con = Data.Connection();
                    rs = con.createStatement().executeQuery("SELECT Id, Nombre, Ruta "
                                    + "FROM Imagen "
                                    + "WHERE Id = " + iId + ";");
                    rs.next();

                    _iId = iId;
                    _sNombre = rs.getString("Nombre");
                    _sRuta = rs.getString("Ruta");
        }
            catch (SQLException ee) { throw ee; }
            finally {
                    if (rs != null) rs.close();
                if (con != null) con.close();
            }
    }

    public String toString() {
        super.toString();
        String sMensaje = getId() + ":" + getNombre() + ":" + getRuta(); 
		
        return sMensaje; 
    }
	
    /**
     * Inserta una imagen en la base de datos
     * 
     * @param sNombre
     * @param sRuta
     * @return devuelve la imagen insertada
     * @throws Exception
     */
    public static Imagen Create(String sNombre, String sRuta) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Imagen (Nombre, Ruta)"
					+ " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                                        + Data.String2Sql(sRuta, true, false) + ");");
			
			return new Imagen(Data.LastId(con));
		}
		catch (SQLException ee) { throw ee; }
		finally {
	 	    if (con != null) con.close();
		}
	}
    
    /**
    * Elimina una imagen de la base de datos y marcamos la variable _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o si hay un error en la conexión.
    */
   public void Delete() throws Exception{
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate("DELETE FROM Imagen WHERE Id = " + _iId);
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
                   con.createStatement().executeUpdate("UPDATE Imagen "
                                   + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                                   + ", Ruta = " + Data.String2Sql(_sRuta, true, false)
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
    * @param sRuta sRuta  a buscar, si es nulo no se busca por Ruta
    * @return devuelve una lista de las imagenes que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Imagen> Select(
                   String sNombre, String sRuta) throws Exception{
           ArrayList<Imagen> aImagenes = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Imagen"
                                   + Where(sNombre, sRuta));

                   while(rs.next()) 
                           aImagenes.add(new Imagen(rs.getInt("Id")));

                   return aImagenes;
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
    * @param sRuta Ruta a buscar, si es nulo no se busca por Ruta
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, String sRuta) {
           String sWhere = "";

           if(sNombre != null) 
                   sWhere = " WHERE Nombre LIKE "+ Data.String2Sql(sNombre, true, true);

           if(sRuta != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Ruta LIKE "+ sRuta;
           }

           return sWhere;
   }
   
   public String getRutaCompleta() throws IOException{
       return Data.RutaImagenes()+_sRuta+_sNombre;
   }
}
