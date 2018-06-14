/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Data.Clases.Articulo;
import Data.Clases.Articulo_Color_Talla;
import Data.Clases.Categoria;
import Data.Clases.Color;
import Data.Clases.Configuracion;
import Data.Clases.Imagen;
import Data.Clases.Marca;
import Data.Clases.Pedido;
import Data.Clases.PedidoPendiente;
import Data.Clases.Stock;
import Data.Clases.Talla;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class IfrPedido extends javax.swing.JFrame {

    private PedidoPendiente _pedidoP;
    /**
     * Creates new form IfrPedido
     */
    public IfrPedido(PedidoPendiente pedidoP, Pedido pedidoR) throws Exception {
        initComponents();
        getContentPane().setBackground(java.awt.Color.white);
        
        ArrayList<Articulo_Color_Talla> aACT = null;
        
        if(pedidoP != null){
            _pedidoP = pedidoP;
        
            setTitle(getTitle()+_pedidoP.getNumPedido());
            lblPedido.setText(lblPedido.getText()+"#"+_pedidoP.getNumPedido());
            lblNumArt.setText(lblNumArt.getText()+_pedidoP.getNumArticulos());
            lblTotal.setText(lblTotal.getText()+String.format("%.2f", _pedidoP.getTotal())+" €");
            
            aACT = pedidoP.getTicketPedido();
            butAtras.setVisible(false);
        }
        else{
            setTitle(getTitle()+pedidoR.getId());
            lblPedido.setText(lblPedido.getText()+"#"+pedidoR.getId()+" - "+pedidoR.getFecha());
            lblNumArt.setText(lblNumArt.getText()+pedidoR.getNumArticulos());
            lblTotal.setText(lblTotal.getText()+String.format("%.2f", pedidoR.getTotal())+" €");
            
            aACT = new ArrayList();
            for(Integer iId_Stock : pedidoR.getArticulosStock()){
                Stock stock = new Stock(iId_Stock);
                Articulo_Color_Talla act = new Articulo_Color_Talla(
                        stock.getId_Articulo(), stock.getId_Color(),
                        stock.getId_Talla());
                aACT.add(act);
            }
            
            txtCodPostal.setText(String.format("%d", pedidoR.getCodPostal()));
            txtCodPostal.setEditable(false);
            if(pedidoR.getDirEnvio().equals("Domicilio")) cmbDireccion.setSelectedIndex(1);
            cmbDireccion.setEnabled(false);
            butConfirmar.setVisible(false);
        }
        setDireccion(0);
          
        for(Articulo_Color_Talla art_col_tal : aACT){
            
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
                JOptionPane.showMessageDialog(null, 
                "Error al obtener el artículo, el color y la talla.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            
            if(articulo != null && marca != null && color != null && talla != null){
                
                JLabel iconoArticulo = new JLabel();
                Image image = null;
                try {
                    image = new ImageIcon(articulo.Get_Imagenes_Color(color.getId()).get(0).getRuta()).getImage();
                } catch (Exception ex) {
                    Logger.getLogger(IfrPedido.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(image != null){
                    ImageIcon iconoEscalado = new ImageIcon (image.getScaledInstance(-1,100,Image.SCALE_SMOOTH));
                    iconoArticulo.setIcon(iconoEscalado);
                }
                iconoArticulo.setVerticalAlignment(SwingConstants.TOP);
                panelTicket.add(iconoArticulo);
                
                String sNombre = null;
                if(articulo.getNombre().length()>20) sNombre = articulo.getNombre().substring(0, 20)+"...";
                else sNombre = articulo.getNombre();
                
                String sMarca = null;
                if(marca.getNombre().length()>20) sMarca = marca.getNombre().substring(0, 20)+"...";
                else sMarca = marca.getNombre();
                
                String sColor = null;
                if(color.getNombre().length()>10) sColor = color.getNombre().substring(0, 10)+"...";
                else sColor = color.getNombre();
                
                JLabel pedido = new JLabel();
                pedido.setText("<html>"+sNombre+" <br> "
                        + sMarca+" <br> "
                        + "Color: "+sColor+" <br> "
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
        
        
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrar();
            }
        });
    }
    
    @Override
     public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/boton_48.png"));
        return retValue;
    }
     
     private void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "easyshopuca";  //Para la dirección nomcuenta@gmail.com
        String clave = "uca-easySHOP18";
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        //props.put("mail.smtp.user", remitente);
        //props.put("mail.smtp.clave", "miClaveDeGMail");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom("EasyShop");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }
     
    private void atras(){
        java.awt.EventQueue.invokeLater(() -> {
            Frame ifrPedidos = null;
            try {
                ifrPedidos = new IfrPedidosRealizados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                "Error al leer la base de datos.\n"+ex.toString(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            }
            if(ifrPedidos != null){
                ifrPedidos.setLocationRelativeTo(this);
                ifrPedidos.setVisible(true);
            }
            this.dispose();
        });
    }
    
    private void setDireccion(int direccion)
    {
        if(direccion == 0)
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
        if(_pedidoP != null){
            _pedidoP.setAbierto(false);
            this.dispose();
        }
        else{
            atras();
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
        butAtras = new javax.swing.JButton();

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
        panelTicket.setLayout(new java.awt.GridLayout(0, 3, 0, 20));
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

        butAtras.setBackground(java.awt.Color.red);
        butAtras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        butAtras.setForeground(new java.awt.Color(255, 255, 255));
        butAtras.setText("Atrás");
        butAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAtrasActionPerformed(evt);
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
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
                                .addGap(0, 103, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEmail))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(butConfirmar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(butAtras, javax.swing.GroupLayout.Alignment.TRAILING)))))
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
                    .addComponent(jSeparator3)
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
                        .addGap(18, 18, 18)
                        .addComponent(butAtras)
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDireccionActionPerformed
        setDireccion(cmbDireccion.getSelectedIndex());
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
            try {
                ArrayList<Integer> aiArticulosStock = new ArrayList();
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                for(Articulo_Color_Talla act : _pedidoP.getTicketPedido()){
                    Stock stock = Stock.Select(act.getId_Articulo(), act.getId_Color(),
                            act.getId_Talla(), null).get(0);
                    /*
                    if(stock.getExistencias()>0) aiArticulosStock.add(stock.getId());
                    else throw new Exception(){
                        @Override
                        public String toString(){
                            return "No hay existencias.";
                        }
                    };   
                    */
                    aiArticulosStock.add(stock.getId());
                    
                    if(!map.containsKey(stock.getId())) map.put(stock.getId(), 1);
                    else map.put(stock.getId(), map.get(stock.getId())+1);
                }
                
                //COMPROBAR SI HAY EXISTENCIAS PARA TODOS LOS ARTICULOS DEL PEDIDO
                for(Integer iId_Stock : aiArticulosStock){
                    Stock stock = new Stock(iId_Stock);
                    if(stock.getExistencias()-map.get(iId_Stock) < 0){
                        Articulo articulo = new Articulo(stock.getId_Articulo());
                        Color color = new Color(stock.getId_Color());
                        Talla talla = new Talla(stock.getId_Talla());
                        throw new Exception(){
                            @Override
                            public String toString(){
                                return "No hay existencias de:\n"+
                                        articulo.getNombre()+"\n"+
                                        "Color: "+color.getNombre()+"\n"+
                                        "Talla: "+talla.getNombre();
                            }
                        };
                    }  
                }
                
                //REALIZAR LA RESTA EN EXISTENCIAS
                for(Integer iId_Stock : aiArticulosStock){
                    Stock s = new Stock(iId_Stock);
                    s.setExistencias(s.getExistencias()-1);
                    s.Update();
                }
                
                Integer codPostal = null;
                if(!txtCodPostal.getText().equals("")) codPostal = Integer.parseInt(txtCodPostal.getText());
            
                Pedido pedido = Pedido.Create(new Date(System.currentTimeMillis()), _pedidoP.getNumArticulos(),
                        _pedidoP.getTotal(), codPostal,
                        cmbDireccion.getSelectedItem().toString(), aiArticulosStock);
                
                //SI EL ENVIO ES A DOMICILIO SE ENVIARÁ UN CORREO CON LOS DATOS PERSONALES
                if(cmbDireccion.getSelectedIndex() == 1){
                    String destinatario = Configuracion.Select("Email", null).get(0).getValor();
                    String asunto = "EasyShop - Envío a domicilio";
                    String linea1 = "\n=====================================\n";
                    String linea2 ="\n--------------------------------------\n";
                    String cuerpo = "Pedido #"+pedido.getId()+linea1;
                    cuerpo+= linea2+"ARTÍCULOS"+linea2;
                    for(Integer iId_Stock : pedido.getArticulosStock()){
                        Stock stock = new Stock(iId_Stock);
                        Articulo articulo = new Articulo(stock.getId_Articulo());
                        Marca marca = new Marca(new Categoria(articulo.getId_Categoria()).getId_Marca());
                        Color color = new Color(stock.getId_Color());
                        Talla talla = new Talla(stock.getId_Talla());
                        
                        cuerpo+= "Cod: "+stock.toString()+"\n"
                                +articulo.getNombre()+"\n"
                                +marca.getNombre()+"\n"
                                +"Color: "+color.getNombre()+"\n"
                                +"Talla: "+talla.getNombre()+"\n"
                                +"Precio: "+articulo.getPVP()+" €\n\n";
                    }
                    cuerpo += "Total: "+pedido.getTotal()+" €";
                    cuerpo += linea2+"DATOS DEL CLIENTE"+linea2
                            +"Nombre: "+txtNombre.getText()+"\n"
                            +"Ciudad: "+txtCiudad.getText()+"\n"
                            +"Código Postal: "+txtCodPostal.getText()+"\n"
                            +"Calle: "+txtCalle.getText()+"\n"
                            +"Número: "+txtNumero.getText()+" Bloque: "+txtBloque.getText()+"\n"
                            +"Teléfono: "+txtTfno.getText()+"\n"
                            +"Email: "+txtEmail.getText()+linea1;
                    
                    enviarConGMail(destinatario,asunto,cuerpo);
                }
                
                JOptionPane.showMessageDialog(this, 
                "El pedido se ha almacenado correctamente.", 
                "Mensaje del sistema", 
                JOptionPane.PLAIN_MESSAGE);
                cerrar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al aceptar el pedido.\n"+ex.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
           
        }
    }//GEN-LAST:event_butConfirmarActionPerformed

    private void butAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAtrasActionPerformed
        atras();
    }//GEN-LAST:event_butAtrasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAtras;
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
