#define HIGHP

uniform sampler2D u_texture;
uniform sampler2D u_noise;
uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;
vec3 yellow = vec3(1.0,1.0,0.0);
vec3 blue = vec3(0.0,0.5,1.0);

void main() {
    vec2 c = v_texCoords.xy;
    vec2 coords = vec2(c.x * u_resolution.x + u_campos.x, c.y * u_resolution.y + u_campos.y);
    
    float noise = texture2D(u_noise, coords / 1200.0 + u_time / 2000.0).r;

    float intensity = noise * 0.7;
    
    vec4 color = texture2D(u_texture, c);

    float blending = (sin(u_time / 200.0) + 1.0) * 0.5;
    blending = smoothstep(0.3, 0.7, blending);
    vec3 tintColor = mix(yellow, blue, blending);

    color.rgb *= (1.0 + intensity * tintColor);
    
    gl_FragColor = color;
}