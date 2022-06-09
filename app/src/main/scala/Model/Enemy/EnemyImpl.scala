package Model.Enemy

import Controller.{DrawingManager, GridController}
import Model.Grid.{Grid, Tile}


class EnemyImpl(enemytype: EnemyType, gridController: GridController) extends Enemy {

  var actualTile : Tile = gridController.gameGrid(findFirstTile(gridController.gameGrid,-1,0)(0))(findFirstTile(gridController.gameGrid,-1,0)(1))
  var dirMultp = (0, 0)
  var health: Int = enemytype.health
  val speed: Int = enemytype.speed
  var alive: Boolean = false
  var tick: Int = 0

  def findFirstTile(grid: Array[Array[Tile]], x: Int, y: Int): Array[Int] = x match {
    case -1 => findFirstTile(grid,grid(y).indexWhere(p => p.xPlace == 1),y+1)
    case _ => Array(y-1,x)
  }

  def update(delta: Double) = {
    this.death()
    if (tick >= speed) {
      this.move()
      tick = 0
    }
    else{
      tick += 1
    }
  }

  override def getType(): EnemyType = {
    this.enemytype
  }

  override def spawn(): Unit = {
    this.alive = true
  }

  //Finds the next direction.
  override def move(): Unit = {
    //All the surrounding tiles.
    val t = this.enemyCurrentPosition()
    var u = gridController.gameGrid(0)(0)
    var l = gridController.gameGrid(0)(0)
    var d = gridController.gameGrid(0)(0)
    var r = gridController.gameGrid(0)(0)
    if (t.yPlace != 0){
      u = gridController.gameGrid(t.yPlace-1)(t.xPlace)
    }
    else{
      u = gridController.gameGrid(t.yPlace)(t.xPlace)
    }

    if(t.yPlace != 19){
      d = gridController.gameGrid(t.yPlace+1)(t.xPlace)
    }
    else{
      d = gridController.gameGrid(t.yPlace)(t.xPlace)
    }

    if(t.xPlace != 14){
      r = gridController.gameGrid(t.yPlace)(t.xPlace+1)
    }
    else{
      r = gridController.gameGrid(t.yPlace)(t.xPlace)
    }

    if (t.xPlace != 0){
      l = gridController.gameGrid(t.yPlace)(t.xPlace-1)
    }
    else {
      l = gridController.gameGrid(t.yPlace)(t.xPlace)
    }


    //Enemy cant turn 180 degrees around so current value of dirMultp cant be opposite.
    if (u.tType.tileType == t.tType.tileType && dirMultp != (0, 1)) {
      this.actualTile = gridController.gameGrid(u.xPlace)(u.yPlace)
      dirMultp = (0, -1)
      println("upper")

    } else if (d.tType.tileType == t.tType.tileType && dirMultp != (0, -1)) {
      this.actualTile = gridController.gameGrid(d.xPlace)(d.yPlace)
      dirMultp = (0, 1)
      println("bottom")
    } else if (r.tType.tileType == t.tType.tileType && dirMultp != (-1, 0)) {
      actualTile = gridController.gameGrid(r.xPlace)(r.yPlace)
      dirMultp = (1, 0)
      println("right")
    } else if (l.tType.tileType == t.tType.tileType && dirMultp != (1, 0)) {
      this.actualTile = gridController.gameGrid(l.xPlace)(l.yPlace)
      dirMultp = (-1, 0)
      println("left")
    }


  }

  override def enemyCurrentPosition(): Tile = {
    gridController.gameGrid(actualTile.xPlace)(actualTile.yPlace)
  }

  override def takeDamage( i: Int): Unit = {
    this.health -= i
    if (this.health <= 0) {
      this.death()
    }
  }

  override def isAlive(): Boolean = {
    alive
  }

  override def death(): Unit = {
    if(this.health <= 0){
      this.alive = false
    }
  }

}
