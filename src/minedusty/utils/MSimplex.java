package minedusty.utils;

/** This is a simplex helper class used for custom Voronoi math functions. Used in planet generators */
public class MSimplex{
    private MSimplex(){};

    // Borrowed from Twcash
    public static float voronoi2d(int seed, float x, float y, float frequency){
        x *= frequency;
        y *= frequency;

        int xi = fastfloor(x);
        int yi = fastfloor(y);

        float minDist = Float.MAX_VALUE;

        for(int xo = -1; xo <= 1; xo++){
            for(int yo = -1; yo <= 1; yo++){
                int cx = xi + xo;
                int cy = yi + yo;

                float px = cx + rand2(seed, cx, cy);
                float py = cy + rand2(seed + 1, cx, cy);

                float dx = px - x;
                float dy = py - y;
                float dist = dx * dx + dy * dy;

                if(dist < minDist){
                    minDist = dist;
                }
            }
        }

        return Math.min(1f, (float)Math.sqrt(minDist));
    }
    public static float voronoi3d(int seed, double octaves, double persistence, double scale, double x, double y, double z){
        double total = 0;
        double frequency = scale;
        double amplitude = 1;
        double maxAmplitude = 0;

        for(int i = 0; i < octaves; i++){
            total += rawVoronoi3d(seed + i * 31, x * frequency, y * frequency, z * frequency) * amplitude;

            frequency *= 2;
            maxAmplitude += amplitude;
            amplitude *= persistence;
        }

        return (float)(total / maxAmplitude);
    }
    public static float rawVoronoi3d(int seed, double x, double y, double z){
        int xi = fastfloor(x);
        int yi = fastfloor(y);
        int zi = fastfloor(z);

        float minDist = Float.MAX_VALUE;

        for(int xo = -1; xo <= 1; xo++){
            for(int yo = -1; yo <= 1; yo++){
                for(int zo = -1; zo <= 1; zo++){
                    int cx = xi + xo;
                    int cy = yi + yo;
                    int cz = zi + zo;

                    double px = cx + rand3(seed, cx, cy, cz);
                    double py = cy + rand3(seed + 1, cx, cy, cz);
                    double pz = cz + rand3(seed + 2, cx, cy, cz);

                    double dx = px - x;
                    double dy = py - y;
                    double dz = pz - z;

                    float dist = (float)(dx * dx + dy * dy + dz * dz);

                    if(dist < minDist){
                        minDist = dist;
                    }
                }
            }
        }

        return (float)Math.sqrt(minDist); // optional: can return dist directly if you prefer squared distance
    }
    private static float rand3(int seed, int x, int y, int z){
        int hash = perm(seed, x + perm(seed, y + perm(seed, z)));
        return (hash & 0xFF) / 255f;
    }
    private static float rand2(int seed, int x, int y){
        int hash = perm(seed, x + perm(seed, y));
        return (hash & 0xFF) / 255f;
    }

    // Taken from arc.util.noise
    static int fastfloor(double x){
        return x > 0 ? (int)x : (int)x - 1;
    }
    //hash function: seed (any) + x (will be masked to fit in 0-255) -> 0-255
    //thanks to TEttinger on Discord for the negative coordinate discontinuity fix
    static int perm(int seed, int x){
        x = (x & 255) * 0x45d9f3b;
        x = ((x >>> 16) ^ x) * (0x45d9f3b + seed);
        x = (x >>> 16) ^ x;
        return x & 0xff;
    }
}
