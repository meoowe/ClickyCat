extends Node2D

@export var camera: Camera2D

var is_paused: bool = false

signal paused(paused: bool)
signal score_changed(new_score: int)

func _on_update_score() -> void:
	Global.score += Global.scoreIncrement
	score_changed.emit(Global.score)
	if Global.score > Global.highScore:
		Global.highScore = Global.score
		Global.new_high_score = true

func pause_game():
	is_paused = !is_paused
	if is_paused:
		$UI/options.show()
		Engine.time_scale = 0
		is_paused = true
	else: 
		$UI/options.hide()
		Engine.time_scale = 1
		is_paused = false
	paused.emit(is_paused)

func _input(event: InputEvent) -> void:
	if event.is_action_pressed("pause"):
		print("pausing")
		paused.emit(is_paused)
		pause_game()

func _ready() -> void:
	if Global.new_high_score:
		Global.highScore = Global.score
	Global.new_high_score = false
	
func _process(delta: float) -> void:
	camera.position.x += Global.camera_scroll_speed * delta
