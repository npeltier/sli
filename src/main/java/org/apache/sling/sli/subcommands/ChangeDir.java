package org.apache.sling.sli.subcommands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.sli.Config;
import org.apache.sling.sli.SubCommand;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

/**
 * change directory on sling instance like cd does on file system:
 * <ul>
 *     <li>cd /blah gets you to /blah</li>
 *     <li>cd blah gets you to /current/path/blah</li>
 *     <li>. and .. can be used to navigate relatively to current path</li>
 * </ul>
 */
@Parameters(commandDescription = "cd to another path")
public class ChangeDir extends SubCommand{

    public final static String CURRENT = ".";
    public final static String PARENT = "..";

    String newPath;

    @Parameter(description = "target directory")
    private List<String> target = new ArrayList<>();

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    protected String getURI() throws IOException {
        newPath = computeNewDirectory(sli.getConfig());
        return newPath + ".0.json";
    }

    @Override
    protected void computeJSON(Object o) throws IOException {
        sli.getConfig().setCurrentPath(newPath);
    }

    protected String computeNewDirectory(Config config) throws IOException {
        String newDirectory = config.getCurrentPath();
        if (target.size() == 0){
            return Config.ROOT_PATH;
        }
        String arg = target.get(0);
        if (arg.startsWith(Config.SEP)){
            return arg;
        } else {
            List<String> tokens = extractTokens(config.getCurrentPath());
            List<String> newTokens = extractTokens(arg);
            String firstToken = newTokens.get(0);
            if (firstToken.startsWith(CURRENT)){
                if (firstToken.equals(PARENT) && tokens.size() > 1){
                    //we remove current path's last token
                    tokens.remove(tokens.size() - 1);
                }
                newTokens.remove(0);
            }
            tokens.addAll(newTokens);
            return assembleTokens(tokens);
        }
    }

    private List<String> extractTokens(String path){
        List<String> tokens = new ArrayList<>();
        for (String token : path.split(Config.SEP)){
            if(StringUtils.isNotBlank(token)){
                tokens.add(token);
            }
        }
        return tokens;
    }

    private String assembleTokens(List<String> tokens){
        StringBuilder path = new StringBuilder(Config.ROOT_PATH);
        for (int t = 0; t < tokens.size(); t ++) {
            path.append(tokens.get(t));
            if (t < tokens.size() - 1) {
                path.append(Config.SEP);
            }
        }
        return path.toString();
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }
}
