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
import korlibs.korge.time.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlinx.coroutines.*

// title screen
suspend fun main() = Korge(windowSize = Size(780, 400), backgroundColor = Colors["#0063FF"], title = "Click Cat") {
    val sceneContainer = sceneContainer()
//?TODO: Make the volume adjustable
    sceneContainer.changeTo {
        TitleScreen()
    }
    val sound = resourcesVfs["music2.mp3"].readSound()
    if (playMusic) {
        sound.play(infinitePlaybackTimes)
    }
}
var score = 0
var playMusic = true
var hiScore = score











class TitleScreen : Scene() {
    override suspend fun SContainer.sceneInit() {
        //= Set version text
        val version = "1.3.1-DEV_24.4.2024"
        val verText = text("Version: $version") {
            stage?.let {
                alignBottomToBottomOf(it)
                fontSize = 15.0
            }
        }
        // Load click sound effect from resources
        val clickSound = resourcesVfs["click.mp3"].readSound()

        // Add the cloud background
        val background = image(KR.img.cover.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }

        // Add the title text
        val title = text("Clicky the Cat") {
            positionX(150)
            color = Colors.LAWNGREEN
            fontSize = 30.0
        }

        // Add the play button
        val playButton = uiButton("Play") {
            position(350, 200)

        }

        // Add the quit button
        val quitButton = uiButton("Quit to Desktop") {
            position(350, 250)
        }

        // Add the credit button
        val creditsButton = uiButton {
            text = "Credits"
            stage?.let {
                alignRightToRightOf(it)
            }
            stage?.let {
                alignBottomToBottomOf(it)
            }
        }

        // Call main scene function
        sceneMain(
            playButton,
            clickSound,
            quitButton,
            creditsButton
        )
    }
    private suspend fun sceneMain(
        playButton: UIButton,
        clickSound: Sound,
        quitButton: UIButton,
        creditsButton: UIButton
    ) {
        // Handle play button click
        playButton.onClick {

            //Change to GameScene()
            sceneContainer.changeTo {
                GameScene()
            }
        }

        // Play click sound on play button click
        playButton.onClick {
            clickSound.play()
        }

        //Handle quit button click
        quitButton.onClick {

            //Close the game window
            gameWindow.close(0)
        }

        // Handle credit button click
        creditsButton.onClick {

            //Change to Credits()
            sceneContainer.changeTo {
                Credits()
            }
            // Play click sound
            clickSound.play()
        }
    }

}
class GameScene : Scene() {
    override suspend fun SContainer.sceneInit(){
        val scoreAmount = 10
        // Define click sound effect
        val clickSound = resourcesVfs["click.mp3"].readSound()
        // Define option button
        val optionButton = uiButton {
            // Set text to "Options"
            text = "Options"
        }
        // Define house image
        val house = image(KR.img.house.read()) {
            // Position in the bottom right corner
            position(-32, 200)
            scale(0.125)
        }
        // Define  main cat image
        val clicky = sprite(KR.img.cat.read()) {
            position(220, 200)
            scale(0.125)
        }
        // TODO: Possibly make this into 1 sprite
        // Define floor
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



        val mountain = sprite(KR.img.mountain.read()) {
            scale(0.5)
            position(290, -100)
        }
        val barkSound = resourcesVfs["bark.mp3"].readSound()
        val bark = sprite(dogAnimation) {
            position(40, 215)
            scale(0.125)
        }
        val clickAmount = 0
        val scoreDisplay = text("Score: $score") {
            position(620, 0)
            fontSize = 25.0
        }
        val balloon = sprite(KR.img.balloon.read()) {
            scale(0.125)
            positionX(50)
        }
        val catHasMoved = false
        sceneMain(
            clickSound = clickSound,
            optionButton = optionButton,
            clicky = clicky,
            bark = bark,
            barkSound = barkSound,
            clickAmount = clickAmount,
            scoreDisplay = scoreDisplay,
            scoreAmount = scoreAmount,
            catHasMoved = catHasMoved,
            balloon = balloon
        )
    }
    private suspend fun SContainer.sceneMain(
        clickSound: Sound,
        optionButton: UIButton,
        clicky: Sprite,
        bark: Sprite,
        barkSound: Sound,
        clickAmount: Int,
        scoreDisplay: Text,
        scoreAmount: Int,
        catHasMoved: Boolean,
        balloon:  Sprite
    )
    {
        score = 0
        var mutableClickAmount = clickAmount
        var mutableCatHasMoved = catHasMoved
        var mutableScoreAmount = scoreAmount
        optionButton.onClick {
            sceneContainer.changeTo {
                Options()
            }
            clickSound.play()
        }
        fun moveDog() {
            if (bark.x.toInt() != 640) {
                bark.x += 1.0
                bark.y -= 0.5
                         }else {
                launch(coroutineContext) {
                    sceneContainer.changeTo {
                        Winningscreen()
                    }
                }

            }
        }
        fun moveCat() {
            if (clicky.x.toInt() != 850) {
                clicky.x += 20.0
                clicky.y -= 8
                mutableClickAmount += 1
                println(mutableScoreAmount)
                score += mutableScoreAmount
                scoreDisplay.text = "Score: $score Hi: $hiScore"
            }
            else {
                launch(coroutineContext) {
                    sceneContainer.changeTo {
                        Winningscreen()
                    }
                }

            }
        }
        balloon.interval(500.milliseconds) {
            balloon.visible = !balloon.visible
            val randomPos = (50..700).random()
            balloon.positionX(randomPos)
        }
        balloon.onClick {
            mutableScoreAmount = 20
        }
        clicky.onClick {
            bark.playAnimationLooped()
            mutableCatHasMoved = true
            moveCat()
            moveDog()
        }
        addUpdater {
            if (input.keys.justPressed(Key.SPACE))launch(coroutineContext) {
                moveCat()
                moveDog()
                mutableCatHasMoved = true
                bark.playAnimationLooped()
            }
        }
        // Make the dog move continuously
        addUpdater {
            if(mutableCatHasMoved) {
               moveDog()
            }
        }
        bark.onCollision({it == clicky}) {
            launch(coroutineContext) {
                sceneContainer.changeTo{
                    LoosingScreen()
               }
            }
        }
        bark.interval(1.seconds) {
            launch(coroutineContext) {
                barkSound.play()
            }
        }
        addUpdater {
            // Why did I make this catastrophe?
            when(clicky.x.toInt()) {
                245 -> {
                    clicky.y = 200.0
                }
                270 -> {
                    clicky.y = 200.0
                }
                295 -> {
                    clicky.y = 171.0
                }
                345 -> {
                    clicky.y = 152.0
                }
                320 -> {
                    clicky.y = 159.0
                }
                445 -> {
                    clicky.y = 110.0
                }
                495 -> {
                    clicky.y = 75.0
                }
                520 -> {
                    clicky.y = 63.0
                }
                545 -> {
                    clicky.y = 50.0
                }
                570 -> {
                    clicky.y = 40.0
                }
                395 -> {
                    clicky.y = 130.0
                }
                420 -> {
                    clicky.y = 125.0
                }
                470 -> {
                    clicky.y = 100.0
                }
                595 -> {
                    clicky.y = 25.0
                }
            }
            when(bark.x.toInt()) {
                42 -> {
                    bark.y = 215.0
                }
                52 -> {
                    bark.y = 215.0
                }
                54 -> {
                    bark.y = 215.0
                }
                56 -> {
                    bark.y = 215.0
                }
                58 -> {
                    bark.y = 215.0
                }
                60 -> {
                    bark.y = 215.0
                }
                62 -> {
                    bark.y = 215.0
                }
                64 -> {
                    bark.y = 215.0
                }
                68 -> {
                    bark.y = 215.0
                }
                70 -> {
                    bark.y = 215.0
                }
                72 -> {
                    bark.y = 215.0
                }
                74 -> {
                    bark.y = 215.0
                }
                78 -> {
                    bark.y = 215.0
                }
                80 -> {
                    bark.y = 215.0
                }
                82 -> {
                    bark.y = 215.0
                }
                84 -> {
                    bark.y = 215.0
                }
                86 -> {
                    bark.y = 215.0
                }
                88 -> {
                    bark.y = 215.0
                }
                90 -> {
                    bark.y = 215.0
                }
                92 -> {
                    bark.y = 215.0
                }
                94 -> {
                    bark.y = 215.0
                }
                98-> {
                    bark.y = 215.0
                }
                100 -> {
                    bark.y = 215.0
                }
                102 -> {
                    bark.y = 215.0
                }
                104 -> {
                    bark.y = 215.0
                }
                108-> {
                    bark.y = 215.0
                }
                110 -> {
                    bark.y = 215.0
                }
                112 -> {
                    bark.y = 215.0
                }
                114-> {
                    bark.y = 215.0
                }
                118 -> {
                    bark.y = 215.0
                }
                120 -> {
                    bark.y = 215.0
                }
                122 -> {
                    bark.y = 215.0
                }
                124 -> {
                    bark.y = 215.0
                }
                128-> {
                    bark.y = 215.0
                }
                130 -> {
                    bark.y = 215.0
                }
                132 -> {
                    bark.y = 215.0
                }
                134-> {
                    bark.y = 215.0
                }
                138 -> {
                    bark.y = 215.0
                }
                140 -> {
                    bark.y = 215.0
                }
                142 -> {
                    bark.y = 215.0
                }
                144-> {
                    bark.y = 215.0
                }
                148 -> {
                    bark.y = 215.0
                }
                150 -> {
                    bark.y = 215.0
                }
                152 -> {
                    bark.y = 215.0
                }
                154-> {
                    bark.y = 215.0
                }
                158 -> {
                    bark.y = 215.0
                }
                160 -> {
                    bark.y = 215.0
                }
                162 -> {
                    bark.y = 215.0
                }
                164-> {
                    bark.y = 215.0
                }
                168 -> {
                    bark.y = 215.0
                }
                170 -> {
                    bark.y = 215.0
                }
                172 -> {
                    bark.y = 215.0
                }
                174-> {
                    bark.y = 215.0
                }
                178 -> {
                    bark.y = 215.0
                }
                180 -> {
                    bark.y = 215.0
                }
                182 -> {
                    bark.y = 215.0
                }
                184-> {
                    bark.y = 215.0
                }
                188 -> {
                    bark.y = 215.0
                }
                190 -> {
                    bark.y = 215.0
                }
                192 -> {
                    bark.y = 215.0
                }
                194-> {
                    bark.y = 215.0
                }
                198 -> {
                    bark.y = 215.0
                }
                200 -> {
                    bark.y = 215.0
                }
                202 -> {
                    bark.y = 215.0
                }
                204 -> {
                    bark.y = 215.0
                }
                208-> {
                    bark.y = 215.0
                }
                210 -> {
                    bark.y = 215.0
                }
                212 -> {
                    bark.y = 215.0
                }
                214-> {
                    bark.y = 215.0
                }
                218 -> {
                    bark.y = 215.0
                }
                220 -> {
                    bark.y = 215.0
                }
                538 -> {
                    bark.y = 62.0
                }
            }
        }
    }}








