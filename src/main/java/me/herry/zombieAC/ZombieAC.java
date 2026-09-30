package me.herry.zombieAC;

import me.herry.zombieAC.commands.TestCommand;
import me.herry.zombieAC.events.*;
import me.herry.zombieAC.mutation.MutationKeys;
import me.herry.zombieAC.mutation.MutationRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ZombieAC extends JavaPlugin {
    private static ZombieAC instance;
    private MutationKeys mutationKeys;
    private MutationRegistry mutationRegistry;

    @Override
    public void onEnable() {
        instance = this;
        mutationKeys = new MutationKeys(this);
        mutationRegistry = new MutationRegistry();

        this.events();
        this.commands();

        Bukkit.getConsoleSender().sendMessage(String.valueOf(ChatColor.RED) + "ZombieAC is now loading...");
    }

    @Override
    public void onDisable() {
        instance = null;

        Bukkit.getConsoleSender().sendMessage(String.valueOf(ChatColor.GREEN) + "ZombieAC is now unloading...");
    }


    public MutationKeys getMutationKeys() {
        return mutationKeys;
    }

    public MutationRegistry getMutationRegistry() {
        return mutationRegistry;
    }


    private void events() {
        this.getServer().getPluginManager().registerEvents(new OnAttackPlayer(), this);
        this.getServer().getPluginManager().registerEvents(new OnCombust(), this);
        this.getServer().getPluginManager().registerEvents(new OnDeath(), this);
        this.getServer().getPluginManager().registerEvents(new OnFallDamage(), this);
        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnSpawn(), this);
        this.getServer().getPluginManager().registerEvents(new OnRightClick(), this);
    }

    private void commands() {
        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());
    }

    public static ZombieAC getInstance(){
        return instance;
    }
}
