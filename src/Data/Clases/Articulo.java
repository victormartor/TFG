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
import java.util.Map;

/**
 *
 * @author victor
 */
public class Articulo {
	
    private int _iId;
    private String _sNombre;
    private double _dPVP;
    private int _iId_Categoria;
    private boolean _bTalla_Es_Numero;
    private ArrayList<Integer> _aiTallas;
    private ArrayList<Integer> _aiColores;
    private ArrayList<Integer> _aiCombinaciones;
    //private Map<Integer, Map<Integer, ArrayList<Integer>>> _mapArticulo_Color_Imagen;
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

    public Articulo(int iId) throws Exception {
        Connection con = null;
        ResultSet rs = null;
            try {
                    con = Data.Connection();
                    rs = con.createStatement().executeQuery("SELECT Id, Nombre, PVP, Id_Categoria, Talla_Es_Numero "
                                    + "FROM Articulo "
                                    + "WHERE Id = " + iId + ";");
                    rs.next();

                    //CARGAR DATOS DE LA TABLA ARTICULOS
                    _iId = iId;
                    _sNombre = rs.getString("Nombre");
                    _dPVP = rs.getDouble("PVP");
                    _iId_Categoria = rs.getInt("Id_Categoria");
                    _bTalla_Es_Numero = rs.getBoolean("Talla_Es_Numero");
                    
                    //CARGAR DATOS DE LA TABLA TALLAS
                    rs = con.createStatement().executeQuery("SELECT Id_Talla "
                                    + "FROM articulo_talla "
                                    + "WHERE Id_Articulo = "+ iId +";");
                    _aiTallas = new ArrayList<>();
                    while(rs.next())
                        _aiTallas.add(rs.getInt("Id_Talla"));
                    
                    //CARGAR DATOS DE LA TABLA COLORES
                    rs = con.createStatement().executeQuery("SELECT Id_Color "
                                    + "FROM articulo_color "
                                    + "WHERE Id_Articulo = "+ iId +";");
                    _aiColores = new ArrayList<>();
                    while(rs.next())
                        _aiColores.add(rs.getInt("Id_Color"));
                    
                    //CARGAR DATOS DE LAS COMBINACIONES
                    rs = con.createStatement().executeQuery("SELECT Id_Articulo1, Id_Articulo2 "
                                    + "FROM art_combina_con "
                                    + "WHERE Id_Articulo1 = "+ iId +" or Id_Articulo2 = "+ iId +";");
                    _aiCombinaciones = new ArrayList<>();
                    while(rs.next()){
                        int id = rs.getInt("Id_Articulo1");
                        if(id != iId)
                            _aiCombinaciones.add(id);
                        else
                            _aiCombinaciones.add(rs.getInt("Id_Articulo2"));
                    }
        }
            catch (SQLException ee) { throw ee; }
            finally {
                    if (rs != null) rs.close();
                if (con != null) con.close();
            }
    }

    public String toString() {
        String sMensaje = getId() + ":" + getNombre() + ":" + getPVP() + ":" + getId_Categoria() + ":" + getTalla_Es_Numero() +
                ":" + getTallas()+ ":" + getColores() + ":" + getCombinaciones(); 
        return sMensaje; 
    }
	
