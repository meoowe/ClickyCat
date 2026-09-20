extends Node

var base_log: LoggieMsg = Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE)

@onready var music: AudioStreamPlayer = $music
@onready var title_music: AudioStreamPlayer = $title_music

@onready var click: AudioStreamPlayer = $click
@export var playerName: String = "Nickname"
@export var LEADERBOARD_ID: String = "clicky-cat-clickycat-njjy" #TODO: Refactor this to be lowercase
@export var VERSION: String = "READ-THE-NOTE-SILLY" # NOTE: Do NOT Edit this variabe. Edit through global.tscn instead.!
@export var save_file_path: String = "user://clickycat.tres"

@export_category("Game Settings")
@export var scoreIncrement: int = 40
@export var dog_speed: float = 70.0
@export var camera_scroll_speed = 100
@export var stamina_decrement: int = 25
@export var stamina_increment: float = 0.25

var score: int = 0
var highScore: int = 0
var balloonClicked: bool = false
var platform: String = ""
var new_high_score: bool = false
var game_has_started: bool = false
var cheats_used: bool = false

signal save_discarded

var platforms: Dictionary[Variant, Variant] = {
	"web": "web",
	"android": "android",
	"linux": "linux",
	"windows": "windows"
}
@export var debug: Dictionary[String, bool] = {
	"disableLoose": false,
	"disableWin": false,
	"hideBalloon": false,
	"disableStamina": true,
	"doSpriteRotation": true
}
func _ready() -> void:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Ready!").color(Color.CHARTREUSE).info()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Version: ", VERSION).info()
	determine_platform()
	title_music.play()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Load_from_save()").debug()
	load_from_save()
	print(OS.get_user_data_dir())
	print(ProjectSettings.get_setting("application/config/version"))
	ProjectSettings.set_setting("application/config/version", VERSION)


func determine_platform() -> String:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Determining Platform...").info()
	for feature in platforms.keys():
		if OS.has_feature(feature):
			platform = feature
			Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Platform determined: ", platform).info()
			return platforms[feature] 
	platform = ""
	return "" # Fallback if no platform matches

func PlayClick(): #TODO: Refactor to be snake case
	click.play()

func wait(time: float):
	await get_tree().create_timer(time).timeout
	
func save(config_version: String = VERSION, player_name: String = playerName, high_score: int = highScore, custom_cheats_used: bool = self.cheats_used):
	print("Save() was called!")
	var data = SaveData.new()
	data.config_version = config_version
	data.name = player_name
	data.high_score = high_score
	data.cheats_used = custom_cheats_used
	print("got to save call")
	var error = ResourceSaver.save(data, save_file_path)
	if error != OK:
		Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Failed  to save! ", error).error()
		return
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Saved game!").info()
	print("saved game")

func load_from_save():
	print("load_from_save() was called")
	if !ResourceLoader.exists(save_file_path):
		base_log.add(" No save file exists! Skipping load!").warn()
		return
	var data: SaveData = load(save_file_path)
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(
		" Found save file. Loading data:"
		).hseparator().add(
			"High Score: ", data.high_score
			).nl().add(
				"Name: ", data.name
				).nl().add(
					"Cheats:", data.cheats_used
				).box().info()
	if data.config_version != VERSION:
		base_log.add(" Tried to load diffrent config ver save! Aborting!").error()
		save_discarded.emit()
		return
	if data.cheats_used:
		base_log.add(" Previous log used cheats, tut tut tut").info()
	highScore = data.high_score
	playerName = data.name

func _notification(what: int) -> void:
	if what == NOTIFICATION_WM_CLOSE_REQUEST:
		Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add("🚪 Recieved WM Close Request, saving and gracefully quitting.").info()
		print("_notification: calling save!")
		get_viewport().set_input_as_handled()
		save()
		if OS.has_feature("web"):
			JavaScriptBridge.eval("window.close()")
		else:
			get_tree().quit()

func toggle_music():
	if music.playing:
		music.stop()
	else: music.play()

func start_title_music():
	music.stop()
	title_music.play()

func start_game_music():
	title_music.stop()
	music.play()
