/* Ardiono code to change lights based on sentiment analysis from twitter */
 #include <Adafruit_NeoPixel.h>
#define PIN 6
Adafruit_NeoPixel strip = Adafruit_NeoPixel(30, PIN, NEO_GRB + NEO_KHZ800);

int reds=255;
int greens = 127;

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);
  strip.begin();
  color();
}

void loop() {
  // put your main code here, to run repeatedly:

  if(Serial.available() > 0){
   char data = Serial.read(); 
   if(data == '0'){
     reds-=60;
     greens+=60;
     if(reds < 0){
      reds = 0; 
     }
     if(greens >255){  
       greens = 255;
     }
   }
   else{
    greens-=60;    
    reds+=60;
    if(greens < 0 ){
       greens = 0; 
    }
    if(reds > 255){
     reds = 255; 
    }
   }
   color();
  }
  
}

void color() {
  
 
  for(uint16_t i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(reds, greens, 0));      
  } 
  for(int i = 1; i < 256; i ++){
   strip.setBrightness(i);
   strip.show();
   delay(2);
  }  
  for(int i = 255; i >0; i --){
   strip.setBrightness(i);
   strip.show();
   delay(2);
  }
}

