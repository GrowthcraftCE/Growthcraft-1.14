package growthcraft.core;

import growthcraft.core.common.blocks.RockSaltOreBlock;
import growthcraft.core.common.blocks.SaltBlock;
import growthcraft.core.common.items.CrowbarItem;
import growthcraft.core.common.items.SaltItem;
import growthcraft.core.setup.ModSetup;
import growthcraft.core.shared.Reference;
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
public class Growthcraft {

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final Logger LOGGER = LogManager.getLogger(growthcraft.trapper.shared.Reference.MODID);

    public static ModSetup setup = new ModSetup();

    public Growthcraft() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ItemGroup itemGroup = new ItemGroup(Reference.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(GrowthcraftCoreBlocks.rockSaltOreBlock);
        }
    };

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
            event.getRegistry().register(new RockSaltOreBlock());
            event.getRegistry().register(new SaltBlock());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            LOGGER.info(growthcraft.trapper.shared.Reference.MODID + " registering items...");
            Item.Properties properties = new Item.Properties().group(itemGroup);

            event.getRegistry().register(new BlockItem(
                    GrowthcraftCoreBlocks.rockSaltOreBlock, properties)
                    .setRegistryName(Reference.MODID, RockSaltOreBlock.unlocalizedName)
            );

            event.getRegistry().register(new BlockItem(
                    GrowthcraftCoreBlocks.saltBlock, properties)
                    .setRegistryName(Reference.MODID, SaltBlock.unlocalizedName)
            );

            event.getRegistry().register(new SaltItem());

            for ( DyeColor colour : DyeColor.values() ) {
                event.getRegistry().register(new CrowbarItem(colour.getName()));
            }

        }
    }
}
