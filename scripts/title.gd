extends Control

@onready var vrsn: Label = $vrsn
@onready var camera: Camera2D = $Camera2D
@export var scroll_speed: float = 100.0

func _process(delta: float) -> void:
	# Move the camera constantly to the right to drive the parallax effect
	camera.position.x += scroll_speed * delta

func _ready() -> void:
	vrsn.text = Global.VERSION

func _on_play_pressed() -> void:
	Global.PlayClick()
	Scenes.play()

func _on_credits_pressed() -> void:
	Global.PlayClick()
	Scenes.credits()

func _on_quit_pressed() -> void:
	Global.PlayClick()
	Scenes.quit()

func _on_texture_button_pressed() -> void:
	Global.PlayClick()
	Scenes.leaderboard()
