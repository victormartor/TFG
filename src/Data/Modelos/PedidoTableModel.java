package Data.Modelos;

import Data.Clases.Pedido;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para hacer una tabla de tipo Pedido.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Pedido
 */
public class PedidoTableModel extends AbstractTableModel 
{
    private final ArrayList<Pedido> _aData;

    /**
     * Constructor a partir de un array de tipo Pedido.
     * @param aData Array de tipo Pedido.
     */
    public PedidoTableModel(ArrayList<Pedido> aData) 
    {
        _aData = aData; 
    }

    /**
     * Devuelve el número de columnas de la tabla.
     * @return Número de columnas de la tabla.
     */
    @Override
    public int getColumnCount() 
    {
        return 6;
    }

    /**
     * Devuelve el número de filas de la tabla, que es igual al tamaño de la
     * lista.
     * @return Número de filas de la tabla.
     */
    @Override
    public int getRowCount() 
    {
        return _aData.size();
    }

    /**
     * Devuelve el valor de una celda de la tabla.
     * @param iRow Fila en la tabla.
     * @param iCol Columna en la tabla.
     * @return Valor de la celda dada, tipo String.
     */
    @Override
    public Object getValueAt(int iRow, int iCol) 
    {
        switch(iCol) 
        {
            case 0: return _aData.get(iRow).getId();
            case 1: return _aData.get(iRow).getFecha();
            case 2: return _aData.get(iRow).getNumArticulos();
            case 3: {
                try {
                    return _aData.get(iRow).getTotal()+" €";
                } catch (SQLException ex) {
                    return "Error";
                }
            }
            case 4: return _aData.get(iRow).getCodPostal();
            case 5: return _aData.get(iRow).getDirEnvio();
            default: throw new IllegalStateException(
                    "Error, número de columna no válida.");
        }
    }

    /**
     * Devuelve el nombre de la columna dada.
     * @param iCol Posición de la columna.
     * @return Nombre de la columna en String.
     */
    @Override
    public String getColumnName(int iCol)
    {
        switch(iCol) 
        {
            case 0: return "#";
            case 1: return "Fecha";
            case 2: return "NúmArtículos";
            case 3: return "Total";
            case 4: return "Código Postal";
            case 5: return "Dirección envío";
            default: throw new IllegalStateException(
                    "Error, número de columna no válida.");
        }
    }

    /**
     * Devuelve si una celda es editable o no. En este caso ninguna celda es
     * editable.
     * @param iRow Fila de la celda.
     * @param iCol Columna de la celda.
     * @return Siempre devuelve false.
     */
    @Override
    public boolean isCellEditable (int iRow, int iCol)
    {
        return false;
    }

    /**
     * Devuelve el elemento de tipo Pedido en una fila dada.
     * @param iRow Fila del elemento.
     * @return Elemento de tipo Pedido.
     */
    public Pedido getData(int iRow) 
    {
        return _aData.get(iRow);
    }
}
