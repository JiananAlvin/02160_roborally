# 02160 Agile OOSD final project

> Group 10  
> Members: Anna, Đurđija, Ion, Jianan, Raül, Simona, Wenjie

:warning: We strongly suggest users that each button should be clicked only once.

:warning: Our server has been deployed on Heroku, but high latency may occur because of Heroku, we suggest users deploy our [server](https://github.com/neluchetraru/server) locally, we describe how to deploy and run our server in Section 3.

:warning: You may see `GameManager` in the report, now it has been renamed to `GameController`. In other word, `GameController `( = `GameManager`) refers to `.\src\main\java\controller\game\GameController.java`

:warning: The  screencast shows what it looks like to run the release `Roborally-group10-v2.0`, which is a bit different from the [latest release](https://github.com/JiananAlvin/group10_roborally/releases/tag/v3.1.1) we submitted. It's good to try the latest release, which will bring you a better game experience.

## 1. Introduction

The aim of this project was to design a simplified version of the popular board game RoboRally, created by Wizards of the Coast. The original idea of the board game is to 'program' a robot to move in a factory, while avoiding dangerous obstacles. The first robot to collect all their checkpoints in order wins. Our simplified version consists of a packaged software visualised using a user-friendly GUI intended to make the gaming experience as intuitive and smooth as possible.

The general structure is

```txt
.
└───src
    ├───main
    │   ├───java
    │   │   ├───app
    │   │   ├───content
    │   │   ├───controller
    │   │   │   ├───game
    │   │   │   └───server
    │   │   ├───exception
    │   │   ├───gui
    │   │   │   ├───cover
    │   │   │   ├───game
    │   │   │   ├───login
    │   │   │   ├───room
    │   │   │   └───waiting
    │   │   ├───model
    │   │   │   └───game
    │   │   │       ├───board
    │   │   │       │   ├───map
    │   │   │       │   │   └───element
    │   │   │       │   └───mat
    │   │   │       └───card
    │   │   │           └───behaviour
    │   │   └───utils
    │   └───resources
    │       ├───images
    │       └───maps
    └───test
        ├───java
        │   └───client
        │       └───test
        └───resources
            └───featureFiles
```



## 2. How to Run 

### 2.1 Requirement

We use `maven` to manage this project. 

```bash
java == 13.0.2

# dependencies
io.cucumber.cucumber-java == 7.2.3
io.cucumber.cucumber-junit == 7.2.3
```

### 2.2 Download 

(1)  Choose the destination folder and run the command in terminal: 

``` bash
git clone https://github.com/JiananAlvin/02160_agile_OOSD_final_project.git
```

(2)  Alternatively, you can download the `.jar` file from the [latest release](https://github.com/JiananAlvin/group10_roborally/releases/tag/v3.1.1).

![image-20220502164902635](https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021905142.png)

### 2.3 How to run

(1)  go to `./src/main/java/app` run `ClientRunner.java`.

(2)  Alternatively, you can run the `.jar` file in terminal directly:

```bash
java -jar .\group10_roborally-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```



## 3. Install and run the server locally (Optional)

Our server is based on `node.js`.  So we need to install it before run our `server`. *<u>You don't need to modify our `client` application because  our client can detect the local `server` automatically with higher priority than the `server` on Heroku.</u>* 

### 3.1 Check if  `Node.js` and `npm` installed

Firstly check your version of `npm` and `Node.js` to see if you already have `Node.js` and `npm` installed and check the installed version by following commands:

```bash
node -v
npm -v
```


If some edition information is shown without any error, you can skip next step and run server directly.

### 3.2 Use Windows Node installer

If you're using Windows, use one of the installers from the [Node.js download page](https://nodejs.org/en/download/). Be sure to install the version labeled *LTS*.

![image-20220502181843234](https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021818285.png)

Once it has been installed, run the command in section `3.1` again to check if you install it successfully.

### 3.3 Run Our Server

Firstly clone our [server](https://github.com/neluchetraru/server) to your PC:

```Shell
git clone https://github.com/neluchetraru/server.git
```

Then go into this folder and turn on the command line to use `npm` to install all necessary packages.

```shell
cd server
# use npm to install packages needed
npm i
```

Finally, run the serve

```shell
node .\server.js
```


The server runs successfully once you see this output :smiley:

![](https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021815551.png)



## 4. Implemented features

### 4.1 Mandatory:

- M1 - Robot's independent movement
- M2 - Interaction of the robots with the environment
- M3 - Multiplayer game
- M4 - User interface

###  4.2 Optional:

- O1 - Generation of factory layouts according to complexity criteria

- O2 - Advanced Obstacles

- O3 - Persistency layer

- O4 - Improved graphical representation

- O5 - Advanced multiplayer mode

- O6 - Timer, multithreading techniques

  

## 5. Game rules

Our version of RoboRally has somewhat different rules than the original one, so here are the ones included in our game:

### 5.1 Getting started

1. When the game starts, the player is able to enter his name, pick a robot character, choose a desired map (indicating the difficulty level of the game) and decide if he wants to create a room or join already existing one.

2. Players are placed randomly on the available initial position, which are start points, around the antenna.

3. Once the game is all setup, the programming phase starts and each player is given 9 cards from which he picks 5. Those 5 programming cards represent his moves for the round. 

4. Along with this, players will automatically be given 5 lives once they start the game, but as the factory floor is dangerous, players might easily lose them.

5. Finally, players activate their robots following the programming cards chosen strategically to get faster to all the checkpoints. The first one who gets all the checkpoint tokens in order wins.

6. The game is done if someone collects all the checkpoints in a numeric order. 

   

### 5.2 Board elements

**Note**: If a robot stays in the same location for several consecutive registers, it will only be affected by the element once. (For example: the robot moves 1 and hits the laser, thus his life is reduced by 1. In the next register is turn right, ie. robot stays on the same position with the laser, but nothing happens).

|                            Image                             |         Name of the element          | Description                                                  |
| :----------------------------------------------------------: | :----------------------------------: | ------------------------------------------------------------ |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/laser_vertical.png" width = "80px" /> |                Laser                 | reduces 1 life when the robot passes through it              |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/oil_stain.png" width = "80px" /> |              Oil stain               | reduces 2 lives when the robot passes through it             |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/rotating_gear_clockwise.png" width = "80px" /> |     Rotating gear: (1) clockwise     | rotates the robot 90 degrees when robot passes through it    |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/rotating_gear_counterclockwise.png" width = "80px" /> | Rotating gear: (2) counter clockwise | rotates the robot 90 degrees when robot passes through it    |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/wall_west.png" width = "80px" /> |                 Wall                 | stops the robot                                              |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/east_one.png" width = "80px" /> |        Conveyor belt: (green)        | transmits the robot from the beginning to the end of the conveyor belt when he moves on the belt in the same direction as the conveyor belt |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/east_two.png" width = "80px" /> |        Conveyor belt: (blue)         | moves the robot in the same direction when moved on the belt, twice fast |
| <img src="https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021905164.png" width = "80px" /> |               Charger                | gives one life to the robot (it will not exceed 5)           |
| <img src="https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021905205.png" width = "80px" /> |                 Pit                  | delivers the robot to the reboot point; lives are set to 5; lose all obtained checkpoint tokens |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/reboot_point.png" width = "80px" /> |             Reboot point             | nothing happens, if a robot passes through the reboot point  |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/start_point.png" width = "80px" /> |             Start point              | robots will be set on one of the start points by the room owner randomly |
| <img src="https://raw.githubusercontent.com/JiananAlvin/image_bed/master/202205021905179.png" width = "80px" /> |               Antenna                | decides the priority of each robot after executing each register. If they have the same distance to antenna, observe them clockwise and determine the priority |
| <img src="https://raw.githubusercontent.com/JiananAlvin/ImageBed/master/images/tiles/check_point1.png" width = "80px" /> |              Checkpoint              | robot gets a checkpoint token only when he stops on it, not when passing through it (it must follow the numeric order: 1, 2, 3) |

*If a robot moves off or is pushed off of the board, it will be delivered to the reboot point, its lives will be set to 5 and the player will lose all obtained checkpoint tokens.

### 5.3 Robot Interactions

1. **Robot Lasers**: When all players have executed the current register, each robot will simultaneously fire a laser in the direction it is facing. the range of the robot laser has no limit. Any robot in the line of sight is shot. Robot lasers cannot fire through walls or shoot more than one robot.  If a robot is shot by a laser, it will be reduced 1 life.
2. **Pushing other robots**: If a robot enters a space occupied by another robot, the robot in motion will push the other robot in the direction the pushing robot is moving until it ends its move. Robots do not change the direction they are facing when they are pushed. Robots can be pushed almost anywhere on the board, including into a pit. They can even be pushed off the side of the board! Robots cannot be pushed through walls.
