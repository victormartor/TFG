package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Configuracion.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Configuracion 
{
    private int _iId;
    private String _sCampo;
    private String _sValor;

    //GET
    public int getId() {return _iId;}	
    public String getCampo() {return _sCampo;}
    public String getValor() {return _sValor;}
    
    //SET
    public void setCampo(String sCampo) {_sCampo = sCampo;}
    public void setValor(String sValor) {_sValor = sValor;}

    /**
     * Constructor a partir de un Id obtiene la configuración de la 
     * base de datos.
     * @param iId Id de la configuración.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Configuracion(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id, Campo, Valor "
                            + "FROM configuracion "
                            + "WHERE Id = " + iId + ";");
            rs.next();

            _iId = iId;
            _sCampo = rs.getString("Campo");
            _sValor = rs.getString("Valor");
        }
        catch (SQLException e) { throw e; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }
	
    /**
     * Devuelve un String con la información más importante.
     * @return Configuración convertida en String.
     */
    @Override
    public String toString() 
    {
	return getId() + ":" + getCampo() + ":" + getValor(); 	 
    }
	

   /**
    * Actualiza el registro en la base de datos con los valores de las 
    * variables privadas.
    * 
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public void Update() throws SQLException  
   {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("UPDATE configuracion "
                        + "SET Campo = " + Data.String2Sql(_sCampo, true, false)
                        + ", Valor = " + Data.String2Sql(_sValor, true, false)
                        + " WHERE Id = " + _iId);
        }
        catch (SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
   }

    /**
     * Realiza una consulta SELECT a la base de datos con los parámetros de 
     * búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
     * 
     * @param sCampo Nombre del campo de configuración.
     * @param sValor Valor del campo.
     * @return devuelve una lista de los campos que coincidan con 
     * los parámetros de búsqueda
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
   public static ArrayList<Configuracion> Select(
                   String sCampo, String sValor) throws SQLException 
   {
        ArrayList<Configuracion> aConfiguracion = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery(
                    "SELECT Id FROM configuracion" + Where(sCampo, sValor));

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
    * Comprueba los parámetros recibidos como no nulos y añade la 
    * búsqueda a la consulta where
    * 
    * @param sCampo Nombre del campo de configuración.
    * @param sValor Valor del campo.
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sCampo, String sValor) 
   {
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

