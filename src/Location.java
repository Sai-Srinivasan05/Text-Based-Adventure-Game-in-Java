import java.util.*;

/**
 * Represents a location in the adventure game
 * Locations have descriptions, items, and connections to other locations
 */
public class Location {
    private String name;
    private String description;
    private Map<String, Location> connections;
    private List<Item> items;
    private boolean visited;
    
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.connections = new HashMap<>();
        this.items = new ArrayList<>();
        this.visited = false;
    }
    
    // Add a connection to another location
    public void addConnection(String direction, Location location) {
        connections.put(direction.toLowerCase(), location);
    }
    
    // Add an item to this location
    public void addItem(Item item) {
        items.add(item);
    }
    
    // Remove an item from this location
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    
    // Get an item by name
    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    
    // Check if location has an item
    public boolean hasItem(String itemName) {
        return getItem(itemName) != null;
    }
    
    // Get location in a direction
    public Location getConnection(String direction) {
        return connections.get(direction.toLowerCase());
    }
    
    // Get all available directions
    public Set<String> getAvailableDirections() {
        return connections.keySet();
    }
    
    // Get the full description including items
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(description);
        
        if (!items.isEmpty()) {
            sb.append("\n\nYou can see:");
            for (Item item : items) {
                sb.append("\n- ").append(item.getName()).append(": ").append(item.getDescription());
            }
        }
        
        if (!connections.isEmpty()) {
            sb.append("\n\nAvailable directions: ");
            sb.append(String.join(", ", connections.keySet()));
        }
        
        return sb.toString();
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
    
    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    @Override
    public String toString() {
        return name;
    }
}