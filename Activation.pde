// Модуль для управлением активацией приложения,
// позволяет пользоваться приложением в демо-режиме
// ограниченный срок.

class _ActivationGlobalInfo {
  // Перевести игру в режим полной версии при введении правильного серийного ключа.
  void ActivateApplication() {
    String[] activationInfo = {
      code("activated")
    }; 
    saveStrings(activationInfoFile,activationInfo);
    demoModeEnabled = false;
    isActivationSkipped = true;
    printlog("Activating application");
  }
  // Файл в который записывается и из которого
  // извлекается информация об активации и оставшемся времени.
  final String activationInfoFile = "logs/chunck";
  // Закончился ли демо-период.
  public boolean isDemoExpired = false;
  // Максимальный срок игры в демо-режиме (в секундах).
  final float maxPlayTime = 15*60;
  // Время в течение которого по окончании срока демо-режима демонстрируется сообщение об окончании игры (в секундах).
  final float showEndOfDemoPopup = 10;
  // Активирован ли демо-режим с отсчётом времени.
  boolean demoModeEnabled = true;
  int screenSizeX, screenSizeY;
  float secondsPerFrame;
  // Времени было сыгранно в секундах.
  float timePlayedInSeconds = 0;//14*60+55;
  // Этап введения активационного ключа пропущен или завершён.
  boolean isActivationSkipped = false;
  // Кнопки для экрана активации.
  ImageButton purchaseBtn = null, acceptBtn = null, skipBtn = null;
  PImage activateScreenBackground = null;
  PImage serial_key_correct_image = null,
          serial_key_invalid_image = null;
  // Строка, которую вводят при активации.
  String currentSerialKeyString = "";
  PFont SerialKeyInputFont = null;
  // Отрисовка окна для ввода пароля для активации.
  void EnterSerialKeyFormDraw() {
    background(128,128,128);
    textFont(SerialKeyInputFont);
    fill(0);
    image(activateScreenBackground, screenSizeX/2 - activateScreenBackground.width/2, screenSizeY/2 - activateScreenBackground.height/2);
    purchaseBtn.update();
    acceptBtn.update();
    skipBtn.update();
    purchaseBtn.display();
    acceptBtn.display();
    skipBtn.display();
    text(currentSerialKeyString.substring(0,min(6,currentSerialKeyString.length())),screenSizeX/2-220,screenSizeY/2);
    if(currentSerialKeyString.length()>6) {
      text(currentSerialKeyString.substring(min(6,currentSerialKeyString.length()),min(9,currentSerialKeyString.length())),screenSizeX/2-20,screenSizeY/2);
    }
    if(currentSerialKeyString.length()>9) {
      text(currentSerialKeyString.substring(min(9,currentSerialKeyString.length()),min(maxSerialKeyLength,currentSerialKeyString.length())),screenSizeX/2+105,screenSizeY/2);
    }
    
    if(skipBtn.pressed()) {
      isActivationSkipped = true;
      DeleteActivationGUI();
      return;
    }
    if(purchaseBtn.pressed()) {
      link(purchaseUrl,"_new");
      DeleteActivationGUI();
      return;
    }
    if(acceptBtn.pressed()) {
      if(isSerialKeyCorrect()) {
        ActivateApplication();
        isActivationSkipped = true;
        DeleteActivationGUI();
        return;
      }
    }
    
    if(maxSerialKeyLength == currentSerialKeyString.length()) {
      image(
              (isSerialKeyCorrect()?serial_key_correct_image:serial_key_invalid_image),
              screenSizeX/2+170,
              screenSizeY/2+20);
    }
  }
  // Уничтожаем интерфейс для ввода серийного ключа.
  void DeleteActivationGUI() {
    acceptBtn.deadElement = true;
    skipBtn.deadElement = true;
    purchaseBtn.deadElement = true;
  }
  // Событие обрабатывает нажимаемые клавиши при вводе активационного ключа.
  void OnKeyboardKeyPressed() {
    if(!isActivationSkipped) {
      if(key >= 'a' && key <= 'z' || key >= 'A' && key <= 'Z' ) {
        currentSerialKeyString += key;
        currentSerialKeyString = currentSerialKeyString.substring(0,min(maxSerialKeyLength,currentSerialKeyString.length())).toUpperCase();
      } else if(key==BACKSPACE && currentSerialKeyString.length()>=1) {
        currentSerialKeyString = currentSerialKeyString.substring(0,currentSerialKeyString.length()-1);
      } else if(key==ENTER || key==RETURN) {
      }
    }
  }
  // Максимальная длина вводимого ключа
  final int maxSerialKeyLength = 13;
  // Верен ли введёный ключ активации.
  boolean isSerialKeyCorrect() {
    return (currentSerialKeyString.length()==maxSerialKeyLength && currentSerialKeyString.charAt(0)-currentSerialKeyString.charAt(7) == 5);
  }
  // Настройки размера экрана, кол-ва кадров в секунду и др.
  void ActivationSetup(int screenSizeX, int screenSizeY, float framerate) {
    printlog("Setup activation");
    this.screenSizeX = screenSizeX;
    this.screenSizeY = screenSizeY;
    this.secondsPerFrame = 1.0/framerate;
    String[] info = loadStrings(activationInfoFile);
    if(info!=null) {
      String activationInfo = decode(info[0]);
      if(activationInfo.equals("just_installed AXBDX")) {
        printlog("Product is just installed");
        timePlayedInSeconds = 0;
      } else if(activationInfo.equals("activated")) {
        printlog("Product is activated");
        ActivateApplication();
      } else {
        printlog("Product is used some time in trial version");
        timePlayedInSeconds = int(activationInfo);
      }
    } else {
      /* // нет файла, кто-то удалил или ещё что-то.
      timePlayedInSeconds = maxPlayTime;*/
      // Сейчас если нет файла, значит только установили.
        printlog("Product is just installed, the file \"" + activationInfoFile + "\" do not exist.");
        timePlayedInSeconds = 0;
    }
    
    if(demoModeEnabled) {
      purchaseBtn = new ImageButton(  playareaWidth/2 - 170,
                                      playareaHeight/2 + 180,
                                      "Activation/PurchaseBtn.png");
      acceptBtn = new ImageButton(  playareaWidth/2,
                                      playareaHeight/2 + 180,
                                      "Activation/AcceptBtn.png");
      skipBtn = new ImageButton(  playareaWidth/2 + 170,
                                      playareaHeight/2 + 180,
                                      "Activation/SkipBtn.png");
      activateScreenBackground = loadImage("Activation/Background.png");
      serial_key_correct_image = loadImage("Activation/serial_key_correct.png");
      serial_key_invalid_image = loadImage("Activation/serial_key_invalid.png");
      SerialKeyInputFont = loadFont("SerialKeyInputFont.vlw");
    }
  }
  // Минимальное значение прозрачности панели оставшегося времени.
  final float minElapsedTimeBarOpacity = 20;
  // Максимальное значение прозрачности панели оставшегося времени.
  final float maxElapsedTimeBarOpacity = 255;
  // Скорость затенения панели оставшегося времени.
  final float elapcedTimeBarFadeVelocity = 600*frametime;
  // Значение прозрачности панели оставшегося времени.
  float elapsedTimeBarOpacity = maxElapsedTimeBarOpacity;
  // Отрисовка панели оставшегося бесплатного времени.
  void DrawElapsedTimeBar() {
      boolean mouseInPanel = (mouseX > screenSizeX - 200 && mouseY > screenSizeY - 200);
      // Здесь панель скакала вверх при наведении мышки.
      /*final float panelStartX = (mouseInPanel ? screenSizeX - 200 : screenSizeX - 200);
      final float panelStartY = (mouseInPanel ? 20 : screenSizeY - 170);*/
      final float panelStartX = screenSizeX - 200;
      final float panelStartY = screenSizeY - 170;
      // Теперь просто плавно становится прозрачной.
      elapsedTimeBarOpacity = (mouseInPanel ?
                 max(elapsedTimeBarOpacity - elapcedTimeBarFadeVelocity,minElapsedTimeBarOpacity):
                 min(elapsedTimeBarOpacity + elapcedTimeBarFadeVelocity,maxElapsedTimeBarOpacity));
      
      stroke(255,255,255,elapsedTimeBarOpacity);
      fill(240,240,240,elapsedTimeBarOpacity);
      rect(panelStartX + 20, panelStartY + 20, 160, 20);      
      stroke(0,0,0,0);
      fill(45,65,240,elapsedTimeBarOpacity);
      float percentOfTime = 1 - timePlayedInSeconds / maxPlayTime;
      rect(panelStartX + 20, panelStartY + 20, 160 * percentOfTime, 20);
      // Если больше одной минуты - пишем кол-во минут, иначе красным кол-во секунд.
      if(maxPlayTime - timePlayedInSeconds>=60) {
        fill(0,0,0,elapsedTimeBarOpacity);
        text(new String("Осталось ") + round((maxPlayTime - timePlayedInSeconds)/60) + " минут.",panelStartX + 20+1, panelStartY + 60+1);
        fill(255,255,255,elapsedTimeBarOpacity);
        text(new String("Осталось ") + round((maxPlayTime - timePlayedInSeconds)/60) + " минут.",panelStartX + 20, panelStartY + 60);
      } else {
        fill(0,0,0,elapsedTimeBarOpacity);
        text(new String("Осталось ") + round((maxPlayTime - timePlayedInSeconds)) + " секунд!",panelStartX + 20+1, panelStartY + 60+1);
        fill(255,100,20,elapsedTimeBarOpacity);
        text(new String("Осталось ") + round((maxPlayTime - timePlayedInSeconds)) + " секунд!",panelStartX + 20, panelStartY + 60);
      }
  }
  // Отрисовываем сообщение об окончании демоверсии.
  void DrawEndOfDemoPopup() {
    /*if(endOfDemoPopupImage == null) {
      endOfDemoPopupImage = loadImage("Activation/endOfDemoPopupImage.png");
    }
    fill(255,0,0);
    rect(0,0,screenSizeX,screenSizeY);
    image(endOfDemoPopupImage, screenSizeX/2 - endOfDemoPopupImage.width/2, screenSizeY/2 - endOfDemoPopupImage.height/2);*/
    EnterSerialKeyFormDraw();
  }
  // Режим в котором время уходит со 100x скоростью. Режим для тестирования.
  boolean _debugSpeedupMode = false;
  void ActivationUpdate() {
    if(demoModeEnabled) {
      if(isActivationSkipped) {
        timePlayedInSeconds += secondsPerFrame*(_debugSpeedupMode?100:1);
        
        if(/*demoModeEnabled &&*/ timePlayedInSeconds < maxPlayTime) {
          DrawElapsedTimeBar();
        } else {
          if(timePlayedInSeconds > maxPlayTime && timePlayedInSeconds < maxPlayTime + showEndOfDemoPopup) {
            isDemoExpired = true;
            DrawEndOfDemoPopup();
          } else if(timePlayedInSeconds >= maxPlayTime + showEndOfDemoPopup) {
            exit();
          }
        }
      } else {
        EnterSerialKeyFormDraw();
      }
    }
  }
  // Событие, вызываемое при выходе из приложения
  void atExit() {    
    printlog("Time elapsed: "+max(ActivationGlobalInfo.timePlayedInSeconds,0));
    printlog("Time to end of trial: "+max(ActivationGlobalInfo.maxPlayTime
            - ActivationGlobalInfo.timePlayedInSeconds,0));
            
    if(demoModeEnabled) {
      String[] activationInfo = {
        code(""+timePlayedInSeconds)
      }; 
      saveStrings(activationInfoFile,activationInfo);
    }
  }
  String code(String s) {
    return s;
  }
  String decode(String s) {
    return s;
  }
}
_ActivationGlobalInfo ActivationGlobalInfo = new _ActivationGlobalInfo();

