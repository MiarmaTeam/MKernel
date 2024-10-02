package dev.gallardo.miarmacore.events;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.CustomConfigManager;
import dev.gallardo.miarmacore.common.minecraft.DisposalInventory;
import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.common.minecraft.MinepacksAccessor;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import static dev.gallardo.miarmacore.util.Constants.*;

public class EventListener {
	public static void onEnable() {
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onInventoryOpen(InventoryOpenEvent event) {
				if (event.getInventory().equals(GlobalChest.getInv())) {
					GlobalChest.loadChest();
				}
			}

			@EventHandler
			public void onInventoryClose(InventoryCloseEvent event) {
				if (event.getInventory().equals(GlobalChest.getInv())) {
					GlobalChest.saveChest();
				} else if(event.getInventory().equals(DisposalInventory.getInv())) {
					DisposalInventory.getInv().clear();				}
			}

			@EventHandler
			public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
				if (MiarmaCore.CONFIG.getBoolean("config.modules.deathTitle")) {
					Player player = event.getEntity();
					Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
					for (Player p : players) {
						p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 1);
						p.sendTitle(Utils.colorCodeParser(
								Utils.placeholderParser(
										MiarmaCore.CONFIG.getString("language.titles.subtitles.death"),
										List.of("%player%"), List.of(player.getName()))),
								"", 30, 30, 30);
					}
				}

