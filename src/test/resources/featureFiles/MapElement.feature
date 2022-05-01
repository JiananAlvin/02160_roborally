@tag
Feature:
  #####################             ANTENNA SCENARIOS       #######################################
  Scenario Outline: The antenna determines the priority
    Given an antenna and three robots "<robot-name1>", "<robot-name2>" and "<robot-name3>" chosen by "<player1>", "<player2>" and "<player3>" respectively
    When robotI, robotII and robotIII are placed in ("<row1>","<col1>"), ("<row2>","<col2>"),("<row3>","<col3>") respectively
    Then the priority of these players is "<result_name1>","<result_name2>","<result_name3>"
    Examples: # (4,0) antenna
      | player1 | player2 | player3 | robot-name1 | robot-name2 | robot-name3 | row1 | col1 | row2 | col2 | row3 | col3 | result_name1 | result_name2 | result_name3 |
      | Raul    | Simona  | Durdija | SQUASH_BOT  | ZOOM_BOT    | HAMMER_BOT  | 5    | 3    | 7    | 5    | 12   | 4    | Raul         | Simona       | Durdija      |
      | Wenjie  | Anna    | Ion     | SQUASH_BOT  | ZOOM_BOT    | HAMMER_BOT  | 5    | 3    | 5    | 5    | 12   | 4    | Wenjie       | Anna         | Ion          |

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
    When perform the programming card "<programming_card>" on the robot
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

  Scenario Outline: As a robot I want to get 1 life when i execute power up card
    Given a robot "<robot_name>"
    And robot has "<life_before>" lives
    When robot execute power up card
    Then the robot now has "<life_after>" lives
    Examples:
      | life_before | life_after | robot_name |
      | 3           | 4          | SQUASH_BOT |
      | 2           | 3          | ZOOM_BOT   |
      | 5           | 5          | HAMMER_BOT |

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
      | TRUNDLE_BOT | 2   | 2   | E           | 3        | 2            | 5            | BEGINNER |
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

# Global scenarios
  Scenario Outline: The game status is checked every time a checkpoint token is taken by a player on map
    Given "<playerA>" and "<playerB>" are in a game with the map "<mapName>"
    And playerA's robot has taken checkpoint tokens from all previous checkpoints numerically except <point_number>
    When playerA's turn ends and his robot stops on the checkpoint <point_number>
    Then  playerA gets a checkpoint token from this checkpoint successfully and now has <point_number> checkpoint tokens
    And this game checks game status and now the game status is "<game_status>"
    Examples:
      | playerA | playerB | point_number | game_status | mapName  |
      | Jianan  | Wenjie  | 2            | unfinished  | ADVANCED |
      | Anna    | Raul    | 3            | finished    | ADVANCED |
      | Ion     | Durdija | 1            | unfinished  | ADVANCED |
      | Ion     | Durdija | 1            | finished    | STARTER  |

