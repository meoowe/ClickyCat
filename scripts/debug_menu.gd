extends PanelContainer

@onready var nameMap = {
	"looseToggle": Global.debug.disableLoose,
	"winToggle": Global.debug.disableWin,
	"balloonToggle": Global.debug.hideBalloon,
	"staminaToggle": Global.debug.disableStamina,
	"rotateToggle": Global.debug.doSpriteRotation,
	"score_increment": Global.scoreIncrement,
	"dog_speed": Global.dog_speed,
	"cam_speed": Global.camera_scroll_speed,
	"high_score": Global.highScore
}
# alled when the node enters the scene tree for the first time.
func _ready() -> void:
	var all_descendants = find_children("*", "", true)
	for node in all_descendants:
		if !(node.name in nameMap): continue
		if node is CheckButton:
			node.button_pressed = nameMap[node.name]
		if node is SpinBox:
			node.value = nameMap[node.name]

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

pass # Replace with function body.
func _on_save_pressed() -> void:
	Global.save()


func _on_clear_save_pressed() -> void:
	$"../ConfirmationDialog".show()


func _on_confirmation_dialog_confirmed() -> void:
	Global.save(Global.VERSION, "", 0, false)
	$"../../AcceptDialog".show()
	


func _on_rotate_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.doSpriteRotation = !Global.debug.doSpriteRotation
