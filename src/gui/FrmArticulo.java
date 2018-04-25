/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Imagen;
import Data.Clases.Talla;
import Data.Data;
import Data.Modelos.ModArticulo_Color;
import Data.Modelos.ModArticulos;
import Data.Renders.ListaRender;
import java.awt.Frame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class FrmArticulo extends javax.swing.JFrame {
    
    private Articulo _articulo = null;
    private ArrayList<JCheckBox> _aCheckBoxTallas = null;
    private ModArticulos _modArticulos = null;
    private ModArticulo_Color _modArticulo_Color = null;
    private boolean _bModificar = false;

    /**
     * Creates new form FrmArticulo
     */
    public FrmArticulo(Articulo articulo, ModArticulos modArticulos, Categoria categoria) throws Exception {
        initComponents();
        
        if(articulo != null){
            _bModificar = true;
            _articulo = articulo;
            txtNombre.setText(_articulo.getNombre());
            txtPVP.setText(String.format("%.2f", _articulo.getPVP()));
            checkEs_Numero.setSelected(articulo.getTalla_Es_Numero());
        }
        else{
            _articulo = Articulo.Create("", 0, categoria.getId(), false, null, null, null);
        }
        
        _modArticulos = modArticulos;
        
        
        //TALLAS
        _aCheckBoxTallas = new ArrayList<>();
        ArrayList<Talla> aTallas = Talla.Select(null, checkEs_Numero.isSelected());
        ArrayList<Integer> aTallasMarcadas = new ArrayList<>();
        if(articulo != null) aTallasMarcadas = articulo.getTallas();
        for(Talla t : aTallas) {
            boolean bMarcada = false;
            for(Integer i : aTallasMarcadas)
                if(t.getId() == i)
                    bMarcada = true;
            
            JCheckBox checkbox_talla = new JCheckBox(t.toString());
            checkbox_talla.setSelected(bMarcada);
            panelTallas.add(checkbox_talla);
        }
        
        panelTallas.updateUI();
        
        //COLORES
        _modArticulo_Color = new ModArticulo_Color(_articulo.getId());
        lColores.setModel(_modArticulo_Color);
        
        lColores.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                   modificarArticuloColor();
                }
           }
        });
        
        //COMBINACIONES
        lCombinaciones.setModel(new ModArticulos(null, _articulo.getId()));
        lCombinaciones.setCellRenderer(new ListaRender());
        /*
        for(Integer id : _articulo.getCombinaciones()) {
            JLabel id_comb = new JLabel(id.toString());
            //panelCombinaciones.add(id_comb);
        }
        */
        
        if(_bModificar)
            this.setTitle("Modificar artículo");
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cancelar();
            }
        });
    }
    
    private void modificarArticuloColor(){
        int iIndex = lColores.getSelectedIndex();
        Color color = _modArticulo_Color.getColor(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmArticuloColor = null;
            try {
                frmArticuloColor = new FrmArticuloColor(_articulo, color, _modArticulo_Color);
            } catch (Exception ex) {
                System.out.println("Error al buscar el color en la base de datos. "+ ex.toString());
            }
            if(frmArticuloColor != null){
                frmArticuloColor.setLocationRelativeTo(FrmArticulo.this);
                frmArticuloColor.setVisible(true);
            }
        });
    }
    
    private void cancelar(){
        try {
            if(!_bModificar)
                _articulo.Delete();

        } catch (Exception ex) {
            System.out.println("Error en la eliminación de la categoria. "+ ex.toString());
        }
        //if(_ifrImagenes != null) _ifrImagenes.dispose();
        this.dispose();
    }
    
    private ArrayList<Integer> getTallasMarcadas() throws Exception{
        ArrayList<Talla> aTallas = Talla.Select(null, checkEs_Numero.isSelected());
        ArrayList<Integer> aTallasMarcadas = new ArrayList<>();
        
        for(int i = 0; i < aTallas.size(); i++){
            JCheckBox checkbox_talla = (JCheckBox)panelTallas.getComponent(i);
            if(checkbox_talla.isSelected()){
                int n = 0;
                while(!checkbox_talla.getText().equals(aTallas.get(n).getNombre())) n++;
                aTallasMarcadas.add(aTallas.get(n).getId());
            }
        }
        
        return aTallasMarcadas;
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
        lblPVP = new javax.swing.JLabel();
        txtPVP = new javax.swing.JTextField();
        lblEuro = new javax.swing.JLabel();
        lblTallas = new javax.swing.JLabel();
        checkEs_Numero = new javax.swing.JCheckBox();
        butCancelar = new javax.swing.JButton();
        butAceptar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtColores = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTallas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lColores = new javax.swing.JList<>();
        butAgregarColor = new javax.swing.JButton();
        butEliminarColor = new javax.swing.JButton();
        lblCombinaciones = new javax.swing.JLabel();
        butAgregarCombinacion = new javax.swing.JButton();
        butEliminarCombinacion = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        lCombinaciones = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar artículo");
        setResizable(false);

        lblNombre.setText("Nombre");

        lblPVP.setText("PVP:");

        txtPVP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPVP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        lblEuro.setText("€");

        lblTallas.setText("Tallas");

        checkEs_Numero.setText("usar número para la talla");
        checkEs_Numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEs_NumeroActionPerformed(evt);
            }
        });

        butCancelar.setText("Cancelar");
        butCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCancelarActionPerformed(evt);
            }
        });

        butAceptar.setText("Aceptar");
        butAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceptarActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtColores.setText("Colores");

        panelTallas.setLayout(new java.awt.GridLayout(4, 0));
        jScrollPane1.setViewportView(panelTallas);

        lColores.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lColores);

        butAgregarColor.setText("Agregar color");
        butAgregarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarColorActionPerformed(evt);
            }
        });

        butEliminarColor.setText("Eliminar color");
        butEliminarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarColorActionPerformed(evt);
            }
        });

        lblCombinaciones.setText("Artículos relacionados");

        butAgregarCombinacion.setText("Añadir");
        butAgregarCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarCombinacionActionPerformed(evt);
            }
        });

        butEliminarCombinacion.setText("Eliminar");
        butEliminarCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarCombinacionActionPerformed(evt);
            }
        });

        lCombinaciones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lCombinaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNombre)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblPVP)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtPVP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblEuro, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblTallas)
                                    .addGap(18, 18, 18)
                                    .addComponent(checkEs_Numero))
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtColores)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(butAgregarColor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(butEliminarColor)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCombinaciones)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(butAgregarCombinacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(butEliminarCombinacion, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPVP)
                            .addComponent(txtPVP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEuro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTallas)
                            .addComponent(checkEs_Numero))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtColores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butAgregarColor)
                            .addComponent(butEliminarColor))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCombinaciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(butAgregarCombinacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(butEliminarCombinacion)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butCancelar)
                    .addComponent(butAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkEs_NumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEs_NumeroActionPerformed
        try {
            _aCheckBoxTallas = new ArrayList<>();
            ArrayList<Talla> aTallas = Talla.Select(null, checkEs_Numero.isSelected());
            panelTallas.removeAll();
            for(Talla t : aTallas){
                panelTallas.add(new JCheckBox(t.toString()));
            }
            
            panelTallas.updateUI();
        } catch (Exception ex) {
            System.out.println("Error al buscar las tallas. "+ex.toString());
        }
    }//GEN-LAST:event_checkEs_NumeroActionPerformed

    private void butCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_butCancelarActionPerformed

    private void butAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceptarActionPerformed
        try {
            _articulo.setNombre(txtNombre.getText());
            _articulo.setPVP(Data.String2Double(txtPVP.getText()));
            _articulo.setTalla_Es_Numero(checkEs_Numero.isSelected());
            _articulo.setTallas(getTallasMarcadas());
            _articulo.setColores(_modArticulo_Color.getColores());
            _articulo.Update();
            if(!_bModificar) _modArticulos.addArticulo(_articulo);
        } catch (Exception ex) {
            System.out.println("Error en la creación o modificación del artículo. "+ ex.toString());
        }
        
        this.dispose();
    }//GEN-LAST:event_butAceptarActionPerformed

    private void butEliminarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarColorActionPerformed
        int index = lColores.getSelectedIndex();
        
        if(index != -1)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Se eliminarán además todos los datos y las imagenes de este"
                        + " artículo asociados a este color."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar color",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modArticulo_Color.removeColor(index);
                } catch (Exception ex) {
                    System.out.println("Error en la eliminación del color. "+ ex.toString());
                }
            }
        }
    }//GEN-LAST:event_butEliminarColorActionPerformed

    private void butAgregarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarColorActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmArticuloColor = null;
            try {
                frmArticuloColor = new FrmArticuloColor(_articulo, null, _modArticulo_Color);
            } catch (Exception ex) {
                System.out.println("Error al buscar el artículo en la base de datos. "+ ex.toString());
            }
            if(frmArticuloColor != null){
                frmArticuloColor.setLocationRelativeTo(FrmArticulo.this);
                frmArticuloColor.setVisible(true);
            }
        });
    }//GEN-LAST:event_butAgregarColorActionPerformed

    private void butAgregarCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarCombinacionActionPerformed
        
    }//GEN-LAST:event_butAgregarCombinacionActionPerformed

    private void butEliminarCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarCombinacionActionPerformed
        ArrayList<Integer> aCombinaciones = _articulo.getCombinaciones();
        aCombinaciones.remove((Integer)
                ((ModArticulos)lCombinaciones.getModel()).getArticulo(lCombinaciones.getSelectedIndex()).getId());
        _articulo.setCombinaciones(aCombinaciones);
        
        ((ModArticulos)lCombinaciones.getModel()).RemoveCombinacion(lCombinaciones.getSelectedIndex());
    }//GEN-LAST:event_butEliminarCombinacionActionPerformed

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
            java.util.logging.Logger.getLogger(FrmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmArticulo().setVisible(true);
            }
        });
        */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAceptar;
    private javax.swing.JButton butAgregarColor;
    private javax.swing.JButton butAgregarCombinacion;
    private javax.swing.JButton butCancelar;
    private javax.swing.JButton butEliminarColor;
    private javax.swing.JButton butEliminarCombinacion;
    private javax.swing.JCheckBox checkEs_Numero;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JList<String> lColores;
    private javax.swing.JList<String> lCombinaciones;
    private javax.swing.JLabel lblCombinaciones;
    private javax.swing.JLabel lblEuro;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPVP;
    private javax.swing.JLabel lblTallas;
    private javax.swing.JPanel panelTallas;
    private javax.swing.JLabel txtColores;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPVP;
    // End of variables declaration//GEN-END:variables
}
