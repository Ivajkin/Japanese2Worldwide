import ddf.minim.*;

Minim minim = new Minim(this);
  
class _MusicBox {
  // Включена ли музыка.
  private boolean isMusicEnabled = true;
  // Текущее проигрываемое произведение.
  private String currentMusicFile = null;
  // Запустить звук на проигрывание.
  void PlayMusic(String file) {
    currentMusicFile = file;
    if(music!=null) {
      music.close();
    }
    music = minim.loadFile(file,2048);
    music.rewind();
    music.play();
  }
  // Конструктор.
  _MusicBox() {
  }
  // текущая проигрываемая музыка.
  private AudioPlayer music = null;
  // Начать проигрывать внутриигровую музыку.
  void startMainTheme() {
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
  // Начать проигрывать музыку из начального меню.
  void playIntro() {
    if(isMusicEnabled) {
      PlayMusic("intro.mp3");
    }
  }
  // Включить или выключить музыку.
  void setMusicEnabled() {
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
  void setCurrenMode(String modPath) {
    currentMode = modPath;
  }
  // Загружает звук из папки с уровнем, если там нет, то из HiraganaCommon
  AudioPlayer loadSound(String fileName) {
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