				if (MiarmaCore.CONFIG.getBoolean("config.modules.recoverInventory")) {
					Player player = event.getEntity();
					Utils.saveInventory(event.getEntity());
					event.getDrops().clear();
					event.setKeepInventory(true);
					player.getInventory().setArmorContents(null);
					player.getInventory().clear();
					player.updateInventory();

				}
			}

			@EventHandler
			public void onPlayerJoin(PlayerJoinEvent event) {
				Player player = event.getPlayer();
				if(MiarmaCore.CONFIG.getBoolean("config.modules.spawnAtLobby")) {
					if(Bukkit.getServer().getWorlds().stream()
							.map(World::getName)
							.map(String::toLowerCase)
							.anyMatch(w -> w.contains(MiarmaCore.CONFIG.getString("config.worlds.lobby.name")))) {
						player.teleport(
								new Location(
									Bukkit.getWorld(MiarmaCore.CONFIG.getString("config.worlds.lobby.name")),
									MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.coords.x"),
									MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.coords.y"),
									MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.coords.z"),
									MiarmaCore.CONFIG.getConfig().getFloat("config.worlds.lobby.coords.yaw"),
									MiarmaCore.CONFIG.getConfig().getFloat("config.worlds.lobby.coords.pitch")
								)
						);
					} else {
						MiarmaCore.getPlugin(MiarmaCore.class).getLogger()
								.log(Level.SEVERE, MiarmaCore.CONFIG.getString("language.errors.lobbyDoesNotExist"));
					}
				}
				if (MiarmaCore.CONFIG.getBoolean("config.modules.joinTitle")) {

					Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
					for (Player p : players) {
						p.sendTitle(
								Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.titles.titleFormat"))
								+ player.getName(),
								Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.titles.subtitles.join")), 30,
								30, 30);
					}
				}
			}

			@EventHandler
			public void onPlayerLeave(PlayerQuitEvent event) {
				if (MiarmaCore.CONFIG.getBoolean("config.modules.leaveTitle")) {
					Player player = event.getPlayer();
					Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
					for (Player p : players) {
						p.sendTitle(
								Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.titles.titleFormat"))
								+ player.getName(),
								Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.titles.subtitles.leave")), 30,
								30, 30);
					}
				}
			}

			@EventHandler
			public void onRightClick(PlayerInteractEvent event) {
				if (!MiarmaCore.CONFIG.getBoolean("config.modules.harvestOnRightClick")) {
		            return;
		        }
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		            Block block = event.getClickedBlock();
		            Player player = event.getPlayer();
		            if (block != null) {
		                BlockEventHelper helper = BlockEventHelper.of(player, block);
		                Material blockType = block.getType();
		                switch (blockType) {
		                    case WHEAT: helper.handleWheat(); break;
		                    case POTATOES: helper.handlePotatoes(); break;
		                    case CARROTS: helper.handleCarrots(); break;
		                    case BEETROOTS: helper.handleBeetroots(); break;
		                    case COCOA: helper.handleCocoa(); break;
		                    case TORCHFLOWER_CROP: helper.handleTorchflower(); break;
		                    case PITCHER_CROP: helper.handlePitcher(); break;
		                    default: break;
		                }
		            }
		        }
			}

			@EventHandler
			public void onEntityRightClick(PlayerInteractEntityEvent event) {
				Entity e = event.getRightClicked();
				EquipmentSlot h = event.getHand();
				EntityEventHelper helper = EntityEventHelper.of(event);
				ItemStack item = event.getPlayer().getItemInHand();
				if(item == null || item.getType().equals(Material.AIR) || item.getAmount() == 0) {
					return;
				}
				NBTItem nbtItemInHand = new NBTItem(item);
				String specialType = nbtItemInHand.getString("specialItem");
				
				if(h.equals(EquipmentSlot.HAND)) {
					switch(e.getType()) {
						case PIG: 
							if("SCISSORS".equals(specialType)) {
								helper.handleScissorsOnPig();
							}
							break;
						case COW: 
							if("SCISSORS".equals(specialType)) {
								helper.handleScissorsOnCow();
							}
							break;
						case CREEPER:
							if("SCISSORS".equals(specialType)) {
								helper.handleScissorsOnCreeper();
							}
							break;
						case ZOMBIE: 
							if("SCISSORS".equals(specialType)) {
								helper.handleScissorsOnZombie();
							}
							break;
						case SKELETON:
							if(item.getType().equals(Material.ROTTEN_FLESH)) {
								helper.handleRottenFleshOnSkeleton();
							}
							break;
						case PILLAGER:
							if(item.getType().equals(Material.TOTEM_OF_UNDYING)) {
								helper.handleTotemOnPillager();
							}
							break;
						default: return;
					}	
				}
			}

			@EventHandler
			public void onEntityLeftClick(EntityDamageByEntityEvent event) {
				if (event.getDamager() instanceof Player player) {
                    ItemStack itemStack = player.getItemInHand();
					Material material = itemStack.getType();
					if (material.equals(Material.STICK) && 
							new NBTItem(itemStack).getString("specialItem").equals("ADMIN_STICK") &&
						event.getEntity() instanceof LivingEntity) {
						((LivingEntity) event.getEntity()).setHealth(0);
					}
				}
			}

			@EventHandler
			public void onCampfireCook(BlockCookEvent event) {
				if (event.getBlock().getType() == Material.CAMPFIRE) {
					if (event.getSource().getType() == Material.ROTTEN_FLESH) {
						event.setResult(new ItemStack(Utils.getMaterialWithProb()));
					}
				}
			}
			
			@EventHandler
			public void onBlockedWorldEnter(PlayerChangedWorldEvent event) {
				Player player = event.getPlayer();
				String world = player.getWorld().getName();

				CustomConfigManager worldBlockerConfigManager = new CustomConfigManager(MiarmaCore.PLUGIN, "blockedWorlds.yml");
				worldBlockerConfigManager.reloadConfig();

				List<String> blockedWorlds = worldBlockerConfigManager.getConfig().getStringList("blockedWorlds");

				if (blockedWorlds.contains(world)) {
					Location loc = LocationTracker.getPlayerLocation(player);
					player.teleport(new Location(loc.getWorld(), loc.getX()-2, loc.getY(), loc.getZ()-2));

					Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.worldIsBlocked"), player, true);
				}
			}
			
			@EventHandler
			public void onChatMessage(AsyncPlayerChatEvent event) {
				if(MiarmaCore.CONFIG.getBoolean("config.modules.chatFormat")) {
					if(event.getPlayer().hasPermission(MiarmaCore.CONFIG.getString("config.permissions.chatFormat"))) {
						event.setMessage(Utils.colorCodeParser(event.getMessage()));
					}
				}
			}
						
			@EventHandler
			public void onAdminMessage(AsyncPlayerChatEvent event) {
				if(MiarmaCore.CONFIG.getBoolean("config.modules.adminChat")) {
					if(event.getMessage().startsWith("#") && event.getPlayer().hasPermission(
							MiarmaCore.CONFIG.getString("config.permissions.adminChat")
					)) {
						String msg = event.getMessage().replace("#",
								Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.adminPrefix"))+" "+
									ChatColor.GRAY+event.getPlayer().getName()+ChatColor.AQUA+":"+ChatColor.RESET+" ")
										.replace("  ", " ");
						event.setCancelled(true);
						for(Player p:Bukkit.getOnlinePlayers()) {
							if(p.hasPermission(
									MiarmaCore.CONFIG.getString("config.permissions.adminChat")
							)) {
								p.sendRawMessage(msg);
							}
						}
					} else if(event.getMessage().startsWith("#") && !event.getPlayer().hasPermission(
							MiarmaCore.CONFIG.getString("config.permissions.adminChat")
					)) {
						event.setCancelled(true);
						Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.noPermission"), event.getPlayer(), true);
					}
				}
			}
			
			@EventHandler
			public void onMention(AsyncPlayerChatEvent event) {
				if(MiarmaCore.CONFIG.getBoolean("config.modules.mentions")) {
					List<String> players = Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
					boolean containsPlayer = false;
					if(event.getPlayer().hasPermission(
							MiarmaCore.CONFIG.getString("config.permissions.mentions")
					)) {
						Player victim = null;
						for(String s:players) {
							if(event.getMessage().contains("@"+s)) {
								victim = Bukkit.getServer().getPlayer(s);
								containsPlayer = true;
							}
						}
						if (victim != null && containsPlayer){
							String formattedMention = Utils.colorCodeParser(MiarmaCore.CONFIG.getString("language.mentions.format")+"@"+victim.getName())+ChatColor.RESET;
							event.setMessage(event.getMessage().replace(victim.getName(), formattedMention));

							Utils.sendMessage(MiarmaCore.CONFIG.getString("language.mentions.youWereMentioned"), victim, true,
									true, List.of("%player%"), List.of(event.getPlayer().getName()));

							victim.playSound(victim, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
						}
					}
				}
			}

			@EventHandler
		    public void onBlockPlace(BlockPlaceEvent event) {
		        if (MiarmaCore.CONFIG.getBoolean("config.modules.autoItemRefill")) {
		            ItemStack item = event.getItemInHand();
		            Material material = event.getBlockPlaced().getType();
		            Inventory playerInventory = event.getPlayer().getInventory();
		            Inventory playerBackpack;

		            if (item.getAmount() == 1) {
		                int itemCountInInventory = Utils.getItemCount(playerInventory, material);

		                if (itemCountInInventory > 1) {
		                    Utils.refillItem(event.getPlayer(), material, event.getHand());
		                } else {
							if(MinepacksAccessor.isLoaded()) {
								playerBackpack = MinepacksAccessor.getPlayerBackpackInventory(event.getPlayer());
								if (!(playerBackpack == null) && !playerBackpack.isEmpty()) {
									for (ItemStack i : playerBackpack.getContents()) {
										if (i != null && i.getType().equals(material)) {
											Utils.refillItemFromMinepack(event.getPlayer(), material, event.getHand());
											break;
										}
									}
								}
							}

                        }
		            }
		        }
		    }
			
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event) {
				Player p = event.getPlayer();
				ItemStack item = p.getItemInHand();
				
				if(item == null)
					return;
				if(item.getType().equals(Material.AIR))
					return;
				if(item.getAmount() == 0)
					return;
				
				NBTItem nbtItemInHand = new NBTItem(item);
				PlayerInventory inv = p.getInventory();
				Block block = event.getBlock();
				String specialType = nbtItemInHand.getString("specialItem");
				
				if(specialType != null && specialType.equals("SPAWNER_BREAKER")) {
					int prob = (int) (Math.random() * 100);
					if(prob > MiarmaCore.CONFIG.getInt("config.values.spawnerBreakerProbability")) {
						event.setCancelled(true);
						for(int i = 0; i < inv.getSize(); i++) {
							ItemStack invItem = inv.getItem(i);
			                if (invItem != null && invItem.getType() != Material.AIR && invItem.getAmount() != 0) {
			                    NBTItem nbtItem = new NBTItem(invItem);
			                    if (specialType.equals(nbtItem.getString("specialItem"))) {
			                        p.playSound(p, Sound.ENTITY_ITEM_BREAK, 1, 1);
			                        inv.clear(p.getInventory().getHeldItemSlot());
			                    }
			                }
						}
					} else {
						BlockState state = block.getState();
		                if (state instanceof CreatureSpawner) {
		                    CreatureSpawner spawner = (CreatureSpawner) state;
		                    ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
		                    BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();
		                    meta.setBlockState(spawner);
		                    spawnerItem.setItemMeta(meta);
		                    block.getWorld().dropItem(block.getLocation(), spawnerItem);
		                    block.setType(Material.AIR); // Remove the spawner block
		                }
					}
				}
				
			}
			
			@EventHandler
			public void onItemBreak(PlayerItemBreakEvent event) {
				if(MiarmaCore.CONFIG.getBoolean("config.modules.autoItemRefill")) {
					ItemStack item = event.getBrokenItem();
					Material material = item.getType();
					PlayerInventory playerInventory = event.getPlayer().getInventory();
		            Inventory playerBackpack;
		            EquipmentSlot hand = (playerInventory.getItemInMainHand().getType().equals(Material.AIR) || 
		                      !playerInventory.getItemInMainHand().getType().equals(item.getType())) ? 
		                      EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;

		            if(item.getAmount() == 1) {
		            	int itemCountInInventory = Utils.getItemCount(playerInventory, material);
		            	if (itemCountInInventory > 1) {
		                    Utils.refillItem(event.getPlayer(), material, hand);
		                } else {
							if(MinepacksAccessor.isLoaded()) {
								playerBackpack = MinepacksAccessor.getPlayerBackpackInventory(event.getPlayer());
								if(!(playerBackpack == null) && !playerBackpack.isEmpty()) {
									for (ItemStack i : playerBackpack.getContents()) {
										if (i != null && i.getType().equals(material)) {
											Utils.refillItemFromMinepack(event.getPlayer(), material, hand);
											break;
										}
									}
								}
							}
		                }
		            }
					
				}
			}
			
			@EventHandler
			public void onCampfireCook(CampfireStartEvent event) {
				if(!event.getBlock().getBlockData().getMaterial().equals(Material.SOUL_CAMPFIRE)) {
					 event.setTotalCookTime((int)Math.floor(1.5*event.getTotalCookTime()));
				}
			}
						
			@EventHandler
			public void onEntityDeath(EntityDeathEvent event) {
				Player p = event.getEntity().getKiller();
				if(p == null) {
					return;
				}
				
				ItemStack i = p.getItemInHand();
				if (i == null || !i.hasItemMeta() || i.getItemMeta().getEnchants().isEmpty()) {
			        return; 
			    }
				
				Enchantment ench = i.getEnchantments().entrySet().stream()
			            .map(entry -> entry.getKey())
			            .filter(e -> e == Enchantment.LOOTING)
			            .findFirst()
			            .orElse(null);
				if(ench == null) {
					return;
				}
				
				if (event.getEntityType().equals(EntityType.ZOMBIE) ||
			            event.getEntityType().equals(EntityType.ZOMBIE_VILLAGER)) {
			        int prob = (int) (Math.random() * 100);
			        if(p.getItemInHand().getEnchantments().entrySet().stream()
			        		.map(k -> k.getKey())
			        		.anyMatch(e -> e.equals(ench))) {
			        	if (prob < 70) {
				            event.getEntity().getWorld()
				                .dropItem(event.getEntity().getLocation(), new ItemStack(Material.ZOMBIE_HEAD,1));
				        }
			        } else {
			        	if (prob < 50) {
				            event.getEntity().getWorld()
				                .dropItem(event.getEntity().getLocation(), new ItemStack(Material.ZOMBIE_HEAD,1));
				        }
			        }
			    }
			}
			
			@EventHandler
			public void onPotionSplash(PotionSplashEvent event) {
				NBTItem nbtItem = new NBTItem(event.getPotion().getItem());
				String specialItem = nbtItem.getString("specialItem");
				if("ZOMBIFICATION_POTION".equals(specialItem)) {
					Collection<LivingEntity> entities = event.getAffectedEntities();
					for(LivingEntity le : entities) {
						if(le instanceof Villager v) {
							int r = (int) (Math.random() * 100.);
							if(r >= 50) {
								v.zombify();
							} else {
								v.setHealth(0.);
							}
						}
						if(le instanceof Player p) {
							p.addPotionEffect(
								new PotionEffect(PotionEffectType.POISON, 30, 2)
							);
						}
						
					}
				}
			}
			
			@EventHandler
			public void onBookWrite(PlayerEditBookEvent event) {
				if(MiarmaCore.CONFIG.getBoolean("config.modules.bookColors")) {
					var bookMeta = event.getNewBookMeta();
			        var pages = bookMeta.getPages();
			        var newPages = new ArrayList<String>();
			        for (String page : pages) {
			            newPages.add(page.replace('&', '§'));
			        }
			        bookMeta.setPages(newPages);
			        event.setNewBookMeta(bookMeta);
				}
			}

		}, MiarmaCore.PLUGIN);
	}

	
}
