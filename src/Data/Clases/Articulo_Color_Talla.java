package Data.Clases;

/**
 * Almacena sus tres Ids.
 * 
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Articulo_Color_Talla
{
    private final Integer _iId_Articulo;
    private final Integer _iId_Color;
    private final Integer _iId_Talla;

    //GET
    public int getId_Articulo(){return _iId_Articulo;}
    public int getId_Color(){return _iId_Color;}
    public int getId_Talla(){return _iId_Talla;}

    public Articulo_Color_Talla(int iId_Articulo, int iId_Color, int iId_Talla)
    {
        _iId_Articulo = iId_Articulo;
        _iId_Color = iId_Color;
        _iId_Talla = iId_Talla;
    }
}