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
public class Articulo {
	
    private int _iId;
    private String _sNombre;
    private double _dPVP;
    private int _iId_Categoria;
    private ArrayList<Integer> _aiTallas;
    private boolean _bIsDeleted;

    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public double getPVP() {return _dPVP;}
    public int getId_Categoria() {return _iId_Categoria;}
    public ArrayList<Integer> getTallas() {return _aiTallas;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setPVP(double dPVP) {_dPVP = dPVP;}
    public void setId_Categoria(int iId_Categoria) {_iId_Categoria = iId_Categoria;}
    public void setTallas(ArrayList<Integer> aiTallas) {_aiTallas = aiTallas;}

    public Articulo(int iId) throws Exception {
        Connection con = null;
        ResultSet rs = null;
            try {
                    con = Data.Connection();
                    rs = con.createStatement().executeQuery("SELECT Id, Nombre, PVP, Id_Categoria "
                                    + "FROM Articulo "
                                    + "WHERE Id = " + iId + ";");
                    rs.next();

                    _iId = iId;
                    _sNombre = rs.getString("Nombre");
                    _dPVP = rs.getDouble("PVP");
                    _iId_Categoria = rs.getInt("Id_Categoria");
                    
                    rs = con.createStatement().executeQuery("SELECT Id_Talla "
                                    + "FROM articulo_talla "
                                    + "WHERE Id_Articulo = "+ iId +";");
                    _aiTallas = new ArrayList<>();
                    while(rs.next())
                        _aiTallas.add(rs.getInt("Id_Talla"));
        }
            catch (SQLException ee) { throw ee; }
            finally {
                    if (rs != null) rs.close();
                if (con != null) con.close();
            }
    }

    public String toString() {
        String sMensaje = getId() + ":" + getNombre() + ":" + getPVP() + ":" + getId_Categoria() + ":" + getTallas(); 
        return super.toString()+sMensaje; 
    }
	
    /**
     * Inserta un articulo en la base de datos
     * 
     * @param sNombre
     * @param dPVP
     * @param iId_Categoria
     * @param aiTallas
     * @return devuelve la categoria insertada
     * @throws Exception
     */
    public static Articulo Create(String sNombre, double dPVP, int iId_Categoria, ArrayList<Integer> aiTallas) 
            throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Articulo (Nombre, PVP, Id_Categoria)"
					+ " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                                        + dPVP + ", "
                                        + iId_Categoria + ");");
                        
                        int iId = Data.LastId(con); 
                        
                        if(aiTallas != null)
                            for(Integer iId_Talla : aiTallas)
                                con.createStatement().executeUpdate("INSERT INTO articulo_talla "
                                        + "(Id_Articulo, Id_Talla)"
					+ " VALUES (" + iId + ", " + iId_Talla + ");");
                                
			return new Articulo(iId);
		}
		catch (SQLException ee) { throw ee; }
		finally {
	 	    if (con != null) con.close();
		}
	}
    
    /**
    * Elimina un artículo de la base de datos y marcamos la variable _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminado o si hay un error en la conexión.
    */
   public void Delete() throws Exception{
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
                con = Data.Connection();
      
                con.createStatement().executeUpdate("DELETE FROM articulo_talla WHERE Id_Articulo = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM Articulo WHERE Id = " + _iId);
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
                   con.createStatement().executeUpdate("UPDATE Articulo "
                                   + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                                   + ", PVP = " + _dPVP
                                   + ", Id_Categoria = " + _iId_Categoria
                                   + " WHERE Id = " + _iId);
                   
                   if(_aiTallas != null){
                        con.createStatement().executeUpdate("DELETE FROM articulo_talla WHERE Id_Articulo = " + _iId);
                        for(Integer iId_Talla : _aiTallas)
                            con.createStatement().executeUpdate("INSERT INTO articulo_talla "
                                    + "(Id_Articulo, Id_Talla)"
                                    + " VALUES (" + _iId + ", " + iId_Talla + ");");
                   }
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
     * @param dPVP
     * @param iId_Categoria
    * @return devuelve una lista de los articulos que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Articulo> Select(
                   String sNombre, Double dPVP, Integer iId_Categoria) throws Exception{
           ArrayList<Articulo> aArticulos = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Articulo"
                                   + Where(sNombre, dPVP, iId_Categoria));

                   while(rs.next()) 
                           aArticulos.add(new Articulo(rs.getInt("Id")));

                   return aArticulos;
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
   private static String Where(String sNombre, Double dPVP, Integer iId_Categoria) {
           String sWhere = "";

           if(sNombre != null) 
                   sWhere = " WHERE Nombre LIKE "+ Data.String2Sql(sNombre, true, true);

           if(dPVP != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "PVP = "+ dPVP;
           }
           
           if(iId_Categoria != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Id_Categoria = "+ iId_Categoria;
           }

           return sWhere;
   }
}
