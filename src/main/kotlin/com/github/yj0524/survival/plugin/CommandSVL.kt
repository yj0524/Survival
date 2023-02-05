package com.github.yj0524.survival.plugin

import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.player
import com.github.monun.kommand.argument.suggestions
import com.github.monun.kommand.argument.target
import com.github.monun.kommand.sendFeedback
import com.github.yj0524.survival.Bio
import com.github.yj0524.survival.SurvivalItem
import com.github.yj0524.survival.survival
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandSVL {
    internal fun register(builder: KommandDispatcherBuilder) {
        builder.register("svl") {
            then("attach") {
                then("player" to target() { it is Player }, "bio" to BioArgument) {
                    executes { context ->
                        attach(context.sender, context.parseArgument("player"), context.parseArgument("bio"))
                    }
                }
            }
            then("vaccine") {
                require { this is Player }
                executes { context ->
                    vaccine(context.sender, listOf(context.sender as Player))
                }
                then("player" to target() {it is Player}) {
                    executes { context ->
                        vaccine(context.sender, context.parseArgument("player"))
                    }
                }
            }
            then("hyperVaccine") {
                require { this is Player }
                executes { context ->
                    hyperVaccine(context.sender, listOf(context.sender as Player))
                }
                then("player" to target() {it is Player}) {
                    executes { context ->
                        hyperVaccine(context.sender, context.parseArgument("player"))
                    }
                }
            }
            then("greenJuice") {
                require { this is Player }
                executes { context ->
                    greenjuice(context.sender, listOf(context.sender as Player))
                }
                then("player" to target() {it is Player}) {
                    executes { context ->
                        greenjuice(context.sender, context.parseArgument("player"))
                    }
                }
            }
            then("resetCoolDown") {
                require { this is Player }
                executes { context ->
                    resetCooldown(context.sender, context.sender as Player)
                }
            }
        }
    }

    private fun attach(sender: CommandSender, players: List<Player>, bio: Bio.Type) {
        for (player in players) {
            player.survival().setBio(bio)
            sender.sendFeedback("${player.name} = ${bio.displayName}")
        }
    }

    private fun vaccine(sender: CommandSender, players: List<Player>) {
        for (player in players) {
            player.inventory.addItem(SurvivalItem.vaccine.clone())
            sender.sendFeedback("백신을 지급했습니다.")
        }
    }

    private fun hyperVaccine(sender: CommandSender, players: List<Player>) {
        for (player in players) {
            player.inventory.addItem(SurvivalItem.hyperVaccine.clone())
            sender.sendFeedback("하이퍼 백신을 지급했습니다.")
        }
    }

    private fun greenjuice(sender: CommandSender, players: List<Player>) {
        for (player in players) {
            player.inventory.addItem(SurvivalItem.greenJuice.clone())
            sender.sendFeedback("녹즙을 지급했습니다.")
        }
    }

    private fun resetCooldown(sender: CommandSender, player: Player) {
        val survival = player.survival()
        val bio = survival.bio

        if (bio is Bio.Zombie) {
            bio.resetCooldown()
        }

        sender.sendFeedback("Reset cooldown ${player.name}")
    }
}

object BioArgument : KommandArgument<Bio.Type> {
    override fun parse(context: KommandContext, param: String): Bio.Type? {
        return Bio.Type.byKey(param)
    }

    override fun listSuggestion(context: KommandContext, target: String): Collection<String> {
        return Bio.Type.values().asList().suggestions(target) { it.key }
    }
}