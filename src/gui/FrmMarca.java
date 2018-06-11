/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Categoria;
import Data.Clases.Configuracion;
import Data.Data;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Modelos.ModCategorias;
import Data.Modelos.ModImagenes;
import Data.Modelos.ModMarcas;
import Data.Renders.ListaImagenesRender;
import Data.Renders.ListaRender;
import java.awt.Color;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.Config;

/**
 *
 * @author victor
 */
public class FrmMarca extends javax.swing.JFrame {

    private Marca _marca = null;
    private ModCategorias _modCategorias = null;
    
    private boolean _bModificar = false;
    private boolean _bCambios = false;
    /**
     * Creates new form FrmMarca
     */
    public FrmMarca(Integer iId_Marca) throws Exception {
        initComponents();
        //getContentPane().setBackground(Color.white);
        
        if(iId_Marca != null){
            _bModificar = true;
            _marca = new Marca(iId_Marca);
            txtNombre.setText(_marca.getNombre());
        }
        else{
            _marca = Marca.Create("", -1);
        }
        
        cargarImagen();
        
        _modCategorias = new ModCategorias(_marca.getId());
        lCategorias.setModel(_modCategorias);
        lCategorias.setCellRenderer(new ListaRender());
        
        if(_bModificar)
            this.setTitle("Modificar marca");
        
        lCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    comprobar_cambios();
                    modificarCategoria();
                }
           }
        });
        
        txtNombre.requestFocus();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if(salir())
                    FrmMarca.this.dispose();
            }
        });
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
    
    private void subir_imagen(){
        JFileChooser ventanaElegirImagen = new JFileChooser();
        String rutaImagenes = Data.getRutaImagenes();
        
        if(rutaImagenes != null) {
            File folder = new File(rutaImagenes);
            if(!folder.exists()) folder.mkdirs();
            ventanaElegirImagen.setCurrentDirectory(folder);
        }
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
                imagen = Imagen.Create(file, rutaImagenes);     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al subir la imagen.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(imagen != null){
                try {
                    if(_marca.getId_Imagen() != -1){
                        int id_imagen = _marca.getId_Imagen();
                        _marca.setId_Imagen(imagen.getId());
                        _marca.Update();
                        new Imagen(id_imagen).Delete();
                    }
                    else
                        _marca.setId_Imagen(imagen.getId());
                 
                    cargarImagen();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al actualizar la imagen.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        _bCambios = true;
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
    
    private void guardar(){
        try {
            _marca.setNombre(txtNombre.getText());
            _marca.Update();
            
            _bModificar = true;
            _bCambios = false;

            JOptionPane.showMessageDialog(null, 
            "Los cambios se han guardado correctamente.", 
            "Mensaje del sistema", 
            JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarCategoria(){
        int iIndex = lCategorias.getSelectedIndex();
        Categoria categoria = _modCategorias.getCategoria(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmCategoria = null;
            try {
                frmCategoria = new FrmCategoria(categoria.getId(), _marca.getId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al buscar la categoría.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(frmCategoria != null){
                frmCategoria.setLocationRelativeTo(FrmMarca.this);
                frmCategoria.setVisible(true);
            }
            this.dispose();
        });
    }
    
    private void cargarImagen() throws Exception{
        if(_marca.getId_Imagen() != -1){
            iconoImagen.setText("");
            Image image = new ImageIcon(new Imagen(_marca.getId_Imagen()).getRuta()).getImage();
            ImageIcon iconoEscalado = new ImageIcon (image.getScaledInstance(-1,100,Image.SCALE_SMOOTH));
            iconoImagen.setIcon(iconoEscalado);
        }
        else{
            iconoImagen.setText("<html><body>Elige una <br> imagen</body></html>");
        }
    }
    
    private boolean salir(){
        comprobar_cambios();
        if(!_bModificar){
            try {
                _marca.Delete();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al eliminar la marca.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            return true;
        }
        else{
            try {
                if(new Marca(_marca.getId()).getId_Imagen() == -1){
                    JOptionPane.showMessageDialog(null,
                            "¡ATENCIÓN! Se debe asignar una imagen a la marca.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                else{
                    return true;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al salir.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
                return false;
            } 
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        butAtras = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        iconoImagen = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblCategorias = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lCategorias = new javax.swing.JList<>();
        butSubir = new javax.swing.JButton();
        butAgregarCat = new javax.swing.JButton();
        butEliminarCat = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtMarca = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar marca");
        setIconImage(getIconImage());
        setResizable(false);

        lblNombre.setText("Nombre");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
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

        iconoImagen.setBackground(new java.awt.Color(255, 255, 255));
        iconoImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconoImagen.setToolTipText("Imagen de la marca");
        iconoImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        iconoImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconoImagenMouseClicked(evt);
            }
        });

        lblCategorias.setText("Categorías");

        lCategorias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lCategorias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lCategorias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lCategorias);

        butSubir.setBackground(new java.awt.Color(0, 0, 0));
        butSubir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butSubir.setForeground(new java.awt.Color(255, 255, 255));
        butSubir.setText("Subir imagen");
        butSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubirActionPerformed(evt);
            }
        });

        butAgregarCat.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarCat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarCat.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarCat.setText("Agregar Categoría");
        butAgregarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarCatActionPerformed(evt);
            }
        });

        butEliminarCat.setBackground(java.awt.Color.red);
        butEliminarCat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarCat.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarCat.setText("Eliminar Categoría");
        butEliminarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarCatActionPerformed(evt);
            }
        });

        txtMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMarca.setText("MARCA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addGap(0, 320, Short.MAX_VALUE))
                            .addComponent(txtNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(iconoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategorias)
                            .addComponent(txtMarca))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butAtras))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butEliminarCat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butAgregarCat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMarca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(iconoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCategorias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(butAgregarCat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(butEliminarCat))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butAtras)
                            .addComponent(butGuardar)))
                    .addComponent(butSubir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        if(salir()){
            java.awt.EventQueue.invokeLater(() -> {
                Frame ifrMarca = null;
                try {
                    ifrMarca = new IfrMarca();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al leer las marcas.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                if(ifrMarca != null){
                    ifrMarca.setLocationRelativeTo(FrmMarca.this);
                    ifrMarca.setVisible(true);
                }
            });
            this.dispose();
        }
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void iconoImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconoImagenMouseClicked
        subir_imagen();
    }//GEN-LAST:event_iconoImagenMouseClicked

    private void butSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubirActionPerformed
        subir_imagen();
    }//GEN-LAST:event_butSubirActionPerformed

    private void butAgregarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarCatActionPerformed
        comprobar_cambios();
        try {
            if(new Marca(_marca.getId()).getId_Imagen() == -1){
                JOptionPane.showMessageDialog(null,
                        "¡ATENCIÓN! Se debe asignar una imagen a la marca.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                
            }
            else{
                java.awt.EventQueue.invokeLater(() -> {
                    Frame frmCategoria = null;
                    try {
                        frmCategoria = new FrmCategoria(null, _marca.getId());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al crear una categoría vacía.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    if(frmCategoria != null){
                        frmCategoria.setLocationRelativeTo(FrmMarca.this);
                        frmCategoria.setVisible(true);
                    }
                    this.dispose();
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al salir.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_butAgregarCatActionPerformed

    private void butEliminarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarCatActionPerformed
        int index = lCategorias.getSelectedIndex();
        
        if(index != -1)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Se eliminarán además todos los artículos de esta categoría."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar categoría",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modCategorias.removeCategoria(index);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al eliminar categoría.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
                
                _bCambios = true;
            }
        }
    }//GEN-LAST:event_butEliminarCatActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        _bCambios = true;
    }//GEN-LAST:event_txtNombreKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAgregarCat;
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butEliminarCat;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butSubir;
    private javax.swing.JLabel iconoImagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JList<String> lCategorias;
    private javax.swing.JLabel lblCategorias;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel txtMarca;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
