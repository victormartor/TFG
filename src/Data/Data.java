/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author victor
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import util.Config;

public class Data {
    public static String getPropertiesUrl() { return "./src/db.properties"; }
    public static Connection Connection() throws Exception {
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards)
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
    
    public static int Boolean2Sql(boolean b)
    {
    	if(b)
    		return 1;
    	else
    		return 0;
    }
    
    /**
     * @param con Conexión actualmente abierta
     * @return Devuelve el último identificador insertado en una conexión dada
     * @throws Exception
     */
    public static int LastId(Connection con) throws Exception{
	    	try {
	    		Properties properties = Config.Properties(getPropertiesUrl());
	    		ResultSet rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
	    		
	    		rs.next(); 
	    		
	    		return rs.getInt(1);
	       }
	       catch (Exception ee) { throw ee; }
    }
    
    
    public static double String2Double(String s){
        s = s.replace(",", ".");
        return Double.parseDouble(s);
    }
}

