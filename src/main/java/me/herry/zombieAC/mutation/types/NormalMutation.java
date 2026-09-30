package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;

public class NormalMutation implements ZombieMutation {
    @Override
    public MutationType type() {
        return MutationType.NORMAL;
    }
}
