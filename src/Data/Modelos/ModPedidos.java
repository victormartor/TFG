/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Clases.PedidoPendiente;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModPedidos extends AbstractListModel
{
    private ArrayList<PedidoPendiente> lPedidos;
    
    public ModPedidos()
    {
        lPedidos = new ArrayList<PedidoPendiente>();
    }

    @Override
    public int getSize() {
        return lPedidos.size();
    }

    @Override
    public Object getElementAt(int index) {
        PedidoPendiente pedido = lPedidos.get(index);
        return "Pedido #"+pedido.getNumPedido()+" - hora: "+pedido.getHora();
    }
    
    public void addPedido(PedidoPendiente pedido)
    {
        lPedidos.add(pedido);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removePedido(int index)
    {
        lPedidos.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public PedidoPendiente getPedido(int index)
    {
        return lPedidos.get(index);
    }
}
