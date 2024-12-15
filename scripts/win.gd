extends Control
@onready var published: Label = $published

@onready var label: Label = $Label
@onready var line_edit: LineEdit = $LineEdit
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
func _process(_delta: float) -> void:
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
	var success = await Leaderboards.post_guest_score(
		Global.LEADERBOARD_ID, Global.highScore, Global.playerName, metadata
	)
	if success:
		published.text = "Your score was published to the leaderboard!"
		Loggie.msg("[Win] Score was published sucessfully!").header().bold().color(Color.BLUE_VIOLET).info()
		published.show()
	else:
		published.text = "Sorry! There was a problem in posting your score."
		published.show()


func start_delay():
	await get_tree().create_timer(1.5).timeout
	finished = true
