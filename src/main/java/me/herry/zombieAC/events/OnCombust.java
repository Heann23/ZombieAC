package me.herry.zombieAC.events;

import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class OnCombust implements Listener {

    @EventHandler
    public void onCombust(EntityCombustEvent event) {
        if (event.getEntity() instanceof Zombie) event.setCancelled(true);
    }
}
