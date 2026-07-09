extends CharacterBody2D

var paused: bool = false
var on_floor: bool = false
var first: bool = false
var cat_first: bool = true
var moveAllowed: bool = true

@export var gravity: int = 2500
@export var staminaIncrement: float = 0.2
@export var staminaDecrement: int = 30
@export var staminaMin: int = 3
@export var belowMinStaminaPenalty: float = 1.5
@export var dogSpeed: int = 78
@export var catSpeed: int =  325 # Added to give proper movement speed
@export var scroll_speed: int = 100

@onready var ground: StaticBody2D = $"../ground"
@onready var dog: RigidBody2D = $"../path/follower/dog"
@onready var dog_shapew: CollisionShape2D = $"../path/follower/dog/dogShapew"
@onready var dog_2: AnimatedSprite2D = $"../path/follower/dog/dog2"
@onready var follower: PathFollow2D = $"../path/follower"
@onready var options: Control = $"../../options"
@onready var camera: Camera2D = $"../../Camera2D"
@onready var stamina_warning: PanelContainer = $"../../UI/PanelContainer"


func _ready() -> void:
	Global.score = 0  # Reset Score so that the player can't 'cheat'.
	dog_2.play()  # Start bark animation.
	%stamina.value = 100.0

func _process(delta: float) -> void:
	if first:  # Only execute if user has pressed or clicked to move.
		follower.progress += dogSpeed * delta

	# Handle input actions for keyboard/controller players
	if Input.is_action_just_pressed("move"):
		trigger_move_action()
	
	# Regenerate stamina if move is not actively being pressed
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

# Consolidated movement logic so UI button and Keyboard do the exact same thing
func trigger_move_action() -> void:
	if paused:
		return
		
	first = true
	if %stamina.value >= staminaMin:
		Global.score += Global.scoreIncrement
		%stamina.value -= staminaDecrement
		velocity.x = catSpeed # Apply meaningful movement speed to the left
	else:
		velocity.x = 0
		show_hide_stamina_warning()
		await get_tree().create_timer(belowMinStaminaPenalty).timeout
		
func show_hide_stamina_warning() -> void:
	stamina_warning.show()
	await get_tree().create_timer(1).timeout
	stamina_warning.hide()

func _on_move_pressed() -> void:
	Global.PlayClick()
	trigger_move_action()

func handleCollision(collision: KinematicCollision2D) -> void:
	if collision.get_collider() == dog:
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
	# 1. Apply gravity smoothly
	velocity.y += gravity * delta
	
	# FIX FOR JAGGED EDGES:
	# Increase the maximum angle the cat can climb (e.g., up to 65 degrees)
	floor_max_angle = deg_to_rad(100.0) 
	
	# Allow the cat to snap to the jagged floor instead of bouncing or floating off edges
	floor_snap_length = 12.0
	floor_constant_speed = true
	
	# 2. Move using the internal velocity
	move_and_slide()
	
	# 3. Check for collisions (like the dog)
	for i in get_slide_collision_count():
		var collision = get_slide_collision(i)
		handleCollision(collision)
		
	# 4. Friction/Damping: Slow down horizontal movement over time
	velocity.x = move_toward(velocity.x, 0, 800 * delta)
