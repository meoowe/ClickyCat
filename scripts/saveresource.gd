@tool
extends Resource
class_name SaveData

@export var config_version: int
@export var name: String = "Nickname"
@export var high_score: int
@export var cheats_used: bool
@export var games_won: int = 0
@export var unlocked_skins: Array[CatSkin] = []
@export var unlocked_achievements: Array[Achivement] = []
