/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import Data.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author victor
 */
public class prueba {
    public static void main(String args[]) throws Exception{
        Data.LoadDriver();
       Connection con = null;
        ResultSet rs = null;
            try {

                    con = Data.Connection();
                    rs = con.createStatement().executeQuery("SELECT * FROM marca;");
                    
                    int i = 0;
                    while (rs.next()) {
                            for(int j=1; j<=rs.getMetaData().getColumnCount();j++)
                            {
                                    System.out.print(rs.getString(j)+" ");
                            }
                            System.out.println();
                            i++;
                    }
        }
            catch (SQLException ee) { throw ee; }
            finally {
                    if (rs != null) rs.close();
                if (con != null) con.close();
            }
    
    }
}
