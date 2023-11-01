import requests
import mysql.connector

# ThingSpeak configuration
thingspeak_url = "https://api.thingspeak.com/channels/2320519/feeds.json?api_key=R8R0DO6QOJYY4M9A"

# MySQL database configuration
db_config = {
    'host': 'localhost',
    'user': 'new7',
    'password': 'sqlserverpassword',
    'database': 'sensor2'
}

try:
    response = requests.get(thingspeak_url)
    data = response.json()

    data_points = data['feeds']

    connection = mysql.connector.connect(**db_config)
    cursor = connection.cursor()

    create_table_query = """
    CREATE TABLE IF NOT EXISTS sensor_data (
        entry_id INT AUTO_INCREMENT PRIMARY KEY,
        field1 FLOAT,
        field2 FLOAT,
        created_at DATETIME
    )
    """
    cursor.execute(create_table_query)

    insert_query = """
    INSERT INTO sensor_data (field1, field2, created_at) VALUES (%s, %s, NOW())
    """
    for point in data_points:
        field1 = point['field1']
        field2 = point['field2']
        cursor.execute(insert_query, (field1, field2))

    connection.commit()

except Exception as e:
    print(f"Error: {e}")

finally:
    cursor.close()
    connection.close()
