extends Label

var time: float = 0.0


func _process(delta: float) -> void:
	if Global.game_has_started:
		time += delta
		text = "DBG: Time elapsed since Global.game_has_started = true: " + format_time(time)

func format_time(t: float) -> String:
	var minutes = int(t / 60)
	var seconds = int(t) % 60
	var msec = int((t - int(t)) * 100)
	return "%02d:%02d.%02d" % [minutes, seconds, msec]
