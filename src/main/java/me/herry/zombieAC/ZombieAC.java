package me.herry.zombieAC;

import me.herry.zombieAC.commands.TestCommand;
import me.herry.zombieAC.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ZombieAC extends JavaPlugin {
    private static ZombieAC main;

    private ZombieAC(){}

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        this.events();
        this.commands();

        Bukkit.getConsoleSender().sendMessage(String.valueOf(ChatColor.RED) + "ZombieAC is now loading...");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getConsoleSender().sendMessage(String.valueOf(ChatColor.GREEN) + "ZombieAC is now unloading...");
    }

    private void events() {
        this.getServer().getPluginManager().registerEvents(new OnSpawn(), this);
        this.getServer().getPluginManager().registerEvents(new OnRightClick(), this);
        this.getServer().getPluginManager().registerEvents(new OnCombust(), this);
        this.getServer().getPluginManager().registerEvents(new OnDamage(), this);
        this.getServer().getPluginManager().registerEvents(new OnDeath(), this);
        this.getServer().getPluginManager().registerEvents(new ElytraBanListener(), this);
    }

    private void commands() {
        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());
    }

    public static ZombieAC getInstance(){
        return main;
    }
}
