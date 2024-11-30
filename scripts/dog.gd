extends Area2D
@onready var cat: Area2D = $"../cat"
@onready var ground: StaticBody2D = $"../ground"
@onready var mountain: StaticBody2D = $"../mountain"
@onready var follower: PathFollow2D = $path/follower

var floor = true
const SPEED = 300.0
const JUMP_VELOCITY = -400.0

var pos = Vector2(0,0)

func _ready() -> void:
	_gravity()

#func _process(delta: float) -> void:
	#follower.progress += 0.1
	#pos = follower.position - self.position
	#pos = pos.normalized() * delta * SPEED
	#self.position += pos

func _gravity():
	while true:
		await get_tree().create_timer(0.1).timeout
		if floor == true:
			print("floor!")
		elif position.y < 0:
			self.position.y += 5
			print(position)

func _on_body_entered(body: Node2D) -> void:
	floor = true


func _on_body_exited(body: Node2D) -> void:
	floor = false
