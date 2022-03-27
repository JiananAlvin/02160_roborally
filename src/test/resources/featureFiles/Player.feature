@tag
Feature:

#  @tag1
#  Scenario Outline: As a player I want to input my name
#    Given I input my name "<name>"
#    Then I validate the name stored in InfoManager
#
#    Examples:
#      | name    |
#      | Weinji  |
#      | Ion     |
#      | Simona  |
#      | Georgia |
#      | Anna    |
#      | Jianan  |
#
#  Scenario: As a player I want to create a room
#    Given that I am not in a room
#    Then I create a room
#
#  Scenario: As a player I want to join a room
#    Given that I am not in a room
# #		And that I clicked on "join room"
#    Then I join a room with room number "uuid"





####################################BEGIN#######################################
# [Dj]
#  scenarios for user stories for robo rally (single player)

  Scenario Outline: Getting a name
    Given a player started the game
    When  player inputs a name "<name>"
    Then this name is assigned to this player
    Examples:
      | name      |
      | test_1    |
      | test_2    |
      | 123333123 |
      | tttttttt  |


  Scenario Outline: Choose a map
    Given a player has a name "<name>"
          #when player clicks on get a map
    When the player chooses a map "<map_num>"
    Then this map "<map_num_displayed>" is displayed
    Examples:
      | name   | map_num | map_num_displayed |
      | Wenjie | map1    | MAP1_CONTENT      |
      | Jianan | map2    | MAP2_CONTENT      |
      | Alice  | map999  | ERROR_MAP_NUM     |


  Scenario Outline: Choose a robot character
    Given a player has a name "<name>"
    And having-a-robot status is false
    When choose a robot "<robot-name>"
    Then  "<robot-name>" is assigned to this player
    Examples:
      | name       | robot-name |
      | test1_user | Alice      |


  Scenario Outline: Initial position
    Given a player has a name "<name>"
    And choose a robot "<robot-name>"
    And having-a-robot status is true
    And robot-on-the-board status is false
    When get initial position randomly
    Then Player is now at a position "<position_x>" and "<position_y>"

    Examples:
      | name  | position_x | position_y | robot-name|
      | test1 | 0          | 0          | jianan|

#  Scenario: Getting a programming cards
#    Given a Player "name"
#    And no-prog_cards status is false
#    When get programming cards
#    Then Player gets cards and chooses 5
#
#  	#//user stories (feautures mandatory)
#
#  Scenario: Choosing a map
#    Given having-a-map status is false
#    When map many
#    Then Player chooses a map
#
#  Scenario: Game over
#    Given game-over status is true
#    And player_informed status is false
#    Then player_informed status is true
#
#
#
#    #########################################START#################################################3
##    [Wenjie]: As a player I can put mark on the checkpoints.
##   A robot can put a marker on a check point only when it is at a check point and it has put marks in all previous checkpoints numerically
#Feature: As a player I can put mark on the checkpoints
#
#  Scenario:  A robot can put a marker on a check point only when it is at a check point and it has put marks in all previous checkpoints numerically
#    Given this is "<player_name>" turn
#    *  his robot is at the check point "<point_number>"
#    *  his robot does not put mark on this check point
#    *  his robot has put marks on all numerically previous checkpoints
#    When he wants to put a mark on this checkpoint
#    Then A mark is put on this checkpoint successfully
#
#  Scenario:  The robot fails when it has not put marker in previous check point
#    Given this is "<player_name>" turn
#    *  his robot is at the check point "<point_number>"
#    *  his robot does not put marks on all numerically previous checkpoints
#    When he wants to put a mark on this checkpoint
#    Then Fail to put a marker on this checkpoint
#
#  Scenario:  The robot fails when it is not at the position of this check point
#    Given this is "<player_name>" turn
#    *  his robot is not at the check point "<point_number>"
#    When he wants to put a mark on this checkpoint
#    Then A mark is put on this checkpoint unsuccessfully
#
#  Scenario:  The robot fails when it has put mark on this check point
#    Given this is "<player_name>" turn
#    *  his robot is at the check point "<point_number>"
#    *  his robot has put mark on this check point
#    When he wants to put a mark on this checkpoint
#    Then A mark is put on this checkpoint unsuccessfully
#
#
##  [Wenjie]: As a player I want to create a room
#Feature: As a player he wants to create a room
#
#  Scenario: A player can create a room if he has logged in and he is not in a room
#    Given  this player "<player_name>" has logged in
#    * he is not in a room now
#    When he wants to create a new room
#    Then successfully create a room
#
#  Scenario:  A player fails for he does not log in
#    Given this player "<player_name>" does not log in
#    When he wants to create a new room
#    Then fail to create a room
#
#  Scenario:  A player fails for he has been in a room
#    Given  this player "<player_name>" has logged in
#    * he is in a room now
#    When he wants to create a new room
#    Then fail to create a room
#
#
##  [Wenjie]: As a player I want to choose the map
#Feature: As a player I want to choose the map
#
#  Scenario: a player can choose a map when he has logged in and he is in a room
#    Given this player "<player_name>" has logged in
#    * he is in a room
#    When he wants to choose a map
#    Then successfully choose a map
#
#  Scenario: a player fails when he does not logged in
#    Given this player "<player_name>" does not logged in
#    When he wants to choose a map
#    Then fail to choose a map
#
#
#  Scenario: a player fails when he is not in a room
#    Given this player "<player_name>" has logged in
#    * he is not in a room
#    When he wants to choose a map
#    Then fail to choose a map
#
##  [Wenjie]: As a player I want to know if it is my turn
#Feature: As a player I want to know if it is my turn
#
#  Scenario:  A player wants to know if it is his turn
#    Given now it is player "<player_name>" turn
#    When he wants to check if it is his turn
#    Then he knows the current turn is "<current_player_name>" turn
#
#  Example:
#  | player_name|current_player_name|
#  | Wenjie| Wenjie|
#  | Jianan| Jianan|
#########################################END##################################################
