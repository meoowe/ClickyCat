extends Area2D

@onready var cat: CharacterBody2D = $"../cat"
var win_recorded := false

func _on_body_entered(body: Node2D) -> void:
	if body == cat and !Global.debug.disableWin and not win_recorded:
		win_recorded = true
		Gamestate.games_won += 1
		AchivementManager.on_game_won()
		Scenes.won()
