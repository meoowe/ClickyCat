extends TextureButton
var pos

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	randPos()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass
func randPos():
	while true:
		await get_tree().create_timer(1).timeout
		pos= randi_range(165, 1200)
		self.position.x = pos


func _on_pressed() -> void:
	if !Global.balloonClicked:
		Global.scoreIncrement = Global.scoreIncrement * 2
		Global.balloonClicked = true
