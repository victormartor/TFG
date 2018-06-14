package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de colores asociados a un artículo.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Articulo
 * @see Color
 */
public class ModArticulo_Color extends AbstractListModel
{
    private final ArrayList<Color> _aColor;
    private final Articulo _articulo;
    
    /**
     * Constructor a partir del Id del artículo. Obtiene sus colores asociados
     * y crea un array de tipo Color.
     * @param iId_Articulo Id del artículo. 
     * @throws java.sql.SQLException Error al buscar el artículo o los colores
     * en la base de datos.
     */
    public ModArticulo_Color(int iId_Articulo) throws SQLException 
    {
        _articulo = new Articulo(iId_Articulo);
        ArrayList<Integer> lId_Colores = _articulo.getColores();
        _aColor = new ArrayList<>();
        
        for(int i : lId_Colores)
            _aColor.add(new Color(i));
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() {
        return _aColor.size();
    }

    /**
     * Devuelve el elemento de una posición dada.
     * @param iIndex La posición del elemento dentro del array.
     * @return El elemento de esa posición de tipo Color.
     */
    @Override
    public Object getElementAt(int iIndex) 
    {
        return _aColor.get(iIndex);
    }
    
    /**
     * Añade un color a la lista de colores y lo asocia al artículo en la 
     * base de datos.
     * @param color Color que se quiere añadir. 
     * @throws java.sql.SQLException Error al asociar el color con el artículo.
     */
    public void addColor(Color color) throws SQLException 
    {
        _articulo.Add_Color(color);
        _aColor.add(color);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina un color de la lista. También lo desasocia del artículo pero
     * no lo elimina de la base de datos.
     * @param iIndex Posición del Color en la lista.
     * @throws Exception Error al desasociar el color del artículo.
     */
    public void removeColor(int iIndex) throws Exception 
    {  
        Color color = _aColor.get(iIndex);    
        _articulo.Delete_Color(color.getId());
        _aColor.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }

    /**
     * Devuelve una lista con los Ids de los colores asociados al artículo.
     * @return Array de Ids de colores.
     */
    public ArrayList<Integer> getColores()
    {    
        return _articulo.getColores();
    }
}
