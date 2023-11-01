#include <WiFiS3.h>  
#include <DHT.h>
#include "ThingSpeak.h"

const char* ssid = "iPhone (2)";    
const char* password = "987654321"; 

#define DHTPIN 4
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

char apiKey[] = "R8R0DO6QOJYY4M9A"; 
unsigned long channelID = 2320519;   

const char* server = "api.thingspeak.com";

WiFiClient client;

void setup() {
  Serial.begin(115200);
  connectToWiFi();
  dht.begin();
  ThingSpeak.begin(client);
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    delay(2000);
    return;
  }

  Serial.print("Temperature = ");
  Serial.print(t);
  Serial.println(" °C");
  Serial.print("Humidity    = ");
  Serial.print(h);
  Serial.println(" %");

  // Send data to ThingSpeak
  ThingSpeak.setField(1, t);
  ThingSpeak.setField(2, h);

  int statusCode = ThingSpeak.writeFields(channelID, apiKey);

  if (statusCode == 200) {
    Serial.println("Data sent to ThingSpeak successfully.");
  } else {
    Serial.print("No error sending data to ThingSpeak. HTTP error code: ");
    Serial.println(statusCode);
  }

  delay(20000);
}

void connectToWiFi() {
  Serial.println("Connecting to WiFi...");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");
}
