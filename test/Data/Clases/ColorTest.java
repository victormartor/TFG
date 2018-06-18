package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Color
 *
 * @author Víctor Martín Torres
 */
public class ColorTest {
    
    private Color _color_prueba;
    private Color _color_blanco;
    
    public ColorTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        _color_prueba = Color.Create("Prueba");
        _color_blanco = new Color(5);
    }
    
    @After
    public void tearDown() throws Exception {
        _color_prueba.Delete();
    }

    /**
     * Test of getId method, of class Color.
     */
    @Test
    public void testGetId() {
        System.out.println("Color: getId");
        Color instance = _color_blanco;
        int expResult = 5;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Color.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Color: getNombre");
        Color instance = _color_blanco;
        String expResult = "Blanco";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Color.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Color: getIsDeleted");
        Color instance = _color_blanco;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Color.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Color: setNombre");
        String sNombre = "Prueba_Nombre";
        Color instance = _color_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of toString method, of class Color.
     */
    @Test
    public void testToString() {
        System.out.println("Color: toString");
        Color instance = _color_blanco;
        String expResult = "Blanco";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Color.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Color: Create");
        String sNombre = "Prueba_Create";
        Color result = Color.Create(sNombre);
        assertEquals(sNombre, result.getNombre());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Color.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Color: Delete");
        Color instance = Color.Create("Prueba_Delete");
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Color.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Color: Update");
        _color_prueba.setNombre("Prueba_Update");
        _color_prueba.Update();
        Color instance = new Color(_color_prueba.getId());
        assertEquals("Prueba_Update", instance.getNombre());
    }

    /**
     * Test of Select method, of class Color.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Color: Select");
        int expResult = 21;
        ArrayList<Color> result = Color.Select(null);
        assertEquals(expResult, result.size());
    }
    
}
