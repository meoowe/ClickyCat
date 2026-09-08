extends CanvasLayer



func _on_game_score_changed(new_score: int) -> void:
	%scoire.text = "Score: " + str(new_score)
