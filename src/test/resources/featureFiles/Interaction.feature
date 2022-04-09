@tag
Feature:

  Scenario: Getting programming cards with enough cards in card desk
    Given a player "Wenjie" is in programming phase
    When he gets programming cards from card desk
    Then Player gets 9 cards


#  Scenario Outline: Choose five programming cards and put them into registers in order
#    Given  a player "<user_name>" is in programming phase
#    And the cards in card desk are shuffled
#    And he gets 9 programming cards from card desk
#    When he chooses five programming cards
#    Examples:
#      | user_name |


