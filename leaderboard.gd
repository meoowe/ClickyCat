extends Control

var entries = []
var hide_balloon_scores: bool = true
var metadata

func get_scores() -> Dictionary:
	return await Leaderboards.get_scores(Global.LEADERBOARD_ID)


func _ready() -> void:
	var scores = await get_scores()
	scores = scores["scores"] # the data is a dictionary containing a scores array
	print(scores)
	for i in scores:
		metadata = i["metadata"]
		if i["is_current_player"]:
			%items.add_item("You!",get_platform_image(metadata["platform"]), false)
			%scores.add_item(str(i["score"]),null, false)
		else:
			%items.add_item(i["name"],get_platform_image(metadata["platform"]), false)
			%scores.add_item(str(i["score"]),null, false)
		entries.append(i)


func _on_texture_button_pressed() -> void:
	Scenes.title()


func _on_check_button_toggled(toggled_on: bool) -> void:
	var scores = await Leaderboards.get_scores(Global.LEADERBOARD_ID)
	scores = scores["scores"]
	print(scores)
	%items.clear()
	%scores.clear()
	if !toggled_on and !entries[0]["metadata"]["balloon used?"]:
		%items.remove_item(0)
		%scores.remove_item(0)
	for i in scores:
		metadata = i["metadata"]
		if metadata["balloon used?"] and toggled_on:
			if i["is_current_player"]:
				%items.add_item("You!", get_platform_image(metadata["platform"]), false)
			else:
				%items.add_item(i["name"], get_platform_image(metadata["platform"]), false)
			%scores.add_item(str(i["score"]),null, false)
		elif metadata["balloon used?"] == false:
			if i["is_current_player"]:
				%items.add_item("You!", get_platform_image(metadata["platform"]), false)
				%scores.add_item(str(i["score"]),null, false)
			else:
				%items.add_item(i["name"], get_platform_image(metadata["platform"]), false)
				%scores.add_item(str(i["score"]),null, false)
		entries.append(i)


func get_platform_image(platform: String) -> Texture:
	match platform:
		"web":
			return preload("res://assets/img/platform_images/web.png")
		"windows":
			return preload("res://assets/img/platform_images/windows.png")
		"android":
			return preload("res://assets/img/platform_images/android.png")
		"linux":
			return preload("res://assets/img/cat.png")
		_:
			return null # No image for unknown platforms
