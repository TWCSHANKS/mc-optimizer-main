package com.example.mcoptimizer;

import com.example.mcoptimizer.gui.OptimizerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class McOptimizerClient implements ClientModInitializer {

    // This is what opens the tool in-game. Default key is O; players can
    // rebind it in Options -> Controls -> Key Binds -> MC Optimizer.
    public static KeyBinding openOptimizerKey;

    @Override
    public void onInitializeClient() {
        openOptimizerKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcoptimizer.open",       // translation key, defined in lang file
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.mcoptimizer"        // keybind category shown in Controls menu
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openOptimizerKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new OptimizerScreen(null));
                }
            }
        });
    }
}
