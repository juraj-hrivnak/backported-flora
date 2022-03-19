package azmalent.backportedflora.common.block.crop

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.registry.ModItems
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.item.Item

class BlockCattail : AbstractWaterCropBlock(NAME) {

    companion object {
        const val NAME = "cattail"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        val AGE: PropertyInteger = PropertyInteger.create("age", 0, 3)
    }

    init {
        defaultState = blockState.baseState
            .withProperty(AGE, 0)
    }

    public override fun getAgeProperty(): PropertyInteger {
        return AGE
    }

    override fun getMaxAge(): Int {
        return 3
    }

    override fun getSeed(): Item {
        return ModItems.itemBlockCattail
    }

    override fun getCrop(): Item {
        return ModItems.itemBlockCattail
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, AGE)
    }

}

class ItemBlockCattail : AbstractWaterCropItemBlock(BlockCattail.NAME, ModBlocks.blockCattail)
