/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Articulo;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModArticulos extends AbstractListModel
{
    private ArrayList<Articulo> lArticulo;
    
    public ModArticulos(Integer iId_Categoria, Integer iId_Articulo) throws Exception
    {
        if(iId_Articulo == null) lArticulo = Articulo.Select(null, null, iId_Categoria, null);
        else{
            Articulo articulo = new Articulo(iId_Articulo);
            lArticulo = new ArrayList<>();
            for(int id_combinacion : articulo.getCombinaciones())
                lArticulo.add(new Articulo(id_combinacion));
        }
    }

    @Override
    public int getSize() {
        return lArticulo.size();
    }

    @Override
    public Object getElementAt(int index) {
        return lArticulo.get(index);
    }
    
    public void addArticulo(Articulo articulo)
    {
        lArticulo.add(articulo);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeArticulo(int index) throws Exception
    {
        lArticulo.get(index).Delete();
        lArticulo.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public Articulo getArticulo(int index)
    {
        return lArticulo.get(index);
    }
    
    public void removeCombinacion(int index){
        lArticulo.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public int getIndexOf(Articulo articulo){
        int index = 0;
        while(lArticulo.get(index).getId() != articulo.getId()) index++;
        return index;
    }
}

