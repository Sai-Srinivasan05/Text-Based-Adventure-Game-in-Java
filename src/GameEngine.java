import java.awt.Color;
import java.util.*;

/**
 * Game engine that adapts the original game logic for GUI interface
 * Handles game state, command processing, and GUI updates
 */
public class GameEngine {
    private AdventureGameGUI gui;
    private Player player;
    private Map<String, Location> locations;
    private boolean gameRunning;
    
    // Colors for different message types - Improved readability
    private static final Color ACCENT_COLOR = new Color(0, 102, 204);     // Blue for titles
    private static final Color SUCCESS_COLOR = new Color(0, 150, 0);      // Green for success
    private static final Color ERROR_COLOR = new Color(220, 20, 60);      // Red for errors
    private static final Color STORY_COLOR = new Color(139, 69, 19);      // Brown for story
    private static final Color COMMAND_COLOR = new Color(75, 0, 130);     // Purple for commands
    
    public GameEngine(AdventureGameGUI gui) {
        this.gui = gui;
        this.gameRunning = false;
        this.locations = new HashMap<>();
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
        player = new Player("GUI Adventurer", startLocation);
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
     * Start the game
     */
    public void startGame() {
        gameRunning = true;
        displayLocation();
        updateGUIDisplays();
    }
    
    /**
     * Process user commands
     */
    public void processCommand(String input) {
        if (input.isEmpty()) {
            return;
        }
        
        String[] parts = input.split("\\s+", 2);
        String command = parts[0].toLowerCase();
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
                gui.displayText("I don't understand that command. Type 'help' for available commands.\n", ERROR_COLOR);
        }
        
        updateGUIDisplays();
        checkGameState();
    }
    
    /**
     * Handle movement commands
     */
    private void handleMovement(String direction) {
        if (direction.isEmpty()) {
            gui.displayText("Go where? Specify a direction.\n", ERROR_COLOR);
            return;
        }
        
        // Special case for entering the tower
        if (player.getCurrentLocation().getName().equals("Ancient Tower") && 
            direction.equals("in")) {
            if (player.hasItem("key")) {
                gui.displayText("You use the golden key to unlock the tower door...\n", STORY_COLOR);
                player.move(direction);
                gui.displayText("The door creaks open, revealing the tower's mystical interior!\n", SUCCESS_COLOR);
                displayLocation();
            } else {
                gui.displayText("The tower door is locked. You need a key to enter.\n", ERROR_COLOR);
            }
            return;
        }
        
        // Special case for dragon's lair
        if (direction.equals("east") && 
            player.getCurrentLocation().getName().equals("Abandoned Village")) {
            gui.displayText("As you approach the dragon's lair, you hear the sound of deep breathing...\n", STORY_COLOR);
            if (player.hasItem("sword")) {
                gui.displayText("Fortunately, you have a sword to defend yourself!\n", SUCCESS_COLOR);
                player.move(direction);
                displayLocation();
            } else {
                gui.displayText("Without a weapon, it would be suicide to enter. You need a sword!\n", ERROR_COLOR);
                return;
            }
            return;
        }
        
        if (player.move(direction)) {
            displayLocation();
            // Special location events
            String locationName = player.getCurrentLocation().getName();
            if (locationName.equals("Dragon's Lair")) {
                handleDragonEncounter();
            } else if (locationName.equals("Hidden Treasure Room")) {
                handleTreasureRoom();
            }
        } else {
            gui.displayText("You can't go that way.\n", ERROR_COLOR);
        }
    }
    
    /**
     * Handle dragon encounter
     */
    private void handleDragonEncounter() {
        gui.displayText("\nSUDDENLY, THE DRAGON AWAKENS!\n", ERROR_COLOR);
        gui.displayText("The massive beast rears its head and breathes fire in your direction!\n", STORY_COLOR);
        
        if (player.hasItem("sword")) {
            gui.displayText("You quickly draw your sword and prepare for battle!\n", SUCCESS_COLOR);
            gui.displayText("After an epic fight, you manage to defeat the dragon!\n", SUCCESS_COLOR);
            gui.displayText("The dragon collapses, leaving behind a path to its treasure hoard.\n", STORY_COLOR);
            
            // Add special reward
            Item dragonGold = new Item("dragon gold", "A bag of precious dragon gold", true);
            player.getCurrentLocation().addItem(dragonGold);
        } else {
            gui.displayText("Without a weapon, you cannot defend yourself!\n", ERROR_COLOR);
            player.takeDamage(50);
            gui.displayText("The dragon's flames sear your flesh! (-50 health)\n", ERROR_COLOR);
            if (!player.isAlive()) {
                gui.displayText("You have been slain by the dragon!\n", ERROR_COLOR);
            }
        }
    }
    
    /**
     * Handle treasure room discovery
     */
    private void handleTreasureRoom() {
        gui.displayText("\n★★★ CONGRATULATIONS! ★★★\n", ACCENT_COLOR);
        gui.displayText("You have discovered the legendary treasure!\n", SUCCESS_COLOR);
        gui.displayText("The room is filled with unimaginable riches!\n", STORY_COLOR);
        player.setGameWon(true);
    }
    
