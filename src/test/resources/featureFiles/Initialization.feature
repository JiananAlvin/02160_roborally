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

  Scenario: Player can create a room
    Given a player opened the application
    When the player creates a room with number 142
    Then there is a new room with number 142

  Scenario Outline: Player chooses a robot character
    Given a player has a name "<name>"
    When the player chooses a robot "<robot_name>"
    Then "<robot_name>" is assigned to this player
    And there is a new record in the collection user with username "<name>" and robotname "<robot_name>"
    Examples:
      | name   | robot_name |
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
    And a room owner "<room_owner_name>" creates a new room with map "<map_name>"
    When the player gets the room number from room owner and join this room
    Then the player is in this room
    Examples:
      | name   | room_owner_name | map_name |
      | Anna   | Jianan          | STARTER  |
      | Raul   | Simona          | BEGINNER |
      | Durjia | Anna            | ADVANCED |

  Scenario Outline: As a room owner, I should generate initial positions for each participant's robot and put them to server.
    Given a room owner "<room_owner_name>" created a new room with map "<map_name>" and chose robot "<room_owner_robot>"
    And player1 "<player1_name>" player2 "<player2_name>" and player3 "<player3_name>" chose robot1 "<player1_robot>" robot2 "<player2_robot>" and robot3 "<player3_robot>" respectively
    And player1 player2 and player3 joint this room
    When the room owner starts the game and gets all information from server
    Then the client of room owner generates all the initial positions and puts them to server
    Examples:
      | room_owner_name | map_name | room_owner_robot | player1_name | player2_name | player3_name | player1_robot | player2_robot | player3_robot |
      | Wenjie          | STARTER  | SQUASH_BOT       | Jianan       | Ion          | Durjia       | ZOOM_BOT      | HAMMER_BOT    | SPIN_BOT      |

  Scenario Outline: As a participant of a room, I should know the initial position of my robot from server.
    Given a room owner "<room_owner_name>" created a new room with map "<map_name>" and chose robot "<room_owner_robot>"
    And player1 "<player1_name>" player2 "<player2_name>" and player3 "<player3_name>" chose robot1 "<player1_robot>" robot2 "<player2_robot>" and robot3 "<player3_robot>" respectively
    And player1 player2 and player3 joint this room
    And the room owner starts the game and gets all information from server
    When the client of room owner generates all the initial positions and puts them to server
    Then player1 player2 and player3 successfully get their robot info from server
    Examples:
      | room_owner_name | map_name | room_owner_robot | player1_name | player2_name | player3_name | player1_robot | player2_robot | player3_robot |
      | Wenjie          | STARTER  | SQUASH_BOT       | Jianan       | Ion          | Durjia       | ZOOM_BOT      | HAMMER_BOT    | SPIN_BOT      |


  Scenario Outline: As a player I want to exit a room
    Given a player has a name "<name>"
    And a room owner "<room_owner_name>" creates a new room with map "<map_name>"
    And player is in this room
    When player want to exit this room
    Then player is not in a room
    Examples:
      | room_owner_name | room_owner_name | map_name |
      | test2           | test1           | STARTER  |


  Scenario Outline: Default room status
    Given a player has a name "<name>"
    When the player creates a new room and chooses a map "<map_name>"
    Then the status of the room is "WAITING"
    Examples:
      | name   | map_name |
      | Ion    | STARTER  |
      | Jianan | BEGINNER |

  Scenario Outline: Room status can be updated
    Given a player has a name "<name>"
    And a room owner "<room_owner_name>" creates a new room with map "<map_name>"
    When update the status of the room to "<status>"
    Then the status of the room is "<status>"
    Examples:
      | room_owner_name | map_name | status  |
      | test1           | STARTER  | START   |
      | test1           | STARTER  | WAITING |
      | test1           | STARTER  | END     |


