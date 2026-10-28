extends Node
# Todo: finish porting musicmanager
var base_log: LoggieMsg = Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE)

@export var LEADERBOARD_ID: String = "clicky-cat-clickycat-njjy" #TODO: Refactor this to be lowercase
@export var VERSION: String = "DO NOT TOUCH" # NOTE: Do NOT Edit this variabe. Edit through project settings!

@export_category("Game Settings")
@export var scoreIncrement: int = 10
@export var dog_speed: float = 70.0
@export var camera_scroll_speed = 100
@export var stamina_decrement: int = 25
@export var stamina_increment: float = 0.25
@export var bounce_timer: float = 2
var platform: String = ""

var platforms: Dictionary[String, String] = {
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
var unlocked_skins: Array[CatSkin]:
	get: return Gamestate.unlocked_skins
	set(value): Gamestate.unlocked_skins = value

# Compatibility properties for existing gameplay scripts. Gamestate is the
# single owner of session and player progress; Global retains settings/audio.
var score: int:
	get: return Gamestate.score
	set(value): Gamestate.score = value
var highScore: int:
	get: return Gamestate.highScore
	set(value): Gamestate.highScore = value
var balloonClicked: bool:
	get: return Gamestate.balloonClicked
	set(value): Gamestate.balloonClicked = value
var game_has_started: bool:
	get: return Gamestate.game_has_started
	set(value): Gamestate.game_has_started = value
var cheats_used: bool:
	get: return Gamestate.cheats_used
	set(value): Gamestate.cheats_used = value
var games_won: int:
	get: return Gamestate.games_won
	set(value): Gamestate.games_won = value
var playerName: String:
	get: return Gamestate.playerName
	set(value): Gamestate.playerName = value
var achivements_unlocked: Array[Achivement]:
	get: return Gamestate.achivements_unlocked
	set(value): Gamestate.achivements_unlocked = value

func _ready() -> void:
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Ready!").color(Color.CHARTREUSE).info()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Version: ", VERSION).info()
	determine_platform()
	Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add(" Load_from_save()").debug()
	SaveManager.load_from_save()
	print(OS.get_user_data_dir())
	VERSION = ProjectSettings.get_setting("application/config/version")


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
	pass

func wait(time: float):
	await get_tree().create_timer(time).timeout

func _notification(what: int) -> void:
	if what == NOTIFICATION_WM_CLOSE_REQUEST:
		Loggie.msg("[Global]").bold().color(Color.CORNFLOWER_BLUE).add("🚪 Recieved WM Close Request, saving and gracefully quitting.").info()
		print("_notification: calling save!")
		get_viewport().set_input_as_handled()
		SaveManager.save()
		if OS.has_feature("web"):
			JavaScriptBridge.eval("window.close()")
		else:
			get_tree().quit()
