package scenes

import __KR
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
            scale(2)
        }
        val clickyYPositions = mapOf(
            245 to 200.0,
            270 to 200.0,
            295 to 171.0,
            345 to 152.0,
            320 to 159.0,
            445 to 110.0,
            495 to 75.0,
            520 to 63.0,
            545 to 50.0,
            570 to 40.0,
            395 to 130.0,
            420 to 125.0,
            470 to 100.0,
            595 to 25.0
        )
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
            moveButton = moveButton,
            clickyYPositions = clickyYPositions
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
        moveButton: UIButton,
        clickyYPositions: Map<Int, Double>
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
        fun launchCoroutine(block: suspend CoroutineScope.() -> Unit) {
            launch(coroutineContext) {
                block()
            }
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
            launchCoroutine {
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
        fun changeScore(){
            mutableClickAmount += 1
            println(mutableScoreAmount)
            score += mutableScoreAmount
            scoreDisplay.text = "Score: $score Hi: $hiScore"
        }

        fun handleBalloonClick() {
            mutableScoreAmount = 20
        }

        fun handleClickyClick() {
            bark.playAnimationLooped()
            mutableCatHasMoved = true
            moveSprite(clicky, 850, 20.0, 8.0)
            moveSprite(bark, 640, 1.0, 0.5)
            changeScore()
        }

        fun handleSpaceKeyPress() = launchCoroutine {
            handleClickyClick()
        }

        fun handleMoveButtonClick() {
            handleClickyClick()
        }
        balloon.interval(500.milliseconds) {
            balloon.visible = !balloon.visible
            val randomPos = (50..700).random()
            balloon.positionX(randomPos)
        }
        balloon.onClick { handleBalloonClick() }
        clicky.onClick { handleClickyClick() }
        addUpdater {
            if (input.keys.justPressed(Key.SPACE)) handleSpaceKeyPress()
        }
        moveButton.onClick {handleMoveButtonClick()}
        // Make the dog move continuously
        addUpdater {
            if(mutableCatHasMoved) {
                moveSprite(bark, 640, 1.0, 0.5)
            }
        }
        bark.onCollision({it == clicky}) {
            launchCoroutine {
                sceneContainer.changeTo { LoosingScreen() }

            }
        }
        bark.interval(1.seconds) {
            launchCoroutine {
                barkSound.play()
            }
        }
        addUpdater {
            // Why did I make this catastrophe?
            clicky.y = clickyYPositions[clicky.x.toInt()] ?: clicky.y
            when(bark.x.toInt()) {
                in 42..220 -> bark.y = 215.0
                538 -> {
                    bark.y = 62.0
                }
            }
        }
    }
}


