package growthcraft.core;

import growthcraft.core.common.block.BlockRockSaltOre;
import growthcraft.core.common.block.BlockSalt;
import growthcraft.core.common.item.ItemCrowbar;
import growthcraft.core.common.item.ItemSalt;
import growthcraft.core.setup.ModSetup;
import growthcraft.core.shared.Reference;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import growthcraft.core.setup.ClientProxy;
import growthcraft.core.setup.IProxy;
import growthcraft.core.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(Reference.MODID)
public class Growthcraft {

    public static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final Logger LOGGER = LogManager.getLogger(growthcraft.core.shared.Reference.MODID);

    public static final ModSetup setup = new ModSetup();

    public Growthcraft() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GrowthcraftCoreConfig.SERVER, GrowthcraftCoreConfig.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GrowthcraftCoreConfig.CLIENT, GrowthcraftCoreConfig.CLIENT_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        GrowthcraftCoreConfig.loadConfig(
                GrowthcraftCoreConfig.SERVER, FMLPaths.CONFIGDIR.get().
                        resolve(GrowthcraftCoreConfig.SERVER_CONFIG).toString());
        GrowthcraftCoreConfig.loadConfig(
                GrowthcraftCoreConfig.CLIENT, FMLPaths.CONFIGDIR.get().
                        resolve(GrowthcraftCoreConfig.CLIENT_CONFIG).toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final ItemGroup itemGroup = new ItemGroup(Reference.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(GrowthcraftCoreBlocks.rockSaltOreBlock);
        }
    };

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Calling FMLCommonSetupEvent for " + growthcraft.core.shared.Reference.MODID);
        setup.init();
        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Calling FMLClientSetupEvent for " + growthcraft.core.shared.Reference.MODID);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Do Nothing at This Time
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Server is starting " + growthcraft.core.shared.Reference.MODID);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        private RegistryEvents() {
            /* Do Nothing */
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info(growthcraft.core.shared.Reference.MODID + " registering blocks...");
            event.getRegistry().register(new BlockRockSaltOre());
            event.getRegistry().register(new BlockSalt());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            LOGGER.info(growthcraft.core.shared.Reference.MODID + " registering items...");
            Item.Properties properties = new Item.Properties().group(itemGroup);

            event.getRegistry().register(new BlockItem(
                    GrowthcraftCoreBlocks.rockSaltOreBlock, properties)
                    .setRegistryName(Reference.MODID, BlockRockSaltOre.unlocalizedName)
            );

            event.getRegistry().register(new BlockItem(
                    GrowthcraftCoreBlocks.blockSalt, properties)
                    .setRegistryName(Reference.MODID, BlockSalt.unlocalizedName)
            );

            event.getRegistry().register(new ItemSalt());

            for ( DyeColor colour : DyeColor.values() ) {
                event.getRegistry().register(new ItemCrowbar(colour.getName()));
            }

        }
    }
}
