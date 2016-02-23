package org.apache.sling.sli.subcommands;

import org.apache.commons.lang3.SystemUtils;
import org.apache.sling.sli.SubCommand;

import java.io.IOException;

/**
 * @todo add license & javadoc :-)
 */
public class CurrentPath extends SubCommand {
    @Override
    public String getName() {
        return "pwd";
    }

    @Override
    protected String getURI() throws IOException {
        return sli.getConfig().getCurrentPath() + ".0.json";
    }

    @Override
    protected void computeJSON(Object o) throws IOException {
        System.out.println(sli.getConfig().getCurrentPath());
    }
}
