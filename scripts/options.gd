extends Control
@onready var game: Node2D = $"../.."



func _on_title_pressed() -> void:
	Engine.time_scale = 1
	Scenes.title()


func _on_play_pressed() -> void:
	Global.PlayClick()
	game.pause_game()
	print("press")
