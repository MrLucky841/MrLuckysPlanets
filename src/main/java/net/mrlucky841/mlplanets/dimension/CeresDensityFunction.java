package net.mrlucky841.mlplanets.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class CeresDensityFunction extends BlendedNoise {

    private final long seed;
    private final double scale;

    public static final KeyDispatchDataCodec<CeresDensityFunction> CODEC = KeyDispatchDataCodec.of(Codec
            .doubleRange(Double.MIN_VALUE, 1)
            .fieldOf("scale")
            .xmap(CeresDensityFunction::new, function -> function.scale)
    );

    public CeresDensityFunction(double scale) {
        this(new XoroshiroRandomSource(0L), scale);
    }
    public CeresDensityFunction(RandomSource randomSource, double scale) {
        super(randomSource, 0, 0, 0, 0, 0);

        this.seed = randomSource.nextLong();
        this.scale = scale;
    }

    //what does this bit do?
    @Override
    public BlendedNoise withNewRandom(RandomSource random) {
        return new CeresDensityFunction(random, scale);
    }

    @Override
    public double compute(FunctionContext context) {
        //def use the seed parameter in here
        double x = context.blockX();
        double y = context.blockY();
        double z = context.blockZ();
        double s = 64 + (5 * Math.cos(Math.sqrt((x*x)+(z*z))));

        return y>s ? 0: 1;
    }

    //why do i need to override these
    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends CeresDensityFunction> codec() {
        return CODEC;
    }
}