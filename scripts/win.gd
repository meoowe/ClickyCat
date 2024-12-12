extends Control
@onready var published: Label = $published

@onready var label: Label = $Label
@onready var line_edit: LineEdit = $LineEdit
var metadata = {"won?": true, "balloon used?": Global.balloonClicked, "version": Global.VERSION, "platform": Global.platform}

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	line_edit.text = Global.playerName
	label.text = (
		"You won with a score of "
		+ str(Global.score)
		+ "\nYour High Score is "
		+ str(Global.highScore)
	)


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass


func _on_title_pressed() -> void:
	get_tree().change_scene_to_file("res://title.tscn")


func _on_line_edit_text_submitted(new_text: String) -> void:
	Global.playerName = new_text
	print(Global.playerName)
	await Leaderboards.post_guest_score(
		Global.LEADERBOARD_ID, 
		Global.highScore,
		Global.playerName, 
		metadata
	)
	published.show()
	
