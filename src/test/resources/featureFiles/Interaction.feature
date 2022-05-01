@tag
Feature:

  Scenario Outline: A player draws cards
    Given a player had "<num_of_cards_in_prog_deck>" cards in his programming deck
    When the player draws nine cards from his programming deck
    Then the player now has "<num_of_cards_in_prog_deck_now>" cards in his programming deck
    And he now has "<num_of_cards_in_discard_pile_now>" cards in his discard pile
    And he now has "<num_of_cards_in_hand>" cards in his hand
    Examples:
      | num_of_cards_in_prog_deck | num_of_cards_in_prog_deck_now | num_of_cards_in_discard_pile_now | num_of_cards_in_hand |
      | 32                        | 23                            | 0                                | 9                    |
      | 10                        | 1                             | 22                               | 9                    |
      | 8                         | 23                            | 0                                | 9                    |
      | 2                         | 23                            | 0                                | 9                    |

  Scenario Outline: An player place programming cards in each of registers
    Given a player had nine cards in his hands
    And the player has finished "<num_of_finished_registers>" registers.
    When the time runs out but the player did not finish programming but only "<num_of_finished_registers>" registers
    Then his unfinished registers will be filled randomly with the remaining programming cards in his hand.
    And he now has "<num_of_finished_registers_now>" cards in each of registers
#    And the programming result will be put to sever
    Examples:
      | num_of_finished_registers | num_of_finished_registers_now |
      | 3                         | 5                             |
      | 2                         | 5                             |
      | 0                         | 5                             |

  Scenario Outline: As game driver I want to see if two players are the same
    Given a player with name "<player_name_1>"
    And another player with name "<player_name_2>"
    When i check if the two players are the same
    Then the result should be "<result>"
    Examples:
      | player_name_1 | player_name_2 | result |
      | player1       | player1       | true   |
      | player1       | player3       | false  |

  Scenario Outline: As a game driver I want to get the user using the client app
    Given a player with name "<player_name>"
    When i get the user using the client app
    Then the user should be "<player_name>"
    Examples:
      | player_name |
      | player1     |
      | player2     |

    Scenario Outline: As a game driver I want to get a register from all available registers
    Given a player with name "<player_name>"
    When register "<register_no>" is requested
    Then i should get a register
      Examples:
      | player_name | register_no |
      | player1     | 1           |
      | player1     | 2           |