#    Robot interaction with elements:
#    In the following scenarios by "land on" means that robot moves forward according to the orientation
  Scenario Outline: Robot lands on a static gear
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    And robot has "<lives>" lives
    When robot lands on an oil stain
    Then the robot now has "<lives1>" lives
    Examples:
      | map_name     | robot_name | row | col | orientation | lives | lives1 |
      | INTERMEDIATE | ZOOM_BOT   | 4   | 4   | E           | 5     | 3      |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 5   | N           | 4     | 2      |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 11  | W           | 3     | 1      |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 11  | W           | 2     | 5      |

  Scenario Outline: Robot lands on a rotating gear
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot lands on a rotating gear with direction "<direction>"
    Then robot has "<final_orientation>" orientation
    Examples:
      | map_name     | robot_name | row | col | orientation | final_orientation | direction        |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 6   | E           | S                 | clockwise        |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 6   | W           | S                 | counterclockwise |
      | INTERMEDIATE | ZOOM_BOT   | 5   | 4   | E           | N                 | counterclockwise |
      | INTERMEDIATE | ZOOM_BOT   | 4   | 7   | E           | S                 | clockwise        |

  Scenario Outline: Robot lands on a pit
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot lands on a pit
    Then robot is sent to the reboot point
    And the robot now has "<lives>" lives
    Examples:
      | map_name     | robot_name | row | col | orientation | lives |
      | INTERMEDIATE | ZOOM_BOT   | 2   | 5   | E           | 5     |
      | INTERMEDIATE | ZOOM_BOT   | 2   | 7   | W           | 5     |
      | INTERMEDIATE | ZOOM_BOT   | 1   | 6   | S           | 5     |

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

  Scenario Outline: Robot lands on a conveyor belt
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot lands on a conveyor belt with distance "<distance>"
    Then robot moves forward according to the direction
    Examples:
      | map_name     | robot_name | row | col | orientation | distance |
      | INTERMEDIATE | ZOOM_BOT   | 1   | 3   | E           | 1        |
      | INTERMEDIATE | ZOOM_BOT   | 1   | 2   | E           | 1        |
      | INTERMEDIATE | ZOOM_BOT   | 9   | 10  | E           | 1        |
      | INTERMEDIATE | ZOOM_BOT   | 0   | 1   | E           | 1        |
      | INTERMEDIATE | ZOOM_BOT   | 2   | 5   | S           | 2        |

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

  Scenario Outline: Robots cannot go back through a wall
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot tries to move backward and there is a wall
    Then robot does not move backward
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 4   | 6   | N           | STARTER  |
      | ZOOM_BOT   | 5   | 3   | E           | STARTER  |
      | ZOOM_BOT   | 5   | 2   | W           | STARTER  |

  Scenario Outline: Robots cannot go over the board
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When robot tries to move forward and there is void
    Then robot dies
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 0   | 0   | N           | STARTER  |
      | ZOOM_BOT   | 0   | 0   | W           | STARTER  |
      | ZOOM_BOT   | 9   | 0   | S           | STARTER  |
      | ZOOM_BOT   | 9   | 12  | E           | STARTER  |
      | ZOOM_BOT   | 9   | 12  | S           | STARTER  |

  Scenario Outline: Robot gets 1 live when it lands on a charger tile
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    And robot has "<lives>" lives
    When robot lands on a charger tile
    Then the robot now has "<lives1>" lives
    Examples:
      | robot_name | row | col | orientation | lives | lives1 | map_name |
      | ZOOM_BOT   | 2   | 4   | E           | 2     | 3      | STARTER  |
      | ZOOM_BOT   | 2   | 4   | E           | 5     | 5      | STARTER  |
      | ZOOM_BOT   | 4   | 7   | E           | 5     | 5      | STARTER  |
      | ZOOM_BOT   | 4   | 9   | W           | 3     | 4      | STARTER  |

  Scenario Outline: Robot pushes another robot
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When there is a robot in the position first robot moves on
    Then fist robot pushes the second robot scenario 1
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 4   | 0   | E           | STARTER  |
      | ZOOM_BOT   | 2   | 1   | E           | STARTER  |
      | ZOOM_BOT   | 3   | 5   | W           | STARTER  |
      | ZOOM_BOT   | 6   | 2   | N           | STARTER  |


  Scenario Outline: Robot pushes another robot when move back is executed
    Given there is a game with map "<map_name>"
    And a robot "<robot_name>" with position "<row>" "<col>"
    And robot has "<orientation>" orientation
    When first robot moves back and there is a robot in the position
    Then fist robot pushes the second robot scenario 2
    Examples:
      | robot_name | row | col | orientation | map_name |
      | ZOOM_BOT   | 4   | 0   | N           | STARTER  |
      | ZOOM_BOT   | 1   | 1   | W           | STARTER  |
      | ZOOM_BOT   | 3   | 4   | W           | STARTER  |
      | ZOOM_BOT   | 6   | 3   | E           | STARTER  |
      | ZOOM_BOT   | 5   | 8   | W           | STARTER  |


  Scenario Outline: Robot shoots a laser at another robot
    Given there is a game with map "<map_name>"
    And a robot1 "<robot_name>" with position <row> <col> and orientation "<orientation>" and robot2 "<robot_name2>" with position <row2> <col2> orientation "<orientation2>" and robot3 "<robot_name3>" with position <row3> <col3> orientation "<orientation3>"
    When shooting phase starts
    Then robot1 has <robot1_lives> and robot2 has <robot2_lives> and robot3 has <robot3_lives>
    Examples:
      | map_name | robot_name | row | col | orientation | robot_name2 | row2 | col2 | orientation2 | robot_name3 | row3 | col3 | orientation3 | robot1_lives | robot2_lives | robot3_lives |
#      There is not a wall between robot1 robot2
      | STARTER  | ZOOM_BOT   | 6   | 0   | E           | HAMMER_BOT  | 6    | 1    | N            | HULK_X90    | 6    | 2    | N            | 5            | 4            | 5            |
      | STARTER  | ZOOM_BOT   | 6   | 0   | E           | HAMMER_BOT  | 6    | 1    | W            | HULK_X90    | 6    | 2    | W            | 4            | 3            | 5            |
#      There is a wall between robot1 and robot2
      | STARTER  | ZOOM_BOT   | 2   | 0   | E           | HAMMER_BOT  | 2    | 2    | W            | HULK_X90    | 2    | 3    | W            | 5            | 4            | 5            |
      | STARTER  | ZOOM_BOT   | 1   | 1   | S           | HAMMER_BOT  | 2    | 1    | N            | HULK_X90    | 3    | 1    | N            | 5            | 4            | 5            |
