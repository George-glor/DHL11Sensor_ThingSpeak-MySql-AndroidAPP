IoT Sensor Data Logger

Overview
The IoT Sensor Data Logger is a project that involves collecting data from a DHT11 temperature and humidity sensor and uploading that data to both ThingSpeak and a MySQL database. This repository contains the Arduino code for the sensor, and a Python script for collecting and storing the data in a MySQL database.

Features
Collects temperature and humidity data from a DHT11 sensor.
Sends data to ThingSpeak for real-time monitoring.
Stores data in a MySQL database for historical analysis.
Usage
Arduino Code (Sensor)
Configure your Wi-Fi network by modifying the ssid and password variables in the Arduino code.

Update the apiKey and channelID variables with your ThingSpeak API key and channel ID.

Upload the Arduino code to your microcontroller (e.g., ESP8266, ESP32).

The sensor will start collecting data and sending it to ThingSpeak.

Python Script (Database)
Make sure you have Python installed on your system.

Install the required Python libraries by running the following command:

Copy code
pip install requests mysql-connector-python
Modify the thingspeak_url and db_config variables in the Python script with your ThingSpeak channel URL and MySQL database configuration.

Run the Python script to collect data from ThingSpeak and store it in the MySQL database:

Copy code
python data_logger.py
Database Schema
The MySQL database contains a table named sensor_data with the following structure:

entry_id (Primary Key, Auto-increment): Unique identifier for each data entry.
field1 (FLOAT): Temperature data.
field2 (FLOAT): Humidity data.
created_at (DATETIME): Timestamp of when the data was inserted into the database.
Contributing
We welcome contributions to improve this project. Please follow these guidelines when contributing:

Fork the repository.
Create a new branch for your feature or bug fix.
Make your changes and test them.
Submit a pull request with a clear description of your changes.
License
This project is licensed under the MIT License. See the LICENSE file for more details.

Contact
If you have any questions or feedback, feel free to reach out to George at georgeglor40@hotmail.com.
