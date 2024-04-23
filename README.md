# Sky Surfer

## To Do
 - [x] Create basic functioning game
 - [x] Make placeholder ui
 - [ ] Create Functioning Stats
 - [ ] Create Functioning Settings
 - [ ] Allow switching between multiple themes
 - [ ] Replace current game assest with improved visuals


## Description of Files

> ~/app/main/
> > java/com/theinnovationnation/skysurfer
> > > /ui
> > > | File              | Description | Methods |
> > > |:------------------|:------------|:--------|
> > > | GameFragment.kt  | Fragment responsible for managing the game UI and sensor events. Registers and unregisters accelerometer sensor. Handles sensor changes to control the game surface. | `onCreate(savedInstanceState: Bundle?)`, `onResume()`, `onPause()`, `onSensorChanged(event: SensorEvent)`, `onAccuracyChanged(sensor: Sensor?, accuracy: Int)`, `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?` |
> > > | SettingsFragment.kt | Fragment for displaying and handling settings options. | `onCreate(savedInstanceState: Bundle?)`, `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?` |
> > > | StatsFragment.kt    | Fragment for displaying statistics and game data. | `onCreate(savedInstanceState: Bundle?)`, `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?` |
> > .
> > > /game
> > > | File         | Description | Methods |
> > > |:-------------|:------------|:--------|
> > > | Bird.kt      | Defines the `Bird` class, representing a bird object in the game. Manages properties for position, movement, appearance, and type. | `relocate(xDistance: Int)`, `move(dy: Int)`, `speedMod(birdType: BirdType)`, `draw(canvas: Canvas)` |
> > > | SkyGame.kt   | Manages the main game logic, including initialization, updating game state, and rendering on the canvas. Provides methods for starting a new game, updating game state, and drawing game elements. | `newGame()`, `update(velocity: PointF)`, `draw(canvas: Canvas)` |
> > > | SkyThread.kt | Implements the game loop as a separate thread, continuously updating and rendering the game state. Provides methods for changing acceleration, stopping the thread, and restarting the game. | `run()`, `changeAcceleration(xForce: Float, yForce: Float)`, `stopThread()`, `shake()` |
> > > | SkyView.kt   | Custom `SurfaceView` responsible for rendering the game on the screen. Manages the game thread and provides methods for interaction, including setting text view, changing acceleration, and restarting the game. | `setTextView(textView: TextView)`, `surfaceCreated(holder: SurfaceHolder)`, `surfaceDestroyed(holder: SurfaceHolder)`, `changeAcceleration(x: Float, y: Float)`, `shake()` |
> > > | Surfer.kt    | Defines the `Surfer` class, representing the player character in the game. Manages surfer movement, collision detection, and drawing on the canvas. Includes methods for setting center position, moving the surfer, and drawing on the canvas. | `setCenter(x: Int, y: Int)`, `move(velocity: PointF, birdList: MutableList<Bird>)`, `draw(canvas: Canvas)` |
> > .
> > | File         | Description | Methods |
> > |:-------------|:------------|:--------|
> > | MainActivity.kt | Main activity responsible for managing the application's navigation and initializing the bottom navigation view. Listens for item selections and replaces fragments accordingly. | `onCreate(savedInstanceState: Bundle?)` |
> .
> > res/
> > > layout/
> > > | File                 | Description                                     |
> > > |:---------------------|:------------------------------------------------|
> > > | activity_main.xml    | Layout for the main activity, containing the `BottomNavigationView` and the fragment container. |
> > > | fragment_game.xml    | Layout for the game fragment, including the `SkyView` for rendering the game and a `TextView` for displaying height. |
> > > | fragment_settings.xml| Layout for the settings fragment, providing options for theme selection, language, and data clearing. |
> > > | fragment_stats.xml   | Layout for the stats fragment, displaying statistics such as high score, total attempts, jumps made, platforms landed on, and a leaderboard. |
> > .
> > > menu/
> > > | File                 | Description                                     |
> > > |:---------------------|:------------------------------------------------|
> > > | bottom_nav_menu.xml  | Defines the items for the bottom navigation menu, including game, stats, and settings icons. |
> > .
> > > navigation/
> > > | File                 | Description                                     |
> > > |:---------------------|:------------------------------------------------|
> > > | game_navigation.xml  | Navigation graph defining the flow between game-related fragments such as game, stats, and settings. |