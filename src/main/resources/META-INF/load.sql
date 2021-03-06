SET SCHEMA LIBRARY;
INSERT INTO BOOK (ID, NAME, CREATED_AT, UPDATED_AT) VALUES (BOOK_SEQUENCE.nextval, 'book_1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, CREATED_AT, UPDATED_AT) VALUES (USER_SEQUENCE.nextval, 'admin', 'admin','Admin', 'Herzog', CONCAT(CONCAT('herzog.admin', USER_SEQUENCE.currval),'@gmail.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO LIBRARY (ID, NAME, USER_ID, CREATED_AT, UPDATED_AT) VALUES (LIBRARY_SEQUENCE.nextval, 'library_admin', USER_SEQUENCE.currval, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES (USER_SEQUENCE.currval, 'ADMIN');
INSERT INTO LIBRARY_BOOKS (BOOK_ID, LIBRARY_ID) VALUES (BOOK_SEQUENCE.currval, LIBRARY_SEQUENCE.currval);
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, CREATED_AT, UPDATED_AT) VALUES (USER_SEQUENCE.nextval, 'user', 'user', 'User', 'Herzog', CONCAT(CONCAT('herzog.user', USER_SEQUENCE.currval),'@gmail.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO LIBRARY (ID, NAME, USER_ID, CREATED_AT, UPDATED_AT) VALUES (LIBRARY_SEQUENCE.nextval, 'library_user', USER_SEQUENCE.currval, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES (USER_SEQUENCE.currval, 'USER');
INSERT INTO LIBRARY_BOOKS (BOOK_ID, LIBRARY_ID) VALUES (BOOK_SEQUENCE.currval, LIBRARY_SEQUENCE.currval);
INSERT INTO BOOK (ID, NAME, CREATED_AT, UPDATED_AT) VALUES (BOOK_SEQUENCE.nextval, 'book_2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO BOOK (ID, NAME, CREATED_AT, UPDATED_AT) VALUES (BOOK_SEQUENCE.nextval, 'book_3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO BOOK (ID, NAME, CREATED_AT, UPDATED_AT) VALUES (BOOK_SEQUENCE.nextval, 'book_4', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());