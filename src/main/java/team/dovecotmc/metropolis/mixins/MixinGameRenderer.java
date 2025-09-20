package team.dovecotmc.metropolis.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.dovecotmc.metropolis.Metropolis;
import team.dovecotmc.metropolis.abstractinterface.util.MALocalizationUtil;

import java.util.Optional;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2024 Arrokoth All Rights Reserved.
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderTail(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        PoseStack matrices = new PoseStack();
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.assertOnRenderThread();
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(Metropolis.MOD_ID);
        if (modContainer.isEmpty()) {
            return;
        }
        ModContainer mod = modContainer.get();

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());

        matrices.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        guiGraphics.drawString(mc.font, mod.getMetadata().getName() + " " + mod.getMetadata().getVersion().getFriendlyString(), 4, 4, 0xFFFFFF);
        guiGraphics.drawString(mc.font, "This is an alpha version", 4, 4 + (2 + mc.font.lineHeight), 0xFFFFFF);
        guiGraphics.drawString(mc.font, "It might cause incompatible issues", 4, 4 + (2 + mc.font.lineHeight) * 2, 0xFFFFFF);

        matrices.popPose();
    }
}