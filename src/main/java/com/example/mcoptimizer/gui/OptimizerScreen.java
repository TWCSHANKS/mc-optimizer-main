package com.example.mcoptimizer.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.text.Text;

/**
 * Opened with the keybind registered in McOptimizerClient.
 * Each button applies a full preset directly to the live GameOptions,
 * the same object the vanilla video settings screen edits, so changes
 * take effect immediately with no restart.
 *
 * Note: exact GameOptions field/method names shift slightly between
 * Minecraft versions. If something doesn't compile, open GameOptions
 * in your IDE (Ctrl+Click through from MinecraftClient.getInstance().options)
 * and use autocomplete to find the current setter names for that version.
 */
public class OptimizerScreen extends Screen {

    protected OptimizerScreen(Screen parent) {
        super(Text.literal("MC Optimizer"));
        this.parent = parent;
    }

    private final Screen parent;

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2 - 70;
        int buttonWidth = 200;
        int spacing = 24;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Bare minimum"), b -> applyPreset(0))
                .dimensions(centerX - buttonWidth / 2, y, buttonWidth, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Light"), b -> applyPreset(1))
                .dimensions(centerX - buttonWidth / 2, y + spacing, buttonWidth, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("High"), b -> applyPreset(2))
                .dimensions(centerX - buttonWidth / 2, y + spacing * 2, buttonWidth, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Max quality"), b -> applyPreset(3))
                .dimensions(centerX - buttonWidth / 2, y + spacing * 3, buttonWidth, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(centerX - buttonWidth / 2, y + spacing * 5, buttonWidth, 20).build());
    }

    // Mirrors the four tiers from the web version of the optimizer.
    private void applyPreset(int tier) {
        GameOptions options = this.client.options;

        int[] renderDistance = {8, 10, 12, 16};
        int[] simulationDistance = {6, 8, 8, 10};
        GraphicsMode[] graphics = {GraphicsMode.FAST, GraphicsMode.FAST, GraphicsMode.FANCY, GraphicsMode.FANCY};
        ParticlesMode[] particles = {ParticlesMode.MINIMAL, ParticlesMode.DECREASED, ParticlesMode.ALL, ParticlesMode.ALL};
        boolean[] entityShadows = {false, false, true, true};
        int[] mipmap = {0, 2, 4, 4};
        boolean[] clouds = {false, true, true, true};

        options.getViewDistance().setValue(renderDistance[tier]);
        options.getSimulationDistance().setValue(simulationDistance[tier]);
        options.getGraphicsMode().setValue(graphics[tier]);
        options.getParticles().setValue(particles[tier]);
        options.getEntityShadows().setValue(entityShadows[tier]);
        options.getMipmapLevels().setValue(mipmap[tier]);
        options.getCloudRenderMode().setValue(clouds[tier]);

        options.write();
        this.client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
