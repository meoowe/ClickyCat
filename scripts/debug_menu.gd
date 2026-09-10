extends PanelContainer


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	$VBoxContainer/HBoxContainer5/score_increment.value = Global.scoreIncrement
	$VBoxContainer/HBoxContainer6/dog_speed.value = Global.dog_speed
	$VBoxContainer/HBoxContainer7/cam_speed.value = Global.camera_scroll_speed


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	if Input.is_action_just_pressed("debug"):
		self.visible = !visible

func _on_stamina_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableStamina = !Global.debug.disableStamina


func _on_balloon_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.hideBalloon = !Global.debug.hideBalloon


func _on_win_togle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableWin = !Global.debug.disableWin


func _on_loose_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.disableLoose = !Global.debug.disableLoose



func _on_score_increment_value_changed(value: float) -> void:
	Global.scoreIncrement = int(value)



func _on_dog_speed_value_changed(value: float) -> void:
	Global.dog_speed = value
	

func _on_cam_speed_value_changed(value: float) -> void:
	Global.camera_scroll_speed = value
