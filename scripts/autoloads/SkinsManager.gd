extends Node
signal skin_unlocked(unlocked: CatSkin)
const SKIN_CATALOG: Array[CatSkin] = [
	preload("res://skins/original.tres"),
	preload("res://skins/testskin.tres")
]

func get_all_skins() -> Array[CatSkin]:
	return SKIN_CATALOG

func is_unlocked(skin_id: String) -> bool:
	for skin in Gamestate.unlocked_skins:
		if skin.id == skin_id:
			return true
	return false

func unlock(skin_id: String) -> bool:
	if is_unlocked(skin_id):
		return false
	for definition in SKIN_CATALOG:
		if definition.id != skin_id:
			continue
		var unlocked_copy := definition.duplicate(true) as CatSkin
		unlocked_copy.unlocked = true
		Gamestate.unlocked_skins.append(unlocked_copy)
		skin_unlocked.emit(unlocked_copy)
		SaveManager.save()
		return true
	push_warning("Unknown achievement id: %s" % skin_id)
	return false
