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
        switch (args.length) {
            case 0: {
                output.add("toggle");
                output.add("look");
                output.add("clear");
                if (sender.hasPermission("blockreference.reload")) { output.add("reload"); }
            }
            case 1: {
                if ("toggle".startsWith(args[0].toLowerCase())) { output.add("toggle"); }
                if ("look".startsWith(args[0].toLowerCase())) { output.add("look"); }
                if ("clear".startsWith(args[0].toLowerCase())) { output.add("clear"); }
                if ("reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("blockreference.reload"))
                { output.add("reload"); }
            }
        }
        return output;
    }
}
