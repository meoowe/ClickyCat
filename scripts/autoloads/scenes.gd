
extends Node
signal game_scene_start
signal title_screen_switch
var base_log = Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE)
# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	base_log.add(" Ready!").color(Color.CHARTREUSE).info()
	title_screen_switch.connect(MusicManager.start_title_music)
	game_scene_start.connect(MusicManager.start_game_music)


func title():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("📻 Changing scene to Title.").info()
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/title.tscn")
	title_screen_switch.emit()
	ScreenFader.fade_clear()

func won():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🏆 Game Won! Changing to Win.").info()
	if !Global.debug.disableWin:
		await ScreenFader.fade_dark()
		get_tree().call_deferred("change_scene_to_file", "res://scenes/win.tscn")
		ScreenFader.fade_clear()
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("Debug option disallows win scene").info()
func lost():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("☹️ Game Lost! Changing to Lost.").info()
	AchivementManager.on_game_lost()
	if Global.debug.disableLoose:
		Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("Debug option disallows lose scene").info()
		return
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/lost.tscn")
	ScreenFader.fade_clear()
func quit():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🎮 Game Quit! Goodbye :)").info()
	get_tree().root.propagate_notification(NOTIFICATION_WM_CLOSE_REQUEST)

func leaderboard():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🥇 Changing scene to Leaderboard").info()
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/leaderboard.tscn")
	ScreenFader.fade_clear()

func achievements():
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/achivememt_viewer.tscn")
	ScreenFader.fade_clear()

func credits():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("📜 Changing scene to Credits").info()
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/credits.tscn")
	ScreenFader.fade_clear()
func play():
	Loggie.msg("[Scenes]").bold().color(Color.CADET_BLUE).add("🕹️ Starting Game scene!").info()
	await ScreenFader.fade_dark()
	get_tree().call_deferred("change_scene_to_file", "res://scenes/game.tscn")
	ScreenFader.fade_clear()
	game_scene_start.emit()
