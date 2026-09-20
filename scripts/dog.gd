extends RigidBody2D

@export var follower: PathFollow2D

func _on_game_paused(paused: bool) -> void:
	if paused:
		$dog2.stop()
		$barkNoise.stop()
		print(paused)
	$dog2.play()
	$barkNoise.play()

func _process(delta: float) -> void:
	if Global.game_has_started:
		follower.progress += Global.dog_speed * delta
