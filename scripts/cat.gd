extends Area2D

var paused = false
var on_floor = false
var first = false
var cat_first = true

@onready var ground: StaticBody2D = $"../ground"
@onready var dog: StaticBody2D = $"../path/follower/dog"
@onready var scoire: Label = $"../scoire"
@onready var dog_shapew: CollisionShape2D = $"../path/follower/dog/dogShapew"
@onready var dog_2: AnimatedSprite2D = $"../path/follower/dog/dog2"
@onready var follower: PathFollow2D = $"../path/follower"
@onready var options: Control = $"../options"


# HACK: DO NOT remove comments, they break the gravity for some reason
func _ready() -> void:
	Global.score = 0  # Reset Score so that the player can't 'cheat'.
	_gravity()
	dog_2.play()  # Start bark animation.


func _process(delta: float) -> void:
	if first == true:  # Only execute if user has pressed or clicked to move.
		follower.progress += 78 * delta
	if !paused:
		if Input.is_action_just_pressed("move"):
			Global.score += Global.scoreIncrement
			first = true
			position.x += 12
			position.y -= 20
		if follower.position.x >= 1200:
			Scenes.won()
	scoire.text = "Score: " + str(Global.score)
	if Global.score > Global.highScore:
		Global.highScore = Global.score
	if self.position.x > dog.position.x:
		cat_first = true
	else:
		cat_first = false
	if Input.is_action_just_pressed("pause"):
		pause()


func _on_body_entered(body: Node2D) -> void:
	if body != dog:
		on_floor = true
	elif body == dog or body == dog_2 or body == dog_shapew:
		Scenes.lost()


func _gravity():
	while true:
		await get_tree().create_timer(0.1).timeout
		if on_floor == true:
			print("on_floor!")
		elif position.y < 0:
			self.position.y += 10


func _on_body_exited(body: Node2D) -> void:
	if body != dog:
		on_floor = false
	elif body == dog or body == dog_2 or body == dog_shapew:
		Scenes.lost()


func _on_move_pressed() -> void:
	Global.PlayClick()
	if !paused:
		Global.score += Global.scoreIncrement
		first = true
		position.x += 12
		position.y -= 10


func pause():
	if paused:
		options.hide()
		Engine.time_scale = 1
	else:
		options.show()
		Engine.time_scale = 0
	paused = !paused
