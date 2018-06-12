package Data.Clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 * 
 * Clase Articulo representa en un objeto una fila de la tabla Articulo
 */
public class Articulo 
{
    private int _iId;
    private String _sNombre;
    private double _dPVP;
    private int _iId_Categoria;
    private boolean _bTalla_Es_Numero;
    private ArrayList<Integer> _aiTallas;
    private ArrayList<Integer> _aiColores;
    private ArrayList<Integer> _aiCombinaciones;
    
    private boolean _bIsDeleted;

    //GET
    public int getId() {return _iId;}	
    public String getNombre() {return _sNombre;}
    public double getPVP() {return _dPVP;}
    public int getId_Categoria() {return _iId_Categoria;}
    public boolean getTalla_Es_Numero() {return _bTalla_Es_Numero;}
    public ArrayList<Integer> getTallas() {return _aiTallas;}
    public ArrayList<Integer> getColores() {return _aiColores;}
    public ArrayList<Integer> getCombinaciones() {return _aiCombinaciones;}
    public boolean getIsDeleted() {return _bIsDeleted;}
    
    //SET
    public void setNombre(String sNombre) {_sNombre = sNombre;}
    public void setPVP(double dPVP) {_dPVP = dPVP;}
    public void setId_Categoria(int iId_Categoria) {_iId_Categoria = iId_Categoria;}
    public void setTalla_Es_Numero(boolean bTalla_Es_Numero) {_bTalla_Es_Numero = bTalla_Es_Numero;}
    public void setTallas(ArrayList<Integer> aiTallas) {_aiTallas = aiTallas;}
    public void setColores(ArrayList<Integer> aiColores) {_aiColores = aiColores;}
    public void setCombinaciones(ArrayList<Integer> aiCombinaciones) {_aiCombinaciones = aiCombinaciones;}

