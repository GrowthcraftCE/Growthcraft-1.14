package growthcraft;

import growthcraft.core.common.blocks.RockSaltOreBlock;
import growthcraft.core.setup.ClientProxy;
import growthcraft.core.setup.IProxy;
import growthcraft.core.setup.ServerProxy;
import growthcraft.core.shared.init.GrowthcraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
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

@Mod("growthcraft")
public class Growthcraft {

    /* Dynamic Proxy depending on which side you are on. */
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    /* Logger for this mod */
    private static final Logger LOGGER = LogManager.getLogger();

    public Growthcraft() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Calling FMLCommonSetupEvent for growthcraft");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Calling FMLClientSetupEvent for growthcraft");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("growthcraft", "helloworld", () -> {
            LOGGER.info("Calling InterModEnqueueEvent for growthcraft");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        private RegistryEvents() {
            /* Do Nothing */
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().register(new RockSaltOreBlock());
        }

        @SubscribeEvent
        public static void onItemsRegistry( final RegistryEvent.Register<Item> itemRegistryEvent ) {
            itemRegistryEvent.getRegistry().register(new BlockItem(GrowthcraftBlocks.rockSaltOreBlock,
                    new Item.Properties()).setRegistryName(RockSaltOreBlock.unlocalizedName));
        }
    }
}
