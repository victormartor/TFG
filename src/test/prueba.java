/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Data.Articulo;
import Data.Categoria;
import Data.Data;
import Data.Imagen;
import Data.Marca;
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
        Articulo a = new Articulo(2);
        a.Delete();
        System.out.println(a);
    
    }

}
