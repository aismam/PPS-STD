package Model.Enemy

import Model.Grid.GridController
import Model.Grid.Tiles.{Tile, TileType, TileTypes}
import Utility.Logger.LogHelper

/**
 * A trait that defines the enemy and his behaviour.
 */
sealed trait Enemy {
  def update(delta: Double): Unit

  /**
   * Get the exact x coordinate of the enemy on the map.
   *
   * @return the x coordinate
   */
  def getX: Double

  /**
   * Get the exact y coordinate of the enemy on the map.
   *
   * @return the y coordinate
   */
  def getY: Double

  /**
   * Method to get enemy type.
   *
   * @return EnemyType
   */
  def getType: EnemyType

  /**
   * Spawn enemy.
   */
  def spawn(): Unit

  /**
   * Move all enemies on the next available tile.
   *
   * @param delta value
   */
  def move(delta: Double): Unit

  /**
   * The tile that the enemy is currently standing.
   *
   * @return the tile that correspond to the enemy position
   */
  def enemyCurrentPosition(): Tile

  /**
   * Deal damage to enemy.
   *
   * @param damage amount
   */
  def takeDamage(damage: Int): Unit

  /**
   * Check if enemy is alive.
   *
   * @return a Boolean corresponding to the result
   */
  def isAlive: Boolean

  /**
   * Eliminate enemy.
   */
  def death(): Unit

  /**
   * Eliminate enemy completely. Can be used when the enemy reaches the end.
   */
  def destroy(): Unit

}

object Enemy {

  sealed private class EnemyImpl(enemyType: EnemyType, gridController: GridController) extends Enemy with LogHelper {

    private var actualTile: Tile = findFirstTile(gridController)
    private var dirMulti: (Int, Int) = (0, 0)
    private var health: Int = enemyType.health
    private val speed: Int = enemyType.speed
    private var alive: Boolean = false
    private var x = actualTile.x.toDouble
    private var y = actualTile.y.toDouble
    private var dir_check: Boolean = false
    private var dir_val_check = 0

    def findFirstTile(gridController: GridController): Tile = {
      gridController.tileStartOrEnd(TileTypes.StartTile) match {
        case Some(tile) => tile
        case None => Tile(0, 0, TileType(TileTypes.StartTile))
      }
    }

    override def update(delta: Double): Unit = {
      this.death()
      this.move(delta)
    }

    override def getX: Double = {
      this.x
    }

    override def getY: Double = {
      this.y
    }

    override def getType: EnemyType = {
      this.enemyType
    }

    override def spawn(): Unit = {
      this.alive = true
    }

    override def enemyCurrentPosition(): Tile = {
      gridController.tile(actualTile.xPlace, actualTile.yPlace)
    }

    override def takeDamage(damage: Int): Unit = {
      this.health -= damage
      if (this.health <= 0) {
        this.death()
      }
    }

    override def isAlive: Boolean = {
      alive
    }

    override def death(): Unit = {
      if (this.health <= 0) {
        this.alive = false
      }
    }

    override def destroy(): Unit = {
      this.health = -1
      this.death()
    }

    override def move(delta: Double): Unit = {
      val t = this.actualTile
      var up = gridController.tile(0, 0)
      var down = gridController.tile(0, 0)
      var right = gridController.tile(0, 0)
      var left = gridController.tile(0, 0)
      if (t.yPlace != 0) {
        up = gridController.tile(t.xPlace, t.yPlace - 1)
      }
      else {
        up = gridController.tile(t.xPlace, t.yPlace)
      }

      if (t.yPlace != 19) {
        down = gridController.tile(t.xPlace, t.yPlace + 1)
      }
      else {
        down = gridController.tile(t.xPlace, t.yPlace)
      }

      if (t.xPlace != 19) {
        right = gridController.tile(t.xPlace + 1, t.yPlace)
      }
      else {
        right = gridController.tile(t.xPlace, t.yPlace)
      }

      if (t.xPlace != 0) {
        left = gridController.tile(t.xPlace - 1, t.yPlace)
      }
      else {
        left = gridController.tile(t.xPlace, t.yPlace)
      }
      dir_val_check match {
        case 0 | 1 if (up.tType.tileType == TileTypes.EndTile || up.tType.tileType == TileTypes.Path || up.tType.tileType == t.tType.tileType) && dirMulti != (0, 1) => moveToPosition(up, "up", delta)
        case 0 | 2 if (down.tType.tileType == TileTypes.EndTile || down.tType.tileType == TileTypes.Path || down.tType.tileType == t.tType.tileType) && dirMulti != (0, -1) => moveToPosition(down, "down", delta)
        case 0 | 3 if (right.tType.tileType == TileTypes.EndTile || right.tType.tileType == TileTypes.Path || right.tType.tileType == t.tType.tileType) && dirMulti != (-1, 0) => moveToPosition(right, "right", delta)
        case 0 | 4 if (left.tType.tileType == TileTypes.EndTile || left.tType.tileType == TileTypes.Path || left.tType.tileType == t.tType.tileType) && dirMulti != (1, 0) => moveToPosition(left, "left", delta)
      }
    }

    def moveToPosition(tile: Tile, direction: String, delta: Double): Unit = {
      if (!this.dir_check) {
        direction match {
          case "up" => dirMulti = (0, -1)
          case "down" => dirMulti = (0, 1)
          case "right" => dirMulti = (1, 0)
          case "left" => dirMulti = (-1, 0)
        }
        this.dir_check = true
      }
      if (x > tile.x - 10 && x < tile.x + 10 && y > tile.y - 10 && y < tile.y + 10) {
        x = tile.x
        y = tile.y
        this.actualTile = gridController.tile(tile.xPlace, tile.yPlace)
        this.dir_check = false
        this.dir_val_check = 0
      }
      else {
        x += delta * speed * dirMulti._1
        y += delta * speed * dirMulti._2
        direction match {
          case "up" => this.dir_val_check = 1
          case "down" => this.dir_val_check = 2
          case "right" => this.dir_val_check = 3
          case "left" => this.dir_val_check = 4
        }
      }
    }
  }

  def apply(enemyType: EnemyType, gridController: GridController): Enemy = {
    val enemyImpl: Enemy = new EnemyImpl(enemyType, gridController)
    enemyImpl
  }
}
