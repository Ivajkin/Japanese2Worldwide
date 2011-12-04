// Отрисовываемые объекты.
IDrawable[] drawableObjects = null;
final int drawableObjectsInitialContainerSize = 128;
// Настоящее количество объектов.
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
  // Отображение объекта.
  void display(){};
  boolean deadElement = false;
}
// Отрисовка всех IDrawables.
void displayDrawables() {
  for(int i = 0; i<drawableObjectsCount; i++) {
    if(drawableObjects[i] != null) {
      if(drawableObjects[i].deadElement)
        drawableObjects[i] = null;
      else
        drawableObjects[i].display();
    }
  }
}

// Кнопка
class Button extends IDrawable
{
  int x, y;
  public int getX() {return x;}
  public int getY() {return y;}
  color basecolor, highlightcolor;
  color currentcolor;
  boolean over = false;
  boolean pressed = false;   
  boolean wasPressed = false;
  
  void update() 
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
  
  boolean pressed() 
  {
    boolean ret = wasPressed;
    wasPressed = false;
    return ret;
  }

  boolean over() 
  { 
    return true; 
  }

  boolean overRect(int x, int y, int width, int height) 
  {
    if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
      return true;
    } 
    else {
      return false;
    }
  }

  boolean overCircle(int x, int y, int diameter) 
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
  CircleButton(int ix, int iy, int isize, color icolor, color ihighlight, String ibuttontext) 
  {
    x = ix;
    y = iy;
    size = isize;
    basecolor = icolor;
    highlightcolor = ihighlight;
    currentcolor = basecolor;
    buttontext = ibuttontext;
  }

  boolean over() 
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

  void display() 
  {
    stroke(255);
    fill(currentcolor);
    ellipse(x, y, size, size);
    
    fill(255);
    text(buttontext,x-size/2.5,y);
    fill(0);
    text(buttontext,x-size/2.5+1,y+1);
    
    if(over)
    lifetime += frametime;
    ellipse(cos(lifetime*2)*size/2+x, sin(lifetime*2)*size/2+y, size*0.1, size*0.1);
  }
  float lifetime = 0;
  
  String buttontext;
}

class ImageButton extends Button
{ 
  PImage buttonImage;
  final float normalScale = 0.85;
  final float selectedScale = 1;
  float scale = normalScale;
  ImageButton(int ix, int iy, String buttonImagePath) 
  {
    x = ix;
    y = iy;
    this.buttonImage = requestImage(buttonImagePath);//loadImage(buttonImagePath);
  }

  boolean over() 
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

  void display() 
  {
    image(buttonImage,x-scale*buttonImage.width/2, y-scale*buttonImage.height/2,scale*buttonImage.width, scale*buttonImage.height);
    if(over) {
      scale = min(scale+0.05,selectedScale);
    } else {
      scale = max(scale-0.05,normalScale);
    }
  }
}

