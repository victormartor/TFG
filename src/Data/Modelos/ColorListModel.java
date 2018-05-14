/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author victor
 */
public class ColorListModel 
    extends AbstractListModel
    implements ComboBoxModel {

    private ArrayList<Color> _aData;
    private Object _selection = null;

    public ColorListModel(ArrayList<Color> aData) {
            _aData = aData;
    }

    @Override
    public int getSize() {
            return _aData.size();
    }

    @Override
    public Color getElementAt(int index) {
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

    public int getIndexColor(int iId_Color){
        int index = 0;
        while(_aData.get(index).getId() != iId_Color) index++;
        return index;
    }
    
}
