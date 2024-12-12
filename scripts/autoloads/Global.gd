extends Node

@onready var music: AudioStreamPlayer = $music
@onready var bark: AudioStreamPlayer = $bark

var score: int = 0
var highScore: int = 0
var scoreIncrement: int = 10
var balloonClicked: bool = false
var playerName: String = "Nickname"
const LEADERBOARD_ID: String = "clicky-cat-high-score-LQze"
const VERSION: String = "2.0.3-10.12.24"
var platform: String = ""
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	if OS.has_feature("web_android"):
		platform = "web-android"
	if OS.has_feature("web_ios"):
		platform = "web-ios"
	if OS.has_feature("android"):
		platform = "android"
	if OS.has_feature("ios"):
		platform = "ios"
	if OS.has_feature("web_macos"):
		platform = "web-macos"
	if OS.has_feature("windows"):
		platform = "windows"
	music.play()

	
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass

func _bark():
	bark.play()
