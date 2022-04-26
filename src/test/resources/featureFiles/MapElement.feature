@tag
Feature:
  #####################             ANTENNA SCENARIOS       #######################################
  Scenario Outline: The antenna determines the priority
    Given an antenna and three robots "<robot-name1>", "<robot-name2>" and "<robot-name3>" chosen by "<player1>", "<player2>" and "<player3>" respectively
    When robotI, robotII and robotIII are placed in ("<row1>","<col1>"), ("<row2>","<col2>"),("<row3>","<col3>") respectively
    Then the priority of these players is "<result_name1>","<result_name2>","<result_name3>"
    Examples: # (0,4) antenna
      | player1 | player2 | player3 | robot-name1 | robot-name2 | robot-name3 | row1 | col1 | row2 | col2 | row3 | col3 | result_name1 | result_name2 | result_name3 |
      | Raul    | Simona  | Durdija | SQUASH_BOT  | ZOOM_BOT    | HAMMER_BOT  | 5    | 3    | 7    | 5    | 12   | 4    | Raul         | Simona       | Durdija      |
      | Wenjie  | Anna    | Ion     | SQUASH_BOT  | ZOOM_BOT    | HAMMER_BOT  | 5    | 3    | 5    | 5    | 12   | 4    | Anna         | Wenjie       | Ion          |

 #####################             ROBOT SCENARIOS          #######################################
  Scenario Outline: A robot gets an initial position
    Given a player chose a robot "<robot_name>"
    When the robot gets an initial position randomly
    Then robot is now at a position "<row>" and "<col>"
    Examples:
      | row | col | robot_name  |
      | 0   | 0   | SQUASH_BOT  |
      | 0   | 0   | ZOOM_BOT    |
      | 0   | 0   | HAMMER_BOT  |
      | 0   | 0   | TRUNDLE_BOT |


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
    Given a robot "<robot_name>" had initial position "<initial_row>" "<initial_col>" with orientation "<orientation>"
    And a card with movement "<movement>"
    When the card is played
    Then the robot position is "<expected_row>" "<expected_col>"
    Examples:
      | robot_name  | initial_row | initial_col | orientation | movement | expected_row | expected_col |
      | SQUASH_BOT  | 1           | 1           | N           | 1        | 0            | 1            |
      | ZOOM_BOT    | 1           | 1           | S           | 1        | 2            | 1            |
      | HAMMER_BOT  | 1           | 1           | E           | 2        | 1            | 3            |
      | TRUNDLE_BOT | 1           | 1           | W           | -1       | 1            | 2            |


  Scenario Outline: Damage affects robot lives
    Given A robot "<robot_name>" had "<initial_lives>" lives
    When The robot lives are reduced "<damage_lives>" points of damage by the game
    Then The robot now has "<final_lives>" lives
    Examples:
      | robot_name | initial_lives | damage_lives | final_lives |
      | SQUASH_BOT | 1             | 1            | 5           |
      | HULK_X90   | 2             | 1            | 1           |
      | SPIN_BOT   | 1             | 2            | 5           |

  # TODO:
  #  When
  Scenario Outline: As a robot I want to check if I am inside of the board
    Given A robot "<robot_name>" has position "<row>" "<col>"
    Then the expected output is "<expected_output>" in a board that have a maximum size of "<max_row>" "<max_col>"
    Examples:
      | robot_name  | row | col | max_row | max_col | expected_output |
      | ZOOM_BOT    | 1   | -2  | 3       | 3       | false           |
      | HULK_X90    | 7   | 1   | 4       | 4       | false           |
      | SPIN_BOT    | 1   | 1   | 1       | 1       | true            |
      | TRUNDLE_BOT | 4   | -1  | 3       | 3       | false           |

  # This test only have the purpose of mix some of the scenarios, no step definition needed (:
  Scenario Outline: As a robot I want to move with some cards and check if I'm inside of the board
    Given a robot "<robot_name>" had initial position "<initial_row>" "<initial_col>" with orientation "<orientation>"
    And a card with movement "<movement>"
    When the card is played
    Then the expected output is "<expected_output>" in a board that have a maximum size of "<max_row>" "<max_col>"
    Examples:
      | robot_name | initial_row | initial_col | orientation | movement | expected_output | max_row | max_col |
      | HAMMER_BOT | 3           | 3           | N           | 2        | true            | 5       | 5       |
      | SPIN_BOT   | 2           | 0           | E           | -1       | false           | 5       | 5       |
      | HULK_X90   | 4           | 2           | S           | 3        | false           | 6       | 5       |

  Scenario Outline: The game status is checked every time a checkpoint token is taken by a player
    Given "<playerA>" and "<playerB>" are in a game with the map ADVANCED
    And playerA's robot has taken checkpoint tokens from all previous checkpoints numerically except <point_number>
    When playerA's turn ends and his robot stops on the checkpoint <point_number>
    Then  playerA gets a checkpoint token from this checkpoint successfully and now has <point_number> checkpoint tokens
    And this game checks game status and now the game status is "<game_status>"
    Examples:
      | playerA | playerB | point_number | game_status |
      | Jianan  | Wenjie  | 2            | unfinished  |
      | Anna    | Raul    | 3            | finished    |
      | Ion     | Durdija | 1            | unfinished  |


#  Scenario Outline: Player lands on an Obstacle
#    Given A robot "<robot_name>" had "<initial_lives>" lives
#    And The robot has initial position "<initial_row>" "<initial_col>" with orientation "<orientation>"
#    And a position "<obstacle_row>" "<obstacle_col>" on the map indicating the obstacle of type "<type_of_obstacle>"
#    When robot lands on an obstacle status is true
#    Then The robot now has "<final_lives>" lives
#    Examples:
#      | robot_name | initial_lives | initial_row | initial_col | orientation | obstacle_row | obstacle_col | type_of_obstacle | final_lives |
#      | ZOOM_BOT   | 2             | 2           | 2           | N           | 2            | 2            | wnl              | 1           |
#      | HULK_X90   | 2             | 3           | 2           | N           | 2            | 2            | wsl              | 2           |
#      | SPIN_BOT   | 1             | 2           | 2           | N           | 2            | 2            | wel              | 5           |
#      | SQUASH_BOT | 3             | 2           | 2           | S           | 2            | 2            | wwl              | 2           |
#      | HAMMER_BOT | 4             | 2           | 2           | S           | 2            | 2            | sg               | 2           |
