package net.mehvahdjukaar.polytone.biome;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.mehvahdjukaar.polytone.Polytone;
import net.mehvahdjukaar.polytone.colormap.Colormap;
import net.mehvahdjukaar.polytone.lightmap.ILightmapNumberProvider;
import net.mehvahdjukaar.polytone.utils.JsonImgPartialReloader;
import net.mehvahdjukaar.polytone.utils.JsonPartialReloader;
import net.mehvahdjukaar.polytone.utils.ReferenceOrDirectCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.Map;

//
public class BiomeIdMapperManager extends JsonPartialReloader {

    private static final BiMap<String, BiomeIdMapper> ID_MAPPERS = HashBiMap.create();


    public static final  Codec<BiomeIdMapper> REFERENCE_CODEC = ExtraCodecs.stringResolverCodec(
            a->ID_MAPPERS.inverse().get(a), ID_MAPPERS::get);
    public static final Codec<BiomeIdMapper> CODEC = new ReferenceOrDirectCodec<>(
            REFERENCE_CODEC, BiomeIdMapper.Custom.CODEC, false);


    public BiomeIdMapperManager() {
        super("biome_id_mappers");
    }

    @Override
    protected void reset() {
        ID_MAPPERS.clear();
    }

    @Override
    protected void process(Map<ResourceLocation, JsonElement> obj) {
        for (var j : obj.entrySet()) {
            var json = j.getValue();
            var id = j.getKey();
            var mapper = CODEC.decode(JsonOps.INSTANCE, json)
                    .getOrThrow(false, errorMsg -> Polytone.LOGGER.warn("Could not decode Biome ID mapper with json id {} - error: {}",
                            id, errorMsg)).getFirst();
            ID_MAPPERS.put(id.getPath(), mapper);
        }
    }

}
