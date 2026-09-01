extends PanelContainer


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass


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
