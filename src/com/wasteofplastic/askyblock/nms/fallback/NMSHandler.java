/*******************************************************************************
 * This file is part of ASkyBlock.
 *
 *     ASkyBlock is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ASkyBlock is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ASkyBlock.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package com.wasteofplastic.askyblock.nms.fallback;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.wasteofplastic.askyblock.nms.NMSAbstraction;
import com.wasteofplastic.org.jnbt.CompoundTag;
import com.wasteofplastic.org.jnbt.StringTag;
import com.wasteofplastic.org.jnbt.Tag;

/**
 * @author ben
 *
 */
public class NMSHandler implements NMSAbstraction {

    /* (non-Javadoc)
     * @see com.wasteofplastic.askyblock.nms.NMSAbstraction#setBlockSuperFast(org.bukkit.block.Block, org.bukkit.Material)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setBlockSuperFast(Block b, int blockId, byte data, boolean applyPhysics) {
        b.setTypeIdAndData(blockId, data, applyPhysics);
    }

    @Override
    public ItemStack setBook(Tag item) {
        Bukkit.getLogger().warning("Written books in schematics not supported with this version of server");
        return new ItemStack(Material.WRITTEN_BOOK);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setFlowerPotBlock(Block block, ItemStack itemStack) {
        block.setTypeIdAndData(itemStack.getTypeId(), itemStack.getData().getData(), false);

    }

    @Override
    public boolean isPotion(ItemStack item) {
        return item.getType().equals(Material.POTION) && item.getDurability() != 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack setPotion(Material itemMaterial, Tag itemTags,
            ItemStack chestItem) {
        // Try some backwards compatibility with new 1.9 schematics
        Map<String,Tag> contents = (Map<String,Tag>) ((CompoundTag) itemTags).getValue().get("tag").getValue();
        StringTag stringTag = ((StringTag)contents.get("Potion"));
        if (stringTag != null) {
            String tag = stringTag.getValue().replace("minecraft:", "");
            PotionType type = null;
            boolean strong = tag.contains("strong");
            boolean _long = tag.contains("long");
            //Bukkit.getLogger().info("tag = " + tag);
            switch (tag) {
                case "fire_resistance":
                case "long_fire_resistance":
                    type = PotionType.FIRE_RESISTANCE;
                    break;
                case "harming":
                case "strong_harming":
                    type = PotionType.INSTANT_DAMAGE;
                    break;
                case "healing":
                case "strong_healing":
                    type = PotionType.INSTANT_HEAL;
                    break;
                case "invisibility":
                case "long_invisibility":
                    type = PotionType.INVISIBILITY;
                    break;
                case "leaping":
                case "long_leaping":
                case "strong_leaping":
                    type = PotionType.JUMP;
                    break;
                case "night_vision":
                case "long_night_vision":
                    type = PotionType.NIGHT_VISION;
                    break;
                case "poison":
                case "long_poison":
                case "strong_poison":
                    type = PotionType.POISON;
                    break;
                case "regeneration":
                case "long_regeneration":
                case "strong_regeneration":
                    type = PotionType.REGEN;
                    break;
                case "slowness":
                case "long_slowness":
                    type = PotionType.SLOWNESS;
                    break;
                case "swiftness":
                case "long_swiftness":
                case "strong_swiftness":
                    type = PotionType.SPEED;
                    break;
                case "strength":
                case "long_strength":
                case "strong_strength":
                    type = PotionType.STRENGTH;
                    break;
                case "water_breathing":
                case "long_water_breathing":
                    type = PotionType.WATER_BREATHING;
                    break;
                case "water":
                    type = PotionType.WATER;
                    break;
                case "weakness":
                case "long_weakness":
                    type = PotionType.WEAKNESS;
                    break;
                default:
                    return chestItem;
            }
            Potion potion = new Potion(type);
            potion.setHasExtendedDuration(_long);
            potion.setLevel(strong ? 2 : 1);
            chestItem = potion.toItemStack(chestItem.getAmount());
        }

        return chestItem;
    }

    /* (non-Javadoc)
     * @see com.wasteofplastic.askyblock.nms.NMSAbstraction#getSpawnEgg(org.bukkit.entity.EntityType, int)
     */
    @Override
    public ItemStack getSpawnEgg(EntityType type, int amount) {
        SpawnEgg egg = new SpawnEgg(type);
        return egg.toItemStack(amount);
    }
}
