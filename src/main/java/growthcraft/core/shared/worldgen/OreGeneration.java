package growthcraft.core.shared.worldgen;


import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

    private OreGeneration() { /* Do Nothing */ }

    public static void setupOreGeneration() {
        for(Biome biome : ForgeRegistries.BIOMES) {
            if(GrowthcraftCoreConfig.getSaltOreGenerate()) {
                CountRangeConfig countRangeConfig = new CountRangeConfig(
                        GrowthcraftCoreConfig.getSaltOreGenCount(),
                        GrowthcraftCoreConfig.getSaltOreGenMinHeight(),
                        0,
                        GrowthcraftCoreConfig.getSaltOreGenMaxHeight()
                );
                biome.addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Biome.createDecoratedFeature(
                                Feature.ORE,
                                new OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                        GrowthcraftCoreBlocks.rockSaltOreBlock.getDefaultState(),
                                        GrowthcraftCoreConfig.getSaltOreGenChance()
                                ),
                                Placement.COUNT_RANGE,
                                countRangeConfig
                        )
                );
            }
        }
    }
}
