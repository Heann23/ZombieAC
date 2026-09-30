package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class TankerMutation implements ZombieMutation{
    @Override
    public MutationType type() {
        return MutationType.TANKER;
    }

    @Override
    public void apply(Zombie zombie) {
        setMaxHealth(zombie, 100.0);
        setAttribute(zombie, Attribute.SCALE, 1.5);
        setAttribute(zombie, Attribute.KNOCKBACK_RESISTANCE, 0.8);
        setAttribute(zombie, Attribute.ATTACK_KNOCKBACK, 2.5);
    }

    @Override
    public void onDeath(EntityDeathEvent event, Zombie zombie) {
        int currentExp = event.getDroppedExp();

        event.setDroppedExp(currentExp * 25);

        if (Math.random() < 0.3) event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 1));
    }
}
