package azmalent.backportedflora.common.util

import com.charles445.simpledifficulty.api.SDFluids
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome

object WorldGenUtil {
    fun getBiomeInChunk(world: World, chunkX: Int, chunkZ: Int): Biome {
        return world.getBiomeForCoordsBody(BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8))
    }

    fun canSeeSky(world: World, pos: BlockPos): Boolean {
        var topPos = pos

        while (world.getBlockState(topPos).material == Material.WATER) {
            topPos = topPos.up()
        }

        val block = world.getBlockState(topPos).block
        if (world.isAirBlock(topPos) || block == Blocks.ICE || block == Blocks.WATERLILY) {
            return world.canSeeSky(topPos)
        }

        return false
    }
}