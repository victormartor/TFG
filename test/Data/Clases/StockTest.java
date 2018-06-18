package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para la clase Stock
 *
 * @author Víctor Martín Torres
 */
public class StockTest {
    
    private Stock _stock_prueba;
    
    public StockTest() {
    }
    
    @Before
    public void setUp() throws SQLException, Exception {
        _stock_prueba = Stock.Create(1, 1, 3);
        _stock_prueba.setExistencias(10);
        _stock_prueba.Update();
    }
    
    @After
    public void tearDown() throws Exception {
        _stock_prueba.Delete();
    }

    /**
     * Test of getId method, of class Stock.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetId() throws SQLException {
        System.out.println("Stock: getId");
        Stock instance = new Stock(1);
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Articulo method, of class Stock.
     */
    @Test
    public void testGetId_Articulo() {
        System.out.println("Stock: getId_Articulo");
        Stock instance = _stock_prueba;
        int expResult = 1;
        int result = instance.getId_Articulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Color method, of class Stock.
     */
    @Test
    public void testGetId_Color() {
        System.out.println("Stock: getId_Color");
        Stock instance = _stock_prueba;
        int expResult = 1;
        int result = instance.getId_Color();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Talla method, of class Stock.
     */
    @Test
    public void testGetId_Talla() {
        System.out.println("Stock: getId_Talla");
        Stock instance = _stock_prueba;
        int expResult = 3;
        int result = instance.getId_Talla();
        assertEquals(expResult, result);
    }

    /**
     * Test of getExistencias method, of class Stock.
     */
    @Test
    public void testGetExistencias() {
        System.out.println("Stock: getExistencias");
        Stock instance = _stock_prueba;
        int expResult = 10;
        int result = instance.getExistencias();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Stock.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Stock: getIsDeleted");
        Stock instance = _stock_prueba;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreMarca method, of class Stock.
     */
    @Test
    public void testGetNombreMarca() {
        System.out.println("Stock: getNombreMarca");
        Stock instance = _stock_prueba;
        String expResult = "ZARA";
        String result = instance.getNombreMarca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreCategoria method, of class Stock.
     */
    @Test
    public void testGetNombreCategoria() {
        System.out.println("Stock: getNombreCategoria");
        Stock instance = _stock_prueba;
        String expResult = "Camisetas hombre";
        String result = instance.getNombreCategoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreArticulo method, of class Stock.
     */
    @Test
    public void testGetNombreArticulo() {
        System.out.println("Stock: getNombreArticulo");
        Stock instance = _stock_prueba;
        String expResult = "CAMISETA TEXTO ESTAMPADO";
        String result = instance.getNombreArticulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreColor method, of class Stock.
     */
    @Test
    public void testGetNombreColor() {
        System.out.println("Stock: getNombreColor");
        Stock instance = _stock_prueba;
        String expResult = "Amarillo flúor";
        String result = instance.getNombreColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombreTalla method, of class Stock.
     */
    @Test
    public void testGetNombreTalla() {
        System.out.println("Stock: getNombreTalla");
        Stock instance = _stock_prueba;
        String expResult = "S";
        String result = instance.getNombreTalla();
        assertEquals(expResult, result);
    }

    /**
     * Test of setExistencias method, of class Stock.
     */
    @Test
    public void testSetExistencias() {
        System.out.println("Stock: setExistencias");
        int iExistencias = 50;
        Stock instance = _stock_prueba;
        instance.setExistencias(iExistencias);
        assertEquals(iExistencias, instance.getExistencias());
    }

    /**
     * Test of toString method, of class Stock.
     */
    @Test
    public void testToString() {
        System.out.println("Stock: toString");
        Stock instance = _stock_prueba;
        String expResult = "1-1-3";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Stock.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Stock: Create");
        int iId_Articulo = 1;
        int iId_Color = 2;
        int iId_Talla = 3;
        String expResult = "1-2-3";
        Stock result = Stock.Create(iId_Articulo, iId_Color, iId_Talla);
        assertEquals(expResult, result.toString());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Stock.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Stock: Delete");
        Stock instance = Stock.Create(1, 2, 3);
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Stock.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Stock: Update");
        _stock_prueba.setExistencias(100);
        _stock_prueba.Update();
        Stock instance = new Stock(_stock_prueba.getId());
        assertEquals(100, instance.getExistencias());
    }

    /**
     * Test of Select method, of class Stock.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Stock: Select");
        int expResult = 135;
        ArrayList<Stock> result = Stock.Select(null, null, null, null);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of Search method, of class Stock.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSearch() throws Exception {
        System.out.println("Stock: Search");
        String sArticulo = "CAMISETA TEXTO ESTAMPADO";
        int expResult = 13;
        ArrayList<Stock> result = Stock.Search(sArticulo);
        assertEquals(expResult, result.size());
    }
    
}
