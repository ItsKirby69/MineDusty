#define HIGHP
uniform sampler2D u_texture;

varying vec2 v_texCoords;

void main() {
    vec4 tex = texture2D(u_texture, v_texCoords);
    
    float a = min(0.6, tex.a);
    if(a <= 0.0) discard;

    vec4 color = vec4(0.313, 0.451, 0.216, a);

	gl_FragColor = color;
}