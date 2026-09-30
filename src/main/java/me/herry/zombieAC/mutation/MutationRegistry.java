package me.herry.zombieAC.mutation;

import me.herry.zombieAC.mutation.types.*;

import java.util.EnumMap;
import java.util.Map;

public class MutationRegistry {
    private final Map<MutationType, ZombieMutation> mutations = new EnumMap<>(MutationType.class);

    public MutationRegistry() {
        register(new BoomerMutation());
        register(new JockeyMutation());
        register(new JumperMutation());
        register(new NormalMutation());
        register(new ParasiteMutation());
        register(new ShooterMutation());
        register(new SprinterMutation());
        register(new TankerMutation());
    }

    private void register(ZombieMutation mutation) {
        mutations.put(mutation.type(), mutation);
    }

    public ZombieMutation get(MutationType type) {
        return mutations.getOrDefault(type, mutations.get(MutationType.NORMAL));
    }

}
