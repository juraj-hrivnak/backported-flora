package azmalent.backportedflora.common.block.grass

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockDirt
import net.minecraft.block.BlockDirt.DirtType
import net.minecraft.block.IGrowable
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemSpade
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockGrass : Block(Material.GRASS), IGrowable {

    lateinit var itemBlock: Item

    companion object {
        const val NAME = "grass"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        val SNOWY: PropertyBool = PropertyBool.create("snowy")
    }

    init {
        setRegistryName(NAME)

        translationKey = NAME
        soundType = SoundType.GROUND
        creativeTab = BackportedFlora.creativeTab

        this.setHardness(1.5F)
        this.setResistance(2.0F)
        this.tickRandomly = true

        defaultState = blockState.baseState
            .withProperty(SNOWY, false)
    }

    fun createItemBlock(): Item {
        itemBlock = ItemBlock(this).setRegistryName(registryName).setTranslationKey(translationKey)
        return itemBlock
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemBlockRenderer(itemBlock, 0, registryName.toString())
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT_MIPPED
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState?, rand: Random) {
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3)) return
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up())
                    .getLightOpacity(worldIn, pos.up()) > 2
            ) {
                worldIn.setBlockState(pos, Blocks.DIRT.defaultState)
            } else {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                    for (i in 0..3) {
                        val blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1)
                        if (blockpos.y >= 0 && blockpos.y < 256 && !worldIn.isBlockLoaded(blockpos)) {
                            return
                        }
                        val iblockstate = worldIn.getBlockState(blockpos.up())
                        val iblockstate1 = worldIn.getBlockState(blockpos)
                        if (iblockstate1.block === Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == DirtType.DIRT && worldIn.getLightFromNeighbors(
                                blockpos.up()
                            ) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2
                        ) {
                            worldIn.setBlockState(blockpos, this.defaultState)
                        }
                    }
                }
            }
        }
    }

    override fun onBlockActivated(
        worldIn: World,
        pos: BlockPos,
        state: IBlockState,
        playerIn: EntityPlayer,
        hand: EnumHand,
        facing: EnumFacing,
        hitX: Float,
        hitY: Float,
        hitZ: Float
    ): Boolean {
        val itemStack: ItemStack = playerIn.getHeldItem(hand)
        return if (itemStack.item is ItemSpade) {
            val path = Blocks.GRASS_PATH.defaultState
            worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f)

            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos, path, 11)
                itemStack.damageItem(1, playerIn)
            }
            true
        } else {
            false
        }
    }

    override fun getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState? {
        val block = worldIn.getBlockState(pos.up()).block
        return state.withProperty(SNOWY, (block == Blocks.SNOW || block == Blocks.SNOW_LAYER))
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState
    }

    override fun getMetaFromState(state: IBlockState?): Int {
        return 0
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, SNOWY)
    }

    override fun getHarvestTool(state: IBlockState): String {
        return "shovel"
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return Blocks.DIRT.getItemDropped(
            Blocks.DIRT.defaultState.withProperty(BlockDirt.VARIANT, DirtType.DIRT),
            rand,
            fortune
        )
    }

    override fun canSustainPlant(
        state: IBlockState,
        world: IBlockAccess,
        pos: BlockPos,
        direction: EnumFacing,
        plantable: IPlantable
    ): Boolean {
        val plantType: EnumPlantType = plantable.getPlantType(world, pos.offset(direction))
        return ((plantable == Blocks.MELON_STEM)
                || (plantable == Blocks.PUMPKIN_STEM)
                || (plantable == Blocks.REEDS)
                || (plantType == EnumPlantType.Desert)
                || (plantType == EnumPlantType.Plains)
                || (plantType == EnumPlantType.Cave))
    }

    // IGrowable implementation
    override fun canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean {
        return canGrow(worldIn, pos, state, false)
    }

    override fun canGrow(worldIn: World?, pos: BlockPos?, state: IBlockState?, isClient: Boolean): Boolean {
        return true
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState?) {
        val blockpos = pos.up()
        for (i in 0..127) {
            var blockpos1 = blockpos
            for (j in 0 until i / 16) {
                blockpos1 =
                    blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1)
                if (worldIn.getBlockState(blockpos1.down()).block !== this || worldIn.getBlockState(blockpos1).isNormalCube) {
                    continue
                }
            }
            if (worldIn.isAirBlock(blockpos1)) {
                if (rand.nextInt(8) == 0) {
                    worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1)
                } else {
                    val iblockstate1 =
                        ModBlocks.blockTallGrass.defaultState
                    if (ModBlocks.blockTallGrass.canBlockStay(worldIn, blockpos1, iblockstate1)) {
                        worldIn.setBlockState(blockpos1, iblockstate1, 3)
                    }
                }
            }
        }
    }
}