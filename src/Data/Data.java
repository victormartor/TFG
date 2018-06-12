package Data;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Config;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 * 
 * Clase Data encargada de proporcionar métodos y variables generales
 * para la ejecución del programa.
 */
public class Data {
    
    /**
     * @return Devuelve la URL del archivo db.properties
     */
    public static URL getPropertiesUrl()
    { 
        return ClassLoader.getSystemResource("db.properties");
    }
    
    /**
     * Crea una conexión con la base de datos y devuelve el canal de conexión
     * @return Devuelve la conexión abierta
     * @throws java.sql.SQLException
     */
    public static Connection Connection() throws SQLException 
    {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/easyshop?useSSL=false", 
                "easyshop_admin", "easyshopUCA18");
    }
    
    /**
     * Carga los drivers para poder usar la base de datos mySQL
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     * @throws java.io.IOException
     */
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException
    {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    /**
     * Transforma un String para que pueda ser reconocido correctamente por SQL
     * @param s - String a transformar
     * @param bAddQuotes - Añadir comillas simples al principio y al final
     * @param bAddWildcards - Añadir símbolo del porcentaje al principio
     * y al final
     * @return Devuelve el String transformado según los parámetros de entrada
     */
    public static String String2Sql(String s, 
            boolean bAddQuotes, boolean bAddWildcards)
    {
    	String result = "";
    	for(int i=0; i<s.length();i++)
    	{
    		result += s.charAt(i);
    		if(s.charAt(i) == '\'') result += '\''; 
                if(s.charAt(i) == '\\') result += '\\'; 
    	}
    	
    	if(bAddWildcards)
    	{
    		result = "%"+result+"%";
    	}
    	
    	if(bAddQuotes)
    	{
    		result = "\'"+result+"\'";
    	}
    	
    	return result;
    }
    
    /**
     * Transforma un Boolean para que pueda ser reconocido correctamente por SQL
     * @param b - Boolean a transformar
     * @return Devuelve 0 si es false y 1 si es true
     */
    public static int Boolean2Sql(boolean b)
    {
    	if(b)
    		return 1;
    	else
    		return 0;
    }
    
    /**
     * Obtiene el campo Id de la última fina insertada en la base de datos
     * @param con - Conexión actualmente abierta
     * @return Devuelve el último identificador insertado en una conexión dada
     * @throws java.sql.SQLException
     */
    public static int LastId(Connection con) throws SQLException 
    {
        ResultSet rs = con.createStatement()
                .executeQuery("SELECT LAST_INSERT_ID()");	    		
        rs.next(); 	    		
        return rs.getInt(1);
    }
    
    /**
     * Transforma un String a un Double para que pueda ser reconocido 
     * correctamente por SQL
     * @param s - El String a transformar
     * @return Devuelve el String transformado en Double
     */
    public static double String2Double(String s)
    {
        return Double.parseDouble(s.replace(",", "."));
    }
    
    /**
     * Obtiene la ruta donde guardar las imágenes según el sistema operativo 
     * @return Devuelve la ruta en String
     */
    public static String getRutaImagenes() 
    {
        String OS = System.getProperty("os.name");
        if(OS.contains("Win")) return "C:\\AppServ\\www\\EasyShop\\Imagenes";
        else return "/var/www/EasyShop/Imagenes";
    }
}

