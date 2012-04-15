import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import processing.opengl.*; 
import java.util.List; 
import ddf.minim.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Japanese2Worldwide extends PApplet {

/*
* Copyright
 * Ivajkin Timofej
 * 13.11.2010
 * EasyLangGo - \u043e\u0431\u0443\u0447\u0430\u044e\u0449\u0430\u044f \u0438 \u0440\u0430\u0437\u0432\u0438\u0432\u0430\u044e\u0449\u0430\u044f
 * \u043c\u0438\u043d\u0438 \u0438\u0433\u0440\u0430, \u043f\u043e \u0443\u043c\u043e\u043b\u0447\u0430\u043d\u0438\u044e \u0432\u043a\u043b\u044e\u0447\u0430\u044e\u0449\u0430\u044f
 * \u043c\u043e\u0434\u0443\u043b\u044c \u0434\u043b\u044f \u0438\u0437\u0443\u0447\u0435\u043d\u0438\u044f \u0445\u0438\u0440\u043e\u0433\u0430\u043d\u044b, \u043a\u0430\u043a\u0442\u0430\u043a\u0430\u043d\u044b,
 * \u043f\u043e\u0437\u0432\u043e\u043b\u044f\u044e\u0449\u0430\u044f \u0443\u0432\u0435\u043b\u0438\u0447\u0438\u0442\u044c \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u044f\u0437\u044b\u043a\u043e\u0432.
 * \u041f\u0440\u0430\u0432\u0438\u043b\u0430 \u043f\u0430\u0437\u0437\u043b\u0430 \u043d\u0435\u0445\u0438\u0442\u0440\u044b\u0435 - \u0441\u043e\u0435\u0434\u0438\u043d\u044f\u0435\u043c \u0441\u0438\u043c\u0432\u043e\u043b
 * \u0441 \u0435\u0433\u043e \u0441\u043b\u043e\u0432\u0435\u0441\u043d\u044b\u043c \u0432\u044b\u0440\u0430\u0436\u0435\u043d\u0438\u0435\u043c (\u0437\u0432\u0443\u0447\u0430\u043d\u0438\u0435\u043c).
 */
/*
\u043f\u0440\u0438 \u043f\u0440\u043e\u0433\u043e\u043d\u043a\u0435 aka \u0440\u044f\u0434\u043a\u043e\u0432 \u043d\u0430 \u043f\u0430\u0446\u0438\u0435\u043d\u0442\u0435\u21161 \u043f\u043e\u043b\u0443\u0447\u0435\u043d \u0440\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442:
 \u0425\u043e\u0440\u043e\u0448\u043e: 128
 \u041f\u043b\u043e\u0445\u043e: 11
 \u041a\u043e\u044d\u0444\u0444\u0438\u0446\u0438\u0435\u043d\u0442 \u0445\u043e\u0440\u043e\u0448\u0435\u0441\u0442\u0438: 11.64
 \u041a\u043e\u044d\u0444\u0444\u0438\u0446\u0438\u0435\u043d\u0442 \u043f\u043b\u043e\u0445\u043e\u0441\u0442\u0438: 0.086
 */
 // \u0411\u0438\u0431\u043b\u0438\u043e\u0442\u0435\u043a\u0430 \u0432\u043e\u0441\u043f\u0440\u043e\u0438\u0437\u0432\u0435\u0434\u0435\u043d\u0438\u044f \u0437\u0432\u0443\u043a\u043e\u0432 \u0438 \u043c\u0443\u0437\u044b\u043a\u0438.

// \u0413\u0440\u0430\u0444\u0438\u043a\u0430 \u043d\u0430 OpenGL.


// \u0410\u0434\u0440\u0435\u0441 \u0441\u0430\u0439\u0442\u0430 \u0438\u0433\u0440\u044b.
final String homeSiteUrl = "http://timatnet.byethost2.com/";
// \u0410\u0434\u0440\u0435\u0441 \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b \u0441 \u043a\u043e\u0442\u043e\u0440\u043e\u0439 \u043c\u043e\u0436\u043d\u043e \u043a\u0443\u043f\u0438\u0442\u044c \u0438\u0433\u0440\u0443.
final String purchaseUrl = homeSiteUrl + "/buy";
// \u041d\u0430\u0447\u0430\u043b\u043e \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u044f \u0444\u0430\u0439\u043b\u0430 \u0432 \u043a\u043e\u0442\u043e\u0440\u043e\u043c \u0445\u0440\u0430\u043d\u044f\u0442\u0441\u044f \u0434\u0430\u043d\u043d\u044b\u0435 \u043e \u0443\u0440\u043e\u0432\u043d\u0435,
// \u043f\u0440\u043e\u0439\u0434\u0435\u043d \u043b\u0438 \u043e\u043d, \u0441\u043a\u043e\u043b\u044c\u043a\u043e \u043e\u0447\u043a\u043e\u0432 \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u043e \u0438 \u0434\u0440.
// \u041d\u0443\u0436\u043d\u043e \u043f\u0440\u043e\u0441\u0442\u043e \u0434\u043e\u0434\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0443\u0440\u043e\u0432\u043d\u044f.
final String userLevelDataFileName = "logs/LevelData-";
// \u0421\u0442\u0440\u043e\u043a\u0430 \u0432 \u0444\u0430\u0439\u043b\u0435 "logs/LevelData-*",
// \u043a\u043e\u0442\u043e\u0440\u0430\u044f \u0433\u043e\u0432\u043e\u0440\u0438\u0442 \u043e \u0442\u043e\u043c \u0447\u0442\u043e \u0443\u0440\u043e\u0432\u0435\u043d\u044c \u043f\u0440\u043e\u0439\u0434\u0435\u043d.
final String levelClearedInfoString = "level_cleared=1";

// \u0417\u0434\u0435\u0441\u044c \u0445\u0440\u0430\u043d\u044f\u0442\u0441\u044f \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f \u043b\u043e\u0433 \u0444\u0430\u0439\u043b\u0430 \u043f\u0435\u0440\u0435\u0434 \u0441\u0431\u0440\u043e\u0441\u043e\u043c \u043d\u0430 \u0434\u0438\u0441\u043a.
String[] logmessages = {
  "Gamelog:"
};
// \u0412\u044b\u0432\u0435\u0441\u0442\u0438 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0432 \u043b\u043e\u0433 \u0444\u0430\u0439\u043b.
public void printlog(String msg) {
  logmessages = append(logmessages,msg);
  println(msg);
}
// \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u0442\u0440\u043e\u043a\u0443 \u0434\u0430\u0442\u044b \u0438 \u0432\u0440\u0435\u043c\u0435\u043d\u0438 \u0432 \u0444\u043e\u0440\u043c\u0430\u0442\u0435:
// 8.03.2011-13.43.21
public String getTimeFormatString() {
  return ""+day()+"."+month()+"."+year()+"-"+hour()+"."+minute()+"."+second();
}

// \u0412\u044b\u0441\u043e\u0442\u0430 \u043d\u0435\u0438\u0433\u0440\u043e\u0432\u043e\u0439 \u043e\u0431\u043b\u0430\u0441\u0442\u0438 \u044d\u043a\u0440\u0430\u043d\u0430.
int hudHeight = 0;
// \u0412\u044b\u0441\u043e\u0442\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u0439 \u043e\u0431\u043b\u0430\u0441\u0442\u0438.
final int playareaHeight = 768;
// \u0428\u0438\u0440\u0438\u043d\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u0439 \u043e\u0431\u043b\u0430\u0441\u0442\u0438.
final int playareaWidth = playareaHeight;
// \u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u043a\u0430\u0434\u0440\u043e\u0432 \u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0443.
final float framerate = 25;
// \u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0441\u0435\u043a\u0443\u043d\u0434 \u043d\u0430 \u043e\u0434\u0438\u043d \u043a\u0430\u0434\u0440 ( < 1; \u043f\u0440\u0438 framerate = 100, frametime = 0.01).
final float frametime = 1/framerate;

// \u041d\u0430\u0447\u0430\u043b\u044c\u043d\u0430\u044f \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 \u0438 \u0438\u043d\u0438\u0446\u0438\u0430\u043b\u0438\u0437\u0430\u0446\u0438\u044f.
public void setup() {
  //!!! \u0415\u0441\u043b\u0438 \u0441\u0440\u043e\u043a \u0432\u044b\u0448\u0435\u043b \u043d\u0430\u0434\u043e \u043e\u0431\u043d\u043e\u0432\u0438\u0442\u044c
  // \u041f\u043e\u043a\u0430 \u0441\u0440\u043e\u043a \u0434\u043e \u0438\u044e\u043d\u044f 2011
  if(month()>10 || year()>2011) {
    printlog("\u0421\u0440\u043e\u043a \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u044d\u0442\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438 \u0438\u0441\u0442\u0451\u043a \u043e\u0431\u043d\u043e\u0432\u0438\u0442\u0435 \u0434\u043e \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u0439 \u0432\u0435\u0440\u0441\u0438\u0438: "+homeSiteUrl);
    link(homeSiteUrl,"_new");
  }
  smooth();
  size(playareaWidth,playareaHeight + hudHeight,OPENGL);   
  frameRate(framerate);
  
  ActivationGlobalInfo.ActivationSetup(playareaWidth,playareaHeight + hudHeight,framerate); 

  menuSetup();
}

// \u041e\u0441\u043d\u043e\u0432\u043d\u0430\u044f \u0444\u0443\u043d\u043a\u0446\u0438\u044f \u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0438.
public void draw() {
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

// \u0412\u0432\u043e\u0434 \u0434\u0430\u043d\u043d\u044b\u0445 \u0441 \u043a\u043b\u0430\u0432\u0438\u0430\u0442\u0443\u0440\u044b.
public void keyPressed() {
  if(!ActivationGlobalInfo.isActivationSkipped) {
    ActivationGlobalInfo.OnKeyboardKeyPressed();
  }
}

// \u0423\u043d\u0438\u0447\u0442\u043e\u0436\u0430\u0435\u043c \u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e\u0435 \u043f\u0440\u0438 \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0438.
public void stop() {
  ActivationGlobalInfo.atExit();
  saveStrings("logs/full_game_log_"+getTimeFormatString()+".txt",logmessages);
}

// \u041c\u043e\u0434\u0443\u043b\u044c \u0434\u043b\u044f \u0443\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435\u043c \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0435\u0439 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u044f,
// \u043f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u043c \u0432 \u0434\u0435\u043c\u043e-\u0440\u0435\u0436\u0438\u043c\u0435
// \u043e\u0433\u0440\u0430\u043d\u0438\u0447\u0435\u043d\u043d\u044b\u0439 \u0441\u0440\u043e\u043a.

class _ActivationGlobalInfo {
  // \u041f\u0435\u0440\u0435\u0432\u0435\u0441\u0442\u0438 \u0438\u0433\u0440\u0443 \u0432 \u0440\u0435\u0436\u0438\u043c \u043f\u043e\u043b\u043d\u043e\u0439 \u0432\u0435\u0440\u0441\u0438\u0438 \u043f\u0440\u0438 \u0432\u0432\u0435\u0434\u0435\u043d\u0438\u0438 \u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e\u0433\u043e \u0441\u0435\u0440\u0438\u0439\u043d\u043e\u0433\u043e \u043a\u043b\u044e\u0447\u0430.
  public void ActivateApplication() {
    String[] activationInfo = {
      code("activated")
    }; 
    saveStrings(activationInfoFile,activationInfo);
    demoModeEnabled = false;
    isActivationSkipped = true;
    printlog("Activating application");
  }
  // \u0424\u0430\u0439\u043b \u0432 \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0437\u0430\u043f\u0438\u0441\u044b\u0432\u0430\u0435\u0442\u0441\u044f \u0438 \u0438\u0437 \u043a\u043e\u0442\u043e\u0440\u043e\u0433\u043e
  // \u0438\u0437\u0432\u043b\u0435\u043a\u0430\u0435\u0442\u0441\u044f \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044f \u043e\u0431 \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0438 \u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u043c\u0441\u044f \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  final String activationInfoFile = "logs/chunck";
  // \u0417\u0430\u043a\u043e\u043d\u0447\u0438\u043b\u0441\u044f \u043b\u0438 \u0434\u0435\u043c\u043e-\u043f\u0435\u0440\u0438\u043e\u0434.
  public boolean isDemoExpired = false;
  // \u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u044b\u0439 \u0441\u0440\u043e\u043a \u0438\u0433\u0440\u044b \u0432 \u0434\u0435\u043c\u043e-\u0440\u0435\u0436\u0438\u043c\u0435 (\u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0430\u0445).
  final float maxPlayTime = 15*60;
  // \u0412\u0440\u0435\u043c\u044f \u0432 \u0442\u0435\u0447\u0435\u043d\u0438\u0435 \u043a\u043e\u0442\u043e\u0440\u043e\u0433\u043e \u043f\u043e \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0441\u0440\u043e\u043a\u0430 \u0434\u0435\u043c\u043e-\u0440\u0435\u0436\u0438\u043c\u0430 \u0434\u0435\u043c\u043e\u043d\u0441\u0442\u0440\u0438\u0440\u0443\u0435\u0442\u0441\u044f \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0438\u0433\u0440\u044b (\u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0430\u0445).
  final float showEndOfDemoPopup = 10;
  // \u0410\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d \u043b\u0438 \u0434\u0435\u043c\u043e-\u0440\u0435\u0436\u0438\u043c \u0441 \u043e\u0442\u0441\u0447\u0451\u0442\u043e\u043c \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  boolean demoModeEnabled = true;
  int screenSizeX, screenSizeY;
  float secondsPerFrame;
  // \u0412\u0440\u0435\u043c\u0435\u043d\u0438 \u0431\u044b\u043b\u043e \u0441\u044b\u0433\u0440\u0430\u043d\u043d\u043e \u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0430\u0445.
  float timePlayedInSeconds = 0;//14*60+55;
  // \u042d\u0442\u0430\u043f \u0432\u0432\u0435\u0434\u0435\u043d\u0438\u044f \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u043e\u043d\u043d\u043e\u0433\u043e \u043a\u043b\u044e\u0447\u0430 \u043f\u0440\u043e\u043f\u0443\u0449\u0435\u043d \u0438\u043b\u0438 \u0437\u0430\u0432\u0435\u0440\u0448\u0451\u043d.
  boolean isActivationSkipped = false;
  // \u041a\u043d\u043e\u043f\u043a\u0438 \u0434\u043b\u044f \u044d\u043a\u0440\u0430\u043d\u0430 \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0438.
  ImageButton purchaseBtn = null, acceptBtn = null, skipBtn = null;
  PImage activateScreenBackground = null;
  PImage serial_key_correct_image = null,
          serial_key_invalid_image = null;
  // \u0421\u0442\u0440\u043e\u043a\u0430, \u043a\u043e\u0442\u043e\u0440\u0443\u044e \u0432\u0432\u043e\u0434\u044f\u0442 \u043f\u0440\u0438 \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0438.
  String currentSerialKeyString = "";
  PFont SerialKeyInputFont = null;
  // \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u043e\u043a\u043d\u0430 \u0434\u043b\u044f \u0432\u0432\u043e\u0434\u0430 \u043f\u0430\u0440\u043e\u043b\u044f \u0434\u043b\u044f \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0438.
  public void EnterSerialKeyFormDraw() {
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
  // \u0423\u043d\u0438\u0447\u0442\u043e\u0436\u0430\u0435\u043c \u0438\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441 \u0434\u043b\u044f \u0432\u0432\u043e\u0434\u0430 \u0441\u0435\u0440\u0438\u0439\u043d\u043e\u0433\u043e \u043a\u043b\u044e\u0447\u0430.
  public void DeleteActivationGUI() {
    acceptBtn.deadElement = true;
    skipBtn.deadElement = true;
    purchaseBtn.deadElement = true;
  }
  // \u0421\u043e\u0431\u044b\u0442\u0438\u0435 \u043e\u0431\u0440\u0430\u0431\u0430\u0442\u044b\u0432\u0430\u0435\u0442 \u043d\u0430\u0436\u0438\u043c\u0430\u0435\u043c\u044b\u0435 \u043a\u043b\u0430\u0432\u0438\u0448\u0438 \u043f\u0440\u0438 \u0432\u0432\u043e\u0434\u0435 \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u043e\u043d\u043d\u043e\u0433\u043e \u043a\u043b\u044e\u0447\u0430.
  public void OnKeyboardKeyPressed() {
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
  // \u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u0430\u044f \u0434\u043b\u0438\u043d\u0430 \u0432\u0432\u043e\u0434\u0438\u043c\u043e\u0433\u043e \u043a\u043b\u044e\u0447\u0430
  final int maxSerialKeyLength = 13;
  // \u0412\u0435\u0440\u0435\u043d \u043b\u0438 \u0432\u0432\u0435\u0434\u0451\u043d\u044b\u0439 \u043a\u043b\u044e\u0447 \u0430\u043a\u0442\u0438\u0432\u0430\u0446\u0438\u0438.
  public boolean isSerialKeyCorrect() {
    return (currentSerialKeyString.length()==maxSerialKeyLength && currentSerialKeyString.charAt(0)-currentSerialKeyString.charAt(7) == 5);
  }
  // \u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0440\u0430\u0437\u043c\u0435\u0440\u0430 \u044d\u043a\u0440\u0430\u043d\u0430, \u043a\u043e\u043b-\u0432\u0430 \u043a\u0430\u0434\u0440\u043e\u0432 \u0432 \u0441\u0435\u043a\u0443\u043d\u0434\u0443 \u0438 \u0434\u0440.
  public void ActivationSetup(int screenSizeX, int screenSizeY, float framerate) {
    printlog("Setup activation");
    this.screenSizeX = screenSizeX;
    this.screenSizeY = screenSizeY;
    this.secondsPerFrame = 1.0f/framerate;
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
        timePlayedInSeconds = PApplet.parseInt(activationInfo);
      }
    } else {
      /* // \u043d\u0435\u0442 \u0444\u0430\u0439\u043b\u0430, \u043a\u0442\u043e-\u0442\u043e \u0443\u0434\u0430\u043b\u0438\u043b \u0438\u043b\u0438 \u0435\u0449\u0451 \u0447\u0442\u043e-\u0442\u043e.
      timePlayedInSeconds = maxPlayTime;*/
      // \u0421\u0435\u0439\u0447\u0430\u0441 \u0435\u0441\u043b\u0438 \u043d\u0435\u0442 \u0444\u0430\u0439\u043b\u0430, \u0437\u043d\u0430\u0447\u0438\u0442 \u0442\u043e\u043b\u044c\u043a\u043e \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u043b\u0438.
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
  // \u041c\u0438\u043d\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u0438 \u043f\u0430\u043d\u0435\u043b\u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u0433\u043e\u0441\u044f \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  final float minElapsedTimeBarOpacity = 20;
  // \u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u0438 \u043f\u0430\u043d\u0435\u043b\u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u0433\u043e\u0441\u044f \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  final float maxElapsedTimeBarOpacity = 255;
  // \u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0437\u0430\u0442\u0435\u043d\u0435\u043d\u0438\u044f \u043f\u0430\u043d\u0435\u043b\u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u0433\u043e\u0441\u044f \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  final float elapcedTimeBarFadeVelocity = 600*frametime;
  // \u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0441\u0442\u0438 \u043f\u0430\u043d\u0435\u043b\u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u0433\u043e\u0441\u044f \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  float elapsedTimeBarOpacity = maxElapsedTimeBarOpacity;
  // \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u043f\u0430\u043d\u0435\u043b\u0438 \u043e\u0441\u0442\u0430\u0432\u0448\u0435\u0433\u043e\u0441\u044f \u0431\u0435\u0441\u043f\u043b\u0430\u0442\u043d\u043e\u0433\u043e \u0432\u0440\u0435\u043c\u0435\u043d\u0438.
  public void DrawElapsedTimeBar() {
      boolean mouseInPanel = (mouseX > screenSizeX - 200 && mouseY > screenSizeY - 200);
      // \u0417\u0434\u0435\u0441\u044c \u043f\u0430\u043d\u0435\u043b\u044c \u0441\u043a\u0430\u043a\u0430\u043b\u0430 \u0432\u0432\u0435\u0440\u0445 \u043f\u0440\u0438 \u043d\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0438 \u043c\u044b\u0448\u043a\u0438.
      /*final float panelStartX = (mouseInPanel ? screenSizeX - 200 : screenSizeX - 200);
      final float panelStartY = (mouseInPanel ? 20 : screenSizeY - 170);*/
      final float panelStartX = screenSizeX - 200;
      final float panelStartY = screenSizeY - 170;
      // \u0422\u0435\u043f\u0435\u0440\u044c \u043f\u0440\u043e\u0441\u0442\u043e \u043f\u043b\u0430\u0432\u043d\u043e \u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u0441\u044f \u043f\u0440\u043e\u0437\u0440\u0430\u0447\u043d\u043e\u0439.
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
      // \u0415\u0441\u043b\u0438 \u0431\u043e\u043b\u044c\u0448\u0435 \u043e\u0434\u043d\u043e\u0439 \u043c\u0438\u043d\u0443\u0442\u044b - \u043f\u0438\u0448\u0435\u043c \u043a\u043e\u043b-\u0432\u043e \u043c\u0438\u043d\u0443\u0442, \u0438\u043d\u0430\u0447\u0435 \u043a\u0440\u0430\u0441\u043d\u044b\u043c \u043a\u043e\u043b-\u0432\u043e \u0441\u0435\u043a\u0443\u043d\u0434.
      if(maxPlayTime - timePlayedInSeconds>=60) {
        fill(0,0,0,elapsedTimeBarOpacity);
        text(new String("\u041e\u0441\u0442\u0430\u043b\u043e\u0441\u044c ") + round((maxPlayTime - timePlayedInSeconds)/60) + " \u043c\u0438\u043d\u0443\u0442.",panelStartX + 20+1, panelStartY + 60+1);
        fill(255,255,255,elapsedTimeBarOpacity);
        text(new String("\u041e\u0441\u0442\u0430\u043b\u043e\u0441\u044c ") + round((maxPlayTime - timePlayedInSeconds)/60) + " \u043c\u0438\u043d\u0443\u0442.",panelStartX + 20, panelStartY + 60);
      } else {
        fill(0,0,0,elapsedTimeBarOpacity);
        text(new String("\u041e\u0441\u0442\u0430\u043b\u043e\u0441\u044c ") + round((maxPlayTime - timePlayedInSeconds)) + " \u0441\u0435\u043a\u0443\u043d\u0434!",panelStartX + 20+1, panelStartY + 60+1);
        fill(255,100,20,elapsedTimeBarOpacity);
        text(new String("\u041e\u0441\u0442\u0430\u043b\u043e\u0441\u044c ") + round((maxPlayTime - timePlayedInSeconds)) + " \u0441\u0435\u043a\u0443\u043d\u0434!",panelStartX + 20, panelStartY + 60);
      }
  }
  // \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0435\u043c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0434\u0435\u043c\u043e\u0432\u0435\u0440\u0441\u0438\u0438.
  public void DrawEndOfDemoPopup() {
    /*if(endOfDemoPopupImage == null) {
      endOfDemoPopupImage = loadImage("Activation/endOfDemoPopupImage.png");
    }
    fill(255,0,0);
    rect(0,0,screenSizeX,screenSizeY);
    image(endOfDemoPopupImage, screenSizeX/2 - endOfDemoPopupImage.width/2, screenSizeY/2 - endOfDemoPopupImage.height/2);*/
    EnterSerialKeyFormDraw();
  }
  // \u0420\u0435\u0436\u0438\u043c \u0432 \u043a\u043e\u0442\u043e\u0440\u043e\u043c \u0432\u0440\u0435\u043c\u044f \u0443\u0445\u043e\u0434\u0438\u0442 \u0441\u043e 100x \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u044c\u044e. \u0420\u0435\u0436\u0438\u043c \u0434\u043b\u044f \u0442\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f.
  boolean _debugSpeedupMode = false;
  public void ActivationUpdate() {
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
  // \u0421\u043e\u0431\u044b\u0442\u0438\u0435, \u0432\u044b\u0437\u044b\u0432\u0430\u0435\u043c\u043e\u0435 \u043f\u0440\u0438 \u0432\u044b\u0445\u043e\u0434\u0435 \u0438\u0437 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u044f
  public void atExit() {    
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
  public String code(String s) {
    return s;
  }
  public String decode(String s) {
    return s;
  }
}
_ActivationGlobalInfo ActivationGlobalInfo = new _ActivationGlobalInfo();

// \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0435\u043c\u044b\u0435 \u043e\u0431\u044a\u0435\u043a\u0442\u044b.
IDrawable[] drawableObjects = null;
final int drawableObjectsInitialContainerSize = 128;
// \u041d\u0430\u0441\u0442\u043e\u044f\u0449\u0435\u0435 \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u043e\u0431\u044a\u0435\u043a\u0442\u043e\u0432.
int drawableObjectsCount = 0;
class IDrawable {
  IDrawable() {
    if(null == drawableObjects) {
      drawableObjects = new IDrawable[drawableObjectsInitialContainerSize];
    }
    if(drawableObjectsCount>=drawableObjects.length-1) {
      IDrawable[] newContainer = new IDrawable[drawableObjects.length*2];
      for(int i = 0; i<drawableObjects.length; i++) {
        newContainer[i] = drawableObjects[i];
      }
      for(int i = drawableObjects.length; i<newContainer.length; i++) {
        newContainer[i] = null;
      }
      drawableObjects = newContainer;
    }
    boolean addedToDrawableObjects = false;
    for(int i = 0; i<drawableObjects.length; i++) {
      if(drawableObjects[i] == null || drawableObjects[i].deadElement) {
        drawableObjects[i] = this;
        addedToDrawableObjects = true;
      }
    }
    if(!addedToDrawableObjects) {
      drawableObjects[drawableObjectsCount++] = this;
    }
  }
  // \u041e\u0442\u043e\u0431\u0440\u0430\u0436\u0435\u043d\u0438\u0435 \u043e\u0431\u044a\u0435\u043a\u0442\u0430.
  public void display(){};
  boolean deadElement = false;
}
// \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0432\u0441\u0435\u0445 IDrawables.
public void displayDrawables() {
  for(int i = 0; i<drawableObjectsCount; i++) {
    if(drawableObjects[i] != null) {
      if(drawableObjects[i].deadElement)
        drawableObjects[i] = null;
      else
        drawableObjects[i].display();
    }
  }
}

// \u041a\u043d\u043e\u043f\u043a\u0430
class Button extends IDrawable
{
  int x, y;
  public int getX() {return x;}
  public int getY() {return y;}
  int basecolor, highlightcolor;
  int currentcolor;
  boolean over = false;
  boolean pressed = false;   
  boolean wasPressed = false;
  
  public void update() 
  {
    if(over()) {
      currentcolor = highlightcolor;
    } else {
      currentcolor = basecolor;
    }
    
    
    if(pressed && !mousePressed && over())
      wasPressed = true;  
      
    if(over() && mousePressed)
      pressed = true;
    else
      pressed = false;
  }
  
  public boolean pressed() 
  {
    boolean ret = wasPressed;
    wasPressed = false;
    return ret;
  }

  public boolean over() 
  { 
    return true; 
  }

  public boolean overRect(int x, int y, int width, int height) 
  {
    if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
      return true;
    } 
    else {
      return false;
    }
  }

  public boolean overCircle(int x, int y, int diameter) 
  {
    float disX = x - mouseX;
    float disY = y - mouseY;
    if(sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
      return true;
    } 
    else {
      return false;
    }
  }

}

class CircleButton extends Button
{ 
  int size;
  CircleButton(int ix, int iy, int isize, int icolor, int ihighlight, String ibuttontext) 
  {
    x = ix;
    y = iy;
    size = isize;
    basecolor = icolor;
    highlightcolor = ihighlight;
    currentcolor = basecolor;
    buttontext = ibuttontext;
  }

  public boolean over() 
  {
    if( overCircle(x, y, size) ) {
      over = true;
      return true;
    } 
    else {
      over = false;
      return false;
    }
  }

  public void display() 
  {
    stroke(255);
    fill(currentcolor);
    ellipse(x, y, size, size);
    
    fill(255);
    text(buttontext,x-size/2.5f,y);
    fill(0);
    text(buttontext,x-size/2.5f+1,y+1);
    
    if(over)
    lifetime += frametime;
    ellipse(cos(lifetime*2)*size/2+x, sin(lifetime*2)*size/2+y, size*0.1f, size*0.1f);
  }
  float lifetime = 0;
  
  String buttontext;
}

class ImageButton extends Button
{ 
  PImage buttonImage;
  final float normalScale = 0.85f;
  final float selectedScale = 1;
  float scale = normalScale;
  ImageButton(int ix, int iy, String buttonImagePath) 
  {
    x = ix;
    y = iy;
    this.buttonImage = requestImage(buttonImagePath);//loadImage(buttonImagePath);
  }

  public boolean over() 
  {
    if( overRect(x-buttonImage.width/2, y-buttonImage.height/2, buttonImage.width, buttonImage.height) ) {
      over = true;
      return true;
    } 
    else {
      over = false;
      return false;
    }
  }

  public void display() 
  {
    image(buttonImage,x-scale*buttonImage.width/2, y-scale*buttonImage.height/2,scale*buttonImage.width, scale*buttonImage.height);
    if(over) {
      scale = min(scale+0.05f,selectedScale);
    } else {
      scale = max(scale-0.05f,normalScale);
    }
  }
}

// \u041c\u0430\u0441\u0441\u0438\u0432 \u0432\u0441\u0435\u0445 \u043a\u0430\u0440\u0442 \u043d\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u043c \u0441\u0442\u043e\u043b\u0435.
Gamecard[] gamecardgrid;

// \u041d\u0443\u0436\u043d\u043e \u043b\u0438 \u0432 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u043c \u043a\u0430\u0434\u0440\u0435 \u043f\u0435\u0440\u0435\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0442\u044c
// \u0432\u0435\u0441\u044c \u044d\u043a\u0440\u0430\u043d \u0441 \u0437\u0430\u0434\u043d\u0438\u043c \u0444\u043e\u043d\u043e\u043c \u0438 \u0438\u0433\u0440\u043e\u0432\u044b\u043c \u043f\u043e\u043b\u0435\u043c, \u0431\u0435\u0437 \u044d\u0444\u0444\u0435\u043a\u0442\u043e\u0432
boolean isFullScreenRedrawNeeded = true;
// \u0421\u043e\u0445\u0440\u0430\u043d\u0451\u043d\u043d\u043e\u0435 \u0434\u043b\u044f \u0431\u044b\u0441\u0442\u0440\u043e\u0439 \u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0438 \u0438\u0433\u0440\u043e\u0432\u043e\u0435 \u043f\u043e\u043b\u0435 \u0438 \u0437\u0430\u0434\u043d\u0438\u0439 \u0444\u043e\u043d - \u043e\u0431\u044a\u0435\u0434\u0438\u043d\u0451\u043d\u043d\u044b\u0435.
PImage gameFullScreenCashed = null;

// \u0412\u0440\u0435\u043c\u044f \u043e\u0436\u0438\u0434\u0430\u043d\u0438\u044f \u043f\u0435\u0440\u0435\u0434 \u043f\u043e\u0434\u0441\u043a\u0430\u0437\u043a\u043e\u0439
final float helpWaitTime = 8;
// \u0422\u0435\u043a\u0443\u0449\u0438\u0439 \u0441\u0447\u0451\u0442\u0447\u0438\u043a \u0432\u0440\u0435\u043c\u0435\u043d\u0438 \u0431\u0435\u0437\u0434\u0435\u0439\u0441\u0442\u0432\u0438\u044f \u0438\u0433\u0440\u043e\u043a\u0430, \u043f\u043e \u0434\u043e\u0441\u0442\u0438\u0436\u0435\u043d\u0438\u0438
// helpWaitTime \u043f\u043e\u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u043f\u043e\u0434\u0441\u043a\u0430\u0437\u043a\u0430 (\u0435\u0441\u043b\u0438 \u0432\u044b\u0434\u0435\u043b\u0435\u043d \u0441\u0438\u043c\u0432\u043e\u043b,
// \u043d\u0430\u0434 \u0432\u0442\u043e\u0440\u044b\u043c \u043f\u043e\u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u0432\u044b\u0434\u0435\u043b\u0435\u043d\u0438\u0435, \u0438\u043d\u0430\u0447\u0435 \u043d\u0430\u0434 \u0441\u043b\u0443\u0447\u0430\u0439\u043d\u044b\u043c \u0438
// \u0435\u0433\u043e \u0432\u0442\u043e\u0440\u044b\u043c \u043f\u043e\u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u0432\u044b\u0434\u0435\u043b\u0435\u043d\u0438\u0435)
// \u0421\u0431\u0440\u0430\u0441\u044b\u0432\u0430\u0435\u0442\u0441\u044f \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u043a\u043b\u0430\u0432\u0438\u0448\u0438 \u043c\u044b\u0448\u0438.
float currentHelpCountdown = 0;
// \u041c\u043e\u0436\u043d\u043e \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u043f\u043e\u043c\u043e\u0449\u044c \u0438\u043b\u0438 \u043d\u0435\u0442.
public boolean isHelpMustBeShown() {
  return (currentHelpCountdown>=helpWaitTime);
}
// \u041a\u0430\u0440\u0442\u044b, \u043a\u043e\u0442\u043e\u0440\u044b\u0435 \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u044e\u0442 \u043d\u0430 \u043f\u043e\u0434\u0441\u043a\u0430\u0437\u043a\u0443.
int helpCardShow_1 = -1;
int helpCardShow_2 = -1;
public void ResetHelpCountdown() {
  currentHelpCountdown = 0;
  helpCardShow_1 = -1;
  helpCardShow_2 = -1;
}
public void UpdateHelp() {
  currentHelpCountdown += frametime;
  if(isHelpMustBeShown() && gamecardCurrentNum>=2) {
    if(helpCardShow_2 == -1) {
      printlog("Start showing help...");
      if(helpCardShow_1 == -1) {
        printlog("Selecting first card for help.");
        if(gamecardselected==null) {
          helpCardShow_1 = round(random(gamecardNumX*gamecardNumY+1)*1.9812f)%(gamecardNumX*gamecardNumY);
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
    // \u043e\u0442\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0435\u043c \u043f\u043e\u0434\u0441\u043a\u0430\u0437\u043a\u0443.
    gamecardgrid[helpCardShow_1].displayFrame();
    gamecardgrid[helpCardShow_2].displayFrame();
  } 
}

// \u0422\u0435\u043a\u0441\u0442 \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435, \u0443\u043b\u0435\u0442\u0430\u0435\u0442 \u0432\u0432\u0435\u0440\u0445.
class FloatingText extends IDrawable
{
  FloatingText(float x, float y, String text2draw, int col) {
    this.x = x;
    this.y = y;
    this.text2draw = text2draw;
    this.col = col;
  }
  float x, y;
  float vx = 0, vy = 0;
  String text2draw;
  int col = color(40,223,15, 255);
  float opacity = 255;
  public void display() {
    fill(color(0,0,0,opacity));
    text(text2draw, x+0.6f, y+0.6f);
    fill(col);
    text(text2draw, x, y);
    vy -= frametime*0.3f;
    vx += frametime*0.09f;
    x += vx;
    y += vy;
    opacity += vy*1;
    col = color(red(col),green(col),blue(col),opacity);
    if(opacity<=0.01f)
      deadElement = true;
  }
}
int gamegreencombotextcolor = color(40,223,15, 255);
int gameyellowcombotextcolor = color(220,223,15, 255);
int gameredcombotextcolor = color(240,53,15, 255);
FloatingText[] gamecombofloatingtext = new FloatingText[256];
int gamecombofloatingtextcount = 0;
public void addFloatingText(FloatingText text2add) {
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
// \u0417\u0432\u0443\u043a\u0438 \u0432\u043d\u0443\u0442\u0440\u0438\u0438\u0433\u0440\u043e\u0432\u044b\u0435.
// \u0417\u0432\u0443\u043a \u043a\u043e\u0433\u0434\u0430 \u043f\u044b\u0442\u0430\u0435\u0448\u0441\u044f \u0441\u043b\u043e\u0436\u0438\u0442\u044c \u043d\u0435\u0432\u0435\u0440\u043d\u0443\u044e \u043a\u043e\u043c\u0431\u0438\u043d\u0430\u0446\u0438\u044e.
AudioPlayer soundWrongChoose;
// \u0417\u0432\u0443\u043a \u043a\u043e\u0433\u0434\u0430 \u0441\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u0435\u0448\u044c \u0432\u0435\u0440\u043d\u0443\u044e \u043a\u043e\u043c\u0431\u0438\u043d\u0430\u0446\u0438\u044e.
AudioPlayer soundGoodChoose;
// \u0417\u0432\u0443\u043a \u043a\u043e\u0433\u0434\u0430 \u0432\u0440\u0435\u043c\u044f \u0432\u044b\u0448\u043b\u043e (\u0435\u0449\u0451 \u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0441\u044f \u043d\u0430 30.04.2011).
AudioPlayer soundTimesup;
// \u0417\u0432\u0443\u043a \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0435\u043c\u044b\u0439 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u043f\u043e\u0431\u0435\u0434\u044b.
AudioPlayer soundVictory;
// \u041c\u044b\u0448\u044c \u0437\u0430\u0436\u0430\u0442\u0430.
boolean mouseLocked = false;
// \u0420\u0430\u0437\u043c\u0435\u0440 \u0438\u0433\u0440\u043e\u0432\u043e\u0433\u043e \u043f\u043e\u043b\u044f \u0432 \u044f\u0447\u0435\u0439\u043a\u0430\u0445 \u043f\u043e \u0433\u043e\u0440\u0438\u0437\u043e\u043d\u0442\u0430\u043b\u0438.
int gamecardNumX = 16;//16
// \u0420\u0430\u0437\u043c\u0435\u0440 \u0438\u0433\u0440\u043e\u0432\u043e\u0433\u043e \u043f\u043e\u043b\u044f \u0432 \u044f\u0447\u0435\u0439\u043a\u0430\u0445 \u043f\u043e \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u0438.
int gamecardNumY = 16;//16
// \u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u044f\u0447\u0435\u0435\u043a \u0432 \u0438\u0433\u0440\u043e\u0432\u043e\u043c \u043f\u043e\u043b\u0435.
int gamecardCurrentNum = gamecardNumX*gamecardNumY;
// \u0412\u043e\u0437\u043c\u043e\u0436\u043d\u043e\u0441\u0442\u044c \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u044f \u043f\u043e\u043b\u044f (\u0435\u0441\u043b\u0438 \u043d\u0435\u043b\u044c\u0437\u044f \u0432 \u0442\u043e\u0447\u043a\u0443 \u0437\u0430\u0433\u0440\u0443\u0436\u0430\u0442\u044c \u043a\u0430\u0440\u0442\u0443 \u043e\u043d\u0430 \u043a\u0430\u043a \u0431\u0443\u0434\u0442\u043e \u0443\u0436\u0435 \u043f\u043e\u0434\u043d\u044f\u0442\u0430).
// \u0417\u0430\u0433\u0440\u0443\u0436\u0430\u0435\u0442\u0441\u044f \u0438\u0437 \u0444\u0430\u0439\u043b\u0430 "GameFieldConfig.gamefield".
boolean[] canBeFilled = new boolean[gamecardNumX*gamecardNumY];
int canBeFilledCellCount = gamecardNumX*gamecardNumY;

// \u0415\u0441\u043b\u0438 \u0441\u043e\u0431\u0440\u0430\u0442\u044c \u043d\u0435\u0441\u043a\u043e\u043b\u044c\u043a\u043e \u043f\u043e\u0434\u0440\u044f\u0434 \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u043c \u043a\u043e\u043c\u0431\u043e \u0431\u043e\u043d\u0443\u0441.
// +1 +2 +3 \u0438 \u0442.\u0434.
final float gamecombotime = 3.0f; // \u0415\u0441\u043b\u0438 \u0431\u043e\u043b\u044c\u0448\u0435 \u043f\u0440\u043e\u0445\u043e\u0434\u0438\u0442 - \u043a\u043e\u043c\u0431\u043e-\u0446\u0435\u043f\u043e\u0447\u043a\u0430 \u043e\u0431\u0440\u044b\u0432\u0430\u0435\u0442\u0441\u044f.
int gamecurrentcombo = 0;
float gamelastcomboframetime = 0;
// \u041a\u043e\u043c\u0431\u043e-\u0431\u043e\u043d\u0443\u0441.
public void gamecomboOnGood() {
  if(gametime - gamelastcomboframetime < gamecombotime) {
    gamestatsGoodNum += gamecurrentcombo;
    if(gamecurrentcombo != 0) {
      int txtcol = gameredcombotextcolor;
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
    // \u041a\u043e\u043c\u0431\u043e-\u0446\u0435\u043f\u043e\u0447\u043a\u0430 \u043e\u043a\u043e\u043d\u0447\u0435\u043d\u0430.
    gamecurrentcombo = 1;
  }
  gamelastcomboframetime = gametime;
}
public void gamecomboOnBad() {
  // \u041a\u043e\u043c\u0431\u043e-\u0446\u0435\u043f\u043e\u0447\u043a\u0430 \u043e\u043a\u043e\u043d\u0447\u0435\u043d\u0430.
  gamecurrentcombo = 1;
}
// \u0412\u044b\u0434\u0435\u043b\u0435\u043d\u0430\u044f \u043a\u0430\u0440\u0442\u0430.
Gamecard gamecardselected = null;
class GamecardType {
  GamecardType(String _name) {
    name = _name;
    String fileToLoad = currentMode+"/"+name+".png";
    img = loadImage(fileToLoad);
    if(null == img) {
      String warningmessage = "\u041d\u0435 \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u043b\u043e\u0441\u044c\"" + fileToLoad + "\"!";
      printlog(warningmessage);
      fileToLoad = "HiraganaCommon/"+name+".png";
      img = loadImage(fileToLoad);
      if(null == img) {
        String errormessage = "\u041d\u0435 \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u043b\u043e\u0441\u044c\"" + fileToLoad + "\"!!";
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
public void gameSetup() {
   printlog("Called gameSetup()");
   
  gamestatsFailedNum = 0;
  gamestatsGoodNum = 0;
  gametime = 0;
  // \u0417\u0430\u043f\u043e\u043b\u043d\u0435\u043d\u043e\u0441\u0442\u044c \u0438\u0433\u0440\u043e\u0432\u043e\u0433\u043e \u043f\u043e\u043b\u044f.
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
      printlog("\u041e\u0448\u0438\u0431\u043a\u0430! \u041d\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u043c \u043f\u043e\u043b\u0435 \u043d\u0435\u0447\u0451\u0442\u043d\u043e\u0435 \u0447\u0438\u0441\u043b\u043e \u043a\u0430\u0440\u0442!!!");
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
  menuBackground = loadImage(currentMode+"/background.jpg");
  gamecardframe = loadImage(currentMode+"/frame.png");
  gamecardselectionframe = loadImage(currentMode+"/selectionframe.png");
  String[] lines = loadStrings(currentMode+"/"+currentMode+".txt");
  gamecardtypes = new GamecardType[PApplet.parseInt(lines[0])];
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
    // \u0412\u0442\u043e\u0440\u0430\u044f \u043a\u0430\u0440\u0442\u0430.
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
// \u0412\u0440\u0435\u043c\u044f \u043f\u0440\u043e\u0448\u0435\u0434\u0448\u0435\u0435 \u0441 \u043d\u0430\u0447\u0430\u043b\u0430 \u0438\u0433\u0440\u044b.
float gametime = 0;
float backgroundPosX = 0, backgroundPosY = 0;
public void game() {
  gametime += frametime;
  // \u041e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435
  final int gamecardNum = gamecardNumX*gamecardNumY; 
  for(int i = 0; i<gamecardNum; i++) {
    if(gamecardgrid[i]!=null && !gamecardgrid[i].deadcard) {
      gamecardgrid[i].update();
    }
  }
  boolean isBackgroundMoving = (playareaWidth!=menuBackground.width||playareaHeight!=menuBackground.height);
  // \u041e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430
  if(isFullScreenRedrawNeeded || isBackgroundMoving) {
    isFullScreenRedrawNeeded = false;
    if(isBackgroundMoving) {
      // maxl=(w-back.w)
      float newBackgroundPosX = mouseX*(width-menuBackground.width)/width;
      backgroundPosX = backgroundPosX*0.8f + newBackgroundPosX*0.2f;
      float newBackgroundPosY = mouseY*(height-menuBackground.height)/height;
      backgroundPosY = backgroundPosY*0.8f + newBackgroundPosY*0.2f;
      image(menuBackground,backgroundPosX,backgroundPosY);
    } else {
      image(menuBackground,0,0);
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
  public void display() {
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
  // \u041d\u0430\u0440\u0438\u0441\u043e\u0432\u0430\u0442\u044c \u0440\u0430\u043c\u043a\u0443.
  public void displayFrame() {
      image( gamecardselectionframe, _drawPosX + (type.img.width-gamecardselectionframe.width)/2, _drawPosY + (type.img.height-gamecardselectionframe.height)/2);
  }
  public void update() {
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
  // \u041f\u043e\u043b\u043e\u0436\u0435\u043d\u0438\u0435 \u043a\u0430\u0440\u0442\u044b \u043d\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u043c \u0441\u0442\u043e\u043b\u0435 (\u0432 \u043c\u0430\u0441\u0441\u0438\u0432\u0435).
  final int index;
  public void onPressed() {
    ResetHelpCountdown();
    //face = !face;
    if(gamecardselected==null)
      gamecardselected = this;
     else {
       if(face != gamecardselected.face) {
         printlog("\u0418\u0433\u0440\u043e\u043a \u0432\u044b\u0431\u0440\u0430\u043b \u043a\u0430\u0440\u0442\u0443 " + gamecardselected.type.name + " \u0438 \u043e\u0431\u044a\u0435\u0434\u0435\u043d\u0438\u043b \u0441 " + type.name);
         printlog(">>Player:" + gamecardselected.type.name + "=>" + type.name);
       }
       if(gamecardselected.type.name == type.name && face != gamecardselected.face) {
         // \u0422\u0440\u0435\u0431\u0443\u0435\u0442\u0441\u044f \u043f\u0435\u0440\u0435\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u0433\u043e \u043f\u043e\u043b\u044f
         isFullScreenRedrawNeeded = true;
         deadcard = true;
         gamecardselected.deadcard = true;
         gamecardselected = null;
         soundGoodChoose.rewind();
         soundGoodChoose.play();
         gamestatsGoodNum++;
         gamecardCurrentNum -= 2;
         // \u041a\u043e\u043c\u0431\u043e-\u0431\u043e\u043d\u0443\u0441.
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
         // \u041a\u043e\u043c\u0431\u043e-\u0446\u0435\u043f\u043e\u0447\u043a\u0430 \u043e\u043a\u043e\u043d\u0447\u0435\u043d\u0430.
         gamecomboOnBad();
       }
     }
  }
  boolean deadcard = false;
  // \u041a\u0430\u043a\u043e\u0439 \u0441\u0442\u043e\u0440\u043e\u043d\u043e\u0439 \u043f\u043e\u0432\u0451\u0440\u043d\u0443\u0442\u0430 \u043a\u0430\u0440\u0442\u0430.
  boolean face;
  public boolean over() {
    return (mouseX>_drawPosX && mouseX<_drawPosX+type.img.width
            &&
           mouseY>_drawPosY && mouseY<_drawPosY+type.img.height);
  }
}

// \u0421\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435, \u0441\u0435\u0439\u0447\u0430\u0441 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0435\u0442\u0441\u044f \u0434\u043b\u044f \u0432\u044b\u0432\u043e\u0434\u0430 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f
// \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0438\u0433\u0440\u044b.
CircleButton message = null;
// \u041a\u043d\u043e\u043f\u043a\u0430 \u043f\u043e \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0443\u0440\u043e\u0432\u043d\u044f \u0434\u043b\u044f \u043f\u0435\u0440\u0435\u0445\u043e\u0434\u0430 \u043a \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u043c\u0443.
ImageButton nextLevelBtn = null;
// \u041a\u043d\u043e\u043f\u043a\u0430 \u043f\u043e \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 \u0443\u0440\u043e\u0432\u043d\u044f \u0434\u043b\u044f \u043f\u0435\u0440\u0435\u0445\u043e\u0434\u0430 \u0432 \u043c\u0435\u043d\u044e.
ImageButton backToMenuBtn = null;

public boolean isEndOfGameReached() {
  return (message != null);
}

public void SetupEndOfGameMenu(int gamestatsGoodNum, int gamestatsFailedNum) {
  String kGood, kBad;
  if(gamestatsGoodNum != 0)
    kBad = "" + (float)gamestatsFailedNum/gamestatsGoodNum;
  else
    kBad = "\u0443\u0436\u0430\u0441\u043d\u043e!";
  if(gamestatsFailedNum != 0)
    kGood = "" + (float)gamestatsGoodNum/gamestatsFailedNum;
  else
    kGood = "\u0432\u0435\u043b\u0438\u043a\u043e\u043b\u0435\u043f\u043d\u043e!";
  message = new CircleButton(width/2,height/2,200,color(100,200,150),color(200,100,26),
  "\u0425\u043e\u0440\u043e\u0448\u043e: " + gamestatsGoodNum + "\n\u041f\u043b\u043e\u0445\u043e: " + gamestatsFailedNum +
    "\n\u041a\u043e\u044d\u0444\u0444\u0438\u0446\u0438\u0435\u043d\u0442 \u0445\u043e\u0440\u043e\u0448\u0435\u0441\u0442\u0438: " + kGood + "\n\u041a\u043e\u044d\u0444\u0444\u0438\u0446\u0438\u0435\u043d\u0442 \u043f\u043b\u043e\u0445\u043e\u0441\u0442\u0438: " + kBad);
  printlog("\u0421\u043e\u0437\u0434\u0430\u044e \u043a\u043d\u043e\u043f\u043a\u0443 \u0434\u043b\u044f \u043f\u0435\u0440\u0435\u0445\u043e\u0434\u0430 \u043a \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u043c\u0443 \u0443\u0440\u043e\u0432\u043d\u044e.");
  nextLevelBtn = new ImageButton(width/2+135,height/2+120,"Menu/NextLevel.png");
  printlog("\u0421\u043e\u0437\u0434\u0430\u044e \u043a\u043d\u043e\u043f\u043a\u0443 \u0434\u043b\u044f \u0432\u044b\u0445\u043e\u0434\u0430 \u0432 \u043c\u0435\u043d\u044e.");
  backToMenuBtn = new ImageButton(width/2,height/2+120,"Menu/BackToMenu.png");
}

public void CloseEndOfGameMenu() {
  
  message.deadElement = true;
  nextLevelBtn.deadElement = true;
  backToMenuBtn.deadElement = true;
  
  message = null;
  nextLevelBtn = null;
  backToMenuBtn = null;
}

public void UpdateEndOfGameMenu() {
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
        // \u0418\u043d\u0430\u0447\u0435 \u043a\u043d\u043e\u043f\u043a\u0443 backToMenuBtn.pressed() \u043f\u043e\u043f\u044b\u0442\u0430\u0435\u0442\u0441\u044f \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u0430\u0442\u044c.
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
      // \u043f\u043e \u043a\u0440\u0443\u0433\u0443:
      //(int)(cos(i*2*3.1415/modeSelectors.length)*250 + width/2),
      //(int)(sin(i*2*3.1415/modeSelectors.length)*250 + height/2),
      x,
      y,
      100,
      color(23,198,44),
      color(243,178,32),
      modes[i]);
      // \u0423\u0440\u043e\u0432\u0435\u043d\u044c \u0443\u0436\u0435 \u0431\u044b\u043b \u043f\u0440\u043e\u0439\u0434\u0435\u043d.
      boolean isLevelCleared = false;
      String[] levelData = loadStrings(userLevelDataFileName+modes[i]);
      if(levelData != null && levelData[0].equals(levelClearedInfoString))
        isLevelCleared = true;
      if(isLevelCleared)
        starsForModeSelectors[i] = new ImageButton(x,y+50,"star.png");
      else  
        starsForModeSelectors[i] = null;
    }
    // \u041a\u043d\u043e\u043f\u043a\u0438 \u0432\u043b\u0435\u0432\u043e \u0438 \u0432\u043f\u0440\u0430\u0432\u043e \u043f\u043e \u0443\u0440\u043e\u0432\u043d\u044f\u043c.
    levelSelectLeftBtn = new ImageButton(  50,  playareaHeight/2,"Menu/LevelSelectLeft.png");
    levelSelectRightBtn = new ImageButton(  playareaWidth-50,  playareaHeight/2,"Menu/LevelSelectRight.png");
  }
  private Button[] modeSelectors = null;
  private Button[] starsForModeSelectors = null;
  // \u041a\u043d\u043e\u043f\u043a\u0438 \u0432\u043b\u0435\u0432\u043e \u0438 \u0432\u043f\u0440\u0430\u0432\u043e \u043f\u043e \u0443\u0440\u043e\u0432\u043d\u044f\u043c.
  private Button levelSelectLeftBtn = null;
  private Button levelSelectRightBtn = null;
  //\u041f\u043e\u043b\u043e\u0436\u0435\u043d\u0438\u0435 \u0443\u0440\u043e\u0432\u043d\u0435\u0439:
  //   o o o o o
  // < o o o o o >
  //   o o o o o
  // \u043d\u0430\u0447\u0438\u043d\u0430\u044f \u0441 \u043d\u0443\u043b\u0435\u0432\u043e\u0433\u043e
  private int index = 0;
  // \u041c\u043e\u0436\u043d\u043e \u043b\u0438 \u0434\u0432\u0438\u043d\u0443\u0442\u044c \u0443\u0440\u043e\u0432\u043d\u0438 \u0432\u043b\u0435\u0432\u043e.
  private boolean canBeMovedLeft() {
    return (index > 0);
  }
  // \u041c\u043e\u0436\u043d\u043e \u043b\u0438 \u0434\u0432\u0438\u043d\u0443\u0442\u044c \u0443\u0440\u043e\u0432\u043d\u0438 \u0432\u043f\u0440\u0430\u0432\u043e.
  private boolean canBeMovedRight() {
    return (index < modeSelectors.length/15);
  }
  // \u0418\u043d\u0434\u0435\u043a\u0441 \u0432\u043b\u0435\u0432\u043e \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u0432\u043b\u0435\u0432\u043e.
  public void indexLeft() {
    if(canBeMovedLeft()) {
      index--;      
      for(int i = 0; i<modeSelectors.length; i++) {
        modeSelectors[i].x += playareaWidth;
        if(starsForModeSelectors[i] != null)
          starsForModeSelectors[i].x += playareaWidth;
      }
    }
  }
  // \u0418\u043d\u0434\u0435\u043a\u0441 \u0432\u043f\u0440\u0430\u0432\u043e \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u0432\u043f\u0440\u0430\u0432\u043e.
  public void indexRight() {
    if(canBeMovedRight()) {
      index++;
      for(int i = 0; i<modeSelectors.length; i++) {
        modeSelectors[i].x -= playareaWidth;
        if(starsForModeSelectors[i] != null)
          starsForModeSelectors[i].x -= playareaWidth;
      }
    }
  }
  // \u041e\u0431\u043d\u043e\u0432\u043b\u044f\u0435\u043c \u0438 \u043e\u0442\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0435\u043c \u043c\u0435\u043d\u044e.
  public void update() {
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
    // \u0417\u0432\u0451\u0437\u0434\u044b \u0437\u0430 \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0438\u0435 \u0443\u0440\u043e\u0432\u043d\u0435\u0439 \u043e\u0431\u043d\u043e\u0432\u043b\u044f\u0435\u043c \u0438 \u043e\u0442\u0440\u0438\u0441\u043e\u0432\u044b\u0432\u0430\u0435\u043c.
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
  // \u0423\u043d\u0438\u0447\u0442\u043e\u0436\u0430\u0435\u043c \u0432\u0441\u0451 \u043b\u0438\u0448\u043d\u0435\u0435 \u043f\u0440\u0438 \u043f\u0435\u0440\u0435\u0445\u043e\u0434\u0435 \u0432 \u0434\u0440\u0443\u0433\u043e\u0439 \u0440\u0435\u0436\u0438\u043c.
  public void destroy() {
    for(int i = 0; i<modeSelectors.length; i++)
      if(modeSelectors[i] != null)
        modeSelectors[i].deadElement = true;
    for(int i = 0; i<starsForModeSelectors.length; i++)
      if(starsForModeSelectors[i] != null)
        starsForModeSelectors[i].deadElement = true;
        
    //    ??????TODO \u043f\u043e\u043c\u0435\u0447\u0430\u0442\u044c, \u0447\u0442\u043e \u043d\u0443\u0436\u043d\u043e \u0438\u0441\u043f\u0440\u0430\u0432\u0438\u0442\u044c, \u0430 \u0442\u043e \u043d\u0435\u043f\u043e\u043d\u044f\u0442\u043d\u043e.
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
// \u041d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u043b\u0438 \u0441\u0435\u0439\u0447\u0430\u0441 \u0438\u0433\u0440\u0430 \u0432 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e (\u0443\u0440\u043e\u0432\u0435\u043d\u044c \u043d\u0435 \u0432\u044b\u0431\u0435\u0440\u0435\u0448\u044c).
boolean isMainMenuSelected = true;
// \u0417\u0430\u0434\u043d\u0438\u0439 \u0444\u043e\u043d \u043c\u0435\u043d\u044e.
PImage menuBackground;
final String copyrightString = "Copyright Ivajkin Timofej \u00a9 2010-2011\nEasyLangGo v.1.006\ntimatnet@gmail.com";
final int copyrightPosX = 10, copyrightPosY = 24;
float copyrightOpacity = 255, copyrightOpacityVel = 0;
public void drawCopyright() {
  fill(231,231,231,copyrightOpacity);
  copyrightOpacity += copyrightOpacityVel;
  copyrightOpacityVel -= 0.002f;
  if(mouseX<200 && mouseY<100 ) {
    copyrightOpacity = 255;
  }
  text(copyrightString, copyrightPosX, copyrightPosY);
}
// \u041a\u043d\u043e\u043f\u043a\u0438 \u043c\u0435\u043d\u044e - \u0432\u044b\u0445\u043e\u0434, \u043d\u0430\u0447\u0430\u0442\u044c \u0438\u0433\u0440\u0443, \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438, \u043d\u0430\u043f\u0438\u0441\u0430\u0442\u044c \u043f\u0438\u0441\u044c\u043c\u043e, \u0433\u0440\u0443\u043f\u043f\u0430 \u0432\u043e \u0432\u043a\u043e\u043d\u0442\u0430\u043a\u0442\u0435, \u0441\u0430\u0439\u0442.
//Button[] menuBtns = new Button[7];
class MainMenuButtonsT {
  private ImageButton StartGame;
  private ImageButton Quit;
  private ImageButton Configure;
  private ImageButton Email;
  private ImageButton SiteLogo;
  private ImageButton VK;
  private PImage RightPanel;
  public void update() {
  }
  public void destroy() {
  }
}
MainMenuButtonsT MainMenuButtons = new MainMenuButtonsT();
PFont menuFont = null;

// \u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 \u0438\u0433\u0440\u043e\u0432\u043e\u0433\u043e \u043c\u0435\u043d\u044e.
public void menuSetup() {
  menuFont = loadFont("TimesNewRomanPS-ItalicMT-18.vlw");
  textFont(menuFont);

  menuBackground = loadImage("background.jpg");
  String[] lines = loadStrings("config.txt");
  modeNum = PApplet.parseInt(lines[0]);
  modes = new String[modeNum];
  for( int i = 0; i<modeNum; i++) {
    modes[i] = lines[i+1];
  }
  modeSelect = new ModeSelect(modes);
  isMenuEnabled = true;
  // TODO \u0413\u043b\u0430\u0432\u043d\u043e\u0435 \u043c\u0435\u043d\u044e, \u043f\u043e\u0445\u043e\u0436\u0435 \u043d\u0430 Angry Birds
  // \u0417\u0430\u0442\u0435\u043c \u0440\u0435\u0436\u0438\u043c \u0442\u0443\u0440\u043d\u0438\u0440\u0430, \u043f\u043e\u043a\u0430\u0437\u0430\u043d\u044b \u0437\u0432\u0451\u0437\u0434\u043e\u0447\u043a\u0438 \u0437\u0430 \u044d\u0442\u0430\u043f, \u043a\u043e\u043b\u043b\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u043e\u0447\u043a\u043e\u0432.
  // \u041d\u0443\u0436\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043b\u043e\u0433\u0438, \u0437\u0430\u043f\u0438\u0441\u044c \u0441\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0438 \u043f\u043e \u0438\u0433\u0440\u0430\u043c, \u0433\u0434\u0435 \u0441\u043a\u043e\u043b\u044c\u043a\u043e \u043e\u0447\u043a\u043e\u0432 \u043d\u0430\u0431\u0438\u0440\u0430\u043b\u0438, \u0441 \u043f\u043e\u043c\u0435\u0442\u043a\u043e\u0439 \u0434\u0430\u0442\u044b.
  MainMenuButtons.StartGame = new ImageButton(  playareaWidth/2,    playareaHeight/2,    "Menu/StartGame.png");
  MainMenuButtons.Quit = new ImageButton(       100,                playareaHeight - 150,"Menu/Quit.png");
  MainMenuButtons.Configure = new ImageButton(  100,  playareaHeight - 250,"Menu/Configure.png");
  MainMenuButtons.RightPanel = loadImage("Menu/RightPanel.png");
  MainMenuButtons.VK = new ImageButton(         playareaWidth-80,  playareaHeight - 200 -1*75,"Menu/VK.png");
  MainMenuButtons.SiteLogo = new ImageButton(   playareaWidth-80,  playareaHeight - 200 -2*75,"Menu/SiteLogo.png");
  MainMenuButtons.Email = new ImageButton(      playareaWidth-80,  playareaHeight - 200 -3*75,"Menu/Email.png");

  MusicBox.playIntro();
}


public void deleteMenu() {
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

// \u0424\u0443\u043d\u043a\u0446\u0438\u044f, \u0432\u044b\u0437\u044b\u0432\u0430\u0435\u043c\u0430\u044f \u0432 \u043a\u0430\u0436\u0434\u043e\u043c \u043a\u0430\u0434\u0440\u0435 \u043f\u0440\u0438 \u0440\u0430\u0431\u043e\u0442\u0435 \u043c\u0435\u043d\u044e.
public void menu() {
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



Minim minim = new Minim(this);
  
class _MusicBox {
  // \u0412\u043a\u043b\u044e\u0447\u0435\u043d\u0430 \u043b\u0438 \u043c\u0443\u0437\u044b\u043a\u0430.
  private boolean isMusicEnabled = true;
  // \u0422\u0435\u043a\u0443\u0449\u0435\u0435 \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0435\u043c\u043e\u0435 \u043f\u0440\u043e\u0438\u0437\u0432\u0435\u0434\u0435\u043d\u0438\u0435.
  private String currentMusicFile = null;
  // \u0417\u0430\u043f\u0443\u0441\u0442\u0438\u0442\u044c \u0437\u0432\u0443\u043a \u043d\u0430 \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u043d\u0438\u0435.
  public void PlayMusic(String file) {
    currentMusicFile = file;
    if(music!=null) {
      music.close();
    }
    music = minim.loadFile(file,2048);
    music.rewind();
    music.play();
  }
  // \u041a\u043e\u043d\u0441\u0442\u0440\u0443\u043a\u0442\u043e\u0440.
  _MusicBox() {
  }
  // \u0442\u0435\u043a\u0443\u0449\u0430\u044f \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0435\u043c\u0430\u044f \u043c\u0443\u0437\u044b\u043a\u0430.
  private AudioPlayer music = null;
  // \u041d\u0430\u0447\u0430\u0442\u044c \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0442\u044c \u0432\u043d\u0443\u0442\u0440\u0438\u0438\u0433\u0440\u043e\u0432\u0443\u044e \u043c\u0443\u0437\u044b\u043a\u0443.
  public void startMainTheme() {
    if(isMusicEnabled) {
      float r = random(100);
      /*if(r>33.33) {
        PlayMusic("sakura.mp3");
      } else if(r>66.66) {
        PlayMusic("sea_fantasy.mp3");
      } else {
        PlayMusic("shamisen.mp3");
      }*/
      if(r>50) {
        PlayMusic("sakura.mp3");
      } else {
        PlayMusic("sea_fantasy.mp3");
      }
    }
  }
  // \u041d\u0430\u0447\u0430\u0442\u044c \u043f\u0440\u043e\u0438\u0433\u0440\u044b\u0432\u0430\u0442\u044c \u043c\u0443\u0437\u044b\u043a\u0443 \u0438\u0437 \u043d\u0430\u0447\u0430\u043b\u044c\u043d\u043e\u0433\u043e \u043c\u0435\u043d\u044e.
  public void playIntro() {
    if(isMusicEnabled) {
      PlayMusic("intro.mp3");
    }
  }
  // \u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0438\u043b\u0438 \u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u043c\u0443\u0437\u044b\u043a\u0443.
  public void setMusicEnabled() {
    isMusicEnabled = !isMusicEnabled;
    if(isMusicEnabled) {
      if(currentMusicFile!=null)
        PlayMusic(currentMusicFile);
    } else {
      music.close();
      music = null;
    }
  }
  private String currentMode;
  public void setCurrenMode(String modPath) {
    currentMode = modPath;
  }
  // \u0417\u0430\u0433\u0440\u0443\u0436\u0430\u0435\u0442 \u0437\u0432\u0443\u043a \u0438\u0437 \u043f\u0430\u043f\u043a\u0438 \u0441 \u0443\u0440\u043e\u0432\u043d\u0435\u043c, \u0435\u0441\u043b\u0438 \u0442\u0430\u043c \u043d\u0435\u0442, \u0442\u043e \u0438\u0437 HiraganaCommon
  public AudioPlayer loadSound(String fileName) {
    String modPath = currentMode;
    AudioPlayer ret = null;
    try {
     ret = minim.loadFile(modPath + "/" + fileName);
    } catch(Throwable nil) {}
    if(ret==null)
      ret = minim.loadFile("HiraganaCommon/"+fileName);
      return ret;
  }
}
_MusicBox MusicBox = new _MusicBox();
  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--hide-stop", "Japanese2Worldwide" });
  }
}
