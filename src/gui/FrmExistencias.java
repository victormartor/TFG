package gui;

import Data.Clases.Articulo;
import Data.Clases.Color;
import Data.Clases.Stock;
import Data.Clases.Talla;
import Data.Modelos.StockTableModel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Ventana desde la que se puede ver y modificar las existencias de cada
 * Artículo.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Stock
 */
public class FrmExistencias extends javax.swing.JFrame 
{
    private final ArrayList<Stock> _aStock;
    
    /**
     * La variable _bCambios es un booleano que nos avisará si se ha realizado
     * algún cambio y este no ha sido guardado.
     */
    private boolean _bCambios;
    
    /**
     * Crea un nuevo formulario de Existencias (Stock).
     * @param sNombre_Articulo Parámetro necesario si se quiere modificar
     * las existencias de un artículo en concreto.
     * @throws java.sql.SQLException Error al buscar o crear el Stock.
     */
    public FrmExistencias(String sNombre_Articulo) throws SQLException  
    {
        initComponents();
        _aStock = new ArrayList();
        
        /**
         * Obtiene todo el Stock de todos los artículos en la tabla Stock.
         * Si existe algún artículo que no tenga ningún registro en la 
         * tabla Stock crea dicho registro.
         */
        ArrayList<Articulo> aArticulo = Articulo.Select(null, null, null, null);
        for(Articulo articulo : aArticulo)
        {
            for(int Id_Color : articulo.getColores())
            {
                Color color = new Color(Id_Color);
                for(int Id_Talla : articulo.getTallas())
                {
                    Talla talla = new Talla(Id_Talla);
                    
                    ArrayList<Stock> aStock = Stock.Select(articulo.getId(), 
                            Id_Color, Id_Talla, null);
                    if(aStock.isEmpty()){
                        Stock stock = Stock.Create(articulo.getId(), 
                                Id_Color, Id_Talla);
                        _aStock.add(stock);
                    }
                    else{
                        Stock stock = aStock.get(0);
                        _aStock.add(stock);
                    }
                }
            }
        }
        
        //Asignar el modelo a la tabla y sorter para que se pueda ordenar
        tableExistencias.setModel(new StockTableModel(_aStock));
        TableRowSorter<TableModel> sorter = 
                new TableRowSorter<>(tableExistencias.getModel());
        tableExistencias.setRowSorter(sorter);
        
        //Personalizar comportamiento del botón de cerrar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrar();
            }
        });
        
        _bCambios = false;
        //Si se ha proporcionado un nombre de artículo se realiza la búsqueda
        if(sNombre_Articulo != null)
        {
            txtBuscar.setText(sNombre_Articulo);
            buscar();
        }   
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
    
    //Realizar una búsqueda por nombre de artículo y modificar el contenido de 
    //la tabla
    private void buscar()
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
        
        try {
            tableExistencias.setModel(
                    new StockTableModel(Stock.Search(txtBuscar.getText())));
            TableRowSorter<TableModel> sorter = 
                    new TableRowSorter<>(tableExistencias.getModel());
            tableExistencias.setRowSorter(sorter);
            _bCambios = false;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar stock.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //cerrar la ventana preguntando si se quieren guardar los cambios
    private void cerrar()
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
        this.dispose();
    }
    
    //Guardar cambios
    private void guardar()
    {
        try{
            for(int i = 0; i<tableExistencias.getModel().getRowCount();i++)
                ((StockTableModel)tableExistencias.getModel())
                        .getData(i).Update();
            
            _bCambios = false;

            JOptionPane.showMessageDialog(this, 
            "Los cambios se han guardado correctamente.", 
            "Mensaje del sistema", 
            JOptionPane.PLAIN_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
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
        tableExistencias = new javax.swing.JTable();
        butAtras = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Existencias");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(800, 428));

        tableExistencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Artículo", "Color", "Talla", "Existencias"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableExistencias.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableExistenciasPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tableExistencias);

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

        butBuscar.setBackground(new java.awt.Color(0, 0, 0));
        butBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butBuscar.setForeground(new java.awt.Color(255, 255, 255));
        butBuscar.setText("Buscar");
        butBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBuscarActionPerformed(evt);
            }
        });

        txtBuscar.setToolTipText("Nombre de artículo");
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 497, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(butGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(butAtras))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(butBuscar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butAtras)
                    .addComponent(butGuardar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void tableExistenciasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableExistenciasPropertyChange
        _bCambios = true;
    }//GEN-LAST:event_tableExistenciasPropertyChange

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        cerrar();
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_butBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if(evt.getExtendedKeyCode() == KeyEvent.VK_ENTER)
            buscar();
    }//GEN-LAST:event_txtBuscarKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butBuscar;
    private javax.swing.JButton butGuardar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableExistencias;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
