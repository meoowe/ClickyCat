@file:Suppress("UNUSED_VARIABLE")

import korlibs.audio.sound.*
import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.color.Colors.BLACK
import korlibs.image.color.Colors.ORANGE
import korlibs.image.color.Colors.YELLOW
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.tween.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.math.geom.*
import korlibs.math.interpolation.*
import korlibs.time.*
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
const val volume = 100.0






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
        val dogSpriteMap = resourcesVfs["img/DogBark.png"].readBitmap()
        val dogAnimation = SpriteAnimation(
            spriteMap = dogSpriteMap,
            spriteWidth = 1024,
            spriteHeight = 895,
            marginTop = 0,
            marginLeft = 0,
            columns = 2,
            rows = 8,
            offsetBetweenColumns = 0,
            offsetBetweenRows = 0,
        )
        val bark = sprite(dogAnimation) {
            position(40, 215)
            scale(0.125)
        }
        var clickAmount = 0
        val scoreDisplay = text("Score: $score") {
            position(675, 0)
            fontSize = 25.0
        }
        var scoreAmount = 10
        onClick { height = 4.0;scoreAmount = 20 }

        val mountain = sprite(KR.img.mountain.read()) {
            scale(0.5)
            position(290, -100)
        }
        val barkSound = resourcesVfs["bark.mp3"].readSound()
        barkSound.play(infinitePlaybackTimes)
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
                clickAmount += 1
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
            bark.playAnimationLooped()
            catHasMoved = true
            moveCat()
            moveDog()
        }
        addUpdater {
            if (input.keys.justPressed(Key.SPACE))launch(coroutineContext) {moveCat();moveDog();catHasMoved = true;bark.playAnimationLooped()}
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
        val luna = sprite(KR.img.luna.read()) {
            scale = 0.125
            position(600,-35)
        }
        while(true) {
            bark.tween(bark::rotation[2.degrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
            bark.tween(bark::rotation[(-2).degrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)

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
            val back = uiButton {
                onClick { sceneContainer.changeTo { GameScene() }; clickSound.play() }
                text = "Back To Game"
                centerXOnStage()
                positionY(225)
                bgColorOut = ORANGE
                bgColorOver = YELLOW
                textColor = BLACK
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
        }


    }

class Winningscreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        val confetti = image(KR.img.confetti1.read())
        val winText = text("You have won with a score of $score!") {
            centerYOnStage()
            font = KR.publicpixel.read()
        }
        val continueButton = uiButton {
            text = "Continue"
            onClick { sceneContainer.changeTo { TitleScreen() }
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
            centerOnStage()
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

        val looseText = text("You have lost with a score of $score!") {
            centerYOnStage()
            font = KR.publicpixel.read()
        }
        val continueButton = uiButton {
            text = "Continue"
            onClick { sceneContainer.changeTo { TitleScreen() } }


        }
        addUpdater {
            if (input.keys.justPressed(Key.ENTER))launch(coroutineContext) {  sceneContainer.changeTo { TitleScreen() }}
        }
        score = 0
    }


}
