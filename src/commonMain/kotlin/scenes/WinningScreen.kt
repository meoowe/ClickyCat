package scenes

import hiScore
import korlibs.event.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import kotlinx.coroutines.*
import score

class Winningscreen : Scene() {
    override suspend fun SContainer.sceneMain() {
        val confetti = image(__KR.KRImg.confetti1.read())
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
            val luna = sprite(__KR.KRImg.luna.read()) {
                scale = 0.125
                stage?.let {
                    alignBottomToBottomOf(it)
                }
            }
            val trophy = sprite(__KR.KRImg.trophy.read()) {
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
