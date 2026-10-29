@tool
extends Node
class_name WriteVersion

## The plain-text version file is the single source of truth. Edit version.txt;
## this tool keeps Godot's project setting in sync.
const VERSION_FILE_PATH := "res://version.txt"

var version_string: String = ""

func _ready() -> void:
	var file_version := _read_version_file()
	if file_version.is_empty():
		push_warning("version.txt is missing or empty; using the project version setting.")
		file_version = str(ProjectSettings.get_setting("application/config/version", version_string)).strip_edges()
	if file_version.is_empty():
		file_version = version_string

	# Sync runtime/editor settings from the canonical file without writing to the
	# project at game startup (exported res:// files are read-only).
	version_string = file_version
	var project_version := str(ProjectSettings.get_setting("application/config/version", "")).strip_edges()
	ProjectSettings.set_setting("application/config/version", file_version)
	if Engine.is_editor_hint() and project_version != file_version:
		ProjectSettings.save()

func _read_version_file() -> String:
	if not FileAccess.file_exists(VERSION_FILE_PATH):
		return ""
	var file := FileAccess.open(VERSION_FILE_PATH, FileAccess.READ)
	if file == null:
		push_warning("Could not read version.txt (error %d)." % FileAccess.get_open_error())
		return ""
	var content := file.get_as_text().strip_edges()
	file.close()
	return content
