package Model.Grid

import Model.Grid.Tiles.{Tile, TileType, TileTypes}
import scalafx.scene.paint.Color

import scala.collection.mutable.ArrayBuffer

/**
 * Controller of the game map
 * This class handle the grid model
 */
trait GridController {

  /**
   * Method to get a specific tile by the given position (x,y)
   *
   * @param x raw of the tile in the grid
   * @param y column of the tile in the grid
   * @return the tile that correspond to the given coordinates
   */
  def tile(x: Int, y: Int): Tile

  /**
   * Method to check if a tile is a buildable tile
   * A tile is buildable when it isn't a:
   * 1. Path tile
   * 2. Start Tile
   * 3. End Tile
   *
   * @param x raw of the tile in the grid
   * @param y column of the tile in the grid
   * @return true if is buildable, false otherwise
   */
  def isTileBuildable(x: Int, y: Int): Boolean

  /**
   * Method to retrieve the drawing information about the grid
   *
   * @return a list that contain a triplet for each tile in the grid
   */
  def drawingInfo: ArrayBuffer[(Color, Int, Int)]

  /**
   * Method to retrieve an optional of the start or end tile
   *
   * @param filter can be start or end tile type
   * @return if exist, the start or end tile
   */
  def tileStartOrEnd(filter: TileTypes.TileType): Option[Tile]

  /**
   * Method to get the columns of the grid in grid model
   *
   * @return length of the matrix
   */
  def gridColumns: Int

  /**
   * Method to get the rows of the grid in grid model
   *
   * @return length of the matrix
   */
  def gridRows: Int
}

object GridController {

  sealed private case class GridControllerImpl(difficulty: Int) extends GridController {

    private val _gameMap: Grid = Grid(difficulty)

    override def tile(x: Int, y: Int): Tile = _gameMap.grid(y)(x)

    override def isTileBuildable(x: Int, y: Int): Boolean = _gameMap.grid(y)(x).tType.buildable

    override def drawingInfo: ArrayBuffer[(Color, Int, Int)] = {
      val buffer: ArrayBuffer[(Color, Int, Int)] = new ArrayBuffer()
      _gameMap.grid.foreach(_.foreach(tile => buffer.addOne(tile.getDrawingInfo)))
      buffer
    }

    override def tileStartOrEnd(filter: TileTypes.TileType): Option[Tile] = {
      filter match {
        case TileTypes.StartTile | TileTypes.EndTile =>
          _gameMap.grid.foreach(y => y.foreach(x => if (x.tType.tileType == filter) {
            return Some(Tile(x.x, x.y, TileType(filter)))
          }))
          None
        case _ => None
      }
    }

    override def gridColumns: Int = _gameMap.grid.length

    override def gridRows: Int = _gameMap.grid(0).length
  }

  def apply(difficulty: Int): GridController = GridControllerImpl(difficulty)
}

