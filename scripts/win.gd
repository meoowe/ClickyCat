extends Control
@onready var published: Label = $CanvasLayer/published

@onready var label: Label = $CanvasLayer/Label
@onready var line_edit: LineEdit = $CanvasLayer/LineEdit
@export var camera: Camera2D
@export var scroll_speed = 100
var metadata = {
	"won?": true,
	"balloon used?": Global.balloonClicked,
	"version": Global.VERSION,
	"platform": Global.platform
}
var finished = false


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
	camera.position.x += scroll_speed * delta
	camera.position.y += scroll_speed * delta
	await start_delay()


func _on_title_pressed() -> void:
	if finished:
		Scenes.title()


func _on_line_edit_text_submitted(new_text: String) -> void:
	Loggie.msg("[Win]").bold().color(Color.BLUE_VIOLET).add(" 🥇 Trying to publish to leaderboard with following data:").header().box(8).nl().msg(metadata).info()
	Global.playerName = new_text
	Loggie.msg("[Win]").bold().color(Color.BLUE_VIOLET).add(" Player name:").msg(" ", Global.playerName).info()
	Loggie.msg("[Win]").bold().color(Color.BLUE_VIOLET).add(" Score:").msg(" ", Global.highScore).info()
	Loggie.msg("[Win]").bold().color(Color.BLUE_VIOLET).add(" Leaderboard ID:").msg(" ", Global.LEADERBOARD_ID).info()

func start_delay():
	await get_tree().create_timer(1.5).timeout
	finished = true
