package com.github.yj0524.survival.plugin

import com.github.yj0524.survival.Bio
import com.github.yj0524.survival.SurvivalConfig
import com.github.yj0524.survival.Survival
import com.github.monun.tap.fake.FakeEntityServer
import java.io.File
import java.util.logging.Logger

class TickTask(
    private val logger: Logger,
    private val configFile: File,
    private val fakeEntityServerForZombie: FakeEntityServer,
    private val fakeEntityServerForHuman: FakeEntityServer,
    private val survival: Survival
): Runnable {
    private var configFileLastModified = configFile.lastModified()

    override fun run() {
        for (player in survival.players) {
            player.update()
        }

        Bio.SuperZombie.updateTarget()

        fakeEntityServerForZombie.update()
        fakeEntityServerForHuman.update()

        if (configFileLastModified != configFile.lastModified()) {
            SurvivalConfig.load(configFile)
            configFileLastModified = configFile.lastModified()

            survival.players.forEach { it.bio.applyAttribute() }

            logger.info("Config reloaded")
        }
    }
}