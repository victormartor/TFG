/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class Pedido {
	
	private int _iId;
        private Date _Fecha;
	private int _iNumArticulos;
        private double _dTotal;
        private Integer _iCodPostal;
        private String _sDirEnvio;
        private ArrayList<Integer> _aiArticulosStock;
        private boolean _bIsDeleted;
	
        //GET
	public int getId() {return _iId;}
        public Date getFecha() {return _Fecha;}
        public int getNumArticulos() {return _iNumArticulos;}
        public double getTotal() {return _dTotal;}
        public Integer getCodPostal() {return _iCodPostal;}
        public String getDirEnvio() {return _sDirEnvio;}
        public ArrayList<Integer> getArticulosStock() {return _aiArticulosStock;}
        public boolean getIsDeleted() {return _bIsDeleted;}
        
        //SET
        public void setId(int iId) {_iId = iId;}
        public void setFecha(Date Fecha) {_Fecha = Fecha;}
        public void setNumArticulos(int iNumArticulos) {_iNumArticulos = iNumArticulos;}
        public void setTotal(double dTotal) { _dTotal = dTotal;}
        public void setCodPostal(Integer iCodPostal) {_iCodPostal = iCodPostal;}
        public void setsDirEnvio(String sDirEnvio) { _sDirEnvio = sDirEnvio;}
        public void setArticulosStock(ArrayList<Integer> articulosStock) {_aiArticulosStock = articulosStock;}
	
	public Pedido(int iId) throws Exception {
            Connection con = null;
	    ResultSet rs = null;
	 	try {
	 		con = Data.Connection();
	 		rs = con.createStatement().executeQuery("SELECT Id, Fecha, "
                                + "NumArticulos, Total, CodPostal, DirEnvio "
	 				+ "FROM Pedido "
	 				+ "WHERE Id = " + iId + ";");
	 		rs.next();
	 		
	 		_iId = iId;
	 		_Fecha = rs.getDate("Fecha");
                        _iNumArticulos = rs.getInt("NumArticulos");
                        _dTotal = rs.getDouble("Total");
                        _iCodPostal = rs.getInt("CodPostal");
                        _sDirEnvio = rs.getString("DirEnvio");
                        
                        //CARGAR LA LISTA DE ARTÍCULOS ASOCIADOS AL PEDIDO
                        rs = con.createStatement().executeQuery("SELECT Id_Stock "
                                        + "FROM pedido_stock "
                                        + "WHERE Id_Pedido = "+ iId +";");
                        _aiArticulosStock = new ArrayList<>();
                        while(rs.next())
                            _aiArticulosStock.add(rs.getInt("Id_Stock"));
	    }
	 	catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
	 	    if (con != null) con.close();
		}
	}
	
    public String toString() {
	return getId() + ":" + getFecha() + ":" + getNumArticulos()
                 + ":" + getTotal() + ":" + getCodPostal()
                 + ":" + getDirEnvio() + ":" + getArticulosStock(); 
    }
	
    /**
     * Inserta una marca en la base de datos
     * 
     * @param sNombre
     * @param iId_Imagen
     * @return devuelve la marca insertada
     * @throws Exception
     */
    public static Pedido Create(Date Fecha, int iNumArticulos, double dTotal,
            Integer iCodPostal, String sDirEnvio, ArrayList<Integer> aiArticulosStock) throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Pedido "
                                + "(Fecha, NumArticulos, Total, CodPostal, DirEnvio)"
				+ " VALUES ("+ Data.String2Sql(Fecha.toString(), true, false) +", "+
                                iNumArticulos +", "+
                                dTotal +", "+
                                iCodPostal +", "+
                                Data.String2Sql(sDirEnvio, true, false) +");");
			int iId = Data.LastId(con);
                        
                        //ASIGNAR ARTÍCULOS ASOCIADOS
                        if(aiArticulosStock != null)
                            for(Integer iId_Stock : aiArticulosStock)
                                con.createStatement().executeUpdate("INSERT INTO pedido_stock "
                                + "(Id_Pedido, Id_Stock)"
				+ " VALUES (" + iId + ", " + iId_Stock + ");");
                        
			return new Pedido(iId);
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
                con.createStatement().executeUpdate("DELETE FROM pedido_stock WHERE Id_Pedido = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM Pedido WHERE Id = " + _iId);
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
                   con.createStatement().executeUpdate("UPDATE Pedido "
                                   + "SET Fecha = " +Data.String2Sql(_Fecha.toString(), true, false)
                                   + ", NumArticulos = " +_iNumArticulos
                                   + ", Total = " +_dTotal
                                   + ", CodPostal = " + _iCodPostal
                                   + ", DirEnvio = " + Data.String2Sql(_sDirEnvio, true, false)
                                   + " WHERE Id = " + _iId);
                   
                   //ACTUALIZAR ARTICULOS ASOCIADOS
                   if(_aiArticulosStock != null){
                        con.createStatement().executeUpdate("DELETE FROM pedido_stock WHERE Id_Pedido = " + _iId);
                        for(Integer iId_Stock : _aiArticulosStock)
                            con.createStatement().executeUpdate("INSERT INTO pedido_stock "
                                    + "(Id_Pedido, Id_Stock)"
                                    + " VALUES (" + _iId + ", " + iId_Stock + ");");
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
    * @param iId_Imagen Id_Imagen  a buscar, si es nulo no se busca por el Id_Imagen
    * @return devuelve una lista de las marcas que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Pedido> Select(Date Fecha, Integer iNumArticulos, Double dTotal,
            Integer iCodPostal, String sDirEnvio) throws Exception{
           ArrayList<Pedido> aPedidos = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Pedido"
                                   + Where(Fecha, iNumArticulos, dTotal,
                                    iCodPostal, sDirEnvio));

                   while(rs.next()) 
                           aPedidos.add(new Pedido(rs.getInt("Id")));

                   return aPedidos;
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
   private static String Where(Date Fecha, Integer iNumArticulos, Double dTotal,
            Integer iCodPostal, String sDirEnvio) {
           String sWhere = "";

           if(Fecha != null) 
                   sWhere = " WHERE Fecha LIKE "+ Data.String2Sql(Fecha.toString(), true, true);

           if(iNumArticulos != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "NumArticulos = "+ iNumArticulos;
           }
            
            if(dTotal != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Total = "+ dTotal;
            }
                   
            if(iCodPostal != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "CodPostal = "+ iCodPostal;
            }
                   
            if(sDirEnvio != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "DirEnvio LIKE "+ Data.String2Sql(sDirEnvio, true, true);
           }

           return sWhere;
   }
}
