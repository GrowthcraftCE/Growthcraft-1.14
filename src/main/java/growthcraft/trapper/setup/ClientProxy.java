package growthcraft.trapper.setup;

import growthcraft.core.setup.IProxy;
import growthcraft.trapper.client.gui.ScreenFishtrap;
import growthcraft.trapper.shared.init.GrowthcraftTrapperBlocks;
import net.minecraft.block.HopperBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(GrowthcraftTrapperBlocks.fishtrapContainerType, ScreenFishtrap::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
