package com.niconator1.main;

import java.util.ArrayList;

import org.bukkit.entity.Player;
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
		ArrayList<Confirm> confirms = KitPlugin.getConfirmationInventories();
		for (int i = 0; i < selects.size(); i++) {
			Selection s = selects.get(i);
			if (s.getInventory().equals(in)) {
				Player p = (Player) s.getInventory().getHolder();
				if (event.getRawSlot() == 46) {
					if (s.getPage() > 0) {
						selects.remove(i);
						Selection neu = Util.getKitSelection(p, s.getPage() - 1);
						p.openInventory(neu.getInventory());
						selects.add(neu);
					}
				} else if (event.getRawSlot() == 52) {
					ArrayList<Kit> playerkits = Util.getAvailableKits(p);
					if (s.getPage() + 1.0 < playerkits.size() / 21.0) {
						selects.remove(i);
						Selection neu = Util.getKitSelection(p, s.getPage() + 1);
						p.openInventory(neu.getInventory());
						selects.add(neu);
					}
				} else {
					int row = event.getRawSlot() / 9;
					int column = event.getRawSlot() % 9;
					if (row > 0 && row < 4 && column % 8 != 0) {
						if (in.getItem(event.getRawSlot()) != null) {
							selects.remove(i);
							ArrayList<Kit> playerkits = Util.getAvailableKits(p);
							int rowaddition = (row - 1) * 2;
							int kitnumber = s.getPage() * 21 + event.getRawSlot() - 10 - rowaddition;
							Kit k = playerkits.get(kitnumber);
							Confirm c = Util.getKitConfirmation(p, k);
							p.openInventory(c.getInventory());
							confirms.add(c);
						}
					}
				}
				event.setCancelled(true);
			}
		}
		for (int i = 0; i < confirms.size(); i++) {
			Confirm c = confirms.get(i);
			if (c.getInventory().equals(in)) {
				Player p = (Player) c.getInventory().getHolder();
				if (event.getRawSlot() == 24 || event.getRawSlot() == 25 || event.getRawSlot() == 33
						|| event.getRawSlot() == 34) {
					Util.addKit(p, c.getKit());
					c.getKit().giveKit(p);
					p.closeInventory();
				} else if (event.getRawSlot() == 53) {
					confirms.remove(i);
					Selection s = Util.getKitSelection(p, 0);
					selects.add(s);
					p.openInventory(s.getInventory());
				}
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory in = event.getInventory();
		ArrayList<Selection> selects = KitPlugin.getSelectionInventories();
		ArrayList<Confirm> confirms = KitPlugin.getConfirmationInventories();
		for (int i = 0; i < selects.size(); i++) {
			Selection s = selects.get(i);
			if (s.getInventory().equals(in)) {
				selects.remove(i);
			}
		}
		for (int i = 0; i < confirms.size(); i++) {
			Confirm c = confirms.get(i);
			if (c.getInventory().equals(in)) {
				confirms.remove(i);
			}
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Kit k = Util.getKit(p);
		if (k != null) {
			k.handleRightclick(p);
		}
	}
}
