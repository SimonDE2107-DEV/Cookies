/* Copyright 2016 Acquized
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package de.simonde2107.cookies.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {

    ItemStack itemStack;
    ItemMeta itemMeta;
    Material material;
    int amount = 1;
    short durability = 0;
    Map<Enchantment, Integer> enchantments = new HashMap<>();
    String displayName;
    List<String> lores = new ArrayList<>();
    List<ItemFlag> itemFlags = new ArrayList<>();


    public ItemBuilder(Material material, int amount, String displayName) {
        if (material == null) material = Material.AIR;
        this.itemStack = new ItemStack(material, amount);
        this.material = material;
        this.amount = amount;
        this.displayName = displayName;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, int level) {
        enchantments.put(enchant, level);
        return this;
    }

    public ItemBuilder lore(String line) {
        lores.add(line);
        return this;
    }

    public ItemBuilder lore(String... lines) {
        for (String line : lines) {
            lore(line);
        }
        return this;
    }

    public ItemBuilder lore(String line, int index) {
        lores.set(index, line);
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        itemFlags.add(flag);
        return this;
    }

    public ItemBuilder flag(List<ItemFlag> flags) {
        this.itemFlags = flags;
        return this;
    }

    public ItemBuilder unbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder glow() {
        enchant(material != Material.BOW ? Enchantment.INFINITY : Enchantment.LUCK_OF_THE_SEA, 10);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder skullOwner(String user) {
        if ((material == Material.PLAYER_HEAD)) {
            SkullMeta smeta = (SkullMeta) itemMeta;
            smeta.setOwner(user);
            itemMeta = smeta;
        }
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmount() {
        return amount;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public short getDurability() {
        return durability;
    }

    public List<String> getLores() {
        return lores;
    }

    public List<ItemFlag> getFlags() {
        return itemFlags;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemMeta getMeta() {
        return itemMeta;
    }

    public ItemStack build() {
        itemStack.setType(material);
        itemStack.setAmount(amount);
        itemStack.setDurability(durability);
        itemMeta = itemStack.getItemMeta();
        if (!enchantments.isEmpty()) {
            itemStack.addUnsafeEnchantments(enchantments);
        }
        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }
        if (!lores.isEmpty()) {
            itemMeta.setLore(lores);
        }
        if (!itemFlags.isEmpty()) {
            for (ItemFlag f : itemFlags) {
                itemMeta.addItemFlags(f);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
