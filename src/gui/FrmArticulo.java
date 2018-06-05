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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class FrmArticulo extends javax.swing.JFrame {
    
    private Articulo _articulo = null;
    private ModArticulo_Color _modArticulo_Color = null;
    private ModArticulos _modArticulosComb = null;
    private boolean _bModificar = false;
    private boolean _bCambios;

    /**
     * Creates new form FrmArticulo
     */
    public FrmArticulo(Integer iId_Articulo, Integer iId_Categoria) throws Exception {
        initComponents();
        
        if(iId_Articulo != null){
            _bModificar = true;
            _articulo = new Articulo(iId_Articulo);
            txtNombre.setText(_articulo.getNombre());
            txtPVP.setText(String.format("%.2f", _articulo.getPVP()));
            checkEs_Numero.setSelected(_articulo.getTalla_Es_Numero());
        }
        else{
            _articulo = Articulo.Create("", 0, iId_Categoria, false, null, null, null);
        }

        jScrollPane3.setVisible(false);
        txtTodos.setVisible(false);
        
        //TALLAS
        ArrayList<Talla> aTallas = Talla.Select(null, checkEs_Numero.isSelected());
        ArrayList<Integer> aTallasMarcadas = new ArrayList<>();
        aTallasMarcadas = _articulo.getTallas();
        for(Talla t : aTallas) {
            boolean bMarcada = false;
            for(Integer i : aTallasMarcadas)
                if(t.getId() == i)
                    bMarcada = true;
            
            JCheckBox checkbox_talla = new JCheckBox(t.toString());
            checkbox_talla.setSelected(bMarcada);
            checkbox_talla.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    _bCambios = true;
                }
            });
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
        
        try {
            _modArticulosComb = new ModArticulos(null,null);

        } catch (Exception ex) {
            System.out.println("Error al crear la lista de artículos para las combinaciones. "+ex.toString());
        }
        _modArticulosComb.removeCombinacion(_modArticulosComb.getIndexOf(_articulo));
        ArrayList<Integer> aComb = _articulo.getCombinaciones();
        for(Integer iId_Comb : aComb)
            _modArticulosComb.removeCombinacion(_modArticulosComb.getIndexOf(new Articulo(iId_Comb)));
        
        lArticulosCombinaciones.setModel(_modArticulosComb);
        lArticulosCombinaciones.setCellRenderer(new ListaRender());
        lArticulosCombinaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                   agregarComb();
                }
            }
        });
        
        if(_bModificar)
            this.setTitle("Modificar artículo");
        
        txtNombre.requestFocus();
        _bCambios = false;
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if(salir()) FrmArticulo.this.dispose();
            }
        });
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
    
    private void guardar(){
        try {
            _articulo.setNombre(txtNombre.getText());
            _articulo.setPVP(Data.String2Double(txtPVP.getText()));
            _articulo.setTalla_Es_Numero(checkEs_Numero.isSelected());
            _articulo.setTallas(getTallasMarcadas());
            _articulo.setColores(_modArticulo_Color.getColores());
            _articulo.Update();
            
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
    
    private void agregarComb(){
        int index = lArticulosCombinaciones.getSelectedIndex();
        if(index != -1){
            ModArticulos modArticulosCombinaciones = (ModArticulos)lArticulosCombinaciones.getModel();
            Articulo articulo = modArticulosCombinaciones.getArticulo(index);
            ((ModArticulos)lCombinaciones.getModel()).addArticulo(articulo);

            ArrayList<Integer> aComb = _articulo.getCombinaciones();
            aComb.add(articulo.getId());
            _articulo.setCombinaciones(aComb);

            ((ModArticulos)lArticulosCombinaciones.getModel()).removeCombinacion(lArticulosCombinaciones.getSelectedIndex());
            _bCambios = true;
        }
    }
    
    private void modificarArticuloColor(){
        int iIndex = lColores.getSelectedIndex();
        Color color = _modArticulo_Color.getColor(iIndex);

        java.awt.EventQueue.invokeLater(() -> {
            Frame frmArticuloColor = null;
            try {
                frmArticuloColor = new FrmArticuloColor(_articulo.getId(), color.getId());
            } catch (Exception ex) {
                System.out.println("Error al buscar el color en la base de datos. "+ ex.toString());
            }
            if(frmArticuloColor != null){
                frmArticuloColor.setLocationRelativeTo(FrmArticulo.this);
                frmArticuloColor.setVisible(true);
            }
            this.dispose();
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
    
    private boolean salir(){
        comprobar_cambios();
        if(!_bModificar){
            try {
                _articulo.Delete();
            } catch (Exception ex) {
                System.out.println("Error en la eliminación del artículo. "+ ex.toString());
            }
            return true;
        }
        else{
            if(_modArticulo_Color.getColores().isEmpty()){
                JOptionPane.showMessageDialog(null,
                "¡ATENCIÓN! Se debe asignar al menos un color al artículo.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
                return false;
            }
            else
                return true;
        }   
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
        butAtras = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtColores = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTallas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lColores = new javax.swing.JList<>();
        butAgregarColor = new javax.swing.JButton();
        butEliminarColor = new javax.swing.JButton();
        butAgregarCombinacion = new javax.swing.JButton();
        butEliminarCombinacion = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        lCombinaciones = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lArticulosCombinaciones = new javax.swing.JList<>();
        txtTodos = new javax.swing.JLabel();
        lblArticulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar artículo");
        setIconImage(getIconImage());
        setResizable(false);

        lblNombre.setText("Nombre");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        lblPVP.setText("PVP:");

        txtPVP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPVP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPVP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPVPKeyTyped(evt);
            }
        });

        lblEuro.setText("€");

        lblTallas.setText("Tallas");

        checkEs_Numero.setText("usar número para la talla");
        checkEs_Numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEs_NumeroActionPerformed(evt);
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

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtColores.setText("Colores");

        panelTallas.setLayout(new java.awt.GridLayout(4, 0));
        jScrollPane1.setViewportView(panelTallas);

        lColores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lColores.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lColores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lColores.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(lColores);

        butAgregarColor.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarColor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarColor.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarColor.setText("Agregar color");
        butAgregarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarColorActionPerformed(evt);
            }
        });

        butEliminarColor.setBackground(java.awt.Color.red);
        butEliminarColor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarColor.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarColor.setText("Eliminar color");
        butEliminarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarColorActionPerformed(evt);
            }
        });

        butAgregarCombinacion.setBackground(new java.awt.Color(0, 0, 0));
        butAgregarCombinacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAgregarCombinacion.setForeground(new java.awt.Color(255, 255, 255));
        butAgregarCombinacion.setText("Añadir");
        butAgregarCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAgregarCombinacionActionPerformed(evt);
            }
        });

        butEliminarCombinacion.setBackground(java.awt.Color.red);
        butEliminarCombinacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminarCombinacion.setForeground(new java.awt.Color(255, 255, 255));
        butEliminarCombinacion.setText("Eliminar");
        butEliminarCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliminarCombinacionActionPerformed(evt);
            }
        });

        lCombinaciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lCombinaciones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lCombinaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(lCombinaciones);

        lArticulosCombinaciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lArticulosCombinaciones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lArticulosCombinaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lArticulosCombinaciones.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane3.setViewportView(lArticulosCombinaciones);

        txtTodos.setText("Todos los artículos");

        lblArticulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblArticulo.setText("ARTÍCULO");

        jLabel1.setText("Artículos relacionados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(butGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butAtras)
                        .addGap(34, 34, 34))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblArticulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNombre)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblPVP)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtPVP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEuro, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblTallas)
                                        .addGap(18, 18, 18)
                                        .addComponent(checkEs_Numero))
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(butAgregarColor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(butEliminarColor))
                                    .addComponent(txtColores)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTodos))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(butAgregarCombinacion, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(butEliminarCombinacion, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblArticulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4)
                                .addGap(54, 54, 54))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(butAgregarCombinacion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(butEliminarCombinacion))
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
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtColores)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(butEliminarColor)
                                            .addComponent(butAgregarColor)))
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butAtras)
                            .addComponent(butGuardar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTodos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkEs_NumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEs_NumeroActionPerformed
        try {
            ArrayList<Talla> aTallas = Talla.Select(null, checkEs_Numero.isSelected());
            panelTallas.removeAll();
            for(Talla t : aTallas){
                panelTallas.add(new JCheckBox(t.toString()));
            }
            
            panelTallas.updateUI();
        } catch (Exception ex) {
            System.out.println("Error al buscar las tallas. "+ex.toString());
        }
        _bCambios = true;
    }//GEN-LAST:event_checkEs_NumeroActionPerformed

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        if(salir()){
            java.awt.EventQueue.invokeLater(() -> {
                Frame frmCategoria = null;
                try {
                    frmCategoria = new FrmCategoria(_articulo.getId_Categoria(), null);
                } catch (Exception ex) {
                    System.out.println("Error al leer las marcas. "+ ex.toString());
                }
                if(frmCategoria != null){
                    frmCategoria.setLocationRelativeTo(FrmArticulo.this);
                    frmCategoria.setVisible(true);
                }
            });
            this.dispose();
        }  
    }//GEN-LAST:event_butAtrasActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_butGuardarActionPerformed

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
        comprobar_cambios();
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmArticuloColor = null;
            try {
                frmArticuloColor = new FrmArticuloColor(_articulo.getId(), null);
            } catch (Exception ex) {
                System.out.println("Error al buscar el artículo en la base de datos. "+ ex.toString());
            }
            if(frmArticuloColor != null){
                frmArticuloColor.setLocationRelativeTo(FrmArticulo.this);
                frmArticuloColor.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_butAgregarColorActionPerformed

    private void butAgregarCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAgregarCombinacionActionPerformed
        if(!jScrollPane3.isVisible())
        {         
            this.setSize(this.getWidth()+200, this.getHeight());   
            jScrollPane3.setVisible(true);
            txtTodos.setVisible(true); 
        }
        else{
            agregarComb();
        }
    }//GEN-LAST:event_butAgregarCombinacionActionPerformed

    private void butEliminarCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliminarCombinacionActionPerformed
        int index = lCombinaciones.getSelectedIndex();
        if(index != -1){
            ArrayList<Integer> aCombinaciones = _articulo.getCombinaciones();
            Articulo articulo = ((ModArticulos)lCombinaciones.getModel()).getArticulo(index);
            aCombinaciones.remove((Integer)articulo.getId());
            _articulo.setCombinaciones(aCombinaciones);

            ((ModArticulos)lCombinaciones.getModel()).removeCombinacion(lCombinaciones.getSelectedIndex());
            _modArticulosComb.addArticulo(articulo);
            _bCambios = true;
        }
    }//GEN-LAST:event_butEliminarCombinacionActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        _bCambios = true;
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtPVPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPVPKeyTyped
        _bCambios = true;
    }//GEN-LAST:event_txtPVPKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAgregarColor;
    private javax.swing.JButton butAgregarCombinacion;
    private javax.swing.JButton butAtras;
    private javax.swing.JButton butEliminarColor;
    private javax.swing.JButton butEliminarCombinacion;
    private javax.swing.JButton butGuardar;
    private javax.swing.JCheckBox checkEs_Numero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList<String> lArticulosCombinaciones;
    private javax.swing.JList<String> lColores;
    private javax.swing.JList<String> lCombinaciones;
    private javax.swing.JLabel lblArticulo;
    private javax.swing.JLabel lblEuro;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPVP;
    private javax.swing.JLabel lblTallas;
    private javax.swing.JPanel panelTallas;
    private javax.swing.JLabel txtColores;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPVP;
    private javax.swing.JLabel txtTodos;
    // End of variables declaration//GEN-END:variables
}
