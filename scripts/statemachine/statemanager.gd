extends Node
class_name StateMachine

var current_state: State

func _process(delta: float) -> void:
	if !current_state:
		pass
	current_state.update(delta)

func change_state(new_state: State):
	if current_state:
		current_state.exit()
	current_state = new_state
	current_state.enter()
