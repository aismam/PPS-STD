package View.ViewModel

import Utility.Configuration.DefaultConfig._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

/**
 * Model of the main menu scene
 * It contains only scene view definitions
 */
trait MainMenuViewModel extends ApplicationViewModel {

  /**
   *
   * @return the option box
   */
  def options: VBox

  /**
   *
   * @return a list of all buttons
   */
  def buttons: List[Button]

  /**
   *
   * @return the difficulty combo box
   */
  def DifficultyComboBox: ComboBox[String]

  /**
   *
   * @return the text field where the player name is written
   */
  def playerNameTextField: TextField

  /**
   *
   * @return a text field where it can be written the custom map path
   */
  def uploadedMapPathTextField: TextField
}

object MainMenuViewModel {

  private sealed case class MainMenuViewModelImpl() extends MainMenuViewModel {

    private val _startGameButton: Button = new Button {
      prefWidth = 150
      prefHeight = 50
      text = START_GAME_BTN
      id = START_GAME_BTN_ID
    }

    private val _difficultyComboBox = new ComboBox[String] {
      prefWidth = 150
      prefHeight = 50
      items = ObservableBuffer(DIFFICULTY_COMBO_BOX_EASY, DIFFICULTY_COMBO_BOX_NORMAL, DIFFICULTY_COMBO_BOX_HARD)
      id = DIFFICULTY_COMBO_BOX_ID
    }

    private val _exitGameButton: Button = new Button {
      prefWidth = 150
      prefHeight = 50
      text = EXIT_GAME_BTN
      id = EXIT_GAME_BTN_ID
    }

    private val _playerNameTextField: TextField = new TextField() {
      minWidth = 300
      prefHeight = 50
      promptText = "Player name"
    }

    private val _uploadedMapPathTextField: TextField = new TextField() {
      minWidth = 300
      prefHeight = 50
      promptText = UPLOAD_MAP_TEXT_BOX
    }

    private val _addMapButton: Button = new Button {
      prefWidth = 150
      prefHeight = 50
      text = ADD_MAP_BTN
      id = ADD_MAP_BTN_ID
    }

    private val _infoBox = new HBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
        _difficultyComboBox,
        _playerNameTextField
      )
    }

    private val _customMapBox = new HBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
        _addMapButton,
        _uploadedMapPathTextField
      )
    }

    private val _infoCustomBox = new VBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
        _infoBox,
        _customMapBox
      )
    }

    private val _optionsVBox = new VBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
        _startGameButton,
        _exitGameButton
      )
    }

    private val _menuScene: Scene = new Scene {
      root = new BorderPane {
        padding = Insets(300, 200, 100, 200)
        prefWidth = GAME_WINDOW_WIDTH
        prefHeight = GAME_WINDOW_HEIGHT
        top = _infoCustomBox
        center = _optionsVBox

      }
    }

    override def options: VBox = _optionsVBox

    override def scene: Scene = _menuScene

    override def buttons: List[Button] = List(_startGameButton, _addMapButton, _exitGameButton)

    override def DifficultyComboBox: ComboBox[String] = _difficultyComboBox

    override def playerNameTextField: TextField = _playerNameTextField

    override def uploadedMapPathTextField: TextField = _uploadedMapPathTextField
  }

  def apply(): MainMenuViewModel = MainMenuViewModelImpl()
}
