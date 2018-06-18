package Data.Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data.Data;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Representa un registro de la tabla Imagen.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Imagen 
{
    private int _iId;
    private String _sNombre;
    private String _sRuta;
    private boolean _bIsDeleted;

    //GET
    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public String getRuta() {return _sRuta;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    //SET
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setRuta(String sRuta) {_sRuta = sRuta;}

    /**
     * Constructor a partir de un Id obtiene la imagen de la base de datos
     * @param iId Id de la imagen
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public Imagen(int iId) throws SQLException 
    {
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

    /**
     * Devuelve un String con la información más importante.
     * @return Imagen convertida en String.
     */
    @Override
    public String toString() 
    {
        return getId() + ":" + getNombre() + ":" + getRuta(); 
    }
	
    /**
     * Inserta una imagen en la base de datos
     * 
     * @param file El archivo de la imagen.
     * @param sRuta La ruta donde se quiere guardar.
     * @return devuelve la imagen insertada
     * @throws java.sql.SQLException Hay un error en la conexión.
     * @throws java.io.IOException Error al copiar el archivo.
     */
    public static Imagen Create(File file, String sRuta) 
            throws SQLException, IOException  
    {
        Connection con = null;
        try {
            con = Data.Connection();

            /*
            * Obtener un nuevo nombre para el archivo si ya existe uno con el 
            * mismo nombre.
            */
            String sNombre = file.getName();
            sNombre = sNombre.replace(" ", "_");
            Integer i = 0;
            while(Imagen.Select(sNombre, null).size() > 0){
                String[] asNombre = sNombre.split("\\.");
                String sFormato = asNombre[asNombre.length-1];
                if(i == 0) sNombre = sNombre.replace("."+sFormato, String.valueOf(i+1))+"."+sFormato;
                else sNombre = sNombre.replace(i.toString()+ "."+sFormato, String.valueOf(i+1))+"."+sFormato;
                i++;
            }

            //Copiar el archivo a la nueva ruta
            sRuta += "\\"+sNombre;
            Files.copy(Paths.get(file.getAbsolutePath()),
                       Paths.get(sRuta), 
                       StandardCopyOption.REPLACE_EXISTING);

            //Insertarlo en la base de datos
            con.createStatement().executeUpdate("INSERT INTO Imagen (Nombre, Ruta)"
                            + " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                            + Data.String2Sql(sRuta, true, false) + ");");

            return new Imagen(Data.LastId(con));
        }
        catch (SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina una imagen de la base de datos y marcamos la variable 
    * _bIsDeleted a true.
    * 
    * @throws Exception Lanza una excepción si ya está eliminada o si hay 
    * un error en la conexión.
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color_imagen WHERE Id_Imagen = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM Imagen WHERE Id = " + _iId);
            
            //Eliminar también el archivo
            Files.delete(Paths.get(_sRuta));
            
            _bIsDeleted = true;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores de las 
    * variables privadas.
    * 
    * @throws Exception Lanza una excepción si está eliminado o si hay 
    * error en la conexión
    */
   public void Update() throws Exception 
   {
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
    * Realiza una consulta SELECT a la base de datos con los parámetros de 
    * búsqueda indicados. Si alguno es nulo no se incluye en el SELECT.
    * 
    * @param sNombre Nombre de la imagen.
    * @param sRuta Ruta de la imagen.
    * @return devuelve una lista de las imagenes que coincidan con los 
    * parámetros de búsqueda
    * @throws java.sql.SQLException Hay un error en la conexión.
    */
   public static ArrayList<Imagen> Select(String sNombre, String sRuta) 
           throws SQLException 
   {
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
    * Comprueba los parámetros recibidos como no nulos y añade la 
    * búsqueda a la consulta where
    * 
    * @param sNombre Nombre de la imagen.
    * @param sRuta Ruta de la imagen.
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, String sRuta) 
   {
        String sWhere = "";

        if(sNombre != null) 
            sWhere = " WHERE Nombre LIKE "
                    + Data.String2Sql(sNombre, true, true);

        if(sRuta != null) {
            if(sWhere.equals(""))
                    sWhere = " WHERE ";
            else 
                    sWhere += " AND ";

            sWhere += "Ruta LIKE " + Data.String2Sql(sRuta, true, true);
        }

        return sWhere;
   }
}
