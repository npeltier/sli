package org.apache.sling.sli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.sli.subcommands.ChangeDir;
import org.apache.sling.sli.subcommands.CurrentPath;
import org.apache.sling.sli.subcommands.List;

import java.util.HashMap;
import java.util.Map;

/**
 * @todo add license & javadoc :-)
 */
@Parameters(commandDescription = "main command")
public class SliCommand {
    private final SubCommand[] registeredCommands = {new ChangeDir(), new List(), new CurrentPath()};
    Map<String, SubCommand> commands = new HashMap<String, SubCommand>();
    JCommander jc;

    public Config getConfig() {
        return config;
    }

    Config config;

    public SliCommand() {
        config = new Config();
        jc = new JCommander(this);
        for (SubCommand cmd : registeredCommands){
            cmd.setSli(this);
            commands.put(cmd.getName(), cmd);
            jc.addCommand(cmd.getName(), cmd);
        }
    }

    @Parameter(names = {"-h","-help"},description = "display help", help=true)
    private boolean help;

    @Parameter(names = "-v",description = "verbose mode")
    private boolean verbose;


    public boolean isVerbose() {
        return verbose;
    }

    public boolean isHelp() {
        return help;
    }

    public void execute(String ... args) throws Exception {
        jc.parse(args);
        String cmd = jc.getParsedCommand();
        if (StringUtils.isNotBlank(cmd) && commands.containsKey(cmd)){
            SubCommand subCommand = commands.get(cmd);
            subCommand.execute();
        }
    }

}
