package gregtech.common.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.IBlockContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GTRenderingWorld;
import gregtech.api.util.LightingHelper;

class GTCopiedCTMBlockTexture extends GTTextureBase implements ITexture, IBlockContainer {

    private final Block mBlock;
    private final byte mSide;
    private final int mMeta;

    GTCopiedCTMBlockTexture(Block aBlock, int ordinalSide, int aMeta, short[] aRGBa, boolean allowAlpha) {
        if (aRGBa.length != 4)
            throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GTCopiedCTMBlockTexture");
        mBlock = aBlock;
        mSide = (byte) ordinalSide;
        mMeta = aMeta;
    }

    @Override
    public boolean isOldTexture() {
        return false;
    }

    private IIcon getIcon(int ordinalSide, int aX, int aY, int aZ, RenderBlocks aRenderer) {
        final int tSide = mSide == 6 ? ordinalSide : mSide;
        return mBlock.getIcon(getBlockAccess(aRenderer), aX, aY, aZ, tSide);
    }

    private GTRenderingWorld getBlockAccess(RenderBlocks aRenderer) {
        return GTRenderingWorld.getInstance(aRenderer.blockAccess);
    }

    @Override
    public void renderXPos(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        final IIcon aIcon = getIcon(ForgeDirection.EAST.ordinal(), aX, aY, aZ, aRenderer);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        aRenderer.field_152631_f = true;
        startDrawingQuads(aRenderer, 1.0f, 0.0f, 0.0f);
        lightingHelper.setupLightingXPos(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.EAST, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
        aRenderer.field_152631_f = false;
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        startDrawingQuads(aRenderer, -1.0f, 0.0f, 0.0f);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        final IIcon aIcon = getIcon(ForgeDirection.WEST.ordinal(), aX, aY, aZ, aRenderer);
        lightingHelper.setupLightingXNeg(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.WEST, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        startDrawingQuads(aRenderer, 0.0f, 1.0f, 0.0f);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        final IIcon aIcon = getIcon(ForgeDirection.UP.ordinal(), aX, aY, aZ, aRenderer);
        lightingHelper.setupLightingYPos(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.UP, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        startDrawingQuads(aRenderer, 0.0f, -1.0f, 0.0f);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        final IIcon aIcon = getIcon(ForgeDirection.DOWN.ordinal(), aX, aY, aZ, aRenderer);
        lightingHelper.setupLightingYNeg(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.DOWN, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceYNeg(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        startDrawingQuads(aRenderer, 0.0f, 0.0f, 1.0f);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        final IIcon aIcon = getIcon(ForgeDirection.SOUTH.ordinal(), aX, aY, aZ, aRenderer);
        lightingHelper.setupLightingZPos(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.SOUTH, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, LightingHelper lightingHelper, Block aBlock, int aX, int aY, int aZ,
        int worldRenderPass) {
        startDrawingQuads(aRenderer, 0.0f, 0.0f, -1.0f);
        if (worldRenderPass != -1 && !mBlock.canRenderInPass(worldRenderPass)) return;
        final IIcon aIcon = getIcon(ForgeDirection.NORTH.ordinal(), aX, aY, aZ, aRenderer);
        aRenderer.field_152631_f = true;
        lightingHelper.setupLightingZNeg(aBlock, aX, aY, aZ)
            .setupColor(ForgeDirection.NORTH, mBlock.colorMultiplier(getBlockAccess(aRenderer), aX, aY, aZ));
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, aIcon);
        draw(aRenderer);
        aRenderer.field_152631_f = false;
    }

    @Override
    public boolean isValidTexture() {
        return mBlock != null;
    }

    @Override
    public Block getBlock() {
        return mBlock;
    }

    @Override
    public int getMeta() {
        return mMeta;
    }
}
