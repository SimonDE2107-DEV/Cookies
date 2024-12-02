package de.simonde2107.cookies.listener;

import de.simonde2107.cookies.util.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BuildListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (GameState.isIngame()) {
            // TODO: allow breaking of player-placed blocks, by storing the blockLocation in a hashmap at placing the block.
            //  And removing it from hashmap after breaking
            event.setCancelled(true);
        }
    }
}
