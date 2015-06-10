package anonymous;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	
	public HashMap<Player, Player> check = new HashMap<Player, Player>();
	public HashMap<Player, ItemStack[]> contents = new HashMap<Player, ItemStack[]>();
	public HashMap<Player, ItemStack[]> armor = new HashMap<Player, ItemStack[]>();
	public int task;
	public int fases = getConfig().getInt("fases");
	public double damage = getConfig().getDouble("dano");
	
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}
	
	public void check(Player tester, Player sender){
		if(inCheck(sender) || inCheck(tester)){
			return;
		}
		addCheck(tester, sender);
		inventoryManager(tester, false);
		
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				
			}
		}, 0L, 20L);
	}
	
	public Player addCheck(Player tester, Player sender){
		return check.put(tester, sender);
	}
	
	public Player removeCheck(Player player){
		return check.remove(player);
	}
	
	public boolean inCheck(Player player){
		if(check.containsKey(player) || check.containsValue(player)){
			return true;
		}
		return false;
	}
	
	public int getRandom(Random r, int min, int max) {
	    return r.nextInt((max - min) + 1) + min;
	}
	
	public void cancelTask(int task){
		Bukkit.getServer().getScheduler().cancelTask(task);
	}
	
	public void inventoryManager(Player player, boolean stats){
		if(!stats){
			if(player.getInventory().getContents()!= null){
				contents.put(player, player.getInventory().getContents());
			}
			if(player.getInventory().getArmorContents() != null){
				armor.put(player, player.getInventory().getArmorContents());
			}
			return;
		}
		if(stats){
			if(contents.get(player) != null){
				ItemStack[] content = contents.get(player);
				player.getInventory().setContents(content);
				if(armor.get(player) != null){
					ItemStack[] armors = armor.get(player);
					player.getInventory().setArmorContents(armors);
				}
			}
			return;
		}
	}
	
}
