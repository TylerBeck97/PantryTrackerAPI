/* Seed Role Table */
INSERT INTO ROLE (ID, NAME, UPDATED_BY) VALUES (1, "user", "system");
INSERT INTO ROLE (ID, NAME, UPDATED_BY) VALUES (2, "admin", "system");
INSERT INTO ROLE (ID, NAME, UPDATED_BY) VALUES (3, "system", "system");
SELECT * FROM ROLE;

/* Seed data in the USER table */
/* Need to encrypt passwword with BCrypt using SQL */
INSERT INTO USERS (USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, UPDATED_BY) VALUES ("jebeck", "jebeck-password", "tooeyb6@gmail.com", "9193657265", "system");
INSERT INTO USERS (USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, UPDATED_BY) VALUES ("wdbeck", "wdbeck-password", "w.david.beck@gmail.com", "9192571569", "system");
INSERT INTO USERS (USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, UPDATED_BY) VALUES ("tjbeck", "tjbeck-password", "tylerbec@gmail.com", "9193538780", "system");
SELECT * FROM USERS;

/* Assign users to roles */
INSERT INTO USERS_ROLES (ROLE_ID, USERS_ID, UPDATED_BY) VALUES (1, 1, "system");
INSERT INTO USERS_ROLES (ROLE_ID, USERS_ID, UPDATED_BY) VALUES (2, 1, "SYSTEM");
SELECT * FROM USERS_ROLES;
SELECT ROLE.NAME AS "ROLE", USERS.USERNAME FROM ROLE, USERS, users_roles WHERE users_roles.ROLE_ID = role.ID AND users.ID = users_roles.USERS_ID;