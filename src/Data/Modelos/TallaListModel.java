/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Color;
import Data.Clases.Talla;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class TallaListModel 
    extends AbstractListModel
    implements ComboBoxModel {

    private ArrayList<Talla> _aData;
    private Object _selection = null;

    public TallaListModel(ArrayList<Talla> aData) {
            _aData = aData;
    }

    @Override
    public int getSize() {
            return _aData.size();
    }

    @Override
    public Talla getElementAt(int index) {
            return _aData.get(index);
    }

    @Override
    public void setSelectedItem(Object o) {
        _selection = o;
    }

    @Override
    public Object getSelectedItem() {
        return _selection;
    }

    public int getIndexTalla(int iId_Talla){
        int index = 0;
        while(_aData.get(index).getId() != iId_Talla) index++;
        return index;
    }
    
    public void addTalla(Talla talla) throws Exception
    {
        _aData.add(talla);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeTalla(int index) throws Exception
    {
        _aData.get(index).Delete();
        _aData.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
}
