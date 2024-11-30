extends Node

@onready var music: AudioStreamPlayer = $music
var score: int = 0
var highScore: int = 0
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	music.play()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass
