package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Talla
 *
 * @author Víctor Martín Torres
 */
public class TallaTest {
    
    private Talla _talla_prueba;
    private Talla _talla_M;
    
    public TallaTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        _talla_prueba = Talla.Create("XXXL", false);
        _talla_M = new Talla(4);
    }
    
    @After
    public void tearDown() throws Exception {
        _talla_prueba.Delete();
    }

    /**
     * Test of getId method, of class Talla.
     */
    @Test
    public void testGetId() {
        System.out.println("Talla: getId");
        Talla instance = _talla_M;
        int expResult = 4;
        int result = instance.getId();
        assertEquals(expResult, result);;
    }

    /**
     * Test of getNombre method, of class Talla.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Talla: getNombre");
        Talla instance = _talla_M;
        String expResult = "M";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEs_Numero method, of class Talla.
     */
    @Test
    public void testGetEs_Numero() {
        System.out.println("Talla: getEs_Numero");
        Talla instance = _talla_M;
        Boolean expResult = false;
        Boolean result = instance.getEs_Numero();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Talla.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Talla: getIsDeleted");
        Talla instance = _talla_M;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Talla.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Talla: setNombre");
        String sNombre = "Prueba_Nombre";
        Talla instance = _talla_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of setEs_Numero method, of class Talla.
     */
    @Test
    public void testSetEs_Numero() {
        System.out.println("Talla: setEs_Numero");
        Boolean bEs_Numero = true;
        Talla instance = _talla_prueba;
        instance.setEs_Numero(bEs_Numero);
        assertEquals(bEs_Numero, instance.getEs_Numero());
    }

    /**
     * Test of toString method, of class Talla.
     */
    @Test
    public void testToString() {
        System.out.println("Talla: toString");
        Talla instance = _talla_M;
        String expResult = "M";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Talla.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Talla: Create");
        String sNombre = "Prueba_Create";
        boolean bEs_Numero = false;
        Talla result = Talla.Create(sNombre, bEs_Numero);
        assertEquals(sNombre, result.getNombre());
        assertEquals(bEs_Numero, result.getEs_Numero());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Talla.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Talla: Delete");
        Talla instance = Talla.Create("Prueba_Delete", false);
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Talla.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Talla: Update");
        _talla_prueba.setNombre("Prueba_Update");
        _talla_prueba.Update();
        Talla instance = new Talla(_talla_prueba.getId());
        assertEquals("Prueba_Update", instance.getNombre());
    }

    /**
     * Test of Select method, of class Talla.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Talla: Select");
        int expResult = 26;
        ArrayList<Talla> result = Talla.Select(null, null);
        assertEquals(expResult, result.size());
    }
    
}
