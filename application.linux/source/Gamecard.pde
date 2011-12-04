class Gamecard {
  Gamecard(GamecardType _type, int _posX, int _posY, boolean _face, int index)  {
    this.index = index;
    type = _type;
    posX = _posX;
    posY = _posY;
    face = _face;
    _drawPosX = posX*width/gamecardNumX+width/gamecardNumX/20;
    _drawPosY = posY*playareaHeight/gamecardNumY+playareaHeight/gamecardNumY/20;
  }
  GamecardType type;
  int posX, posY;
  float _drawPosX, _drawPosY;
  void display() {
    if(face) {
      image( type.img, _drawPosX, _drawPosY);
    } else {
      fill(255);
      rect(_drawPosX,_drawPosY,type.img.width-1,type.img.height-1);
      fill(0);
      text( type.name, _drawPosX+type.img.width/2-5, _drawPosY+type.img.height/2 + 5);
    }
      image( gamecardframe, _drawPosX + (type.img.width-gamecardframe.width)/2, _drawPosY + (type.img.height-gamecardframe.height)/2);
  }
  // Нарисовать рамку.
  void displayFrame() {
      image( gamecardselectionframe, _drawPosX + (type.img.width-gamecardselectionframe.width)/2, _drawPosY + (type.img.height-gamecardselectionframe.height)/2);
  }
  void update() {
    if(mouseLocked) {
      if(!mousePressed)
        mouseLocked = false;
    } else {
      if(mousePressed && over()) {
        mouseLocked = true;
        //
        onPressed();
      }
    }
  }
  // Положение карты на игровом столе (в массиве).
  final int index;
  void onPressed() {
    ResetHelpCountdown();
    //face = !face;
    if(gamecardselected==null)
      gamecardselected = this;
     else {
       if(face != gamecardselected.face) {
         printlog("Игрок выбрал карту " + gamecardselected.type.name + " и объеденил с " + type.name);
         printlog(">>Player:" + gamecardselected.type.name + "=>" + type.name);
       }
       if(gamecardselected.type.name == type.name && face != gamecardselected.face) {
         // Требуется перерисовка игрового поля
         isFullScreenRedrawNeeded = true;
         deadcard = true;
         gamecardselected.deadcard = true;
         gamecardselected = null;
         soundGoodChoose.rewind();
         soundGoodChoose.play();
         gamestatsGoodNum++;
         gamecardCurrentNum -= 2;
         // Комбо-бонус.
         gamecomboOnGood();
         if(gamecardCurrentNum<=0) {
           soundVictory.rewind();
           soundVictory.play();
           SetupEndOfGameMenu(gamestatsGoodNum,gamestatsFailedNum);
           String dateStr = getTimeFormatString();
           String[] winInformation = {
             "Player wins info",
             "Game mode: "+currentMode,
             "Date: "+dateStr,
             "Good score: "+gamestatsGoodNum,
             "Bad score: "+gamestatsFailedNum
           };
           String fileToSafe = "logs/Win-"+dateStr+".log";
           println("Saving data: "+winInformation[0]+"... into "+fileToSafe);
           saveStrings(fileToSafe,winInformation);
           String userDataFile = userLevelDataFileName+currentMode;
           String[] levelData = {levelClearedInfoString};
           saveStrings(userDataFile,levelData);
         }
       } else if(gamecardselected == this) {
         gamecardselected = null;
       } else {
         gamecardselected = null;
         soundWrongChoose.rewind();
         soundWrongChoose.play();
         gamestatsFailedNum++;
         // Комбо-цепочка окончена.
         gamecomboOnBad();
       }
     }
  }
  boolean deadcard = false;
  // Какой стороной повёрнута карта.
  boolean face;
  boolean over() {
    return (mouseX>_drawPosX && mouseX<_drawPosX+type.img.width
            &&
           mouseY>_drawPosY && mouseY<_drawPosY+type.img.height);
  }
}
