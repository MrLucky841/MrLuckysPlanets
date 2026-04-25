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
                    Codec.INT.fieldOf("big_radius").forGetter(r -> r.big_radius),
                    Codec.INT.fieldOf("small_radius").forGetter(r -> r.small_radius)
            ).apply(instance, CeresDensityFunction::new)
    );

    public static final KeyDispatchDataCodec<CeresDensityFunction> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);
    private final int big_radius;
    private final int small_radius;
    private final int maxValue;
    private final int minValue;


    public CeresDensityFunction(int small_radius, int big_radius) {
        this.small_radius = small_radius;
        this.big_radius = big_radius;
        this.maxValue = 1;
        this.minValue = 0;
    }

    //Actual chunk-genning goes here!!! (output is whatever I want, used directly, it is 0 for air, 1 for block)
    @Override
    public double compute(FunctionContext context) {
        //do Perlin noise

        //create old flat craters

        //create new pockmark craters

        //test function
        int x = context.blockX();
        int y = context.blockY();
        int z = context.blockZ();
        double s = 64 + (5 * Math.cos((x*x+z*z)));

        return y>s ? 0: 1;
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
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }
}