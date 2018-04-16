/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Data;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class prueba {
    
    public static void main(String args[]) throws Exception{
        //Articulo a = Articulo.Create("camiseta", 20, 4, null);
        //a.Delete();
        //System.out.println(a);
        Articulo.Create("", 0, 5, false, null, null, null);
    }

}
