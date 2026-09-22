extends Node
class_name State
var context

func _init(contexti):
	self.context =contexti

#@warning_ignore("unused_parameter")
func update(delta: float): # Equivalant of _process, ran every frame while this state is active
	pass

func enter(): # Called when this state becomes active
	pass

func exit():
	pass
