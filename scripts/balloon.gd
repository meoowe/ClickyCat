extends TextureButton
var pos = null
 

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	control_show_hide_loop()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass

func control_show_hide_loop():
	while true:
		# 1. Randomize the position
		pos = Vector2(randi_range(220, 980),randi_range(18, 130))
		self.position = pos
		
		# 2. Make it appear
		self.visible = true
		print("show ballon!")
		
		# 3. Wait 3 seconds while it's on screen
		await get_tree().create_timer(1).timeout
		
		# 4. Hide the balloon
		self.visible = false
		
		# 5. Wait 1 second while it's hidden before restarting the loop
		await get_tree().create_timer(3).timeout

func _on_pressed() -> void:
	self.modulate = Color(1, 0.62, 0.02)
	await get_tree().create_timer(0.3).timeout
	self.modulate = Color.WHITE
	if !Global.balloonClicked and self.visible: 
		Global.scoreIncrement = Global.scoreIncrement * 2
		Global.balloonClicked = true 
		Global.PlayClick()
