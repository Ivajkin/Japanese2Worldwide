
// Сообщение, сейчас используется для вывода сообщения
// об окончании игры.
CircleButton message = null;
// Кнопка по окончании уровня для перехода к следующему.
ImageButton nextLevelBtn = null;
// Кнопка по окончании уровня для перехода в меню.
ImageButton backToMenuBtn = null;

boolean isEndOfGameReached() {
  return (message != null);
}

void SetupEndOfGameMenu(int gamestatsGoodNum, int gamestatsFailedNum) {
  String kGood, kBad;
  if(gamestatsGoodNum != 0)
    kBad = "" + (float)gamestatsFailedNum/gamestatsGoodNum;
  else
    kBad = "ужасно!";
  if(gamestatsFailedNum != 0)
    kGood = "" + (float)gamestatsGoodNum/gamestatsFailedNum;
  else
    kGood = "великолепно!";
  message = new CircleButton(width/2,height/2,200,color(100,200,150),color(200,100,26),
  "Хорошо: " + gamestatsGoodNum + "\nПлохо: " + gamestatsFailedNum +
    "\nКоэффициент хорошести: " + kGood + "\nКоэффициент плохости: " + kBad);
  printlog("Создаю кнопку для перехода к следующему уровню.");
  nextLevelBtn = new ImageButton(width/2+135,height/2+120,"Menu/NextLevel.png");
  printlog("Создаю кнопку для выхода в меню.");
  backToMenuBtn = new ImageButton(width/2,height/2+120,"Menu/BackToMenu.png");
}

void CloseEndOfGameMenu() {
  
  message.deadElement = true;
  nextLevelBtn.deadElement = true;
  backToMenuBtn.deadElement = true;
  
  message = null;
  nextLevelBtn = null;
  backToMenuBtn = null;
}

void UpdateEndOfGameMenu() {
  message.display();
  message.update();
  nextLevelBtn.display();
  nextLevelBtn.update();
  backToMenuBtn.display();
  backToMenuBtn.update();
  if(nextLevelBtn.pressed()) {
    for(int i = 0; i<modes.length-1; i++) {
      if(modes[i].equals(currentMode)) {
        CloseEndOfGameMenu();
        currentMode = modes[i+1];
        gameSetup();
        // Иначе кнопку backToMenuBtn.pressed() попытается обработать.
        return;
      }
    }
  }
  if(backToMenuBtn.pressed()) {
    CloseEndOfGameMenu();
    menuSetup();
    return;
  }
}