    /**
     * Handle look command
     */
    private void handleLook(String target) {
        if (target.isEmpty()) {
            displayLocation();
        } else {
            Location current = player.getCurrentLocation();
            Item item = current.getItem(target);
            if (item == null) {
                item = player.getInventoryItem(target);
            }
            
            if (item != null) {
                gui.displayText(item.getDescription() + "\n");
            } else {
                gui.displayText("You don't see a " + target + " here.\n", ERROR_COLOR);
            }
        }
    }
    
    /**
     * Handle take command
     */
    private void handleTake(String itemName) {
        if (itemName.isEmpty()) {
            gui.displayText("Take what?\n", ERROR_COLOR);
            return;
        }
        
        Location current = player.getCurrentLocation();
        Item item = current.getItem(itemName);
        
        if (item == null) {
            gui.displayText("There's no " + itemName + " here.\n", ERROR_COLOR);
        } else if (!item.canTake()) {
            gui.displayText("You can't take the " + itemName + ".\n", ERROR_COLOR);
        } else {
            current.removeItem(item);
            player.addItem(item);
            gui.displayText("You take the " + itemName + ".\n", SUCCESS_COLOR);
        }
    }
    
    /**
     * Handle drop command
     */
    private void handleDrop(String itemName) {
        if (itemName.isEmpty()) {
            gui.displayText("Drop what?\n", ERROR_COLOR);
            return;
        }
        
        Item item = player.getInventoryItem(itemName);
        if (item == null) {
            gui.displayText("You don't have a " + itemName + ".\n", ERROR_COLOR);
        } else {
            player.removeItem(item);
            player.getCurrentLocation().addItem(item);
            gui.displayText("You drop the " + itemName + ".\n", SUCCESS_COLOR);
        }
    }
    
    /**
     * Handle use command
     */
    private void handleUse(String itemName) {
        if (itemName.isEmpty()) {
            gui.displayText("Use what?\n", ERROR_COLOR);
            return;
        }
        
        String result = player.useItem(itemName);
        if (result.contains("heal") || result.contains("+25")) {
            gui.displayText(result + "\n", SUCCESS_COLOR);
        } else if (result.contains("can't") || result.contains("don't have")) {
            gui.displayText(result + "\n", ERROR_COLOR);
        } else {
            gui.displayText(result + "\n");
        }
    }
    
    /**
     * Handle inventory command
     */
    private void handleInventory() {
        gui.displayText(player.getInventoryDisplay() + "\n");
    }
    
    /**
     * Handle status command
     */
    private void handleStatus() {
        gui.displayText(player.getStatus() + "\n");
    }
    
    /**
     * Display help message
     */
    private void displayHelp() {
        String helpText = "Available commands:\n" +
            "• go <direction>  - Move in a direction (north, south, east, west, in, out)\n" +
            "• look [item]     - Examine your current location or a specific item\n" +
            "• take <item>     - Pick up an item\n" +
            "• drop <item>     - Drop an item from your inventory\n" +
            "• use <item>      - Use an item from your inventory\n" +
            "• inventory       - Check your inventory\n" +
            "• status          - Check your health and inventory\n" +
            "• help            - Display this help message\n" +
            "• quit            - Exit the game\n\n" +
            "TIP: You can also use the direction buttons and action buttons!\n";
        
        gui.displayText(helpText);
    }
    
    /**
     * Handle quit command
     */
    private void handleQuit() {
        gui.displayText("Thank you for playing! Goodbye!\n", ACCENT_COLOR);
        System.exit(0);
    }
    
    /**
     * Display current location
     */
    private void displayLocation() {
        Location current = player.getCurrentLocation();
        gui.displayText("═══ " + current.getName() + " ═══\n", ACCENT_COLOR);
        gui.displayText(current.getFullDescription() + "\n\n");
    }
    
    /**
     * Update GUI displays (status and inventory)
     */
    private void updateGUIDisplays() {
        gui.updateStatus("Health: " + player.getHealth() + "/100");
        
        if (player.getInventory().isEmpty()) {
            gui.updateInventory("Your inventory is empty.");
        } else {
            StringBuilder sb = new StringBuilder("Your inventory:\n");
            for (Item item : player.getInventory()) {
                sb.append("• ").append(item.getName()).append("\n");
            }
            gui.updateInventory(sb.toString());
        }
    }
    
    /**
     * Check for win/lose conditions
     */
    private void checkGameState() {
        if (player.isGameWon()) {
            gui.showGameEndDialog(
                "★ VICTORY! ★\n\nYou have successfully completed your quest!\nThe legendary treasure is yours!",
                "Game Complete"
            );
        } else if (player.isGameLost() || !player.isAlive()) {
            gui.showGameEndDialog(
                "☠ GAME OVER ☠\n\nYour adventure has come to an unfortunate end.\nBetter luck next time, brave adventurer!",
                "Game Over"
            );
        }
    }
}