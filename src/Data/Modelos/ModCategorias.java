package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de tipo Categoria.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Categoria
 */
public class ModCategorias extends AbstractListModel
{
    private final ArrayList<Categoria> _aCategoria;
    
    /**
     * Constructor a partir de el Id de la Marca.
     * @param iId_Marca Id de la marca. 
     * @throws java.sql.SQLException Error al obtener las categorías de la 
     * marca.
     */
    public ModCategorias(int iId_Marca) throws SQLException 
    {
        _aCategoria = Categoria.Select(null, null, iId_Marca);
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aCategoria.size();
    }

    /**
     * Devuelve el elemento en una posición dada de tipo Categoria.
     * @param iIndex Posición del elemento.
     * @return El elemento en esa posición.
     */
    @Override
    public Object getElementAt(int iIndex) {
        return _aCategoria.get(iIndex);
    }
    
    /**
     * Añade una categoría a la lista.
     * @param categoria Categoría que se quiere añadir.
     */
    public void addCategoria(Categoria categoria)
    {
        _aCategoria.add(categoria);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina una Categoria en una posición dada. También elimina la Categoría
     * de la base de datos, teniendo que eliminar también los artículos que
     * se ecuentran en ella.
     * @param iIndex Posición de la categoría dentro de la lista.
     * @throws Exception Error al eliminar la categoría de la base de datos o
     * alguno de sus artículos.
     */
    public void removeCategoria(int iIndex) throws Exception
    {
        Categoria categoria = _aCategoria.get(iIndex);
        
        ArrayList<Articulo> aArticulos = Articulo.Select(null, null, categoria.getId(), null);
        for(Articulo a : aArticulos){
            a.Delete();
        }
        categoria.Delete();
               
        _aCategoria.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
}
