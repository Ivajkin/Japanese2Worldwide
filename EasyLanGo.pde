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
 // Библиотека воспроизведения звуков и музыки.
import ddf.minim.*;
// Графика на OpenGL.
import processing.opengl.*;

// Адрес сайта игры.
final String homeSiteUrl = "http://timatnet.byethost2.com/";
// Адрес страницы с которой можно купить игру.
final String purchaseUrl = homeSiteUrl + "/buy";
// Начало названия файла в котором хранятся данные о уровне,
// пройден ли он, сколько очков получено и др.
// Нужно просто додставить название уровня.
final String userLevelDataFileName = "logs/LevelData-";
// Строка в файле "logs/LevelData-*",
// которая говорит о том что уровень пройден.
final String levelClearedInfoString = "level_cleared=1";

// Здесь хранятся сообщения лог файла перед сбросом на диск.
String[] logmessages = {
  "Gamelog:"
};
// Вывести сообщение в лог файл.
void printlog(String msg) {
  logmessages = append(logmessages,msg);
  println(msg);
}
// Получить строку даты и времени в формате:
// 8.03.2011-13.43.21
String getTimeFormatString() {
  return ""+day()+"."+month()+"."+year()+"-"+hour()+"."+minute()+"."+second();
}

// Высота неигровой области экрана.
int hudHeight = 0;
// Высота игровой области.
final int playareaHeight = 768;
// Ширина игровой области.
final int playareaWidth = playareaHeight;
// Количество кадров в секунду.
final float framerate = 25;
// Количество секунд на один кадр ( < 1; при framerate = 100, frametime = 0.01).
final float frametime = 1/framerate;

// Начальная настройка и инициализация.
void setup() {
  //!!! Если срок вышел надо обновить
  // Пока срок до июня 2011
  if(month()>10 || year()>2011) {
    printlog("Срок использования этой версии истёк обновите до следующей версии: "+homeSiteUrl);
    link(homeSiteUrl,"_new");
  }
  smooth();
  size(playareaWidth,playareaHeight + hudHeight,OPENGL);   
  frameRate(framerate);

  ActivationGlobalInfo.ActivationSetup(playareaWidth,playareaHeight + hudHeight,framerate); 

  menuSetup();
}

// Основная функция отрисовки.
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

// Ввод данных с клавиатуры.
void keyPressed() {
  if(!ActivationGlobalInfo.isActivationSkipped) {
    ActivationGlobalInfo.OnKeyboardKeyPressed();
  }
}

// Уничтожаем необходимое при выключении.
void stop() {
  ActivationGlobalInfo.atExit();
  saveStrings("logs/full_game_log_"+getTimeFormatString()+".txt",logmessages);
}

