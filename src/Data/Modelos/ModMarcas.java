package Data.Modelos;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Marca;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de tipo Marca.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Marca
 */
public class ModMarcas extends AbstractListModel
{
    private final ArrayList<Marca> _aMarca;
    
    /**
     * Constructor. 
     * @throws java.sql.SQLException Error al obtener las marcas de la base de
     * datos.
     */
    public ModMarcas() throws SQLException 
    {
        _aMarca = Marca.Select(null, null);
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aMarca.size();
    }

    /**
     * Devuelve el elemento de tipo Marca en una posición dada.
     * @param iIndex Posición del elemento dentro de la lista.
     * @return Elemento de tipo Marca en esa posición.
     */
    @Override
    public Object getElementAt(int iIndex) 
    {
        return _aMarca.get(iIndex);
    }
    
    /**
     * Añade una marca a la lista.
     * @param marca Marca que se queire añadir.
     */
    public void addMarca(Marca marca)
    {
        _aMarca.add(marca);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina una marca de la lista en una posición dada. Tambien elimina la
     * marca de la base de datos, teniendo que eliminar sus categorías y
     * los artículos de esas categorías.
     * @param iIndex Posición del elemento a eliminar.
     * @throws Exception Error al eliminar la marca, alguna de sus categorías
     * o alguno de los artículos de esas categorías.
     */
    public void removeMarca(int iIndex) throws Exception
    {
        Marca marca = _aMarca.get(iIndex);
        
        ArrayList<Categoria> aCategorias = Categoria.Select(null, null, marca.getId());
        for(Categoria c : aCategorias){
            ArrayList<Articulo> aArticulos = Articulo.Select(null, null, c.getId(), null);
            for(Articulo a : aArticulos)
                a.Delete();
            
            c.Delete();
        }
        marca.Delete();
        
        _aMarca.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
}
