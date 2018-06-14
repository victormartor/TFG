/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Imagen;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class ModImagenes extends AbstractListModel
{
    private ArrayList<Imagen> lImagen;
    
    public ModImagenes() throws Exception
    {
        lImagen = Imagen.Select(null, null);
    }

    @Override
    public int getSize() {
        return lImagen.size();
    }

    @Override
    public Object getElementAt(int index) {
        
        return lImagen.get(index);
    }
    
    public void addImagen(Imagen imagen)
    {
        lImagen.add(imagen);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeImagen(int index) throws Exception
    {
        lImagen.get(index).Delete();
        lImagen.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public Imagen getImagen(int index)
    {
        return lImagen.get(index);
    }
}

