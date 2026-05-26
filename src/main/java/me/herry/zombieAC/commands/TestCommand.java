package me.herry.zombieAC.commands;

import me.herry.zombieAC.MUTATION_TYPE;
import me.herry.zombieAC.MutationHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player) || !player.isOp()) return true;
        if (!command.getName().equalsIgnoreCase("test")) return true;

        if (args.length != 2) return false;

        MUTATION_TYPE mt;

        try {
            mt = MUTATION_TYPE.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid mutation type");
            player.sendMessage(ChatColor.RED + e.getMessage());
            return true;
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            Location loc = player.getLocation();
            loc.getWorld().spawn(loc, Zombie.class, (zombie -> {
                MutationHandler mh = new MutationHandler(zombie);
                mh.setMutation(mt);
                mh.setSplitTier(3);
            }), CreatureSpawnEvent.SpawnReason.NATURAL);

            player.sendMessage(ChatColor.GREEN + "Spawned mutation zombie " + ChatColor.YELLOW + "TYPE: " + mt.name());

        } else
            if (args[0].equalsIgnoreCase("tp")) {
            for (Zombie zombie : player.getLocation().getWorld().getEntitiesByClass(Zombie.class)) {
                MutationHandler mh = new MutationHandler(zombie);

                if (mh.getMutation() == mt) zombie.teleport(player.getLocation());
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player) || !player.isOp()) return null;
        if (!command.getName().equalsIgnoreCase("test")) return null;

        if (args.length == 1) return List.of("spawn", "tp");
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("spawn")) return List.of("NORMAL", "BOOMER", "JOCKEY", "SHOOTER", "JUMPER", "SPRINT", "TANKER", "PARASITE");
            if (args[0].equalsIgnoreCase("tp")) return List.of("NORMAL", "BOOMER", "JOCKEY", "SHOOTER", "JUMPER", "SPRINT", "TANKER", "PARASITE");
        }

        return List.of();
    }
}
