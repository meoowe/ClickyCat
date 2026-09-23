extends CharacterBody2D

@export var navigation_agent: NavigationAgent2D
@export var cat: CharacterBody2D
@export var sprite: AnimatedSprite2D
@export var game: Node2D

var direction 
func _ready():
	await get_tree().physics_frame
	navigation_agent.target_position = cat.global_position
	navigation_agent.target_desired_distance = 0.5
	print(navigation_agent.path_desired_distance)

func _on_game_paused(paused: bool) -> void:
	if paused:
		$dog2.stop()
		$barkNoise.stop()
		print(paused)
	$dog2.play()
	$barkNoise.play()

func _process(_delta: float) -> void:
	$dog2.rotation = 0
	rotation = 0

func _physics_process(delta):
	var next_position := navigation_agent.get_next_path_position()
	direction = global_position.direction_to(next_position)
	velocity.x = direction.x * 200.0
	if not is_on_floor():
		velocity.y += 2500.0 * delta
	else:
		# Navigation controls vertical movement while following the slope
		velocity.y = direction.y * 200.0
	floor_max_angle = deg_to_rad(65.0) # Lowered to 65 to prevent wall climbs treating as a floor
	floor_snap_length = 12.0
	floor_constant_speed = true
	if Global.game_has_started:
		move_and_slide()
	if is_on_floor() and Global.debug.doSpriteRotation:
		var floor_normal = get_floor_normal()
		# Calculate the target angle from the slope normal
		var target_angle = floor_normal.angle() + PI / 2.0
		# Smoothly rotate the sprite over time
		sprite.rotation = lerp_angle(sprite.rotation, target_angle, 10 * delta)
	else:
		# Reset to zero rotation smoothly if in the air
		sprite.rotation = lerp_angle(sprite.rotation, 0.0, 10 * delta)
		


func _on_timer_timeout() -> void:
	navigation_agent.target_position = cat.global_position
