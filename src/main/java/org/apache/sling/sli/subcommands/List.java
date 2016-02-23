package org.apache.sling.sli.subcommands;

import com.beust.jcommander.Parameters;
import org.apache.sling.sli.SliCommand;
import org.apache.sling.sli.SubCommand;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * @todo add license & javadoc :-)
 */
@Parameters(commandDescription = "list current path children")
public class List extends SubCommand{

    @Override
    public String getName() {
        return "ls";
    }

    @Override
    protected String getURI() throws IOException {
        return sli.getConfig().getCurrentPath() + ".1.json";
    }

    @Override
    protected void computeJSON(Object o) {
        JSONObject jsonObject = (JSONObject) o;
        for (Object key : jsonObject.keySet()){
            Object child = jsonObject.get(key);
            if (child instanceof JSONObject){
                JSONObject node = (JSONObject)child;
                StringBuilder line = new StringBuilder((String)key);
                if (node.containsKey(TYPE)){
                    line.append("\t\t").append(node.get(TYPE));
                }
                System.out.println(line.toString());
            }
        }
    }
}