### Design Refactoring:

1. The implementation of cell update should be inside each Cell subclass (like `WatorCell`). Because we want to "tell it to update", instead of letting someone else (the `WatorRule`) to update it. By writing the update implementation inside `WatorCell`, we have access to all the variables needed. If we implement update in `WatorRule`, then we have to `get` variables from `WatorCell` and `set` values to it.

**Are you saying that we now need to make a cell subclass for each simulation? Essentially what I suggested since the beginning?**

2. When `WatorCell` updates, it has to have a list of neighbors. The neighbors should be generated from `Grid` and passed to the `udpate()` method (that's what we are doing), since only `Grid` knows who the neighbors are.

3. When `WatorCell` receives this list of neighbors, they'd better be a list of `WatorCell`s, since during the `update()` the current `WatorCell` has to get and set the `WatorCell-specific` variables of its neighbors. If it receives a list of `Cell`s, then `Cell` doesn't have attributes like `energy`, and it's better to avoid casting. So the `getNeighbors()` method in `Grid` should return a list of `WatorCell`. We can achieve this by making `Grid` takes a generic type, which means it is `Grid<T>`. While we construct our `Grid<T>`, put in their the most specific type of cells, that is, `WatorCell` instead of `Cell`. 

4. For different kinds of grids, square, triangle, or hexagon, we can make `SquareGrid<T>`, `TriangleGrid<T>`, `HexagonGrid<T>`. They'd better be different classes (instead of different methods) since many things are going to differ among the three kinds of shapes:
  (1). `getNeighbors()`.
  (2). Different shapes of cells... We still haven't made an agreement on how to do this, I'll talk about it just below.

5. I think our `Cell` shouldn't extend JavaFX `Node` (and thus `Society` shouldn't extend a `Group`). I insist on "drawing" our `Cell` on the canvas, for the following reasons:
  (1). Right now `Polygon` seems to be able to handle this, but what if we need to draw a circle. If to add some feature, we have to change the super class, then the code is definitely bad design, since changing the super class means changing all subclasses and sub-subclasses.
  (2). Actually extending `Polygon` is not sufficient her e. The `sugar` simulation requires us to draw a circle on a square ground.
  (3). We can let the `Cell` contain different `Shapes`, but it still is not flexible enough. If we want to add a circle on a square, we have to pass around a bunch of JavaFX `Node`s. And deleting `Node` from a `Group` doesn't seem an efficient way of updating the view. 
  (4). Using `Canvas` to draw is good because it separates our backend `Cell` away from our frontend view. This way our `Cell` doesn't contain any information about positions, shapes, and colors, so we have cleaner constructors for cells.
  (5). We can have `SquareGrid<T>`, `TriangleGrid<T>`, and `HexagonGird<T>` to handle the shapes of our cells, and the `Cell`s can get colors configuration from somewhere else. We pass a different list of colors and the color scheme of the cells can change on the run.

**(1)If you read the new implementations, we are only required to add in hexagonal and triangular shapes. These shapes are special since they have different arrangements of neighbors. For circles, it is arbitrary to accurately define a neighbor of circles since this would solely depend on the arrangement of the grid itself. This is why we SHOULD NOT have to worry about cells being circular. I took a cursory look at sugar scape and the circles only occupy the square-like tiles. Nowhere is it necessary for them to be circles. For example, look at Wa-Tor, in the examples they were circular shapes; however, you can still treat them like squares and the simulation is the same. In addition, if you really want it to be visually simular, we can always implement the JavaFX's circle class. Again, I do understand where you're coming from that we can simply just draw on the shapes; however, how would you be able to detect a user clicking on a shape in canvas? Would you have to create a listener event for every single point ont he canvas? On the other hand, every node in JavaFX automatically has the capabilities of calling a listener when clicked on. I still side with the choice of using JavaFX nodes to visually represent our cells.**
**(2)I think I forgot to mention this before, but I do agree now that perhaps we shouldn't have cells extend directly from javafx classes. Instead I would prefer cells to have a private instance of a node such as rectangle or polygon**

6. Perhaps we can store all the view related configurations in a single class, just to make the code cleaner. It could be called `ViewConfig`, which contains total width and height of our society, a list of colors for different states, and a list of string mapping to different cell shapes (so that we can draw circle cells on a square grid), and perhaps more...


# cellsociety

A seemingly useful [git repo](https://github.com/GollyGang/ruletablerepository)

CompSci 308 Cell Society Project

The MVC pattern is mainly for web applications. Since the web pages have to get data through html request and response, it is necessary to have a Controller to routes the requests and send back the data. JavaFX can adopt the pure MVC design pattern, but that requires using FXML (a pure view without controlling method, see this [JavaFX Best Practices](http://docs.oracle.com/javafx/2/best_practices/jfxpub-best_practices.htm)). I don't think we have to pay the extra learning cost to use FXML, since it will definitely not be used in the final project (most fun part of the class).

Therefore, our JavaFX cell-society don't have to have an independent Controller class, but it is essential that we separate Model from View. We can put the controller sort of either in Model or in View. If we have a reference of Model in View, and call `model.getData()` in View, then renders data, then it equivalently is a Controller inside View. If we have a reference of View in Model, and call `view.showData()` in Model, then the Controller is inside Model.

As for this JavaFX project, I suggest that we put Controller inside Model, which effectively means something like:
```
private Model model;

button.setOnMouseClicked(e -> {
    render(model.getData());
});

/** Receives data from the model and update the View.
 */
public render(Data data) {
    // TODO
}
```

That being said, I read some [articles online](http://gamedev.stackexchange.com/questions/3426/why-are-mvc-tdd-not-employed-more-in-game-architecture) and changed my mind. I think currently we are doing good by extending Society from Group and extending Cell from Rectangle.



A better git log, add this to ~/.gitconfig, and then use `git lg`:
```
[alias]
        lg  = log --branches --remotes --tags --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(bold yellow)%d%C(reset)'

```
