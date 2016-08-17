package com.niconator1.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.niconator1.api.HorseTamer;
import com.niconator1.api.Kit;
import com.niconator1.api.Rarity;

public class KitPlugin extends JavaPlugin {

	private static ArrayList<Kit> kits = new ArrayList<Kit>();
	private static ArrayList<Cooldown> cooldown = new ArrayList<Cooldown>();
	private static ArrayList<Selection> selection = new ArrayList<Selection>();

	public void onEnable() {
		this.getLogger().info("Kit test loaded");
		this.getServer().getPluginManager().registerEvents(new EventManager(), this);
		loadKits();
		startLoops();
	}

	public void onDisable() {
		for (int i = 0; i < selection.size(); i++) {
			Selection s = selection.get(i);
			Player p = (Player) (s.getInventory().getHolder());
			p.closeInventory();
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("select")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Selection s = Util.getKitSelection(p, 0);
				selection.add(s);
				p.openInventory(s.getInventory());
			} else {
				sender.sendMessage("You have to be a player to use this command.");
			}
		}
		return false;
	}

	private void startLoops() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (int i = 0; i < cooldown.size(); i++) {
					Cooldown c = cooldown.get(i);
					if (c.getRemainingTicks() > 0) {
						Player p = c.getPlayer();
						if (p.isOnline() && !p.isDead()) {
							p.setLevel((int) Math.ceil(c.getRemainingTicks() / 20.0));
							float pro = c.getRemainingTicks() / (c.getTicks() + 0.000001f);
							p.setExp(pro);
						}
						c.setRemainingTicks(c.getRemainingTicks() - 1);
					} else {
						c.getPlayer().setLevel(0);
						c.getPlayer().setExp(0f);
						cooldown.remove(i);
					}
				}
			}
		}, 0, 1);
	}

	private void loadKits() {
		ItemStack iconht = new ItemStack(Material.IRON_BARDING);
		ItemStack itemht = new ItemStack(Material.LEASH);
		HorseTamer ht = new HorseTamer("Horsetamer", "kits.horsetamer", iconht, Rarity.RED, "", 20, itemht);
		kits.add(ht);
	}

	public static void addCooldown(Player p, int acooldown) {
		Cooldown c = new Cooldown(p, acooldown * 20);
		cooldown.add(c);
	}

	public static boolean isAbilityReady(Player p) {
		for (int i = 0; i < cooldown.size(); i++) {
			Cooldown c = cooldown.get(i);
			if (c.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				return false;
			}
		}
		return true;
	}

	public static Cooldown getCooldown(Player p) {
		for (int i = 0; i < cooldown.size(); i++) {
			Cooldown c = cooldown.get(i);
			if (c.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				return c;
			}
		}
		return null;
	}

	public static ArrayList<Kit> getKits() {
		return kits;
	}

	public static ArrayList<Selection> getSelectionInventories() {
		return selection;
	}

	public static void removeSelection(int i) {
		selection.remove(i);
	}
}
