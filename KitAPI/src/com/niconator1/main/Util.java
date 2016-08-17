package com.niconator1.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.niconator1.api.Kit;

public class Util {

	public static Selection getKitSelection(Player p, int page) {
		Inventory i = Bukkit.createInventory(p, 54, "Your Kits");
		ItemStack placeholder = new ItemStack(Material.THIN_GLASS);
		ItemMeta im = placeholder.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "");
		placeholder.setItemMeta(im);
		for (int j = 0; j < 54; j++) {
			int row = (j / 9);
			if (row == 0 || row == 4) {
				i.setItem(j, placeholder);
			} else if (row > 0 && row < 4) {
				if (j % 9 == 0 || j % 9 == 8) {
					i.setItem(j, placeholder);
				}
			} else if (row == 5 && j % 3 != 1) {
				i.setItem(j, placeholder);
			}
		}
		int start = 10;
		ArrayList<Kit> playerkits = getAvailableKits(p);
		for (int j = page * 21; j < playerkits.size(); j++) {
			int rowaddition = j % 21 / 7 * 2;
			int slot = start + j % 21 + rowaddition;
			Kit k = playerkits.get(j);
			i.setItem(slot, k.getIcon());
		}
		ItemStack previous = new ItemStack(Material.INK_SACK, 1, (short) 8);
		if (page > 0) {
			previous.setDurability((short) 10);
		}
		ItemMeta pim = previous.getItemMeta();
		pim.setDisplayName(ChatColor.RESET + "Previous Page");
		previous.setItemMeta(pim);
		i.setItem(46, previous);
		ItemStack next = new ItemStack(Material.INK_SACK, 1, (short) 8);
		if (page + 1.0 < playerkits.size() / 21.0) {
			next.setDurability((short) 10);
		}
		ItemMeta nim = next.getItemMeta();
		nim.setDisplayName(ChatColor.RESET + "Next Page");
		next.setItemMeta(nim);
		i.setItem(52, next);
		Selection s = new Selection(i, page);
		return s;
	}

	public static ArrayList<Kit> getAvailableKits(Player p) {
		ArrayList<Kit> ret = new ArrayList<Kit>();
		ArrayList<Kit> all = KitPlugin.getKits();
		for (int i = 0; i < all.size(); i++) {
			Kit k = all.get(i);
			if (k.hasPermission(p)) {
				ret.add(k);
			}
		}
		return ret;
	}

	public static Kit getKit(Player p) {
		HashMap<UUID, Kit> choosen = KitPlugin.getChoosenKits();
		for (int i = 0; i < choosen.size(); i++) {
			if (choosen.containsKey(p.getUniqueId())) {
				return choosen.get(p.getUniqueId());
			}
		}
		return null;
	}

	public static void addKit(Player p, Kit k) {
		p.setLevel(0);
		p.setExp(0);
		removeCooldown(p);
		HashMap<UUID, Kit> choosen = KitPlugin.getChoosenKits();
		choosen.put(p.getUniqueId(), k);
	}

	public static void addCooldown(Player p, int acooldown) {
		Cooldown c = new Cooldown(p, acooldown * 20);
		KitPlugin.getCooldown().add(c);
	}

	public static boolean isAbilityReady(Player p) {
		ArrayList<Cooldown> cooldown = KitPlugin.getCooldown();
		for (int i = 0; i < cooldown.size(); i++) {
			Cooldown c = cooldown.get(i);
			if (c.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				return false;
			}
		}
		return true;
	}

	public static Cooldown getCooldown(Player p) {
		ArrayList<Cooldown> cooldown = KitPlugin.getCooldown();
		for (int i = 0; i < cooldown.size(); i++) {
			Cooldown c = cooldown.get(i);
			if (c.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				return c;
			}
		}
		return null;
	}

	public static void removeCooldown(Player p) {
		ArrayList<Cooldown> cooldown = KitPlugin.getCooldown();
		for (int i = 0; i < cooldown.size(); i++) {
			Cooldown c = cooldown.get(i);
			if (c.getPlayer().getUniqueId().compareTo(p.getUniqueId()) == 0) {
				cooldown.remove(i);
			}
		}
	}

	public static Confirm getKitConfirmation(Player p, Kit k) {
		Inventory i = Bukkit.createInventory(p, 54, k.getName() + " Kit");
		ItemStack placeholder = new ItemStack(Material.THIN_GLASS);
		ItemMeta im = placeholder.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "");
		placeholder.setItemMeta(im);
		for (int j = 0; j < 54; j++) {
			int row = j / 9;
			int column = j % 9;
			if (row == 0) {
				i.setItem(j, placeholder);
			} else if (row == 1 || row == 4) {
				if (column != 1 && column != 3) {
					i.setItem(j, placeholder);
				}
			} else if (row == 2 || row == 3) {
				if (column != 1 && column != 3 && column != 6 && column != 7) {
					i.setItem(j, placeholder);
				}
			} else if (row == 5) {
				if (column != 8) {
					i.setItem(j, placeholder);
				}
			}
		}
		ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta cim = confirm.getItemMeta();
		cim.setDisplayName(ChatColor.RESET + "Play with this kit");
		confirm.setItemMeta(cim);
		i.setItem(24, confirm);
		i.setItem(25, confirm);
		i.setItem(33, confirm);
		i.setItem(34, confirm);
		ItemStack back = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta bim = back.getItemMeta();
		bim.setDisplayName(ChatColor.RESET + "Go Back");
		back.setItemMeta(bim);
		i.setItem(53, back);
		Confirm c = new Confirm(i, k);
		return c;
	}

}
