package bonn2.BlockReference.Listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> output = new ArrayList<>();
        if (args.length > 1) { return output; }
        if (args.length == 0) {
            output.add("toggle");
            output.add("look");
            if (sender.hasPermission("blockreference.reload")) { output.add("reload"); }
            return output;
        }
        if (args.length == 1) {
            if ("toggle".startsWith(args[0].toLowerCase())) { output.add("toggle"); }
            if ("look".startsWith(args[0].toLowerCase())) { output.add("look"); }
            if ("reload".startsWith(args[0].toLowerCase())
                    && sender.hasPermission("blockreference.reload")) { output.add("reload"); }
            return output;
        }
        return null;
    }
}
