// Массив всех карт на игровом столе.
Gamecard[] gamecardgrid;

// Нужно ли в следующем кадре перерисовывать
// весь экран с задним фоном и игровым полем, без эффектов
boolean isFullScreenRedrawNeeded = true;
// Сохранённое для быстрой отрисовки игровое поле и задний фон - объединённые.
PImage gameFullScreenCashed = null;

// Время ожидания перед подсказкой
final float helpWaitTime = 8;
// Текущий счётчик времени бездействия игрока, по достижении
// helpWaitTime появляется подсказка (если выделен символ,
// над вторым появляется выделение, иначе над случайным и
// его вторым появляется выделение)
// Сбрасывается при нажатии клавиши мыши.
float currentHelpCountdown = 0;
// Можно показывать помощь или нет.
boolean isHelpMustBeShown() {
  return (currentHelpCountdown>=helpWaitTime);
}
// Карты, которые показывают на подсказку.
int helpCardShow_1 = -1;
int helpCardShow_2 = -1;
void ResetHelpCountdown() {
  currentHelpCountdown = 0;
  helpCardShow_1 = -1;
  helpCardShow_2 = -1;
}
void UpdateHelp() {
  currentHelpCountdown += frametime;
  if(isHelpMustBeShown() && gamecardCurrentNum>=2) {
    if(helpCardShow_2 == -1) {
      printlog("Start showing help...");
      if(helpCardShow_1 == -1) {
        printlog("Selecting first card for help.");
        if(gamecardselected==null) {
          helpCardShow_1 = round(random(gamecardNumX*gamecardNumY+1)*1.9812)%(gamecardNumX*gamecardNumY);
          while(gamecardgrid[helpCardShow_1]==null || gamecardgrid[helpCardShow_1].deadcard) {
            helpCardShow_1 = (helpCardShow_1+1)%(gamecardNumX*gamecardNumY);
          }
        } else
          helpCardShow_1 = gamecardselected.index;
      }
      printlog("Selecting second card for help.");
      for(int i = 1; i<=gamecardNumX*gamecardNumY; ++i) {
        helpCardShow_2 = (helpCardShow_1+i)%(gamecardNumX*gamecardNumY);
        if(gamecardgrid[helpCardShow_2]!=null
          && !gamecardgrid[helpCardShow_2].deadcard
          && gamecardgrid[helpCardShow_1].type==gamecardgrid[helpCardShow_2].type
          && gamecardgrid[helpCardShow_1].face != gamecardgrid[helpCardShow_2].face) {
          break;
        }
      }
      
      printlog("End showing help...");
    }
    // отрисовываем подсказку.
    gamecardgrid[helpCardShow_1].displayFrame();
    gamecardgrid[helpCardShow_2].displayFrame();
  } 
}

// Текст на экране, улетает вверх.
class FloatingText extends IDrawable
{
  FloatingText(float x, float y, String text2draw, color col) {
    this.x = x;
    this.y = y;
    this.text2draw = text2draw;
    this.col = col;
  }
  float x, y;
  float vx = 0, vy = 0;
  String text2draw;
  color col = color(40,223,15, 255);
  float opacity = 255;
  void display() {
    fill(color(0,0,0,opacity));
    text(text2draw, x+0.6, y+0.6);
    fill(col);
    text(text2draw, x, y);
    vy -= frametime*0.3;
    vx += frametime*0.09;
    x += vx;
    y += vy;
    opacity += vy*1;
    col = color(red(col),green(col),blue(col),opacity);
    if(opacity<=0.01)
      deadElement = true;
  }
}
color gamegreencombotextcolor = color(40,223,15, 255);
color gameyellowcombotextcolor = color(220,223,15, 255);
color gameredcombotextcolor = color(240,53,15, 255);
FloatingText[] gamecombofloatingtext = new FloatingText[256];
int gamecombofloatingtextcount = 0;
void addFloatingText(FloatingText text2add) {
  for(int i = 0; i<gamecombofloatingtextcount; i++) {
    if(gamecombofloatingtext[i] == null) {
      gamecombofloatingtext[i] = text2add;
      return;
    }
  }
  gamecombofloatingtext[gamecombofloatingtextcount++] = text2add;
}


