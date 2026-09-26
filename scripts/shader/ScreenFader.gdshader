shader_type canvas_item;

uniform float progress : hint_range(0.0, 1.0) = 0.0;
uniform sampler2D SCREEN_TEXTURE : hint_screen_texture, filter_linear_mipmap;

void fragment() {
	vec4 screen_color = texture(SCREEN_TEXTURE, SCREEN_UV);
	// Darken the screen color to black based on progress
	vec3 final_color = mix(screen_color.rgb, vec3(0.0), progress);
	COLOR = vec4(final_color, 1.0);
}
