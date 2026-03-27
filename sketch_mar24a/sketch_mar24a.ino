#include <WiFi.h>
#include <WiFiClientSecure.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include "DHT.h"
/* ========= WIFI ========= */
const char* ssid = "TP-LINK_C34A";
const char* password = "75263638";
/* ========= MQTT (HiveMQ Cloud) ========= */
const char* mqtt_server   = "b069ed9f4ad24e53ab13d415211da7bb.s1.eu.hivemq.cloud";
const int   mqtt_port     = 8883;
const char* mqtt_username = "admin";
const char* mqtt_password = "Thuan2004";
/* ========= OBJECT ========= */
WiFiClientSecure espClient;
PubSubClient client(espClient);
/* ========= PIN ========= */
#define DHTPIN   21
#define DHTTYPE DHT11
#define LDR_PIN  36
#define LED1 25
#define LED2 26
#define LED3 27
bool led1State = false;
bool led2State = false;
bool led3State = false;
DHT dht(DHTPIN, DHTTYPE);
/* ========= WIFI CONNECT ========= */
void setup_wifi() {
    Serial.print("Connecting WiFi");
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");

  }
    Serial.println("\nWiFi connected");
}
void setLed(uint8_t pin, bool& ledState, bool newState) {
      if (ledState == newState) {
            return;

  }
      if (newState == true) {
           digitalWrite(pin, HIGH);

  }
  else {
            digitalWrite(pin, LOW);

  }
      ledState = newState;
}
void publishLedState(const char* ledName, bool state) {
    StaticJsonDocument<128> doc;

    doc["led"] = ledName;
    doc["state"] = state ? "ON" : "OFF";

    char buf[128];
    serializeJson(doc, buf);

    client.publish("device/state", buf);

    Serial.println("Published LED state:");
    Serial.println(buf);
}

/* ========= MQTT CALLBACK ========= */
void callback(char* topic, byte* payload, unsigned int length) {
    String msg;
    for (unsigned int i = 0; i < length; i++) {
        msg += (char)payload[i];

  }
    Serial.println("Message arrived:");
    Serial.println(msg);
    StaticJsonDocument<200> doc;
    DeserializationError error = deserializeJson(doc, msg);
    if (error) {
        Serial.println("JSON parse failed");
        return;

  }
    const char* led   = doc["led"];
    const char* state = doc["state"];
    if (!led || !state) return;
    bool newState = (strcmp(state, "ON") == 0);
    if (strcmp(led, "led1") == 0) {
        setLed(LED1, led1State, newState);
        publishLedState("led1", newState);
    }
    else if (strcmp(led, "led2") == 0) {
        setLed(LED2, led2State, newState);
        publishLedState("led2", newState);
    }
    else if (strcmp(led, "led3") == 0) {
        setLed(LED3, led3State, newState);
        publishLedState("led3", newState);
    }
}
/* ========= MQTT RECONNECT ========= */
void reconnect() {
    while (!client.connected()) {
        Serial.print("Connecting MQTT...");
        if (client.connect("ESP32Client", mqtt_username, mqtt_password)) {
            Serial.println("connected");
            client.subscribe("device/control");

            StaticJsonDocument<128> doc;
            doc["device"] = "esp32";
            doc["status"] = "online";

            char buf[128];
            serializeJson(doc, buf);

            client.publish("esp32/status", buf);

            Serial.println("Published startup status:");
            Serial.println(buf);

    }
    else {
            Serial.print("failed, rc=");
            Serial.println(client.state());
            delay(3000);

    }

  }
}
/* ========= SETUP ========= */
void setup() {
    Serial.begin(115200);
    pinMode(LED1, OUTPUT);
    pinMode(LED2, OUTPUT);
    pinMode(LED3, OUTPUT);
    dht.begin();
    setup_wifi();
    espClient.setInsecure();
    client.setServer(mqtt_server, mqtt_port);
    client.setCallback(callback);
}
/* ========= LOOP ========= */
void loop() {
    if (!client.connected()) {
        reconnect();

  }
    client.loop();
    static unsigned long lastMsg = 0;
    if (millis() - lastMsg > 2000) {
        lastMsg = millis();
        float h = dht.readHumidity();
        float t = dht.readTemperature();
        int light = 4096 - analogRead(LDR_PIN);

        StaticJsonDocument<256> doc;

        // Validate DHT11
        bool dhtOk = !isnan(t) && !isnan(h) && t > 0 && t < 80 && h > 0 && h <= 100;
        if (dhtOk) {
            doc["temperature"] = t;
            doc["humidity"]    = h;
        } else {
            doc["temperature"] = nullptr;
            doc["humidity"]    = nullptr;
        }

        // Validate LDR (pin floating → đọc 0 hoặc 4095)
        bool ldrOk = (light > 0 && light < 4095);
        if (ldrOk) {
            doc["light"] = light;
        } else {
            doc["light"] = nullptr;
        }

        char buffer[256];
        serializeJson(doc, buffer);
        client.publish("iot/sensor/data", buffer);
        Serial.print("Published sensor data:");
        Serial.println(buffer);

  }
}
