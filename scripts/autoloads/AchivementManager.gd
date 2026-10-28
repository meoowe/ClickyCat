extends Node

signal achievement_unlocked(achievement: Achivement)

const CATALOG: Array[Achivement] = [
	preload("res://achivements/first_move.tres"),
	preload("res://achivements/win_1.tres"),
	preload("res://achivements/balloon_1.tres"),
	preload("res://achivements/score_1000.tres"),
	preload("res://achivements/lose_game.tres")
]

func _ready() -> void:
	Gamestate.score_changed.connect(_on_score_changed)

func get_all_achievements() -> Array[Achivement]:
	return CATALOG

func is_unlocked(achievement_id: String) -> bool:
	for achievement in Gamestate.achivements_unlocked:
		if achievement.id == achievement_id:
			return true
	return false

func unlock(achievement_id: String) -> bool:
	if is_unlocked(achievement_id):
		return false
	for definition in CATALOG:
		if definition.id != achievement_id:
			continue
		var unlocked_copy := definition.duplicate(true) as Achivement
		unlocked_copy.unlocked = true
		Gamestate.achivements_unlocked.append(unlocked_copy)
		achievement_unlocked.emit(unlocked_copy)
		SaveManager.save()
		return true
	push_warning("Unknown achievement id: %s" % achievement_id)
	return false

func _on_score_changed(total_score: int) -> void:
	if total_score > 0:
		unlock("first_move")
	if total_score >= 1000:
		unlock("score_1000")

func on_game_won() -> void:
	unlock("win_1")

func on_balloon_clicked() -> void:
	unlock("balloon_1")

func on_game_lost() -> void:
	unlock("lose_game")
