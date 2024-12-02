package de.simonde2107.cookies.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.Location;

public class AutoTNTIgnite implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.TNT) {

            Block tntBlock = event.getBlockPlaced();
            Location tntBlockLocation = event.getBlockPlaced().getLocation();

            tntBlock.setType(Material.AIR);

            TNTPrimed tnt = tntBlockLocation.getWorld().spawn(tntBlockLocation, TNTPrimed.class);

            tnt.setFuseTicks(2*20);
        }
    }
}
