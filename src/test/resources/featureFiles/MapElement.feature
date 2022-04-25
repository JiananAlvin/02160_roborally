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
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" had initial position "<initial_row>" "<initial_col>" with orientation "<orientation>"
    And a card with movement "<movement>"
    When the card is played
    Then the robot position is "<expected_row>" "<expected_col>"
    Examples:
      | robot_name  | initial_row | initial_col | orientation | movement | expected_row | expected_col | map_name |
      | SQUASH_BOT  | 1           | 1           | N           | 1        | 0            | 1            | BEGINNER |
      | ZOOM_BOT    | 3           | 0           | E           | 1        | 3            | 1            | BEGINNER |
      | HAMMER_BOT  | 1           | 1           | E           | 2        | 1            | 3            | BEGINNER |
      | TRUNDLE_BOT | 1           | 1           | W           | -1       | 1            | 2            | BEGINNER |


  Scenario Outline: Damage affects robot lives
    Given A robot "<robot_name>" had "<initial_lives>" lives
    When The robot lives are reduced "<damage_lives>" points of damage by the game
    Then The robot now has "<final_lives>" lives
    Examples:
      | robot_name | initial_lives | damage_lives | final_lives |
      | SQUASH_BOT | 3             | 2            | 1           |
      | HULK_X90   | 2             | 1            | 1           |
      | SPIN_BOT   | 3             | 1            | 2           |

  # A robot can take a checkpoint token only when it stops on a checkpoint and it has taken checkpoint tokens from all previous checkpoints numerically
  Scenario Outline: A robot can take a checkpoint token only when it stops on a checkpoint and it has taken checkpoint tokens from all previous checkpoints numerically
    Given there is a game with map "<map_name>"
    And there are players "<playerA>" and "<playerB>" in this game
    And this is "<playerA>" turn
    And this player's robot stops on the checkpoint <point_number>
    And this player's robot has taken checkpoint tokens from all previous checkpoints numerically and did not take a checkpoint token from this checkpoint <point_number>
    When this player's turn ends and the robot stops on this checkpoint <point_number>
    Then this player gets a checkpoint token from this checkpoint successfully and now has <point_number> checkpoint tokens
    Examples:
      | map_name     | playerA | playerB | point_number |
      | INTERMEDIATE | Jianan  | Wenjie  | 1            |
      | ADVANCED     | Anna    | Raul    | 2            |
      | INTERMEDIATE | Ion     | Durdija | 2            |


  Scenario Outline: The game status is checked every time a checkpoint token is taken by a player
    Given there is a game with map "<map_name>"
    And there are players "<playerA>" and "<playerB>" in this game
    And this is "<playerA>" turn
    And this player's robot stops on the checkpoint <point_number>
    And this player's robot has taken checkpoint tokens from all previous checkpoints numerically and did not take a checkpoint token from this checkpoint <point_number>
    And this player's turn ends and the robot stops on this checkpoint <point_number>
    When this player gets a checkpoint token from this checkpoint successfully and now has <point_number> checkpoint tokens
    Then this game checks game status and now the game status is "<is_finished>"
    Examples:
      | map_name     | playerA | playerB | point_number | is_finished |
      | STARTER      | Jianan  | Wenjie  | 1            | finished  |
      | ADVANCED     | Ion     | Durdija | 3            | finished  |
      | INTERMEDIATE | Anna    | Raul    | 2            | unfinished    |


  Scenario Outline: Robot can not go through wall when wall is at the current position
    Given there is a game with map "<map_name>"
    And A robot "<robot_name>" has position "<row>" "<col>"
    And there is a wall at the same position as the robot
    And the robot faces the wall
    When robot tries to move forward
    Then robot does not move forward
    Examples:
      | robot_name  | row | col | map_name |
      | ZOOM_BOT    | 2   | 1   | BEGINNER |
      | HULK_X90    | 7   | 1   | BEGINNER |
      | SPIN_BOT    | 6   | 5   | BEGINNER |
      | TRUNDLE_BOT | 5   | 2   | BEGINNER |

    Scenario Outline: Robot can not go through wall when wall is at next position
    Given there is a game with map "<map_name>"
    And A robot "<robot_name>" has position "<row>" "<col>"
    When robot tries to move forward
    And there is a wall at the next position of the robot
    Then robot does not move forward
    Examples:
      | robot_name  | row | col | map_name |
      | ZOOM_BOT    | 4   | 2   | BEGINNER |
      | SPIN_BOT    | 6   | 5   | BEGINNER |
      | TRUNDLE_BOT | 5   | 2   | BEGINNER |
      | HULK_X90    | 6   | 7   | BEGINNER |

  Scenario Outline: Robots can shoot each other
    Given there is a game with map "<map_name>"
    And A robot "<robot_name>" has position "<row>" "<col>"
    And robot faces another robot "<robot_name2>" with position "<row2>" "<col2>"
    When programming phase is over
    Then robot1 shoots robot2

    Examples:
