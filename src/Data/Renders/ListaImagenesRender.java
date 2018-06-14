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
 * Render para que una lista tenga solo imágenes.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class ListaImagenesRender extends JLabel implements ListCellRenderer
{
    /**
     * Personaliza el elemento de la lista cargando la imagen.
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
        Imagen imagen = null;
        
        //Dividir el elemento en distintas partes separadas por :
        String[] asPartes = value.toString().split(":");      
        
        //Cargar su imagen de la base de datos
        try {
            imagen = new Imagen(Integer.parseInt(asPartes[0]));
        } catch (NumberFormatException | SQLException ex) {
            Logger.getLogger(ListaImagenesRender.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        //Obtener el icono desde el archivo y escalándolo
        if(imagen != null)
        {
           try {
                Image image = new ImageIcon(imagen.getRuta()).getImage();
                iconoEscalado = new ImageIcon(
                        image.getScaledInstance(-1,100,Image.SCALE_SMOOTH));
            } catch (Exception ex) {
                Logger.getLogger(ListaRender.class.getName())
                        .log(Level.SEVERE, null, ex);
            } 
        }
        
        //Asignar el icono una vez obtenido
        if(iconoEscalado != null){
            setIcon(iconoEscalado);
        }
        
        //PERSONALIZAR COLORES/////////////////////////////////////////
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

