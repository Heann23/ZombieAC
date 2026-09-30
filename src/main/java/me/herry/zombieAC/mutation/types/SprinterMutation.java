package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;

public class SprinterMutation implements ZombieMutation {
    @Override
    public MutationType type() {
        return MutationType.SPRINTER;
    }

    @Override
    public void apply(Zombie zombie) {
        setMaxHealth(zombie, 15.0);
        setAttribute(zombie, Attribute.MOVEMENT_SPEED, 0.35);
    }
}
