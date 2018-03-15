/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author victor
 */
public class ListaRender extends JLabel implements ListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        
        //String valor = value.toString();
        setText(value.toString());
        
        ImageIcon iconoEscalado = null;
        
        String[] asPartes = value.toString().split(":");
        int iId_Imagen = Integer.parseInt(asPartes[2]);
        try {
            Image imagen = new ImageIcon(new Imagen(iId_Imagen).getRutaCompleta()).getImage();
            iconoEscalado = new ImageIcon (imagen.getScaledInstance(100,100,Image.SCALE_SMOOTH));
        } catch (Exception ex) {
            Logger.getLogger(ListaRender.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(iconoEscalado != null){
            setIcon(iconoEscalado);
        }
        /*
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(true);
        setFont(list.getFont());
        setOpaque(true);
        */
        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = Color.LIGHT_GRAY;
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);
        setOpaque(true);
       
        return this;
    }
    
}