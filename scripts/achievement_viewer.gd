extends Control

const PLAQUE_SCENE: PackedScene = preload("res://scenes/achivement_plaque.tscn")

@onready var grid: GridContainer = %GridContainer

func _ready() -> void:
	for child in grid.get_children():
		child.queue_free()
	for definition in AchivementManager.get_all_achievements():
		var plaque := PLAQUE_SCENE.instantiate() as AchievementPlaque
		grid.add_child(plaque)
		plaque.set_achievement(_get_player_version(definition))
	$Back.pressed.connect(_on_back_pressed)

func _get_player_version(definition: Achivement) -> Achivement:
	for unlocked in Gamestate.achivements_unlocked:
		if unlocked.id == definition.id:
			return unlocked
	return definition

func _on_back_pressed() -> void:
	Scenes.title()
