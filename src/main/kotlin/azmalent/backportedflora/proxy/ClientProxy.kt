package azmalent.backportedflora.proxy

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.registry.ModItems
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ColorizerGrass
import net.minecraft.world.IBlockAccess
import net.minecraft.world.biome.BiomeColorHelper
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(Side.CLIENT)
class ClientProxy : IProxy {
    companion object {
        @SubscribeEvent
        @JvmStatic fun registerModels(event: ModelRegistryEvent) {
            BackportedFlora.LOGGER.info("Registering models")
            ModBlocks.registerModels()
            ModItems.registerModels()
        }
    }

    override fun preInit(event: FMLPreInitializationEvent) {

    }

    override fun init(event: FMLInitializationEvent) {

    }

    override fun postInit(event: FMLPostInitializationEvent) {

    }

    override fun registerItemRenderer(item: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(item, meta, ModelResourceLocation(id))
    }

    override fun registerItemBlockRenderer(itemBlock: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(
            itemBlock, meta, ModelResourceLocation(id, "inventory")
        )
    }

    override fun registerBlockColourHandlers(block: Block, event: ColorHandlerEvent.Block) {
        val blockColors: BlockColors = event.blockColors
        val grassColourHandler = IBlockColor(
            fun(_: IBlockState?, blockAccess: IBlockAccess?, pos: BlockPos?, _: Int): Int {
                return if (blockAccess != null && pos != null) {
                    BiomeColorHelper.getGrassColorAtPos(blockAccess, pos)
                } else return ColorizerGrass.getGrassColor(0.5, 1.0)
            }
        )
        blockColors.registerBlockColorHandler(grassColourHandler, block)
    }
}
