package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.flower.AbstractFlower
import azmalent.backportedflora.common.world.*
import net.minecraftforge.fml.common.registry.GameRegistry

object ModWorldgen {
    private fun registerOverworldFlowerGen(flower: AbstractFlower) {
        GameRegistry.registerWorldGenerator(WorldGenOverworldFlowers(flower), 0)
    }

    private fun registerNetherFlowerGen(flower: AbstractFlower) {
        GameRegistry.registerWorldGenerator(WorldGenNetherFlowers(flower), 0)
    }

    fun register() {
        if (ModConfig.Seagrass.enabled) GameRegistry.registerWorldGenerator(WorldGenSeagrass(), 0)

        GameRegistry.registerWorldGenerator(WorldGenRivergrass(), 0)
        GameRegistry.registerWorldGenerator(WorldGenTallGrass(), 0)
        GameRegistry.registerWorldGenerator(WorldGenFerns(), 0)

        GameRegistry.registerWorldGenerator(WorldGenCattail(), 0)

        if (ModConfig.Kelp.enabled) GameRegistry.registerWorldGenerator(WorldGenKelp(), 0)

        if (ModConfig.Cornflower.enabled) registerOverworldFlowerGen(ModBlocks.blockCornflower)
        if (ModConfig.LilyOfTheValley.enabled) registerOverworldFlowerGen(ModBlocks.blockLilyOfTheValley)
        if (ModConfig.WitherRose.enabled) registerNetherFlowerGen(ModBlocks.blockWitherRose)
    }
}