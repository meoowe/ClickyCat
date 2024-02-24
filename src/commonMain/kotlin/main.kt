@file:Suppress("UNUSED_VARIABLE")

import korlibs.audio.sound.*
import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.color.Colors.BLACK
import korlibs.image.color.Colors.LAWNGREEN
import korlibs.image.color.Colors.ORANGE
import korlibs.image.color.Colors.YELLOW
import korlibs.image.font.*
import korlibs.image.format.*
import korlibs.image.text.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.time.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.math.geom.Size
import korlibs.time.milliseconds
import korlibs.time.seconds
import kotlinx.coroutines.*

// title screen
suspend fun main() = Korge(windowSize = Size(780, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()
//TODO: Make the volume adjustable
    sceneContainer.changeTo { TitleScreen() }
    val sound = resourcesVfs["music2.mp3"].readSound()
    sound.play(infinitePlaybackTimes)
    addUpdater { sound.volume = volume }
}
var score = 0
var volume = 100.0
class TitleScreen : Scene() {
    override suspend fun SContainer.sceneMain() {


        val version = "1.1.0-RELEASE_24.2.2024"
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
            fontSize = 15.0
        }
        val creditsButton = uiButton {
            onClick { sceneContainer.changeTo { Credits() }; clickSound.play() }
            text = "Credits"
            stage?.let { alignRightToRightOf(it) }
            stage?.let {alignBottomToBottomOf(it)}

        }
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
        stage?.delayFrame()
        val time = 30.seconds

        val house = image(KR.img.house.read()) {
            position(-32, 200)
            scale(0.125)
        }
        val clicky = sprite(KR.img.cat.read()) {
            position(220, 200)
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
            position(40, 215)
            scale(0.125)

        }
        var height = 4.0
        val scoreDisplay = text("Score: $score") {
            position(675, 0)
            fontSize = 25.0
        }
        var scoreAmount = 10
        onClick { height = 4.0;scoreAmount = 20 }

        val mountain = sprite(KR.img.mountain.read()) {
            scale(0.5)
            position(270, -100)
        }

        fun moveDog() {
            if (bark.x.toInt() != 640) {
                bark.x += 2.0
                bark.y -= 1
            }else {
                launch(coroutineContext) {
                    sceneContainer.changeTo { Winningscreen() }
                }

            }
        }
        fun moveCat() {

            if (clicky.x.toInt() != 640) {
                clicky.x += 25.0
                clicky.y -= 8
                score += scoreAmount
                scoreDisplay.text = "Score: $score"
            } else {
                launch(coroutineContext) {
                    sceneContainer.changeTo { Winningscreen() }
                }

            }

        }
        var catHasMoved = false
        clicky.onClick {
                catHasMoved = true
                moveCat()
                moveDog()
        }
        addUpdater {
            if (input.keys.justPressed(Key.SPACE))launch(coroutineContext) {moveCat();moveDog();catHasMoved = true}
        }
        addUpdater {
            if(catHasMoved)
            moveDog()
        }
        bark.onCollision({it == clicky}) {
            launch(coroutineContext) {
                sceneContainer.changeTo{LoosingScreen()}
            }
        }
        bark.interval(time = 400.milliseconds) {

        }
        val luna = sprite(KR.img.luna.read()) {
            scale = 0.125
            position(600,-35)
        }

    }}


    class Options : Scene() {
        override suspend fun SContainer.sceneMain() {
            val clickSound = resourcesVfs["click.mp3"].readSound()
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
            val back = uiButton(icon = resourcesVfs["img/icons/arrow-removebg-preview.png"].readBitmapSlice()){
                onClick { sceneContainer.changeTo { GameScene() }; clickSound.play() }
                text = "Back To Game"
                centerXOnStage()
                positionY(225)
                bgColorOut = ORANGE
                bgColorOver = YELLOW
                textColor = BLACK
                textAlignment = TextAlignment.RIGHT
                icon?.left
            }
            val credits = text("Made by meoowe and LeoNunk") {
                stage?.let { alignBottomToBottomOf(it) }
                fontSize = 20.0
            }
            val creditsButton = uiButton {
                onClick { sceneContainer.changeTo { Credits() }; clickSound.play() }
                text = "Credits"
                stage?.let { alignRightToRightOf(it) }
                centerXOnStage()
                positionY(355)
                bgColorOut = ORANGE
                bgColorOver = YELLOW
                textColor = BLACK

            }
            val volumeSlider = uiSlider(value = 100, min = 0, max = 100) {
                TODO("Not yet implemented")
            }
        }


    }

class Winningscreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        val confetti = image(KR.img.confetti1.read())

        val winText = text("You have won with a score of $score!") {
            centerYOnStage()
            font = KR.publicpixel.read()
        }
        val continueText = text("Press ENTER or button to continue") {
            centerXOnStage()
            positionY(500)
            font = KR.publicpixel.read()
        }
        val continueButton = uiButton {
            text = "Continue"
            onClick { sceneContainer.changeTo { TitleScreen() }
        }
        continueText.interval(2.seconds) {
            continueText.visible = !continueText.visible
        }
        val luna = sprite(KR.img.luna.read()) {
            scale = 0.125
            stage?.let { alignBottomToBottomOf(it) }
        }
        val trophy = sprite(KR.img.trophy.read()) {
            scale = 0.125
            position(500,-35)
        }
            addUpdater {
                if (input.keys.justPressed(Key.ENTER))launch(coroutineContext) {  sceneContainer.changeTo { TitleScreen() }}
            }
    }
        score = 0
}
}
class Credits : Scene() {
    override suspend fun SContainer.sceneMain() {
        val clickSound = resourcesVfs["click.mp3"].readSound()
        val cloudBackground = image(KR.img.clouds.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }
        val scroll = image(KR.img.credits.read()) {

            scale(0.5)
                .centerOnStage()
        }
        val creditsText = text("Programming - meoowe") {
            positionX(210)
            positionY(125)
            color = BLACK
            fontSize = 35.0
        }

        val othercreditsText = text("Art - LeoNunk") {
            positionX(290)
            positionY(155)
            color = BLACK
            fontSize = 35.0
        }
        val back = uiButton {
            onClick { sceneContainer.changeTo { TitleScreen() }; clickSound.play() }
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK

        }

    }


}
class LoosingScreen : Scene() {
    override suspend fun SContainer.sceneMain() {

        val winText = text("You have lost with a score of $score!") {
            centerYOnStage()
            font = KR.publicpixel.read()
        }
        val continueText = text("Press ENTER or button to continue") {
            centerXOnStage()
            positionY(200)
            font = KR.publicpixel.read()
        }
        val continueButton = uiButton {
            text = "Continue"
            onClick { sceneContainer.changeTo { TitleScreen() }
            }
            continueText.interval(1.seconds) {
                continueText.visible = !continueText.visible
            }


        }
        addUpdater {
            if (input.keys.justPressed(Key.ENTER))launch(coroutineContext) {  sceneContainer.changeTo { TitleScreen() }}
        }
        score = 0
    }


}





