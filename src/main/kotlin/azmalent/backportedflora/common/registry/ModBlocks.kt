package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.block.BlockCattail
import azmalent.backportedflora.common.block.BlockDriedKelp
import azmalent.backportedflora.common.block.ItemBlockCattail
import azmalent.backportedflora.common.block.flower.BlockCornflower
import azmalent.backportedflora.common.block.flower.BlockLilyOfTheValley
import azmalent.backportedflora.common.block.flower.BlockWitherRose
import azmalent.backportedflora.common.block.plant.freshwater.BlockRivergrass
import azmalent.backportedflora.common.block.plant.saltwater.BlockKelp
import azmalent.backportedflora.common.block.plant.saltwater.BlockSeagrass
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Loader
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
    val itemBlockCattail = ItemBlockCattail()

    fun register(registry: IForgeRegistry<Block>) {
        if (ModConfig.Seagrass.enabled) registry.register(blockSeagrass)

        if (Loader.isModLoaded("simpledifficulty")) {
            registry.register(blockRivergrass)
        }

        if (ModConfig.Kelp.enabled) {
            registry.register(blockKelp)
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(blockDriedKelp)
            }
        }

        registry.register(blockCattail)

        if (ModConfig.Cornflower.enabled) registry.register(blockCornflower)
        if (ModConfig.LilyOfTheValley.enabled) registry.register(blockLilyOfTheValley)
        if (ModConfig.WitherRose.enabled) registry.register(blockWitherRose)
    }

    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        if (ModConfig.Seagrass.enabled) registry.register(blockSeagrass.createItemBlock())

        if (Loader.isModLoaded("simpledifficulty")) {
            registry.register(blockRivergrass.createItemBlock())
        }

        if (ModConfig.Kelp.enabled) {
            registry.register(blockKelp.createItemBlock())
            if (ModConfig.Kelp.driedKelpEnabled) {
                registry.register(blockDriedKelp.createItemBlock())
            }
        }

        registry.register(itemBlockCattail)

        if (ModConfig.Cornflower.enabled) registry.register(blockCornflower.createItemBlock())
        if (ModConfig.LilyOfTheValley.enabled) registry.register(blockLilyOfTheValley.createItemBlock())
        if (ModConfig.WitherRose.enabled) registry.register(blockWitherRose.createItemBlock())
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        if (ModConfig.Seagrass.enabled) blockSeagrass.registerItemModel()

        if (Loader.isModLoaded("simpledifficulty")) {
            blockRivergrass.registerItemModel()
        }

        if (ModConfig.Kelp.enabled) {
            blockKelp.registerItemModel()
            if (ModConfig.Kelp.driedKelpEnabled) {
                blockDriedKelp.registerItemModel()
            }
        }

        itemBlockCattail.registerItemModel()

        if (ModConfig.Cornflower.enabled) blockCornflower.registerItemModel()
        if (ModConfig.LilyOfTheValley.enabled) blockLilyOfTheValley.registerItemModel()
        if (ModConfig.WitherRose.enabled) blockWitherRose.registerItemModel()
    }
}