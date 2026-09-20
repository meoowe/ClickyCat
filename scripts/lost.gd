extends Control
@export var label_2: Label
@export var camera: Camera2D

# Called when the node enters the scene tree for the first time.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	camera.position.x += Global.camera_scroll_speed * delta

func _ready() -> void:
	label_2.text = (
		"You lost with a score of "
		+ str(Global.score)
		+ "\nYour High Score is "
		+ str(Global.highScore)
	)
func _on_quit_pressed() -> void:
	Global.PlayClick()
	get_tree().quit()


func _on_title_pressed() -> void:
	Global.PlayClick()
	get_tree().change_scene_to_file("res://scenes/title.tscn")
