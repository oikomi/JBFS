package org.miaohong.jbfs.store.config;

import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by baidu on 16/1/13.
 */
public class StoreConfig {
    private static ClassLoader classLoader = StoreConfig.class.getClassLoader();
    private static Properties propertie;

    private static final String configFilePath = "config/store_config.properties";

    private static StoreConfig _instance;


    static {
        //Gson gson = new Gson();
        FileInputStream configInputStream = null;
        try {
            configInputStream = new FileInputStream(classLoader.getResource(configFilePath).getFile());
            propertie.load(configInputStream);
            //_instance = gson.fromJson(IOUtils.toString(configIn), StoreConfig.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(configInputStream);
        }
    }
//
    public static StoreConfig getInstance() {
        return _instance;
    }


    public String getValue(String key) {
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);
            return value;
        } else {
            return "";
        }
    }

    public void clear() {
        propertie.clear();
    }


    public static void main(String[] args) {
        StoreConfig.getInstance();
    }
}
