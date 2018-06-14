package gui;

import Data.Clases.Articulo;
import Data.Clases.Color;
import Data.Clases.Imagen;
import Data.Data;
import Data.Modelos.ColorListModel;
import Data.Modelos.ModArticulo_Color_Imagen;
import Data.Renders.ListaImagenesRender;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Ventana desde la que se puede crear o modificar un color y asociarlo a un
 * artículo existente. Desde ella se le pueden asignar las imágenes de ese
 * artículo para ese color.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Articulo
 * @see Color
 * @see ModArticulo_Color_Imagen
 */
public class FrmArticuloColor extends javax.swing.JFrame 
{
    private Articulo _articulo;
    private Color _color;
    private ModArticulo_Color_Imagen _modArticulo_Color_Imagen;
    
    /**
     * La varible _sRutaGuardada será la ruta donde estaba la última imagen
     * que asociamos al color. Con esto conseguimos que si se van a asociar
     * varias imágenes no haya que volver a buscar la carpeta.
     */
    private String _srutaGuardada;
    
    /**
     * La variable _bModificar es un booleano que estará a false cuando se esté
     * creando un elemento nuevo, y estará a true cuando se esté modificando
     * un elemento existente.
     */
    private boolean _bModificar;
    
    /**
     * La variable _bCambios es un booleano que nos avisará si se ha realizado
     * algún cambio y este no ha sido guardado.
     */
    private boolean _bCambios;
    
    /**
     * Crea un nuevo formulario de ArticuloColor.
     * @param iId_Articulo El Id del Artículo al que se va a asociar el Color.
     * @param iId_Color Si se va a modificar un color existente este 
     * parámetro es su Id en la base de datos. Si se va a asociar un Color
     * nuevo, este parámetro debe ser null.
     */
    public FrmArticuloColor(Integer iId_Articulo, Integer iId_Color) {
        initComponents();
        _bCambios = false;
        
        //Cargar la lista de colores disponibles de la base de datos
        try {
            ArrayList<Color> lColores = Color.Select(null);
            lColores.add(new Color(-1));
            cmbColor.setModel(new ColorListModel(lColores));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al leer colores.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        //Cargar el artículo de la base de datos
        try {
            _articulo = new Articulo(iId_Articulo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar artículo.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        /**
         * Si el color ya está asociado al artículo cargar sus imágenes 
         * asociadas y deshabilitar que se pueda cambiar el nombre.
         */
        if(iId_Color != null){
            _bModificar = true;
            try {
                _color = new Color(iId_Color);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al buscar color.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            cmbColor.setSelectedIndex(((ColorListModel)cmbColor.getModel())
                    .getIndexColor(iId_Color));
            cmbColor.setEnabled(false);
             
            try {
                _modArticulo_Color_Imagen = 
                        new ModArticulo_Color_Imagen(_articulo, _color);
                lImagenes.setModel(_modArticulo_Color_Imagen);
                lImagenes.setCellRenderer(new ListaImagenesRender());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al buscar imágenes.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
        else
            _bModificar = false;
        
        //Personalizar comportamiento del botón de cerrar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if(es_posible_salir())
                    FrmArticuloColor.this.dispose();
            }
        });
    }
    
    /**
     * Personalizar el icono de la ventana
     * @return Devuelve el icono personalizado.
     */
    @Override
    public Image getIconImage() {
       return Toolkit.getDefaultToolkit()
               .getImage(ClassLoader.getSystemResource("img/boton_48.png"));
    }
    
    //MÉTODOS PRIVADOS//////////////////////////////////////////////////////////
     
    //Guardar cambios
    private void guardar()
    {
        if(!_bModificar)
        {
            try {
                _articulo.Add_Color(_color);
                _bModificar = true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al añadir color.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
        
        _bCambios = false;
        JOptionPane.showMessageDialog(null, 
        "Los cambios se han guardado correctamente.", 
        "Mensaje del sistema", 
        JOptionPane.PLAIN_MESSAGE);
    }
    
    //Comprobar si existem cambios sin guardar
    private void comprobar_cambios()
    {
        if(_bCambios)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "Hay cambios sin guardar, ¿desea guardarlos antes de continuar?.",
                "Mensaje del sistema",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                guardar();
            }
        }
    }
    
    //Comprobar si es posible cerrar la ventana
    private boolean es_posible_salir()
    {
        comprobar_cambios();
        if(!_bModificar && _color != null){
            try {
                _articulo.Delete_Color(_color.getId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al eliminar color.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            return true;
        }
        else
        {
            if(_color != null && _modArticulo_Color_Imagen.getSize() == 0){
                JOptionPane.showMessageDialog(null,
                "¡ATENCIÓN! Se debe asignar al menos una imagen al color.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
                return false;
            }
            else
                return true;     
        }  
    }
    
    //Comprobar si un color está ya asociado al artículo o no
    private boolean color_esta_asociado()
    {
        ArrayList<Integer> lColores = _articulo.getColores();
        boolean bEsta_asociado = false;
        for(Integer i : lColores)
            if(i == _color.getId()) bEsta_asociado = true;

        if(bEsta_asociado)
        {
            JOptionPane.showMessageDialog(null, 
                "Error, ese color ya está asociado a este artículo.", 
                "Color ya asociado", 
                JOptionPane.WARNING_MESSAGE);

            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Comprobar si se ha elegido el nombre del color
    private boolean comprobar_color()
    {
        if(_color == null || _color.getId()== -1)
        {
            JOptionPane.showMessageDialog(null, 
                "Error, debe elegir primero un color.", 
                "Elija un color", 
                JOptionPane.WARNING_MESSAGE);
            
            return false;
        }
        else
            return true;  
    }
    ////////////////////////////////////////////////////////////////////////////

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblColor = new javax.swing.JLabel();
        cmbColor = new javax.swing.JComboBox<>();
        butAtras = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        lblImagenes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lImagenes = new javax.swing.JList<>();
        butSubir = new javax.swing.JButton();
        butEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Asignar color al artículo");
        setIconImage(getIconImage());
        setResizable(false);

        lblColor.setText("Color");

        cmbColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColorActionPerformed(evt);
            }
        });

        butAtras.setBackground(java.awt.Color.red);
        butAtras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAtras.setForeground(new java.awt.Color(255, 255, 255));
        butAtras.setText("Atrás");
        butAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAtrasActionPerformed(evt);
            }
        });

        butGuardar.setBackground(java.awt.Color.green);
        butGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butGuardar.setText("Guardar cambios");
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });

