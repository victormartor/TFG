package Data.Modelos;

import Data.Clases.PedidoPendiente;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * Modelo para hacer una lista de Pedidos pendientes.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see PedidoPendiente
 */
public class ModPedidos extends AbstractListModel
{
    private final ArrayList<PedidoPendiente> _aPedidoPendiente;
    
    /**
     * Constructor.
     */
    public ModPedidos()
    {
        _aPedidoPendiente = new ArrayList<>();
    }

    /**
     * Devuelve el tamaño de la lista.
     * @return Tamaño de la lista.
     */
    @Override
    public int getSize() 
    {
        return _aPedidoPendiente.size();
    }

    /**
     * Devuelve en forma de String el elemento en esa posición para que sea
     * reconocido en la lista de pedidos pendientes. 
     * El String es del formato: "Pedido #NumPedido - hora: xh:xm:xs"
     * @param iIndex Posición del elemento dentro de la lista.
     * @return Elemento convertido en String.
     */
    @Override
    public Object getElementAt(int iIndex) {
        PedidoPendiente pedido = _aPedidoPendiente.get(iIndex);
        return "Pedido #"+pedido.getNumPedido()+" - hora: "+pedido.getHora();
    }
    
    /**
     * Añade un nuevo pedido a la lista de tipo PedidoPendiente.
     * @param pedido PedidoPendiente que se quiere añadir.
     */
    public void addPedido(PedidoPendiente pedido)
    {
        _aPedidoPendiente.add(pedido);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    /**
     * Elimina un elemento de la lista.
     * @param iIndex Posición del elemento a eliminar.
     */
    public void removePedido(int iIndex)
    {
        _aPedidoPendiente.remove(iIndex);
        this.fireIntervalRemoved(iIndex, getSize(), getSize()+1);
    }
    
    /**
     * Devuelve el PedidoPendiente que se encuentra en la posición dada.
     * @param iIndex Posición del elemento.
     * @return Elemento de tipo PedidoPendiente dentro de la lista.
     */
    public PedidoPendiente getPedido(int iIndex)
    {
        return _aPedidoPendiente.get(iIndex);
    }
}
