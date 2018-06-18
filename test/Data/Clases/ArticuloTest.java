package Data.Clases;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Articulo
 *
 * @author Víctor Martín Torres
 */
public class ArticuloTest {
    
    private Articulo _articulo_prueba;
    private Articulo _articulo_camiseta;
    
    public ArticuloTest() {
    }
    
    @Before
    public void setUp() throws SQLException {
        ArrayList<Integer> aiTallas = new ArrayList();
        ArrayList<Integer> aiColores = new ArrayList();
        ArrayList<Integer> aiCombinaciones = new ArrayList();
        _articulo_prueba = Articulo.Create("Prueba_Articulo", 10, 1, false, 
                aiTallas, aiColores, aiCombinaciones);
        _articulo_camiseta = new Articulo(1);
    }
    
    @After
    public void tearDown() throws Exception {
        _articulo_prueba.Delete();
    }

    /**
     * Test of getId method, of class Articulo.
     */
    @Test
    public void testGetId() {
        System.out.println("Articulo: getId");
        Articulo instance = _articulo_camiseta;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Articulo.
     */
    @Test
    public void testGetNombre() {
        System.out.println("Articulo: getNombre");
        Articulo instance = _articulo_camiseta;
        String expResult = "CAMISETA TEXTO ESTAMPADO";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPVP method, of class Articulo.
     */
    @Test
    public void testGetPVP() {
        System.out.println("Articulo: getPVP");
        Articulo instance = _articulo_camiseta;
        double expResult = 9.95;
        double result = instance.getPVP();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getId_Categoria method, of class Articulo.
     */
    @Test
    public void testGetId_Categoria() {
        System.out.println("Articulo: getId_Categoria");
        Articulo instance = _articulo_camiseta;
        int expResult = 1;
        int result = instance.getId_Categoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTalla_Es_Numero method, of class Articulo.
     */
    @Test
    public void testGetTalla_Es_Numero() {
        System.out.println("Articulo: getTalla_Es_Numero");
        Articulo instance = _articulo_camiseta;
        boolean expResult = false;
        boolean result = instance.getTalla_Es_Numero();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTallas method, of class Articulo.
     */
    @Test
    public void testGetTallas() {
        System.out.println("Articulo: getTallas");
        Articulo instance = _articulo_camiseta;
        int expResult = 4;
        ArrayList<Integer> result = instance.getTallas();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getColores method, of class Articulo.
     */
    @Test
    public void testGetColores() {
        System.out.println("Articulo: getColores");
        Articulo instance = _articulo_camiseta;
        int expResult = 3;
        ArrayList<Integer> result = instance.getColores();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getCombinaciones method, of class Articulo.
     */
    @Test
    public void testGetCombinaciones() {
        System.out.println("Articulo: getCombinaciones");
        Articulo instance = _articulo_camiseta;
        int expResult = 3;
        ArrayList<Integer> result = instance.getCombinaciones();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getIsDeleted method, of class Articulo.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("Articulo: getIsDeleted");
        Articulo instance = _articulo_camiseta;
        boolean expResult = false;
        boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Articulo.
     */
    @Test
    public void testSetNombre() {
        System.out.println("Articulo: setNombre");
        String sNombre = "Prueba_Set";
        Articulo instance = _articulo_prueba;
        instance.setNombre(sNombre);
        assertEquals(sNombre, instance.getNombre());
    }

    /**
     * Test of setPVP method, of class Articulo.
     */
    @Test
    public void testSetPVP() {
        System.out.println("Articulo: setPVP");
        double dPVP = 5.99;
        Articulo instance = _articulo_prueba;
        instance.setPVP(dPVP);
        assertEquals(dPVP, instance.getPVP(), 0.0);
    }

    /**
     * Test of setId_Categoria method, of class Articulo.
     */
    @Test
    public void testSetId_Categoria() {
        System.out.println("Articulo: setId_Categoria");
        int iId_Categoria = 5;
        Articulo instance = _articulo_prueba;
        instance.setId_Categoria(iId_Categoria);
        assertEquals(iId_Categoria, instance.getId_Categoria());
    }

    /**
     * Test of setTalla_Es_Numero method, of class Articulo.
     */
    @Test
    public void testSetTalla_Es_Numero() {
        System.out.println("Articulo: setTalla_Es_Numero");
        boolean bTalla_Es_Numero = true;
        Articulo instance = _articulo_prueba;
        instance.setTalla_Es_Numero(bTalla_Es_Numero);
        assertEquals(bTalla_Es_Numero, instance.getTalla_Es_Numero());
    }

    /**
     * Test of setTallas method, of class Articulo.
     */
    @Test
    public void testSetTallas() {
        System.out.println("Articulo: setTallas");
        ArrayList<Integer> aiTallas = new ArrayList();
        aiTallas.add(1);
        aiTallas.add(3);
        int expResult = 2;
        Articulo instance = _articulo_prueba;
        instance.setTallas(aiTallas);
        assertEquals(expResult, instance.getTallas().size());
    }

    /**
     * Test of setColores method, of class Articulo.
     */
    @Test
    public void testSetColores() {
        System.out.println("Articulo: setColores");
        ArrayList<Integer> aiColores = new ArrayList();
        aiColores.add(1);
        aiColores.add(7);
        aiColores.add(10);
        int expResult = 3;
        Articulo instance = _articulo_prueba;
        instance.setColores(aiColores);
        assertEquals(expResult, instance.getColores().size());
    }

    /**
     * Test of setCombinaciones method, of class Articulo.
     */
    @Test
    public void testSetCombinaciones() {
        System.out.println("Articulo: setCombinaciones");
        ArrayList<Integer> aiCombinaciones = new ArrayList();
        aiCombinaciones.add(1);
        aiCombinaciones.add(10);
        int expResult = 2;
        Articulo instance = _articulo_prueba;
        instance.setCombinaciones(aiCombinaciones);
        assertEquals(expResult, instance.getCombinaciones().size());
    }

    /**
     * Test of toString method, of class Articulo.
     */
    @Test
    public void testToString() {
        System.out.println("Articulo: toString");
        Articulo instance = _articulo_camiseta;
        String expResult = "1:CAMISETA TEXTO ESTAMPADO:6:9.95:false";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of Create method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("Articulo: Create");
        String sNombre = "Prueba_Create";
        double dPVP = 5;
        int iId_Categoria = 2;
        Boolean bTalla_Es_Numero = true;
        ArrayList<Integer> aiTallas = new ArrayList();
        ArrayList<Integer> aiColores = new ArrayList();
        ArrayList<Integer> aiCombinaciones = new ArrayList();
        Articulo result = Articulo.Create(sNombre, dPVP, iId_Categoria, 
                bTalla_Es_Numero, aiTallas, aiColores, aiCombinaciones);
        assertEquals(sNombre, result.getNombre());
        assertEquals(dPVP, result.getPVP(), 0.0);
        assertEquals(iId_Categoria, result.getId_Categoria());
        assertEquals(0, result.getTallas().size());
        assertEquals(0, result.getColores().size());
        assertEquals(0, result.getCombinaciones().size());
        result.Delete();
    }

    /**
     * Test of Delete method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("Articulo: Delete");
        Articulo instance = Articulo.Create("Prueba_Delete", 1, 1, false, 
                new ArrayList(), new ArrayList(), new ArrayList());
        instance.Delete();
        assertEquals(true, instance.getIsDeleted());
    }

    /**
     * Test of Update method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("Articulo: Update");
        _articulo_prueba.setNombre("Prueba_Update");
        _articulo_prueba.Update();
        Articulo instance = new Articulo(_articulo_prueba.getId());
        assertEquals("Prueba_Update", instance.getNombre());
    }

    /**
     * Test of Select method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testSelect() throws Exception {
        System.out.println("Articulo: Select");
        ArrayList<Articulo> result = Articulo.Select(null, null, null, null);
        assertEquals(15, result.size());
        result = Articulo.Select(null, null, null, true);
        assertEquals(5, result.size());
    }

    /**
     * Test of Add_Color method, of class Articulo.
     * @throws java.lang.Exception Error al acceder  la base de datos
     */
    @Test
    public void testAdd_Color() throws Exception {
        System.out.println("Articulo: Add_Color");
        Color color = new Color(5);
        Articulo instance = _articulo_prueba;
        instance.Add_Color(color);
        assertEquals(true, instance.getColores().contains(5));
    }

    /**
     * Test of Get_Imagenes_Color method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testGet_Imagenes_Color() throws Exception {
        System.out.println("Articulo: Get_Imagenes_Color");
        Integer iId_Color = 1;
        Articulo instance = _articulo_camiseta;
        int expResult = 4;
        ArrayList<Imagen> result = instance.Get_Imagenes_Color(iId_Color);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of Add_Imagen_Color method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testAdd_Imagen_Color() throws Exception {
        System.out.println("Articulo: Add_Imagen_Color");
        Integer iId_Color = 1;
        File file = new File("C:\\Users\\victor\\Pictures\\fondos de ejemplo\\tiger.jpg");
        Integer iId_Imagen = Imagen.Create(file, Data.Data.getRutaImagenes()).getId();
        Articulo instance = _articulo_prueba;
        instance.Add_Imagen_Color(iId_Color, iId_Imagen);
        assertEquals(1, _articulo_prueba.Get_Imagenes_Color(1).size());
    }

    /**
     * Test of Delete_Color method, of class Articulo.
     * @throws java.lang.Exception Error al acceder a la base de datos
     */
    @Test
    public void testDelete_Color() throws Exception {
        System.out.println("Articulo: Delete_Color");
        Integer iId_Color = 1;
        Articulo instance = _articulo_prueba;
        instance.Delete_Color(iId_Color);
        assertEquals(0, _articulo_prueba.Get_Imagenes_Color(1).size());
    }
    
}
