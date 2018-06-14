package Data.Modelos;

import Data.Clases.Articulo;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de tipo Articulo.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Articulo
 */
public class ModArticulos extends AbstractListModel
{
    private final ArrayList<Articulo> _aArticulo;
    
    /**
     * Constructor a partir de un Id de Categoria o a partir de un Id de un
     * artículo. Si se proporciona el Id de la Categoria la lista se compondrá
     * de todos los artículos de esa Categoría. Si se proporciona el Id del
     * Articulo la lista se compondrá de Artículos pertenecientes a sus 
     * combinaciones.
     * @param iId_Categoria Id de Categoria.
     * @param iId_Articulo Id de Articulo. 
     * @throws java.sql.SQLException Error al obtener los Artículos de la 
     * Categoría o las combinaciones del Artículo.
     */
    public ModArticulos(Integer iId_Categoria, Integer iId_Articulo) 
            throws SQLException 
    {
        if(iId_Articulo == null) 
            _aArticulo = Articulo.Select(null, null, iId_Categoria, null);
        else{
            Articulo articulo = new Articulo(iId_Articulo);
            _aArticulo = new ArrayList<>();
            for(int id_combinacion : articulo.getCombinaciones())
                _aArticulo.add(new Articulo(id_combinacion));
        }
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aArticulo.size();
    }

    /**
     * Devuelve el elemento en una posición dada de tipo Articulo.
     * @param iIndex Posición del elemento.
     * @return Elemento de esa posición en la lista.
     */
    @Override
    public Object getElementAt(int iIndex) 
    {
        return _aArticulo.get(iIndex);
    }
    
    /**
     * Añade un elemento de tipo Articulo a la lista.
     * @param articulo Articulo a añadir.
     */
    public void addArticulo(Articulo articulo)
    {
        _aArticulo.add(articulo);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina un Articulo de la lista (usar solo cuando sea lista de Articulos
     * de una Categoria). También elimina el Articulo de la base de datos.
     * @param iIndex Posición del elemento en la lista.
     * @throws Exception Error al eliminar el Articulo de la base de datos.
     */
    public void removeArticulo(int iIndex) throws Exception
    {
        _aArticulo.get(iIndex).Delete();
        _aArticulo.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }

    /**
     * Elimina una Combinación de la lista (usar solo cuando sea lista de
     * Combinaciones de un Articulo).
     * @param iIndex Posición del elemento en la lista.
     */
    public void removeCombinacion(int iIndex)
    {
        _aArticulo.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
    
    /**
     * Obtener el índice de un artículo dentro de la lista. Si el artículo no
     * pertenece a la lista devuelve el tamaño de esta.
     * @param articulo Artículo del que se quiere saber su posición dentro de
     * la lista.
     * @return La posición dentro de la lista.
     */
    public int getIndexOf(Articulo articulo)
    {
        int iIndex = 0;
        while(_aArticulo.get(iIndex).getId() != articulo.getId()) iIndex++;
        return iIndex;
    }
}

