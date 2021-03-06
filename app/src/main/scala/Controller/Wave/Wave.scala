package Controller.Wave

import Controller.GameController
import Model.Enemy.{Easy, Enemy, Hard, Medium}
import Model.Grid.GridController

import scala.collection.mutable.ListBuffer

/**
 * A class that defines the wave and its behaviour.
 */
class Wave(nWave: Int, gameController: GameController) {

  private val listHelper = new ListBuffer[Enemy]
  private val enemyList = populate(this.getWave, gameController.gridController)
  private var tick: Int = 0

  def update(): Unit = {
    if (tick >= 100) {
      this.spawn()
      tick = 0
    }
    else {
      tick += 1
    }
  }

  private def getWave: Int = {
    this.nWave
  }

  private def populate(waveNumber: Int, grid: GridController): ListBuffer[Enemy] = waveNumber match {
    case it if 0 until 5 contains it => for (_ <- 1 to waveNumber * 2) {
      listHelper += Enemy(Easy, grid)
    }
      listHelper
    case it if 5 until 10 contains it => for (_ <- 1 to waveNumber) {
      listHelper += Enemy(Medium, grid)
    }
      listHelper
    case _ => for (_ <- 1 to waveNumber) {
      listHelper += Enemy(Hard, grid)
    }
      listHelper
  }

  private def spawn(): Unit = {
    if (this.hasEnemies) {
      enemyList.head.spawn()
      gameController += enemyList.remove(0)
    }
  }

  def nextWave(): Wave = {
    Wave(this.getWave + 1, gameController)
  }

  def hasEnemies: Boolean = {
    enemyList.nonEmpty
  }

  def clearWave(): Unit = {
    enemyList.clear()
  }
}

object Wave {
  def apply(nWave: Int, gameController: GameController): Wave = {
    val wave: Wave = new Wave(nWave, gameController)
    wave
  }
}
