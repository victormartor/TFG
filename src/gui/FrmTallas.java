/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Color;
import Data.Clases.Talla;
import Data.Modelos.TallaListModel;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class FrmTallas extends javax.swing.JFrame {

    private TallaListModel _modLetras = null;
    private TallaListModel _modNumeros = null;
    
    /**
     * Creates new form FrmTallas
     */
    public FrmTallas() {
        initComponents();
        
        try {
            _modLetras = new TallaListModel(Talla.Select(null, false));
            _modNumeros = new TallaListModel(Talla.Select(null, true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener las tallas.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        lTallasLetra.setModel(_modLetras);
        lTallasNumero.setModel(_modNumeros);
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
    
    private boolean isNumeric(String cadena){
        try {
                Integer.parseInt(cadena);
                return true;
        } catch (NumberFormatException nfe){
                return false;
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lTallasLetra = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lTallasNumero = new javax.swing.JList<>();
        butEliminarLetra = new javax.swing.JButton();
        butEliminarNumero = new javax.swing.JButton();
        butAgregarLetra = new javax.swing.JButton();
        butAgregarNumero = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tallas");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(352, 295));

        jLabel1.setText("Tallas letra");

        lTallasLetra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lTallasLetra.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lTallasLetra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lTallasLetra.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(lTallasLetra);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setText("Tallas Número");

        lTallasNumero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lTallasNumero.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lTallasNumero.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lTallasNumero.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(lTallasNumero);

        butEliminarLetra.setBackground(java.awt.Color.red);
        butEliminarLetra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarLetra.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarLetra.setText("Eliminar");
        butEliminarLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarLetraActionPerformed(evt);
            }
        });

        butEliminarNumero.setBackground(java.awt.Color.red);
        butEliminarNumero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarNumero.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarNumero.setText("Eliminar");
        butEliminarNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarNumeroActionPerformed(evt);
            }
        });

        butAgregarLetra.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarLetra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarLetra.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarLetra.setText("Agregar");
        butAgregarLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarLetraActionPerformed(evt);
            }
        });

        butAgregarNumero.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarNumero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarNumero.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarNumero.setText("Agregar");
        butAgregarNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarNumeroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(butAgregarLetra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butEliminarLetra)))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(butAgregarNumero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butEliminarNumero))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butEliminarNumero)
                            .addComponent(butAgregarNumero)
                            .addComponent(butAgregarLetra)
                            .addComponent(butEliminarLetra)))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butAgregarLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarLetraActionPerformed
        String sTalla = (String)JOptionPane.showInputDialog(
            this,
            "Talla",
            "Nueva talla",
            JOptionPane.PLAIN_MESSAGE);
        
        if(sTalla != null){
            if(!isNumeric(sTalla)){
                try {
                    _modLetras.addTalla(Talla.Create(sTalla, false));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al crear la talla.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this,
                    "La talla no debe ser numérica.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_butAgregarLetraActionPerformed

    private void butEliminarLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarLetraActionPerformed
        if(lTallasLetra.getSelectedIndex() != -1){
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Todos los artículos que tengan esta talla la perderán."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar talla",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modLetras.removeTalla(lTallasLetra.getSelectedIndex());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al eliminar la talla.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_butEliminarLetraActionPerformed

    private void butAgregarNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarNumeroActionPerformed
        String sTalla = (String)JOptionPane.showInputDialog(
            this,
            "Talla",
            "Nueva talla",
            JOptionPane.PLAIN_MESSAGE);
        
        if(sTalla != null){
            if(isNumeric(sTalla)){
                try {
                    Talla.Create(sTalla, true);
                    _modNumeros = new TallaListModel(Talla.Select(null, true));
                    lTallasNumero.setModel(_modNumeros);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al crear la talla.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this,
                    "La talla debe ser numérica.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_butAgregarNumeroActionPerformed

    private void butEliminarNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarNumeroActionPerformed
        if(lTallasNumero.getSelectedIndex() != -1){
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Todos los artículos que tengan esta talla la perderán."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar talla",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modNumeros.removeTalla(lTallasNumero.getSelectedIndex());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                    "Error al eliminar la talla.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_butEliminarNumeroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAgregarLetra;
    private javax.swing.JButton butAgregarNumero;
    private javax.swing.JButton butEliminarLetra;
    private javax.swing.JButton butEliminarNumero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> lTallasLetra;
    private javax.swing.JList<String> lTallasNumero;
    // End of variables declaration//GEN-END:variables
}
