package minedusty.graphics;

import arc.math.*;
import mindustry.*;

import static arc.Core.*;
import static arc.math.Mathf.*;

/** Taken from Meep's 3d psuedo code for the tantros-test-java repo. */
public class DrawPseudo3D{
    public static float xHeight(float x, float height){
        if(height <= 0) return x;
        return x + xOffset(x, height);
    }

    public static float yHeight(float y, float height){
        if(height <= 0) return y;
        return y + yOffset(y, height);
    }

    public static float xOffset(float x, float height){
        return (x - camera.position.x) * hMul(height);
    }

    public static float yOffset(float y, float height){
        return (y - camera.position.y) * hMul(height);
    }

    public static float hScale(float height){
        return 1f + hMul(height);
    }

    public static float hMul(float height){
        return height * Vars.renderer.getDisplayScale();
    }

    public static float layerOffset(float x, float y){
        float max = Math.max(camera.width, camera.height);
        return -dst(x, y, camera.position.x, camera.position.y) / max / 1000f;
    }

    public static float heightFade(float height){
        float scl = hScale(height);
        return 1f - Mathf.curve(scl, 1.5f, 7f);
    }
}