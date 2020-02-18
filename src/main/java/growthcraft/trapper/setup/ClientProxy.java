package growthcraft.trapper.setup;

import growthcraft.core.setup.IProxy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        /* Do Nothing At This Time */
    }

    @Override
    public World getClientWorld() {
        return null;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return null;
    }
}
