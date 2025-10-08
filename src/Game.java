import java.util.*;

/**
 * Main game class for the Text-Based Adventure Game
 * Handles game initialization, command parsing, and game loop
 */
public class Game {
    private Scanner scanner;
    private Player player;
    private Map<String, Location> locations;
    private boolean gameRunning;
    
    public Game() {
        scanner = new Scanner(System.in);
        gameRunning = false;
        locations = new HashMap<>();
        initializeGame();
    }
    
    /**
     * Initialize the game world, locations, items, and player
     */
    private void initializeGame() {
        createLocations();
        createItems();
        connectLocations();
        
        // Create player and place in starting location
        Location startLocation = locations.get("forest");
        player = new Player("Adventurer", startLocation);
        startLocation.setVisited(true);
    }
    
    /**
     * Create all game locations
     */
    private void createLocations() {
        // Forest (Starting location)
        locations.put("forest", new Location("Mysterious Forest",
            "You find yourself in a dark, mysterious forest. Ancient trees tower above you, " +
            "their branches creating a canopy that blocks most of the sunlight. Strange sounds " +
            "echo from the depths of the woods."));
        
        // Village
        locations.put("village", new Location("Abandoned Village",
            "Before you lies an abandoned village. The houses are in ruins, with broken windows " +
            "and doors hanging off their hinges. Weeds grow through the cobblestone streets. " +
            "Despite its desolate appearance, you sense that valuable items might be hidden here."));
        
        // Cave
        locations.put("cave", new Location("Dark Cave",
            "You enter a damp, dark cave. Water drips from stalactites above, creating echoing " +
            "sounds throughout the cavern. The air is cold and musty. Deep within the shadows, " +
            "you can make out the glint of something metallic."));
        
        // Tower
        locations.put("tower", new Location("Ancient Tower",
            "An imposing stone tower rises before you. Its walls are covered in mysterious runes " +
            "that seem to glow faintly in the darkness. A heavy wooden door blocks the entrance, " +
            "secured with an ornate lock."));
        
        // Tower Interior
        locations.put("tower_inside", new Location("Tower Interior",
            "Inside the tower, mystical energy fills the air. Ancient books and scrolls line the " +
            "walls, and a glowing crystal sits atop a pedestal in the center of the room. This " +
            "appears to be the lair of a powerful wizard!"));
        
        // Dragon's Lair
        locations.put("dragon_lair", new Location("Dragon's Lair",
            "You've entered the lair of an ancient dragon! The cavern is filled with piles of " +
            "gold and precious gems. In the center, a massive dragon sleeps on a bed of treasure. " +
            "One wrong move could wake the beast..."));
        
        // Treasure Room
        locations.put("treasure_room", new Location("Hidden Treasure Room",
            "You've discovered a hidden treasure room! Chests overflowing with gold and jewels " +
            "surround you. Ancient artifacts and magical items gleam in the torchlight. You've " +
            "found the legendary treasure!"));
    }
    
    /**
     * Create all game items and place them in locations
     */
    private void createItems() {
        // Forest items
        Item stick = new Item("stick", "A sturdy wooden stick", true);
        locations.get("forest").addItem(stick);
        
        // Village items  
        Item key = new Item("key", "An ornate golden key with mystical engravings", true, true,
            "The key glows briefly as you hold it. It seems to resonate with magical energy.");
        Item potion = new Item("potion", "A small bottle containing a red healing potion", true, true,
            "You drink the potion and feel your wounds healing. (+25 health)");
        locations.get("village").addItem(key);
        locations.get("village").addItem(potion);
        
        // Cave items
        Item sword = new Item("sword", "A sharp steel sword with intricate engravings", true, true,
            "You raise the sword, feeling its balanced weight. You're ready for battle!");
        Item torch = new Item("torch", "A burning torch that provides light", true, true,
            "The torch illuminates the dark corners around you.");
        locations.get("cave").addItem(sword);
        locations.get("cave").addItem(torch);
        
        // Tower items
        Item spellbook = new Item("spellbook", "An ancient book of powerful spells", true, true,
            "You flip through the pages, learning powerful magic spells!");
        locations.get("tower_inside").addItem(spellbook);
        
        // Non-takeable items
        Item door = new Item("door", "A heavy wooden door with an ornate lock", false);
        locations.get("tower").addItem(door);
        
        Item dragon = new Item("dragon", "A massive sleeping dragon", false);
        locations.get("dragon_lair").addItem(dragon);
        
        Item treasure = new Item("treasure", "Piles of gold, gems, and precious artifacts", false);
        locations.get("treasure_room").addItem(treasure);
    }
    
