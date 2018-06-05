/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Config {
    public static Properties Properties(URL sFile) throws IOException {
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
