extends TileMapLayer

@export var speed := 50.0
@export var direction := Vector2.LEFT
@export var texture_width := 512.0 # The pixel width of your entire drawn map

func _process(delta: float) -> void:
	# Move the layer
	position += direction * speed * delta
	
	# Smoothly loop the position using floating point modulo
	position.x = fposmod(position.x, texture_width) - texture_width
