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



