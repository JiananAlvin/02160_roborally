@tag
Feature: 
  @tag1
  Scenario: As a player I want to input my name
    Given I input my name "<name>"
    Then I validate the name stored in InfoManager
    
 Examples:
 | name |
 | Weinji | 
 | Ion |
 | Simona |
 | Georgia |
 | Anna |
 | Jianan |
 
 	Scenario: As a player I want to create a room
 		Given that I am not in a room
 		Then I create a room
 	Scenario: As a player I want to join a room
 		Given that I am not in a room
 #		And that I clicked on "join room"
 		Then I join a room with room number "uuid"
 		