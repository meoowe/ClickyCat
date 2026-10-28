extends Node
@export var title_music: AudioStreamPlayer
@export var click: AudioStreamPlayer
@export var music: AudioStreamPlayer

func toggle_music():
	if music.playing:
		music.stop()
	else: music.play()

func start_title_music():
	music.stop()
	title_music.play()

func start_game_music():
	title_music.stop()
	music.play()

func _ready() -> void:
	title_music.play()
