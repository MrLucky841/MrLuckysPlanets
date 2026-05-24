package net.mrlucky841.mlplanets.worldgen.densityfunctions;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

public class VolcanoDensityFunction extends BlendedNoise {

    private final double[] hashTable = {-0.20875336, 0.88035103, 0.63394067, 0.54108980, 0.33953086, 0.79952693, 0.11931760, -0.18090021, 0.46677120, -0.67752001, 0.95838907,
            -0.66623730, -0.28497610, -0.10058655, -0.42615528, 0.36745468, 0.34878287, 0.79287894, 0.55349598, -0.24834600, -0.18215664, 0.65174700, -0.19745490, -0.14457104,
            -0.19787248, 0.55661061, 0.03180509, -0.77074035, -0.66670157, 0.22539977, -0.05054034, -0.73831604, -0.24005372, 0.11151812, 0.10871683, -0.06996236, 0.46223359,
            0.20566039, -0.09298309, -0.82270803, 0.87673090, 0.07745567, 0.24383239, -0.39530484, -0.85786566, 0.87787277, -0.45636817, -0.46024670, -0.63168319, 0.45919318,
            -0.39061618 ,-0.22879296,0.51821675,0.38791147,-0.20711137,0.79678091,0.65407252,-0.66142532,-0.61016059,-0.66295559,-0.50504015,-0.05902441,-0.59530774,-0.72366174,
            -0.98425772,-0.23727283,-0.36973815,0.12728247,0.64306247,0.50930035,0.30325419,0.51043747,0.08962087,-0.72479448,0.16616445,0.32620902,-0.61414878,-0.32146758,
            -0.18889290,0.42166590,-0.67309746,-0.69000136,-0.35100700,-0.91128097,0.36118886,0.83177936,0.08531630,-0.46781795,-0.00808856,0.36888002,0.00456305,0.15542399,
            -0.09600539,-0.85819835,0.97844148,-0.34087843,-0.29532261,0.67443420,0.69487581,0.40503177};
    private final long seed;
    private final double scale;

    public static final KeyDispatchDataCodec<VolcanoDensityFunction> CODEC = KeyDispatchDataCodec.of(Codec
            .doubleRange(Double.MIN_VALUE, 1)
            .fieldOf("scale")
            .xmap(VolcanoDensityFunction::new, function -> function.scale)

    );

    public VolcanoDensityFunction(double scale) {
        this(new XoroshiroRandomSource(0L), scale);
    }
    public VolcanoDensityFunction(RandomSource randomSource, double scale) {
        super(randomSource, 0, 0, 0, 0, 0);

        this.seed = randomSource.nextLong();
        this.scale = scale;
    }

    //what does this bit do?
    @Override
    public BlendedNoise withNewRandom(RandomSource random) {
        return new VolcanoDensityFunction(random, scale);
    }

    @Override
    public double compute(FunctionContext context) {
        double x = context.blockX();
        double y = context.blockY();
        double z = context.blockZ();

        double out = 1; //put volcano geometry in here (cone with interior cone?)

        return y>7 ? 0 : out; //<<<DONT DO THIS!
    }

    private double crater(double x, double z, double poi_dist, double radius, double offX, double offZ,
                          double base, double slope, double probability, double seedOffset) { //ie, b^b^((x-r)/s) - b, min of zero
        Pair<Double, Double> poi = poiPoints(x, z, poi_dist, offX, offZ);
        double Cx = poi.getFirst();
        double Cz = poi.getSecond();

        if (hash(Cx+x,Cz+z, seedOffset)<probability) {
            double out = Math.max(Math.sqrt(Cx*Cx + Cz*Cz),0);
            return Math.min(0, Math.pow(base, Math.pow(base, (out-radius)/slope))-base);
        } else {return 0;}
    }


    private Pair<Double, Double> poiPoints(double x, double z, double a, double offX, double offZ) {
        double Cx = Math.round((x+offX)/a)*a - x;
        double Cz = Math.round((z+offZ)/a)*a - z;

        return Pair.of(Cx,Cz);
    }

    private double hash(double x, double z, double rand) {
        //domain 0-1, it's a probability thing
        long t = Math.round(x*x % 7 + z*z % 11);
        double out = seed * rand;
        for (int i = 0; i < t; i++) {
            out = out * (hashTable[i%hashTable.length]);
        }
        //String str = "" + Math.round(out);
        //return (Double.parseDouble(String.valueOf(str.charAt(2) + str.charAt(3) + str.charAt(4)))/Math.PI) % 1;
        if (out < 0.1) {
            return Math.abs(1/out) % 1;
        }
        return Math.abs(out) % 1;
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
    public KeyDispatchDataCodec<? extends VolcanoDensityFunction> codec() {
        return CODEC;
    }
}