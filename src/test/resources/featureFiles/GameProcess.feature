@tag
Feature:

  Scenario: Getting programming cards
    Given a player "<name>" is in programming phase
    When he gets programming cards from card desk
    Then Player gets 9 cards

#  	#//user stories (features mandatory)
#
#  Scenario: Choosing a map
#    Given having-a-map status is false
#    When map many
#    Then Player chooses a map

