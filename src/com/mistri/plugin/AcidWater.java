package com.mistri.plugin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class AcidWater extends JavaPlugin implements Listener
{
	Manager manager;
	FileManager fileManager;

	List<String> defaultWorlds = Arrays.asList("world", "world_nether", "world_the_end");

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String prefix = (ChatColor.GREEN + "[" + ChatColor.AQUA + "AcidWater" + ChatColor.GREEN + "] " + ChatColor.GRAY);
		String error = (ChatColor.RED + "");
		String noPermission = (ChatColor.RED + "You do not have permission to execute this command!");

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("acidwater") && args.length == 0) {
				p.sendMessage(prefix + "AcidWater v1.2 made by " + ChatColor.GOLD + "Mistri");
				p.sendMessage(ChatColor.RED + "Usage:");
				p.sendMessage(ChatColor.AQUA + "/AcidWater " + ChatColor.GRAY + "- " + ChatColor.GOLD + "Bring up this help menu.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater setDamage <amount> " + ChatColor.GRAY + "- " + ChatColor.GOLD + "Sets the amount of hearts a player is damaged by while in water. Measured by half hearts.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater setTicks <amount> " + ChatColor.GRAY + "- " + ChatColor.GOLD + "Sets the amount of ticks in between each time a player is damaged while in water.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater worldSupport [on/off] " + ChatColor.GRAY + "- " + ChatColor.GOLD + "Turns world support on or off. Without the last argument, doing this command will notify you if world support is currently on or off.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater worlds " + ChatColor.GRAY + "- " + ChatColor.GOLD + "Will show you which worlds are currently on the world support list.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater addWorld <name> " + ChatColor.GRAY + "- " + ChatColor.GOLD + "If world support is on, this will add a world to damage players in.");
				p.sendMessage(ChatColor.AQUA + "/AcidWater removeWorld <name> " + ChatColor.GRAY + "- " + ChatColor.GOLD + "If world support is on, this will remove a world to damage players in.");
			}
			else if (commandLabel.equalsIgnoreCase("acidwater") && args.length == 1) {
				if (p.hasPermission("AcidWater.worldSupport")) {
					if (args[0].equalsIgnoreCase("worldSupport")) {
						if (fileManager.getConfig("config.yml").get().getBoolean("world-support")) {
							p.sendMessage(prefix + "World support is on. To disable it, do " + ChatColor.AQUA + "/AcidWater worldSupport off" + ChatColor.GRAY + ".");
						}
						else {
							p.sendMessage(prefix + "World support is off. To enable it, do " + ChatColor.AQUA + "/AcidWater worldSupport on" + ChatColor.GRAY + ".");
						}
					}
				}
				else {
					p.sendMessage(prefix + noPermission);
				}
				if (p.hasPermission("AcidWater.worlds")) {
					if (args[0].equalsIgnoreCase("worlds")) {
						List<String> worlds = fileManager.getConfig("config.yml").get().getStringList("worlds");
						if (fileManager.getConfig("config.yml").get().getBoolean("world-support")) {
							p.sendMessage(prefix + "Current worlds " + ChatColor.RED + "(WORLD SUPPORT IS ON)" + ChatColor.GRAY + ":");
							for (String s : worlds) {
								p.sendMessage(ChatColor.AQUA + "- " + s);
							}
							p.sendMessage(prefix + "To add a world, do " + ChatColor.AQUA + "/AcidWater addWorld <world>");
						}
						else {
							p.sendMessage(prefix + "Current worlds " + ChatColor.RED + "(WORLD SUPPORT IS OFF)" + ChatColor.GRAY + ":");
							for (String s : worlds) {
								p.sendMessage(ChatColor.AQUA + "- " + s);
							}
							p.sendMessage(prefix + "To add a world, do " + ChatColor.AQUA + "/AcidWater addWorld <world>");
						}
					}
				}
				else {
					p.sendMessage(prefix + noPermission);
				}
			}
			else if (commandLabel.equalsIgnoreCase("acidwater") && args.length == 2) {
				if (args[0].equalsIgnoreCase("setdamage")) {
					if (p.hasPermission("AcidWater.setDamage")) {
						try {
							fileManager.getConfig("config.yml").get().set("damage", Double.parseDouble(args[1]));
							fileManager.saveConfig("config.yml");
							p.sendMessage(prefix + "Sucessfully set damage to " + error + args[1] + ChatColor.GRAY + ".");
						}
						catch (NumberFormatException exception) {
							p.sendMessage(prefix + error + args[1] + " is not a valid number.");
						}
					}
					else {
						p.sendMessage(prefix + noPermission);
					}

				}
				else if (args[0].equalsIgnoreCase("setticks")) {
					if (p.hasPermission("AcidWater.setTicks")) {
						try {
							fileManager.getConfig("config.yml").get().set("ticks", Long.parseLong(args[1]));
							fileManager.saveConfig("config.yml");
							p.sendMessage(prefix + "Sucessfully set amount of ticks to " + error + args[1] + ChatColor.GRAY + ". Please note that in order for this change to take affect, you need to reload the server by doing" + error + " /reload" + ChatColor.GRAY + ".");
						}
						catch (NumberFormatException exception) {
							p.sendMessage(prefix + error + args[1] + " is not a valid number.");
						}
					}
					else {
						p.sendMessage(prefix + noPermission);
					}

				}
				else if (args[0].equalsIgnoreCase("worldSupport")) {
					if (p.hasPermission("AcidWater.worldSupport")) {
						if (args[1].equalsIgnoreCase("on")) {
							fileManager.getConfig("config.yml").get().set("world-support", true);
							fileManager.saveConfig("config.yml");
							p.sendMessage(prefix + "Sucessfully turned world support on.");
						}
						else if (args[1].equalsIgnoreCase("off")) {
							fileManager.getConfig("config.yml").get().set("world-support", false);
							fileManager.saveConfig("config.yml");
							p.sendMessage(prefix + "Sucessfully turned world support off.");
						}
						else {
							p.sendMessage(prefix + error + "Incorrect usage of command. Do " + ChatColor.BLUE + "/AcidWater " + error + "to view help.");
						}
					}
					else {
						p.sendMessage(prefix + noPermission);
					}
				}
				else if (args[0].equalsIgnoreCase("addWorld")) {
					if (p.hasPermission("AcidWater.addWorld")) {
						List<String> addThisWorld = fileManager.getConfig("config.yml").get().getStringList("worlds");
						addThisWorld.add(args[1].toString());
						fileManager.getConfig("config.yml").get().set("worlds", addThisWorld);
						fileManager.getConfig("config.yml").save();
						p.sendMessage(prefix + "Successfully added world " + ChatColor.AQUA + args[1] + ChatColor.GRAY + " to the worlds list. To remove it, do " + ChatColor.AQUA + "/AcidWater removeWorld " + args[1] + ChatColor.GRAY + ".");
					}
					else {
						p.sendMessage(prefix + noPermission);
					}
				}
				else if (args[0].equalsIgnoreCase("removeWorld")) {
					if (p.hasPermission("AcidWater.removeWorld")) {
						List<String> removeThisWorld = fileManager.getConfig("config.yml").get().getStringList("worlds");
						removeThisWorld.remove(args[1].toString());
						fileManager.getConfig("config.yml").get().set("worlds", removeThisWorld);
						fileManager.getConfig("config.yml").save();
						p.sendMessage(prefix + "Successfully removed world " + ChatColor.AQUA + args[1] + ChatColor.GRAY + " from the worlds list. To add it back, do " + ChatColor.AQUA + "/AcidWater addWorld " + args[1] + ChatColor.GRAY + ".");
					}
					else {
						p.sendMessage(prefix + noPermission);
					}
				}
				else {
					p.sendMessage(prefix + error + "Incorrect usage of command. Do " + ChatColor.BLUE + "/AcidWater " + error + "to view help.");
				}
			}
			else if (commandLabel.equalsIgnoreCase("acidwater") && args.length != 0 && args.length != 2) {
				p.sendMessage(prefix + error + "Incorrect usage of command. Do " + ChatColor.BLUE + "/AcidWater " + error + "to view help.");
			}
		}
		return true;
	}

	@Override
	public void onEnable() {
		fileManager = new FileManager(this);
		fileManager.getConfig("config.yml");
		fileManager.getConfig("config.yml").get().addDefault("damage", 2);
		fileManager.getConfig("config.yml").get().addDefault("ticks", 1);
		fileManager.getConfig("config.yml").get().addDefault("world-support", false);
		fileManager.getConfig("config.yml").get().addDefault("worlds", defaultWorlds);
		fileManager.getConfig("config.yml").copyDefaults(true).save();

		manager = new Manager(this);
		manager.runTaskTimer(this, 0, fileManager.getConfig("config.yml").get().getLong("ticks"));
	}

	@Override
	public void onDisable() {

	}




}
