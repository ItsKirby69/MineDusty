package minedusty.graphics;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.FrameBuffer;
import mindustry.graphics.Layer;

// Taken from Aquarion's shadow buffer
public class Renderer {
    public static FrameBuffer buffer;

    public static void draw() {
        if (buffer == null) buffer = new FrameBuffer();
        buffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());

        Draw.drawRange(Layer.legUnit + 3.77f, 0.01f,
            () -> {
                buffer.begin(Color.clear);
                Draw.blend(Blending.normal);
            },
            () -> {
                Draw.blend();
                buffer.end();

                Draw.blend(Blending.additive);
                buffer.blit(DustShaders.bog);
                Draw.blend();
            });
        Draw.drawRange(Layer.effect + 1.22f, 0.01f,
            () -> {
                buffer.begin(Color.clear);
                Draw.blend(Blending.normal);
            },
            () -> {
                Draw.blend();
                buffer.end();

                Draw.blend(Blending.additive);
                buffer.blit(DustShaders.mist);
                Draw.blend();
            });
    }
}
