package View.ViewController

import Controller.GameController
import Model.Tower.TowerTypes._
import Utility.Configuration.DefaultConfig._
import Utility.Logger.LogHelper
import View.EventHandlers.GameEventHandlers
import View.ViewModel.GameViewModel
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.input.MouseEvent

/**
 * This class is the controller of the Game View Scene
 * From here a player can:
 * 1. Do all action that are related to the game, like:
 *    a. Start the wave
 *       b. place a tower in the grid
 *       2. Back to the Main Menu
 *       3. Restart the game
 *
 */
trait GameViewController extends ViewModelController {

  /**
   * This method retrieve the model of the game
   *
   * @return the model of game
   */
  def gameViewModel: GameViewModel
}

object GameViewController {

  private sealed case class GameViewControllerImpl(gameController: GameController) extends GameViewController
    with LogHelper {

    private val _gameViewModel: GameViewModel = GameViewModel()

    private val gameEventHandler: GameEventHandlers = GameEventHandlers(this, gameController)

    /**
     * This method hookup the listeners, it is called by the apply in the companion object
     * All the class that implement this trait must be instantiate only from their apply method for this reason
     */
    def hookupEvents(): Unit = {

      // bottom buttons action listeners
      _gameViewModel.buttons.foreach(button => {
        button.getId match {
          case START_WAVE_BTN_ID => button.setOnAction(gameEventHandler.startWave)
          case GO_MAIN_MENU_BTN_ID => button.setOnAction(gameEventHandler.goMainMenu(this.primaryStage))
          case RESTART_GAME_BTN_ID => button.setOnAction(gameEventHandler.restartGame(this.primaryStage))
        }
      })

      // tower toggle button action listeners
      _gameViewModel.towerToggleButtons.foreach(toggleButton => {
        toggleButton.getId match {
          case "baseTower" => toggleButton.setOnAction(gameEventHandler.selectTower(BASE_TOWER))
          case "cannonTower" => toggleButton.setOnAction(gameEventHandler.selectTower(CANNON_TOWER))
          case "flameTower" => toggleButton.setOnAction(gameEventHandler.selectTower(FLAME_TOWER))
          case _ => logger.warn(NOT_IMPLEMENTED_YET)
        }
      })

      _gameViewModel.canvas.addEventHandler(MouseEvent.MouseClicked,
        gameEventHandler.onCellClickedEventHandler)

    }

    override def gameViewModel: GameViewModel = _gameViewModel
  }

  def apply(primaryStage: PrimaryStage, gameController: GameController): GameViewController = {
    val gameViewController = GameViewControllerImpl(gameController)
    gameViewController.primaryStage = primaryStage
    gameViewController.hookupEvents()
    gameViewController
  }
}
