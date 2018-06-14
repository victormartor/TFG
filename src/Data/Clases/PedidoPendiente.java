package Data.Clases;

import java.awt.Frame;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Representa un pedido que aún no ha sido aceptado.
 *
 * @author Víctor Martín Torres - 12/06/2018
 */
public class PedidoPendiente 
{
    private final ArrayList<Articulo_Color_Talla> _aTicketPedido;
    private final int _iNumPedido;
    private double _dTotal;
    private final String _sHora;
    private boolean _bAbierto;
    private Frame _frmPedido;
    
    //GET
    public ArrayList<Articulo_Color_Talla> getTicketPedido(){return _aTicketPedido;}
    public int getNumPedido(){return _iNumPedido;}
    public double getTotal(){return _dTotal;}
    public String getHora(){return _sHora;}
    public boolean getAbierto(){return _bAbierto;}
    public Frame getFrame(){return _frmPedido;}
    
    public int getNumArticulos(){return _aTicketPedido.size();}
    
    //SET
    public void setAbierto(boolean state){_bAbierto = state;}
    public void setFrame(Frame frame){_frmPedido = frame;}
    
    /**
     * Constructor a partir de un String lee todos los artículos del pedido y
     * le asigna un número de pedido.
     * 
     * @param sTicket Ticket recibido de la aplicación android en formato
     * String
     * @param iNumPedido Número del pedido.
     * @throws java.sql.SQLException Hay un error en la conexión.
     */
    public PedidoPendiente(String sTicket, int iNumPedido) throws SQLException 
    {
        _aTicketPedido = new ArrayList<>();
        String[] lines = sTicket.split("\n");
        
        _iNumPedido = iNumPedido;
        _dTotal = 0;
        
        int i = 0;
        while(!lines[i].equals("FinTicket"))
        {
            String[] Ids = lines[i].split(":");
            int Id_Articulo = Integer.parseInt(Ids[0]);
            int Id_Color = Integer.parseInt(Ids[1]);
            int Id_Talla = Integer.parseInt(Ids[2]);
            
            Articulo_Color_Talla act = new Articulo_Color_Talla(Id_Articulo, Id_Color, Id_Talla);
            _aTicketPedido.add(act);
            
            _dTotal += new Articulo(Id_Articulo).getPVP();
            i++;
        }
        
        _bAbierto = false;
        
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH'h':mm'm':ss's'");
        
        _sHora = hourFormat.format(date);
    }
}
