## Project Design

### Introduction

The objective of this project is to create a program that can run a collection of different cell society simulations, at least four, which may be controlled by user input. The user will have the ability to change screen size, the proportionality of different cells, including empty cells, the speed at which the simulation occurs, as well as the simulation being undergone. Such adjustments will be made available to the user through a separate “society options” screen. The project will consist of a variety of classes—including superclasses and subclasses—to maximize efficiency in accessing and modifying information. For example, the super class for cells will contain several subclasses representing the variety of different cells that may be used in the game, as well as accessor and mutator methods to retrieve information about and change the characteristics of cells, respectively. As for the primary architecture of the project, cell classes will be mostly closed, the scene controller class, which handles scene transitions, will be closed, the society class, which sets up each scene, will be closed and the society options class, previously described, will be open, as it depends upon user input. The program will utilize the scene controller to determine which simulation is being run at any given time, then consider society options to format/run the simulation according to user input, then society to execute a simulation, which consists of objects of the cell class acting in accordance with their given characteristics.

### Overview

In the front end, there will be three main classes we are working with as introduced earlier. They are SceneController, SocietySettings, and SocietySimulator. SceneController serves as the main class to handle scene transitions and any necessary information exchange between scenes. This means that scene controller will need methods that will call helper classes such as an InitialScene class or an InstructionScene class. Here the SceneController will manage displaying information in a single location. The SceneController will also have buttons that allow the user to navigate to their desired cell simulation. This allows any addition simulator to be added into the original by creating a button for itself inside SceneController. Any other interaction should be already built in.

Moving on, after a user selects a simulation through choosing a button, the SceneController should call on method to create a SocietySettings class. The SocietySettings class will have methods to create input parameters for users to edit their settings for a simulation. This will depend on the society and its characteristics; but should be fairly standardized in necessary methods that will be implemented. This could involve functions such as create text field, radio buttons, or checkboxes. Specific societies added in should have their own SocietySettings subclass created and added for an easy implementation of a new simulation.

Finally, the SocietySettings will have methods to navigate back to SceneController or move on to the SocietySimulator class. Here is where, the program will visually present the society simulation with all the parameters that the user selected. This is where we will also have most of our backend classes and functions. This includes a Cell class which handles the state of every cell; a Rules class that defines certain specifics and calculations of cell status; a HUD class that will handle all pertinent information of the current simulation.The SocietySimulator itself, will serve as the central location of all the other classes used in the simulation; however it will delegate calculations and changes to the objects themselves. This is where, we implement JavaFX timeline objects in order to create an animation of the simulation. By having a timeline, this means that our SocietySimulator will need methods that calls to every cell to check and update its status. SocietySimulator then will have to be able to graphically update changes.

The relationships are displayed as following:
![overview-img](./images/overview.jpg)

### User Interface

The user will have the ability to interact with the program before the start of every simulation. Additionally, the user will be able to return to the options menu to interact with the program at any point during the program by clicking a button to return to the options menu. The user will be able to control generally how many of each cell is in each simulation by entering a percentage, the size of the screen by entering a number for ‘Y’ in a Y x Y screen where Y is a cell, the number of empty cells in the simulation and the delay for each step in the simulation. The user will input data by means of keyboard input in response to Java FX prompt text. Limitations to user input (what constitutes bad date, empty data, etc.) will be made clear in the prompt text, and, should the user input erroneous data, the options screen will request new data to be input rather than transitioning to the specified simulation. Bad input data includes data that is out of range, empty data fields and wrong-type data (e.g. a String value in the place of an int). If all input data is valid, then the simulations will commence. The pictures below indicate what the options menu will look like:
 
![ui1](./images/InterfaceCellSociety1.JPG)

![ui2](./images/InterfaceCellSociety2.JPG)

Additionally, at any point during the simulation, the user may exit and return to the options menu, where he/she will have the ability to adjust the parameters for simulations.  

### Design Details

