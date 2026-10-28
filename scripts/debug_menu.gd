extends PanelContainer
@export var option_button: OptionButton
@export var accept_dialog: AcceptDialog
@export var confirm_dialog: ConfirmationDialog
@onready var nameMap = {
	"looseToggle": Global.debug.disableLoose,
	"winToggle": Global.debug.disableWin,
	"balloonToggle": Global.debug.hideBalloon,
	"staminaToggle": Global.debug.disableStamina,
	"rotateToggle": Global.debug.doSpriteRotation,
	"score_increment": Global.scoreIncrement,
	"dog_speed": Global.dog_speed,
	"cam_speed": Global.camera_scroll_speed,
	"high_score": Gamestate.highScore
}
var flag_inf_proc = false
# alled when the node enters the scene tree for the first time.
func _ready() -> void:
	var all_descendants = find_children("*", "", true)
	for node in all_descendants:
		if !(node.name in nameMap): continue
		if node is CheckButton:
			node.button_pressed = nameMap[node.name]
		if node is SpinBox:
			node.value = nameMap[node.name]
	confirm_dialog.connect("confirmed", _on_confirmation_dialog_confirmed)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	if Input.is_action_just_pressed("debug"):
		self.visible = !visible
	if flag_inf_proc:
		while true:
			print("Oops infinite loop in _process")

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
	Gamestate.highScore = int(value)
	Global.cheats_used = true


func _on_load_pressed() -> void:
	SaveManager.load_from_save()

func _on_save_pressed() -> void:
	SaveManager.save()


func _on_clear_save_pressed() -> void:
	confirm_dialog.show()


func _on_confirmation_dialog_confirmed() -> void:
	Gamestate.playerName = "Nickname"
	Gamestate.highScore = 0
	Gamestate.cheats_used = false
	Gamestate.games_won = 0
	Gamestate.unlocked_skins.clear()
	Gamestate.achivements_unlocked.clear()
	SaveManager.save()
	accept_dialog.show()
	
func _on_rotate_toggle_toggled(_toggled_on: bool) -> void:
	Global.debug.doSpriteRotation = !Global.debug.doSpriteRotation


func _on_crash_pressed() -> void:
	match option_button.selected:
		0: # Free root node
			get_tree().root.queue_free()
		1: 
			flag_inf_proc = true
		2:
			while true:
				add_child(Node.new())
		3:
			OS.kill(OS.get_process_id())
		4: 
			var dummy = Node.new()
			add_child(dummy)
			dummy.free()
			dummy.set_meta("crash", true) 
