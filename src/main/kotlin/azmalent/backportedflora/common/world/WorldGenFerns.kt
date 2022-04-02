package azmalent.backportedflora.common.world

import azmalent.backportedflora.common.registry.ModBlocks
import azmalent.backportedflora.common.util.WorldGenUtil
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.common.BiomeDictionary
import java.util.*

class WorldGenFerns : AbstractGrassGenerator() {
    override fun generate(rand: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        val chunkPos = world.getChunk(chunkX, chunkZ).pos
        val biome = WorldGenUtil.getBiomeInChunk(world, chunkX, chunkZ)

        chances.forEach { (type, value) ->
            if (BiomeDictionary.hasType(biome, type)) {
                for (i in 0 until 16 * value) {
                    val pos = getGenerationPos(world, rand, chunkPos)
                    generateFerns(world, rand, pos)
                }
            }
        }
    }

    private fun getGenerationPos(world: World, rand: Random, chunkPos: ChunkPos): BlockPos {
        val x = rand.nextInt(16) + 8
        val z = rand.nextInt(16) + 8

        val yRange = world.getHeight(chunkPos.getBlock(0, 0, 0).add(x, 0, z)).y + 32
        val y = rand.nextInt(yRange)

        return chunkPos.getBlock(0, 0, 0).add(x, y, z)
    }

    private fun generateFerns(world: World, rand: Random, targetPos: BlockPos) {
        for (i in 0..32) {
            val pos = targetPos.add(
                rand.nextInt(8) - rand.nextInt(8),
                rand.nextInt(4) - rand.nextInt(4),
                rand.nextInt(8) - rand.nextInt(8)
            )

            if (!world.isBlockLoaded(pos)) continue

            if (world.isAirBlock(pos)) {
                placeFern(world, pos, rand)
            }
        }
    }

    private fun placeFern(world: World, pos: BlockPos, rand: Random) {
        val startingAge = rand.nextInt(ModBlocks.blockTallFern.maxAge)
        val state = ModBlocks.blockTallFern.defaultState.withProperty(ModBlocks.blockTallFern.ageProperty, startingAge)
        val maxState = ModBlocks.blockTallFern.defaultState.withProperty(ModBlocks.blockTallFern.ageProperty, ModBlocks.blockTallFern.maxAge)

        if (ModBlocks.blockTallFern.canBlockStay(world, pos, state)) {
            world.setBlockState(pos, state, 2)

            if (rand.nextDouble() < 0.2) {
                world.setBlockState(pos, maxState, 2)

                if (world.isAirBlock(pos.up()) && ModBlocks.blockTallFern.canBlockStay(world, pos.up(), state)) {
                    world.setBlockState(pos.up(), state, 2)
                }
            }
        }
    }
}

