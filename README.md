Design doc
========

The n-Puzzle app will consist of three different activities. The activities are explained in more detail 
below.

Activity 1 ImageSelection:
--------

This first activity will be shown when the user opens the app. This activity will contain a list of 
pictures. These pictures will be shown by name and a small icon of that picture. In order to implement this
list, a ListView will be used and an Adapter containing all the items of the list. When the user clicks on
one of the items the second activity will be opened.

![First Activity](https://github.com/jelleswester/npuzzle/blob/master/design_doc_img/start_screen_choose_picture(1).png)

Activity 2 GamePlay:
--------

This activity will first show the chosen picture and a countdown from 3 until 1. Thereafter the gameboard
will be loaded in order for the user to start playing the game. The default difficulty level will be medium.

![Second Activity](https://github.com/jelleswester/npuzzle/blob/master/design_doc_img/third_screen_puzzle.png)

In order to create the board, the Bitmap class will be used. The functions of that class that will be used 
include BitmapFactory.decodeResource, Bitmap.createScaledBitMap and Bitmap.createBitmap.
BitmapFactory.decodeResource will be used in order to load the picture. Bitmap.createScaledBitMap will be 
used to scale the image. Bitmap.createBitmap will be used to crop the image and thus to split the image 
in the needed tiles.

Two useful functions that will be used include view.setTag(Object o) and view.getTag() functions. Using these
functions, some additional data can be added to each tile. For instance the number of the tile and its x and y
location can be added. In order to display the tiles, a TableLayout, GridLayout and LinearLayour can be used.

Each time a user clicks on a tile, there must be some check where this tile is located as well as the blank 
tile. When these two are next to each other (either above, underneath, to the left or to the right), they 
should swap. In order to swap two tiles, their ImageViews can be swapped or the images within the Imageviews,
leaving the ImageViews themselves on their fixed positions. 

If the user decides to leave the app while the game is not finished, the current state of the board, the number
of moves made and the difficulty level should be saved/remembered. When the user leaves the app, onPause() is 
called and therefore the game will be saved within onPause(). When the user opens the app again onCreate() or 
onResume() are called and therefore within these two the saved data should be retrieved. In order to save and 
retrieve the data, sharedPreferences and sharedPreferencesEditor will be used. 

When the user clicks on the menu button on his Android device, a menu will pop up. This menu will contain four
buttons including a restart button and three button for the multiple difficulty levels (easy, medium, hard).
When the user clicks on the restart button, the board will be reshuffled. When the user clicks on one of 
the difficulty buttons, the board will be reshuffled and the size of the board will correspond to the chosen
level. In order to create this menu, the onCreateOptionsMenu() and onOptionsItemSelected() objects will be used.

![Menu](https://github.com/jelleswester/npuzzle/blob/master/design_doc_img/third_screen_%2Bmenu.png)

In order to check whether the game is won, the order of the tiles should be checked after each swap. Therefore
each tile will receive a number using view.setTag() in order to check the order. When the user wins the game, a
new activity will be opened.

Activity 3 YouWin:
--------

This activity will contain two TextViews and an ImageView. The TextViews will give a message that the user has
won the game and the ImageView will contain the solved puzzle by the user. When the user clicks anywhere in this 
activity, activity 1 will be opened in order for the user to start a new game.

![Third Activity](https://github.com/jelleswester/npuzzle/blob/master/design_doc_img/third_screen_puzzle.png)
