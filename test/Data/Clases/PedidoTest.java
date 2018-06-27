package Data.Clases;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Pedido
 *
 * @author Víctor Martín Torres
 */
public class PedidoTest {
    
    private Pedido _pedido_prueba;
    private Pedido _pedido_uno;
    
    public PedidoTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        ArrayList<Integer> aiArticulosStock = new ArrayList();
        aiArticulosStock.add(1);
        _pedido_prueba = Pedido.Create(new Date(System.currentTimeMillis()),
                11406, "Tienda", aiArticulosStock);
        _pedido_uno = new Pedido(1);
    }
    
    @After
    public void tearDown() throws Exception {
        _pedido_prueba.Delete();
    }

    /**
     * Test of getId method, of class Pedido.
     */
    @Test
    public void testGetId() {
        System.out.println("Pedido: getId");
        Pedido instance = _pedido_uno;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFecha method, of class Pedido.
     */
    @Test
    public void testGetFecha() {
        System.out.println("Pedido: getFecha");
        Pedido instance = _pedido_uno;
        String expResult = "2018-06-16";
        Date result = instance.getFecha();
        assertEquals(expResult, result.toString());
    }

    /**
     * Test of getNumArticulos method, of class Pedido.
     */
    @Test
    public void testGetNumArticulos() {
        System.out.println("Pedido: getNumArticulos");
        Pedido instance = _pedido_uno;
        int expResult = 6;
        int result = instance.getNumArticulos();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotal method, of class Pedido.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetTotal() throws SQLException {
        System.out.println("Pedido: getTotal");
        Pedido instance = _pedido_uno;
        double expResult = 143.7;
        double result = instance.getTotal();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getCodPostal method, of class Pedido.
     */
    @Test
    public void testGetCodPostal() {
        System.out.println("Pedido: getCodPostal");
        Pedido instance = _pedido_uno;
        Integer expResult = 11406;
        Integer result = instance.getCodPostal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDirEnvio method, of class Pedido.
     */
    @Test
    public void testGetDirEnvio() {
        System.out.println("Pedido: getDirEnvio");
        Pedido instance = _pedido_uno;
        String expResult = "Tienda";
        String result = instance.getDirEnvio();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArticulosStock method, of class Pedido.
     */
    @Test
    public void testGetArticulosStock() {
        System.out.println("Pedido: getArticulosStock");
        Pedido instance = _pedido_uno;
        int expResult = 6;
        ArrayList<Integer> result = instance.getArticulosStock();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getIsDeleted method, of class Pedido.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Pedido: getIsDeleted");
        Pedido instance = _pedido_uno;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFecha method, of class Pedido.
     */
    @Test
    public void testSetFecha() {
        System.out.println("Pedido: setFecha");
        Date Fecha = new Date(System.currentTimeMillis());
        Pedido instance = _pedido_prueba;
        instance.setFecha(Fecha);
        assertEquals(Fecha, instance.getFecha());
    }


    /**
     * Test of setCodPostal method, of class Pedido.
     */
    @Test
    public void testSetCodPostal() {
        System.out.println("Pedido: setCodPostal");
        Integer iCodPostal = 20010;
        Pedido instance = _pedido_prueba;
        instance.setCodPostal(iCodPostal);
        assertEquals(iCodPostal, instance.getCodPostal());
    }

    /**
     * Test of setsDirEnvio method, of class Pedido.
     */
    @Test
    public void testSetsDirEnvio() {
        System.out.println("Pedido: setsDirEnvio");
        String sDirEnvio = "Domicilio";
        Pedido instance = _pedido_prueba;
        instance.setsDirEnvio(sDirEnvio);
        assertEquals(sDirEnvio, instance.getDirEnvio());
    }

    /**
     * Test of setArticulosStock method, of class Pedido.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testSetArticulosStock() throws SQLException, Exception {
        System.out.println("Pedido: setArticulosStock");
        Pedido instance = Pedido.Create(new Date(System.currentTimeMillis()),
                11406, "Tienda", new ArrayList<>());
        assertEquals(0, instance.getArticulosStock().size());
        instance.Delete();
    }

    /**
     * Test of toString method, of class Pedido.
     */
    @Test
    public void testToString() {
        System.out.println("Pedido: toString");
        Pedido instance = _pedido_uno;
        String expResult = "1:2018-06-16:6:11406:Tienda:[1, 42, 47, 51, 56, 64]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Update method, of class Pedido.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Pedido: Update");
        _pedido_prueba.setCodPostal(5000);
        _pedido_prueba.Update();
        Pedido instance = new Pedido(_pedido_prueba.getId());
        assertEquals(_pedido_prueba.getCodPostal(), instance.getCodPostal());
    }

    /**
     * Test of Select method, of class Pedido.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Pedido: Select");
        int expResult = 7;
        ArrayList<Pedido> result = Pedido.Select(null, null, null, null, null);
        assertEquals(expResult, result.size());
    }
    
}
