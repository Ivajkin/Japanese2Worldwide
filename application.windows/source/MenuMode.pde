
import java.util.List;

class ModeSelect {
  ModeSelect(String[] modes) {
    modeSelectors = new Button[modeNum];
    starsForModeSelectors = new Button[modeNum];
    for(int i = 0; i<modeNum; i++) {
      int ipos = i%5;
      int jpos = (i-ipos)/5;
      boolean exceedScreen = (jpos >= 3);
      final int xrange = 120;
      final int yrange = 135;
      int x = (768-xrange*4)/2 + ipos*xrange;//(768-675)/2
      int y = 200 + jpos*yrange;
      if(exceedScreen) {
        x += playareaHeight;
        y -= 3*yrange;
      }
      modeSelectors[i] = new CircleButton(
      //i*width/modeNum + 1*width/modeNum/2,
      // height/2,
      // по кругу:
      //(int)(cos(i*2*3.1415/modeSelectors.length)*250 + width/2),
      //(int)(sin(i*2*3.1415/modeSelectors.length)*250 + height/2),
      x,
      y,
      100,
      color(23,198,44),
      color(243,178,32),
      modes[i]);
      // Уровень уже был пройден.
      boolean isLevelCleared = false;
      String[] levelData = loadStrings(userLevelDataFileName+modes[i]);
      if(levelData != null && levelData[0].equals(levelClearedInfoString))
        isLevelCleared = true;
      if(isLevelCleared)
        starsForModeSelectors[i] = new ImageButton(x,y+50,"star.png");
      else  
        starsForModeSelectors[i] = null;
    }
    // Кнопки влево и вправо по уровням.
    levelSelectLeftBtn = new ImageButton(  50,  playareaHeight/2,"Menu/LevelSelectLeft.png");
    levelSelectRightBtn = new ImageButton(  playareaWidth-50,  playareaHeight/2,"Menu/LevelSelectRight.png");
  }
  private Button[] modeSelectors = null;
  private Button[] starsForModeSelectors = null;
  // Кнопки влево и вправо по уровням.
  private Button levelSelectLeftBtn = null;
  private Button levelSelectRightBtn = null;
  //Положение уровней:
  //   o o o o o
  // < o o o o o >
  //   o o o o o
  // начиная с нулевого
  private int index = 0;
  // Можно ли двинуть уровни влево.
  private boolean canBeMovedLeft() {
    return (index > 0);
  }
  // Можно ли двинуть уровни вправо.
  private boolean canBeMovedRight() {
    return (index < modeSelectors.length/15);
  }
  // Индекс влево при нажатии влево.
  void indexLeft() {
    if(canBeMovedLeft()) {
      index--;      
      for(int i = 0; i<modeSelectors.length; i++) {
        modeSelectors[i].x += playareaWidth;
        if(starsForModeSelectors[i] != null)
          starsForModeSelectors[i].x += playareaWidth;
      }
    }
  }
  // Индекс вправо при нажатии вправо.
  void indexRight() {
    if(canBeMovedRight()) {
      index++;
      for(int i = 0; i<modeSelectors.length; i++) {
        modeSelectors[i].x -= playareaWidth;
        if(starsForModeSelectors[i] != null)
          starsForModeSelectors[i].x -= playareaWidth;
      }
    }
  }
  // Обновляем и отрисовываем меню.
  void update() {
    for(int i = 0; i<modes.length; i++) {
      if(modeSelectors[i] != null) {
        modeSelectors[i].display();
        modeSelectors[i].update();
        if(modeSelectors[i].pressed()) {
          isMenuEnabled = false;
          deleteMenu();
          currentMode = modes[i];
          gameSetup();
          return;
        }
      }
    }    
    // Звёзды за завершение уровней обновляем и отрисовываем.
    for(int i = 0; i<starsForModeSelectors.length; i++) {
      if(starsForModeSelectors[i] != null) {
        starsForModeSelectors[i].display();
        starsForModeSelectors[i].update();
      }
    }
    if(canBeMovedLeft()) {
      levelSelectLeftBtn.update();
      levelSelectLeftBtn.display();
      if(levelSelectLeftBtn.pressed()) {
        indexLeft();
      }
    }    
    if(canBeMovedRight()) {
      levelSelectRightBtn.update();
      levelSelectRightBtn.display();
      if(levelSelectRightBtn.pressed()) {
        indexRight();
      }
    }
  }
  // Уничтожаем всё лишнее при переходе в другой режим.
  void destroy() {
    for(int i = 0; i<modeSelectors.length; i++)
      if(modeSelectors[i] != null)
        modeSelectors[i].deadElement = true;
    for(int i = 0; i<starsForModeSelectors.length; i++)
      if(starsForModeSelectors[i] != null)
        starsForModeSelectors[i].deadElement = true;
        
    //    ??????TODO помечать, что нужно исправить, а то непонятно.
    levelSelectRightBtn.deadElement = true;
    levelSelectRightBtn = null;
    levelSelectLeftBtn.deadElement = true;
    levelSelectLeftBtn = null;
  }
}
ModeSelect modeSelect = null;
//List<Button> menuBtnSet = new ArrayList<Button>();

