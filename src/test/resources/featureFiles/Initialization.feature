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

#    TODO: Players in the same game/room cannot choose the same robot, but the same robot can appear in different games/rooms
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

