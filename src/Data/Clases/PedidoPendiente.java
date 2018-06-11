/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import java.awt.Frame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author victor
 */
public class PedidoPendiente {
    public class Articulo_Color_Talla{
        private Integer _iId_Articulo;
        private Integer _iId_Color;
        private Integer _iId_Talla;
        
        //GET
        public int getId_Articulo(){return _iId_Articulo;}
        public int getId_Color(){return _iId_Color;}
        public int getId_Talla(){return _iId_Talla;}
        
        public Articulo_Color_Talla(int iId_Articulo, int iId_Color, int iId_Talla){
            _iId_Articulo = iId_Articulo;
            _iId_Color = iId_Color;
            _iId_Talla = iId_Talla;
        }
    }
    
    private ArrayList<Articulo_Color_Talla> _aTicketPedido;
    private int _iNumPedido;
    private double _dTotal;
    private boolean _bAbierto;
    private Frame _frmPedido;
    private String _sHora;
    
    //GET
    public ArrayList<Articulo_Color_Talla> getTicketPedido(){return _aTicketPedido;}
    public int getNumPedido(){return _iNumPedido;}
    public double getTotal(){return _dTotal;}
    public boolean getAbierto(){return _bAbierto;}
    public Frame getFrame(){return _frmPedido;}
    public String getHora(){return _sHora;}
    
    public int getNumArticulos(){return _aTicketPedido.size();}
    
    //SET
    public void setAbierto(boolean state){_bAbierto = state;}
    public void setFrame(Frame frame){_frmPedido = frame;}
    
    public PedidoPendiente(String sTicket, int iNumPedido) throws Exception{
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
        
        _sHora = hourFormat.format(date).toString();
    }
}
