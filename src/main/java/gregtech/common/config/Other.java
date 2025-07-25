package gregtech.common.config;

import com.gtnewhorizon.gtnhlib.config.Config;

import gregtech.api.enums.Mods;

@Config(modid = Mods.ModIDs.GREG_TECH, category = "other", configSubDirectory = "GregTech", filename = "Other")
public class Other {

    @Config.Comment("How much pipes you can chain wrench to disable their input.")
    @Config.DefaultInt(64)
    @Config.RequiresMcRestart
    public static int pipeWrenchingChainRange;

    @Config.Comment("How much blocks you can chain paint with GT spray cans.")
    @Config.DefaultInt(64)
    @Config.RequiresMcRestart
    public static int sprayCanChainRange;

    @Config.Comment("How much blocks you can paint with GT spray cans.")
    @Config.DefaultInt(512)
    @Config.RequiresMcRestart
    public static int sprayCanUses;
}
