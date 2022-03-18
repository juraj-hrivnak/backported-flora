package azmalent.backportedflora.common.world

import azmalent.backportedflora.ModConfig
import azmalent.backportedflora.common.registry.ModBlocks
import com.charles445.simpledifficulty.api.SDFluids
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

class WorldGenCattail : IWorldGenerator {
    override fun generate(
        rand: Random,
        chunkX: Int,
        chunkZ: Int,
        world: World,
        chunkGenerator: IChunkGenerator,
        chunkProvider: IChunkProvider
    ) {
        if (world.worldType != WorldType.FLAT) {
            if (rand.nextFloat() < ModConfig.Seagrass.generationChance) {
                val chunkPos = world.getChunk(chunkX, chunkZ).pos

                for (i in 0..ModConfig.Seagrass.patchAttempts) {
                    val x = rand.nextInt(16) + 8
                    val z = rand.nextInt(16) + 8

                    val yRange = world.getHeight(chunkPos.getBlock(0, 0, 0).add(x, 0, z)).y + 32
                    val y = rand.nextInt(yRange)

                    val pos = chunkPos.getBlock(0, 0, 0).add(x, y, z)
                    generateCattail(world, rand, pos)
                }
            }
        }
    }

    private fun generateCattail(world: World, rand: Random, targetPos: BlockPos) {
        if (!ModConfig.Seagrass.canGenerate(world, targetPos)) {
            return
        }

        for (i in 0..ModConfig.Seagrass.plantAttempts) {
            val pos = targetPos.add(
                rand.nextInt(8) - rand.nextInt(8),
                rand.nextInt(4) - rand.nextInt(4),
                rand.nextInt(8) - rand.nextInt(8)
            )

            if (!world.isBlockLoaded(pos)) continue

            val down = world.getBlockState(pos.down()).block
            if ((down == SDFluids.blockPurifiedWater || down == Blocks.WATER) && pos.y < 100 && pos.y > 44) {
                placeCattail(world, pos, rand)
            }
        }
    }

    private fun placeCattail(world: World, pos: BlockPos, rand: Random) {
        val startingAge = rand.nextInt(ModBlocks.blockCattail.maxAge)
        val state = ModBlocks.blockCattail.defaultState.withProperty(ModBlocks.blockCattail.ageProperty, startingAge)

        if (ModBlocks.blockCattail.canBlockStay(world, pos, state)) {
            world.setBlockState(pos, state)
        }
    }
}

