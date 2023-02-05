package com.github.yj0524.survival

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object SurvivalItem {
    val greenJuice = ItemStack(Material.SUSPICIOUS_STEW).apply {
        itemMeta = itemMeta.apply {
            displayName(text("녹즙").color(TextColor.color(0x008000)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false))
        }
    }

    val vaccine = ItemStack(Material.FIREWORK_ROCKET).apply {
        itemMeta = itemMeta.apply {
            displayName(text("백신").color(TextColor.color(0x4FAEC9)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false))
        }
    }

    val hyperVaccine = ItemStack(Material.TOTEM_OF_UNDYING).apply {
        itemMeta = itemMeta.apply {
            displayName(text("하이퍼 백신").color(TextColor.color(0xFF0000)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false))
        }
    }

    val wandSpector = ItemStack(Material.EMERALD)

    val wandSummon = ItemStack(Material.DIAMOND)
}