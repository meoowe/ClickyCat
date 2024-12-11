extends Control
@onready var label_2: Label = $Panel/VBoxContainer/Label2

# Called when the node enters the scene tree for the first time.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	label_2.text = (
		"You lost with a score of "
		+ str(Global.score)
		+ "\nYour High Score is "
		+ str(Global.highScore)
	)


func _on_quit_pressed() -> void:
	get_tree().quit()


func _on_title_pressed() -> void:
	get_tree().change_scene_to_file("res://title.tscn")
