/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Categoria;
import Data.Clases.Marca;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModMarcas extends AbstractListModel
{
    private ArrayList<Marca> lMarca;
    
    public ModMarcas() throws Exception
    {
        lMarca = Marca.Select(null, null);
    }

    @Override
    public int getSize() {
        return lMarca.size();
    }

    @Override
    public Object getElementAt(int index) {
        return lMarca.get(index);
    }
    
    public void addMarca(Marca marca) throws Exception
    {
        lMarca.add(marca);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeMarca(int index) throws Exception
    {
        Marca marca = lMarca.get(index);
        
        ArrayList<Categoria> aCategorias = Categoria.Select(null, null, marca.getId());
        for(Categoria c : aCategorias){
            c.Delete();
        }
        marca.Delete();
        
        lMarca.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public Marca getMarca(int index)
    {
        return lMarca.get(index);
    }
}
