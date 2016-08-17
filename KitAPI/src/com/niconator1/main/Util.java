package com.niconator1.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.niconator1.api.Kit;

public class Util {

	public static Selection getKitSelection(Player p, int page) {
		Inventory i = Bukkit.createInventory(p, 54, "Your Kits");
		ItemStack placeholder = new ItemStack(Material.THIN_GLASS);
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
		Selection s = new Selection(i, page);
		return s;
	}

	private static ArrayList<Kit> getAvailableKits(Player p) {
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

}
