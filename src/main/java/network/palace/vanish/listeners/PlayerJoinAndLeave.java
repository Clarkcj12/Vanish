package network.palace.vanish.listeners;

import network.palace.core.Core;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Marc on 12/23/16.
 */
public class PlayerJoinAndLeave implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CPlayer player = Core.getPlayerManager().getPlayer(event.getPlayer());
        if (player.getRank().getRankId() >= Rank.SPECIALGUEST.getRankId()) {
            if (player.getRank().getRankId() >= Rank.SQUIRE.getRankId()) {
                Vanish.getInstance().getVanishUtil().hide(player, true);
            }
            return;
        }
        Player bplayer = player.getBukkitPlayer();
        for (UUID uuid : Vanish.getInstance().getVanishUtil().getVanished()) {
            bplayer.hidePlayer(Bukkit.getPlayer(uuid));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        quit(event.getPlayer());
    }

    private void quit(Player player) {
        Vanish.getInstance().getVanishUtil().logout(player.getUniqueId());
    }
}