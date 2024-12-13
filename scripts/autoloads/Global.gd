extends Node

@onready var music: AudioStreamPlayer = $music
@onready var bark: AudioStreamPlayer = $bark
@onready var click: AudioStreamPlayer = $click

var score: int = 0
var highScore: int = 0
@export var scoreIncrement: int = 10
var balloonClicked: bool = false
@export var playerName: String = "Nickname"
@export var LEADERBOARD_ID: String = "clicky-cat-clickycat-njjy"
@export var VERSION: String = "2.0.3-10.12.24"
var platform: String = ""


func _ready() -> void:
	determine_platform()
	music.play()


func determine_platform():
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


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass

func _bark():
	bark.play()

func PlayClick():
	click.play()
