package com.niconator1.main;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.niconator1.api.Kit;

public class EventManager implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory in = event.getInventory();
		ArrayList<Selection> selects = KitPlugin.getSelectionInventories();
		for (int i = 0; i < selects.size(); i++) {
			Selection s = selects.get(i);
			if (s.getInventory().equals(in)) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory in = event.getInventory();
		ArrayList<Selection> selects = KitPlugin.getSelectionInventories();
		for (int i = 0; i < selects.size(); i++) {
			Selection s = selects.get(i);
			if (s.getInventory().equals(in)) {
				KitPlugin.removeSelection(i);
			}
		}
	}
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		ArrayList<Kit> kits = KitPlugin.getKits();
		for (int i = 0; i < kits.size(); i++) {
			Kit k = kits.get(i);
			k.handleRightclick(event.getPlayer());
		}
	}
}
