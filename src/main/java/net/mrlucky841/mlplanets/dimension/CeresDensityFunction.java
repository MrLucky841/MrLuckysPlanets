package net.mrlucky841.mlplanets.dimension;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class CeresDensityFunction extends BlendedNoise {

    private final double[] hashTable = {0.485457726,-0.65891425,0.36984526,0.75128652,-0.59187436,0.554803547,-0.23984124,0.38456953,
            -0.14863259,-0.86254129,0.41217953,-0.48175326,0.23847535}; //TODO:make this bigger? Automate hashTable creation
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

        double h = 64; //baseline depth

        //rough out terrain features


        //create mega craters
        double dist = crater(x,z,72,20.3, 20, 0.005,0.5);
        h = h + dist;

        //create dunes OR


        //OR create pockmark craters



        return y>h ? 0 : 1;
    }

    private double crater(double x, double z, double poi_dist, double radius,
                          double depth, double slope, double probability) {
        Pair<Double, Double> poi = poiPoints(x, z, poi_dist);
        double Cx = poi.getFirst();
        double Cz = poi.getSecond();

        if (hash(Cx+x,Cz+z)<probability) {
            double out = Math.max(Math.sqrt(Cx*Cx + Cz*Cz),0);
            //return Math.min(Math.max(0,Math.pow(out-radius+2.75+0.07*depth,3)*slope),depth) -depth;
            return Math.min(depth, Math.pow(slope, Math.pow(slope, out)) - slope);
        } else {return 0;}
    }

    private Pair<Double, Double> poiPoints(double x, double z, double a) {
        double Cx = Math.round(x/a)*a - x;
        double Cz = Math.round(z/a)*a - z;

        return Pair.of(Cx,Cz);
    }

    private double hash(double x, double z) {
        //domain 0-1, it's a probability thing
        long t = Math.round(x*x % 7 + z*z % 11);
        double out = seed;
        for (int i = 0; i < t; i++) {
            out = out * (hashTable[i%hashTable.length]);
        }
        String str = "" + Math.round(out);
        return (Double.parseDouble(String.valueOf(str.charAt(2) + str.charAt(3) + str.charAt(4)))/Math.PI) % 1;
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