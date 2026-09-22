extends Area2D

@onready var cat: CharacterBody2D = $"../cat"

func _on_body_entered(body: Node2D) -> void:
	if body == cat and !Global.debug.disableWin:
		Scenes.won()
		Global.games_won += 1
