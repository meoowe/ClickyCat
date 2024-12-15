extends Node

var base_log = Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE)

@onready var music: AudioStreamPlayer = $music
@onready var bark: AudioStreamPlayer = $bark
@onready var click: AudioStreamPlayer = $click

@export var scoreIncrement: int = 10
@export var playerName: String = "Nickname"
@export var LEADERBOARD_ID: String = "clicky-cat-clickycat-njjy" #TODO: Refactor this to be lowercase
@export var VERSION: String = ""

var score: int = 0
var highScore: int = 0
var balloonClicked: bool = false
var platform: String = ""

var platforms = {
	"web": "web",
	"android": "android",
	"linux": "linux",
	"windows": "windows"
}

func _ready() -> void:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Ready!").color(Color.CHARTREUSE).info()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Version: ", VERSION).info()
	determine_platform()
	music.play()


func determine_platform() -> String:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Determining Platform...").info()
	for feature in platforms.keys():
		if OS.has_feature(feature):
			platform = feature
			Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Platform determined: ", platform).info()
			return platforms[feature]
	
	platform = ""
	return "" # Fallback if no platform matches


func _bark():
	bark.play()


func PlayClick(): #TODO: Refactor to be snake case
	click.play()

func wait(time: float):
	await get_tree().create_timer(time).timeout
