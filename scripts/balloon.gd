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
		fade_out_and_hide(false,0.5)
		print("show ballon!")
		
		# 3. Wait 3 seconds while it's on screen
		await get_tree().create_timer(3).timeout
		
		# 4. Hide the balloon
		if Global.debug.hideBalloon: pass
		fade_out_and_hide(true, 0.5)
		# 5. Wait 1 second while it's hidden before restarting the loop
		await get_tree().create_timer(2).timeout

func _on_pressed() -> void:
	self.modulate = Color(1, 0.62, 0.02)
	await get_tree().create_timer(0.3).timeout
	self.modulate = Color.WHITE
	if self.visible and Global.game_has_started: 
		Global.scoreIncrement = 20
		Global.balloonClicked = true 
		Global.PlayClick()

func fade_out_and_hide(end_show: bool,duration: float = 0.5):
	# Ensure the node is visible and ready to fade
	if end_show == true:
		visible = true
		var tween = create_tween()
		# Animate modulate alpha property to 0 over the duration
		tween.tween_property(self, "modulate:a", 0.0, duration)
		
		# Hide the node completely after the fade ends to save resources
		tween.tween_callback(self.hide)

	if end_show == false:
		var tween = create_tween()
		tween.tween_property(self,"modulate:a", 1, duration)
