/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Configuracion;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author victor
 */
public class FrmConfig extends javax.swing.JFrame {

    private ArrayList<Configuracion> _aConfig = null;
    private JLabel _lblNombreTienda;
    
    /**
     * Creates new form FrmConfig
     */
    public FrmConfig(JLabel lblNombreTienda) {
        initComponents();
        
        _lblNombreTienda = lblNombreTienda;
        
        try {
            _aConfig = Configuracion.Select(null, null);
        } catch (Exception ex) {
            System.out.println("Error al leer la configuración. "+ex.toString());
        }
        
        txtNombreTienda.setText(_aConfig.get(0).getValor());
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombreTienda = new javax.swing.JLabel();
        txtNombreTienda = new javax.swing.JTextField();
        butGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuración");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(600, 120));

        lblNombreTienda.setText("Nombre de la tienda:");

        butGuardar.setBackground(java.awt.Color.green);
        butGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butGuardar.setText("Guardar y salir");
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(butGuardar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreTienda)
                        .addGap(19, 19, 19)
                        .addComponent(txtNombreTienda, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreTienda)
                    .addComponent(txtNombreTienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(butGuardar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        _lblNombreTienda.setText(txtNombreTienda.getText());
        _aConfig.get(0).setValor(txtNombreTienda.getText());
        
        try{
            for(Configuracion c : _aConfig){
                c.Update();
            }
        }catch(Exception ex){
            System.out.println("Error al guardar la configuración. "+ex.toString());
        }
        this.dispose();
    }//GEN-LAST:event_butGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butGuardar;
    private javax.swing.JLabel lblNombreTienda;
    private javax.swing.JTextField txtNombreTienda;
    // End of variables declaration//GEN-END:variables
}
