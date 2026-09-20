extends Control

@onready var camera: Camera2D = $Camera2D

func _on_button_pressed() -> void:
	Scenes.title()

func _process(delta: float) -> void:
	camera.position.x += Global.camera_scroll_speed * delta
