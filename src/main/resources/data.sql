-- Create Pincode Table
CREATE TABLE Pincode (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         pincode INT NOT NULL,
                         countryCode VARCHAR(10),
                         latitude FLOAT,
                         longitude FLOAT
);

-- Create Weather Table
CREATE TABLE Weather (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         pincode_id INT,
                         weather_date DATE,
                         temperature FLOAT,
                         description VARCHAR(255),
                         FOREIGN KEY (pincode_id) REFERENCES Pincode(id)
);


INSERT INTO Pincode (id, pincode, latitude, longitude) VALUES (1, '411014', 18.5204, 73.8567);
INSERT INTO weather (id, pincode_id, weather_date, temperature, description) VALUES (1, 1, '2020-10-15', 30.5, 'Clear Sky');
