/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Locale;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class modMarcas extends AbstractListModel
{
    private ArrayList<Marca> lMarca;
    
    public modMarcas() throws Exception
    {
        lMarca = Marca.Select(null, null);//new ArrayList<Marca>();
    }

    @Override
    public int getSize() {
        return lMarca.size();
    }

    @Override
    public Object getElementAt(int index) {
        /*
        Pedido p = lista.get(index);
        
        String mensaje = Cadenas.cadenas[tipo_idioma][6];
        
       mensaje += String.format(Locale.ENGLISH,"%d",p.getNumPedido());
       mensaje += " ["+p.getHora()+"]";
        
        return mensaje;
        */
        return lMarca.get(index).getNombre();
    }
    
    public void addMarca(Marca marca)
    {
        lMarca.add(marca);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void eliminarPedido(int index)
    {
        lMarca.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    /*
    public Pedido getPedido(int index)
    {
        return lista.get(index);
    }
    */
}
