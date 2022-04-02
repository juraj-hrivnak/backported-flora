package azmalent.backportedflora.common.world

import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.fml.common.IWorldGenerator

abstract class AbstractGrassGenerator : IWorldGenerator {
    val chances = mapOf<BiomeDictionary.Type, Int>(

        /*Temperature-based tags. Specifying neither implies a biome is temperate*/
        BiomeDictionary.Type.HOT to 12,
        BiomeDictionary.Type.COLD to 6,

        /*Tags specifying the amount of vegetation a biome has. Specifying neither implies a biome to have moderate amounts*/
        BiomeDictionary.Type.SPARSE to 3,
        BiomeDictionary.Type.DENSE to 12,

        /*Tags specifying how moist a biome is. Specifying neither implies the biome as having moderate humidity*/
        BiomeDictionary.Type.WET to 12,
        BiomeDictionary.Type.DRY to 3,

        /*Tree-based tags, SAVANNA refers to dry, desert-like trees (Such as Acacia), CONIFEROUS refers to snowy trees (Such as Spruce) and JUNGLE refers to jungle trees.
         * Specifying no tag implies a biome has temperate trees (Such as Oak)*/
        BiomeDictionary.Type.SAVANNA to 12,
        BiomeDictionary.Type.CONIFEROUS to 6,
        BiomeDictionary.Type.JUNGLE to 12,

        /*Tags specifying the nature of a biome*/
        BiomeDictionary.Type.SPOOKY to 6,
        BiomeDictionary.Type.DEAD to 3,
        BiomeDictionary.Type.LUSH to 6,
        BiomeDictionary.Type.NETHER to 0,
        BiomeDictionary.Type.END to 0,
        BiomeDictionary.Type.MUSHROOM to 3,
        BiomeDictionary.Type.MAGICAL to 3,
        BiomeDictionary.Type.RARE to 3,

        BiomeDictionary.Type.OCEAN to 3,
        BiomeDictionary.Type.RIVER to 6,

        BiomeDictionary.Type.WATER to 3,

        /*Generic types which a biome can be*/
        BiomeDictionary.Type.MESA to 3,
        BiomeDictionary.Type.FOREST to 3,
        BiomeDictionary.Type.PLAINS to 6,
        BiomeDictionary.Type.MOUNTAIN to 6,
        BiomeDictionary.Type.HILLS to 3,
        BiomeDictionary.Type.SWAMP to 6,
        BiomeDictionary.Type.SANDY to 3,
        BiomeDictionary.Type.SNOWY to 0,
        BiomeDictionary.Type.WASTELAND to 3,
        BiomeDictionary.Type.BEACH to 6,
        BiomeDictionary.Type.VOID to 0
    )
}