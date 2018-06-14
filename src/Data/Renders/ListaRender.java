package Data.Renders;

import Data.Clases.Imagen;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Render para que una lista tenga imágenes y el nombre del elemento.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class ListaRender extends JLabel implements ListCellRenderer
{
    /**
     * Personaliza el elemento de la lista cargando la imagen y el nombre.
     * @param list Lista
     * @param value Valor del elemento.
     * @param index Índice del elemento dentro de la lista.
     * @param isSelected El elemento está seleccionado.
     * @param cellHasFocus El elemento tiene el focus.
     * @return El elemento personalizado.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) 
    {   
        ImageIcon iconoEscalado = null;
        
        //Dividir el elemento en distintas partes separadas por :
        String[] asPartes = value.toString().split(":");
        
        //Asignar el texto
        setText(asPartes[1]);
        
        //Cargar su imagen de la base de datos y obtener el icono
        int iId_Imagen = Integer.parseInt(asPartes[2]);
        try {
            Image imagen = new ImageIcon(
                    new Imagen(iId_Imagen).getRuta()).getImage();
            iconoEscalado = new ImageIcon(
                    imagen.getScaledInstance(-1,100,Image.SCALE_SMOOTH));
        } catch (SQLException ex) {
            Logger.getLogger(ListaRender.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        //Asignar el icono una vez obtenido
        if(iconoEscalado != null){
            setIcon(iconoEscalado);
        }
       
        //PERSONALIZAR COLORES////////////////////////////////////////
        setEnabled(true);
        setFont(list.getFont());
        setOpaque(true);
        
        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) 
        {
            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) 
        {
            background = Color.BLACK;
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else 
        {
            background = Color.WHITE;
            foreground = Color.BLACK;
        }

        setBackground(background);
        setForeground(foreground);
        setOpaque(true);
       
        return this;
    }  
}
