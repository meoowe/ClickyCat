
#NOT USED
# TODO: Delete
extends Node2D
@onready var animated_sprite_2d: AnimatedSprite2D = $Dog/AnimatedSprite2D
@export var scroll_speed: int = 100


func _ready() -> void:
	animated_sprite_2d.play()


func _process(delta: float) -> void:
	# Scroll the background continuously
	pass
