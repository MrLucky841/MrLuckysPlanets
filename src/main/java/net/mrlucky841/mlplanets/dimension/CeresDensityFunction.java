package net.mrlucky841.mlplanets.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class CeresDensityFunction extends BlendedNoise {

    private final double[] hashTable = {0.485457726,-0.65891425,0.36984526,0.75128652,-0.59187436,0.554803547,-0.23984124,0.38456953,
            -0.14863259,-0.86254129,0.41217953,-0.48175326,0.23847535};
    private final double sqrt2 = 1.41421;
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
        double x = context.blockX();
        double y = context.blockY();
        double z = context.blockZ();

        //rough out terrain features
        double h = 64;

        //create mega craters
        double dist = poiPoints(x,z,196,0.5);
        //h = h - Math.pow(63-dist,3)/500; //these constants shape the crater
        h = dist;
        //create dunes OR


        //OR create pockmark craters



        return y>h ? 0 : 1;
    }

    private double poiPoints(double x, double z, double d, double p) {
        //get (inverse) distance from nearest poi point
        double Cx = Math.round(x/d)*d - x;
        double Cz = Math.round(z/d)*d - z;

        //think of this function like a square grid of touching circles. Void space between circles is always 0
        //returns zero when probability not met, otherwise returns 45deg cone shape
        return hash(Cx+x,Cz+z)<p ? Math.max(Math.sqrt(Cx*Cx + Cz*Cz)+1-d/2,0) : 0;
    }

    private double hash(double x, double z) {
        //domain 0-1, it's a probability thing
        long t = Math.round(x*x % 7 + z*z % 11);
        double out = seed;
        for (int i = 0; i < t; i++) {
            out = out * hashTable[i%hashTable.length];
        }
        String str = "" + Math.round(out);
        return Double.parseDouble(String.valueOf(str.charAt(3) + str.charAt(5) + str.charAt(4)))/1000;
        //return Math.abs(out)%1;
    }

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