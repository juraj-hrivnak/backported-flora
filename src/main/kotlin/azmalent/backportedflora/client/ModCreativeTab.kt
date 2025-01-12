package azmalent.backportedflora.client

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

class ModCreativeTab : CreativeTabs(BackportedFlora.MODID) {
    override fun createIcon(): ItemStack {
        return ItemStack(ModItems.itemModIcon)
    }

    override fun hasSearchBar(): Boolean {
        return false
    }
}