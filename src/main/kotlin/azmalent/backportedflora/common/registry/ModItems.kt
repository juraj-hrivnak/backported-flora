package azmalent.backportedflora.common.registry

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.item.ItemDriedKelp
import azmalent.backportedflora.common.item.ItemKelpSoup
import azmalent.backportedflora.common.item.ItemModIcon
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.registries.IForgeRegistry

object ModItems {

    val itemModIcon = ItemModIcon()
    val itemKelpSoup = ItemKelpSoup()
    val itemDriedKelp = ItemDriedKelp()

    fun register(registry: IForgeRegistry<Item>) {
        registry.register(itemModIcon)

        if (ModConfig.Kelp.enabled) {
            if (ModConfig.Kelp.kelpSoupEnabled) registry.register(itemKelpSoup)
            if (ModConfig.Kelp.driedKelpEnabled) registry.register(itemDriedKelp)
        }
    }

    @SideOnly(Side.CLIENT)
    fun registerModels() {
        itemModIcon.registerItemModel()

        if (ModConfig.Kelp.enabled) {
            if (ModConfig.Kelp.kelpSoupEnabled) itemKelpSoup.registerItemModel()
            if (ModConfig.Kelp.driedKelpEnabled) itemDriedKelp.registerItemModel()
        }
    }

    fun initOreDictionary() {
        if (ModConfig.Cornflower.enabled) OreDictionary.registerOre("allFlowers", ModBlocks.blockCornflower)
        if (ModConfig.LilyOfTheValley.enabled) OreDictionary.registerOre("allFlowers", ModBlocks.blockLilyOfTheValley)
        if (ModConfig.WitherRose.enabled) OreDictionary.registerOre("allFlowers", ModBlocks.blockWitherRose)
        if (ModConfig.Kelp.enabled) OreDictionary.registerOre("cropSeaweed", ModBlocks.blockKelp)
    }
}