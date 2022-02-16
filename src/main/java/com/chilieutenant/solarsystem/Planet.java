package com.chilieutenant.solarsystem;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class Planet {

    static List<Planet> galaxies = new ArrayList<>();
    private String name;
    private double radius;
    private Location mainLocation;
    private double speed;
    private float turnSpeed;
    private ArmorStand mainStand;
    private double t = 0;
    private ModeledEntity modeledEntity;
    private int tick;

    public Planet(String name, Location location, double radius, float turnspeed, double speed){
        this.name = name;
        this.mainLocation = location;
        mainLocation.setYaw(0);
        mainLocation.setPitch(0);
        this.radius = radius;
        this.speed = speed;
        this.turnSpeed = turnspeed;
        createBall();
        galaxies.add(this);
    }

    public void createBall(){
        double x = radius * Math.cos(0);
        double y = radius/7 * Math.cos(0);
        double z = radius * Math.sin(0);
        Location l = mainLocation.clone().add(x, y, z);
        mainStand = l.getWorld().spawn(l, ArmorStand.class);
        mainStand.setGravity(false);
        mainStand.setInvisible(true);
        mainStand.setMarker(true);
        ActiveModel model = ModelEngineAPI.api.getModelManager().createActiveModel(name);
        modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(mainStand);
        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
    }

    public void setRadius(double rad){
        this.radius = rad;
    }

    public void setLocation(Location loc){
        this.mainLocation = loc;
    }

    public void setSpeed(double spd){
        this.speed = spd;
    }

    public void setTurnSpeed(float speed){
        this.turnSpeed = speed;
    }


    public void remove(){
        mainStand.remove();
    }

    public void progress(){
        tick++;
        if(!mainStand.getLocation().getChunk().isLoaded()) return;
        if(name.equalsIgnoreCase("sun")){
            ParticleEffect.FLASH.display(mainStand.getLocation().add(0, 2, 0), 1);
            return;
        }
        t += speed/100;
        if(t > 360){
            t = 0;
        }
        if(tick % 100 == 0){
            modeledEntity.detectPlayers();
        }
        double x = radius * Math.cos(t);
        double y = radius/7 * Math.cos(t);
        double z = radius * Math.sin(t);
        Location loc = mainLocation.clone();
        loc.add(x, y, z);
        loc.setPitch(0);
        loc.setYaw(mainStand.getLocation().getYaw() + turnSpeed);
        mainStand.teleport(loc);
    }
}
