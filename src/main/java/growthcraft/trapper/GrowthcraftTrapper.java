package growthcraft.trapper;

import growthcraft.core.setup.IProxy;
import growthcraft.trapper.common.block.BlockFishtrap;
import growthcraft.trapper.common.tileentity.TileEntityFishtrap;
import growthcraft.trapper.setup.ClientProxy;
import growthcraft.trapper.setup.ModSetup;
import growthcraft.trapper.setup.ServerProxy;
import growthcraft.trapper.shared.Reference;
import growthcraft.trapper.shared.init.GrowthcraftTrapperBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

import static growthcraft.core.Growthcraft.itemGroup;

@Mod(Reference.MODID)
public class GrowthcraftTrapper {

    public static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final Logger LOGGER = LogManager.getLogger(growthcraft.trapper.shared.Reference.MODID);

    public static final ModSetup setup = new ModSetup();

    public GrowthcraftTrapper() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Calling FMLCommonSetupEvent for " + growthcraft.trapper.shared.Reference.MODID);
        setup.init();
        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Calling FMLClientSetupEvent for " + growthcraft.trapper.shared.Reference.MODID);
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
        LOGGER.info("Server is starting " + growthcraft.trapper.shared.Reference.MODID);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        private RegistryEvents() {
            /* Do Nothing */
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info(growthcraft.trapper.shared.Reference.MODID + " registering blocks...");
            event.getRegistry().register(new BlockFishtrap());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            LOGGER.info(growthcraft.trapper.shared.Reference.MODID + " registering items...");
            Item.Properties properties = new Item.Properties().group(itemGroup);

            event.getRegistry().register(new BlockItem(
                    GrowthcraftTrapperBlocks.fishtrap, properties)
                    .setRegistryName(Reference.MODID, BlockFishtrap.unlocalizedName)
            );

        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(
                    TileEntityType.Builder.create(
                            TileEntityFishtrap::new,
                            GrowthcraftTrapperBlocks.fishtrap).build(null)
                    .setRegistryName(Reference.MODID, BlockFishtrap.unlocalizedName)
            );
        }
    }
}