#      positions on map so that there is no obstacle between them
      | robot_name | robot_name2 | map_name | row | col | row2 | col2 |
      | ZOOM_BOT   | SPIN_BOT    | BEGINNER | 3   | 1   | 3    | 3    |
      | HULK_X90   | ZOOM_BOT    | BEGINNER | 1   | 0   | 1    | 3    |

  Scenario Outline: Robot can not go past the board limits
    Given there is a game with map "<map_name>"
    And A robot "<robot_name>" has position "<row>" "<col>"
    And robot has orientation "<orientation>"
    When robot tries to move past the board limits
    Then robot does not move past the board limits
    Examples:
#    Testing on 4 different borders
      | robot_name | row | col | map_name | orientation |
      | HULK_X90   | 0   | 0   | BEGINNER | N           |
      | ZOOM_BOT   | 0   | 0   | BEGINNER | W           |
      | SPIN_BOT   | 9   | 0   | BEGINNER | W           |
      | SQUASH_BOT | 8   | 12  | BEGINNER | E           |
      | HAMMER_BOT | 0   | 12  | BEGINNER | N           |
      | HAMMER_BOT | 0   | 12  | BEGINNER | E           |

# TODO: implement the following scenario
#    Scenario Outline: Robots can not shoot each other when there is an obstacle between them
#      Given there is a game with map "<map_name>"
#      And A robot "<robot_name>" has position "<row>" "<col>"
#      And robot faces another robot "<robot_name2>" with position "<row2>" "<col2>"
#      When programming phase is over
#      Then robot1 shoots robot2
#      Examples:
##      positions of robots so that there is an obstacle between them
#        | robot_name | robot_name2 | map_name | row | col | row2 | col2 |
#        | ZOOM_BOT   | SPIN_BOT    | BEGINNER | 2   | 1   | 3   | 1   |
#        | HULK_X90   | ZOOM_BOT    | BEGINNER | 7   | 1   | 2   | 1   |
#  Scenario Outline: Robots push each other
#    Given there is a game with map "<map_name>"
#    And A robot "<robot_name>" has position "<row>" "<col>"
#    And robot faces another robot "<robot_name2>" with position "<row2>" "<col2>"
#    When programming phase is over
#    Then robot1 pushes robot2
#    And robot1 is at the initial position of robot2
#    And robot2 is at a new position
#    Examples:
#      | robot_name | robot_name2 | map_name | row | col | row2 | col2 |
#      | ZOOM_BOT   | SPIN_BOT    | BEGINNER | 3   | 1   | 3   | 2   |
#      | HULK_X90   | ZOOM_BOT    | BEGINNER | 1   | 0   | 1   | 1   |
#      | SPIN_BOT   | ZOOM_BOT    | BEGINNER | 3   | 0   | 3   | 1   |