int gamestatsFailedNum = 0;
int gamestatsGoodNum = 0;
// Звуки внутриигровые.
// Звук когда пытаешся сложить неверную комбинацию.
AudioPlayer soundWrongChoose;
// Звук когда складываешь верную комбинацию.
AudioPlayer soundGoodChoose;
// Звук когда время вышло (ещё не используется на 30.04.2011).
AudioPlayer soundTimesup;
// Звук проигрываемый во время победы.
AudioPlayer soundVictory;
// Мышь зажата.
boolean mouseLocked = false;
// Размер игрового поля в ячейках по горизонтали.
int gamecardNumX = 16;//16
// Размер игрового поля в ячейках по вертикали.
int gamecardNumY = 16;//16
// Количество ячеек в игровом поле.
int gamecardCurrentNum = gamecardNumX*gamecardNumY;
// Возможность заполнения поля (если нельзя в точку загружать карту она как будто уже поднята).
// Загружается из файла "GameFieldConfig.gamefield".
boolean[] canBeFilled = new boolean[gamecardNumX*gamecardNumY];
int canBeFilledCellCount = gamecardNumX*gamecardNumY;

// Если собрать несколько подряд получаем комбо бонус.
// +1 +2 +3 и т.д.
final float gamecombotime = 3.0; // Если больше проходит - комбо-цепочка обрывается.
int gamecurrentcombo = 0;
float gamelastcomboframetime = 0;
// Комбо-бонус.
void gamecomboOnGood() {
  if(gametime - gamelastcomboframetime < gamecombotime) {
    gamestatsGoodNum += gamecurrentcombo;
    if(gamecurrentcombo != 0) {
      color txtcol = gameredcombotextcolor;
      switch(gamecurrentcombo) {
        case 1:
          txtcol = gamegreencombotextcolor;
          break;
        case 2:
          txtcol = gameyellowcombotextcolor;
          break;
        default:
          txtcol = gameredcombotextcolor;
          break;
      }addFloatingText(new FloatingText(mouseX,mouseY,"+"+gamecurrentcombo+" combo!",txtcol));
    }
    gamecurrentcombo++;
    gamecurrentcombo = min(gamecurrentcombo,3);
  } else {
    // Комбо-цепочка окончена.
    gamecurrentcombo = 1;
  }
  gamelastcomboframetime = gametime;
}
void gamecomboOnBad() {
  // Комбо-цепочка окончена.
  gamecurrentcombo = 1;
}
// Выделеная карта.
Gamecard gamecardselected = null;
class GamecardType {
  GamecardType(String _name) {
    name = _name;
    String fileToLoad = currentMode+"/"+name+".png";
    img = loadImage(fileToLoad);
    if(null == img) {
      String warningmessage = "Не загрузилось\"" + fileToLoad + "\"!";
      printlog(warningmessage);
      fileToLoad = "HiraganaCommon/"+name+".png";
      img = loadImage(fileToLoad);
      if(null == img) {
        String errormessage = "Не загрузилось\"" + fileToLoad + "\"!!";
        printlog(errormessage);
      }
    }
  }
  String name;
  PImage img;
}
PImage gamecardselectionframe;
PImage gamecardframe;

