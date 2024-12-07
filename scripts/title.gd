extends Control

@onready var vrsn: Label = $vrsn

func _ready() -> void:
	vrsn.text = Global.VERSION
func _on_play_pressed() -> void:
	get_tree().change_scene_to_file("res://game.tscn")


func _on_credits_pressed() -> void:
	get_tree().change_scene_to_file("res://credits.tscn")


func _on_quit_pressed() -> void:
	get_tree().quit()


func _on_texture_button_pressed() -> void:
	get_tree().change_scene_to_file("res://leaderboard.tscn")
