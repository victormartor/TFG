/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victor
 */
public class MarcaTest {
    
    public MarcaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Marca.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetId() throws SQLException {
        System.out.println("getId");
        Marca instance = new Marca(1);
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Marca.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetNombre() throws SQLException {
        System.out.println("getNombre");
        Marca instance = new Marca(1);
        String expResult = "ZARA";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Imagen method, of class Marca.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetId_Imagen() throws SQLException {
        System.out.println("getId_Imagen");
        Marca instance = new Marca(1);
        int expResult = 1;
        int result = instance.getId_Imagen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Marca.
     * @throws java.sql.SQLException Error al acceder a la base de datos
     */
    @Test
    public void testGetIsDeleted() throws SQLException {
        System.out.println("getIsDeleted");
        Marca instance = new Marca(1);
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Marca.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String sNombre = "";
        Marca instance = null;
        instance.setNombre(sNombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId_Imagen method, of class Marca.
     */
    @Test
    public void testSetId_Imagen() {
        System.out.println("setId_Imagen");
        int iId_Imagen = 0;
        Marca instance = null;
        instance.setId_Imagen(iId_Imagen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Marca.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Marca instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Create method, of class Marca.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Create");
        String sNombre = "";
        int iId_Imagen = 0;
        Marca expResult = null;
        Marca result = Marca.Create(sNombre, iId_Imagen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Delete method, of class Marca.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Delete");
        Marca instance = null;
        instance.Delete();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Update method, of class Marca.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Update");
        Marca instance = null;
        instance.Update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Select method, of class Marca.
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Select");
        String sNombre = "";
        Integer iId_Imagen = null;
        ArrayList<Marca> expResult = null;
        ArrayList<Marca> result = Marca.Select(sNombre, iId_Imagen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
