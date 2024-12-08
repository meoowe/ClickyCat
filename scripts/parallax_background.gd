extends ParallaxBackground
@onready var animated_sprite_2d: AnimatedSprite2D = $Dog/AnimatedSprite2D

@export var scroll_speed: Vector2 = Vector2(50, 0)  # Adjust scrolling speed


func _ready() -> void:
	animated_sprite_2d.play()


func _process(delta: float) -> void:
	# Scroll the background continuously
	scroll_offset.x += scroll_speed.x * delta
