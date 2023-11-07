import korlibs.time.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.tween.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.math.geom.*
import korlibs.math.interpolation.*

suspend fun main() = Korge(windowSize = Size(800, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo{ MyScene() }
}

class MyScene : Scene() {
	override suspend fun SContainer.sceneMain() {
        // moving clouds image code
		val minDegrees = (-0.25).degrees
		val maxDegrees = (+0.25).degrees

		val cloudBackground = image(resourcesVfs["korge.webp"].readBitmap()) {
			rotation = maxDegrees
			anchor(.5, .5)
			scale(1)
			position(400, 200)
		}


        // cat image code

        val cat = image(resourcesVfs["cat.webp"].readBitmap()) {
            scale(0.125)
            position(400,0)

        }


	}
}