So our main frontend class is the SceneController class. This class is meant to be a central location to add or remove transitions in the program. For these reasons, SceneController will extend JavaFX’s application class. SceneController will have some sort of method to first create a title screen or instruction screen. This will be implemented in a separate class such as IntialScene. This class solely exists to create the user interface for a main screen. Within the scene, the SceneController will proceed to add buttons to the scene that will provide the user to see all available society simulators and proceed to a selected society simulator. For this functionality, the SceneController will create eventListeners that will lead to other methods that will initialize new instances of a SocietySetting class. In the SocietySetting class, there should be a method to create a setting page which will be returned back to SceneController. At this point, the user shall be directed to a new scene by the SceneController.

In SocietySetting, the scene it created for itself shall be passed off to another helper class such as a SettingScene which is a superclass for the specific SettingScenes for different society simulators. Here, input fields as mentioned earlier are created and placed to accept parameters for a simulation. Within, SocietySetting, it will simple sit idle and await for user inputs. Once the user decides to either move back to the main menu or run the simulation, the SocietySetting class will act based on the user’s choice. If the user wants to go back to the main menu, SocietySetting will simply make a call back to SceneController to redirect the user back to the main menu. Otherwise, the SocietySetting will still make a call back to SceneController but instead to direct the user to SocietySimulator. This is done by SocietySetting actually creating the SocietySimulator instance. It will then pass the parameters to SocietySimulator and then will return it back to SceneController to handle screen transitions.

Within SocietySimulator, we will be taking advantage of JavaFX’s timeline class. First off, since SocietySimulator also acts as another screen for the user, it will make a call to a class to create a scene for the user to see. Then it will make a call to another class to set up the HUD for the user to view any notable values. Afterwards, the SocietySimulator will generate a grid of Cells depending on user defined or predefined dimensions which will be stored in a double array. As the cells are created, their states are initialized based off user defined or random generation. From here on, SocietySimulator will work based on timeline’s step function. SocietySimulator will first provide every cell prevalent information depending on the society rules. Then, it will have all the cells update any changes necessary. In addition to updating cells, the SocietySimulator will also update the HUD. Finally, it will call a method to check if the simulation is finished running or not which is defined in the Rule class.

At this point, the helper classes will be described to explain the remaining functionality of the program. For all the scene generating classes, they will utilize JavaFX’s scene class. First it creates a new Group object that will serve as the root of the scene. Then it will create the scene, followed by a border pane. Depending on the interface, labels and input objects may be added. After this, the class will simply return the finished scene back to a main class.

Another helper class will be the Cell class. This class will rely on JavaFX’s shape class in order to manage object data while also being a graphical object. To initialize a cell, it will need it’s position, a Rules instance, and additional information depending on the society. The Cell will have a check state method that will return the current state of the cell, a update state method that will take in a list of its neighbors and change it’s state based on the Rule class it adheres to, and finally an update method that will visually update its appearance to reflect its state.

Finally a Rules class will implement specific rules for a SocietySimulator and its Cells. This will involve methods such as checkEnd, checkNeighbors, and other relevent mechanics for a society.

In the end, for every cell society, there will need to be a unique SocietySetting, SocietySimulator, Cell, and Rules in order to fully incorporate a new cell society simulation.
	
Use cases:

1. Use case 1, in SocietySimulator, a middle cell will be give a list of its neighbors be called to update its state. The cell would then use the list it is given and call on the Rules class to determine what its next state should be. The Rules class used in this use will be a subclass called GameOfLifeRules which will define a cell dead depending if there are enough living neighbors nearby but not too many living neighbors. For this case, there were either too many or too few living neighbors, therefore the cell sets its state to dead. Finally the SocietySimulator will call for the Cell to update its visuals and thus the case is resolved
2. In this case, SocietySimulator will again give a list of neighboring cells to the edge cell; however, this list will only include 5 neighboring cells due to out of bound numbers for the array of cells. From here on, it follows the same steps as use case 1; but instead the cell is initially dead and there are exactly 3 living neighbors for the Rules to allow the cell to switch to a living state. Again, SocietySimulator will call for the edge cell to finally update visually.
3. In order to move to the next generation, SocietySimulator will loop through its array of Cells. Through each iteration, it will pass the Cell its neighbors that are in bounds of the array dimensions. It will have the Cell make a call to update its state. Then, the SocietySimulator will call every cell to update its visuals.
4. Here, the user will simply need to choose the Fire simulation from the main screen. They will then be directed to the Fire settings screen. Here, the user has the opportunity to select an XML file to fill in parameters for the simulation.
5. In this case, the user can either be in the settings screen or simulation screen for Game of Life. The user then only needs to press the button to return to the main menu screen. From here, the user can select the Wator simulation to proceed to the Wator setting screen.

