package net.sixunderscore.oldvisuals.mixin.crosshair;

import net.minecraft.client.CameraType;
import net.minecraft.client.gui.Hud;
import net.sixunderscore.oldvisuals.config.RuntimeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Hud.class)
public class HudMixin {
	@Redirect(
			method = "extractCrosshair",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z"
			)
	)
	private boolean isFirstPerson(CameraType instance) {
		if (RuntimeConfig.enabledThirdPersonCrosshair) {
			return true;
		}

		return instance.isFirstPerson();
	}
}