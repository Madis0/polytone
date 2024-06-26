package net.mehvahdjukaar.polytone;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.mehvahdjukaar.polytone.biome.BiomeEffectsManager;
import net.mehvahdjukaar.polytone.biome.BiomeIdMapper;
import net.mehvahdjukaar.polytone.biome.BiomeIdMapperManager;
import net.mehvahdjukaar.polytone.block.BlockPropertiesManager;
import net.mehvahdjukaar.polytone.color.ColorManager;
import net.mehvahdjukaar.polytone.colormap.ColormapsManager;
import net.mehvahdjukaar.polytone.fluid.FluidPropertiesManager;
import net.mehvahdjukaar.polytone.lightmap.LightmapsManager;
import net.mehvahdjukaar.polytone.particle.ParticleModifiersManager;
import net.mehvahdjukaar.polytone.slotify.GuiModifierManager;
import net.mehvahdjukaar.polytone.sound.SoundTypesManager;
import net.mehvahdjukaar.polytone.texture.VariantTextureManager;
import net.mehvahdjukaar.polytone.utils.CompoundReloader;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Polytone {
    public static final String MOD_ID = "polytone";

    public static final Logger LOGGER = LogManager.getLogger("Polytone");

    public static final BlockPropertiesManager BLOCK_PROPERTIES = new BlockPropertiesManager();
    public static final FluidPropertiesManager FLUID_PROPERTIES = new FluidPropertiesManager();
    public static final BiomeEffectsManager BIOME_EFFECTS = new BiomeEffectsManager();
    public static final ColormapsManager COLORMAPS = new ColormapsManager();
    public static final LightmapsManager LIGHTMAPS = new LightmapsManager();
    public static final BiomeIdMapperManager BIOME_ID_MAPPERS = new BiomeIdMapperManager();
    public static final ParticleModifiersManager PARTICLE_MODIFIERS = new ParticleModifiersManager();
    public static final SoundTypesManager SOUND_TYPES = new SoundTypesManager();
    public static final VariantTextureManager VARIANT_TEXTURES = new VariantTextureManager();
    public static final ColorManager COLORS = new ColorManager();
    public static final GuiModifierManager GUI_MODIFIER = new GuiModifierManager();

    public static boolean sodiumOn = false;

    public static void init(boolean isSodiumOn) {
        PlatStuff.addClientReloadListener(() -> new CompoundReloader(
                        SOUND_TYPES, BIOME_ID_MAPPERS, COLORMAPS, COLORS, BLOCK_PROPERTIES, FLUID_PROPERTIES,
                        BIOME_EFFECTS, VARIANT_TEXTURES, LIGHTMAPS, PARTICLE_MODIFIERS),
                res("block_properties_manager"));
        sodiumOn = isSodiumOn;
        PlatStuff.addClientReloadListener(() -> GUI_MODIFIER, res("gui_modifiers"));
        //TODO: colormap for particles
        //SKY and fog
        //block particles
        //item properties and color. cache pllayer coord
        //exp color
    }

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }


    public static void onTagsReceived(RegistryAccess registryAccess) {
        BIOME_EFFECTS.doApply(registryAccess, true);
    }



}
