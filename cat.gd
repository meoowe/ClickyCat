extends Area2D
@onready var follower: PathFollow2D = $"../path/follower"

const SPEED = 300.0
const JUMP_VELOCITY = -200.0
var floor = false
@onready var mountain: StaticBody2D = $"../mountain"
@onready var ground: StaticBody2D = $"../ground"
@onready var dog: StaticBody2D = $"../path/follower/dog"
@onready var scoire: Label = $"../scoire"
@onready var dog_shapew: CollisionShape2D = $"../path/follower/dog/dogShapew"

@onready var dog_2: AnimatedSprite2D = $"../path/follower/dog/dog2"

signal started
var first = false
var cat_first = true
func _ready() -> void:
	Global.score = 0 # Reset Score so that the player can't 'cheat'.
	_gravity()
	dog_2.play() # Start bark animation.
func _process(delta: float) -> void:
	if first == true: # Only execute if user has pressed or clicked to move.
		follower.progress += 78 * delta
	if Input.is_action_just_pressed("move"):
		Global.score += 10 # Each press is worth 10.
		first = true # 
		position.x += 12
		position.y -= 20
	if follower.position.x >= 1200:
		get_tree().change_scene_to_file("res://win.tscn")
	scoire.text = "Score: " + str(Global.score)
	if Global.score > Global.highScore:
		Global.highScore = Global.score
	if self.position.x > dog.position.x:
		cat_first = true
	else: 
		cat_first = false


func _on_body_entered(body: Node2D) -> void:
	if body != dog:
		print("true")
		floor = true
	elif body == dog or body == dog_2 or body == dog_shapew:
		get_tree().change_scene_to_file("res://lost.tscn")
func _gravity():
	while true:
		await get_tree().create_timer(0.1).timeout
		if floor == true:
			print("floor!")
		elif position.y < 0:
			self.position.y += 10
			print(position)


func _on_body_exited(body: Node2D) -> void:
	print("exit")
	floor = false


func _on_move_pressed() -> void:
	Global.score += 10
	first = true
	position.x += 12
	position.y -= 10
