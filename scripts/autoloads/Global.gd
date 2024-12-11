extends Node

@onready var music: AudioStreamPlayer = $music
@onready var bark: AudioStreamPlayer = $bark

var score: int = 0
var highScore: int = 0
var scoreIncrement: int = 10
var balloonClicked: bool = false
var playerName: String = "Nickname"

const VERSION: String = "2.0.2-10.12.24"
#const LEADERBOARD_ID: String = "clicky-cat-high-score-LQze"
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	music.play()

	
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass

func _bark():
	bark.play()
