extends Control

@onready var vrsn: Label = $vrsn


func _ready() -> void:
	vrsn.text = Global.VERSION


func _on_play_pressed() -> void:
	Global.PlayClick()
	Scenes.play()


func _on_credits_pressed() -> void:
	Global.PlayClick()
	Scenes.credits()


func _on_quit_pressed() -> void:
	Global.PlayClick()
	Scenes.quit()


func _on_texture_button_pressed() -> void:
	Global.PlayClick()
	Scenes.leaderboard()
