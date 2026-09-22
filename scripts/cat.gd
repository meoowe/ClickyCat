extends CharacterBody2D

var paused: bool = false
var on_floor: bool = false
var first: bool = false
var moveAllowed: bool = true

@export var gravity: int = 2500
@export var staminaIncrement: float = 0.5
@export var staminaDecrement: int = 25
@export var staminaMin: int = 10
@export var belowMinStaminaPenalty: float = 1.5
@export var dogSpeed: int = 70
@export var catSpeed: int = 325
@export var scroll_speed: int = 100
@export var slope_rotation_speed: float = 10.0 # Speed of smooth sprite rotation
@export var high_stamina_speed_buff: float = 1.7
@export var high_stamina_threshold: int = 70
@export var stamina_warning: PanelContainer

# Reference to your specific AnimatedSprite2D or Sprite2D node
@export var sprite: AnimatedSprite2D

signal update_score

func _process(_delta: float) -> void:
	if Input.is_action_just_pressed("move"):
		trigger_move_action()

	if not Input.is_action_pressed("move"):
		%stamina.value += staminaIncrement

func trigger_move_action() -> void:
	if paused:
		return
	Global.game_has_started = true
	if (%stamina.value >= staminaMin and moveAllowed) or (Global.debug.disableStamina):
		update_score.emit()
		sprite.play_backwards()
		if %stamina.value >= high_stamina_threshold:
			velocity.x = catSpeed * high_stamina_speed_buff
		else: velocity.x = catSpeed
	elif !Global.debug.disableStamina:
		velocity.x = 0
		show_hide_stamina_warning()
		punish_spam()

func show_hide_stamina_warning() -> void:
	stamina_warning.show()
	await get_tree().create_timer(1).timeout
	stamina_warning.hide()

func handleCollision(collision: KinematicCollision2D) -> void:
	collision.get_collider()
	print(collision.get_collider())

func _physics_process(delta: float) -> void:
	velocity.y += gravity * delta
	floor_max_angle = deg_to_rad(65.0) # Lowered to 65 to prevent wall climbs treating as a floor
	floor_snap_length = 12.0
	floor_constant_speed = true
	move_and_slide()
	if is_on_floor() and Global.debug.doSpriteRotation:
		var floor_normal = get_floor_normal()
		# Calculate the target angle from the slope normal
		var target_angle = floor_normal.angle() + PI / 2.0
		# Smoothly rotate the sprite over time
		sprite.rotation = lerp_angle(sprite.rotation, target_angle, slope_rotation_speed * delta)
	elif Global.debug.doSpriteRotation:
		# Reset to zero rotation smoothly if in the air
		sprite.rotation = lerp_angle(sprite.rotation, 0.0, slope_rotation_speed * delta)
	for i in get_slide_collision_count():
		var collision := get_slide_collision(i)
		var collider := collision.get_collider()
		#print("COLLISION: ", collider)
		#print("TYPE: ", collider.get_class())
		if collider.get_class() == "RigidBody2D":
			print("HIT RIGIDBODY")
	velocity.x = move_toward(velocity.x, 0, 800 * delta)

func punish_spam():
	moveAllowed = false
	await get_tree().create_timer(1).timeout
	moveAllowed = true
