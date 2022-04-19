@tag
Feature:

  Scenario Outline: Player inputs a name
    Given a player opened the application
    When the player inputs a name "<name>"
    Then the player has the name "<name>"
    And there is a new record in the collection user with username "<name>"
    Examples:
      | name    |
      | Anna    |
      | Raul    |
      | Durdija |
      | Simona  |


  Scenario Outline: Player chooses a robot character
    Given a player has a name "<name>"
    When the player chooses a robot "<robot-name>"
    Then "<robot-name>" is assigned to this player
    And there is a new record in the collection user with username "<name>" and robotname "<robot-name>"
    Examples:
      | name   | robot-name |
      | Ion    | SQUASH_BOT |
      | Jianan | ZOOM_BOT   |
      | Wenjie | HAMMER_BOT |
      | Alien  | SPIN_BOT   |


  Scenario Outline: As a player I want to create a room
    Given a player has a name "<name>"
    When the player creates a new room and chooses a map "<map_name>"
    Then there is a new room record in the collection room
    Examples:
      | name   | map_name     |
      | Ion    | STARTER      |
      | Jianan | BEGINNER     |
      | Wenjie | INTERMEDIATE |


  Scenario Outline: As a player I want to join a room
    Given a player has a name "<name>"
    * a room owner "<room_owner_name>" creates a new room with map "<map_name>"
    When the player gets the room number from room owner and join this room
    Then the player is in this room
    Examples:
      | name   | room_owner_name | map_name |
      | Anna   | Jianan          | STARTER  |
      | Raul   | Simona          | BEGINNER |
      | Durjia | Anna            | ADVANCED |

  Scenario Outline: As a room owner, I must generate all the participants' robots' initial positions and upload them to server.
    Given a room owner "<room_owner_name>" creates a new room with map "<map_name>" and chose robot "<room_owner_robot>"
    And player1 "<player1_name>" player2 "<player2_name>" and player3 "<player3_name>" chose robot1 "<player1_robot>" robot2 "<player2_robot>" and robot3 "<player3_robot>" respectively
    And player1 player2 and player3 joint this room
    When the game starts and room owner pulls all information from server
    Then the client of room owner generates all the initial positions and upload them to server
    Examples:
      | room_owner_name | map_name | room_owner_robot | player1_name | player2_name | player3_name | player1_robot | player2_robot | player3_robot |
      | Wenjie          | STARTER  | SQUASH_BOT       | Jianan       | Ion          | Durjia       | ZOOM_BOT      | HAMMER_BOT    | SPIN_BOT      |

  Scenario Outline: As a participant of a room, I must pull the initial position of my robot from server.
    Given a room owner "<room_owner_name>" creates a new room with map "<map_name>" and chose robot "<room_owner_robot>"
    And player1 "<player1_name>" player2 "<player2_name>" and player3 "<player3_name>" chose robot1 "<player1_robot>" robot2 "<player2_robot>" and robot3 "<player3_robot>" respectively
    And player1 player2 and player3 joint this room
    And  the game starts and room owner pulls all information from server
    When the client of room owner generates all the initial positions and upload them to server
    Then player1 player2 and player3 successfully get their robot info from server
    Examples:
      | room_owner_name | map_name | room_owner_robot | player1_name | player2_name | player3_name | player1_robot | player2_robot | player3_robot |
      | Wenjie          | STARTER  | SQUASH_BOT       | Jianan       | Ion          | Durjia       | ZOOM_BOT      | HAMMER_BOT    | SPIN_BOT      |
