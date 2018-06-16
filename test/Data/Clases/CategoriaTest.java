/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data.Clases;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test para la clase Categoria
 *
 * @author Víctor Martín Torres
 */
public class CategoriaTest {
    
    private Categoria _categoria_prueba;
    private Categoria _categoria_bodies;
    
    public CategoriaTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        _categoria_prueba = Categoria.Create("Prueba_Categoria", -1, 1);
        _categoria_bodies = new Categoria(6);
    }
    
    @After
    public void tearDown() throws Exception {
        _categoria_prueba.Delete();
    }

    /**
     * Test of getId method, of class Categoria.
     */
    @Test
    public void testGetId() {
        System.out.println("Categoria: getId");
        Categoria instance = _categoria_bodies;
        int expResult = 6;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Categoria.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Categoria: getNombre");
        Categoria instance = _categoria_bodies;
        String expResult = "Bodies";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Imagen method, of class Categoria.
     */
    @Test
    public void testGetId_Imagen() {
        System.out.println("Categoria: getId_Imagen");
        Categoria instance = _categoria_bodies;
        int expResult = 106;
        int result = instance.getId_Imagen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId_Marca method, of class Categoria.
     */
    @Test
    public void testGetId_Marca() {
        System.out.println("Categoria: getId_Marca");
        Categoria instance = _categoria_bodies;
        int expResult = 3;
        int result = instance.getId_Marca();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsDeleted method, of class Categoria.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Categoria: getIsDeleted");
        Categoria instance = _categoria_bodies;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Categoria.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Categoria: setNombre");
        String sNombre = "Prueba_nombre";
        Categoria instance = _categoria_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of setId_Imagen method, of class Categoria.
     */
    @Test
    public void testSetId_Imagen() {
        System.out.println("Categoria: setId_Imagen");
        int iId_Imagen = 0;
        Categoria instance = _categoria_prueba;
        instance.setId_Imagen(iId_Imagen);
        assertEquals(iId_Imagen, instance.getId_Imagen());
        instance.setId_Imagen(-1);
    }

    /**
     * Test of setId_Marca method, of class Categoria.
     */
    @Test
    public void testSetId_Marca() {
        System.out.println("Categoria: setId_Marca");
        int iId_Marca = 4;
        Categoria instance = _categoria_prueba;
        instance.setId_Marca(iId_Marca);
        assertEquals(iId_Marca, instance.getId_Marca());
    }

    /**
     * Test of toString method, of class Categoria.
     */
    @Test
    public void testToString() {
        System.out.println("Categoria: toString");
        Categoria instance = _categoria_bodies;
        String expResult = "6:Bodies:106:3";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Categoria.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Categoria: Create");
        String sNombre = "Prueba_create";
        int iId_Imagen = -1;
        int iId_Marca = 1;
        Categoria result = Categoria.Create(sNombre, iId_Imagen, iId_Marca);
        assertEquals(sNombre, result.getNombre());
        assertEquals(iId_Imagen, result.getId_Imagen());
        assertEquals(iId_Marca, result.getId_Marca());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Categoria.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Categoria: Delete");
        Categoria instance = Categoria.Create("Prueba_Delete", -1, 1);
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Categoria.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Categoria: Update");
        _categoria_prueba.setNombre("Prueba_Update");
        _categoria_prueba.Update();
        Categoria instance = new Categoria(_categoria_prueba.getId());
        assertEquals("Prueba_Update", instance.getNombre());
    }

    /**
     * Test of Select method, of class Categoria.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Categoria: Select");
        ArrayList<Categoria> result = Categoria.Select(null, null, null);
        assertEquals(11, result.size());
        result = Categoria.Select("hombre", null, null);
        assertEquals(4, result.size());
    }
    
}
