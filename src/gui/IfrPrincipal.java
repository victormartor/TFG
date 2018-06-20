package gui;

import Data.Clases.Configuracion;
import Data.Clases.PedidoPendiente;
import Data.Modelos.ModPedidos;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import util.Servidor;

/**
 * Ventana desde la que se pueden recibir los pedidos y desde donde se puede
 * acceder a las demás ventanas, además de tener acceso a encender o apagar
 * el servidor.
 *
 * @author Víctor Martín Torres - 12/06/2018
 * @see Servidor
 * @see ModPedidos
 */
public class IfrPrincipal extends javax.swing.JFrame 
{
    private final Servidor _servidor;
    private final ModPedidos _modPedidos;
    private String _sIP = null;
    private Thread _hilo;
    private int _numPedidos;
    
    /**
     * Crea una nueva interfaz principal de la aplicación.
     */
    public IfrPrincipal() 
    {
        initComponents();
        getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        _numPedidos = 1;
        
        //Obtener la IP del equipo
        obtenerIP();
        
        //Obtener el nombre de la tienda
        try {
            lblNombreTienda.setText(Configuracion.Select("Nombre_tienda", null)
                    .get(0).getValor());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al obtener el nombre de la tienda.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        //Asignarle un modelo a la lista de pedidos pendientes
        _modPedidos = new ModPedidos();
        listPedidosPendientes.setModel(_modPedidos);
        
        //crear el servidor y encenderlo por defecto
        _servidor = new Servidor();
        butEstadoServidor.doClick();
        
        //si se hace doble click en un pedido acceder a la interfaz de pedido
        listPedidosPendientes.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2)
                   ver_pedido();
           }
        });
        
        //Obtener el logo de la aplicación
        Image image = new ImageIcon(ClassLoader.getSystemResource("img/ES.PNG")).getImage();
        ImageIcon iconoEscalado = new ImageIcon (image.getScaledInstance(100,-1,Image.SCALE_SMOOTH));
        icono_logo.setIcon(iconoEscalado);
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
    
    //acceder al pedido
    private void ver_pedido()
    {
        int index = listPedidosPendientes.getSelectedIndex();
        
        if(index != -1)
        {
            PedidoPendiente pedido = _modPedidos.getPedido(index);
        
            if(!pedido.getAbierto())
            {
                java.awt.EventQueue.invokeLater(() -> {
                    try {
                        Frame ifrPedido = new IfrPedido(pedido, null);
                        ifrPedido.setBackground(java.awt.Color.white);
                        ifrPedido.setLocationRelativeTo(this);
                        ifrPedido.setVisible(true);
                        pedido.setFrame(ifrPedido);
                        pedido.setAbierto(true);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, 
                        "Error al obtener el pedido.\n"+ex.toString(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    }
                });  
            }
            else
            {
                pedido.getFrame().setState(NORMAL);
                pedido.getFrame().toFront();
            }
        }
    }
    
    //obtener la IP del equipo, si el equipo no está conectado a la red se le
    //notificará
    private void obtenerIP()
    {
        try{
            _sIP = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException ex){
            JOptionPane.showMessageDialog(null, 
                "Error al obtener la ip.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        if(!_sIP.equals("127.0.0.1"))
            lblIP.setText("Tu dirección IP es: "+_sIP);
        else 
            lblIP.setText("No estás conectado.");
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

        lblNombreTienda = new javax.swing.JLabel();
        lblIP = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPedidosPendientes = new javax.swing.JList<>();
        butEstadoServidor = new javax.swing.JToggleButton();
        lblEstadoServidor = new javax.swing.JLabel();
        butVerPedido = new javax.swing.JButton();
        butEliminar = new javax.swing.JButton();
        icono_logo = new javax.swing.JLabel();
        lblException = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jSplitPane1 = new javax.swing.JSplitPane();
        jToolBar2 = new javax.swing.JToolBar();
        butBaseDatos = new javax.swing.JButton();
        butExistencias = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jSplitPane2 = new javax.swing.JSplitPane();
        jToolBar4 = new javax.swing.JToolBar();
        butTallas = new javax.swing.JButton();
        butColores = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        butPedidos = new javax.swing.JButton();
        butConf = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EasyShop");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(433, 490));

        lblNombreTienda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNombreTienda.setText("Nombre tienda");

        lblIP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIP.setText("Dirección IP");

        listPedidosPendientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        listPedidosPendientes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listPedidosPendientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listPedidosPendientes.setToolTipText("Lista de pedidos");
        listPedidosPendientes.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(listPedidosPendientes);

        butEstadoServidor.setBackground(new java.awt.Color(238, 36, 36));
        butEstadoServidor.setForeground(new java.awt.Color(255, 255, 255));
        butEstadoServidor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/power_90.png"))); // NOI18N
        butEstadoServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEstadoServidorActionPerformed(evt);
            }
        });

        lblEstadoServidor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstadoServidor.setText("Apagado");

        butVerPedido.setBackground(new java.awt.Color(0, 0, 0));
        butVerPedido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butVerPedido.setForeground(new java.awt.Color(255, 255, 255));
        butVerPedido.setText("Ver pedido");
        butVerPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVerPedidoActionPerformed(evt);
            }
        });

        butEliminar.setBackground(java.awt.Color.red);
        butEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butEliminar.setForeground(new java.awt.Color(255, 255, 255));
        butEliminar.setText("Eliminar");

        icono_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icono_logo.setMaximumSize(new java.awt.Dimension(100, 100));

        lblException.setText(" ");

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jSplitPane1.setBorder(null);

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        butBaseDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/010-database-1.png"))); // NOI18N
        butBaseDatos.setToolTipText("Base de datos");
        butBaseDatos.setFocusable(false);
        butBaseDatos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butBaseDatos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butBaseDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBaseDatosActionPerformed(evt);
            }
        });
        jToolBar2.add(butBaseDatos);

        butExistencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/004-online-shop.png"))); // NOI18N
        butExistencias.setToolTipText("Existencias");
        butExistencias.setFocusable(false);
        butExistencias.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butExistencias.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butExistencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExistenciasActionPerformed(evt);
            }
        });
        jToolBar2.add(butExistencias);

        jSplitPane1.setLeftComponent(jToolBar2);

        jToolBar3.setBorder(null);
        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        jSplitPane2.setBorder(null);

        jToolBar4.setBorder(null);
        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        butTallas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/006-price-tag.png"))); // NOI18N
        butTallas.setToolTipText("Tallas");
        butTallas.setFocusable(false);
        butTallas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butTallas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butTallas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTallasActionPerformed(evt);
            }
        });
        jToolBar4.add(butTallas);

        butColores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/008-pantone.png"))); // NOI18N
        butColores.setToolTipText("Colores");
        butColores.setFocusable(false);
        butColores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butColores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butColoresActionPerformed(evt);
            }
        });
        jToolBar4.add(butColores);

        jSplitPane2.setLeftComponent(jToolBar4);

        jToolBar5.setBorder(null);
        jToolBar5.setFloatable(false);
        jToolBar5.setRollover(true);

        butPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/001-list.png"))); // NOI18N
        butPedidos.setToolTipText("Pedidos realizados");
        butPedidos.setFocusable(false);
        butPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butPedidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedidosActionPerformed(evt);
            }
        });
        jToolBar5.add(butPedidos);

        butConf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/002-gear.png"))); // NOI18N
        butConf.setToolTipText("Configuración");
        butConf.setFocusable(false);
        butConf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butConf.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        butConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConfActionPerformed(evt);
            }
        });
        jToolBar5.add(butConf);

        jSplitPane2.setRightComponent(jToolBar5);

        jToolBar3.add(jSplitPane2);

        jSplitPane1.setRightComponent(jToolBar3);

        jToolBar1.add(jSplitPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(lblNombreTienda)
                    .addComponent(lblIP))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(butVerPedido, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(butEliminar, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblEstadoServidor)
                                .addGap(29, 29, 29))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(butEstadoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(icono_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jSeparator3)
            .addComponent(lblException, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(butEstadoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstadoServidor)
                        .addGap(68, 68, 68)
                        .addComponent(butVerPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(icono_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreTienda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblException))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butEstadoServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEstadoServidorActionPerformed
        obtenerIP();
        
        if(butEstadoServidor.isSelected())
        {
            lblException.setText(" ");
            try {
                _servidor.encenderServidor();
            } catch (IOException ex) {
                lblException.setText(" "+ex.toString());
            }
            lblEstadoServidor.setText("Encendido");
            butEstadoServidor.setBackground(new Color(135,253,150));

            _hilo = new Thread(){
            @Override
            public void run()
                {
                    while(_servidor.encendido())
                    {
                        try {
                            _servidor.conectar();
                            String sMensaje = _servidor.obtenerMensaje();

                            if(!isInterrupted()) 
                            {
                                int iId_Marca;
                                int iId_Categoria;
                                int iId_Articulo;

                                switch(sMensaje)
                                {
                                    case "conectar": 
                                        _servidor.enviarMensaje("conectado");
                                        break;
                                    case "marcas":
                                        _servidor.enviarMarcas();
                                        break;
                                    case "marca":
                                        iId_Marca = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarMarca(iId_Marca);    
                                        break;
                                    case "categorias":
                                        iId_Marca = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarCategorias(iId_Marca);
                                        break;
                                    case "articulos":
                                        iId_Categoria = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarArticulos(iId_Categoria);
                                        break;
                                    case "articulo":
                                        iId_Articulo = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarUnArticulo(iId_Articulo);
                                        break;
                                    case "colores":
                                        iId_Articulo = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarColoresArticulo(iId_Articulo);
                                        break;
                                    case "tallas":
                                        iId_Articulo = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarTallasArticulo(iId_Articulo);
                                        break;
                                    case "combinaciones":
                                        iId_Articulo = Integer.parseInt(
                                                _servidor.obtenerMensaje());
                                        _servidor.enviarCombinacionesArticulo(iId_Articulo);
                                        break;
                                    case "pedido":
                                        sMensaje = _servidor.obtenerPedido();
                                        _modPedidos.addPedido(
                                                new PedidoPendiente(sMensaje,
                                                        _numPedidos));
                                        _servidor.enviarMensaje(
                                                String.format("%d",_numPedidos));
                                        _numPedidos++;
                                        break;
                                }
                            } 
                        }catch(IOException | NumberFormatException | SQLException ex){ 
                            if(ex.toString().contains("socket closed"))
                                lblException.setText(" Servidor apagado.");
                            else
                                lblException.setText("  "+ex.toString());
                        }
                    }
                }
            };

            _hilo.start();
        }
        else
        {
            try{
                _servidor.apagarServidor();
                _hilo.interrupt();
                lblEstadoServidor.setText("Apagado");
                butEstadoServidor.setBackground(new Color(238,36,36));
            }catch(IOException ex){ 
                lblException.setText(" "+ex.toString());
            }
        }
    }//GEN-LAST:event_butEstadoServidorActionPerformed

    private void butVerPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVerPedidoActionPerformed
        ver_pedido();
    }//GEN-LAST:event_butVerPedidoActionPerformed

    private void butBaseDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBaseDatosActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame ifrMarca = null;
            try {
                ifrMarca = new IfrMarca();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                "Error al leer la base de datos.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(ifrMarca != null){
                ifrMarca.setLocationRelativeTo(this);
                ifrMarca.setVisible(true);
            }
        });
    }//GEN-LAST:event_butBaseDatosActionPerformed

    private void butExistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExistenciasActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmExistencias = null;
            try {
                frmExistencias = new FrmExistencias(null);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al leer la base de datos.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(frmExistencias != null){
                frmExistencias.setLocationRelativeTo(this);
                frmExistencias.setVisible(true);
            }
        });
    }//GEN-LAST:event_butExistenciasActionPerformed

    private void butTallasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTallasActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmTallas = new FrmTallas();
            frmTallas.setLocationRelativeTo(this);
            frmTallas.setVisible(true);
        });
    }//GEN-LAST:event_butTallasActionPerformed

    private void butColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butColoresActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmColores = new FrmColores();
            frmColores.setLocationRelativeTo(this);
            frmColores.setVisible(true);
        });
    }//GEN-LAST:event_butColoresActionPerformed

    private void butConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConfActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame frmConfig = null;
            try {
                frmConfig = new FrmConfig(lblNombreTienda);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                "Error al leer la base de datos.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(frmConfig != null){
                frmConfig.setLocationRelativeTo(this);
                frmConfig.setVisible(true);
            }
        });
    }//GEN-LAST:event_butConfActionPerformed

    private void butPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedidosActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Frame ifrPedidos = null;
            try {
                ifrPedidos = new IfrPedidosRealizados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al leer la base de datos.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(ifrPedidos != null){
                ifrPedidos.setLocationRelativeTo(this);
                ifrPedidos.setVisible(true);
            }
        });
    }//GEN-LAST:event_butPedidosActionPerformed

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
            java.util.logging.Logger.getLogger(IfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IfrPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new IfrPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBaseDatos;
    private javax.swing.JButton butColores;
    private javax.swing.JButton butConf;
    private javax.swing.JButton butEliminar;
    private javax.swing.JToggleButton butEstadoServidor;
    private javax.swing.JButton butExistencias;
    private javax.swing.JButton butPedidos;
    private javax.swing.JButton butTallas;
    private javax.swing.JButton butVerPedido;
    private javax.swing.JLabel icono_logo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JLabel lblEstadoServidor;
    private javax.swing.JLabel lblException;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblNombreTienda;
    private javax.swing.JList<String> listPedidosPendientes;
    // End of variables declaration//GEN-END:variables
}
