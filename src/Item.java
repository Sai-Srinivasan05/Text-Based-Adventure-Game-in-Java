/**
 * Represents an item in the adventure game
 * Items can be collected, used, and have various properties
 */
public class Item {
    private String name;
    private String description;
    private boolean canTake;
    private boolean canUse;
    private String useMessage;
    
    public Item(String name, String description, boolean canTake, boolean canUse, String useMessage) {
        this.name = name;
        this.description = description;
        this.canTake = canTake;
        this.canUse = canUse;
        this.useMessage = useMessage;
    }
    
    public Item(String name, String description, boolean canTake) {
        this(name, description, canTake, false, "You can't use that.");
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean canTake() {
        return canTake;
    }
    
    public boolean canUse() {
        return canUse;
    }
    
    public String getUseMessage() {
        return useMessage;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return name.equalsIgnoreCase(item.name);
    }
    
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}