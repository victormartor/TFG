package Data.Clases;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para la clase Imagen
 *
 * @author Víctor Martín Torres
 */
public class ImagenTest {
    
    private Imagen _imagen_prueba;
    private Imagen _imagen_zara;
    
    public ImagenTest() {
    }
    
    @Before
    public void setUp() throws SQLException, IOException {
        File file = new File("C:\\Users\\victor\\Pictures\\fondos de ejemplo\\tiger.jpg");
        _imagen_prueba = Imagen.Create(file, Data.Data.getRutaImagenes());
        _imagen_zara = new Imagen(1);
    }
    
    @After
    public void tearDown() throws Exception {
        new Imagen(_imagen_prueba.getId()).Delete();
    }

    /**
     * Test of getId method, of class Imagen.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Imagen instance = _imagen_zara;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Imagen.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Imagen: getNombre");
        Imagen instance = _imagen_zara;
        String expResult = "historia-de-zara-2-336x330.jpg";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRuta method, of class Imagen.
     */
    @Test
    public void testGetRuta() {
        System.out.println("Imagen: getRuta");
        Imagen instance = _imagen_zara;
        String expResult = "C:\\AppServ\\www\\EasyShop\\Imagenes\\historia-de-zara-2-336x330.jpg";
        String result = instance.getRuta();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Imagen.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Imagen: getIsDeleted");
        Imagen instance = _imagen_zara;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Imagen.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Imagen: setNombre");
        String sNombre = "Prueba_nombre";
        Imagen instance = _imagen_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of setRuta method, of class Imagen.
     */
    @Test
    public void testSetRuta() {
        System.out.println("Imagen: setRuta");
        String sRuta = "Escritorio";
        Imagen instance = _imagen_prueba;
        instance.setRuta(sRuta);
        assertEquals(sRuta, instance.getRuta());
    }

    /**
     * Test of toString method, of class Imagen.
     */
    @Test
    public void testToString() {
        System.out.println("Imagen: toString");
        Imagen instance = _imagen_zara;
        String expResult = "1:historia-de-zara-2-336x330.jpg:"
                + "C:\\AppServ\\www\\EasyShop\\Imagenes\\historia-de-zara-2-336x330.jpg";
        String result = instance.toString();
        assertEquals(expResult, result);
    }


    /**
     * Test of Select method, of class Imagen.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Imagen: Select");
        int expResult = 94;
        ArrayList<Imagen> result = Imagen.Select(null, null);
        assertEquals(expResult, result.size());
    }
    
}