    /**
     * Constructor a partir de un Id obtiene el artículo de la base de datos
     * @param iId - Id del artículo
     * @throws java.sql.SQLException
     */
    public Articulo(int iId) throws SQLException
    {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = Data.Connection();
            rs = con.createStatement().executeQuery(
                "SELECT Id, Nombre, PVP, Id_Categoria, Talla_Es_Numero "
                + "FROM Articulo "
                + "WHERE Id = " + iId + ";");
            rs.next();

            //CARGAR DATOS DE LA TABLA ARTICULO
            _iId = iId;
            _sNombre = rs.getString("Nombre");
            _dPVP = rs.getDouble("PVP");
            _iId_Categoria = rs.getInt("Id_Categoria");
            _bTalla_Es_Numero = rs.getBoolean("Talla_Es_Numero");

            //CARGAR TALLAS ASOCIADAS
            rs = con.createStatement().executeQuery("SELECT Id_Talla "
                            + "FROM articulo_talla "
                            + "WHERE Id_Articulo = "+ iId +";");
            _aiTallas = new ArrayList<>();
            while(rs.next())
                _aiTallas.add(rs.getInt("Id_Talla"));

            //CARGAR COLORES ASOCIADOS
            rs = con.createStatement().executeQuery("SELECT Id_Color "
                            + "FROM articulo_color "
                            + "WHERE Id_Articulo = "+ iId +";");
            _aiColores = new ArrayList<>();
            while(rs.next())
                _aiColores.add(rs.getInt("Id_Color"));

            //CARGAR DATOS DE LAS COMBINACIONES
            rs = con.createStatement().executeQuery(
                    "SELECT Id_Articulo1, Id_Articulo2 "
                    + "FROM art_combina_con "
                    + "WHERE Id_Articulo1 = "+ iId +" "
                    + "or Id_Articulo2 = "+ iId +";");
            _aiCombinaciones = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("Id_Articulo1");
                if(id != iId)
                    _aiCombinaciones.add(id);
                else
                    _aiCombinaciones.add(rs.getInt("Id_Articulo2"));
            }
        }catch (SQLException e) { throw e; }
        finally 
        {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }

    /**
     * Devuelve un String con los datos más importantes del artículo
     * @return Artículo convertido en String
     */
    @Override
    public String toString()
    {
        String sMensaje;
        try {
            sMensaje = getId() + ":" + getNombre() + ":" 
                    + Get_Imagenes_Color(_aiColores.get(0)).get(0).getId()
                    +":"+getPVP()+":"+getTalla_Es_Numero();
        } catch (Exception ex) {
            return "Error al obtener la imagen del artículo "+_iId+"\n"
                    +ex.toString();
        }
        return sMensaje; 
    }
	
    /**
     * Inserta un articulo en la base de datos
     * 
     * @param sNombre - Nombre del artículo
     * @param dPVP - PVP del artículo
     * @param iId_Categoria - Id de la categoría a la que pertenece
     * @param bTalla_Es_Numero - Está clasificado en tallas número
     * @param aiTallas - Array de Id de tallas
     * @param aiColores - Array de Id de colores
     * @param aiCombinaciones - Array de Id de artículos que combinan
     * @return devuelve el artículo insertado
     * @throws java.sql.SQLException 
     */
    public static Articulo Create(String sNombre, double dPVP, 
            int iId_Categoria, Boolean bTalla_Es_Numero,
            ArrayList<Integer> aiTallas, ArrayList<Integer> aiColores, 
            ArrayList<Integer> aiCombinaciones) throws SQLException  
    {
        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
            "INSERT INTO Articulo (Nombre, PVP, Id_Categoria, Talla_Es_Numero)"
            + " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
            + dPVP + ", "
            + iId_Categoria + ", "
            + Data.Boolean2Sql(bTalla_Es_Numero) +");");

            int iId = Data.LastId(con); 

            //ASIGNAR TALLAS
            if(aiTallas != null)
                for(Integer iId_Talla : aiTallas)
                    con.createStatement().executeUpdate(
                    "INSERT INTO articulo_talla (Id_Articulo, Id_Talla)"
                    + " VALUES (" + iId + ", " + iId_Talla + ");");

            //ASIGNAR COLORES
            if(aiColores != null)
                for(Integer iId_Color : aiColores)
                    con.createStatement().executeUpdate(
                    "INSERT INTO articulo_color (Id_Articulo, Id_Color)"
                    + " VALUES (" + iId + ", " + iId_Color + ");");

            //ASIGNAR COMBINACIONES
            if(aiCombinaciones != null)
                for(Integer iId_ArticuloCombinado : aiCombinaciones)
                    con.createStatement().executeUpdate(
                    "INSERT INTO art_combina_con (Id_Articulo1, Id_Articulo2)"
                    + " VALUES (" + iId + ", " + iId_ArticuloCombinado + ");");

            return new Articulo(iId);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
    }
    
    /**
    * Elimina un artículo de la base de datos y marcamos la variable 
    * _bIsDeleted a true.
    * 
    * @throws Exception
    */
   public void Delete() throws Exception
   {
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT Id_Imagen FROM Articulo_Color_Imagen "
                + "WHERE Id_Articulo = "+_iId);

            con.createStatement().executeUpdate(
                "DELETE FROM articulo_talla WHERE Id_Articulo = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color WHERE Id_Articulo = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM art_combina_con WHERE Id_Articulo1 = " + _iId 
                +" or Id_Articulo2 = " + _iId);
            con.createStatement().executeUpdate(
               "DELETE FROM articulo_color_imagen WHERE Id_Articulo = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM stock WHERE Id_Articulo = " + _iId);
            con.createStatement().executeUpdate(
                "DELETE FROM Articulo WHERE Id = " + _iId);

            while(rs.next())
                new Imagen(rs.getInt("Id_Imagen")).Delete();

            _bIsDeleted = true;
        }
        catch (SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
   }

   /**
    * Actualiza el registro en la base de datos con los valores de las 
    * variables privadas.
    * 
    * @throws Exception
    */
   public void Update() throws Exception 
   {
        if(_bIsDeleted)
                throw new Exception();

        Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate("UPDATE Articulo "
                + "SET Nombre = " + Data.String2Sql(_sNombre, true, false)
                + ", PVP = " + _dPVP
                + ", Id_Categoria = " + _iId_Categoria
                + ", Talla_Es_Numero = " + Data.Boolean2Sql(_bTalla_Es_Numero)
                + " WHERE Id = " + _iId);

            //ACTUALIZAR TALLAS
            if(_aiTallas != null){
                 con.createStatement().executeUpdate(
                    "DELETE FROM articulo_talla WHERE Id_Articulo = " + _iId);
                 for(Integer iId_Talla : _aiTallas)
                     con.createStatement().executeUpdate(
                             "INSERT INTO articulo_talla "
                             + "(Id_Articulo, Id_Talla)"
                             + " VALUES (" + _iId + ", " + iId_Talla + ");");
            }

            //ACTUALIZAR COMBINACIONES
            if(_aiCombinaciones != null){
                 con.createStatement().executeUpdate(
                    "DELETE FROM art_combina_con WHERE Id_Articulo1 = " + _iId+
                    " or Id_Articulo2 = " + _iId+";");
                 for(Integer iId_ArticuloCombinado : _aiCombinaciones)
                     con.createStatement().executeUpdate(
                    "INSERT INTO art_combina_con "
                    + "(Id_Articulo1, Id_Articulo2)"
                    + " VALUES (" + _iId + ", " + iId_ArticuloCombinado + ");");
            }
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
    * @param sNombre - Nombre del artículo
    * @param dPVP - PVP del artículo
    * @param iId_Categoria - Id de la cetegoría a la que pertenece
    * @param bTalla_Es_Numero - Está clasificado en tallas número
    * @return devuelve una lista de los articulos que coincidan con los 
    * parámetros de búsqueda
    * @throws Exception
    */
   public static ArrayList<Articulo> Select(
           String sNombre, Double dPVP, Integer iId_Categoria, 
           Boolean bTalla_Es_Numero) throws Exception
   {
        ArrayList<Articulo> aArticulos = new ArrayList<>();

        Connection con = null;
        ResultSet rs = null;
        try {
                con = Data.Connection();
                rs = con.createStatement().executeQuery(
                    "SELECT Id FROM Articulo"
                    + Where(sNombre, dPVP, iId_Categoria, bTalla_Es_Numero));

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
    * Comprueba los parámetros recibidos como no nulos y añade la búsqueda 
    * a la consulta where
    * 
    * @param sNombre - Nombre del artículo
    * @param dPVP - PVP del artículo
    * @param iId_Categoria - Id de la cetegoría a la que pertenece
    * @param bTalla_Es_Numero - Está clasificado en tallas número
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, Double dPVP, 
           Integer iId_Categoria, Boolean bTalla_Es_Numero) 
   {
        String sWhere = "";

        if(sNombre != null) 
                sWhere = " WHERE Nombre LIKE "
                        + Data.String2Sql(sNombre, true, true);

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

        if(bTalla_Es_Numero != null) {
                if(sWhere.equals(""))
                        sWhere = " WHERE ";
                else 
                        sWhere += " AND ";

                sWhere += "Talla_Es_Numero = "
                        + Data.Boolean2Sql(bTalla_Es_Numero);
        }

        return sWhere;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   //RELACION CON COLORES
   /////////////////////////////////////////////////////////////////////////////
   
   /**
    * Asocia un Color ya creado con el artículo
    * 
     * @param color - Color a asociar
     * @throws java.sql.SQLException
    */
   public void Add_Color(Color color) throws SQLException {
       Connection con = null;
        try {
                con = Data.Connection();
                con.createStatement().executeUpdate(
                        "INSERT INTO articulo_color (Id_Articulo, Id_Color)"
                        + " VALUES (" + _iId + ", " + color.getId() +  ");");
        }catch(SQLException e) { throw e; }
        finally {
            if (con != null) con.close();
        }
        
        _aiColores.add(color.getId());
   }
   
    /**
     * Obtiene una lista con las imágenes asociadas a un color ya asociado
     * con el artículo.
     * 
     * @param iId_Color - Id del color
     * @return Array de objetos Imagen 
     * @throws java.lang.Exception
     */
   public ArrayList<Imagen> Get_Imagenes_Color(Integer iId_Color) 
           throws Exception 
   {
       ArrayList<Imagen> aImagenes = new ArrayList<>();
       Connection con = null;
       ResultSet rs = null;
        try {
                con = Data.Connection();
      
                rs = con.createStatement().executeQuery(
                    "SELECT Id_Imagen FROM articulo_color_imagen "
                    + "WHERE Id_Articulo = "+ _iId + " "
                    + "and Id_Color = " + iId_Color);
                
                while(rs.next()) 
                        aImagenes.add(new Imagen(rs.getInt("Id_Imagen")));

                return aImagenes;
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
   }
   
    /**
     * Asocia una imagen ya creada con un color ya asociado al artículo.
     * 
     * @param iId_Color - Id del color
     * @param iId_Imagen - Id de la imagen
     * @throws java.sql.SQLException
     */
   public void Add_Imagen_Color(Integer iId_Color, Integer iId_Imagen) 
           throws SQLException 
   {
       Connection con = null;
        try {
            con = Data.Connection();
            con.createStatement().executeUpdate(
                "INSERT INTO articulo_color_imagen "
                + "(Id_Articulo, Id_Color, Id_Imagen)"
                + " VALUES (" + _iId + ", " 
                + iId_Color + ", "
                + iId_Imagen + ");");
        }catch(SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }
   
   /**
     * Desasocia un color que ya estaba asociado al artículo. Eliminando
     * también sus imágenes del servidor.
     * 
     * @param iId_Color - Id del color
     * @throws Exception
     */
   public void Delete_Color(Integer iId_Color) throws Exception
   {
       Connection con = null;
        try {
            con = Data.Connection();

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT Id_Imagen FROM Articulo_Color_Imagen "
                + "WHERE Id_Articulo = "+_iId+" AND Id_Color = "+iId_Color);

            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color "
               + "WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);

            con.createStatement().executeUpdate(
                "DELETE FROM articulo_color_imagen "
               + "WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);

            con.createStatement().executeUpdate(
                "DELETE FROM stock "
               + "WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);

            while(rs.next())
                new Imagen(rs.getInt("Id_Imagen")).Delete();

            _aiColores.remove(iId_Color);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   } 
}