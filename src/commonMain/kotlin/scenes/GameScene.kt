package scenes

import hiScore
import korlibs.audio.sound.*
import korlibs.event.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.time.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.collision.*
import korlibs.time.*
import kotlinx.coroutines.*
import score

class GameScene : Scene() {
    override suspend fun SContainer.sceneInit(){
        val scoreAmount = 10
        // Define click sound effect
        val clickSound = resourcesVfs["click.mp3"].readSound()
        // Define option button
        val optionButton = uiButton {
            // Set text to "Scenes.Options"
            text = "Options"
        }
        // Define house image
        val house = image(__KR.KRImg.house.read()) {
            // Position in the bottom right corner
            position(-32, 200)
            scale(0.125)
        }
        // Define  main cat image
        val clicky = sprite(__KR.KRImg.cat.read()) {
            position(220, 200)
            scale(0.125)
        }
        // TODO: Possibly make this into 1 sprite
        // Define floor
        val grass = image(__KR.KRImg.grass1.read()) {
            scale(0.5)
            positionY(4)
            positionX(1)
        }
        val otherGrass = image(__KR.KRImg.grass2.read()) {
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



        val mountain = sprite(__KR.KRImg.mountain.read()) {
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
        val balloon = sprite(__KR.KRImg.balloon.read()) {
            scale(0.125)
            positionX(50)
        }
        val catHasMoved = false
        val moveButton = uiButton {
            text = "Move"
            centerXOnStage()
        }
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
            balloon = balloon,
            moveButton = moveButton
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
        balloon: Sprite,
        moveButton: UIButton
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
        /**
         * onReachTarget: This function is called when a sprite reaches its target position.
         * See [moveSprite()]
         *
         * It launches a new coroutine and changes the scene to the WinningScreen.
         * The function uses the launch coroutine builder to start a new coroutine in the given coroutineContext.
         * Inside the coroutine, it calls the changeTo function on
         * the sceneContainer object, passing a lambda that creates and returns a new
         * instance of the WinningScreen class.
         */
        fun onReachTarget() {
            launch(coroutineContext) {
                sceneContainer.changeTo {
                    Winningscreen()
                }
            }
        }
        /**
         * Moves a sprite across the screen by a specified step size along the x and y axes.
         *
         * @param sprite The sprite object to be moved.
         * @param limitX The x-coordinate at which the sprite should stop moving.
         * @param xStep The amount to increment the sprite's x-coordinate in each step.
         * @param yStep The amount to decrement the sprite's y-coordinate in each step.
         *
         * This function continuously moves the sprite by adding `xStep` to its x-coordinate
         * and subtracting `yStep` from its y-coordinate until the sprite's x-coordinate
         * reaches the `limitX` value.
         * When the sprite reaches the limit, the [onReachTarget()]
         * function is called, which is responsible for handling the winning conditions.
         */
        fun moveSprite(sprite: Sprite, limitX: Int, xStep:  Double, yStep: Double) {
            if (sprite.x.toInt() != limitX) {
                sprite.x += xStep
                sprite.y -= yStep
            }
            else {
                onReachTarget()
            }
        }
        fun moveDog() {
            moveSprite(bark, 640, 1.0, 0.5)
            bark.playAnimationLooped()
        }
        fun moveCat() {
            moveSprite(clicky, 850, 20.0, 8.0)
            mutableClickAmount += 1
            println(mutableScoreAmount)
            score += mutableScoreAmount
            scoreDisplay.text = "Score: $score Hi: $hiScore"
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
            }
        }
        moveButton.onClick {
            moveCat()
            moveDog()
            mutableCatHasMoved = true
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
                245, 270 -> clicky.y = 200.0
                295 -> clicky.y = 171.0
                345 -> clicky.y = 152.0
                320 -> clicky.y = 159.0
                445 -> clicky.y = 110.0
                495 -> clicky.y = 75.0
                520 -> clicky.y = 63.0
                545 -> clicky.y = 50.0
                570 -> clicky.y = 40.0
                395 -> clicky.y = 130.0
                420 -> clicky.y = 125.0
                470 -> clicky.y = 100.0
                595 -> clicky.y = 25.0
            }
            when(bark.x.toInt()) {
                in 42..220 -> bark.y = 215.0
                538 -> {
                    bark.y = 62.0
                }
            }
        }
    }
}