int modeNum = 0;
String currentMode = null;
String[] modes = null;
boolean isMenuEnabled = true;
// Находится ли сейчас игра в главном меню (уровень не выберешь).
boolean isMainMenuSelected = true;
// Задний фон меню.
PImage menuBackground;
final String copyrightString = "Copyright Ivajkin Timofej © 2010-2011\nEasyLangGo v.1.006\ntimatnet@gmail.com";
final int copyrightPosX = 10, copyrightPosY = 24;
float copyrightOpacity = 255, copyrightOpacityVel = 0;
void drawCopyright() {
  fill(231,231,231,copyrightOpacity);
  copyrightOpacity += copyrightOpacityVel;
  copyrightOpacityVel -= 0.002;
  if(mouseX<200 && mouseY<100 ) {
    copyrightOpacity = 255;
  }
  text(copyrightString, copyrightPosX, copyrightPosY);
}
// Кнопки меню - выход, начать игру, настройки, написать письмо, группа во вконтакте, сайт.
//Button[] menuBtns = new Button[7];
class MainMenuButtonsT {
  private ImageButton StartGame;
  private ImageButton Quit;
  private ImageButton Configure;
  private ImageButton Email;
  private ImageButton SiteLogo;
  private ImageButton VK;
  private PImage RightPanel;
  void update() {
  }
  void destroy() {
  }
}
MainMenuButtonsT MainMenuButtons = new MainMenuButtonsT();
PFont menuFont = null;

// Настройка игрового меню.
void menuSetup() {
  menuFont = loadFont("TimesNewRomanPS-ItalicMT-18.vlw");
  textFont(menuFont);

  menuBackground = loadImage("background.jpg");
  String[] lines = loadStrings("config.txt");
  modeNum = int(lines[0]);
  modes = new String[modeNum];
  for( int i = 0; i<modeNum; i++) {
    modes[i] = lines[i+1];
  }
  modeSelect = new ModeSelect(modes);
  isMenuEnabled = true;
  // TODO Главное меню, похоже на Angry Birds
  // Затем режим турнира, показаны звёздочки за этап, коллличество очков.
  // Нужно добавить логи, запись статистики по играм, где сколько очков набирали, с пометкой даты.
  MainMenuButtons.StartGame = new ImageButton(  playareaWidth/2,    playareaHeight/2,    "Menu/StartGame.png");
  MainMenuButtons.Quit = new ImageButton(       100,                playareaHeight - 150,"Menu/Quit.png");
  MainMenuButtons.Configure = new ImageButton(  100,  playareaHeight - 250,"Menu/Configure.png");
  MainMenuButtons.RightPanel = loadImage("Menu/RightPanel.png");
  MainMenuButtons.VK = new ImageButton(         playareaWidth-80,  playareaHeight - 200 -1*75,"Menu/VK.png");
  MainMenuButtons.SiteLogo = new ImageButton(   playareaWidth-80,  playareaHeight - 200 -2*75,"Menu/SiteLogo.png");
  MainMenuButtons.Email = new ImageButton(      playareaWidth-80,  playareaHeight - 200 -3*75,"Menu/Email.png");

  MusicBox.playIntro();
}


void deleteMenu() {
  /*while(menuBtnSet.size() != 0) {
   Button rem = menuBtnSet.remove(0);
   rem.deadElement = true;
   }*/
  MainMenuButtons.StartGame.deadElement = true;
  MainMenuButtons.Quit.deadElement = true;
  MainMenuButtons.Configure.deadElement = true;
  MainMenuButtons.VK.deadElement = true;
  MainMenuButtons.SiteLogo.deadElement = true;
  MainMenuButtons.Email.deadElement = true;

  modeSelect.destroy();
}

// Функция, вызываемая в каждом кадре при работе меню.
void menu() {
  if(!isMenuEnabled)
    return;
    
  textFont(menuFont);  
  //background(234,67,176);
  image(menuBackground,0,0);
  drawCopyright();

  if(!isMainMenuSelected) {
    modeSelect.update();
  } 
  else {
    MainMenuButtons.StartGame.display();
    MainMenuButtons.StartGame.update();

    MainMenuButtons.Quit.display();
    MainMenuButtons.Quit.update();

    MainMenuButtons.Configure.display();
    MainMenuButtons.Configure.update();

    image(MainMenuButtons.RightPanel,MainMenuButtons.SiteLogo.getX()-MainMenuButtons.RightPanel.width/2,MainMenuButtons.SiteLogo.getY()-MainMenuButtons.RightPanel.height/2);

    MainMenuButtons.Email.display();
    MainMenuButtons.Email.update();

    MainMenuButtons.SiteLogo.display();
    MainMenuButtons.SiteLogo.update();

    MainMenuButtons.VK.display();
    MainMenuButtons.VK.update();
    if(MainMenuButtons.VK.pressed()) {
      link("http://vkontakte.ru/club26013748","_new");
      return;
    }
    else if(MainMenuButtons.Email.pressed()) {
      link("mailto:timatnet@gmail.com","_new");
      return;
    }
    else if(MainMenuButtons.Quit.pressed()) {
      exit();
      return;
    }
    else if(MainMenuButtons.SiteLogo.pressed()) {
      link(homeSiteUrl,"_new");
      return;
    }
    else if(MainMenuButtons.StartGame.pressed()) {
      isMainMenuSelected = false;
      return;
    }
    else if(MainMenuButtons.Configure.pressed()) {
      MusicBox.setMusicEnabled();
      return;
    }
  }
}

