extends Control
@onready var label: Label = $Label


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	label.text = "You won with a score of " + str(Global.score) + "\nYour High Score is " + str(Global.highScore)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass


func _on_title_pressed() -> void:
	get_tree().change_scene_to_file("res://title.tscn")
