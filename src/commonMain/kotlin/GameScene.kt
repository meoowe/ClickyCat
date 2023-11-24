@file:Suppress("unused", "UNUSED_VARIABLE")

import korlibs.audio.sound.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.collision.*
import korlibs.logger.*
import korlibs.math.geom.*

suspend fun window(): Unit = Korge(windowSize = Size(100, 600), backgroundColor = Colors["#55a7ff"], title = "Click Cat") {
    val sceneContainer = sceneContainer()


    sceneContainer.changeTo { GameScene() }
}

class GameScene : Scene() {


    override suspend fun SContainer.sceneMain() {
        // @TODO: Main scene code here (after sceneInit)
        val cloudBackground = image(resourcesVfs["img/clouds.png"].readBitmap()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
        val optionButton = uiButton {

            onClick {
                sceneContainer.changeTo { Options() }
            }
            text = "Options"
        }


        val sound = resourcesVfs["Track.mp3"].readSound()
        sound.play(infinitePlaybackTimes)

        val house = image(resourcesVfs["img/house.png"].readBitmap()) {
            position(50, 200)
            scale(0.125)
        }
        val cat = sprite(resourcesVfs["img/cat.png"].readBitmap()) {
            position(100,200)
            scale(0.125)
        }
        val grass = image(resourcesVfs["img/grass2.png"].readBitmap()) {
            scale(0.5)
            positionY(4)
            positionX(1)
        }
        val othergrass = image(resourcesVfs["img/grass1.png"].readBitmap()) {
            scale(0.5)
            positionY(4)
            positionX(280)
        }
        val dog = sprite(resourcesVfs["img/dog.png"].readBitmap()) {
            position(175,200)
            scale(0.125)
            onCollision { Console.warn("Collided   ") }
        }
        var height = 3.0


        cat.onClick {cat.x += 10.0; cat.y -= height; println(gameWindow.width);println(cat.x.toInt());}
            if (cat.x >= gameWindow.width.toDouble()) {
               println(gameWindow.width.toString() + "hello")
               println("you have reached the end")
            }
        cat.onClick { println("cat:collided") }
        val balloon = sprite(resourcesVfs["img/balloon.png"].readBitmap()) {
            onClick { height = 6.0 }
            scale(0.125)
            positionX(50)
        }
        val mountain = image(resourcesVfs["img/mountain.png"].readBitmap()) {
            scale(0.5)
            position(300,10)
        }


    }


        }
