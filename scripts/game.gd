extends Node2D

var is_paused: bool = false

signal paused(paused: bool)
signal score_changed(new_score: int)

func _on_update_score() -> void:
	Global.score += Global.scoreIncrement
	score_changed.emit(Global.score)
	if Global.score > Global.highScore:
		Global.highScore = Global.score

func pause_game():
	is_paused = !is_paused
	
	paused.emit(is_paused)
	if is_paused:
		$UI/options.show()
		Engine.time_scale = 0
	else: 
		$UI/options.hide()
		Engine.time_scale = 1

func _input(event: InputEvent) -> void:
	if event.is_action_pressed("pause"):
		print("pausing")
		paused.emit(is_paused)
		pause_game()
