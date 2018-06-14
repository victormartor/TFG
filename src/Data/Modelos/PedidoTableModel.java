/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.Pedido;
import Data.Clases.Stock;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class PedidoTableModel extends AbstractTableModel {

	private ArrayList<Pedido> _aData;
	
	public PedidoTableModel(ArrayList<Pedido> aData) {
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
			case 0: return _aData.get(iRow).getId();
			case 1: return _aData.get(iRow).getFecha();
			case 2: return _aData.get(iRow).getNumArticulos();
                        case 3: return _aData.get(iRow).getTotal()+" €";
                        case 4: return _aData.get(iRow).getCodPostal();
                        case 5: return _aData.get(iRow).getDirEnvio();
			default: throw new IllegalStateException("Error, número de columna no válida.");
		}
	}
        
        @Override
        public String getColumnName(int iCol){
            switch(iCol) {
                    case 0: return "#";
                    case 1: return "Fecha";
                    case 2: return "NúmArtículos";
                    case 3: return "Total";
                    case 4: return "Código Postal";
                    case 5: return "Dirección envío";
                    default: throw new IllegalStateException("Error, número de columna no válida.");
		}
        }
        
        @Override
        public boolean isCellEditable (int iRow, int iCol){
            return false;
        }
       	
	public Pedido getData(int iRow) {
		return _aData.get(iRow);
	}
}
