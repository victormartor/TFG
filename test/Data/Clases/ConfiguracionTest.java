package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase configuracion
 *
 * @author Víctor Martín Torres
 */
public class ConfiguracionTest {
    
    private Configuracion _conf_email;
    
    public ConfiguracionTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        _conf_email = new Configuracion(2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Configuracion.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Configuracion instance = _conf_email;
        int expResult = 2;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCampo method, of class Configuracion.
     */
    @Test
    public void testGetCampo() {
        System.out.println("getCampo");
        Configuracion instance = _conf_email;
        String expResult = "Email";
        String result = instance.getCampo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValor method, of class Configuracion.
     */
    @Test
    public void testGetValor() {
        System.out.println("getValor");
        Configuracion instance = _conf_email;
        String expResult = "victormartor@hotmail.com";
        String result = instance.getValor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCampo method, of class Configuracion.
     */
    @Test
    public void testSetCampo() {
        System.out.println("setCampo");
        String sCampo = "Prueba_Campo";
        Configuracion instance = _conf_email;
        instance.setCampo(sCampo);
        assertEquals(sCampo, instance.getCampo());
    }

    /**
     * Test of setValor method, of class Configuracion.
     */
    @Test
    public void testSetValor() {
        System.out.println("setValor");
        String sValor = "email@email.com";
        Configuracion instance = _conf_email;
        instance.setValor(sValor);
        assertEquals(sValor, instance.getValor());
    }

    /**
     * Test of toString method, of class Configuracion.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testToString() throws SQLException {
        System.out.println("toString");
        Configuracion instance = new Configuracion(1);
        String expResult = "1:Nombre_tienda:VictorShop";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Update method, of class Configuracion.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Update");
        Configuracion instance = new Configuracion(2);
        instance.setValor("email@email.com");
        instance.Update();
        assertEquals("email@email.com", instance.getValor());
        instance.setValor("victormartor@hotmail.com");
        instance.Update();
    }

    /**
     * Test of Select method, of class Configuracion.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Select");
        int expResult = 2;
        ArrayList<Configuracion> result = Configuracion.Select(null, null);
        assertEquals(expResult, result.size());
    }
    
}
