package net.hexagoat49.evangelion.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.hexagoat49.evangelion.Evangelion;
import net.hexagoat49.evangelion.entity.client.model.Eva01Model;
import net.hexagoat49.evangelion.entity.custom.Eva01;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Eva01Renderer extends GeoEntityRenderer<Eva01> {

    public Eva01Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Eva01Model());
    }

    @Override
    public ResourceLocation getTextureLocation(Eva01 animatable) {
        return new ResourceLocation(Evangelion.MOD_ID, "textures/entity/eva_01.png");
    }

    @Override
    public void render(Eva01 entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    // Method for rendering the arms in first person view
    @Override
    public void actuallyRender(PoseStack poseStack, Eva01 animatable, BakedGeoModel model, RenderType renderType,
                               MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                               float partialTick, int packedLight, int packedOverlay,
                               float red, float green, float blue, float eva01) {

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer,
                isReRender, partialTick, packedLight, packedOverlay, red, green, blue, eva01);

        if(animatable.isInvisibleTo(Minecraft.getInstance().player)) {
            GeoBone right_arm = model.getBone("r_arm").get();
            GeoBone left_arm = model.getBone("l_arm").get();

            poseStack.rotateAround(Axis.YP.rotationDegrees(180f), 0f, animatable.getBbHeight(), 0f);
            float lerpBodyRot = Mth.rotLerp(partialTick, animatable.yBodyRotO, animatable.yBodyRot);
            applyRotations(animatable, poseStack, animatable.tickCount + partialTick, lerpBodyRot, partialTick);

            super.renderRecursively(poseStack, animatable, right_arm,
                    renderType, bufferSource, buffer, isReRender, partialTick,
                    packedLight, packedOverlay, red, green, blue, eva01);

            super.renderRecursively(poseStack, animatable, left_arm,
                    renderType, bufferSource, buffer, isReRender, partialTick,
                    packedLight, packedOverlay, red, green, blue, eva01);
        }
    }
}
