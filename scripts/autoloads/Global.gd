extends Node

var base_log: LoggieMsg = Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE)

@onready var music: AudioStreamPlayer = $music
@onready var click: AudioStreamPlayer = $click

@export var scoreIncrement: int = 10
@export var playerName: String = "Nickname"
@export var LEADERBOARD_ID: String = "clicky-cat-clickycat-njjy" #TODO: Refactor this to be lowercase
@export var VERSION: String = "2.3.0-11.07.26-DEV"
@export var save_file_path: String = ""
var score: int = 0
var highScore: int = 0
var balloonClicked: bool = false
var platform: String = ""

var platforms: Dictionary[Variant, Variant] = {
	"web": "web",
	"android": "android",
	"linux": "linux",
	"windows": "windows"
}
var debug: Dictionary[String, bool] = {
	"disableLoose": false,
	"disableWin": false,
	"hideBalloon": false,
	"disableStamina": false
}
func _ready() -> void:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Ready!").color(Color.CHARTREUSE).info()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Version: ", VERSION).info()
	determine_platform()
	music.play()
	load_from_save()


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
	pass


func PlayClick(): #TODO: Refactor to be snake case
	click.play()

func wait(time: float):
	await get_tree().create_timer(time).timeout
	
func save():
	var data = SaveData.new()
	data.config_version = VERSION
	data.name = playerName
	data.high_score = highScore
	
	var error = ResourceSaver.save(data, save_file_path)
	if error:
		base_log.add(" Failed  to save! ", error).error()

func load_from_save():
	if !ResourceLoader.exists(save_file_path):
		base_log.add(" No save file exists! Skipping load!").warn()
		return
	var data: SaveData = load(save_file_path)
	if data.config_version != VERSION:
		base_log.add(" Tried to load diffrent config ver save! Aborting!").error()
	data.high_score = highScore
	data.name = playerName

func _notification(what: int) -> void:
	if what == NOTIFICATION_WM_CLOSE_REQUEST:
		Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add("🚪 Recieved WM Close Request, saving and gracefully quitting.").info()
		save()
		get_viewport().set_input_as_handled()
		if OS.has_feature("web"):
			JavaScriptBridge.eval("window.close()")
		else:
			get_tree().quit()
