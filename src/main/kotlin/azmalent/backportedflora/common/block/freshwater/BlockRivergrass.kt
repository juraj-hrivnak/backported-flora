package azmalent.backportedflora.common.block.freshwater

import azmalent.backportedflora.BackportedFlora
import net.minecraft.block.BlockLiquid.LEVEL
import net.minecraft.block.IGrowable
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.util.IStringSerializable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockRivergrass : AbstractAquaticPlant(NAME), IGrowable {
    enum class RivergrassVariant : IStringSerializable {
        SINGLE, BOTTOM, TOP;

        override fun getName(): String {
            return name.toLowerCase()
        }

        override fun toString(): String {
            return getName()
        }
    }

    companion object {
        const val NAME = "rivergrass"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        val VARIANT: PropertyEnum<RivergrassVariant> = PropertyEnum.create("variant", RivergrassVariant::class.java)
    }

    init {
        defaultState = blockState.baseState
                .withProperty(VARIANT, RivergrassVariant.SINGLE)
                .withProperty(LEVEL, 15)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return 0
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, VARIANT, LEVEL)
    }

    override fun getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState {
        val hasRivergrassBelow = worldIn.getBlockState(pos.down()).block == this
        val hasRivergrassAbove = worldIn.getBlockState(pos.up()).block == this

        return state.withProperty(VARIANT, when {
            hasRivergrassBelow -> RivergrassVariant.TOP
            hasRivergrassAbove -> RivergrassVariant.BOTTOM
            else -> RivergrassVariant.SINGLE
        })
    }

    @SideOnly(Side.CLIENT)
    override fun getOffsetType(): EnumOffsetType {
        return EnumOffsetType.XZ
    }

    override fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean {
        //Must have water above
        val up = worldIn.getBlockState(pos.up())
        if (up.block.registryName != REGISTRY.getObject(ResourceLocation("simpledifficulty", "purifiedwater")).registryName
            && up.block.registryName != this.registryName) return false


        //Must have a SINGLE weed or valid soil below
        val down = worldIn.getBlockState(pos.down())
        val down2 = worldIn.getBlockState(pos.down(2))
        if (down.block == this) {                       // if block down is weed
            return down2.block != this                  // if 2 block down is weed return false
        } else return down.material in ALLOWED_SOILS    // if block down is not weed return if down is in ALLOWED_SOILS
    }


    // IGrowable implementation
    override fun canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean {
        val actualState = state.getActualState(worldIn, pos)
        return actualState.getValue(VARIANT) == RivergrassVariant.SINGLE
                && canBlockStay(worldIn, pos.up(), state)
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState) {
        worldIn.setBlockState(pos.up(), state)
    }
}