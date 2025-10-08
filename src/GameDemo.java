import java.util.*;

/**
 * Demo version of the Text-Based Adventure Game
 * This version simulates gameplay to demonstrate all features
 */
public class GameDemo {
    private Player player;
    private Map<String, Location> locations;
    
    public GameDemo() {
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
        player = new Player("Demo Adventurer", startLocation);
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
        Location treasureRoom = locations.get("treasure_room");
        
        // Forest connections
        forest.addConnection("north", village);
        forest.addConnection("east", cave);
        forest.addConnection("west", tower);
        
        // Village connections
        village.addConnection("south", forest);
        
        // Cave connections
        cave.addConnection("west", forest);
        cave.addConnection("north", treasureRoom);
        
        // Tower connections
        tower.addConnection("east", forest);
        tower.addConnection("in", towerInside);
        
        // Tower inside connections
        towerInside.addConnection("out", tower);
        
        // Treasure room connections
        treasureRoom.addConnection("south", cave);
    }
    
    /**
     * Run the demo
     */
    public void runDemo() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("    Text-Based Adventure Game DEMO");
        System.out.println("═══════════════════════════════════════════");
        System.out.println();
        System.out.println("This demo will simulate a complete playthrough");
        System.out.println("showing all the game mechanics in action!");
        System.out.println();
        
        // Step 1: Starting location
        displayStep("STEP 1: Starting in the Mysterious Forest");
        displayLocation();
        displayStatus();
        
        // Step 2: Look around and take stick
        displayStep("STEP 2: Looking around and taking the stick");
        simulateCommand("look");
        simulateCommand("take stick");
        displayStatus();
        
        // Step 3: Go to village
        displayStep("STEP 3: Moving to the village");
        simulateCommand("go north");
        displayLocation();
        
        // Step 4: Collect key and potion from village
        displayStep("STEP 4: Collecting items from the village");
        simulateCommand("take key");
        simulateCommand("take potion");
        displayStatus();
        
        // Step 5: Go to cave
        displayStep("STEP 5: Exploring the cave");
        simulateCommand("go south");  // Back to forest
        simulateCommand("go east");   // To cave
        displayLocation();
        
        // Step 6: Get sword and torch
        displayStep("STEP 6: Collecting weapons from the cave");
        simulateCommand("take sword");
        simulateCommand("take torch");
        displayStatus();
        
        // Step 7: Use potion
        displayStep("STEP 7: Using the healing potion");
        simulateCommand("use potion");
        displayStatus();
        
        // Step 8: Go to tower
        displayStep("STEP 8: Heading to the Ancient Tower");
        simulateCommand("go west");  // Back to forest
        simulateCommand("go west");  // To tower
        displayLocation();
        
        // Step 9: Try to enter tower without key (demonstrate failure)
        displayStep("STEP 9: Attempting to enter tower (will show key requirement)");
        System.out.println("Command: go in");
        if (player.hasItem("key")) {
            System.out.println("You use the golden key to unlock the tower door...");
            player.move("in");
            System.out.println("The door creaks open, revealing the tower's mystical interior!");
        } else {
            System.out.println("The tower door is locked. You need a key to enter.");
        }
        
        // Step 10: Enter tower with key
        displayStep("STEP 10: Using the key to enter the tower");
        displayLocation();
        
        // Step 11: Get spellbook
        displayStep("STEP 11: Collecting the ancient spellbook");
        simulateCommand("take spellbook");
        simulateCommand("use spellbook");
        displayStatus();
        
        // Step 12: Go to treasure room
        displayStep("STEP 12: Finding the treasure room");
        simulateCommand("go out");   // Exit tower
        simulateCommand("go east");  // To forest
        simulateCommand("go east");  // To cave
        simulateCommand("go north"); // To treasure room
        
        // Victory!
        displayStep("🏆 VICTORY! You found the legendary treasure!");
        System.out.println("★★★ CONGRATULATIONS! ★★★");
        System.out.println("You have discovered the legendary treasure!");
        System.out.println("The room is filled with unimaginable riches!");
        player.setGameWon(true);
        
        System.out.println();
        System.out.println("═══════════════════════════════════════════");
        System.out.println("           DEMO COMPLETE!");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("This demonstrates all the key features:");
        System.out.println("✓ Location navigation");
        System.out.println("✓ Item collection and management");
        System.out.println("✓ Inventory system");
        System.out.println("✓ Item usage mechanics");
        System.out.println("✓ Conditional access (key required for tower)");
        System.out.println("✓ Health and status tracking");
        System.out.println("✓ Win condition achievement");
        System.out.println();
        System.out.println("The full interactive game supports all commands:");
        System.out.println("go, look, take, drop, use, inventory, status, help, quit");
    }
    
    private void displayStep(String step) {
        System.out.println();
        System.out.println("─── " + step + " ───");
        System.out.println();
    }
    
    private void displayLocation() {
        Location current = player.getCurrentLocation();
        System.out.println("═══ " + current.getName() + " ═══");
        System.out.println(current.getFullDescription());
        System.out.println();
    }
    
    private void displayStatus() {
        System.out.println("🎒 " + player.getStatus());
        System.out.println();
    }
    
    private void simulateCommand(String command) {
        System.out.println("Command: " + command);
        String[] parts = command.split("\\s+", 2);
        String cmd = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";
        
        switch (cmd) {
            case "look":
                if (arg.isEmpty()) {
                    System.out.println(player.getCurrentLocation().getFullDescription());
                } else {
                    Item item = player.getCurrentLocation().getItem(arg);
                    if (item == null) item = player.getInventoryItem(arg);
                    if (item != null) {
                        System.out.println(item.getDescription());
                    } else {
                        System.out.println("You don't see a " + arg + " here.");
                    }
                }
                break;
            case "take":
                if (!arg.isEmpty()) {
                    Item item = player.getCurrentLocation().getItem(arg);
                    if (item != null && item.canTake()) {
                        player.getCurrentLocation().removeItem(item);
                        player.addItem(item);
                        System.out.println("You take the " + arg + ".");
                    } else if (item != null) {
                        System.out.println("You can't take the " + arg + ".");
                    } else {
                        System.out.println("There's no " + arg + " here.");
                    }
                }
                break;
            case "go":
                if (!arg.isEmpty()) {
                    if (player.move(arg)) {
                        String locationName = player.getCurrentLocation().getName();
                        if (locationName.equals("Hidden Treasure Room")) {
                            System.out.println("\\n★★★ CONGRATULATIONS! ★★★");
                            System.out.println("You have discovered the legendary treasure!");
                            player.setGameWon(true);
                        }
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
                break;
            case "use":
                if (!arg.isEmpty()) {
                    String result = player.useItem(arg);
                    System.out.println(result);
                }
                break;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        GameDemo demo = new GameDemo();
        demo.runDemo();
    }
}