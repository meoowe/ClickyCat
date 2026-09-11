extends PanelContainer


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	$VBoxContainer/HBoxContainer5/score_increment.value = Global.scoreIncrement
	$VBoxContainer/HBoxContainer6/dog_speed.value = Global.dog_speed
	$VBoxContainer/HBoxContainer7/cam_speed.value = Global.camera_scroll_speed	
	$VBoxContainer/HBoxContainer8/high_score.value = Global.highScore

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	if Input.is_action_just_pressed("debug"):
		self.visible = !visible

func _on_stamina_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableStamina = !Global.debug.disableStamina
	Global.cheats_used = true


func _on_balloon_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.hideBalloon = !Global.debug.hideBalloon
	Global.cheats_used = true


func _on_win_togle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableWin = !Global.debug.disableWin
	Global.cheats_used = true


func _on_loose_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableLoose = !Global.debug.disableLoose
	Global.cheats_used = true

func _on_score_increment_value_changed(value: float) -> void:
	Global.scoreIncrement = int(value)
	Global.cheats_used = true

func _on_dog_speed_value_changed(value: float) -> void:
	Global.dog_speed = value
	Global.cheats_used = true

func _on_cam_speed_value_changed(value: float) -> void:
	Global.camera_scroll_speed = value
	Global.cheats_used = true


func _on_high_score_value_changed(value: float) -> void:
	Global.highScore = int(value)
	Global.cheats_used = true


func _on_load_pressed() -> void:
	Global.load_from_save()


func _on_save_pressed() -> void:
	Global.save()
