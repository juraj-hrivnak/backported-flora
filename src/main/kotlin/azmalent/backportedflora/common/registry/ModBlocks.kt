package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.BlockDriedKelp
import azmalent.backportedflora.common.block.crop.BlockCattail
import azmalent.backportedflora.common.block.flower.BlockCornflower
import azmalent.backportedflora.common.block.flower.BlockLilyOfTheValley
import azmalent.backportedflora.common.block.flower.BlockWitherRose
import azmalent.backportedflora.common.block.grass.BlockGrass
import azmalent.backportedflora.common.block.plant.freshwater.BlockRivergrass
import azmalent.backportedflora.common.block.plant.saltwater.BlockKelp
import azmalent.backportedflora.common.block.plant.saltwater.BlockSeagrass
import azmalent.backportedflora.common.block.tallgrass.BlockTallGrass
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

object ModBlocks {

    val blockSeagrass = BlockSeagrass()
    val blockRivergrass = BlockRivergrass()
    val blockKelp = BlockKelp()
    val blockLilyOfTheValley = BlockLilyOfTheValley()
    val blockCornflower = BlockCornflower()
    val blockWitherRose = BlockWitherRose()
    val blockDriedKelp = BlockDriedKelp()
    val blockCattail = BlockCattail()
    val blockTallGrass = BlockTallGrass()
    val blockGrass = BlockGrass()

    fun register(registry: IForgeRegistry<Block>) {
        if (ModConfig.Seagrass.enabled) registry.register(blockSeagrass)

        registry.register(blockRivergrass)
        registry.register(blockCattail)
        registry.register(blockTallGrass)
        registry.register(blockGrass)


        if (ModConfig.Kelp.enabled) {
            registry.register(blockKelp)
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(blockDriedKelp)
            }
        }

        if (ModConfig.Cornflower.enabled) registry.register(blockCornflower)
        if (ModConfig.LilyOfTheValley.enabled) registry.register(blockLilyOfTheValley)
        if (ModConfig.WitherRose.enabled) registry.register(blockWitherRose)
    }

    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seagrass.enabled) registry.register(blockSeagrass.createItemBlock())

        registry.register(blockRivergrass.createItemBlock())
        registry.register(blockTallGrass.createItemBlock())
        registry.register(blockGrass.createItemBlock())


        if (ModConfig.Kelp.enabled) {
            registry.register(blockKelp.createItemBlock())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(blockDriedKelp.createItemBlock())
            }
        }


        if (ModConfig.Cornflower.enabled) registry.register(blockCornflower.createItemBlock())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(blockLilyOfTheValley.createItemBlock())
        if (ModConfig.WitherRose.enabled) registry.register(blockWitherRose.createItemBlock())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seagrass.enabled) blockSeagrass.registerItemModel()

        blockRivergrass.registerItemModel()
        blockTallGrass.registerItemModel()
        blockGrass.registerItemModel()

        if (ModConfig.Kelp.enabled) {
            blockKelp.registerItemModel()
            if (ModConfig.Kelp.driedKelpEnabled) {
                blockDriedKelp.registerItemModel()
            }
        }

        if (ModConfig.Cornflower.enabled) blockCornflower.registerItemModel()
        if (ModConfig.LilyOfTheValley.enabled) blockLilyOfTheValley.registerItemModel()
        if (ModConfig.WitherRose.enabled) blockWitherRose.registerItemModel()
    }

}