extends TextureButton
var pos = null


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	randPos()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass


func randPos():
	while true:
		await get_tree().create_timer(1).timeout
		pos = randi_range(530, 1200)
		self.position.x = pos


func _on_pressed() -> void:
	self.modulate = Color(1, 0.62, 0.02)
	await get_tree().create_timer(0.3).timeout
	self.modulate = Color.WHITE
	if !Global.balloonClicked:
		Global.scoreIncrement = Global.scoreIncrement * 2
		Global.balloonClicked = true
		Global.PlayClick()
