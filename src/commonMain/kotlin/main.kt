import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.color.Colors.BLACK
import korlibs.image.color.Colors.RED
import korlibs.image.font.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

// title screen

suspend fun main() = Korge(windowSize = Size(800, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo{ MyScene() }
}

class MyScene : Scene() {
	override suspend fun SContainer.sceneMain() {
        // moving clouds image code
		val minDegrees = (-0.25).degrees
		val maxDegrees = (+0.25).degrees

		val cloudBackground = image(resourcesVfs["korge.png"].readBitmap()) {
			rotation = maxDegrees
			anchor(.5, .5)
			scale(1)
			position(400, 200)
		}


        // cat image code

        val cat = image(resourcesVfs["cat.webp"].readBitmap()) {
            scale(0.125)
            position(350,0)

        }

        /* text for title screen TODO:add PublicPixel font */
        val title = text("Clicky Cat") {
            position(100,100)
            color = BLACK
            fontSize = 30.0
            font = resourcesVfs["PublicPixel.ttf"].readTtfFont()
        }
        val sound = resourcesVfs["Track.mp3"].readMusic() {}
        sound.play(PlaybackTimes(5))

    }
}