The design’s key goals rely on the fact of making a flexible program that is able to implement different cell society simulations. Our choice of having a single scene controller allows our program to change scenes in a central location without having to search different classes for where the scene was changed. This then allows us to only make slight changes here for any new simulation. Along with this, the choice of breaking up the simulation between settings and actual representation, allows us to procedurally set up the simulation without any errors from changing parameters on the fly. Perhaps in later iterations, we can combine the two screens into one interface; but our intentions are to create a basic and working program. Within the backend, our choices of SocietySimulation, Cell, and Rules serve to act as a base for new simulations. With these as our main classes for a simulation, it makes adding new types of simulations simple. Along with this, it makes changes or updates easier to target. Any issues with cells changing is a Rules issue; any issues with cell’s appearance is a Cell issue; and any issue with the overall simulation is a SocietySimulation issue. Our choice of any resource implementation is based largely on the fact of how useful it is for our program. Most of JavaFX implementations are nearly crucial such as scene, application, and timeline. For cell, our choice of JavaFX shapes, meets the requirement of simple graphical objects which allow versatility in design. 


### Design Considerations

First we had quite some discussion about to what extent should we rely on the JavaFX components. 
Gordon and Jacob suggested using the JavaFX Rectangle Node for the super class of a Cell, add all the Cells to the Scene, and keep a 2-d array of the cells at the same time. They would like to use Timeline for animation, which is based on changing the properties of these Rectangle Nodes over KeyFrames. 

However, Keping suggested that our Cell class be customized to the largest extent instead of inheriting from JavaFX Node. He suggested using one single Canvas in the Scene for graphics rendering, and using AnimationTimer for more customized frame animation.

Our different opinions originated from our different ways of designing and implementing the first breakout project. Gordon and Jacob were more familier with JavaFX Nodes and Timeline. Keping was more familiar with AnimationTimer. Keping argued that his approach keeps the customized Cell object away from JavaFX Node for more flexibility. It will be flexible in the sense that the cell society simulation works without using JavaFX and that AnimationTimer allows easier customized animations. Yet Gordon and Jacob argued that we are building a JavaFX application anyway and have to rely on JavaFX components whatsoever. Furthermore, we don’t need any advanced customized animation to implement with AnimationTimer. Finally we decided to stick to Gordon and Jacob’s plan, making our cell society more JavaFX flavored.

Another lengthy discussion we had about is whether to make the user able to adjust the game configuration on the fly. We looked at the pretty cool [Segragation Simluation](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/) and decided it would be useful to allow users to adjust simulation ending conditions, delay between steps, and grid size, etc. flexibly on the user interface.

Keping suggested making the configurations in the same page (Scene) as the simulation, just as [Segragation Simluation](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/), so that it is easier for the users to play with.

Nevertheless Gordon argued that tasks such as changing grid size on the fly might be tricky to implement so we had better first implement a version separating configuration adjustment from the running simulation.

Keping agreed and we decided to have a separate scene allowing for configuration adjustment before the simulation starts.



### Team Responsibilities
After setting up some basic classes and important methods together, we decided to divide the work for the next stage as follows:

* Keping: Switcing between scenes and the flow of the app.

* Jacob: Visuals for welcome, settings, and society scenes.

* Gordon: Create society structure.

Besides, each team member will also be tasked specific classes when implementing specific society simulations such as Cell subclass, Society subclass, and Rule subclass.

