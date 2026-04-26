package net.mrlucky841.mlplanets.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class PlanetDensityFunctions {
    public static DensityFunction ceresNoise(Holder<NormalNoise.NoiseParameters> noiseParametersHolder) {
        return ceresNoise(noiseParametersHolder, 1);
    } //not sure why id need to use overloading here

    public static DensityFunction ceresNoise(Holder<NormalNoise.NoiseParameters> noiseParameters, int var) {
        return new PlanetDensityFunctions.CeresNoise(new DensityFunction.NoiseHolder(noiseParameters), var);
    }

    private record CeresNoise(DensityFunction.NoiseHolder noise, int var) implements DensityFunction {
        public static final MapCodec<PlanetDensityFunctions.CeresNoise> DATA_CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(PlanetDensityFunctions.CeresNoise::noise),
                    //Codec.DOUBLE.fieldOf("xz_scale").forGetter(DensityFunctions.Noise::xzScale), //ie, others go here
                    Codec.INT.fieldOf("var").forGetter(PlanetDensityFunctions.CeresNoise::var)
            ).apply(instance, PlanetDensityFunctions.CeresNoise::new);
        });

        public static final KeyDispatchDataCodec<PlanetDensityFunctions.CeresNoise> CODEC = PlanetDensityFunctions.makeCodec(DATA_CODEC);

        public double compute(DensityFunction.FunctionContext context) {
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

        public void fillArray(double[] arr, DensityFunction.ContextProvider context) {
            context.fillAllDirectly(arr, this);
        }

        public DensityFunction mapAll(DensityFunction.Visitor visitor) {
            return visitor.apply(new PlanetDensityFunctions.CeresNoise(visitor.visitNoise(this.noise), this.var));
        }

        public double minValue() {
            return -this.maxValue();
        }

        public double maxValue() {
            return this.noise.maxValue();
        }

        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    static <T> KeyDispatchDataCodec<T> makeCodec(MapCodec<T> mapCodec) {
        return KeyDispatchDataCodec.of(mapCodec);
    }
}
