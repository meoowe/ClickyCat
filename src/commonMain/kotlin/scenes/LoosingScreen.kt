package scenes

import korlibs.event.*
import korlibs.korge.input.*
import korlibs.korge.scene.*
import korlibs.korge.ui.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import kotlinx.coroutines.*
import score

class LoosingScreen : Scene() {
    override suspend fun SContainer.sceneMain() {

        val loosingImage = image(__KR.KRImg.lost.read()) {
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
