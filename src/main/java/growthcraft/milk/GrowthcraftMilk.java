package growthcraft.milk;

import growthcraft.core.Growthcraft;
import growthcraft.milk.setup.ClientProxy;
import growthcraft.milk.setup.IProxy;
import growthcraft.milk.setup.ServerProxy;
import growthcraft.milk.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(Reference.MODID)
public class GrowthcraftMilk {

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    public GrowthcraftMilk() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Calling FMLCommonSetupEvent for " + Reference.MODID);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Calling FMLClientSetupEvent for " + Reference.MODID);
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
        LOGGER.info("Server is starting " + Reference.MODID);
    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        private RegistryEvents() {
            /* Do Nothing */
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info(Reference.MODID + " registering blocks...");

        }

        @SubscribeEvent
        public static void onItemsRegistry( final RegistryEvent.Register<Item> event ) {
            LOGGER.info(Reference.MODID + " registering items...");
            Item.Properties properties = new Item.Properties().group(Growthcraft.itemGroup);

        }
    }
}
