extends CanvasLayer

@export var fade_rect: ColorRect
@export var animation_player: AnimationPlayer

var playing_fade: bool

func _ready() -> void:
	fade_rect.mouse_filter = Control.MOUSE_FILTER_IGNORE

func fade_dark() -> int:
	playing_fade = true
	fade_rect.mouse_filter = Control.MOUSE_FILTER_STOP
	animation_player.play("fade_to_black")
	while playing_fade:
		await Global.wait(0.1)
	return 0

	
func fade_clear() -> int:
	playing_fade = true
	fade_rect.mouse_filter = Control.MOUSE_FILTER_IGNORE
	animation_player.play("fade_to_clear")
	while playing_fade:
		await Global.wait(0.1)
	return 0

func _on_animation_player_animation_finished(_anim_name: StringName) -> void:
	playing_fade = false
