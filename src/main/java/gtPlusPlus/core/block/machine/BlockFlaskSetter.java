package gtPlusPlus.core.block.machine;

import static gregtech.api.enums.Mods.GTPlusPlus;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import gregtech.api.util.GTLog;
import gregtech.common.items.MetaGeneratedTool01;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.api.objects.minecraft.CubicObject;
import gtPlusPlus.core.block.base.BasicTileBlockWithTooltip;
import gtPlusPlus.core.creative.AddToCreativeTab;
import gtPlusPlus.core.handler.GuiHandler;
import gtPlusPlus.core.item.base.itemblock.ItemBlockBasicTile;
import gtPlusPlus.core.tileentities.general.TileEntityVolumetricFlaskSetter;

public class BlockFlaskSetter extends BasicTileBlockWithTooltip {

    /**
     * Determines which tooltip is displayed within the itemblock.
     */
    private final int mTooltipID = 5;

    @Override
    public int getTooltipID() {
        return this.mTooltipID;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockBasicTile.class;
    }

    public BlockFlaskSetter() {
        super(Material.iron);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player,
        final int side, final float lx, final float ly, final float lz) {
        if (world.isRemote) {
            return true;
        }
        // Check For Screwdriver
        try {
            final ItemStack mHandStack = player.getHeldItem();
            final Item mHandItem = mHandStack.getItem();
            if (((mHandItem instanceof MetaGeneratedTool01)
                && ((mHandItem.getDamage(mHandStack) == 22) || (mHandItem.getDamage(mHandStack) == 150)))) {
                final TileEntityVolumetricFlaskSetter tile = (TileEntityVolumetricFlaskSetter) world
                    .getTileEntity(x, y, z);
                if (tile != null) {
                    if (tile.onScrewdriverRightClick((byte) side, player, x, y, z)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(GTLog.err);
        }
        final TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityVolumetricFlaskSetter) {
            player.openGui(GTplusplus.instance, GuiHandler.GUI18, world, x, y, z);
            // new Packet_VolumetricFlaskGui2(aTile, aTile.getCustomValue());
            return true;
        }
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int p_149915_2_) {
        return new TileEntityVolumetricFlaskSetter();
    }

    @Override
    public void onBlockAdded(final World world, final int x, final int y, final int z) {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entity,
        final ItemStack stack) {
        if (stack.hasDisplayName()) {
            ((TileEntityVolumetricFlaskSetter) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public int getMetaCount() {
        return 0;
    }

    @Override
    public String getUnlocalBlockName() {
        return "blockVolumetricFlaskSetter";
    }

    @Override
    protected float initBlockHardness() {
        return 5f;
    }

    @Override
    protected float initBlockResistance() {
        return 1f;
    }

    @Override
    protected CreativeTabs initCreativeTab() {
        return AddToCreativeTab.tabMachines;
    }

    @Override
    protected String getTileEntityName() {
        return "Volumetric Flask Configurator";
    }

    @Override
    public CubicObject<String>[] getCustomTextureDirectoryObject() {
        String[] aTexData = new String[] { GTPlusPlus.ID + ":metro/TEXTURE_METAL_PANEL_H",
            GTPlusPlus.ID + ":metro/TEXTURE_TECH_PANEL_C", GTPlusPlus.ID + ":metro/TEXTURE_METAL_PANEL_H",
            GTPlusPlus.ID + ":metro/TEXTURE_METAL_PANEL_H", GTPlusPlus.ID + ":metro/TEXTURE_METAL_PANEL_H",
            GTPlusPlus.ID + ":metro/TEXTURE_METAL_PANEL_H" };
        return (CubicObject<String>[]) new CubicObject[] { new CubicObject<>(aTexData) };
    }
}
