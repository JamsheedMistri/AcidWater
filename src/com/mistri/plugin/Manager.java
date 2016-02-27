package com.mistri.plugin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Manager extends BukkitRunnable {

	AcidWater plugin;
	FileManager fileManager;

	public Manager(AcidWater plugin) {
		this.plugin = plugin;
	}
	public Manager(FileManager fileManager) {
		this.fileManager = fileManager;
	}


	public void run() {
		// TODO Auto-generated method stub
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().getBlock().getType() == Material.WATER || p.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
				if (!p.hasPermission("AcidWater.bypass")) {
					fileManager = new FileManager(plugin);
					fileManager.getConfig("config.yml");
					if (fileManager.getConfig("config.yml").get().getBoolean("world-support")) {
						List<String> worlds = fileManager.getConfig("config.yml").get().getStringList("worlds");
						for (String s : worlds) {
							if (p.getWorld().getName().equalsIgnoreCase(s)) {
								p.damage(fileManager.getConfig("config.yml").get().getDouble("damage"));
							}
						}
					}
					else {
						p.damage(fileManager.getConfig("config.yml").get().getDouble("damage"));
					}

				}
			}

		}
	}

}