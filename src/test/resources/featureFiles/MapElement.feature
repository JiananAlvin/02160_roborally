@tag
Feature:
##############           ROBOT SCENARIOS          #######################
  Scenario Outline: A robot gets an initial position
    Given a player chose a robot "<robot-name>"
    When the robot gets an initial position randomly
    Then robot is now at a position "<position_x>" and "<position_y>"
    Examples:
      | position_x | position_y | robot-name  |
      | 0          | 0          | SQUASH BOT  |
      | 0          | 0          | ZOOM BOT    |
      | 0          | 0          | HAMMER BOT  |
      | 0          | 0          | TRUNDLE BOT |


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


  Scenario Outline: A robot moves according to the programming card
    Given A robot "<robot_name>" has initial position "<initial_posX>" "<initial_posY>" with orientation "<orientation>"
    And A card with movement "<movement>"
    When The card is played
    Then the robot position is "<expected_positionX>" "<expected_positionY>"
    Examples:
      | robot_name | initial_posX | initial_posY | orientation | movement | expected_positionX | expected_positionY |
      | Anna       | 1            | 1            | N           | 1        | 1                  | 0                  |
      | Anna       | 1            | 1            | S           | 1        | 1                  | 2                  |
      | Anna       | 1            | 1            | E           | 2        | 3                  | 1                  |
      | Anna       | 1            | 1            | W           | -1       | 2                  | 1                  |


  Scenario Outline: Damage affects robot lives
    Given A robot "<robot_name>" had "<initial_lives>" lives
    When The robot lives are reduced "<damage_lives>" points of damage by the game
    Then The robot now has "<final_lives>" lives
    Examples:
      | robot_name | initial_lives | damage_lives | final_lives |
      | Raul       | 1             | 1            | 5           |
      | Raul       | 2             | 1            | 1           |
      | Jianan     | 1             | 2            | 5           |

  # TODO:
  #  When
  Scenario Outline: As a robot I want to check if I am inside of the board
    Given A robot "<robot_name>" has position "<posX>" "<posY>"
    Then The expected output is "<expected_output>" in a board that have a maximum size of "<max_posX>" "<max_posY>"
    Examples:
      | robot_name | posX | posY | max_posX | max_posY | expected_output |
      | Raul       | 1    | -2   | 3        | 3        | false           |
      | Raul       | 7    | 1    | 4        | 4        | false           |
      | Raul       | 1    | 1    | 1        | 1        | true            |
      | Raul       | 4    | -1   | 3        | 3        | false           |


  #this test only have the purpose of mix some of the scenarios, no step definition needed (:
  Scenario Outline: As a robot I want to move with some cards and check if I'm inside of the board
    Given A robot "<robot_name>" has initial position "<initial_posX>" "<initial_posY>" with orientation "<orientation>"
    And A card with movement "<movement>"
    When The card is played
    Then The expected output is "<expected_output>" in a board that have a maximum size of "<max_posX>" "<max_posY>"
    Examples:
      | robot_name | initial_posX | initial_posY | orientation | movement | expected_output | max_posX | max_posY |
      | Anna       | 3            | 3            | N           | 2        | true            | 5        | 5        |
      | Anna       | 2            | 0            | S           | -1       | false           | 5        | 5        |
      | Anna       | 4            | 2            | E           | 3        | false           | 6        | 5        |


#    ################             ANTENNA SCENARIOS       #######################################
  Scenario Outline: The antenna determines the priority
    Given An antenna and three robots "<robot-name1>", "<robot-name2>" and "<robot-name3>" in a game
    When RobotI, robotII and robotIII are placed in ("<x1>","<y1>"), ("<x2>","<y2>"),("<x3>","<y3>") respectively.
    Then The order of these robots is "<result_name1>","<result_name2>","<result_name3>".
    Examples: # (0,4) antenna
      | robot-name1 | robot-name2 | robot-name3 | x1 | y1 | x2 | y2 | x3 | y3 | result_name1 | result_name2 | result_name3 |
      | SQUASH BOT  | ZOOM BOT    | HAMMER BOT  | 5  | 3  | 7  | 5  | 12 | 4  | SQUASH BOT   | ZOOM BOT     | HAMMER BOT   |
      | SQUASH BOT  | ZOOM BOT    | HAMMER BOT  | 5  | 3  | 5  | 5  | 12 | 4  | ZOOM BOT     | SQUASH BOT   | HAMMER BOT   |
