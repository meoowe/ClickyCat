extends CharacterBody2D

var paused: bool = false
var on_floor: bool = false
var first: bool = false
var cat_first: bool = true
var moveAllowed: bool = true

@export var gravity: int = 2500
@export var staminaIncrement: float = 0.25
@export var staminaDecrement: int = 25
@export var staminaMin: int = 3
@export var belowMinStaminaPenalty: float = 1.5
@export var dogSpeed: int = 70
@export var catSpeed: int = 325
@export var scroll_speed: int = 100
@export var slope_rotation_speed: float = 5.0 # Speed of smooth sprite rotation

@onready var ground: StaticBody2D = $"../ground"
@onready var dog: RigidBody2D = $"../path/follower/dog"
@onready var dog_shapew: CollisionShape2D = $"../path/follower/dog/dogShapew"
@onready var dog_2: AnimatedSprite2D = $"../path/follower/dog/dog2"
@onready var follower: PathFollow2D = $"../path/follower"
@onready var options: Control = $"../../options"
@onready var camera: Camera2D = $"../../Camera2D"
@onready var stamina_warning: PanelContainer = $"../../UI/PanelContainer"

# Reference to your specific AnimatedSprite2D or Sprite2D node
@onready var sprite: Sprite2D = $Sprite2D # Update this path if necessary

func _ready() -> void:
	Global.score = 0
	dog_2.play()
	%stamina.value = 100.0

func _process(delta: float) -> void:
	if first:
		follower.progress += dogSpeed * delta

	if Input.is_action_just_pressed("move"):
		trigger_move_action()

	if not Input.is_action_pressed("move"):
		%stamina.value += staminaIncrement

	%scoire.text = "Score: " + str(Global.score)

	if Global.score > Global.highScore:
		Global.highScore = Global.score

	if self.position.x > dog.position.x:
		cat_first = true
	else:
		cat_first = false

	if Input.is_action_just_pressed("pause"):
		pause()

	camera.position.x += scroll_speed * delta

func trigger_move_action() -> void:
	if paused:
		return

	first = true
	if %stamina.value >= staminaMin and moveAllowed:
		Global.score += Global.scoreIncrement
		%stamina.value -= staminaDecrement
		velocity.x = catSpeed
	elif !Global.debug.disableStamina:
		velocity.x = 0
		show_hide_stamina_warning()
		punish_spam()

func show_hide_stamina_warning() -> void:
	stamina_warning.show()
	await get_tree().create_timer(1).timeout
	stamina_warning.hide()

func _on_move_pressed() -> void:
	Global.PlayClick()
	trigger_move_action()

func handleCollision(collision: KinematicCollision2D) -> void:
	if collision.get_collider() == dog and !Global.debug.disableLoose:
		await get_tree().create_timer(0.5).timeout
		Scenes.lost()

func pause() -> void:
	if paused:
		options.hide()
		Engine.time_scale = 1
	else:
		options.show()
		Engine.time_scale = 0
	paused = !paused

func _physics_process(delta: float) -> void:
	velocity.y += gravity * delta

	floor_max_angle = deg_to_rad(65.0) # Lowered to 65 to prevent wall climbs treating as a floor
	floor_snap_length = 12.0
	floor_constant_speed = true

	move_and_slide()

	# Sprite Rotation Logic for Slopes
	if is_on_floor():
		var floor_normal = get_floor_normal()
		# Calculate the target angle from the slope normal
		var target_angle = floor_normal.angle() + PI / 2.0
		# Smoothly rotate the sprite over time
		sprite.rotation = lerp_angle(sprite.rotation, target_angle, slope_rotation_speed * delta)
	else:
		# Reset to zero rotation smoothly if in the air
		sprite.rotation = lerp_angle(sprite.rotation, 0.0, slope_rotation_speed * delta)

	for i in get_slide_collision_count():
		var collision = get_slide_collision(i)
		handleCollision(collision)

	velocity.x = move_toward(velocity.x, 0, 800 * delta)

func punish_spam():
	moveAllowed = false
	await get_tree().create_timer(1).timeout
	moveAllowed = true
