package azmalent.backportedflora.common.block

import azmalent.backportedflora.BackportedFlora
import azmalent.backportedflora.client.ModSoundTypes
import azmalent.backportedflora.common.registry.ModBlocks
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.Block
import net.minecraft.block.BlockCrops
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityBoat
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.BlockSnapshot
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

private val ALLOWED_SOILS = setOf<Material>(
    Material.GROUND, Material.SAND, Material.GRASS, Material.CLAY, Material.ROCK
)


class BlockCattail : BlockCrops() {

    companion object {
        const val NAME = "cattail"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"

        val CATTAIL_AGE: PropertyInteger = PropertyInteger.create("age", 0, 3)
        val CATTAIL_AABB = arrayOf(
            AxisAlignedBB(0.10000001192092896, 0.025, 0.10000001192092896, 0.899999988079071, 0.5, 0.899999988079071),
            AxisAlignedBB(0.10000001192092896, 0.025, 0.10000001192092896, 0.899999988079071, 0.625, 0.899999988079071),
            AxisAlignedBB(0.10000001192092896, 0.025, 0.10000001192092896, 0.899999988079071, 0.75, 0.899999988079071),
            AxisAlignedBB(0.10000001192092896, 0.025, 0.10000001192092896, 0.899999988079071, 0.875, 0.899999988079071)
        )
    }

    init {
        setRegistryName(NAME)
        translationKey = NAME
        soundType = ModSoundTypes.SEAWEED
        creativeTab = BackportedFlora.creativeTab
        this.defaultState = blockState.baseState.withProperty(CATTAIL_AGE, 0)
    }

    lateinit var itemBlock: Item

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemBlockRenderer(itemBlock, 0, registryName.toString())
    }


    public override fun getAgeProperty(): PropertyInteger {
        return CATTAIL_AGE
    }

    override fun getMaxAge(): Int {
        return 3
    }

    override fun getSeed(): Item {
        return ModBlocks.itemBlockCattail
    }

    override fun getCrop(): Item {
        return ModBlocks.itemBlockCattail
    }

    @SideOnly(Side.CLIENT)
    override fun getOffsetType(): EnumOffsetType {
        return EnumOffsetType.XZ
    }

    override fun canSustainBush(state: IBlockState): Boolean {
        return true
    }

    override fun addCollisionBoxToList(
        state: IBlockState,
        worldIn: World,
        pos: BlockPos,
        entityBox: AxisAlignedBB,
        collidingBoxes: List<AxisAlignedBB?>,
        entityIn: Entity?,
        isActualState: Boolean
    ) {
        if (entityIn !is EntityBoat) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NULL_AABB)
        }
    }

    override fun isPassable(worldIn: IBlockAccess, pos: BlockPos): Boolean {
        return true
    }


    override fun isFullCube(state: IBlockState): Boolean {
        return false
    }

    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false
    }

    override fun onEntityCollision(worldIn: World?, pos: BlockPos?, state: IBlockState?, entityIn: Entity) {
        entityIn.motionX = entityIn.motionX / 1.1
        entityIn.motionY = entityIn.motionY / 1.1
        entityIn.motionZ = entityIn.motionZ / 1.1
    }


    override fun canBlockStay(worldIn: World, pos: BlockPos, state: IBlockState): Boolean {
        val down = worldIn.getBlockState(pos.down())
        val down2 = worldIn.getBlockState(pos.down(2))

        if ((worldIn.isAirBlock(pos) || worldIn.getBlockState(pos).block == this) && down.material == Material.WATER) {
            return down2.material in ALLOWED_SOILS
        } else return false
    }

    override fun neighborChanged(state: IBlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos) {
        if (!canBlockStay(worldIn, pos, state)) {
            dropBlockAsItem(worldIn, pos, state, 0)
            worldIn.setBlockToAir(pos)
        }
    }

    override fun canPlaceBlockOnSide(worldIn: World, pos: BlockPos, side: EnumFacing): Boolean {
        return canBlockStay(worldIn, pos, defaultState)
    }

    override fun canPlaceBlockAt(worldIn: World, pos: BlockPos): Boolean {
        return canBlockStay(worldIn, pos, defaultState)
    }


    // Crop implementation
    override fun getBonemealAgeIncrease(worldIn: World): Int {
        return super.getBonemealAgeIncrease(worldIn) / 3
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, CATTAIL_AGE)
    }

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return CATTAIL_AABB[(state.getValue(this.ageProperty) as Int).toInt()].offset(state.getOffset(source, pos))
    }
}

class ItemBlockCattail : ItemBlock(ModBlocks.blockCattail) {

    companion object {
        const val NAME = "cattail"
        const val REGISTRY_NAME = "${BackportedFlora.MODID}:$NAME"
    }

    init {
        setRegistryName(NAME)
        translationKey = NAME
        creativeTab = BackportedFlora.creativeTab
    }

    fun canBlockStay(worldIn: World, pos: BlockPos): Boolean {
        val down = worldIn.getBlockState(pos.down())
        val down2 = worldIn.getBlockState(pos.down(2))

        return if (worldIn.isAirBlock(pos) && down.material == Material.WATER) {
            down2.material in ALLOWED_SOILS
        } else false
    }

    @SideOnly(Side.CLIENT)
    fun registerItemModel() {
        BackportedFlora.proxy.registerItemRenderer(this, 0, registryName.toString())
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val itemstack = playerIn.getHeldItem(handIn)
        val raytraceresult = rayTrace(worldIn, playerIn, true)
        return if (raytraceresult == null) {
            ActionResult(EnumActionResult.PASS, itemstack)
        } else {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                val blockpos = raytraceresult.blockPos
                if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(
                        blockpos.offset(
                            raytraceresult.sideHit
                        ), raytraceresult.sideHit, itemstack
                    )
                ) {
                    return ActionResult(EnumActionResult.FAIL, itemstack)
                }
                val blockpos1 = blockpos.up()

                if (canBlockStay(worldIn, blockpos1)) {
                    // special case for handling block placement with water lilies
                    val blocksnapshot = BlockSnapshot.getBlockSnapshot(worldIn, blockpos1)

                    if (ForgeEventFactory.onPlayerBlockPlace(
                            playerIn,
                            blocksnapshot,
                            EnumFacing.UP,
                            handIn
                        ).isCanceled
                    ) {
                        blocksnapshot.restore(true, false)
                        return ActionResult(EnumActionResult.FAIL, itemstack)
                    }

                    worldIn.setBlockState(blockpos1, ModBlocks.blockCattail.defaultState)

                    if (playerIn is EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger(playerIn, blockpos1, itemstack)
                    }

                    if (!playerIn.capabilities.isCreativeMode) {
                        itemstack.shrink(1)
                    }

                    playerIn.addStat(StatList.getObjectUseStats(this))
                    worldIn.playSound(
                        playerIn,
                        blockpos,
                        SoundEvents.BLOCK_WATERLILY_PLACE,
                        SoundCategory.BLOCKS,
                        1.0f,
                        1.0f
                    )
                    return ActionResult(EnumActionResult.SUCCESS, itemstack)
                }
            }
            ActionResult(EnumActionResult.FAIL, itemstack)
        }
    }

}
