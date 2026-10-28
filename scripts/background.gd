
extends Node2D

@export var show_title_features: bool
@export var show_confetti: bool
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
	else:
		house.visible = true
		dog.visible = true
		cat.visible = true
		luna.visible = true
	if show_confetti:
		confetti.show()
	$CanvasLayer/Clouds.autoscroll.x = Global.camera_scroll_speed

func toggle_title_feat(value: bool):
	if !value:
		house.visible = false
		dog.visible = false
		cat.visible = false
		luna.visible = false
