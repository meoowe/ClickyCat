package com.meoowe.clickycat
import korlibs.render.*
import main
class MainActivity : KorgwActivity(config = GameWindowCreationConfig(msaa = 1, fullscreen = null)) {
	override suspend fun activityMain() {
		main()
	}
}
