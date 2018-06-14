package Data.Modelos;

import Data.Clases.Stock;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo para hacer una tabla de tipo Stock.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Stock
 */
public class StockTableModel extends AbstractTableModel 
{
    private final ArrayList<Stock> _aData;

    /**
     * Constructor a partir de un array de tipo Stock.
     * @param aData Array de tipo Stock.
     */
    public StockTableModel(ArrayList<Stock> aData) 
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
            case 0: return _aData.get(iRow).getNombreMarca();
            case 1: return _aData.get(iRow).getNombreCategoria();
            case 2: return _aData.get(iRow).getNombreArticulo();
            case 3: return _aData.get(iRow).getNombreColor();
            case 4: return _aData.get(iRow).getNombreTalla();
            case 5: return _aData.get(iRow).getExistencias();
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
            case 0: return "Marcas";
            case 1: return "Categorías";
            case 2: return "Artículo";
            case 3: return "Color";
            case 4: return "Talla";
            case 5: return "Existencias";
            default: throw new IllegalStateException(
                    "Error, número de columna no válida.");
        }
    }

    /**
     * Devuelve si una celda es editable o no. 
     * @param iRow Fila de la celda.
     * @param iCol Columna de la celda.
     * @return Devuelve true si es la columna número 5 que es la de Existencias.
     */
    @Override
    public boolean isCellEditable (int iRow, int iCol)
    {
        return iCol == 5;
    }

    /**
     * Asigna el valor a las existencias.
     * @param aValue Valor de tipo entero.
     * @param iRow Fila de la tabla.
     * @param iCol Columna de la tabla.
     */
    @Override
    public void setValueAt (Object aValue, int iRow, int iCol)
    {
        Stock stock = _aData.get(iRow);
        stock.setExistencias(Integer.parseInt((String)aValue));
    }

    /**
     * Devuelve el elemento de tipo Stock en una fila dada.
     * @param iRow Fila del elemento.
     * @return Elemento de tipo Stock.
     */
    public Stock getData(int iRow) 
    {
        return _aData.get(iRow);
    }
}
