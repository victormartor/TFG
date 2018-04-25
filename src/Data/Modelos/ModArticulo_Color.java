/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Marca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModArticulo_Color extends AbstractListModel
{
    private ArrayList<Color> lColores;
    private Articulo articulo;
    
    public ModArticulo_Color(int iId_Articulo) throws Exception
    {
        articulo = new Articulo(iId_Articulo);
        ArrayList<Integer> lId_Colores = articulo.getColores();
        lColores = new ArrayList<>();
        
        for(int i : lId_Colores)
            lColores.add(new Color(i));
    }

    @Override
    public int getSize() {
        return lColores.size();
    }

    @Override
    public Object getElementAt(int index) {
        return lColores.get(index);
    }
    
    
    public void addColor(Color color) throws Exception
    {
        articulo.Add_Color(color);
        lColores.add(color);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    
    public Color getColor(int index){
        return lColores.get(index);
    }
    
    public void removeColor(int index) throws Exception
    {  
        Color color = lColores.get(index);
              
        articulo.Delete_Color(color.getId());
        
        lColores.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }

    public ArrayList<Integer> getColores()
    {    
        return articulo.getColores();
    }
    
}