        lblImagenes.setText("Imágenes");

        lImagenes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lImagenes);

        butSubir.setBackground(new java.awt.Color(0, 0, 0));
        butSubir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butSubir.setForeground(new java.awt.Color(255, 255, 255));
        butSubir.setText("Subir Imagen");
        butSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubirActionPerformed(evt);
            }
        });

        butEliminar.setBackground(java.awt.Color.red);
        butEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminar.setForeground(new java.awt.Color(255, 255, 255));
        butEliminar.setText("Eliminar Imagen");
        butEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butAtras))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblColor)
                            .addComponent(cmbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImagenes))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butSubir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblImagenes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butAtras)
                            .addComponent(butGuardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(butSubir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butEliminar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        if(es_posible_salir())
        {
            java.awt.EventQueue.invokeLater(() -> {
                Frame frmArticulo = null;
                try {
                    frmArticulo = new FrmArticulo(_articulo.getId(),null);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al leer artículo.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                if(frmArticulo != null){
                    frmArticulo.setTitle("Modificar artículo");
                    frmArticulo.setLocationRelativeTo(this);
                    frmArticulo.setVisible(true);
                }
            });
            this.dispose();
        }
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void butSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubirActionPerformed
        if(comprobar_color())
        {
            JFileChooser ventanaElegirImagen = new JFileChooser();
            String rutaImagenes = Data.getRutaImagenes();
           
            if(_srutaGuardada != null) 
                ventanaElegirImagen.setCurrentDirectory(new File(_srutaGuardada));
            else if(rutaImagenes != null) 
                ventanaElegirImagen.setCurrentDirectory(new File(rutaImagenes));
            
            JLabel img = new JLabel();
            img.setPreferredSize(new Dimension(175,175));
            ventanaElegirImagen.setAccessory(img);

            FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                    "Formatos de Archivos JPEG(*.JPG;*.JPEG) y PNG", "jpg",
                    "jpeg", "png");
            ventanaElegirImagen.addChoosableFileFilter(filtro);
            ventanaElegirImagen.setFileFilter(filtro);
            ventanaElegirImagen.setDialogTitle("Abrir Imagen");           

            // Add property change listener
            ventanaElegirImagen.addPropertyChangeListener(
                    (final PropertyChangeEvent pe) -> {
                // Create SwingWorker for smooth experience
                SwingWorker<Image,Void> worker = new SwingWorker<Image,Void>()
                {               
                    // The image processing method
                    @Override
                    protected Image doInBackground()
                    {
                        // If selected file changes..
                        if(pe.getPropertyName().equals(JFileChooser
                                .SELECTED_FILE_CHANGED_PROPERTY))
                        {
                            // Get selected file
                            File f=ventanaElegirImagen.getSelectedFile();
                            
                            try
                            {
                                img.setText("");
                                // Create FileInputStream for file
                                FileInputStream fin=new FileInputStream(f);
                                
                                // Read image from fin
                                BufferedImage bim=ImageIO.read(fin);
                                
                                // Return the scaled version of image
                                return bim.getScaledInstance(-1,170,
                                        BufferedImage.SCALE_FAST);              
                            }catch(IOException e){
                                // If there is a problem reading image,
                                // it might not be a valid image or unable
                                // to read
                                img.setText(" Not valid image/Unable to read");
                            }
                        }
                        
                        return null;
                    }
                    
                    @Override
                    protected void done()
                    {
                        try
                        {
                            // Get the image
                            Image i=get(1L,TimeUnit.NANOSECONDS);

                            // If i is null, go back!
                            if(i==null) return;

                            // Set icon otherwise
                            img.setIcon(new ImageIcon(i));
                        }catch(InterruptedException | ExecutionException | 
                                TimeoutException e){
                            // Print error occured
                            img.setText(" Error occured.");
                        }
                    }
                };
                
                // Start worker thread
                worker.execute();
            } // When any JFileChooser property changes, this handler
            // is executed
            );

            int ventana = ventanaElegirImagen.showOpenDialog(null);
            if(ventana == JFileChooser.APPROVE_OPTION)
            {
                File file = ventanaElegirImagen.getSelectedFile();
                Imagen imagen = null;
                try {
                    String sRuta = file.getAbsolutePath();
                    sRuta = sRuta.replace(file.getName(), "");
                    _srutaGuardada = sRuta;
                    imagen = Imagen.Create(file, rutaImagenes);   
                } catch (IOException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al subir imagen.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                if(imagen != null)
                {
                    try {
                        _modArticulo_Color_Imagen.addImagen(imagen);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al añadir imagen.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_butSubirActionPerformed

    private void butEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarActionPerformed
        int index = lImagenes.getSelectedIndex();
        
        if(index != -1)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? La imagen dejará de estar asociada a este color."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar imagen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modArticulo_Color_Imagen.removeImagen(index);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al eliminar imagen.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_butEliminarActionPerformed

    private void cmbColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorActionPerformed
        if(!_bModificar)
        {
            _color = (Color)cmbColor.getSelectedItem();
            if(_color.getId()==-1){
                String sColor = (String)JOptionPane.showInputDialog(
                    this,
                    "Color",
                    "Nuevo color",
                    JOptionPane.PLAIN_MESSAGE);
                
                Color color = null;
                if(sColor!= null){
                   try {
                        color = Color.Create(sColor);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al crear color.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    } 
                
                    try {
                        cmbColor.setModel(new ColorListModel(Color.Select(null)));
                        cmbColor.setSelectedIndex(((ColorListModel)cmbColor
                                .getModel()).getIndexColor(color.getId()));
                        cmbColor.setEnabled(false);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al leer colores.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    _color = color;
                    
                    try {
                        _modArticulo_Color_Imagen = 
                                new ModArticulo_Color_Imagen(_articulo, _color);
                        lImagenes.setModel(_modArticulo_Color_Imagen);
                        lImagenes.setCellRenderer(new ListaImagenesRender());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al buscar imágenes.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    _bCambios = true;
                }
            }
            else
            {
                if(!color_esta_asociado())
                {
                    cmbColor.setEnabled(false);

                    try {
                        _modArticulo_Color_Imagen = 
                                new ModArticulo_Color_Imagen(_articulo, _color);
                        lImagenes.setModel(_modArticulo_Color_Imagen);
                        lImagenes.setCellRenderer(new ListaImagenesRender());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al buscar imágenes.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    _bCambios = true;
                } 
                else{
                    cmbColor.setSelectedItem(null);
                }
            }
        }
    }//GEN-LAST:event_cmbColorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butEliminar;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butSubir;
    private javax.swing.JComboBox<String> cmbColor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lImagenes;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblImagenes;
    // End of variables declaration//GEN-END:variables
}
