package gregtech.common.items;

import static net.minecraft.util.StatCollector.translateToLocal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Mods;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.items.GTGenericItem;
import gregtech.api.util.GTLanguageManager;
import shedar.mods.ic2.nuclearcontrol.api.CardState;
import shedar.mods.ic2.nuclearcontrol.api.ICardWrapper;
import shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource;
import shedar.mods.ic2.nuclearcontrol.api.IRemoteSensor;
import shedar.mods.ic2.nuclearcontrol.api.PanelSetting;
import shedar.mods.ic2.nuclearcontrol.api.PanelString;

@Optional.InterfaceList(
    value = {
        @Optional.Interface(
            iface = "shedar.mods.ic2.nuclearcontrol.api.IRemoteSensor",
            modid = Mods.ModIDs.I_C2_NUCLEAR_CONTROL),
        @Optional.Interface(
            iface = "shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource",
            modid = Mods.ModIDs.I_C2_NUCLEAR_CONTROL) })
public class ItemSensorCard extends GTGenericItem implements IRemoteSensor, IPanelDataSource {

    private static final UUID CARD_TYPE = new UUID(0L, 41L);

    private int strCount;

    public ItemSensorCard(String aUnlocalized, String aEnglish) {
        super(aUnlocalized, aEnglish, "Insert into Display Panel");
        setMaxStackSize(1);
    }

    @Override
    public void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        if (aStack != null) {
            NBTTagCompound tNBT = aStack.getTagCompound();
            if (tNBT == null) {
                aList.add(translateToLocal("gt.item.desc.miss_coord"));
            } else {
                aList.add(translateToLocal("gt.item.desc.device_at"));
                aList.add(
                    String.format(
                        "x: %d, y: %d, z: %d",
                        tNBT.getInteger("x"),
                        tNBT.getInteger("y"),
                        tNBT.getInteger("z")));
            }
        }
    }

    @Override
    public CardState update(TileEntity aPanel, ICardWrapper aCard, int aMaxRange) {
        return update(aPanel.getWorldObj(), aCard, aMaxRange);
    }

    @Override
    public CardState update(World world, ICardWrapper aCard, int aMaxRange) {
        ChunkCoordinates target = aCard.getTarget();

        TileEntity tTileEntity = world.getTileEntity(target.posX, target.posY, target.posZ);
        if (((tTileEntity instanceof IGregTechDeviceInformation))
            && (((IGregTechDeviceInformation) tTileEntity).isGivingInformation())) {
            String[] tInfoData = ((IGregTechDeviceInformation) tTileEntity).getInfoData();
            for (int i = 0; i < tInfoData.length; i++) {
                aCard.setString("mString" + i, tInfoData[i]);
            }
            aCard.setInt("mString", strCount = tInfoData.length);
            return CardState.OK;
        }
        return CardState.NO_TARGET;
    }

    @Override
    public List<PanelString> getStringData(int aSettings, ICardWrapper aCard, boolean aLabels) {
        List<PanelString> rList = new LinkedList<>();
        for (int i = 0; i < (strCount = aCard.getInt("mString")); i++) {
            if ((aSettings & 1 << i) != 0) {
                PanelString line = new PanelString();
                line.textLeft = GTLanguageManager.getTranslation(aCard.getString("mString" + i), "\\\\");
                rList.add(line);
            }
        }
        return rList;
    }

    @Override
    public List<PanelSetting> getSettingsList() {
        List<PanelSetting> rList = new ArrayList<>();
        for (int i = 0; i < strCount; i++) {
            rList.add(new PanelSetting(String.valueOf((i + 1)), 1 << i, getCardType()));
        }
        return rList;
    }

    @Override
    public UUID getCardType() {
        return CARD_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aCreativeTab, List<ItemStack> aOutputSubItems) {}
}
