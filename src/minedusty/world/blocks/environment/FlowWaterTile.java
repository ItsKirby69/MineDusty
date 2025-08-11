package minedusty.world.blocks.environment;

// Make rotatable later
public class FlowWaterTile extends WaterTileEffect{

    public FlowWaterTile(String name) {
        this(name, 0);
    }

    public FlowWaterTile(String name, int variants) {
        super(name);
        this.variants = variants;
        rotate = true;
        update = true;
    }
}
