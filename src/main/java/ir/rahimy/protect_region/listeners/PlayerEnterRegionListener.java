package ir.rahimy.protect_region.listeners;

import ir.rahimy.protect_region.base.ProtectMyRegion;
import ir.rahimy.protect_region.region.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerEnterRegionListener implements Listener {

    ProtectMyRegion plugin;
    Region region;
    private static final boolean OUTSIDE = false;
    private static final boolean INSIDE = true;
    public boolean prevLoc=OUTSIDE;

    public PlayerEnterRegionListener(ProtectMyRegion plugin, Region region){

        this.plugin = plugin;
        this.region = region;

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        // check if the player has entered a region's area and WAS previously outside of the region
        if(enteredRegion(player) && prevLoc==OUTSIDE){
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode 2 " + player.getName());
            player.sendMessage(player.getName() + ": you have entered a restricted area, no breaks and build allowed!");
            // the player is inside of this region now
            prevLoc = INSIDE;
        }

        // check if the player has left a region's area and WAS previously inside of the region
        if (leftRegion(player) && prevLoc==INSIDE){
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "gamemode 0 " + player.getName());
            player.sendMessage(player.getName() + ": you have left a restricted area, free to break and build!");
            prevLoc=OUTSIDE;
        }
    }

    public boolean enteredRegion(Player player){
        if(  (player.getLocation().distance(region.location()))  <=  region.radius()  ){
            return true;
        }else return false;
    }

    public boolean leftRegion(Player player){
        if(  (player.getLocation().distance(region.location()))  >=  region.radius()  ){
            return true;
        }else return false;
    }
}
