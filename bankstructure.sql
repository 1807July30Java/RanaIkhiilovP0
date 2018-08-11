
-- ACCOUNT CREATION
CREATE TABLE USER_ACCOUNT(
    USER_ACCOUNT_ID INT PRIMARY KEY,
    USERNAME VARCHAR2(15),
    PASSWORD VARCHAR2(15),
    USER_ACCOUNT_SUPER NUMBER (1,0) DEFAULT 0 CHECK (USER_ACCOUNT_SUPER = 0 OR USER_ACCOUNT_SUPER = 1)
);
/


-- create admin
INSERT INTO USER_ACCOUNT
VALUES (0, 'admin', 'p4ssw0rd', 1);
/

-- BANK ACOUNT CREATION
CREATE TABLE BANK_ACCOUNT(
    BANK_ACCOUNT_ID INT PRIMARY KEY,
    USER_ACCOUNT_ID INT,
    CURRENT_BALANCE NUMBER (20,2) DEFAULT 0
);
/

--ADD FOREIGN KEY REFERENCE
ALTER TABLE BANK_ACCOUNT
ADD FOREIGN KEY (USER_ACCOUNT_ID) REFERENCES USER_ACCOUNT(USER_ACCOUNT_ID);
/

--CREATE TRANSACTION TABLE

CREATE TABLE TRANSACTION(
    TRANSACTION_ID INT PRIMARY KEY,
    BANK_ACCOUNT_ID INT,
    TRANSACTION_DATE DATE,
    PREVIOUS_BALANCE NUMBER (20,2),
    NEW_BALANCE NUMBER (20,2),
    TRANSACTION_VALUE NUMBER (20,2),
    TRANSACTION_TYPE VARCHAR2(1)
);
/
--ADD FOREIGN KEY REFERENCE
ALTER TABLE TRANSACTION
ADD FOREIGN KEY (BANK_ACCOUNT_ID) REFERENCES BANK_ACCOUNT(BANK_ACCOUNT_ID);
/
    
--CREATE SEQUENCES 

CREATE SEQUENCE SQ_USER_ACCOUNT_PK
START WITH 1
INCREMENT BY 1;
/
CREATE SEQUENCE SQ_BANK_ACCOUNT_PK
START WITH 1
INCREMENT BY 1;
/
CREATE SEQUENCE SQ_TRANSACTION_PK
START WITH 1
INCREMENT BY 1;
/

--TRIGGERS
CREATE TRIGGER TR_INSERT_USER_ACCOUNT
BEFORE INSERT ON USER_ACCOUNT
FOR EACH ROW
BEGIN
    SELECT SQ_USER_ACCOUNT_PK.NEXTVAL INTO :NEW.USER_ACCOUNT_ID FROM DUAL;
END;
/

CREATE TRIGGER TR_INSERT_BANK_ACCOUNT
BEFORE INSERT ON BANK_ACCOUNT
FOR EACH ROW
BEGIN
    SELECT SQ_BANK_ACCOUNT_PK.NEXTVAL INTO :NEW.BANK_ACCOUNT_ID FROM DUAL;
END;
/

CREATE TRIGGER TR_INSERT_TRANSACTION
BEFORE INSERT ON TRANSACTION
FOR EACH ROW
BEGIN
    SELECT SQ_TRANSACTION_PK.NEXTVAL INTO :NEW.TRANSACTION_ID FROM DUAL;
END;
/  
    
INSERT INTO USER_ACCOUNT(USERNAME, PASSWORD) VALUES ('TEST2', '1234');
INSERT INTO BANK_ACCOUNT(USER_ACCOUNT_ID) VALUES (21);
INSERT INTO TRANSACTION( BANK_ACCOUNT_ID,TRANSACTION_DATE, PREVIOUS_BALANCE,NEW_BALANCE, TRANSACTION_VALUE,TRANSACTION_TYPE) 
VALUES (1, TO_DATE('1999-08-08 00:00:00','yyyy-mm-dd hh24:mi:ss') , 10, 30, 20, 'D');

SELECT * FROM TRANSACTION B
INNER JOIN BANK_ACCOUNT A 
ON B.BANK_ACCOUNT_ID=A.BANK_ACCOUNT_ID;


SELECT * FROM BANK_ACCOUNT b INNER JOIN USER_ACCOUNT u ON b.USER_ACCOUNT_ID = u.USER_ACCOUNT_ID where b.USER_ACCOUNT_ID = 22;

CREATE OR REPLACE PROCEDURE SP_DEPOSIT(B_ID IN NUMBER, V_ID IN NUMBER)
IS
BEGIN
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    --CHECK THAT THE THIS BEAR IS CORRECTLY MATCHED TO THIS BEEHIVE
    UPDATE BANK_ACCOUNT 
    SET CURRENT_BALANCE = CURRENT_BALANCE + V_ID
    WHERE BANK_ACCOUNT_ID = B_ID;
     --DBMS_OUTPUT.PUT_LINE('SUCCESS DEPOSIT');
    COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
        --DBMS_OUTPUT.PUT_LINE('FAILED TO DEPOSIT');
        ROLLBACK;
END;

BEGIN
SP_DEPOSIT(2,10);
END;


CREATE OR REPLACE PROCEDURE SP_WITHDRAW(B_ID IN NUMBER, V_ID IN NUMBER)
IS
BEGIN
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    --CHECK THAT THE THIS BEAR IS CORRECTLY MATCHED TO THIS BEEHIVE
    UPDATE BANK_ACCOUNT 
    SET CURRENT_BALANCE = CURRENT_BALANCE - V_ID
    WHERE BANK_ACCOUNT_ID = B_ID;
     
    IF CURRENT_BALANCE > 0 THEN
        DBMS_OUTPUT.PUT_LINE('SUCCESS WITHDRAW' || CURRENT_BALANCE);
        COMMIT;
        ELSE 
        DBMS_OUTPUT.PUT_LINE('UNSUCCESSFUL WITHDRAW' ||CURRENT_BALANCE);
            ROLLBACK;
     END IF;
    
END;


Begin
SP_Withdraw(3, 10);
end;

