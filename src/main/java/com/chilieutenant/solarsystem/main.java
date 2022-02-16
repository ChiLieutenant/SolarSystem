package com.chilieutenant.solarsystem;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable(){
            @Override
            public void run() {
                progressAll();
            }
        }.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(Planet.galaxies.isEmpty()){
            return;
        }
        for(Planet planet : Planet.galaxies){
            planet.remove();
        }
    }

    public void progressAll(){
        if(Planet.galaxies.isEmpty()){
            return;
        }
        for(Planet planet : Planet.galaxies){
            planet.progress();
        }
    }

    public void createGalaxies(Location mainLoc) {
        Planet sun     = new Planet("sun", mainLoc, 0, 0f, 0);
        Planet mercury = new Planet("mercury", mainLoc, 10, 2f, (int) (47.9 / 5));
        Planet venus   = new Planet("venus", mainLoc, 20, 2f, (int) (35.0 / 5));
        Planet earth   = new Planet("earth", mainLoc, 30, 2f, (int) (29.8 / 5));
        Planet mars    = new Planet("mars", mainLoc, 35, 2f, (int) (24.1 / 5));
        Planet jupiter = new Planet("jupiter", mainLoc, 45, 2f, (int) (13.1 / 5));
        Planet saturn  = new Planet("saturn", mainLoc, 60, 1f, (int) (9.7 / 5));
        Planet uranus  = new Planet("uranus", mainLoc, 90, 1f, (int) (6.8 / 5));
        Planet neptune = new Planet("neptune", mainLoc, 110, 1f, (int) (5.4 / 5));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(label.equalsIgnoreCase("create")){
                createGalaxies(player.getLocation().add(0, 0.5, 0));
            }
            if(label.equalsIgnoreCase("delete")){
                if(Planet.galaxies.isEmpty()){
                    return false;
                }
                for(Planet planet : Planet.galaxies){
                    planet.remove();
                }
            }
        }
        return false;
    }
}
