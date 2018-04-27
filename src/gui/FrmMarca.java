/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Categoria;
import Data.Data;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Modelos.ModCategorias;
import Data.Modelos.ModImagenes;
import Data.Modelos.ModMarcas;
import Data.Renders.ListaImagenesRender;
import Data.Renders.ListaRender;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author victor
 */
public class FrmMarca extends javax.swing.JFrame {

    private Marca _marca = null;
    private IfrImagenes _ifrImagenes = null;
    private ModCategorias _modCategorias = null;
    
    private boolean _bModificar = false;
    private boolean _bCambios = false;
    /**
     * Creates new form FrmMarca
     */
    public FrmMarca(Integer iId_Marca) throws Exception {
        initComponents();
        
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
                salir();
                System.exit(0);
            }
        });
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
            System.out.println("Error al guardar. "+ ex.toString());
        }
        if(_ifrImagenes != null) _ifrImagenes.dispose();
    }
    
    private void modificarCategoria(){
        int iIndex = lCategorias.getSelectedIndex();
        Categoria categoria = _modCategorias.getCategoria(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmCategoria = null;
            try {
                frmCategoria = new FrmCategoria(categoria.getId(), _marca.getId());
            } catch (Exception ex) {
                System.out.println("Error al buscar la categoria en la base de datos. "+ ex.toString());
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
            Image image = new ImageIcon(new Imagen(_marca.getId_Imagen()).getRutaCompleta()).getImage();
            ImageIcon iconoEscalado = new ImageIcon (image.getScaledInstance(100,100,Image.SCALE_SMOOTH));
            iconoImagen.setIcon(iconoEscalado);
        }
        else{
            iconoImagen.setText("<html><body>Elige una <br> imagen</body></html>");
        }
    }
    
    private void salir(){
        comprobar_cambios();
        try {
            if(!_bModificar){
                _marca.Delete();
            }    
        } catch (Exception ex) {
            System.out.println("Error en la eliminación de la marca. "+ ex.toString());
        }
        if(_ifrImagenes != null) _ifrImagenes.dispose();
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
        butElegir = new javax.swing.JButton();
        butSubir = new javax.swing.JButton();
        butAgregarCat = new javax.swing.JButton();
        butEliminarCat = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtMarca = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar marca");
        setResizable(false);

        lblNombre.setText("Nombre");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        butAtras.setText("Atrás");
        butAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAtrasActionPerformed(evt);
            }
        });

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

        lCategorias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lCategorias);

        butElegir.setText("Elegir imagen");
        butElegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butElegirActionPerformed(evt);
            }
        });

        butSubir.setText("Subir imagen");
        butSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubirActionPerformed(evt);
            }
        });

        butAgregarCat.setText("Agregar Categoría");
        butAgregarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarCatActionPerformed(evt);
            }
        });

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
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNombre))
                        .addGap(18, 18, 18)
                        .addComponent(iconoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butElegir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(butEliminarCat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(butAgregarCat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butAtras))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategorias)
                            .addComponent(txtMarca))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(iconoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(butElegir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butSubir)))
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
                    .addComponent(butGuardar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        salir();
        java.awt.EventQueue.invokeLater(() -> {
            Frame ifrMarca = null;
            try {
                ifrMarca = new IfrMarca();
            } catch (Exception ex) {
                System.out.println("Error al leer las marcas. "+ ex.toString());
            }
            if(ifrMarca != null){
                ifrMarca.setLocationRelativeTo(FrmMarca.this);
                ifrMarca.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void iconoImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconoImagenMouseClicked

    }//GEN-LAST:event_iconoImagenMouseClicked

    private void butSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubirActionPerformed
        JFileChooser archivo = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg","jpeg");
        archivo.addChoosableFileFilter(filtro);
        archivo.setFileFilter(filtro);
        archivo.setDialogTitle("Abrir Imagen");
    
        File ruta = null;
        try {
            ruta = new File(Data.RutaImagenes());
        } catch (IOException ex) {
            System.out.println("Error en la lectura de la ruta. "+ ex.toString());
        }
         archivo.setCurrentDirectory(ruta);

        int ventana = archivo.showOpenDialog(null);
        if(ventana == JFileChooser.APPROVE_OPTION)
        {
            File file = archivo.getSelectedFile();
            String sRuta = null;
            Imagen imagen = null;
            try {
                sRuta = file.getPath().replace(Data.RutaImagenes(), "");
                sRuta = sRuta.replace(file.getName(), "");
                if(Imagen.Select(file.getName(), null).size() > 0)
                    imagen = Imagen.Select(file.getName(), null).get(0);
                else
                    imagen = Imagen.Create(file.getName(), sRuta);
            } catch (Exception ex) {
                System.out.println("Error en la creación de la imagen. "+ ex.toString());
            }
            if(imagen != null){
                _marca.setId_Imagen(imagen.getId());
                try {
                    //_marca.Update();
                    cargarImagen();
                } catch (Exception ex) {
                    System.out.println("Error en la actualización de la imagen de la marca. "+ ex.toString());
                }
            }
        }
        
        _bCambios = true;
    }//GEN-LAST:event_butSubirActionPerformed

    private void butElegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butElegirActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            if(_ifrImagenes == null || !_ifrImagenes.bAbierto){
                try {
                    _ifrImagenes = new IfrImagenes(iconoImagen, _marca, null);
                } catch (Exception ex) {
                    System.out.println("Error al leer la lista de imágenes. "+ ex.toString());
                }
            }
            
            _ifrImagenes.setLocationRelativeTo(FrmMarca.this);
            _ifrImagenes.setBounds(this.getX()+this.getWidth()-10, 
                    this.getY()+30, _ifrImagenes.getWidth(), _ifrImagenes.getHeight());
            _ifrImagenes.setVisible(true);
        });
        
        _bCambios = true;
    }//GEN-LAST:event_butElegirActionPerformed

    private void butAgregarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarCatActionPerformed
        comprobar_cambios();
        
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmCategoria = null;
            try {
                frmCategoria = new FrmCategoria(null, _marca.getId());
            } catch (Exception ex) {
                System.out.println("Error al crear una categoria vacía en la base de datos. "+ ex.toString());
            }
            if(frmCategoria != null){
                frmCategoria.setLocationRelativeTo(FrmMarca.this);
                frmCategoria.setVisible(true);
            }
            this.dispose();
        });
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
                    System.out.println("Error en la eliminación de la categoria. "+ ex.toString());
                }
                
                _bCambios = true;
            }
        }
    }//GEN-LAST:event_butEliminarCatActionPerformed

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
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(() -> {
            
            FrmMarca frmMarca = null;
            try {
                frmMarca = new FrmMarca();
            } catch (Exception ex) {
                Logger.getLogger(FrmMarca.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(frmMarca != null){
                frmMarca.setLocationRelativeTo(null);
                frmMarca.setVisible(true);
            }
        });
        */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAgregarCat;
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butElegir;
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