    /**
     * Connect locations to create the game map
     */
    private void connectLocations() {
        Location forest = locations.get("forest");
        Location village = locations.get("village");
        Location cave = locations.get("cave");
        Location tower = locations.get("tower");
        Location towerInside = locations.get("tower_inside");
        Location dragonLair = locations.get("dragon_lair");
        Location treasureRoom = locations.get("treasure_room");
        
        // Forest connections
        forest.addConnection("north", village);
        forest.addConnection("east", cave);
        forest.addConnection("west", tower);
        
        // Village connections
        village.addConnection("south", forest);
        village.addConnection("east", dragonLair);
        
        // Cave connections
        cave.addConnection("west", forest);
        cave.addConnection("north", treasureRoom);
        
        // Tower connections
        tower.addConnection("east", forest);
        tower.addConnection("in", towerInside);
        
        // Tower inside connections
        towerInside.addConnection("out", tower);
        
        // Dragon's lair connections
        dragonLair.addConnection("west", village);
        
        // Treasure room connections
        treasureRoom.addConnection("south", cave);
    }
    
    /**
     * Start the main game loop
     */
    public void start() {
        gameRunning = true;
        displayWelcome();
        
        while (gameRunning && !player.isGameWon() && !player.isGameLost()) {
            displayLocation();
            String input = getInput();
            processCommand(input);
            
            // Check win/lose conditions
            checkGameState();
        }
        
        displayGameEnd();
        scanner.close();
    }
    
