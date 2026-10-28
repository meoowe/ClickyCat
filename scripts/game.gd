extends Node2D

@export var camera: Camera2D
@export var cat: CharacterBody2D
@export var clouds: Parallax2D
@export var timer: Timer

var is_bounce_active: bool = false
var is_paused: bool = false

enum GameState {
	PAUSED,
	GAME_STARTED,
	GAME_READY
}
signal paused(paused: bool)

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
	Gamestate.game_has_started = false
	Gamestate.score = 0
	Global.dog_speed = 200
	clouds.autoscroll.x = Global.camera_scroll_speed
	cat.load_skin(load("res://skins/original.tres"))

func _on_losezone_body_entered(body: Node2D) -> void:
	if body == cat:
		await Global.wait(0.3)
		Scenes.lost()


func _on_move_area_pressed() -> void:
	cat.trigger_move_action()


func _on_bounce_body_entered(body: Node2D) -> void:
	if body == cat:
		timer.start(Global.bounce_timer)
		await timer.timeout
		print("spamming velocity")
		cat.velocity.y -= 10000
