@file:Suppress("UNUSED_VARIABLE")

import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.color.Colors.LAWNGREEN
import korlibs.image.font.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.math.geom.*

// title screen
suspend fun main() = Korge(windowSize = Size(780, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo { TitleScreen() }
}
class TitleScreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        var version = "24-11-2023_Unfinished"
        val cloudBackground = image(resourcesVfs["img/clouds.png"].readBitmap()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }


        // cat image code

        val cat = sprite(resourcesVfs["img/cat.png"].readBitmap()) {
            scale(0.125)
            position(350, 0)

        }


        // text for title screen
        val title = text("Clicky Cat") {
            centerXOnStage()
            color = LAWNGREEN
            fontSize = 30.0
            font = resourcesVfs["PublicPixel.ttf"].readTtfFont()

        }

        // play button

        val playButton = uiButton("Play") { position(350, 200) }
        playButton.onClick { sceneContainer.changeTo { GameScene() } }

        val quitButton = uiButton("Quit to Desktop") { position(350, 250) }
        quitButton.onClick { gameWindow.close(0) /*test if works*/ }
        //val sound = resourcesVfs["Track.mp3"].readSound()
        //sound.play(infinitePlaybackTimes)
        val verText = text("Version: $version") {stage?.let { alignBottomToBottomOf(it) }
        fontSize = 10.0
        }
        if (input.keys.pressing(Key.ESCAPE)) gameWindow.close(0)
    }
}


