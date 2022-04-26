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
    Given a robot was facing "<previous_orientation>"
    When the robot changes its orientation by using the programming card "<programming_card>"
    Then the robot is now facing "<new_orientation>"
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
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    And a card with movement "<movement>"
    When the card is played
    Then the robot position is "<expected_row>" "<expected_col>"
    Examples:
      | robot_name  | row | col | orientation | movement | expected_row | expected_col | map_name |
      | SQUASH_BOT  | 1   | 1   | N           | 1        | 0            | 1            | BEGINNER |
      | ZOOM_BOT    | 3   | 0   | E           | 1        | 3            | 1            | BEGINNER |
      | HAMMER_BOT  | 1   | 1   | E           | 2        | 1            | 3            | BEGINNER |
      | TRUNDLE_BOT | 1   | 1   | W           | -1       | 1            | 2            | BEGINNER |


  Scenario Outline: Damage affects robot lives
    Given a robot "<robot_name>"
    And robot has "<lives>" lives
    When the robot lives are reduced "<damage_lives>" points of damage by the game
    Then the robot now has "<final_lives>" lives
    Examples:
      | robot_name | lives | damage_lives | final_lives |
      | SQUASH_BOT | 3     | 2            | 1           |
      | HULK_X90   | 2     | 1            | 1           |
      | SPIN_BOT   | 3     | 1            | 2           |


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


#    Robot interaction with elements:
#    In the following scenarios by "land on" means that robot moves forward according to the orientation
  Scenario Outline: Robot lands on a static gear
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    And robot has "<lives>" lives
    When robot lands on a static gear
    Then the robot now has "<lives1>" lives
    Examples:
      | map_name | robot_name | row | col | orientation | lives | lives1 |
      | STARTER  | ZOOM_BOT   | 2   | 2   | N           | 2     | 1      |

  Scenario Outline: Robot lands on a rotating gear
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot lands on a rotating gear
    Then robot has "<final_orientation>" orientation
    Examples:
      | map_name | robot_name | row | col | orientation | final_orientation |
      | STARTER  | ZOOM_BOT   | 2   | 2   | N           | E                 |

  Scenario Outline: Robot lands on a pit
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot lands on a pit
    Then robot is sent to the reboot point
    And the robot now has "<lives>" lives
    Examples:
      | map_name | robot_name | row | col | orientation | lives |
      | STARTER  | ZOOM_BOT   | 2   | 2   | N           | 5     |

  Scenario Outline: Robot lands on a laser tile
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    And robot has "<lives>" lives
    When robot lands on a laser tile
    Then the robot now has "<lives1>" lives

    Examples:
      | robot_name | row | col | orientation | lives | lives1 | map_name |
      | ZOOM_BOT   | 3   | 5   | E           | 2     | 1      | STARTER  |
      | ZOOM_BOT   | 6   | 10  | W           | 2     | 1      | STARTER  |
      | ZOOM_BOT   | 7   | 6   | N           | 2     | 1      | STARTER  |


  Scenario Outline: Robots cannot go through a wall when wall is at next position
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot tries to move forward and there is a wall
    Then robot does not move forward
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 1   | 1   | S           | STARTER  |
      | ZOOM_BOT   | 4   | 3   | W           | STARTER  |
      | ZOOM_BOT   | 8   | 1   | N           | STARTER  |

  Scenario Outline: Robots cannot go through a wall when wall is at current position
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot tries to move forward and there is a wall
    Then robot does not move forward
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 2   | 1   | N           | STARTER  |
      | ZOOM_BOT   | 5   | 6   | N           | STARTER  |
      | ZOOM_BOT   | 7   | 9   | N           | STARTER  |
      | ZOOM_BOT   | 6   | 5   | E           | STARTER  |

  Scenario Outline: Robots cannot go over the board
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot tries to move forward and there is void
    Then robot does not move forward
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 0   | 0   | N           | STARTER  |
      | ZOOM_BOT   | 0   | 0   | W           | STARTER  |
      | ZOOM_BOT   | 9   | 0   | S           | STARTER  |
      | ZOOM_BOT   | 9   | 12  | E           | STARTER  |
      | ZOOM_BOT   | 9   | 12  | S           | STARTER  |
