package org.apache.sling.sli;

import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * @todo add license & javadoc :-)
 */
public abstract class SubCommand {

    public static final String TYPE = "jcr:primaryType";

    protected SliCommand sli;

    public abstract String getName();

    public SubCommand(){
    }

    public void setSli(SliCommand sli) {
        this.sli = sli;
    }

    public void execute() throws Exception{
        Config cfg = sli.getConfig();
        URL url = new URL(cfg.getProtocol(), cfg.getHostName(), cfg.getPort(), getURI());
        URLConnection connection = url.openConnection();
        String decodedAuth = cfg.getUser() + ":" + cfg.getPassword();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(decodedAuth.getBytes()));
        connection.setRequestProperty("Authorization", basicAuth);
        JSONParser parser = new JSONParser();
        InputStream response = connection.getInputStream();
        computeJSON(parser.parse(new BufferedReader(new InputStreamReader(response))));
    }
    protected abstract String getURI() throws IOException;
    protected abstract void computeJSON(Object o) throws IOException;
}
