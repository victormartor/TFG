package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Pedido.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Pedido 
{	
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
    public void setFecha(Date Fecha) {_Fecha = Fecha;}
    public void setNumArticulos(int iNumArticulos) {_iNumArticulos = iNumArticulos;}
    public void setTotal(double dTotal) { _dTotal = dTotal;}
    public void setCodPostal(Integer iCodPostal) {_iCodPostal = iCodPostal;}
    public void setsDirEnvio(String sDirEnvio) { _sDirEnvio = sDirEnvio;}
    public void setArticulosStock(ArrayList<Integer> articulosStock) {_aiArticulosStock = articulosStock;}

    /**
     * Constructor a partir de un Id obtiene el pedido de la base de datos.
     * @param iId Id del pedido.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Pedido(int iId) throws SQLException  
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id, Fecha, "
                    + "NumArticulos, Total, CodPostal, DirEnvio "
                            + "FROM pedido "
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
	
    /**
     * Devuelve un String con la información más importante.
     * @return Pedido convertido en String.
     */
    @Override
    public String toString() 
    {
	return getId() + ":" + getFecha() + ":" + getNumArticulos()
                 + ":" + getTotal() + ":" + getCodPostal()
                 + ":" + getDirEnvio() + ":" + getArticulosStock(); 
    }
	
    /**
     * Inserta un pedido en la base de datos.
     * 
     * @param Fecha Fecha del pedido.
     * @param iNumArticulos Número de artículos del pedido.
     * @param dTotal Total del precio del pedido.
     * @param iCodPostal Código postal del cliente. Puede ser nulo.
     * @param sDirEnvio Lugar donde será entregado el pedido.
     * @param aiArticulosStock Array de artículos del pedido.
     * @return devuelve el pedido insertado.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public static Pedido Create(Date Fecha, int iNumArticulos, double dTotal,
            Integer iCodPostal, String sDirEnvio, 
            ArrayList<Integer> aiArticulosStock) 
            throws SQLException  
    {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("INSERT INTO pedido "
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
    * Elimina un pedido de la base de datos y marcamos la variable 
    * _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminado o si 
    * hay un error en la conexión.
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                    "DELETE FROM pedido_stock WHERE Id_Pedido = " + _iId);
            con.createStatement().executeUpdate(
                    "DELETE FROM pedido WHERE Id = " + _iId);
            _bIsDeleted = true;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores de 
    * las variables privadas.
    * 
    * @throws Exception Lanza una excepción si está eliminado o 
    * si hay error en la conexión
    */
   public void Update() throws Exception 
   {
        if(_bIsDeleted)
            throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("UPDATE pedido "
                + "SET Fecha = "+Data.String2Sql(_Fecha.toString(), true, false)
                + ", NumArticulos = " +_iNumArticulos
                + ", Total = " +_dTotal
                + ", CodPostal = " + _iCodPostal
                + ", DirEnvio = " + Data.String2Sql(_sDirEnvio, true, false)
                + " WHERE Id = " + _iId);

            //ACTUALIZAR ARTICULOS ASOCIADOS
            if(_aiArticulosStock != null){
                 con.createStatement().executeUpdate(
                         "DELETE FROM pedido_stock WHERE Id_Pedido = " + _iId);
                 for(Integer iId_Stock : _aiArticulosStock)
                     con.createStatement().executeUpdate(
                             "INSERT INTO pedido_stock "
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
    * Realiza una consulta SELECT a la base de datos con los parámetros 
    * de búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
    * 
    * @param Fecha Fecha del pedido.
    * @param iNumArticulos Número de artículos del pedido.
    * @param dTotal Total del precio del pedido.
    * @param iCodPostal Código postal del cliente. Puede ser nulo.
    * @param sDirEnvio Lugar donde será entregado el pedido.
    * @return devuelve una lista de los pedidos que coincidan con los 
    * parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Pedido> Select(Date Fecha, Integer iNumArticulos, Double dTotal,
            Integer iCodPostal, String sDirEnvio) throws SQLException 
   {
        ArrayList<Pedido> aPedidos = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery("SELECT Id FROM pedido"
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
    * Comprueba los parámetros recibidos como no nulos y añade la búsqueda 
    * a la consulta where
    * 
    * @param Fecha Fecha del pedido.
    * @param iNumArticulos Número de artículos del pedido.
    * @param dTotal Total del precio del pedido.
    * @param iCodPostal Código postal del cliente. Puede ser nulo.
    * @param sDirEnvio Lugar donde será entregado el pedido.
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(Date Fecha, Integer iNumArticulos, Double dTotal,
            Integer iCodPostal, String sDirEnvio) 
   {
        String sWhere = "";

        if(Fecha != null) 
                sWhere = " WHERE Fecha LIKE "
                        + Data.String2Sql(Fecha.toString(), true, true);

        if(iNumArticulos != null) 
        {
            if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "NumArticulos = "+ iNumArticulos;
        }

        if(dTotal != null) 
        {
           if(sWhere.equals(""))
                   sWhere = " WHERE ";
           else 
                   sWhere += " AND ";

           sWhere += "Total = "+ dTotal;
        }

        if(iCodPostal != null) 
        {
           if(sWhere.equals(""))
                   sWhere = " WHERE ";
           else 
                   sWhere += " AND ";

           sWhere += "CodPostal = "+ iCodPostal;
        }

        if(sDirEnvio != null) 
        {
           if(sWhere.equals(""))
                   sWhere = " WHERE ";
           else 
                   sWhere += " AND ";

           sWhere += "DirEnvio LIKE "+ Data.String2Sql(sDirEnvio, true, true);
        }

        return sWhere;
   }
}
