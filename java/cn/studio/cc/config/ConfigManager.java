package cn.studio.cc.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.studio.cc.utils.LogUtils;

/**
 * Configuration manager for CCProxy
 * Loads and manages application configuration from properties file
 */
public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;
    
    // System property name for external config file
    private static final String EXTERNAL_CONFIG_PROPERTY = "ccproxy.config";

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        
        // First, try to load external config file if specified
        String externalConfigPath = System.getProperty(EXTERNAL_CONFIG_PROPERTY);
        if (externalConfigPath != null && !externalConfigPath.isEmpty()) {
            try {
                File externalConfigFile = new File(externalConfigPath);
                if (externalConfigFile.exists() && externalConfigFile.isFile()) {
                    properties.load(new java.io.FileInputStream(externalConfigFile));
                    LogUtils.debug("Loaded external configuration from: " + externalConfigPath);
                    return;
                } else {
                    LogUtils.error("External config file not found: " + externalConfigPath);
                }
            } catch (IOException ex) {
                LogUtils.error("Error loading external config file: " + ex.getMessage());
            }
        }
        
        // Then try to load from classpath
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("cn/studio/cc/config/app.properties")) {
            if (input == null) {
                LogUtils.error("Unable to find app.properties, using default values");
                return;
            }
            properties.load(input);
            LogUtils.debug("Loaded default configuration from classpath");
        } catch (IOException ex) {
            LogUtils.error("Error loading app.properties, using default values: " + ex.getMessage());
        }
    }

    /**
     * Get server port, default is 8888
     * @return server port
     */
    public int getPort() {
        return Integer.parseInt(properties.getProperty("port", "8888"));
    }

    /**
     * Get number of answer threads, default is 16
     * @return number of threads
     */
    public int getAnswerThreadNum() {
        return Integer.parseInt(properties.getProperty("answer.thread.num", "16"));
    }

    /**
     * Get log level, default is DEBUG
     * @return log level
     */
    public String getLogLevel() {
        return properties.getProperty("log.level", "DEBUG");
    }
}