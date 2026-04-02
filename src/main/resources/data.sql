-- create Users
INSERT INTO users (name, email, created_date, modified_date)
VALUES ('Aarav Sharma', 'aarav.sharma@example.com', NOW(), NOW());

INSERT INTO users (name, email, created_date, modified_date)
VALUES ('Priya Patel', 'priya.patel@example.com', NOW(), NOW());

-- create Inventory
INSERT INTO inventory (kickston_id, device_username, device_password, manufacture_factory_place, created_date, modified_date)
VALUES ('000001', 'admin', 'pass123', 'China Hub 1', NOW(), NOW());

INSERT INTO inventory (kickston_id, device_username, device_password, manufacture_factory_place, created_date, modified_date)
VALUES ('a1b2c3', 'user', 'secure456', 'India Hub 1', NOW(), NOW());

-- create a house
INSERT INTO house (name, address, admin_user_id, created_date, modified_date)
VALUES ('Sharma Villa', '42 MG Road, Bengaluru', 1, NOW(), NOW());

-- link the user to the house
INSERT INTO house_members (house_id, user_id) VALUES (1, 1);

-- create a room
INSERT INTO room (name, house_id, created_date, modified_date)
VALUES ('Living Room', 1, NOW(), NOW());