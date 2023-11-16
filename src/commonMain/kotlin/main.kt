import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.color.Colors.LAWNGREEN
import korlibs.image.font.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.korge.input.*

// title screen

class MyScene : Scene() {
    override suspend fun SContainer.sceneMain() {

        val cloudBackground = image(resourcesVfs["img/korge.png"].readBitmap()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }


        // cat image code

        val cat = sprite(resourcesVfs["img/cat.png"].readBitmap()) {
            scale(0.125)
            position(350,0)

        }

        /* text for title screen */
        val title = text("Clicky Cat") {
            position(50,50)
            color = LAWNGREEN
            fontSize = 30.0
            font = resourcesVfs["PublicPixel.ttf"].readTtfFont()
        }

        // play button TODO:implement scene change

        val playButton = uiButton("Play") {
            position(350,200)

        }
        playButton.onClick{
            sceneContainer.changeTo({GameScene()})
            sceneDestroy()

        }
        // play AI generated music
        val sound = resourcesVfs["Track.mp3"].readMusic()
        sound.play(infinitePlaybackTimes)

    }
}
suspend fun main() = Korge(windowSize = Size(800, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo{ MyScene() }
}


