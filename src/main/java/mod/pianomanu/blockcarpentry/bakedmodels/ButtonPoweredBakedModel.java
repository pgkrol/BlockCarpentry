package mod.pianomanu.blockcarpentry.bakedmodels;

import com.google.common.collect.ImmutableList;
import mod.pianomanu.blockcarpentry.block.ButtonFrameBlock;
import mod.pianomanu.blockcarpentry.block.FrameBlock;
import mod.pianomanu.blockcarpentry.tileentity.FrameBlockTile;
import mod.pianomanu.blockcarpentry.util.TextureHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains all information for the block model
 * See {@link mod.pianomanu.blockcarpentry.util.ModelHelper} for more information
 * @author PianoManu
 * @version 1.2 09/21/20
 */
public class ButtonPoweredBakedModel implements IDynamicBakedModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "block/oak_planks");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURE);
    }

    private void putVertex(BakedQuadBuilder builder, Vector3d normal,
                           double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {

        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0 ; j < elements.size() ; j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getInterpolatedU(u);
                            float iv = sprite.getInterpolatedV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
        builder.setApplyDiffuseLighting(true);
    }

    private BakedQuad createUpDownQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite, Direction facing, AttachFace face) {
        Vector3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        float uShort = 5;
        float uLong = 11;
        float vShort = 6;
        float vLong = 10;
        if (face==AttachFace.WALL && facing!=Direction.NORTH && facing!=Direction.SOUTH) {
            float tmp = uShort;
            uShort = uLong;
            uLong = tmp;
            tmp = vShort;
            vShort = vLong;
            vLong = tmp;
        }
        if (face==AttachFace.WALL && (facing==Direction.NORTH || facing==Direction.SOUTH)) {
            float tmp = uShort;
            uShort = vLong;
            vLong = tmp;
            tmp = vShort;
            vShort = uLong;
            uLong = tmp;
        }
        if (face!=AttachFace.WALL && (facing==Direction.NORTH || facing==Direction.SOUTH)) {
            uShort = 6;
            uLong = 10;
            vShort = 5;
            vLong = 11;
        }
        putVertex(builder, normal, v1.x, v1.y, v1.z, uShort, vShort, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, uShort, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uLong, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, uLong, vShort, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    //North/South
    private BakedQuad createLongSideQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite, Direction facing, AttachFace face) {
        Vector3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        float uShort = 5;
        float uLong = 11;
        float vShort = 14;
        float vLong = 16;
        if (face==AttachFace.WALL && (facing==Direction.SOUTH || facing==Direction.NORTH)) {
            uShort = 6;
            uLong = 10;
            vShort = 6;
            vLong = 10;
        }
        if (face==AttachFace.WALL && (facing==Direction.WEST || facing==Direction.EAST)) {
            //uShort = 5;
            //uLong = 11; //already set
            vShort = 6;
            vLong = 10;
        }
        if (face!=AttachFace.WALL && (facing==Direction.NORTH || facing==Direction.SOUTH)) {
            uShort = 6;
            uLong = 10;
            vShort = 7;
            vLong = 9;
        }
        putVertex(builder, normal, v1.x, v1.y, v1.z, uShort, vShort, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, uShort, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uLong, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, uLong, vShort, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    //East/West
    private BakedQuad createShortSideQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite, Direction facing, AttachFace face) {
        Vector3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        builder.setApplyDiffuseLighting(true);
        float uShort = 6;
        float uLong = 10;
        float vShort = 14;
        float vLong = 16;
        if (face==AttachFace.WALL && (facing==Direction.SOUTH || facing==Direction.NORTH)) {
            uShort = 5;
            uLong = 11;
            vShort = 6;
            vLong = 10;
        }
        if (face==AttachFace.WALL && (facing==Direction.WEST || facing==Direction.EAST)) {
            //uShort = 6;
            //uLong = 10; //already set
            vShort = 6;
            vLong = 10;
        }
        if (face!=AttachFace.WALL && (facing==Direction.NORTH || facing==Direction.SOUTH)) {
            uShort = 5;
            uLong = 11;
            vShort = 7;
            vLong = 9;
        }
        putVertex(builder, normal, v1.x, v1.y, v1.z, uShort, vShort, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, uShort, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uLong, vLong, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, uLong, vShort, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    private static Vector3d v(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }


    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof FrameBlock)) {
            ModelResourceLocation location = BlockModelShapes.getModelLocation(mimic);
            if (location != null) {
                IBakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                model.getBakedModel().getQuads(mimic, side, rand, extraData);
                if (model != null) {
                    return getMimicQuads(state, side, rand, extraData, model);
                }
            }
        }
        return Collections.emptyList();
    }

    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData, IBakedModel model) {
        if (side != null) {
            return Collections.emptyList();
        }

        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        int tex = extraData.getData(FrameBlockTile.TEXTURE);
        if (mimic!=null && state != null) {
            Direction direction = state.get(ButtonFrameBlock.HORIZONTAL_FACING);
            AttachFace face = state.get(ButtonFrameBlock.FACE);
            //get texture from block in tile entity and apply it to the quads
            List<TextureAtlasSprite> textureList = TextureHelper.getTextureFromModel(model, extraData, rand);
            TextureAtlasSprite texture;
            if (textureList.size() <= tex) {
                //texture = textureList.get(0);
                extraData.setData(FrameBlockTile.TEXTURE, 0);
                tex = 0;
            }
            if (textureList.size() == 0) {
                return Collections.emptyList();
            }
            texture = textureList.get(tex);
            List<BakedQuad> quads = new ArrayList<>();
            //down
            quads.add(createUpDownQuad(v(x(direction,face), y(direction,face), z(direction,face)), v(x(direction,face)+xr(direction, face), y(direction,face), z(direction,face)), v(x(direction,face)+xr(direction, face), y(direction,face), z(direction,face)+zr(direction, face)), v(x(direction,face), y(direction,face), z(direction,face)+zr(direction, face)), texture, direction, face));
            //up
            quads.add(createUpDownQuad(v(x(direction,face), y2(direction, face), z(direction,face)+zr(direction, face)), v(x(direction,face)+xr(direction, face), y2(direction,face), z(direction,face)+zr(direction, face)), v(x(direction,face)+xr(direction, face), y2(direction,face), z(direction,face)), v(x(direction,face), y2(direction, face), z(direction,face)), texture, direction, face));
            //sides
            quads.add(createLongSideQuad(v(x(direction,face), y(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face), y2(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face), y2(direction,face), z(direction, face)), v(x(direction,face), y(direction,face), z(direction, face)), texture, direction, face));
            quads.add(createLongSideQuad(v(x(direction,face)+xr(direction,face), y(direction,face), z(direction, face)), v(x(direction,face)+xr(direction,face), y2(direction,face), z(direction, face)), v(x(direction,face)+xr(direction,face), y2(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face)+xr(direction,face), y(direction,face), z(direction, face)+zr(direction, face)), texture, direction, face));
            quads.add(createShortSideQuad(v(x(direction,face), y(direction,face), z(direction, face)), v(x(direction,face), y2(direction,face), z(direction, face)), v(x(direction,face)+xr(direction,face), y2(direction,face), z(direction, face)), v(x(direction,face)+xr(direction,face), y(direction,face), z(direction, face)), texture, direction, face));
            quads.add(createShortSideQuad(v(x(direction,face)+xr(direction,face), y(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face)+xr(direction,face), y2(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face), y2(direction,face), z(direction, face)+zr(direction, face)), v(x(direction,face), y(direction,face), z(direction, face)+zr(direction, face)), texture, direction, face));

            return quads;
        }
        return Collections.emptyList();
    }

    //getXPositionForQuad
    private float x(Direction direction, AttachFace face) {
        float x;
        switch (face) {
            case CEILING:
            case FLOOR:
                switch (direction) {
                    case NORTH:
                    case SOUTH:
                        x = 5/16f;
                        break;
                    default:
                        x = 6/16f;
                        break;
                }
                break;
            default:
                switch (direction) {
                    case EAST:
                        x = -3/16f;
                        break;
                    case WEST:
                        x = 15/16f;
                        break;
                    case NORTH:
                    case SOUTH:
                        x = 5/16f;
                        break;
                    default:
                        x = 0.5f;
                        break;
                }
        }
        return x;
    }

    //getYPositionForQuad (bottom side)
    private float y(Direction direction, AttachFace face) {
        float y;
        switch (face) {
            case CEILING:
                y=15/16f;
                break;
            case FLOOR:
                y=0;
                break;
            default:
                y = 6/16f;
        }
        return y;
    }

    //getYPositionForQuad (top side)
    private float y2(Direction direction, AttachFace face) {
        float y;
        switch (face) {
            case CEILING:
                y=1;
                break;
            case FLOOR:
                y=1/16f;
                break;
            default:
                y = 10/16f;
        }
        return y;
    }

    //getZPositionForQuad
    private float z(Direction direction, AttachFace face) {
        float z;
        switch (face) {
            case CEILING:
            case FLOOR:
                switch (direction) {
                    case NORTH:
                    case SOUTH:
                        z = 6/16f;
                        break;
                    default:
                        z = 5/16f;
                        break;
                }
                break;
            default:
                switch (direction) {
                    case NORTH:
                        z = 15/16f;
                        break;
                    case SOUTH:
                        z = -3/16f;
                        break;
                    case EAST:
                    case WEST:
                        z = 5/16f;
                        break;
                    default:
                        z = 0.5f;
                        break;
                }
        }
        return z;
    }

    //"x-rotation" of quad (by 90degrees)
    private float xr(Direction direction, AttachFace face) {
        float r;
        switch (direction) {
            case SOUTH:
            case NORTH:
                r=6/16f;
                break;
            case WEST:
            case EAST:
                r=4/16f;
                break;
            default:
                r=0;
        }
        return r;
    }

    //"z-rotation" of quad (by 90degrees)
    private float zr(Direction direction, AttachFace face) {
        float r;
        switch (direction) {
            case SOUTH:
            case NORTH:
                r=4/16f;
                break;
            case WEST:
            case EAST:
                r=6/16f;
                break;
            default:
                r=0;
        }
        return r;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean func_230044_c_() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }
}
//========SOLI DEO GLORIA========//