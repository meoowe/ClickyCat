extends Node

@onready var music: AudioStreamPlayer = $music
@onready var bark: AudioStreamPlayer = $bark

var score: int = 0
var highScore: int = 0
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	music.play()

	
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass

func _bark():
	await get_tree().create_timer(3).timeout
	bark.play()
