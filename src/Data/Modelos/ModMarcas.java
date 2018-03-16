/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Modelos;

import Data.Marca;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author victor
 */
public class ModMarcas extends AbstractListModel
{
    private ArrayList<Marca> lMarca;
    
    public ModMarcas() throws Exception
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
        return lMarca.get(index);
    }
    
    public void addMarca(Marca marca) throws Exception
    {
        lMarca.add(marca);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void removeMarca(int index) throws Exception
    {
        lMarca.get(index).Delete();
        lMarca.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public Marca getMarca(int index)
    {
        return lMarca.get(index);
    }
}
