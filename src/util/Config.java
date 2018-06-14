package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Clase Config que carga las propiedades de la base de datos.
 * @author Víctor Martín Torres - 12/06/2018
 */
public class Config 
{
    /**
     * Función estática para obtener las propiedades de la base de datos.
     * @param sFile el archivo properties donde están las propiedades.
     * @return Devuelve las propiedades en tipo Properties.
     * @throws IOException Error al leer el archivo.
     */
    public static Properties Properties(URL sFile) throws IOException 
    {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sFile.getPath());
            Properties result = new Properties();
            result.load(inputStream);
            return result;
        }
        finally { if (inputStream != null) inputStream.close(); }
    }
}
