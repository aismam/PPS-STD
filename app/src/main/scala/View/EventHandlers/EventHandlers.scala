package View.EventHandlers

import Logger.LogHelper
import View.ViewController.GameViewController
import scalafx.stage.Stage

/**
 * This interface must be implemented from all event handler class
 * It contain all generic private and public methods that is used by classes that extend this interface
 */
trait EventHandlers extends LogHelper{

  /**
   * This method is the mechanism that permit to change scene
   * @param stage current stage
   * @param gameViewController to get the scene where we will go
   * @param playerName log print
   * @param difficulty log print
   */
  protected def setScene(stage: Stage, gameViewController: GameViewController, playerName: String, difficulty: Int): Unit = {
    stage.setScene(gameViewController.gameViewModel.gameScene())
    logger.info("Initialize game: \n Player name = {} \n Difficult choice = {}", playerName, difficulty)
  }

}
