package Model.Tower

import Model.Tower.TowerTypes.FLAME_TOWER
import Utility.Configuration.DefaultConfig

/**
 * This type of tower fires flames.
 */
private class FlameTower extends CircularRadiusTower {

  override val name: String = DefaultConfig.FLAME_TOWER_NAME
  override val desc: String = DefaultConfig.FLAME_TOWER_DESC
  override val towerGraphic: String = DefaultConfig.FLAME_TOWER_IMAGE

  override val firingSpeed: Int = DefaultConfig.FLAME_TOWER_FIRING_SPEED
  override val price: Int = DefaultConfig.FLAME_TOWER_PRICE
  override val damage: Int = DefaultConfig.FLAME_TOWER_DAMAGE
  override val rangeInTiles: Int = DefaultConfig.FLAME_TOWER_RANGE

  override def towerType: TowerTypes.TowerType = FLAME_TOWER
}

object FlameTower {
  def apply(): TowerType = {
    val flameTower: FlameTower = new FlameTower()
    flameTower
  }
}