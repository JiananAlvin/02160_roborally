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
    Given a player opened the application
    When  player inputs a name "<name>"
    Then this name is assigned to this player
    Examples:
      | name      |
      | test_1    |
      | test_2    |
      | 123333123 |
      | tttttttt  |


#  Scenario Outline: Choose a map
#    Given a player has a name "<name>"
#          #when player clicks on get a map
#    When the player chooses a map "<map_num>"
#    Then this map "<map_num_displayed>" is displayed
#    Examples:
#      | name   | map_num | map_num_displayed |
#      | Wenjie | map1    | MAP1_CONTENT      |
#      | Jianan | map2    | MAP2_CONTENT      |
#      | Alice  | map999  | ERROR_MAP_NUM     |

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
      | name  | position_x | position_y | robot-name |
      | test1 | 0          | 0          | jianan     |

  Scenario: Getting programming cards
    Given a player has a name "<name>"
#    And prog_cards status is false
    When get programming cards
    Then Player gets 9 cards

#  	#//user stories (features mandatory)
#
#  Scenario: Choosing a map
#    Given having-a-map status is false
#    When map many
#    Then Player chooses a map

  Scenario Outline: The antenna determines the priority
    Given An antenna and three robots "<robot-name1>", "<robot-name2>" and "<robot-name3>" in a game
    When RobotI, robotII and robotIII are placed in ("<x1>","<y1>"), ("<x2>","<y2>"),("<x3>","<y3>") respectively.
    Then The order of these robots is "<result_name1>","<result_name2>","<result_name3>".
    Examples: # (0,4) antenna
      | robot-name1 | robot-name2 | robot-name3 | x1 | y1 | x2 | y2 | x3 | y3 | result_name1 | result_name2 | result_name3 |
      | SQUASH BOT  | ZOOM BOT    | HAMMER BOT  | 5  | 3  | 7  | 5  | 12 | 4  | SQUASH BOT   | ZOOM BOT     | HAMMER BOT   |
      | SQUASH BOT  | ZOOM BOT    | HAMMER BOT  | 5  | 3  | 5  | 5  | 12 | 4  | ZOOM BOT     | SQUASH BOT   | HAMMER BOT   |

  Scenario Outline: As a robot, I want to change my direction according to the input angle value
    Given A robot was facing "<previous_orientation>"
    When The robot changes its orientation by using the programming card "<programming_card>"
    Then The robot is now facing "<new_orientation>"
    Examples:
      | previous_orientation | programming_card | new_orientation |
      | N                    | CardTurnLeft     | W               |
      | W                    | CardTurnLeft     | S               |
      | S                    | CardTurnLeft     | E               |
      | E                    | CardTurnLeft     | N               |
      | N                    | CardTurnRight    | E               |
      | E                    | CardTurnRight    | S               |
      | S                    | CardTurnRight    | W               |
      | W                    | CardTurnRight    | N               |
      | N                    | CardUTurn        | S               |
      | S                    | CardUTurn        | N               |
      | E                    | CardUTurn        | W               |
      | W                    | CardUTurn        | E               |


#   Scenario Outline: As player, I want to move my robot on the map, according to the programming cards selected
#     Given A robot called "<robot_name>" assigned to a player
#     And robot orientation is "<orientation>"
#     And there exists a robot "<robot_name>" on the board
#     When the board reads the card reavealed
#     Then robot position is expected postition "<arg1>" "<arg2>"
#
#     Examples:
#       | robot_name           | orientation     | arg2        | arg1|
#       | Simona               | n               | 1           | 1   |

  Scenario Outline: reboot after robot dies
    Given A robot "<robot_name>" has "<initial_lives>" lives
    When The robot lives is reduced "<damage_lives>"
    Then The robot is rebooted
    And The robot has "<final_lives>" lives
    Examples:
      | robot_name | initial_lives | final_lives | damage_lives |
      | Raul       | 1             | 5           | 1            |
      | Raul       | 2             | 1           | 1            |
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
# hhahha
#  Example:
#  | player_name|current_player_name|
#  | Wenjie| Wenjie|
#  | Jianan| Jianan|
#########################################END##################################################