    /**
     * Display welcome message and instructions
     */
    private void displayWelcome() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("    Welcome to the Text-Based Adventure!");
        System.out.println("═══════════════════════════════════════════");
        System.out.println();
        System.out.println("You are an adventurer seeking the legendary treasure hidden");
        System.out.println("somewhere in these mystical lands. Your quest will take you");
        System.out.println("through forests, villages, caves, and ancient towers.");
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("  go <direction>  - Move in a direction (north, south, east, west, in, out)");
        System.out.println("  look           - Examine your current location");
        System.out.println("  take <item>    - Pick up an item");
        System.out.println("  drop <item>    - Drop an item from your inventory");
        System.out.println("  use <item>     - Use an item from your inventory");
        System.out.println("  inventory      - Check your inventory");
        System.out.println("  status         - Check your health and inventory");
        System.out.println("  help           - Display this help message");
        System.out.println("  quit           - Exit the game");
        System.out.println();
        System.out.println("Your adventure begins now...");
        System.out.println();
    }
    
    /**
     * Display current location information
     */
    private void displayLocation() {
        Location current = player.getCurrentLocation();
        System.out.println("═══ " + current.getName() + " ═══");
        System.out.println(current.getFullDescription());
        System.out.println();
    }
    
    /**
     * Get user input
     */
    private String getInput() {
        System.out.print("> ");
        return scanner.nextLine().trim().toLowerCase();
    }
    
    /**
     * Process user commands
     */
    private void processCommand(String input) {
        if (input.isEmpty()) {
            return;
        }
        
        String[] parts = input.split("\\s+", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";
        
        switch (command) {
            case "go":
            case "move":
                handleMovement(argument);
                break;
            case "look":
            case "examine":
                handleLook(argument);
                break;
            case "take":
            case "get":
            case "pick":
                handleTake(argument);
                break;
            case "drop":
                handleDrop(argument);
                break;
            case "use":
                handleUse(argument);
                break;
            case "inventory":
            case "inv":
            case "items":
                handleInventory();
                break;
            case "status":
            case "stats":
                handleStatus();
                break;
            case "help":
            case "commands":
                displayHelp();
                break;
            case "quit":
            case "exit":
                handleQuit();
                break;
            default:
                System.out.println("I don't understand that command. Type 'help' for available commands.");
        }
        System.out.println();
    }
    
    /**
     * Handle movement commands
     */
    private void handleMovement(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Go where? Specify a direction.");
            return;
        }
        
        // Special case for entering the tower
        if (player.getCurrentLocation().getName().equals("Ancient Tower") && 
            direction.equals("in")) {
            if (player.hasItem("key")) {
                System.out.println("You use the golden key to unlock the tower door...");
                player.move(direction);
                System.out.println("The door creaks open, revealing the tower's mystical interior!");
            } else {
                System.out.println("The tower door is locked. You need a key to enter.");
            }
            return;
        }
        
        // Special case for dragon's lair
        if (direction.equals("east") && 
            player.getCurrentLocation().getName().equals("Abandoned Village")) {
            System.out.println("As you approach the dragon's lair, you hear the sound of deep breathing...");
            if (player.hasItem("sword")) {
                System.out.println("Fortunately, you have a sword to defend yourself!");
                player.move(direction);
            } else {
                System.out.println("Without a weapon, it would be suicide to enter. You need a sword!");
                return;
            }
            return;
        }
        
        if (player.move(direction)) {
            // Special location events
            String locationName = player.getCurrentLocation().getName();
            if (locationName.equals("Dragon's Lair")) {
                handleDragonEncounter();
            } else if (locationName.equals("Hidden Treasure Room")) {
                handleTreasureRoom();
            }
        } else {
            System.out.println("You can't go that way.");
        }
    }
    
    /**
     * Handle dragon encounter
     */
    private void handleDragonEncounter() {
        System.out.println("\nSUDDENLY, THE DRAGON AWAKENS!");
        System.out.println("The massive beast rears its head and breathes fire in your direction!");
        
        if (player.hasItem("sword")) {
            System.out.println("You quickly draw your sword and prepare for battle!");
            System.out.println("After an epic fight, you manage to defeat the dragon!");
            System.out.println("The dragon collapses, leaving behind a path to its treasure hoard.");
            
            // Add special reward
            Item dragonGold = new Item("dragon gold", "A bag of precious dragon gold", true);
            player.getCurrentLocation().addItem(dragonGold);
        } else {
            System.out.println("Without a weapon, you cannot defend yourself!");
            player.takeDamage(50);
            System.out.println("The dragon's flames sear your flesh! (-50 health)");
            if (!player.isAlive()) {
                System.out.println("You have been slain by the dragon!");
            }
        }
    }
    
    /**
     * Handle treasure room discovery
     */
    private void handleTreasureRoom() {
        System.out.println("\n★★★ CONGRATULATIONS! ★★★");
        System.out.println("You have discovered the legendary treasure!");
        System.out.println("The room is filled with unimaginable riches!");
        player.setGameWon(true);
    }
    
    /**
     * Handle look command
     */
    private void handleLook(String target) {
        if (target.isEmpty()) {
            // Look around current location
            System.out.println(player.getCurrentLocation().getFullDescription());
        } else {
            // Look at specific item
            Location current = player.getCurrentLocation();
            Item item = current.getItem(target);
            if (item == null) {
                item = player.getInventoryItem(target);
            }
            
            if (item != null) {
                System.out.println(item.getDescription());
            } else {
                System.out.println("You don't see a " + target + " here.");
            }
        }
    }
    
    /**
     * Handle take command
     */
    private void handleTake(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Take what?");
            return;
        }
        
        Location current = player.getCurrentLocation();
        Item item = current.getItem(itemName);
        
        if (item == null) {
            System.out.println("There's no " + itemName + " here.");
        } else if (!item.canTake()) {
            System.out.println("You can't take the " + itemName + ".");
        } else {
            current.removeItem(item);
            player.addItem(item);
            System.out.println("You take the " + itemName + ".");
        }
    }
    
    /**
     * Handle drop command
     */
    private void handleDrop(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Drop what?");
            return;
        }
        
        Item item = player.getInventoryItem(itemName);
        if (item == null) {
            System.out.println("You don't have a " + itemName + ".");
        } else {
            player.removeItem(item);
            player.getCurrentLocation().addItem(item);
            System.out.println("You drop the " + itemName + ".");
        }
    }
    
    /**
     * Handle use command
     */
    private void handleUse(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Use what?");
            return;
        }
        
        String result = player.useItem(itemName);
        System.out.println(result);
    }
    
    /**
     * Handle inventory command
     */
    private void handleInventory() {
        System.out.println(player.getInventoryDisplay());
    }
    
    /**
     * Handle status command
     */
    private void handleStatus() {
        System.out.println(player.getStatus());
    }
    
    /**
     * Display help message
     */
    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("  go <direction>  - Move in a direction (north, south, east, west, in, out)");
        System.out.println("  look [item]     - Examine your current location or a specific item");
        System.out.println("  take <item>     - Pick up an item");
        System.out.println("  drop <item>     - Drop an item from your inventory");
        System.out.println("  use <item>      - Use an item from your inventory");
        System.out.println("  inventory       - Check your inventory");
        System.out.println("  status          - Check your health and inventory");
        System.out.println("  help            - Display this help message");
        System.out.println("  quit            - Exit the game");
    }
    
    /**
     * Handle quit command
     */
    private void handleQuit() {
        System.out.println("Thank you for playing! Goodbye!");
        gameRunning = false;
    }
    
    /**
     * Check for win/lose conditions
     */
    private void checkGameState() {
        if (!player.isAlive()) {
            player.setGameLost(true);
        }
    }
    
    /**
     * Display game end message
     */
    private void displayGameEnd() {
        System.out.println("\n═══════════════════════════════════════════");
        if (player.isGameWon()) {
            System.out.println("           ★ VICTORY! ★");
            System.out.println("You have successfully completed your quest!");
            System.out.println("The legendary treasure is yours!");
        } else if (player.isGameLost()) {
            System.out.println("           ☠ GAME OVER ☠");
            System.out.println("Your adventure has come to an unfortunate end.");
            System.out.println("Better luck next time, brave adventurer!");
        } else {
            System.out.println("Thanks for playing!");
        }
        System.out.println("═══════════════════════════════════════════");
    }
    
    /**
     * Main method to start the game
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}