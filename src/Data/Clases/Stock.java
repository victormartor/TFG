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
public class Stock {
	
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
        
	public void setExistencias(int iExistencias) {_iExistencias = iExistencias;}
	
	public Stock(int iId_Articulo, int iId_Color, int iId_Talla) throws Exception {
            Connection con = null;
	    ResultSet rs = null;
	 	try {
	 		con = Data.Connection();
	 		rs = con.createStatement().executeQuery("SELECT "
                                        + "Id_Articulo, Id_Color, Id_Talla, Existencias "
	 				+ "FROM Stock "
	 				+ "WHERE "
                                        + "Id_Articulo = " + iId_Articulo 
                                        + " AND Id_Color = " + iId_Color 
                                        + " AND Id_Talla = " + iId_Talla + ";");
	 		rs.next();
	 		
	 		_iId_Articulo = iId_Articulo;
                        _iId_Color = iId_Color;
                        _iId_Talla = iId_Talla;
                        _iExistencias = rs.getInt("Existencias");
                        
                        _sMarca = new Marca(new Categoria(
                                new Articulo(iId_Articulo).getId_Categoria()).getId_Marca()).getNombre();
                        _sCategoria = new Categoria(new Articulo(iId_Articulo).getId_Categoria()).getNombre();
                        _sArticulo = new Articulo(iId_Articulo).getNombre();
                        _sColor = new Color(iId_Color).getNombre();
                        _sTalla = new Talla(iId_Talla).getNombre();
	    }
	 	catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
	 	    if (con != null) con.close();
		}
	}
	
    public String toString() {
        return getId_Articulo() + ":" + getId_Color() + ":" + getId_Talla() + ":" + getExistencias(); 
    }
	
    /**
     * Inserta una marca en la base de datos
     * 
     * @param sNombre
     * @param iId_Imagen
     * @return devuelve la marca insertada
     * @throws Exception
     */
    public static Stock Create(int iId_Articulo, int iId_Color, int iId_Talla) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Stock "
                                        + "(Id_Articulo, Id_Color, Id_Talla, Existencias)"
					+ " VALUES (" + iId_Articulo + ", " 
                                        + iId_Color + ", "
                                        + iId_Talla + ",0);");
			
			return new Stock(iId_Articulo, iId_Color, iId_Talla);
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
                con.createStatement().executeUpdate("DELETE FROM Marca WHERE "
                                        + "Id_Articulo = " + _iId_Articulo 
                                        + " AND Id_Color = " + _iId_Color 
                                        + " AND Id_Talla = " + _iId_Talla + ";");
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
                   con.createStatement().executeUpdate("UPDATE Stock "
                                   + "SET Existencias = " + _iExistencias
                                   + " WHERE "
                                    + "Id_Articulo = " + _iId_Articulo 
                                    + " AND Id_Color = " + _iId_Color 
                                    + " AND Id_Talla = " + _iId_Talla + ";");
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
   public static ArrayList<Stock> Select(
                   Integer iId_Articulo, Integer iId_Color, Integer iId_Talla, Integer iExistencias) throws Exception{
           ArrayList<Stock> aStock = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT "
                                    + "Id_Articulo, Id_Color, Id_Talla "
                                    + "FROM Stock"
                                    + Where(iId_Articulo, iId_Color, iId_Talla, iExistencias));

                   while(rs.next()) 
                           aStock.add(new Stock(rs.getInt("Id_Articulo"), 
                                   rs.getInt("Id_Color"), 
                                   rs.getInt("Id_Talla")));

                   return aStock;
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
   private static String Where(Integer iId_Articulo, Integer iId_Color, Integer iId_Talla, Integer iExistencias) {
           String sWhere = "";

           if(iId_Articulo != null) 
                   sWhere = " WHERE Id_Articulo = "+ iId_Articulo;

           if(iId_Color != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Id_Color = "+ iId_Color;
           }
           
           if(iId_Talla != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Id_Talla = "+ iId_Talla;
           }
           
           if(iExistencias != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Existencias = "+ iExistencias;
           }

           return sWhere;
   }
   
   public static ArrayList<Stock> Search(String sArticulo) throws Exception{
           ArrayList<Stock> aStock = new ArrayList<>();

           try {
                   ArrayList<Articulo> aArticulo = Articulo.Select(sArticulo, null, null, null);
                   for(Articulo articulo : aArticulo){
                       aStock.addAll(Stock.Select(articulo.getId(), null, null, null));
                   }

                   return aStock;
           }
           catch (SQLException ee) { throw ee; }
   }
}
