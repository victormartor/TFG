/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties Properties(String sFile) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sFile);
            Properties result = new Properties();
            result.load(inputStream);
            return result;
        }
        finally { if (inputStream != null) inputStream.close(); }
    }
}
