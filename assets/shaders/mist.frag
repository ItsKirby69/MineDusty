#define HIGHP
uniform sampler2D u_texture;

varying vec2 v_texCoords;

void main() {
    vec4 tex = texture2D(u_texture, v_texCoords);
    
    float a = min(0.8, tex.a);
    if(a <= 0.0) discard;

    vec4 color = vec4(0.949,0.969,1.0, a);

	gl_FragColor = color;
}