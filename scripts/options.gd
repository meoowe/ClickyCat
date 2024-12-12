extends Control
@onready var cat: Area2D = $"../cat"


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass  # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass


func _on_title_pressed() -> void:
	Engine.time_scale = 1
	get_tree().change_scene_to_file("res://title.tscn")


func _on_play_pressed() -> void:
	cat.pause()
