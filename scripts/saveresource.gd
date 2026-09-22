@tool
extends Resource
class_name SaveData

@export var config_version: String
@export var name: String
@export var high_score: int
@export var cheats_used: bool
@export var games_won: bool
@export var unlocked_skins: Array[CatSkin]
