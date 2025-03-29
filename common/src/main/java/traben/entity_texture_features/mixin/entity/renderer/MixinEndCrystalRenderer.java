package traben.entity_texture_features.mixin.entity.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import traben.entity_texture_features.ETF;

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer {

    @Shadow @Final private static ResourceLocation END_CRYSTAL_LOCATION;

    @ModifyArg(method = "render(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private RenderType etf$modifyTexture(final RenderType renderType) {
        if (ETF.config().getConfig().canDoCustomTextures()) {
            // recreate each frame so ETF can modify
            return RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
        }
        return renderType;
    }

    //todo multiversioning
}