GamecardType[] gamecardtypes;
void gameSetup() {
   printlog("Called gameSetup()");
   
  gamestatsFailedNum = 0;
  gamestatsGoodNum = 0;
  gametime = 0;
  // Заполненость игрового поля.
  String[] gameField = loadStrings(currentMode+"/GameFieldConfig.gamefield");
  if(gameField != null) {
    canBeFilledCellCount = 0;
    for(int i = 0; i<gamecardNumX; ++i) {
      for(int j = 0; j<gamecardNumY; ++j) {
        String gameFieldLine = gameField[i];
        boolean exist = gameFieldLine.charAt(j)!=' ';
        canBeFilled[i+gamecardNumX*j] = exist;
        if(exist)
          canBeFilledCellCount++;
      }
    }
    if(canBeFilledCellCount%2!=0) {
      printlog("Ошибка! На игровом поле нечётное число карт!!!");
    }
  } else {
    for(int i = 0; i<gamecardNumX; ++i) {
      for(int j = 0; j<gamecardNumY; ++j) {
        canBeFilled[i+gamecardNumX*j] = true;
      }
    }
    canBeFilledCellCount = gamecardNumX*gamecardNumY;
  }
  gamecardCurrentNum = canBeFilledCellCount;
  //TODO
  MusicBox.setCurrenMode(currentMode);
  soundWrongChoose = MusicBox.loadSound("wrong.wav");//minim.loadFile(currentMode+"/wrong.wav");
  soundGoodChoose = MusicBox.loadSound("good.wav");//minim.loadFile(currentMode+"/good.wav");
  //TODO
  //soundTimesup = MusicBox.loadSound("timesup.wav");//minim.loadFile(currentMode+"/timesup.wav");
  soundVictory = MusicBox.loadSound("victory.wav");//minim.loadFile(currentMode+"/victory.wav");
  back = loadImage(currentMode+"/background.jpg");
  gamecardframe = loadImage(currentMode+"/frame.png");
  gamecardselectionframe = loadImage(currentMode+"/selectionframe.png");
  String[] lines = loadStrings(currentMode+"/"+currentMode+".txt");
  gamecardtypes = new GamecardType[int(lines[0])];
  for(int i = 1; i<lines.length; i++) {
    gamecardtypes[i-1] = new GamecardType(lines[i]);
  }
  gamecardgrid = new Gamecard[gamecardNumX*gamecardNumY];
  for(int i = 0; i<gamecardNumX*gamecardNumY; i++) {
    gamecardgrid[i] = null;
  }
  for(int i = 0; i<canBeFilledCellCount/2; i++) {
    printlog("Gamecard field loading: "+i);
    int cell = (int)random(gamecardNumX*gamecardNumY);
    while(gamecardgrid[cell]!=null || !canBeFilled[cell]) {
      cell = (cell+1)%(gamecardNumX*gamecardNumY);
    }
    GamecardType cardtype = gamecardtypes[(int)random(gamecardtypes.length)];
    {
      int x = cell%gamecardNumX;
      int y = (cell-x)/gamecardNumX;
      gamecardgrid[cell] = new Gamecard(cardtype, x, y, true, cell);
      printlog("First gamecard selected: " + cardtype.name + " {" + x + "," + y + "}");
    }
    canBeFilled[cell] = false;
    // Вторая карта.
    cell = (int)random(gamecardNumX*gamecardNumY);
    while(gamecardgrid[cell]!=null || !canBeFilled[cell]) {
      cell = (cell+1)%(gamecardNumX*gamecardNumY);
    }
    {
      int x = cell%gamecardNumX;
      int y = (cell-x)/gamecardNumX;
      gamecardgrid[cell] = new Gamecard(cardtype, x, y, false, cell);
      printlog("Second gamecard selected: " + cardtype.name + " {" + x + "," + y + "}" + "\n");
    }
  }
  MusicBox.startMainTheme();
}
// Время прошедшее с начала игры.
float gametime = 0;
float backgroundPosX = 0, backgroundPosY = 0;
void game() {
  gametime += frametime;
  // Обновление
  final int gamecardNum = gamecardNumX*gamecardNumY; 
  for(int i = 0; i<gamecardNum; i++) {
    if(gamecardgrid[i]!=null && !gamecardgrid[i].deadcard) {
      gamecardgrid[i].update();
    }
  }
  boolean isBackgroundMoving = (playareaWidth!=back.width||playareaHeight!=back.height);
  // Отрисовка
  if(isFullScreenRedrawNeeded || isBackgroundMoving) {
    isFullScreenRedrawNeeded = false;
    if(isBackgroundMoving) {
      // maxl=(w-back.w)
      float newBackgroundPosX = mouseX*(width-back.width)/width;
      backgroundPosX = backgroundPosX*0.8 + newBackgroundPosX*0.2;
      float newBackgroundPosY = mouseY*(height-back.height)/height;
      backgroundPosY = backgroundPosY*0.8 + newBackgroundPosY*0.2;
      image(back,backgroundPosX,backgroundPosY);
    } else {
      image(back,0,0);
    }
    for(int i = 0; i<gamecardNum; i++) {
      if(gamecardgrid[i]!=null && !gamecardgrid[i].deadcard) {
        gamecardgrid[i].display();
      }
    }
    if(!isBackgroundMoving)
      gameFullScreenCashed = get();
  } else {
    image(gameFullScreenCashed,0,0);
  }
  /* 
  {
    if(isGameFieldRedrawNeeded) {
      isGameFieldRedrawNeeded = false;
      PImage tmp = get();
      background(0,0,0,0);
      for(int i = 0; i<gamecardNumX*gamecardNumY; i++) {
        if(gamecardgrid[i]!=null && !gamecardgrid[i].deadcard) {
          gamecardgrid[i].display();
        }
      }
      gameFieldCashed = get();
      image(tmp,0,0);
    }
    image(gameFieldCashed,0,0);
  }*/
  if(gamecardselected != null) {
    gamecardselected.displayFrame();
  }
  // wrong
  fill(240,53,15, 230);
  text(gamestatsFailedNum, 5, 20);
  fill(40,223,15, 230);
  text(gamestatsGoodNum, 5, 40);

  //drawFloatingText();
  displayDrawables();
  UpdateHelp();
  
  if(isEndOfGameReached()) {
    UpdateEndOfGameMenu();
  }
}

