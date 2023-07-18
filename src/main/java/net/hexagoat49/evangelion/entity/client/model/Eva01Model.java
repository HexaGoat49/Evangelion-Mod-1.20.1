package net.hexagoat49.evangelion.entity.client.model;

import net.hexagoat49.evangelion.Evangelion;
import net.hexagoat49.evangelion.entity.custom.Eva01;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Eva01Model extends GeoModel<Eva01> {
    @Override
    public ResourceLocation getModelResource(Eva01 animatable) {
        return new ResourceLocation(Evangelion.MOD_ID, "geo/eva_01.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Eva01 animatable) {
        return new ResourceLocation(Evangelion.MOD_ID, "textures/entity/eva_01.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Eva01 animatable) {
        return new ResourceLocation(Evangelion.MOD_ID, "animations/eva_01.animation.json");
    }

    @Override
    public void setCustomAnimations(Eva01 animatable, long instanceId, AnimationState<Eva01> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
