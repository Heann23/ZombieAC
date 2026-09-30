package me.herry.zombieAC.events;

import me.herry.zombieAC.mutation.MutationType;
import me.herry.zombieAC.mutation.MutationHandler;
import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.types.ZombieMutation;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Zombie zombie)) return;

        MutationHandler mh = new MutationHandler(zombie);
        MutationType type = mh.getMutation();

        ZombieMutation mutation = ZombieAC.getInstance().getMutationRegistry().get(type);
        mutation.onDeath(event, zombie);
    }
}
