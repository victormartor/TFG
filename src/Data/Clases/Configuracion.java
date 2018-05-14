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
public class Configuracion {
	
	private int _iId;
	private String _sCampo;
	private String _sValor;
	
	public int getId() {return _iId;}	
	public String getCampo() {return _sCampo;}
	public String getValor() {return _sValor;}
	public void setCampo(String sCampo) {_sCampo = sCampo;}
	public void setValor(String sValor) {_sValor = sValor;}
	
	public Configuracion(int iId) throws Exception {
            Connection con = null;
	    ResultSet rs = null;
	 	try {
	 		con = Data.Connection();
	 		rs = con.createStatement().executeQuery("SELECT Id, Campo, Valor "
	 				+ "FROM Configuracion "
	 				+ "WHERE Id = " + iId + ";");
	 		rs.next();
	 		
	 		_iId = iId;
	 		_sCampo = rs.getString("Campo");
	 		_sValor = rs.getString("Valor");
	    }
	 	catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
	 	    if (con != null) con.close();
		}
	}
	
    public String toString() {

	String sMensaje = getId() + ":" + getCampo() + ":" + getValor(); 	
        return sMensaje; 
    }
	

   /**
    * Actualiza el registro en la base de datos con los valores de las variables privadas.
    * 
    * @throws Exception Lanza una excepción si está eliminado o si hay error en la conexión
    */
   public void Update() throws Exception {

           Connection con = null;
           try {
                   con = Data.Connection();
                   con.createStatement().executeUpdate("UPDATE Configuracion "
                                   + "SET Campo = " + Data.String2Sql(_sCampo, true, false)
                                   + ", Valor = " + Data.String2Sql(_sValor, true, false)
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
   public static ArrayList<Configuracion> Select(
                   String sCampo, String sValor) throws Exception{
           ArrayList<Configuracion> aConfiguracion = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Configuracion"
                                   + Where(sCampo, sValor));

                   while(rs.next()) 
                           aConfiguracion.add(new Configuracion(rs.getInt("Id")));

                   return aConfiguracion;
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
   private static String Where(String sCampo, String sValor) {
           String sWhere = "";

           if(sCampo != null) 
                   sWhere = " WHERE Campo LIKE "+ Data.String2Sql(sCampo, true, true);

           if(sValor != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Valor LIKE "+ Data.String2Sql(sCampo, true, true);
           }

           return sWhere;
   }
}

