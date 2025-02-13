
-- Create the Water Billing System Database
CREATE DATABASE wbs;

USE wbs;

-- Table: Customers
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(15) NOT NULL,
    Address TEXT NOT NULL,
    City VARCHAR(50) NOT NULL,
    State VARCHAR(50) NOT NULL,
    ZipCode VARCHAR(10) NOT NULL,
    ServiceCenter VARCHAR(50),
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: Meters (Tracking Water Usage Per Customer)
CREATE TABLE Meters (
    MeterID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    InstallationDate DATE NOT NULL,
    MeterStatus ENUM('Active', 'Inactive', 'Under Maintenance') DEFAULT 'Active',
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

-- Table: Meter Readings
CREATE TABLE MeterReadings (
    ReadingID INT AUTO_INCREMENT PRIMARY KEY,
    MeterID INT NOT NULL,
    ReadingDate DATE NOT NULL,
    PreviousReading INT NOT NULL,
    CurrentReading INT NOT NULL,
    UnitsConsumed INT GENERATED ALWAYS AS (CurrentReading - PreviousReading) STORED,
    FOREIGN KEY (MeterID) REFERENCES Meters(MeterID) ON DELETE CASCADE
);

-- Table: Bills
CREATE TABLE Bills (
    BillID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    BillDate DATE NOT NULL,
    DueDate DATE NOT NULL,
    UnitsConsumed INT NOT NULL,
    RatePerUnit DECIMAL(5,2) NOT NULL DEFAULT 2.50,
    FixedCharge DECIMAL(10,2) NOT NULL DEFAULT 15.00,
    TotalAmount DECIMAL(10,2) GENERATED ALWAYS AS ((UnitsConsumed * RatePerUnit) + FixedCharge) STORED,
    Status ENUM('Pending', 'Paid', 'Overdue') DEFAULT 'Pending',
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

-- Table: Payments
CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BillID INT NOT NULL,
    CustomerID INT NOT NULL,
    PaymentDate DATE NOT NULL,
    PaymentAmount DECIMAL(10,2) NOT NULL,
    PaymentMethod ENUM('Cash', 'Credit Card', 'Bank Transfer') NOT NULL,
    FOREIGN KEY (BillID) REFERENCES Bills(BillID) ON DELETE CASCADE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

-- Table: Admin Users (For System Management)
CREATE TABLE AdminUsers (
    AdminID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'Staff') NOT NULL DEFAULT 'Staff',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- Insert Customers
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address, City, State, ZipCode, ServiceCenter)
VALUES 
('John', 'Doe', 'johndoe@email.com', '123-456-7890', '123 Main St', 'Toronto', 'ON', 'M1A 2B3', 'Downtown Center'),
('John', 'Wick', 'johnwick@email.com', '987-654-3210', '456 Elm St', 'Vancouver', 'BC', 'V5K 1Z3', 'West Coast Center'),
('Christian', 'Ronaldo', 'christian.ronaldo@email.com', '555-123-4567', '789 Oak St', 'London', 'UK', 'UUK 4L5', 'East Side Center');

-- Insert Meters
INSERT INTO Meters (CustomerID, InstallationDate, MeterStatus)
VALUES 
(1, '2023-01-10', 'Active'),
(2, '2023-02-15', 'Active'),
(3, '2023-03-20', 'Active');

-- Insert Meter Readings
INSERT INTO MeterReadings (MeterID, ReadingDate, PreviousReading, CurrentReading)
VALUES 
(1, '2024-07-01', 1000, 1100),
(2, '2024-07-01', 1200, 1250),
(3, '2024-07-01', 900, 950);

-- Insert Bills (Rates: $2.50 per unit + $15 Fixed Charge)
INSERT INTO Bills (CustomerID, BillDate, DueDate, UnitsConsumed, RatePerUnit, FixedCharge, Status)
VALUES 
(1, '2024-07-05', '2024-07-20', 100, 2.50, 15.00, 'Pending'),
(2, '2024-07-05', '2024-07-20', 50, 2.50, 15.00, 'Paid'),
(3, '2024-07-05', '2024-07-20', 50, 2.50, 15.00, 'Pending'),
(3, '2024-07-05', '2024-07-20', 50, 2.50, 15.00, 'Paid'),
(2, '2024-07-05', '2024-07-20', 50, 2.50, 15.00, 'Pending'),
(1, '2024-07-05', '2024-07-20', 100, 2.50, 15.00, 'Paid'),
(2, '2024-07-05', '2024-07-20', 50, 2.50, 15.00, 'Paid');

-- Insert Payments
INSERT INTO Payments (BillID, CustomerID, PaymentDate, PaymentAmount, PaymentMethod)
VALUES 
(1, 1, '2024-07-10', 265.00, 'Credit Card'),
(2, 2, '2024-07-12', 140.00, 'Bank Transfer');

-- Insert Admin Users
INSERT INTO AdminUsers (Username, Password, Role)
VALUES 
('admin', '1234', 'Admin'),
('staff', '1234', 'Staff');


-- SELECT * FROM CUSTOMERS;

-- SELECT b.CustomerID, CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, b.BillDate, b.DueDate, b.UnitsConsumed, b.RatePerUnit, b.FixedCharge, b.Status FROM Bills b JOIN Customers c ON b.CustomerID = c.CustomerID;

-- SELECT * FROM Bills;

-- SELECT COUNT(*) FROM Customers;

-- UPDATE Bills SET BillDate = '2024-08-01', DueDate = '2024-08-15' WHERE BillID = 1;