package net.sixunderscore.oldvisuals;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.sixunderscore.oldvisuals.config.ModMenuConfigScreen;
import org.jspecify.annotations.NonNull;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public @NonNull ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuConfigScreen::new;
    }
}
