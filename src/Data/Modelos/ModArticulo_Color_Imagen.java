/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Color;
import Data.Clases.Imagen;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModArticulo_Color_Imagen extends AbstractListModel
{
    private ArrayList<Imagen> lImagen;
    private Articulo _articulo = null;
    private Color _color = null;
    
    public ModArticulo_Color_Imagen(Articulo articulo, Color color) throws Exception
    {
        _articulo = articulo;
        _color = color;
        
        if(_color != null)
            lImagen = articulo.Get_Imagenes_Color(_color.getId());
        else
            lImagen = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return lImagen.size();
    }

    @Override
    public Object getElementAt(int index) {
        
        return lImagen.get(index);
    }
    
    public void addImagen(Imagen imagen) throws Exception
    {
        _articulo.Add_Imagen_Color(_color.getId(), imagen.getId());
        lImagen.add(imagen);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeImagen(int index) throws Exception
    {
        _articulo.Remove_Imagen_Color(_color.getId(), lImagen.get(index).getId());
        lImagen.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public Imagen getImagen(int index)
    {
        return lImagen.get(index);
    }
}


