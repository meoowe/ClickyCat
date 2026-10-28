extends PanelContainer
class_name AchievementPlaque

@onready var icon_rect: TextureRect = $HBoxContainer/TextureRect
@onready var title_label: Label = $HBoxContainer/VBoxContainer/Title
@onready var description_label: Label = $HBoxContainer/VBoxContainer/Description
@onready var state: RichTextLabel = $HBoxContainer/VBoxContainer/State

func set_achievement(achievement: Achivement) -> void:
	title_label.text = str(achievement.name)
	description_label.text = achievement.description
	icon_rect.texture = achievement.icon
	icon_rect.modulate = Color.WHITE if achievement.unlocked else Color(0.35, 0.35, 0.35, 1.0)
	if not achievement.unlocked:
		state.text = "[i]Locked[/i]"
