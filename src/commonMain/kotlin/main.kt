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
import korlibs.korge.time.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.logger.*
import korlibs.math.geom.*
import korlibs.time.*

// title screen
suspend fun main() = Korge(windowSize = Size(780, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo { TitleScreen() }
}
class TitleScreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        val version = "30-11-2023_Unfinished@10PM-2DAYS"
        val clickSound = resourcesVfs["click.mp3"].readSound()
        val cover = image(KR.img.cover.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
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
        playButton.onClick { sceneContainer.changeTo { GameScene()} }
        playButton.onClick { clickSound.play() }

        val quitButton = uiButton("Quit to Desktop") { position(350, 250) }
        quitButton.onClick { gameWindow.close(0)  }
        onClick { clickSound.play() }

        val verText = text("Version: $version") {stage?.let { alignBottomToBottomOf(it) }
        fontSize = 10.0
        }
        if (input.keys.justPressed(Key.ESCAPE)) gameWindow.close(0)
    }
}





class GameScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val clickSound = resourcesVfs["click.mp3"].readSound()
        val optionButton = uiButton {
            onClick {
                sceneContainer.changeTo { Options() }
                clickSound.play()
            }
            text = "Options"
        }
        val time = 30.seconds

        val house = image(KR.img.house.read()) {
            position(-32,200)
            scale(0.125)
        }
        val clicky = sprite(KR.img.cat.read()) {
            position(250,200)
            scale(0.125)
        }
        val grass = image(KR.img.grass1.read()) {
            scale(0.5)
            positionY(4)
            positionX(1)
        }
        val otherGrass = image(KR.img.grass2.read()) {
            scale(0.5)
            positionY(4)
            positionX(280)
        }
        val bark = sprite(KR.img.dog.read()) {
            position(90,195)
            scale(0.125)
            rotation = 16.degrees
        }
        var height = 3.0
        var score = 0
        val scoreDisplay = text("Score: $score") {position(675,0)
            fontSize = 25.0
        }
        var scoreAmount = 1
        onClick { height = 6.0;scoreAmount = 2 }

        val balloon = sprite(KR.img.balloon.read()) {

            scale(0.125)
            positionX(50)
        }
        balloon.interval(20.seconds) {
            balloon.visible = false
            scoreAmount = 1
            height = 3.0

        }
        balloon.interval(1.minutes) {
            balloon.visible = true
            val randomPos = (50..700).random()
            balloon.positionX(randomPos)
        }
        val mountain = image(KR.img.mountain.read()) {
            scale(0.5)
            position(250,40)
        }

        val music = resourcesVfs["music2.mp3"].readSound()
        music.play(infinitePlaybackTimes)
        val tutorialText = uiText("Hello there, welcome to ClickyCat") {
            centerOnStage()

        }
        val continueText = uiText("Click to continue") {
            centerXOnStage()
            positionY(30)
        }
        tutorialText.onClick { continueText.visible = false ;tutorialText.text = "The Brown Dog named Bark will be chasing you"}
        continueText.interval(20.seconds) {
            continueText.visible = true
            tutorialText.onClick { continueText.visible = false ;tutorialText.text = "The Brown Dog named Bark will be chasing you"}
            continueText.onClick { continueText.visible = false ;tutorialText.text = "The Brown Dog named Bark will be chasing you"}

        }
        bark.speed = 2.0
        balloon.onClick { height = 6.0;scoreAmount = 2 }
        fun moveCat() {
            if (/*Implement*/clicky.x.toInt() != 780) {
                clicky.x += 10.0
                clicky.y -= height
                println(gameWindow.width)
                println(clicky.x.toInt())
                score += scoreAmount
                scoreDisplay.text = "Score: $score"
            }
            else {
                Console.trace("Game won!!!! Score:$score")
            }
        }
        clicky.onClick {moveCat() }
        addUpdater {
            if (input.keys.justPressed(Key.SPACE)) moveCat()
        }
        if(bark.collidesWith(clicky)) { Console.debug("Bark: Collided")}
    }







class Options : Scene() {
    override suspend fun SContainer.sceneMain() {
        val clickSound = resourcesVfs["click.mp3"].readSound()
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
                clickSound.play()
            }
            text = "Quit To Title"
            centerXOnStage()
            positionY(125)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK

        }
        val back = uiButton {
            onClick { sceneContainer.changeTo{GameScene()}; clickSound.play()}
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


}}




