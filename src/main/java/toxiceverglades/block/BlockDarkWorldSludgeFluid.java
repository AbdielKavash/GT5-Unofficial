package toxiceverglades.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.Fluid;

public class BlockDarkWorldSludgeFluid extends Fluid {

    public static final Material SLUDGE = new MaterialLiquid(MapColor.dirtColor);

    protected static int mapColor = 0xFFFFFFFF;
    protected static float overlayAlpha = 0.2F;
    protected static Material material = SLUDGE;

    public BlockDarkWorldSludgeFluid(String fluidName, int rgbColour) {
        this(fluidName, rgbColour, null);
    }

    public BlockDarkWorldSludgeFluid(String fluidName, int rgbColour, Float overlayAlpha) {
        super(fluidName);
        setColor(rgbColour);
        if (overlayAlpha != null) {
            setAlpha(overlayAlpha);
        } else {
            setAlpha(0);
        }
    }

    @Override
    public int getColor() {
        return mapColor;
    }

    public BlockDarkWorldSludgeFluid setColor(int parColor) {
        mapColor = parColor;
        return this;
    }

    public float getAlpha() {
        return overlayAlpha;
    }

    public BlockDarkWorldSludgeFluid setAlpha(float parOverlayAlpha) {
        overlayAlpha = parOverlayAlpha;
        return this;
    }

    public BlockDarkWorldSludgeFluid setMaterial(Material parMaterial) {
        material = parMaterial;
        return this;
    }

    public Material getMaterial() {
        return material;
    }
}
