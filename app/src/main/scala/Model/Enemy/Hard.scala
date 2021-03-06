package Model.Enemy

import Utility.Configuration.DefaultConfig
import Utility.Utils
import scalafx.scene.image.Image

object Hard extends EnemyType {

  def speed: Int = 150

  def health: Int = 150

  def damage: Int = 5

  def image: Image = Utils.getImageFromResource(DefaultConfig.HARD_ENEMY_IMAGE)

  def apply: EnemyType = Easy
}
