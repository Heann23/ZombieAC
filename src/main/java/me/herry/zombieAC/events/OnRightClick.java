package me.herry.zombieAC.events;

import me.herry.zombieAC.MUTATION_TYPE;
import me.herry.zombieAC.MutationHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OnRightClick implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!player.isOp()) return;
        if (!(entity instanceof Zombie zombie)) return;

        MutationHandler mh = new MutationHandler(zombie);
        MUTATION_TYPE mt = mh.getMutation();
        player.sendMessage(ChatColor.YELLOW + "Mutation Type: " + mt);
        if (mt == MUTATION_TYPE.PARASITE) player.sendMessage(ChatColor.YELLOW + "Parasite Tier: " + mh.getSplitTier());
    }
}
