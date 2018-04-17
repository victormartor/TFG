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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class prueba {
    
    public static void main(String args[]) throws Exception{
        //Articulo a = Articulo.Create("camiseta", 20, 4, null);
        //a.Delete();
        //System.out.println(a);
        /*
        Map<Integer, Map<Integer, ArrayList<Integer>>> m = new HashMap<>();
        Map<Integer, ArrayList<Integer>> n = new HashMap<>();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        n.put(1, a);
        m.put(1, n);
        
        System.out.println(m.get(1).get(1));
        */
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(5);
        System.out.println(a);
        a.remove(new Integer(5));
        System.out.println(a);
    }

}
