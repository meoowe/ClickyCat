extends Node
@export var save_file_path: String = "user://clickycat.tres"
@export var configversion: int = 1
signal save_discarded

func save() -> Error:
	var data = SaveData.new()
	data.config_version = configversion
	data.name = Gamestate.playerName
	data.high_score = Gamestate.highScore
	data.cheats_used = Gamestate.cheats_used
	data.games_won = Gamestate.games_won
	data.unlocked_skins = Gamestate.unlocked_skins.duplicate()
	data.unlocked_achievements = Gamestate.achivements_unlocked.duplicate(true)
	var error = ResourceSaver.save(data, save_file_path)
	if error != OK:
		Loggie.msg("[SaveManager]").bold().color(Color.CORNFLOWER_BLUE).add(" Failed  to save! ", error).error()
		return error
	Loggie.msg("[SaveManager]").bold().color(Color.CORNFLOWER_BLUE).add(" Saved game!").info()
	return OK

func load_from_save() -> bool:
	if not ResourceLoader.exists(save_file_path):
		Loggie.msg("[SaveManager]").bold().color(Color.CORNFLOWER_BLUE).add(" No save file exists! Skipping load!").warn()
		return false
	var resource := ResourceLoader.load(save_file_path)
	if not resource is SaveData:
		push_error("Save file is invalid or has an unsupported resource type.")
		save_discarded.emit()
		return false
	var data := resource as SaveData
	if data.config_version != configversion:
		Loggie.msg("[SaveManager]").bold().color(Color.CORNFLOWER_BLUE).add(" Save version is unsupported; leaving it untouched.").error()
		save_discarded.emit()
		return false
	Loggie.msg("[SaveManager]").bold().color(Color.CORNFLOWER_BLUE).add(
		" Found save file. Loading data:"
		).hseparator().add(
			"High Score: ", data.high_score
			).nl().add(
				"Name: ", data.name
				).nl().add(
					"Cheats:", data.cheats_used
				).box().info()
	Gamestate.highScore = maxi(data.high_score, 0)
	Gamestate.playerName = data.name.strip_edges() if not data.name.strip_edges().is_empty() else "Nickname"
	Gamestate.cheats_used = data.cheats_used
	Gamestate.games_won = maxi(data.games_won, 0)
	Gamestate.unlocked_skins = data.unlocked_skins.duplicate()
	Gamestate.achivements_unlocked = data.unlocked_achievements.duplicate(true)
	return true
