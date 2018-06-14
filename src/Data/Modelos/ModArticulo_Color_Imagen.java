package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Color;
import Data.Clases.Imagen;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de las imágenes de un color asociado a un
 * artículo.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Imagen 
 * @see Articulo
 * @see Color
 */
public class ModArticulo_Color_Imagen extends AbstractListModel
{
    private final ArrayList<Imagen> _aImagen;
    private Articulo _articulo = null;
    private Color _color = null;
    
    /**
     * Constructor a partir de un artículo y un color asociado a ese artículo.
     * @param articulo Articulo.
     * @param color Color asociado a ese articulo.
     * @throws java.sql.SQLException Error al obtener las imágenes de ese 
     * artículo con ese color.
     */
    public ModArticulo_Color_Imagen(Articulo articulo, Color color) 
            throws SQLException 
    {
        _articulo = articulo;
        _color = color;
        
        if(_color != null)
            _aImagen = articulo.Get_Imagenes_Color(_color.getId());
        else
            _aImagen = new ArrayList<>();
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aImagen.size();
    }

    /**
     * Devuelve el elemento de una posición dada.
     * @param iIndex La posición del elemento dentro de la lista,
     * @return El elemento de tipo Imagen.
     */
    @Override
    public Object getElementAt(int iIndex) 
    {
        return _aImagen.get(iIndex);
    }
    
    /**
     * Añade una imagen a la lista, también la asocia al color en la base de
     * datos que a su vez está asociado con el artículo.
     * @param imagen Imagen que se quiere asociar. 
     * @throws java.sql.SQLException Error al asociar la imagen.
     */
    public void addImagen(Imagen imagen) throws SQLException
    {
        _articulo.Add_Imagen_Color(_color.getId(), imagen.getId());
        _aImagen.add(imagen);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina la imagen de la lista en una posición dada, también la desasocia
     * del color al que estaba asociada en la base de datos.
     * @param iIndex Posición de la Imagen dentro de la lista.
     * @throws Exception Error al desasociar la imagen con el color.
     */
    public void removeImagen(int iIndex) throws Exception 
    {
        _aImagen.get(iIndex).Delete();
        _aImagen.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
}


