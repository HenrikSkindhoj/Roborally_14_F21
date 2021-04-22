/* Need to switch of FK check for MySQL since there are crosswise FK references */
CREATE SCHEMA IF NOT EXISTS pisu; 
USE pisu;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS Game (
  gameID int NOT NULL UNIQUE AUTO_INCREMENT,
  
  name varchar(255),

  phase tinyint,
  step tinyint,
  currentPlayer tinyint NULL,
  boardName varchar(45),
  
  PRIMARY KEY (gameID),
  FOREIGN KEY (gameID, currentPlayer) REFERENCES Player(gameID, playerID)
);
  
CREATE TABLE IF NOT EXISTS Player (
  gameID int NOT NULL,
  playerID tinyint NOT NULL,

  name varchar(255),
  colour varchar(31),
  
  positionX int,
  positionY int,
  heading tinyint,
  
  
  PRIMARY KEY (gameID, playerID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

CREATE TABLE IF NOT EXISTS Laser(
  gameID int NOT NULL,
  laserID int NOT NULL,

  positionX int,
  positionY int,
  heading tinyint,

  PRIMARY KEY (gameID, laserID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

CREATE TABLE IF NOT EXISTS Wall(
  gameID int NOT NULL,
  wallID int NOT NULL,

  positionX int,
  positionY int,
  heading tinyint,

  PRIMARY KEY (gameID, wallID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

CREATE TABLE IF NOT EXISTS Checkpoint(
  gameID int NOT NULL,
  checkpointID int NOT NULL,

  positionX int,
  positionY int,

  PRIMARY KEY (gameID, checkpointID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

CREATE TABLE IF NOT EXISTS Register(
gameID int NOT NULL,
playerID tinyint NOT NULL,

pCard1 varchar(45),
pCard2 varchar(45),
pCard3 varchar(45),
pCard4 varchar(45),
pCard5 varchar(45),
    
cCard1 varchar(45),
cCard2 varchar(45),
cCard3 varchar(45),
cCard4 varchar(45),
cCard5 varchar(45),
cCard6 varchar(45),
cCard7 varchar(45),
cCard8 varchar(45),

PRIMARY KEY (gameID, playerID),
FOREIGN KEY (gameID, playerID) REFERENCES Player(gameID, playerID)
);

SET FOREIGN_KEY_CHECKS = 1;