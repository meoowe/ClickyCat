extends Node2D

@export var show_title_features: bool = false
@export var show_confetti: bool = false
@onready var house: Parallax2D = $CanvasLayer/House
@onready var dog: Parallax2D = $CanvasLayer/Dog
@onready var luna: Parallax2D = $CanvasLayer/Luna
@onready var cat: Parallax2D = $CanvasLayer/Cat
@onready var confetti: Parallax2D = $CanvasLayer/Confetti

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	if !show_title_features:
		house.visible = false
		dog.visible = false
		cat.visible = false
		luna.visible = false
	if show_confetti:
		confetti.show()
		


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass
