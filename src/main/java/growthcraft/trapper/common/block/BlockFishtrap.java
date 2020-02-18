package growthcraft.trapper.common.block;

import growthcraft.trapper.common.tileentity.TileEntityFishtrap;
import growthcraft.trapper.shared.block.BlockTrap;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockFishtrap extends BlockTrap {

    public static final String unlocalizedName = "fishtrap";

    public BlockFishtrap() {
        super(unlocalizedName);
    }

    // region TileEntity
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityFishtrap();
    }
    // endregion

}
