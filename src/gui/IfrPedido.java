/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Articulo;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Marca;
import Data.Clases.PedidoPendiente;
import Data.Clases.Talla;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author victor
 */
public class IfrPedido extends javax.swing.JFrame {

    private PedidoPendiente _pedidoP;
    /**
     * Creates new form IfrPedido
     */
    public IfrPedido(PedidoPendiente pedidoP) {
        initComponents();
        getContentPane().setBackground(java.awt.Color.white);
        
        _pedidoP = pedidoP;
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrar();
            }
        });
        
        setTitle(getTitle()+_pedidoP.getNumPedido());
        lblPedido.setText(lblPedido.getText()+_pedidoP.getNumPedido());
        
        for(PedidoPendiente.Articulo_Color_Talla art_col_tal : _pedidoP.getTicketPedido()){
            
            Articulo articulo = null;
            Marca marca = null;
            Color color= null;
            Talla talla = null;
            
            try {
                articulo = new Articulo(art_col_tal.getId_Articulo());
                marca = new Marca(new Categoria(articulo.getId_Categoria()).getId_Marca());
                color = new Color(art_col_tal.getId_Color());
                talla = new Talla(art_col_tal.getId_Talla());
            } catch (Exception ex) {
                System.out.println("Error al obtener el artículo, el color y la talla. "+ex.toString());
            }
            
            if(articulo != null && marca != null && color != null && talla != null){
                String nombre = null;
                if(articulo.getNombre().length()>30) nombre = articulo.getNombre().substring(0, 30)+"...";
                else nombre = articulo.getNombre();
                JLabel pedido = new JLabel();
                pedido.setText("<html>"+nombre+" <br> "
                        + "Marca: "+marca.getNombre()+" <br> "
                        + "Color: "+color.getNombre()+" <br> "
                        + "Talla: "+talla.getNombre()+"</html>");
                pedido.setVerticalAlignment(SwingConstants.TOP);
                pedido.setFont(panelTicket.getFont());
                pedido.setSize(10, 10);
                panelTicket.add(pedido);
                
                JLabel precio = new JLabel();
                precio.setText(articulo.getPVP()+" €");
                precio.setHorizontalAlignment(SwingConstants.RIGHT);
                precio.setVerticalAlignment(SwingConstants.TOP);
                precio.setFont(panelTicket.getFont());
                panelTicket.add(precio);
            }
        }
        
        lblNumArt.setText(lblNumArt.getText()+_pedidoP.getNumArticulos());
        lblTotal.setText(lblTotal.getText()+String.format("%.2f", _pedidoP.getTotal())+" €");
        
        setDireccion();
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
    
    private void setDireccion()
    {
        if(cmbDireccion.getSelectedIndex() == 0)
        {
            lblNombre.setVisible(false);
            txtNombre.setVisible(false);
            lblCiudad.setVisible(false);
            txtCiudad.setVisible(false);
            lblCalle.setVisible(false);
            txtCalle.setVisible(false);
            lblNumero.setVisible(false);
            txtNumero.setVisible(false);
            lblBloque.setVisible(false);
            txtBloque.setVisible(false);
            lblTfno.setVisible(false);
            txtTfno.setVisible(false);
            lblEmail.setVisible(false);
            txtEmail.setVisible(false);
        }
        else
        {
            lblNombre.setVisible(true);
            txtNombre.setVisible(true);
            lblCiudad.setVisible(true);
            txtCiudad.setVisible(true);
            lblCalle.setVisible(true);
            txtCalle.setVisible(true);
            lblNumero.setVisible(true);
            txtNumero.setVisible(true);
            lblBloque.setVisible(true);
            txtBloque.setVisible(true);
            lblTfno.setVisible(true);
            txtTfno.setVisible(true);
            lblEmail.setVisible(true);
            txtEmail.setVisible(true);
        }
    }
    
    private void cerrar()
    {
        _pedidoP.setAbierto(false);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblPedido = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblArticulo = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblNumArt = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTicket = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        cmbDireccion = new javax.swing.JComboBox<>();
        lblCalle = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblBloque = new javax.swing.JLabel();
        txtBloque = new javax.swing.JTextField();
        lblCodPostal = new javax.swing.JLabel();
        txtCodPostal = new javax.swing.JTextField();
        lblTfno = new javax.swing.JLabel();
        txtTfno = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        butConfirmar = new javax.swing.JButton();
        lblCiudad = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pedido ");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(710, 529));

        lblPedido.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPedido.setText("Pedido ");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        lblArticulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblArticulo.setText("Artículo");

        lblPrecio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrecio.setText("Precio");

        lblNumArt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNumArt.setText("Número de artículos: ");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("Total: ");

        jScrollPane1.setBorder(null);

        panelTicket.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        panelTicket.setMaximumSize(new java.awt.Dimension(537, 358));
        panelTicket.setLayout(new java.awt.GridLayout(0, 2, 0, 20));
        jScrollPane1.setViewportView(panelTicket);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblArticulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNumArt, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArticulo)
                    .addComponent(lblPrecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumArt)
                    .addComponent(lblTotal))
                .addContainerGap())
        );

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Datos del cliente");

        lblNombre.setText("Nombre");

        lblDireccion.setText("Dirección de envío");

        cmbDireccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tienda", "Domicilio" }));
        cmbDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDireccionActionPerformed(evt);
            }
        });

        lblCalle.setText("Calle");

        lblNumero.setText("Número");

        lblBloque.setText("Bloque");

        lblCodPostal.setText("Código Postal");

        lblTfno.setText("Teléfono");

        lblEmail.setText("E-Mail");

        butConfirmar.setBackground(new java.awt.Color(0, 0, 0));
        butConfirmar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        butConfirmar.setText("CONFIRMAR Y ENVIAR");
        butConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConfirmarActionPerformed(evt);
            }
        });

        lblCiudad.setText("Ciudad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(butConfirmar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDireccion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbDireccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombre))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCiudad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCiudad))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCalle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCalle))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTfno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTfno))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCodPostal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNumero)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblBloque)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtBloque, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 63, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEmail))))
                    .addComponent(lblPedido))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodPostal)
                            .addComponent(txtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(cmbDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCiudad)
                            .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCalle)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNumero)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBloque)
                            .addComponent(txtBloque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTfno)
                            .addComponent(txtTfno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(butConfirmar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDireccionActionPerformed
        setDireccion();
    }//GEN-LAST:event_cmbDireccionActionPerformed

    private void butConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConfirmarActionPerformed
        Object[] options = {"Aceptar",
                            "Cancelar"};
        int n = JOptionPane.showOptionDialog(this,
            "Se almacenará el pedido, ¿confirmar?",
            "Confirmar pedido",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]); //default button title
        
        if(n==0){
           cerrar(); 
        }
    }//GEN-LAST:event_butConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butConfirmar;
    private javax.swing.JComboBox<String> cmbDireccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblArticulo;
    private javax.swing.JLabel lblBloque;
    private javax.swing.JLabel lblCalle;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCodPostal;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumArt;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblPedido;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTfno;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelTicket;
    private javax.swing.JTextField txtBloque;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCodPostal;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtTfno;
    // End of variables declaration//GEN-END:variables
}
