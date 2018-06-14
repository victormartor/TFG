/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Stock;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class StockTableModel extends AbstractTableModel {

	private ArrayList<Stock> _aData;
	
	public StockTableModel(ArrayList<Stock> aData) {
		_aData = aData; 
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		switch(iCol) {
			case 0: return _aData.get(iRow).getNombreMarca();
			case 1: return _aData.get(iRow).getNombreCategoria();
			case 2: return _aData.get(iRow).getNombreArticulo();
                        case 3: return _aData.get(iRow).getNombreColor();
                        case 4: return _aData.get(iRow).getNombreTalla();
                        case 5: return _aData.get(iRow).getExistencias();
			default: throw new IllegalStateException("Error, número de columna no válida.");
		}
	}
        
        @Override
        public String getColumnName(int iCol){
            switch(iCol) {
                    case 0: return "Marcas";
                    case 1: return "Categorías";
                    case 2: return "Artículo";
                    case 3: return "Color";
                    case 4: return "Talla";
                    case 5: return "Existencias";
                    default: throw new IllegalStateException("Error, número de columna no válida.");
		}
        }
        
        @Override
        public boolean isCellEditable (int iRow, int iCol){
            if(iCol == 5) return true;
            else return false;
        }
        
        @Override
        public void setValueAt (Object aValue, int iRow, int iCol){
            Stock stock = _aData.get(iRow);
            stock.setExistencias(Integer.parseInt((String)aValue));
        }
	
	public Stock getData(int iRow) {
		return _aData.get(iRow);
	}
}
