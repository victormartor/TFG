/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Configuracion;
import Data.Data;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Modelos.ModArticulos;
import Data.Modelos.ModCategorias;
import Data.Renders.ListaRender;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author victor
 */
public class FrmCategoria extends javax.swing.JFrame {

    private Categoria _categoria = null;
    private ModArticulos _modArticulos = null;
    private boolean _bModificar = false;
    private boolean _bCambios = false;
    
    /**
     * Creates new form FrmCategoria
     */
    public FrmCategoria(Integer iId_Categoria, Integer iId_Marca) throws Exception {
        initComponents();
        
        if(iId_Categoria != null){
            _bModificar = true;
            _categoria = new Categoria(iId_Categoria);
            txtNombre.setText(_categoria.getNombre());
        }
        else{
            _categoria = Categoria.Create("", -1, iId_Marca);
        }
        
        cargarImagen();
        
        //LISTA DE ARTÍCULOS
        _modArticulos = new ModArticulos(_categoria.getId(),null);
        lArticulos.setModel(_modArticulos);
        lArticulos.setCellRenderer(new ListaRender());
        
        if(_bModificar)
            this.setTitle("Modificar categoría");
        
        lArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    comprobar_cambios();
                    modificarArticulo();
                }
           }
        });
        
        txtNombre.requestFocus();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if(salir())
                    FrmCategoria.this.dispose();
            }
        });
    }
    
    private void subir_imagen(){
        JFileChooser ventanaElegirImagen = new JFileChooser();
        String rutaImagenes = null;
        try {
            rutaImagenes = Configuracion.Select("Ruta_imagenes", null).get(0).getValor();
        } catch (Exception ex) {
            System.out.println("Error al obtener la ruta de las imagenes. "+ex.toString());
        }
        if(rutaImagenes != null) ventanaElegirImagen.setCurrentDirectory(new File(rutaImagenes));
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
                            // Create FileInputStream for file
                            FileInputStream fin=new FileInputStream(f);
                           
                            // Read image from fin
                            BufferedImage bim=ImageIO.read(fin);
                           
                            // Return the scaled version of image
                            return bim.getScaledInstance(178,170,BufferedImage.SCALE_FAST);
                           
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
                System.out.println("Error al subir la imagen. "+ ex.toString());
            }
            if(imagen != null){
                try{
                    if(_categoria.getId_Imagen() != -1){
                        int id_imagen = _categoria.getId_Imagen();
                        _categoria.setId_Imagen(imagen.getId());
                        new Imagen(id_imagen).Delete();
                    }
                    else
                        _categoria.setId_Imagen(imagen.getId());
                
                    _categoria.Update();
                    cargarImagen();
                } catch (Exception ex) {
                    System.out.println("Error en la actualización de la imagen de la marca. "+ ex.toString());
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
            _categoria.setNombre(txtNombre.getText());
            _categoria.Update();
            
            _bModificar = true;
            _bCambios = false;

            JOptionPane.showMessageDialog(null, 
            "Los cambios se han guardado correctamente.", 
            "Mensaje del sistema", 
            JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            System.out.println("Error al guardar. "+ ex.toString());
        }
    }
    
    private void modificarArticulo(){
        int iIndex = lArticulos.getSelectedIndex();
        Articulo articulo = _modArticulos.getArticulo(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmArticulo = null;
            try {
                frmArticulo = new FrmArticulo(articulo.getId(), _categoria.getId());
            } catch (Exception ex) {
                System.out.println("Error al buscar el artículo en la base de datos. "+ ex.toString());
            }
            if(frmArticulo != null){
                frmArticulo.setLocationRelativeTo(FrmCategoria.this);
                frmArticulo.setVisible(true);
            }
            this.dispose();
        });
    }
    
    private void cargarImagen() throws Exception{
        if(_categoria.getId_Imagen() != -1){
            Image image = new ImageIcon(new Imagen(_categoria.getId_Imagen()).getRutaCompleta()).getImage();
            ImageIcon iconoEscalado = new ImageIcon (image.getScaledInstance(100,100,Image.SCALE_SMOOTH));
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
                if(!_bModificar){
                    _categoria.Delete();
                }    
            } catch (Exception ex) {
                System.out.println("Error en la eliminación de la categoria. "+ ex.toString());
            }
            return true;
        }
        else{
            try {
                if(new Categoria(_categoria.getId()).getId_Imagen() == -1){
                    JOptionPane.showMessageDialog(null,
                            "¡ATENCIÓN! Se debe asignar una imagen a la categoría.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                else{
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Error al salir. "+ex.toString());
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

        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        iconoImagen = new javax.swing.JLabel();
        butSubir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        butAtras = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        lblArticulos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lArticulos = new javax.swing.JList<>();
        butAgregarArt = new javax.swing.JButton();
        butEliminarArt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar categoría");
        setResizable(false);

        lblNombre.setText("Nombre");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
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

        butSubir.setBackground(new java.awt.Color(0, 0, 0));
        butSubir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butSubir.setForeground(new java.awt.Color(255, 255, 255));
        butSubir.setText("Subir imagen");
        butSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubirActionPerformed(evt);
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

        lblArticulos.setText("Artículos");

        lArticulos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lArticulos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lArticulos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lArticulos);

        butAgregarArt.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarArt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarArt.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarArt.setText("Agregar Artículo");
        butAgregarArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarArtActionPerformed(evt);
            }
        });

        butEliminarArt.setBackground(java.awt.Color.red);
        butEliminarArt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarArt.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarArt.setText("Eliminar Artículo");
        butEliminarArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarArtActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("CATEGORÍA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addGap(0, 206, Short.MAX_VALUE))
                            .addComponent(txtNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblArticulos)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butAtras))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butAgregarArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butEliminarArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
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
                        .addComponent(lblArticulos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(butAtras)
                                    .addComponent(butGuardar)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(butAgregarArt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(butEliminarArt))))
                    .addComponent(butSubir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void iconoImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconoImagenMouseClicked
        subir_imagen();
    }//GEN-LAST:event_iconoImagenMouseClicked

    private void butSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubirActionPerformed
        subir_imagen();
    }//GEN-LAST:event_butSubirActionPerformed

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        if(salir()){
            java.awt.EventQueue.invokeLater(() -> {
                Frame frmMarca = null;
                try {
                    frmMarca = new FrmMarca(_categoria.getId_Marca());
                } catch (Exception ex) {
                    System.out.println("Error al leer las marcas. "+ ex.toString());
                }
                if(frmMarca != null){
                    frmMarca.setLocationRelativeTo(FrmCategoria.this);
                    frmMarca.setVisible(true);
                }
            });
            this.dispose();
        }  
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void butAgregarArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarArtActionPerformed
        comprobar_cambios();
        try {
            if(new Categoria(_categoria.getId()).getId_Imagen() == -1){
                JOptionPane.showMessageDialog(null,
                        "¡ATENCIÓN! Se debe asignar una imagen a la categoría.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                
            }
            else{
                java.awt.EventQueue.invokeLater(() -> {
                    Frame frmArticulo = null;
                    try {
                        frmArticulo = new FrmArticulo(null, _categoria.getId());
                    } catch (Exception ex) {
                        System.out.println("Error al buscar el artículo en la base de datos. "+ ex.toString());
                    }
                    if(frmArticulo != null){
                        frmArticulo.setLocationRelativeTo(FrmCategoria.this);
                        frmArticulo.setVisible(true);
                    }
                    this.dispose();
                });
            }
        } catch (Exception ex) {
            System.out.println("Error al salir. "+ex.toString());
        }
    }//GEN-LAST:event_butAgregarArtActionPerformed

    private void butEliminarArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarArtActionPerformed
        int index = lArticulos.getSelectedIndex();
        
        if(index != -1)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Se eliminarán además todos los datos asociados a este artículo."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar artículo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modArticulos.removeArticulo(index);
                } catch (Exception ex) {
                    System.out.println("Error en la eliminación del artículo. "+ ex.toString());
                }
                _bCambios = true;
            }
        }    
    }//GEN-LAST:event_butEliminarArtActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        _bCambios = true;
    }//GEN-LAST:event_txtNombreKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FrmCategoria(null, null, null).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(FrmCategoria.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAgregarArt;
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butEliminarArt;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butSubir;
    private javax.swing.JLabel iconoImagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JList<String> lArticulos;
    private javax.swing.JLabel lblArticulos;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
