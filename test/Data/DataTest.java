package Data;

import Data.Clases.Marca;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Víctor Martín Torres
 */
public class DataTest {
    
    public DataTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPropertiesUrl method, of class Data.
     * @throws java.net.MalformedURLException Error al crear la URL de prueba
     */
    @Test
    public void testGetPropertiesUrl() throws MalformedURLException {
        System.out.println("Data: getPropertiesUrl");
        URL expResult = new URL("file:\\C:\\Users\\victor\\Dropbox\\universidad\\TFG"
                + "\\TFG_PC\\build\\classes\\db.properties");
        URL result = Data.getPropertiesUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of LoadDriver method, of class Data.
     * @throws java.lang.Exception Errores al cargar el driver
     */
    @Test
    public void testLoadDriver() throws Exception {
        System.out.println("Data: LoadDriver");
        Data.LoadDriver();
    }

    /**
     * Test of String2Sql method, of class Data.
     */
    @Test
    public void testString2Sql() {
        System.out.println("Data: String2Sql");
        String s = "prueba";
        boolean bAddQuotes = true;
        boolean bAddWildcards = true;
        String expResult = "\'%prueba%\'";
        String result = Data.String2Sql(s, bAddQuotes, bAddWildcards);
        assertEquals(expResult, result);
    }

    /**
     * Test of Boolean2Sql method, of class Data.
     */
    @Test
    public void testBoolean2Sql() {
        System.out.println("Data: Boolean2Sql");
        boolean b = false;
        int expResult = 0;
        int result = Data.Boolean2Sql(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of LastId method, of class Data.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testLastId() throws Exception {
        System.out.println("Data: LastId");
        Connection con = Data.Connection();
        con.createStatement().executeUpdate(
                    "INSERT INTO Marca (Nombre, Id_Imagen)"
                    + " VALUES (" + Data.String2Sql("prueba_id", true, false) + ", " 
                    + -1 + ");");
        Marca marca = Marca.Select("prueba_id", null).get(0);
        int expResult = marca.getId();
        int result = Data.LastId(con);
        assertEquals(expResult, result);
        marca.Delete();
    }

    /**
     * Test of String2Double method, of class Data.
     */
    @Test
    public void testString2Double() {
        System.out.println("Data: String2Double");
        String s = "5,99";
        double expResult = 5.99;
        double result = Data.String2Double(s);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getRutaImagenes method, of class Data.
     */
    @Test
    public void testGetRutaImagenes() {
        System.out.println("Data: getRutaImagenes");
        String expResult = "C:\\AppServ\\www\\EasyShop\\Imagenes";
        String result = Data.getRutaImagenes();
        assertEquals(expResult, result);
    }
    
}
