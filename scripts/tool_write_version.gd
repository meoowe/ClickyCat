@tool
extends Node

const VERSION_FILE_PATH = "res://version.txt"

@export var version_string: String = "1.0.0":
	set(value):
		version_string = value.strip_edges()
		if Engine.is_editor_hint():
			save_version_plain_text()

func _ready() -> void:
	# Load the accurate text file version
	print("Hey bro im running")
	load_version_plain_text()
	print("VERSION INFO: txt: ", version_string, "\nProject Settings: ", ProjectSettings.get_setting("application/config/version"))
	# Push to Global singleton only when the actual game runs
	if not Engine.is_editor_hint():
		var global_as_node = get_node_or_null("/root/Global")
		if global_as_node:
			global_as_node.VERSION = version_string
	ProjectSettings.set_setting("application/config/version", version_string)

func save_version_plain_text() -> void:
	# Keep ProjectSettings updated
	ProjectSettings.set_setting("application/config/version", version_string)
	# ProjectSettings changes are only temporary in memory until you save them to project.godot:
	ProjectSettings.save() 
	var file = FileAccess.open(VERSION_FILE_PATH, FileAccess.WRITE)
	if file:
		file.store_string(version_string)
		file.close()
		print("Successfully wrote version file: ", version_string)
	else:
		var err = FileAccess.get_open_error()
		push_error("Failed to write version file. Error code: %d" % err)

func load_version_plain_text() -> void:
	if not FileAccess.file_exists(VERSION_FILE_PATH):
		return
		
	var file = FileAccess.open(VERSION_FILE_PATH, FileAccess.READ)
	if file:
		var content = file.get_as_text().strip_edges()
		file.close()
		
		if content != version_string:
			version_string = content
			notify_property_list_changed()
