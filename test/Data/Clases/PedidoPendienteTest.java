package Data.Clases;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para la clase PedidoPendiente
 *
 * @author Víctor Martín Torres
 */
public class PedidoPendienteTest {
    
    private PedidoPendiente _pedpen;
    
    public PedidoPendienteTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        String ticket = "1:1:3\nFinTicket";
        _pedpen = new PedidoPendiente(ticket, 1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTicketPedido method, of class PedidoPendiente.
     */
    @Test
    public void testGetTicketPedido() {
        System.out.println("PedidoPendiente: getTicketPedido");
        PedidoPendiente instance = _pedpen;
        int expResult = 1;
        ArrayList<Articulo_Color_Talla> result = instance.getTicketPedido();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getNumPedido method, of class PedidoPendiente.
     */
    @Test
    public void testGetNumPedido() {
        System.out.println("PedidoPendiente: getNumPedido");
        PedidoPendiente instance = _pedpen;
        int expResult = 1;
        int result = instance.getNumPedido();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotal method, of class PedidoPendiente.
     */
    @Test
    public void testGetTotal() {
        System.out.println("PedidoPendiente: getTotal");
        PedidoPendiente instance = _pedpen;
        double expResult = 9.95;
        double result = instance.getTotal();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getAbierto method, of class PedidoPendiente.
     */
    @Test
    public void testGetAbierto() {
        System.out.println("PedidoPendiente: getAbierto");
        PedidoPendiente instance = _pedpen;
        boolean expResult = false;
        boolean result = instance.getAbierto();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFrame method, of class PedidoPendiente.
     */
    @Test
    public void testGetFrame() {
        System.out.println("PedidoPendiente: getFrame");
        PedidoPendiente instance = _pedpen;
        Frame expResult = null;
        Frame result = instance.getFrame();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumArticulos method, of class PedidoPendiente.
     */
    @Test
    public void testGetNumArticulos() {
        System.out.println("PedidoPendiente: getNumArticulos");
        PedidoPendiente instance = _pedpen;
        int expResult = 1;
        int result = instance.getNumArticulos();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAbierto method, of class PedidoPendiente.
     */
    @Test
    public void testSetAbierto() {
        System.out.println("PedidoPendiente: setAbierto");
        boolean state = true;
        PedidoPendiente instance = _pedpen;
        instance.setAbierto(state);
        assertEquals(state, instance.getAbierto());
    }

    /**
     * Test of setFrame method, of class PedidoPendiente.
     */
    @Test
    public void testSetFrame() {
        System.out.println("PedidoPendiente: setFrame");
        Frame frame = null;
        PedidoPendiente instance = _pedpen;
        instance.setFrame(frame);
        assertEquals(null, instance.getFrame());
    }
    
}
