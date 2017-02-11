# cellsociety

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
