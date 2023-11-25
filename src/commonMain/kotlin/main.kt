@file:Suppress("UNUSED_VARIABLE")

import korlibs.audio.sound.*
import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.color.Colors.BLACK
import korlibs.image.color.Colors.LAWNGREEN
import korlibs.image.color.Colors.ORANGE
import korlibs.image.color.Colors.YELLOW
import korlibs.image.font.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.logger.*
import korlibs.math.geom.*

// title screen
suspend fun main() = Korge(windowSize = Size(780, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo { TitleScreen() }
}
class TitleScreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        var version = "25-11-2023_Unfinished"
        val cloudBackground = image(KR.img.clouds.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }


        // cat image code

        val cat = sprite(KR.img.cat.read()) {
            scale(0.125)
            position(350, 0)

        }


        // text for title screen
        val title = text("Clicky the Cat") {
            positionX(150)
            color = LAWNGREEN
            fontSize = 30.0
            font = resourcesVfs["PublicPixel.ttf"].readTtfFont()

        }

        // play button

        val playButton = uiButton("Play") { position(350, 200) }
        playButton.onClick { sceneContainer.changeTo { GameScene() } }

        val quitButton = uiButton("Quit to Desktop") { position(350, 250) }
        quitButton.onClick { gameWindow.close(0) /*test if works*/ }

        val verText = text("Version: $version") {stage?.let { alignBottomToBottomOf(it) }
        fontSize = 10.0
        }
        if (input.keys.justPressed(Key.ESCAPE)) gameWindow.close(0)
    }
}
class GameScene : Scene() {


    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        /*
        val cloudBackground = image(resourcesVfs["img/clouds.png"].readBitmap()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)

        }
         */
        val optionButton = uiButton {

            onClick {
                sceneContainer.changeTo { Options() }
            }
            text = "Options"
        }




        val house = image(KR.img.house.read()) {
            position(50, 200)
            scale(0.125)
        }
        val clicky = sprite(KR.img.cat.read()) {
            position(100,200)
            scale(0.125)
        }
        val grass = image(KR.img.grass1.read()) {
            scale(0.5)
            positionY(4)
            positionX(1)
        }
        val othergrass = image(KR.img.grass2.read()) {
            scale(0.5)
            positionY(4)
            positionX(280)
        }
        val dog = sprite(KR.img.dog.read()) {
            position(180,200)
            scale(0.125)
            collidesWith(clicky)
            collidesWithShape(clicky)
            onCollision() { Console.debug("collided") }
        }
        var height = 3.0


        clicky.onClick {clicky.x += 10.0; clicky.y -= height; println(gameWindow.width);println(clicky.x.toInt());}
        if (clicky.x >= gameWindow.width.toDouble()) {
            println(gameWindow.width.toString() + "hello")
            println("you have reached the end")
        }
        val balloon = sprite(KR.img.balloon.read()) {
            onClick { height = 6.0 }

            scale(0.125)
            positionX(50)
        }
        val mountain = image(KR.img.mountain.read()) {
            scale(0.5)
            position(250,20)
        }

        val music = resourcesVfs["music2.mp3"].readSound()
        music.play(infinitePlaybackTimes)

    }


}



class Options() : Scene() {
    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val cloudBackground = image(KR.img.clouds.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
        val scroll = image(KR.img.scroll.read()) {

            scale(0.5)
                .centerOnStage()
        }

        val quitButton = uiButton {

            onClick {
                sceneContainer.changeTo { TitleScreen() }
            }
            text = "Quit To Title"
            centerXOnStage()
            positionY(125)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK

        }
        val back = uiButton {
            onClick { sceneContainer.changeTo{GameScene()}}
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK
        }
        val sound = resourcesVfs["music2.mp3"].readSound()
        sound.play(infinitePlaybackTimes)
    }



}




