package me.herry.zombieAC.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ElytraBanListener implements Listener {

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;

        if (e.getItem().getItemStack().getType() == Material.ELYTRA) {
            e.setCancelled(true);
            e.getItem().remove();
            player.sendMessage(ChatColor.RED + "겉날개 먹지 마라.");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.ELYTRA) return;

        e.setCancelled(true);
        e.setCurrentItem(null);

        if (!(e.getWhoClicked() instanceof Player player)) return;

        player.sendMessage(ChatColor.RED + "겉날개 쓰지 마라.");
    }
}
