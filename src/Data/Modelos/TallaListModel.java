package Data.Modelos;

import Data.Clases.Talla;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * Modelo para hacer una lista de tipo Talla.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Talla
 */
public class TallaListModel extends AbstractListModel implements ComboBoxModel 
{
    private final ArrayList<Talla> _aData;
    private Object _selection = null;

    /**
     * Constructor a partir de un array de tipo Talla.
     * @param aData Array de Talla.
     */
    public TallaListModel(ArrayList<Talla> aData) 
    {
        _aData = aData;
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aData.size();
    }

    /**
     * Devuelve el elemento en una posicion dada.
     * @param iIndex Posición del elemento.
     * @return Elemento de tipo Talla en esa posición.
     */
    @Override
    public Talla getElementAt(int iIndex) 
    {
        return _aData.get(iIndex);
    }

    /**
     * Selecciona un item de la lista.
     * @param item El item de tipo Talla a seleccionar.
     */
    @Override
    public void setSelectedItem(Object item) 
    {
        _selection = item;
    }

    /**
     * Devuelve el item seleccionado, si no hay ninguno seleccionado devuelve
     * null.
     * @return El item de tipo Talla seleccionado.
     */
    @Override
    public Object getSelectedItem() 
    {
        return _selection;
    }

    /**
     * Devuelve la posición del elemento que tenga ese Id en la lista.
     * Si el elemento no está en la lista devuelve el tamaño de esta.
     * @param iId_Talla Id del elemento del que se quiere saber su posición.
     * @return La posición del elemento.
     */
    public int getIndexTalla(int iId_Talla)
    {
        int index = 0;
        while(_aData.get(index).getId() != iId_Talla) index++;
        return index;
    }
    
    /**
     * Añade un elemento de tipo Talla a la lista.
     * @param talla El elemento a añadir. 
     */
    public void addTalla(Talla talla)
    {
        _aData.add(talla);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina un elemento de la lista. También se elimina este elemento de la 
     * base de datos.
     * @param iIndex El índice del elemento a eliminar.
     * @throws Exception Hay un error en la eliminación del elemento en la
     * base de datos.
     */
    public void removeTalla(int iIndex) throws Exception
    {
        _aData.get(iIndex).Delete();
        _aData.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
}
