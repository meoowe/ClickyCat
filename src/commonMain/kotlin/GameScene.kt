@file:Suppress("unused", "UNUSED_VARIABLE")

import korlibs.audio.sound.*
import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.math.geom.*

suspend fun window(): Unit = Korge(windowSize = Size(100, 600), backgroundColor = Colors["#55a7ff"], title = "Click Cat") {
    val sceneContainer = sceneContainer()


    sceneContainer.changeTo { GameScene() }
}

class GameScene : Scene() {


    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val quitButton = uiButton {

            onClick {
                /*println(
                    message = "the game didn't quit LOL" +
                        "\n well if you can even call this stupid piece of code a game. \n oh it did quit. nevermind!" +
                        "Oh its just gone back to the title screen"
                )*/
                sceneContainer.changeTo { TitleScreen() }
            }
            text = "Quit To Title"
        }


        val sound = resourcesVfs["Track.mp3"].readSound()
        sound.play(infinitePlaybackTimes)

        val house = sprite(resourcesVfs["img/house.png"].readBitmap()) {
            position(50, 180)
            scale(0.125)
        }
        val cat = sprite(resourcesVfs["img/cat.png"].readBitmap()) {
            position(80,150)
            scale(0.125)
        }
        val grass = sprite(resourcesVfs["img/grass1.png"].readBitmap()) {
            scale(1)
            position(50,10)

        }
        cat.onClick {cat.x += 10.0; cat.y -= 1.0; println(gameWindow.width);println(cat.x.toInt()) }
            if (cat.x.toDouble() >= gameWindow.width.toDouble()) {
               println(gameWindow.width.toString() + "hello")
               println("you have reached end")
            }

        if (input.keys.justReleased(Key.SPACE)) println("space")
        }

}
