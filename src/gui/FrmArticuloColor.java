/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Articulo;
import Data.Clases.Color;
import Data.Clases.Configuracion;
import Data.Clases.Imagen;
import Data.Data;
import Data.Modelos.ColorListModel;
import Data.Modelos.ModArticulo_Color;
import Data.Modelos.ModArticulo_Color_Imagen;
import Data.Modelos.ModImagenes;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author victor
 */
public class FrmArticuloColor extends javax.swing.JFrame {

    private Articulo _articulo = null;
    private Color _color = null;
    private ModArticulo_Color_Imagen _modArticulo_Color_Imagen = null;
    private String _srutaGuardada = null;
    private boolean _bModificar = false;
    private boolean _bCambios = false;
    
    /**
     * Creates new form FrmArticuloColor
     */
    public FrmArticuloColor(Integer iId_Articulo, Integer iId_Color) {
        initComponents();
        
        try {
            ArrayList<Color> lColores = Color.Select(null);
            lColores.add(new Color(-1));
            cmbColor.setModel(new ColorListModel(lColores));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al leer colores.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            _articulo = new Articulo(iId_Articulo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al buscar artículo.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        if(iId_Color != null){
            _bModificar = true;
            try {
                _color = new Color(iId_Color);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al buscar color.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            cmbColor.setSelectedIndex(((ColorListModel)cmbColor.getModel()).getIndexColor(iId_Color));
            cmbColor.setEnabled(false);
            this.setTitle("Modificar color "+_color);
             
            try {
                _modArticulo_Color_Imagen = new ModArticulo_Color_Imagen(_articulo, _color);
                lImagenes.setModel(_modArticulo_Color_Imagen);
                lImagenes.setCellRenderer(new ListaImagenesRender());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al buscar imágenes.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if(salir())
                    FrmArticuloColor.this.dispose();
            }
        });
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
    
    private void guardar(){
        if(!_bModificar){
            try {
                _articulo.Add_Color(_color);
                _bModificar = true;
            } catch (Exception ex) {
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
    
    private void comprobar_cambios(){
        if(_bCambios){
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
    
    private boolean salir(){
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
        else{
            if(_color != null && _modArticulo_Color_Imagen.getSize() == 0){
                JOptionPane.showMessageDialog(null,
                "¡ATENCIÓN! Se debe asignar al menos una imagen al color.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
                return false;
            }
            else{
                return true;
            }        
        }  
    }
    
    private boolean color_esta_asociado(){
        
        ArrayList<Integer> lColores = _articulo.getColores();
        boolean bEsta_asociado = false;
        for(Integer i : lColores)
            if(i == _color.getId()) bEsta_asociado = true;

        if(bEsta_asociado){
            JOptionPane.showMessageDialog(null, 
                "Error, ese color ya está asociado a este artículo.", 
                "Color ya asociado", 
                JOptionPane.WARNING_MESSAGE);

            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean comprobar_color(){
        if(_color == null || _color.getId()==-1){
            JOptionPane.showMessageDialog(null, 
                "Error, debe elegir primero un color.", 
                "Elija un color", 
                JOptionPane.WARNING_MESSAGE);
            
            return false;
        }
        else
            return true;  
    }

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
        if(salir()){
            java.awt.EventQueue.invokeLater(() -> {
                Frame frmArticulo = null;
                try {
                    frmArticulo = new FrmArticulo(_articulo.getId(),null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al leer artículo.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                if(frmArticulo != null){
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
        if(comprobar_color()){
            JFileChooser ventanaElegirImagen = new JFileChooser();
            String rutaImagenes = Data.getRutaImagenes();
 
            if(rutaImagenes != null) ventanaElegirImagen.setCurrentDirectory(new File(rutaImagenes));
            if(_srutaGuardada != null) ventanaElegirImagen.setCurrentDirectory(new File(_srutaGuardada));
            JLabel img = new JLabel();
            img.setPreferredSize(new Dimension(175,175));
            ventanaElegirImagen.setAccessory(img);

            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG) y PNG", "jpg","jpeg", "png");
            ventanaElegirImagen.addChoosableFileFilter(filtro);
            ventanaElegirImagen.setFileFilter(filtro);
            ventanaElegirImagen.setDialogTitle("Abrir Imagen");

            

            // Add property change listener
            ventanaElegirImagen.addPropertyChangeListener(new PropertyChangeListener(){

                // When any JFileChooser property changes, this handler
                // is executed
                public void propertyChange(final PropertyChangeEvent pe)
                {
                    // Create SwingWorker for smooth experience
                    SwingWorker<Image,Void> worker=new SwingWorker<Image,Void>(){

                        // The image processing method
                        protected Image doInBackground()
                        {
                            // If selected file changes..
                            if(pe.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY))
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
                                    return bim.getScaledInstance(-1,170,BufferedImage.SCALE_FAST);

                                }catch(Exception e){
                                    // If there is a problem reading image,
                                    // it might not be a valid image or unable
                                    // to read
                                    img.setText(" Not valid image/Unable to read");
                                }
                            }

                        return null;
                        }

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
                            }catch(Exception e){
                                // Print error occured
                                img.setText(" Error occured.");
                            }
                        }
                    };

                    // Start worker thread
                    worker.execute();
                }
            });

            int ventana = ventanaElegirImagen.showOpenDialog(null);
            if(ventana == JFileChooser.APPROVE_OPTION)
            {
                File file = ventanaElegirImagen.getSelectedFile();
                String sRuta = null;
                Imagen imagen = null;
                try {
                    sRuta = file.getAbsolutePath();
                    sRuta = sRuta.replace(file.getName(), "");
                    _srutaGuardada = sRuta;
                    imagen = Imagen.Create(file, rutaImagenes);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al subir imagen.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                if(imagen != null){
                    try {
                        _modArticulo_Color_Imagen.addImagen(imagen);
                    } catch (Exception ex) {
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
                //_bCambios = true;
            }
        }
    }//GEN-LAST:event_butEliminarActionPerformed

    private void cmbColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorActionPerformed
        if(!_bModificar){
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
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al crear color.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    } 
                
                    try {
                        cmbColor.setModel(new ColorListModel(Color.Select(null)));
                        cmbColor.setSelectedIndex(((ColorListModel)cmbColor.getModel()).getIndexColor(color.getId()));
                        cmbColor.setEnabled(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al leer colores.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    _color = color;
                    
                    try {
                        _modArticulo_Color_Imagen = new ModArticulo_Color_Imagen(_articulo, _color);
                        lImagenes.setModel(_modArticulo_Color_Imagen);
                        lImagenes.setCellRenderer(new ListaImagenesRender());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al buscar imágenes.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    _bCambios = true;
                }
            }
            else{
                if(!color_esta_asociado()){
                    cmbColor.setEnabled(false);

                    try {
                        _modArticulo_Color_Imagen = new ModArticulo_Color_Imagen(_articulo, _color);
                        lImagenes.setModel(_modArticulo_Color_Imagen);
                        lImagenes.setCellRenderer(new ListaImagenesRender());
                    } catch (Exception ex) {
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
