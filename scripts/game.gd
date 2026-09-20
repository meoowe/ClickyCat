extends Node2D

@export var camera: Camera2D
@export var cat: CharacterBody2D
@export var clouds: Parallax2D

@export var decay := 0.8          # How quickly the shake stops (0 to 1)
@export var max_offset := Vector2(100, 75)  # Maximum horizontal/vertical shake in pixels
var trauma := 0.0                 # Current trauma level (0 to 1)
var trauma_power := 2             # Trauma exponent for non-linear decay

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
	Global.game_has_started = false
	Global.score = 0
	Global.dog_speed = 200
	clouds.autoscroll.x = Global.camera_scroll_speed

	


func _on_losezone_body_entered(body: Node2D) -> void:
	if body == cat:
		await Global.wait(0.3)
		Scenes.lost()
