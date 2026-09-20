extends CanvasLayer


var active_tween: Tween
var current_display_value: int = 0

func _on_game_score_changed(new_score: int) -> void:
	if active_tween:
		active_tween.kill()
		
	# 2. Create a new tween
	active_tween = create_tween()
	
	# 3. Configure the tween to call our update method over a duration (e.g., 1.0 second)
	active_tween.tween_method(
		_update_label_text,      # The function to call every frame
		current_display_value,   # Starting value
		new_score,        # Target value received from the signal
		0.2                     # Duration in seconds
	)
	
func _update_label_text(value: int) -> void:
	current_display_value = value
	%scoire.text = "Score: " + str(value)  # Converts the interpolated integer into text
