package gui;

import Data.Clases.Marca;
import Data.Renders.ListaRender;
import Data.Modelos.ModMarcas;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Ventana desde donde se pueden ver las marcas, eliminarlas o modificarlas.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Marca
 * @see ModMarcas
 */
public class IfrMarca extends javax.swing.JFrame 
{
    private static ModMarcas _modMarcas;
    
    /**
     * Crea una nueva interfaz de Marcas. 
     * @throws java.sql.SQLException Error al buscar las marcas en la base
     * de datos.
     */
    public IfrMarca() throws SQLException  
    {
        initComponents();
        
        //Buscar las marcas en la base de datos
        _modMarcas = new ModMarcas();
        listMarcas.setModel(_modMarcas);
        listMarcas.setCellRenderer(new ListaRender());
        
        //Al hacer doble click en una marca se habre el formulariod de Marca
        listMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                   modificarMarca();
                }
           }
        });
    }
    
    /**
     * Personalizar el icono de la ventana
     * @return Devuelve el icono personalizado.
     */
    @Override
    public Image getIconImage() 
    {
       return Toolkit.getDefaultToolkit()
               .getImage(ClassLoader.getSystemResource("img/boton_48.png"));
    }
    
    //MÉTODOS PRIVADOS//////////////////////////////////////////////////////////
    
    //modificar marca existente
    private void modificarMarca()
    {
        int iIndex = listMarcas.getSelectedIndex();
        Marca marca = (Marca)_modMarcas.getElementAt(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmMarca = null;
            try {
                frmMarca = new FrmMarca(marca.getId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                "Error al buscar la marca.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(frmMarca != null){
                frmMarca.setTitle("Modificar marca");
                frmMarca.setLocationRelativeTo(IfrMarca.this);
                frmMarca.setVisible(true);
            }
            this.dispose();
        });
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

        jScrollPane1 = new javax.swing.JScrollPane();
        listMarcas = new javax.swing.JList<>();
        butAddMarca = new javax.swing.JButton();
        butRemoveMarca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Marcas");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(669, 361));
        setResizable(false);

        listMarcas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        listMarcas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listMarcas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listMarcas);

        butAddMarca.setBackground(new java.awt.Color(0, 0, 0));
        butAddMarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAddMarca.setForeground(new java.awt.Color(255, 255, 255));
        butAddMarca.setText("Agregar marca");
        butAddMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAddMarcaActionPerformed(evt);
            }
        });

        butRemoveMarca.setBackground(java.awt.Color.red);
        butRemoveMarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butRemoveMarca.setForeground(new java.awt.Color(255, 255, 255));
        butRemoveMarca.setText("Eliminar marca");
        butRemoveMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRemoveMarcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(butAddMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(butRemoveMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(butAddMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(butRemoveMarca)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butRemoveMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRemoveMarcaActionPerformed
        int index = listMarcas.getSelectedIndex();
        
        if(index != -1)
        {
            Object[] options = {"Sí",
                                "No"};
            int n = JOptionPane.showOptionDialog(this,
                "¿Está seguro? Se eliminarán además todos los artículos de esta marca."
                        + "\n Esta acción no se puede deshacer.",
                "Eliminar marca",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if(n == 0)
            {
                try {
                    _modMarcas.removeMarca(index);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                    "Error al eliminar la marca.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_butRemoveMarcaActionPerformed

    private void butAddMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAddMarcaActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmMarca = null;
            try {
                frmMarca = new FrmMarca(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                "Error al crear una marca vacía.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(frmMarca != null){
                frmMarca.setLocationRelativeTo(IfrMarca.this);
                frmMarca.setVisible(true);
            }
            this.dispose();
        });  
    }//GEN-LAST:event_butAddMarcaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAddMarca;
    private javax.swing.JButton butRemoveMarca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listMarcas;
    // End of variables declaration//GEN-END:variables
}
