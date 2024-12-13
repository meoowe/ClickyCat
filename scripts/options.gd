extends Control
@onready var cat: Area2D = $"../cat"


func _on_title_pressed() -> void:
	Engine.time_scale = 1
	get_tree().change_scene_to_file("res://title.tscn")


func _on_play_pressed() -> void:
	Global.PlayClick()
	cat.pause()
	print("press")
