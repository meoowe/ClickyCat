extends PanelContainer
class_name SkinPlaque

@export var icon_rect: TextureRect
@export var title_label: Label
@export var description_label: Label
@export var state: RichTextLabel

func set_achievement(achievement: Achivement) -> void:
	title_label.text = str(achievement.name)
	description_label.text = achievement.description
	icon_rect.texture = achievement.icon
	icon_rect.modulate = Color.WHITE if achievement.unlocked else Color(0.35, 0.35, 0.35, 1.0)
	if not achievement.unlocked:
		state.text = "[i]Locked[/i]"
