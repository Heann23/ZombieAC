package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public interface ZombieMutation {

    MutationType type();

    default void apply(Zombie zombie) {}

    default void onDeath(EntityDeathEvent event, Zombie zombie) {}

    default void onAttackPlayer(EntityDamageByEntityEvent event, Zombie zombie, Player player) {}

    default void onFallDamage(EntityDamageEvent event) {}


    default void setAttribute(Zombie zombie, Attribute attribute, double value) {
        Objects.requireNonNull(zombie.getAttribute(attribute)).setBaseValue(value);
    }

    default void setMaxHealth(Zombie zombie, double health) {
        setAttribute(zombie, Attribute.MAX_HEALTH, health);
        zombie.setHealth(health);
    }
}
