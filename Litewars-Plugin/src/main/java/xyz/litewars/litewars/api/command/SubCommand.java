package xyz.litewars.litewars.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;

public abstract class SubCommand implements CommandExecutor {
    private final ParentCommand parent;
    private final String name;
    private String description;
    private String permission;
    private final boolean isOnlyPlayer;
    private final boolean isOnlySetup;
    private String[] subs;

    public SubCommand (ParentCommand parent, String name, String description, String permission, boolean isOnlyPlayer, boolean isOnlySetup) {
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.isOnlyPlayer = isOnlyPlayer;
        this.isOnlySetup = isOnlySetup;
    }

    public SubCommand (ParentCommand parent, String name, String description, String permission, boolean isOnlyPlayer, boolean isOnlySetup, String... subs) {
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.isOnlyPlayer = isOnlyPlayer;
        this.isOnlySetup = isOnlySetup;
        this.subs = subs;
    }

    public ParentCommand getParent () {
        return this.parent;
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String s, String[] args) {
        if (args[0].equals(name)) {
            if (getPermission() != null) {
                if (!sender.hasPermission(getPermission())) {
                    return true;
                }
            }
            String[] args1 = new String[args.length - 1];
            System.arraycopy(args, 1, args1, 0, args1.length);
            execute(sender, command, s, args1);
            return  true;
        }
        return false;
    }

    public abstract boolean execute (CommandSender sender, Command command, String s, String[] args);

    public String getDescription(Pl) {
        return description;
    }

    public String getName () {
        return this.name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getPermission () {
        return this.permission;
    }

    public void setPermission (String permission) {
        this.permission = permission;
    }

    public boolean getIsOnlyPlayer() {
        return this.isOnlyPlayer;
    }

    public boolean getIsOnlySetup() {
        return this.isOnlySetup;
    }

    public String[] getSubs () {
        return subs;
    }

    public void addSub (String sub) {
        String[] newSubs = new String[this.subs.length + 1];
        if (this.subs.length - 1 >= 0) System.arraycopy(this.subs, 0, newSubs, 0, this.subs.length - 1);
        newSubs[newSubs.length - 1] = sub;
        this.subs = newSubs;
    }

    public TabCompleter getTabcompleter () {
        return (commandSender, command, s, strings) -> Arrays.stream(this.subs).toList();
    }

    @Override
    public String toString () {
        return "Litewars-SubCommand [" + this.getClass().getName() + "]\n" +
                "{parent=" + parent + ", name=" + name + ", description=" + getDescription() + ", permission=" + permission + ", isOnlyPlayer=" + isOnlyPlayer + ", isOnlySetup=" + isOnlySetup + "}" +
                "Subs [" + Arrays.toString(subs) + "]";
    }
}
