package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.BlockDriedKelp
import azmalent.backportedflora.common.block.flower.BlockCornflower
import azmalent.backportedflora.common.block.flower.BlockLilyOfTheValley
import azmalent.backportedflora.common.block.flower.BlockWitherRose
import azmalent.backportedflora.common.block.freshwater.BlockRivergrass
import azmalent.backportedflora.common.block.saltwater.BlockKelp
import azmalent.backportedflora.common.block.saltwater.BlockSeagrass
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ModBlocks {
    @ObjectHolder(BlockSeagrass.REGISTRY_NAME)
    lateinit var SEAGRASS: BlockSeagrass

    @ObjectHolder(BlockRivergrass.REGISTRY_NAME)
    lateinit var RIVERGRASS: BlockRivergrass

    @ObjectHolder(BlockKelp.REGISTRY_NAME)
    lateinit var KELP: BlockKelp

    @ObjectHolder(BlockLilyOfTheValley.REGISTRY_NAME)
    lateinit var LILY_OF_THE_VALLEY: BlockLilyOfTheValley

    @ObjectHolder(BlockCornflower.REGISTRY_NAME)
    lateinit var CORNFLOWER: BlockCornflower

    @ObjectHolder(BlockWitherRose.REGISTRY_NAME)
    lateinit var WITHER_ROSE: BlockWitherRose

    @ObjectHolder(BlockDriedKelp.REGISTRY_NAME)
    lateinit var DRIED_KELP_BLOCK: BlockDriedKelp

//    //  Biomes o' Plenty integration:
//    @ObjectHolder(BlockSeaweed.REGISTRY_NAME)
//    lateinit var SEAGRASSBOP: BlockSeaweed

    fun register(registry: IForgeRegistry<Block>) {
        if (ModConfig.Seagrass.enabled) registry.register(BlockSeagrass())

        if (Loader.isModLoaded("simpledifficulty")) {
            registry.register(BlockRivergrass())
        }

//        //  Biomes o' Plenty integration:
//        if (Loader.isModLoaded("biomesoplenty")) {
//            registry.register(BlockSeaweed())
//        }

        if (ModConfig.Kelp.enabled) {
            registry.register(BlockKelp())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(BlockDriedKelp())
            }
        }

        if (ModConfig.Cornflower.enabled) registry.register(BlockCornflower())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(BlockLilyOfTheValley())
        if (ModConfig.WitherRose.enabled) registry.register(BlockWitherRose())
    }

    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seagrass.enabled) registry.register(SEAGRASS.createItemBlock())

        if (Loader.isModLoaded("simpledifficulty")) {
            registry.register(RIVERGRASS.createItemBlock())
        }

//        //  Biomes o' Plenty integration:
//        if (Loader.isModLoaded("biomesoplenty")) {
//            registry.register(SEAGRASSBOP.createItemBlock())
//        }

        if (ModConfig.Kelp.enabled) {
            registry.register(KELP.createItemBlock())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(DRIED_KELP_BLOCK.createItemBlock())
            }
        }

        if (ModConfig.Cornflower.enabled) registry.register(CORNFLOWER.createItemBlock())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(LILY_OF_THE_VALLEY.createItemBlock())
        if (ModConfig.WitherRose.enabled) registry.register(WITHER_ROSE.createItemBlock())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seagrass.enabled) SEAGRASS.registerItemModel()

        if (Loader.isModLoaded("simpledifficulty")) {
            RIVERGRASS.registerItemModel()
        }

//        //  Biomes o' Plenty integration:
//        if (Loader.isModLoaded("biomesoplenty")) {
//            SEAGRASSBOP.registerItemModel()
//        }

        if (ModConfig.Kelp.enabled) {
            KELP.registerItemModel()
            if (ModConfig.Kelp.driedKelpEnabled) {
                DRIED_KELP_BLOCK.registerItemModel()
            }
        }

        if (ModConfig.Cornflower.enabled) CORNFLOWER.registerItemModel()
        if (ModConfig.LilyOfTheValley.enabled) LILY_OF_THE_VALLEY.registerItemModel()
        if (ModConfig.WitherRose.enabled) WITHER_ROSE.registerItemModel()
    }
}