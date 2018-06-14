package Data.Modelos;

import Data.Clases.Color;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * Modelo para hacer una lista de tipo Color.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Color
 */
public class ColorListModel extends AbstractListModel implements ComboBoxModel 
{
    private final ArrayList<Color> _aData;
    private Object _selection = null;

    /**
     * Constructor a partir de un array de tipo Color.
     * @param aData Array de Color.
     */
    public ColorListModel(ArrayList<Color> aData) 
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
     * @return Elemento de tipo Color en esa posición.
     */
    @Override
    public Color getElementAt(int iIndex) 
    {
        return _aData.get(iIndex);
    }

    /**
     * Selecciona un item de la lista.
     * @param item El item de tipo Color a seleccionar.
     */
    @Override
    public void setSelectedItem(Object item) {
        _selection = item;
    }

    /**
     * Devuelve el item seleccionado, si no hay ninguno seleccionado devuelve
     * null.
     * @return El item de tipo Color seleccionado.
     */
    @Override
    public Object getSelectedItem() {
        return _selection;
    }

    /**
     * Devuelve la posición del elemento que tenga ese Id en la lista.
     * Si el elemento no está en la lista devuelve el tamaño de esta.
     * @param iId_Color Id del elemento del que se quiere saber su posición.
     * @return La posición del elemento.
     */
    public int getIndexColor(int iId_Color){
        int iIndex = 0;
        while(_aData.get(iIndex).getId() != iId_Color) iIndex++;
        return iIndex;
    }
    
    /**
     * Añade un elemento de tipo Color a la lista.
     * @param color El elemento a añadir. 
     */
    public void addColor(Color color)
    {
        _aData.add(color);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina un elemento de la lista. También se elimina este elemento de la 
     * base de datos.
     * @param iIndex El índice del elemento a eliminar.
     * @throws Exception Hay un error en la eliminación del elemento en la
     * base de datos.
     */
    public void removeColor(int iIndex) throws Exception 
    {
        _aData.get(iIndex).Delete();
        _aData.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
}
