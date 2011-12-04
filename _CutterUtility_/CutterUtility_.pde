// CutterUtility~ - вспомогательная программа для разрезания
// большой таблички с катаканой на маленькие png файлики

//size(1024,1024);

PImage orig = loadImage("_japanese-hiragana-chart.gif");

PImage mini = null;//createImage(32,32,ARGB);
final int symbsize = 33;
size(symbsize,symbsize);

String[] nameK = {"","k","s","t","n","h","m","y","r","w","n"};
String[] nameA = {"a","i","u","e","o"};

final int startX = 288;
final int startY = 33;
final float rangeX = 45.3;
final float rangeY = 50.3;
for(int col = 0; col<5; col++) {
  for(int row = 0; row<11; row++) {
    int cutStartX = int(startX+col*rangeX);
    int cutStartY = int(startY+row*rangeY);
    mini = createImage(symbsize,symbsize,ARGB);
    for(int i = 0; i<symbsize; i++) {
      for(int j = 0; j<symbsize; j++) {
        mini.pixels[i+j*symbsize] = orig.pixels[cutStartX+i+(j+cutStartY)*orig.width];
        if(i==0||j==0||i==symbsize-1||j==symbsize-1)
          mini.pixels[i+j*symbsize] = color(0,0,0,255);
      }
    }
    image(mini,0,0);//cutStartX,cutStartY);
    String name = "kana-"+nameK[row]+nameA[col]+".png";
    save(name);
  }
}
exit();
