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
    
    public ModArticulos(int iId_Categoria) throws Exception
    {
        lArticulo = Articulo.Select(null, null, iId_Categoria);
    }

    @Override
    public int getSize() {
        return lArticulo.size();
    }

    @Override
    public Object getElementAt(int index) {
        return lArticulo.get(index);
    }
    
    public void addArticulo(Articulo articulo) throws Exception
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
}

