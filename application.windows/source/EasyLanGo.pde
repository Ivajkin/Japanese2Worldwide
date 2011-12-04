/*
* Copyright
 * Ivajkin Timofej
 * 13.11.2010
 * EasyLangGo - обучающая и развивающая
 * мини игра, по умолчанию включающая
 * модуль для изучения хироганы, кактаканы,
 * позволяющая увеличить количество языков.
 * Правила паззла нехитрые - соединяем символ
 * с его словесным выражением (звучанием).
 */
/*
при прогонке aka рядков на пациенте№1 получен результат:
 Хорошо: 128
 Плохо: 11
 Коэффициент хорошести: 11.64
 Коэффициент плохости: 0.086
 */
import ddf.minim.*;
import processing.opengl.*;

final String homeSiteUrl = "http://timatnet.byethost2.com/";
final String purchaseUrl = homeSiteUrl + "/buy";
// Начало названия файла в котором хранятся данные о уровне,
// пройден ли он, сколько очков получено и др.
// Нужно просто додставить название уровня.
final String userLevelDataFileName = "logs/LevelData-";
// Строка в файле "logs/LevelData-*",
// которая говорит о том что уровень пройден.
final String levelClearedInfoString = "level_cleared=1";

String[] logmessages = {
  "Gamelog:"
};
void printlog(String msg) {
  logmessages = append(logmessages,msg);
  println(msg);
}

String getTimeFormatString() {
  return ""+day()+"."+month()+"."+year()+"-"+hour()+"."+minute()+"."+second();
}

int hudHeight = 0;
final int playareaHeight = 768;
final int playareaWidth = playareaHeight;
final float framerate = 25;
final float frametime = 1/framerate;

void setup() {
  //!!! Если срок вышел надо обновить
  // Пока срок до июня 2011
  if(month()>6 || year()>2011) {
    printlog("Срок использования этой версии истёк обновите до следующей версии: "+homeSiteUrl);
    link(homeSiteUrl,"_new");
  }
  smooth();
  size(playareaWidth,playareaHeight + hudHeight,OPENGL);   
  frameRate(framerate);

  ActivationGlobalInfo.ActivationSetup(playareaWidth,playareaHeight + hudHeight,framerate); 

  menuSetup();
}

void draw() {
  if(!ActivationGlobalInfo.isDemoExpired) {
    if(ActivationGlobalInfo.isActivationSkipped) {
      if(isMenuEnabled)
        menu();
      else
        game();
    }
  }

  ActivationGlobalInfo.ActivationUpdate();
}

void keyPressed() {
  if(!ActivationGlobalInfo.isActivationSkipped) {
    ActivationGlobalInfo.OnKeyboardKeyPressed();
  }
}

void stop() {
  ActivationGlobalInfo.atExit();
  saveStrings("logs/full_game_log_"+getTimeFormatString()+".txt",logmessages);
}

