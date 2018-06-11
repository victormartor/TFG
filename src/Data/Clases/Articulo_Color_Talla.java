/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

/**
 *
 * @author victor
 */
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