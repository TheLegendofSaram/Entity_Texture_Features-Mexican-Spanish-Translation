package traben.emissive_entities.mixin.client.entity.multitexture;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.StriderEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import traben.emissive_entities.client.EmissiveFeatureRenderer;

import static traben.emissive_entities.client.EmissiveClient.addEmissiveRenderer;

@Mixin(StriderEntityRenderer.class)
public class MIX_StriderEntityRenderer {
    public StriderEntity mainEntity;

    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/StriderEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"))
    private void injected(StriderEntity striderEntity, CallbackInfoReturnable<Identifier> cir) {
        mainEntity = striderEntity;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injected(EntityRendererFactory.Context context, CallbackInfo ci) {

        LivingEntityRenderer renderer = (LivingEntityRenderer) (Object)this;
        FeatureRenderer[]adding= {new EmissiveFeatureRenderer(renderer){
            @Override
            public RenderLayer getEyesTexture() {
                //return RenderLayer.getEyes(new Identifier("textures/entity/chicken_e.png"));
                String path = getTexture(mainEntity).getPath().replace(".png","_e.png");
                return RenderLayer.getEyes(new Identifier(path));
            }


        }};
        addEmissiveRenderer(renderer,context,adding);
    }

   // this.features.add(new EndermanEyeFeatureRenderer(this))
}