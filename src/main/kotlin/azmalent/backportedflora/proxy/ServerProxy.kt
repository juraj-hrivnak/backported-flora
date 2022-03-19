package azmalent.backportedflora.proxy

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side



@Mod.EventBusSubscriber(Side.SERVER)
class ServerProxy : IProxy {
    override fun preInit(event: FMLPreInitializationEvent) {

    }

    override fun init(event: FMLInitializationEvent) {

    }

    override fun postInit(event: FMLPostInitializationEvent) {

    }

    override fun registerItemRenderer(item: Item, meta: Int, id: String) {
        throw IProxy.WrongSideException("Tried to call ${::registerItemRenderer.name} on server")
    }

    override fun registerItemBlockRenderer(itemBlock: Item, meta: Int, id: String) {
        throw IProxy.WrongSideException("Tried to call ${::registerItemBlockRenderer.name} on server")
    }

    override fun registerBlockColourHandlers(block: Block, event: ColorHandlerEvent.Block) {
        throw IProxy.WrongSideException("Tried to call ${::registerBlockColourHandlers.name} on server")
    }

    override fun registerItemColourHandlers(item: Item, event: ColorHandlerEvent.Item) {
        throw IProxy.WrongSideException("Tried to call ${::registerItemColourHandlers.name} on server")
    }

}