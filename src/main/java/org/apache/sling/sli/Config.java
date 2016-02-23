package org.apache.sling.sli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @todo add license & javadoc :-)
 */
@Parameters(commandDescription = "configures sling instance this session is plugged to")
public class Config {
    public final static String SEP = "/";
    public final static String ROOT_PATH = SEP;
    public final static String PATH = "sli.path";
    public final static String FILE_PATH = ".sli.properties";

    private File propertiesFile = null;
    private Properties properties = null;

    @Parameter(names="-protocol", description="protocol")
    private String protocol = "http";

    @Parameter(names="-host", description="sling instance hostname")
    private String hostName = "localhost";

    @Parameter(names="-port", description="sling instance port")
    private int port = 4502;

    @Parameter(names="-user", description="sling user")
    private String user = "admin";

    @Parameter(names="-password", description = "sling user password", password = true)
    private String password = "admin";

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getCurrentPath() throws IOException {
        return getProperty(PATH, ROOT_PATH);
    }

    public void setCurrentPath(String currentPath) throws IOException {
        setProperty(PATH, currentPath);
    }

    protected Properties getProperties() throws IOException {
        if (properties == null){
            if (propertiesFile == null) {
                String filePath = System.getProperty("user.home") + "/" + FILE_PATH;
                propertiesFile = new File(filePath);
                if (!propertiesFile.exists()) {
                    propertiesFile.createNewFile();
                }
            }
            properties = new Properties();
            properties.load(new FileInputStream(propertiesFile));
        }
        return properties;
    }

    protected String getProperty(String name, String defaultValue) throws IOException {
        return getProperties().getProperty(name, defaultValue);
    }

    protected void setProperty(String name, String value) throws IOException {
        getProperties().setProperty(name, value);
        properties.store(new FileOutputStream(propertiesFile), "no comments");
    }
}
