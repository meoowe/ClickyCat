extends ParallaxBackground

@export var scroll_speed: Vector2 = Vector2(50, 20)  # Adjust scrolling speed


func _process(delta: float) -> void:
	# Scroll the background continuously
	scroll_offset.x += scroll_speed.x * delta
	scroll_offset.y += scroll_speed.y * delta
