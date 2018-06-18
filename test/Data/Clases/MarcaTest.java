package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para la clase Marca.
 *
 * @author Víctor Martín Torres
 */
public class MarcaTest {
    
    private Marca _marca_prueba;
    private Marca _marca_zara;
    
    public MarcaTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        _marca_prueba = Marca.Create("Prueba_Marca", -1);
        _marca_zara = new Marca(1);
    }
    
    @After
    public void tearDown() throws Exception {
        _marca_prueba.Delete();
    }

    /**
     * Test of getId method, of class Marca.
     */
    @Test
    public void testGetId() {
        System.out.println("Marca: getId");
        Marca instance = _marca_zara;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Marca.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Marca: getNombre");
        Marca instance = _marca_prueba;
        String expResult = "Prueba_Marca";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Imagen method, of class Marca.
     */
    @Test
    public void testGetId_Imagen() {
        System.out.println("Marca: getId_Imagen");
        Marca instance = _marca_prueba;
        int expResult = -1;
        int result = instance.getId_Imagen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Marca.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Marca: getIsDeleted");
        Marca instance = _marca_prueba;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Marca.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Marca: setNombre");
        String sNombre = "Prueba_Marca_Set_Nombre";
        Marca instance = _marca_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of setId_Imagen method, of class Marca.
     */
    @Test
    public void testSetId_Imagen() {
        System.out.println("Marca: setId_Imagen");
        int iId_Imagen = 0;
        Marca instance = _marca_prueba;
        instance.setId_Imagen(iId_Imagen);
        assertEquals(iId_Imagen, instance.getId_Imagen());
        instance.setId_Imagen(-1);
    }

    /**
     * Test of toString method, of class Marca.
     */
    @Test
    public void testToString() {
        System.out.println("Marca: toString");
        Marca instance = _marca_zara;
        String expResult = "1:ZARA:1";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Marca.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Marca: Create");
        String sNombre = "create";
        int iId_Imagen = -1;
        Marca result = Marca.Create(sNombre, iId_Imagen);
        assertEquals(sNombre, result.getNombre());
        assertEquals(iId_Imagen, result.getId_Imagen());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Marca.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Marca: Delete");
        Marca instance = Marca.Create("Pruabe_Delete", -1);
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Marca.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Marca: Update");
        _marca_prueba.setNombre("Prueba_Update");
        _marca_prueba.Update();
        Marca instance = new Marca(_marca_prueba.getId());
        assertEquals("Prueba_Update", instance.getNombre());
    }

    /**
     * Test of Select method, of class Marca.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws SQLException  {
        System.out.println("Marca: Select");
        ArrayList<Marca> result = Marca.Select(null, null);
        assertEquals(5, result.size());
        
        result = Marca.Select("ZARA", null);
        assertEquals(1, result.size());
        
        result = Marca.Select(null, -1);
        assertEquals(1, result.size());
    }
    
}