class Options : Scene() {
    override suspend fun SContainer.sceneInit(){
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
            text = "Quit To Title"
            centerXOnStage()
            positionY(125)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK
        }
        val credits = text("Made by meoowe and LeoNunk") {
            stage?.let {
                alignBottomToBottomOf(it)
            }
            fontSize = 20.0
        }
        val back = uiButton {
            text = "Back To Game"
            centerXOnStage()
            positionY(225)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK
        }
        val creditsButton = uiButton {
            text = "Credits"
            stage?.let {
                alignRightToRightOf(it)
            }
            centerXOnStage()
            positionY(355)
            bgColorOut = ORANGE
            bgColorOver = YELLOW
            textColor = BLACK

        }
        sceneMain(quitButton, back, creditsButton, clickSound)

    }
    private suspend fun sceneMain(
        quitButton: UIButton,
        back: UIButton,
        creditsButton: UIButton,
        clickSound: Sound
    ) {
        quitButton.onClick {
            sceneContainer.changeTo {
                TitleScreen()
            }
            clickSound.play()
        }
        back.onClick {
            sceneContainer.changeTo {
                GameScene()
            }
            clickSound.play()
        }
        creditsButton.onClick{
            sceneContainer.changeTo {
                Credits()
            }
            clickSound.play()
        }
    }


}








class Winningscreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        val confetti = image(KR.img.confetti1.read())
        var hiScoreBeaten = false
        if(score > hiScore) {
            hiScore = score
            hiScoreBeaten = true
        }
        val winText = text("You have won with a score of $score! \n Your high score is $hiScore") {
            centerOnStage()
            fontSize = 25.0
        }
        val beatenScoreText = text("You beat your high score! Well done!!"){visible = false; centerXOnStage()}
        if(hiScoreBeaten) {beatenScoreText.visible = true}
        val continueButton = uiButton {
            text = "Continue"
            onClick { sceneContainer.changeTo {
                TitleScreen()
            }
            }
            val luna = sprite(KR.img.luna.read()) {
                scale = 0.125
                stage?.let {
                    alignBottomToBottomOf(it)
                }
            }
            val trophy = sprite(KR.img.trophy.read()) {
                scale = 0.125
                position(500,0)
            }
            addUpdater {
                if (input.keys.justPressed(Key.ENTER))
                    launch(coroutineContext) {
                        sceneContainer.changeTo {
                            TitleScreen()
                        }
                    }
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
            onClick {
                sceneContainer.changeTo {
                    TitleScreen()
                }
                clickSound.play()
            }
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

        val loosingImage = image(KR.img.lost.read()) {
            anchor(.5, .5)
            scale(1)
            position(400, 200)
        }

        val looseText = text("You have lost with a score of $score!") {
            centerOnStage()
            fontSize = 20.0
        }

        val continueButton = uiButton {
            text = "Continue"
            onClick {
                sceneContainer.changeTo {
                    TitleScreen()
                }
            }

        }

        addUpdater {
            if (input.keys.justPressed(Key.ENTER)) launch(coroutineContext) {
                sceneContainer.changeTo {
                    TitleScreen()
                }
            }
        }

        score = 0

    }




}
