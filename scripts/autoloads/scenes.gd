extends Node

signal game_scene_start
signal title_screen_switch
var base_log = Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE)
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	base_log.add(" Ready!").color(Color.CHARTREUSE).info()
	title_screen_switch.connect(Global.start_title_music)
	game_scene_start.connect(Global.start_game_music)


func title():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("📻 Changing scene to Title.").info()
	get_tree().change_scene_to_file("res://scenes/title.tscn")
	title_screen_switch.emit()

func won():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🏆 Game Won! Changing to Win.").info()
	get_tree().change_scene_to_file("res://scenes/win.tscn")
func lost():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("☹️ Game Lost! Changing to Lost.").info()
	get_tree().change_scene_to_file("res://scenes/lost.tscn")
func quit():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🎮 Game Quit! Goodbye :)").info()
	get_tree().root.propagate_notification(NOTIFICATION_WM_CLOSE_REQUEST)

func leaderboard():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🥇 Changing scene to Leaderboard").info()
	get_tree().change_scene_to_file("res://scenes/leaderboard.tscn")
func credits():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("📜 Changing scene to Credits").info()
	get_tree().change_scene_to_file("res://scenes/credits.tscn")
func play():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🕹️ Starting Game scene!").info()
	get_tree().change_scene_to_file("res://scenes/game.tscn")
	game_scene_start.emit()
