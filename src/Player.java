import java.util.*;

/**
 * Represents the player in the adventure game
 * Manages player's location, inventory, and health
 */
public class Player {
    private String name;
    private Location currentLocation;
    private List<Item> inventory;
    private int health;
    private int maxHealth;
    private boolean gameWon;
    private boolean gameLost;
    
    public Player(String name, Location startingLocation) {
        this.name = name;
        this.currentLocation = startingLocation;
        this.inventory = new ArrayList<>();
        this.health = 100;
        this.maxHealth = 100;
        this.gameWon = false;
        this.gameLost = false;
    }
    
    // Move to a new location
    public boolean move(String direction) {
        Location nextLocation = currentLocation.getConnection(direction);
        if (nextLocation != null) {
            currentLocation = nextLocation;
            currentLocation.setVisited(true);
            return true;
        }
        return false;
    }
    
    // Add item to inventory
    public boolean addItem(Item item) {
        if (item.canTake()) {
            inventory.add(item);
            return true;
        }
        return false;
    }
    
    // Remove item from inventory
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }
    
    // Get item from inventory by name
    public Item getInventoryItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    
    // Check if player has an item
    public boolean hasItem(String itemName) {
        return getInventoryItem(itemName) != null;
    }
    
    // Use an item
    public String useItem(String itemName) {
        Item item = getInventoryItem(itemName);
        if (item != null && item.canUse()) {
            // Special item interactions
            handleItemUse(item);
            return item.getUseMessage();
        } else if (item != null) {
            return "You can't use the " + itemName + ".";
        } else {
            return "You don't have a " + itemName + ".";
        }
    }
    
    // Handle special item usage
    private void handleItemUse(Item item) {
        String itemName = item.getName().toLowerCase();
        switch (itemName) {
            case "potion":
                heal(25);
                removeItem(item);
                break;
            case "key":
                // Key usage is handled in specific locations
                break;
            case "sword":
                // Sword gives combat advantage
                break;
        }
    }
    
    // Health management
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            gameLost = true;
        }
    }
    
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    // Get inventory display
    public String getInventoryDisplay() {
        if (inventory.isEmpty()) {
            return "Your inventory is empty.";
        }
        
        StringBuilder sb = new StringBuilder("Your inventory contains:\n");
        for (Item item : inventory) {
            sb.append("- ").append(item.getName()).append("\n");
        }
        return sb.toString();
    }
    
    // Get player status
    public String getStatus() {
        return "Health: " + health + "/" + maxHealth + "\n" + getInventoryDisplay();
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public Location getCurrentLocation() {
        return currentLocation;
    }
    
    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
        location.setVisited(true);
    }
    
    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
    
    public boolean isGameWon() {
        return gameWon;
    }
    
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
    
    public boolean isGameLost() {
        return gameLost;
    }
    
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
}