extends Control

@onready var vrsn: Label = $UI/vrsn
@export var camera: Camera2D

func _process(delta: float) -> void:
	# Move the camera constantly to the right to drive the parallax effect
	camera.position.x += Global.camera_scroll_speed * delta
	camera.position.y += Global.camera_scroll_speed * delta

func _ready() -> void:
	vrsn.text = Global.VERSION
	var root = get_tree().root
	var my_autoload = root.get_node("/root/ScreenFader")
	# Move the autoload to the very bottom of the root viewport children list
	root.move_child.call_deferred(my_autoload, root.get_child_count() - 1)

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