    /**
     * Inserta un articulo en la base de datos
     * 
     * @param sNombre
     * @param dPVP
     * @param iId_Categoria
     * @param bTalla_Es_Numero
     * @param aiTallas
     * @param aiColores
     * @param aiCombinaciones
     * @return devuelve la categoria insertada
     * @throws Exception
     */
    public static Articulo Create(String sNombre, double dPVP, int iId_Categoria, Boolean bTalla_Es_Numero,
            ArrayList<Integer> aiTallas, ArrayList<Integer> aiColores, ArrayList<Integer> aiCombinaciones) 
            throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO Articulo (Nombre, PVP, Id_Categoria, Talla_Es_Numero)"
					+ " VALUES (" + Data.String2Sql(sNombre, true, false) + ", " 
                                        + dPVP + ", "
                                        + iId_Categoria + ", "
                                        + Data.Boolean2Sql(bTalla_Es_Numero) +");");
                        
                        int iId = Data.LastId(con); 
                        
                        //ASIGNAR TALLAS
                        if(aiTallas != null)
                            for(Integer iId_Talla : aiTallas)
                                con.createStatement().executeUpdate("INSERT INTO articulo_talla "
                                        + "(Id_Articulo, Id_Talla)"
					+ " VALUES (" + iId + ", " + iId_Talla + ");");
                        
                        //ASIGNAR COLORES
                        if(aiColores != null)
                            for(Integer iId_Color : aiColores)
                                con.createStatement().executeUpdate("INSERT INTO articulo_color "
                                        + "(Id_Articulo, Id_Color)"
					+ " VALUES (" + iId + ", " + iId_Color + ");");
                        
                        //ASIGNAR COMBINACIONES
                        if(aiCombinaciones != null)
                            for(Integer iId_ArticuloCombinado : aiCombinaciones)
                                con.createStatement().executeUpdate("INSERT INTO art_combina_con "
                                        + "(Id_Articulo1, Id_Articulo2)"
					+ " VALUES (" + iId + ", " + iId_ArticuloCombinado + ");");
                        
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
                con.createStatement().executeUpdate("DELETE FROM articulo_color WHERE Id_Articulo = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM art_combina_con WHERE Id_Articulo1 = " + _iId +
                                                                                 " or Id_Articulo2 = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM articulo_color_imagen WHERE Id_Articulo = " + _iId);
                con.createStatement().executeUpdate("DELETE FROM stock WHERE Id_Articulo = " + _iId);
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
                                   + ", Talla_Es_Numero = " + Data.Boolean2Sql(_bTalla_Es_Numero)
                                   + " WHERE Id = " + _iId);
                   
                   //ACTUALIZAR TALLAS
                   if(_aiTallas != null){
                        con.createStatement().executeUpdate("DELETE FROM articulo_talla WHERE Id_Articulo = " + _iId);
                        for(Integer iId_Talla : _aiTallas)
                            con.createStatement().executeUpdate("INSERT INTO articulo_talla "
                                    + "(Id_Articulo, Id_Talla)"
                                    + " VALUES (" + _iId + ", " + iId_Talla + ");");
                   }
                   
                   //ACTUALIZAR COLORES
                   if(_aiColores != null){
                        con.createStatement().executeUpdate("DELETE FROM articulo_color WHERE Id_Articulo = " + _iId);
                        for(Integer iId_Color : _aiColores)
                            con.createStatement().executeUpdate("INSERT INTO articulo_color "
                                    + "(Id_Articulo, Id_Color)"
                                    + " VALUES (" + _iId + ", " + iId_Color + ");");
                   }
                   
                   //ACTUALIZAR COMBINACIONES
                   if(_aiCombinaciones != null){
                        con.createStatement().executeUpdate("DELETE FROM art_combina_con WHERE Id_Articulo1 = " + _iId+
                                                                                           " or Id_Articulo2 = " + _iId+";");
                        for(Integer iId_ArticuloCombinado : _aiCombinaciones)
                            con.createStatement().executeUpdate("INSERT INTO art_combina_con "
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
    * Realiza una consulta SELECT a la base de datos con los parámetros de búsqueda indicados.
    * 
    * @param sNombre Nombre a buscar, si es nulo no se busca por el Nombre
     * @param dPVP
     * @param iId_Categoria
     * @param bTalla_Es_Numero
    * @return devuelve una lista de los articulos que coincidan con los parámetros de búsqueda
    * @throws Exception Lanza una excepción si hay un error en la conexión
    */
   public static ArrayList<Articulo> Select(
                   String sNombre, Double dPVP, Integer iId_Categoria, Boolean bTalla_Es_Numero) throws Exception{
           ArrayList<Articulo> aArticulos = new ArrayList<>();

           Connection con = null;
           ResultSet rs = null;
           try {
                   con = Data.Connection();
                   rs = con.createStatement().executeQuery("SELECT Id FROM Articulo"
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
    * Comprueba los parámetros recibidos como no nulos y añade la búsqueda a la consulta where
    * 
    * @param sNombre Nombre a buscar, si es nulo no se busca por el Nombre
    * @param iId_Imagen Id_Imagen a buscar, si es nulo no se busca por el Id_Imagen
    * @return Devuelve la consulta WHERE como un String
    */
   private static String Where(String sNombre, Double dPVP, Integer iId_Categoria, Boolean bTalla_Es_Numero) {
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
           
           if(bTalla_Es_Numero != null) {
                   if(sWhere.equals(""))
                           sWhere = " WHERE ";
                   else 
                           sWhere += " AND ";

                   sWhere += "Talla_Es_Numero = "+ Data.Boolean2Sql(bTalla_Es_Numero);
           }

           return sWhere;
   }
   
   /////////////////////////////////////////////////////////////////////////////////////
   //RELACION CON COLORES
   ////////////////////////////////////////////////////////////////////////////////////
   
   public ArrayList<Imagen> Get_Imagenes_Color(Integer iId_Color) throws Exception{
       ArrayList<Imagen> aImagenes = new ArrayList<>();
       Connection con = null;
       ResultSet rs = null;
        try {
                con = Data.Connection();
      
                rs = con.createStatement().executeQuery(
                        "SELECT Id_Imagen FROM articulo_color_imagen WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);
                
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
   
   public void Add_Imagen_Color(Integer iId_Color, Integer iId_Imagen) throws Exception{
       Connection con = null;
            try {
                    con = Data.Connection();
                    con.createStatement().executeUpdate("INSERT INTO articulo_color_imagen (Id_Articulo, Id_Color, Id_Imagen)"
                                    + " VALUES (" + _iId + ", " 
                                    + iId_Color + ", "
                                    + iId_Imagen + ");");
            }catch(SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   }
   
   public void Remove_Imagen_Color(Integer iId_Color, Integer iId_Imagen) throws Exception{
       
       Connection con = null;
        try {
                con = Data.Connection();
      
                con.createStatement().executeUpdate(
                        "DELETE FROM articulo_color_imagen WHERE "
                                + "Id_Articulo = "+ _iId 
                                + " and Id_Color = " + iId_Color
                                + " and Id_Imagen = " + iId_Imagen);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   } 
   
   public void Delete_Color(Integer iId_Color) throws Exception{
       
       Connection con = null;
        try {
                con = Data.Connection();
      
                con.createStatement().executeUpdate(
                        "DELETE FROM articulo_color WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);
                
                con.createStatement().executeUpdate(
                        "DELETE FROM articulo_color_imagen WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);
                
                con.createStatement().executeUpdate(
                        "DELETE FROM stock WHERE Id_Articulo = "+ _iId + " and Id_Color = " + iId_Color);
                
                _aiColores.remove(iId_Color);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (con != null) con.close();
        }
   } 
}
