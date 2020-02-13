package growthcraft.core.shared.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod.EventBusSubscriber
public class GrowthcraftCoreConfig {

    public static final String SERVER_CONFIG = "growthcraft-core-server.toml";
    public static final String CLIENT_CONFIG = "growthcraft-core-client.toml";

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER;
    public static final ForgeConfigSpec CLIENT;

    private static ForgeConfigSpec.IntValue saltOreGenChance;
    private static ForgeConfigSpec.BooleanValue saltOreGenerate;
    private static ForgeConfigSpec.IntValue saltOreGenCount;
    private static ForgeConfigSpec.IntValue saltOreGenMinHeight;
    private static ForgeConfigSpec.IntValue saltOreGenMaxHeight;

    static {
        initWorldGenConfig(SERVER_BUILDER, CLIENT_BUILDER);
        SERVER = SERVER_BUILDER.build();
        CLIENT = CLIENT_BUILDER.build();
    }

    private GrowthcraftCoreConfig() { /* Do Nothing */ }

    public static void loadConfig(ForgeConfigSpec configSpec, String path) {
        final CommentedFileConfig fileConfig = CommentedFileConfig.builder(
                new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();

        fileConfig.load();
        configSpec.setConfig(fileConfig);
    }

    public static void initWorldGenConfig(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("Growthcraft Core World Generation");
        saltOreGenerate = server
                .comment("Generate Growthcraft custom ores within the world.")
                .define("worldgen.saltOre.enable", true);
        saltOreGenChance = server
                .comment("Maximum number of ore veins generated in a chunk.")
                .defineInRange("worldgen.saltOre.chance", 20, 1, 1000000);
        saltOreGenCount = server
                .comment("Max number of SaltOre per cluster.")
                .defineInRange("worldgen.saltOre.count", 5, 1, 10);
        saltOreGenMinHeight = server
                .comment("Minimum height to generate SaltOre within a chunk.")
                .defineInRange("worldgen.saltOre.minHeight", 10, 1, 128);
        saltOreGenMaxHeight = server
                .comment("Maximum height to generate SaltOre within a chunk.")
                .defineInRange("worldgen.saltOre.maxHeight", 100, 1, 128);
    }

    // region Getters
    public static boolean getSaltOreGenerate() { return saltOreGenerate.get(); }
    public static int getSaltOreGenChance() { return saltOreGenChance.get(); }
    public static int getSaltOreGenCount() { return saltOreGenCount.get(); }
    public static int getSaltOreGenMinHeight() { return saltOreGenMinHeight.get(); }
    public static int getSaltOreGenMaxHeight() { return saltOreGenMaxHeight.get(); }
    // endregion
}
