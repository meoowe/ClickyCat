extends Node
# Runtime player/session state. Keep persistent fields here so save/load has one owner.
var score: int = 0
var highScore: int = 0
var balloonClicked: bool = false
var new_high_score: bool = false
var game_has_started: bool = false
var cheats_used: bool = false
var games_won: int = 0
var playerName: String = "Nickname"
var unlocked_skins: Array[CatSkin] = []
var achivements_unlocked: Array[Achivement] = []

signal score_changed(new_score: int)

func add_score():
	score += Global.scoreIncrement
	if score > highScore:
		highScore = score
	score_changed.emit(score)
