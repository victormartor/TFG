package Data.Clases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Articulo_Color_Talla
 *
 * @author victor
 */
public class Articulo_Color_TallaTest {
    
    private Articulo_Color_Talla _act_prueba;
    
    public Articulo_Color_TallaTest() {
    }
    
    @Before
    public void setUp() {
        _act_prueba = new Articulo_Color_Talla(1,5,3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId_Articulo method, of class Articulo_Color_Talla.
     */
    @Test
    public void testGetId_Articulo() {
        System.out.println("getId_Articulo");
        Articulo_Color_Talla instance = _act_prueba;
        int expResult = 1;
        int result = instance.getId_Articulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Color method, of class Articulo_Color_Talla.
     */
    @Test
    public void testGetId_Color() {
        System.out.println("getId_Color");
        Articulo_Color_Talla instance = _act_prueba;
        int expResult = 5;
        int result = instance.getId_Color();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Talla method, of class Articulo_Color_Talla.
     */
    @Test
    public void testGetId_Talla() {
        System.out.println("getId_Talla");
        Articulo_Color_Talla instance = _act_prueba;
        int expResult = 3;
        int result = instance.getId_Talla();
        assertEquals(expResult, result);
    }
    
}
