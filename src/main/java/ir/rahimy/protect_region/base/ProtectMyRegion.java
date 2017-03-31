package ir.rahimy.protect_region.base;

import ir.rahimy.protect_region.listeners.PlayerEnterRegionListener;
import ir.rahimy.protect_region.region.Region;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ProtectMyRegion extends JavaPlugin {

    // Arrays for region data.
    // just 3 regions allowed because of a possible server lag
    // We load these data from config.yml
    Vector[] regionsVector=new Vector[3];
    Region[] regions = new Region[3];
    double[] rads = new double[3];
    boolean[] regionIsPresent = new boolean[3];

    // maybe the radius for a region has not been set, so we need a default value.
    double defaultRad=50;

    @Override
    public void onEnable() {

        // Checking if the plugin is enabled by user,
        // Checking if the user defined regions or not, we use them later for avoiding exceptions
        checkConfigs();

        // Loading radius for a region, if it hasn't been set
        // set it to default value
        loadRads();

        // Loading vectors from config file
        loadRegionVectors();

        // Construct regions with previously loaded vectors and rads
        registerRegionEvents();

        saveAllConfigs();

        getLogger().info("Protect My Region has been enabled successfully.");

    }

    @Override
    public void onDisable(){
        reloadConfig();     // saving data before its elimination
        saveConfig();
    }

    public void checkConfigs(){

        // try getting "region" data, if they are not present, the array is filled with 'false'
        regionIsPresent[0] = getConfig().contains("region0");
        regionIsPresent[1] = getConfig().contains("region1");
        regionIsPresent[2] = getConfig().contains("region2");
    }

    public void loadRads(){
        // try getting the 'r' property of each region
        // set it to default if it's not present

        try {
            rads[0] = getConfig().getDouble("region0.r");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            rads[0] = defaultRad;
        }

        try {
            rads[1] = getConfig().getDouble("region1.r");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rads[1] = defaultRad;
        }

        try {
            rads[2] = getConfig().getDouble("region2.r");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rads[2] = defaultRad;
        }
    }

    public void loadRegionVectors(){

        // get x, y, z data for each region if the 'region[n]' is present
        if(regionIsPresent[0]) {
            regionsVector[0] = new Vector(getConfig().getDouble("region0.x"), getConfig().getDouble("region0.y"), getConfig().getDouble("region0.z"));
        }else {
            //new Vector(-141.0, 69.0, 252.0);
        }

        if(regionIsPresent[1]) {
            regionsVector[1] = new Vector(getConfig().getDouble("region1.x"), getConfig().getDouble("region1.y"), getConfig().getDouble("region1.z"));
        }else {
            //new Vector(-150.0, 69.0, 300.0);
        }

        if(regionIsPresent[2]) {
            regionsVector[2] = new Vector(getConfig().getDouble("region2.x"), getConfig().getDouble("region2.y"), getConfig().getDouble("region2.z"));
        }else {
            //new Vector(-130.0, 69.0, 200.0);
        }
    }

    public void registerRegionEvents(){

        // Define a new vector for present regions and make a new Region() .
        // Fill regions array with new Region()s defined.
        // Register events in server

        if(regionIsPresent[0]) {
            Vector vector = regionsVector[0];
            regions[0] = new Region("region0",
                    new Location(this.getServer().getWorlds().get(0),
                            vector.getX(), vector.getY(), vector.getZ()), rads[0]);
            getServer().getPluginManager().registerEvents(new PlayerEnterRegionListener(this, regions[0]), this);
        }

        if(regionIsPresent[1]) {
            Vector vector = regionsVector[1];
            regions[1] = new Region("region1",
                    new Location(this.getServer().getWorlds().get(0),
                            vector.getX(),vector.getY(),vector.getZ()), rads[1]);
            getServer().getPluginManager().registerEvents(new PlayerEnterRegionListener(this, regions[1]), this);
        }

        if (regionIsPresent[2]){
            Vector vector = regionsVector[2];
            regions[2] = new Region("region2",
                    new Location(this.getServer().getWorlds().get(0),
                            vector.getX(),vector.getY(),vector.getZ()), rads[2]);
            getServer().getPluginManager().registerEvents(new PlayerEnterRegionListener(this, regions[2]), this);
        }
    }

    public void saveAllConfigs(){
        if(regionIsPresent[0]) {
            getConfig().set("region0.x", regions[0].location().getX());
            getConfig().set("region0.y", regions[0].location().getY());
            getConfig().set("region0.z", regions[0].location().getZ());
        }
        if(regionIsPresent[1]) {
            getConfig().set("region1.x", regions[1].location().getX());
            getConfig().set("region1.y", regions[1].location().getY());
            getConfig().set("region1.z", regions[1].location().getZ());
        }
        if(regionIsPresent[2]) {
            getConfig().set("region2.x", regions[2].location().getX());
            getConfig().set("region2.y", regions[2].location().getY());
            getConfig().set("region2.z", regions[2].location().getZ());
        }

        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
