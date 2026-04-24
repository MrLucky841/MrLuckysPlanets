package net.mrlucky841.mlplanets.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.NotNull;

public class CeresDensityFunction implements DensityFunction {
    public static final MapCodec<CeresDensityFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("big_radius").forGetter(r -> r.big_radius),
                    Codec.DOUBLE.fieldOf("small_radius").forGetter(r -> r.small_radius),
                    Codec.DOUBLE.optionalFieldOf("center_x", 0.0).forGetter(r -> r.centerX),
                    Codec.DOUBLE.optionalFieldOf("center_z", 0.0).forGetter(r -> r.centerZ)
            ).apply(instance, CeresDensityFunction::new)
    );

    public static final KeyDispatchDataCodec<CeresDensityFunction> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);
    private final double big_radius;
    private final double small_radius;
    private final double maxValue;
    private final double minValue;
    private final double centerX;
    private final double centerZ;


    public CeresDensityFunction(double small_radius, double big_radius, double centerX, double centerZ) {
        this.small_radius = small_radius;
        this.big_radius = big_radius;
        this.maxValue = 256;
        this.minValue = 0;
        this.centerX = centerX;
        this.centerZ = centerZ;
    }

    //Actual chunk-genning goes here!!! (output is height!)
    @Override
    public double compute(FunctionContext context) {
        //do Perlin noise

        //create old flat craters

        //create new pockmark craters


        return 1;
    }

    @Override
    public void fillArray(double[] arr, @NotNull ContextProvider provider) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = compute(provider.forIndex(i));
        }
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return this; //supposed to map children? whose children?
    }

    @Override
    public @NotNull KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    @Override
    public double minValue() {
        return minValue;
    }

    @Override
    public double maxValue() {
        return maxValue;
    }
}