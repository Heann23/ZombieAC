package me.herry.zombieAC.mutation;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class MutationKeys {
    private final NamespacedKey mutation;
    private final NamespacedKey splitTier;

    public MutationKeys(Plugin plugin) {
        this.mutation = new NamespacedKey(plugin, "mutation");
        this.splitTier = new NamespacedKey(plugin, "splitTier");
    }

    public NamespacedKey getMutation() {
        return mutation;
    }

    public NamespacedKey getSplitTier() {
        return splitTier;
    }
}
