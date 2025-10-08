# Text-Based Adventure Game

A comprehensive Java-based text adventure game featuring object-oriented programming, interactive storytelling, inventory management, and branching storylines. Now available in both console and GUI versions!

## 📖 Game Description

You are an adventurer seeking the legendary treasure hidden in mystical lands. Your quest will take you through mysterious forests, abandoned villages, dark caves, and ancient towers. Along the way, you'll encounter dragons, discover magical items, and make crucial decisions that determine your fate.

## 🎯 Features

- **Interactive Storytelling**: Immersive narrative with multiple locations and story branches
- **Inventory Management**: Collect, use, and manage various items throughout your adventure
- **Command-Based Interface**: Simple text commands to navigate and interact with the world
- **Health System**: Monitor your health and use items to heal
- **Multiple Endings**: Different outcomes based on your choices and actions
- **Object-Oriented Design**: Clean, extensible code structure using Java OOP principles
- **Dual Interface**: Both console-based and modern GUI versions available
- **Rich Graphics**: Dark-themed GUI with color-coded messages and intuitive controls

## 🛠️ Technical Implementation

### Core Components

1. **Item Class**: Represents game objects with properties like name, description, and usability
2. **Location Class**: Manages game areas with connections, items, and descriptions
3. **Player Class**: Handles player state, inventory, health, and movement
4. **Game Class**: Console-based game engine with command parsing and game loop
5. **AdventureGameGUI Class**: Modern Swing-based graphical interface
6. **GameEngine Class**: GUI-adapted game logic with visual feedback

### Key Programming Concepts Demonstrated

- **Object-Oriented Programming**: Classes, inheritance, encapsulation
- **Data Structures**: HashMaps for locations, ArrayLists for inventory
- **String Manipulation**: Command parsing and text processing
- **Control Flow**: Conditional statements and loops
- **Exception Handling**: Input validation and error management

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (NetBeans, IntelliJ IDEA, Eclipse) or command line

### Installation & Setup

1. **Clone or download the project**:
   ```
   Navigate to: H:\TextAdventureGame\
   ```

2. **Compile the Java files**:
   ```bash
   cd H:\TextAdventureGame\src
   javac *.java
   ```

3. **Run the game**:
   
   **Console Version:**
   ```bash
   java Game
   ```
   
   **GUI Version:**
   ```bash
   java AdventureGameGUI
   ```

### Quick Start with Batch Scripts

**Console Version:**
```bash
# Double-click or run from command line:
"H:\TextAdventureGame\run_game.bat"
```

**GUI Version:**
```bash
# Double-click or run from command line:
"H:\TextAdventureGame\run_gui_game.bat"
```

### Alternative IDE Setup

1. Open your preferred Java IDE
2. Create a new project and import the source files from the `src` directory
3. Run the `Game.java` (console) or `AdventureGameGUI.java` (GUI) file's main method

## 🎮 How to Play

### Game Commands

| Command | Description | Example |
|---------|-------------|---------|
| `go <direction>` | Move in a direction | `go north`, `go east` |
| `look` | Examine current location | `look` |
| `look <item>` | Examine specific item | `look sword` |
| `take <item>` | Pick up an item | `take key` |
| `drop <item>` | Drop an item | `drop stick` |
| `use <item>` | Use an item from inventory | `use potion` |
| `inventory` | Check your inventory | `inventory` |
| `status` | Check health and inventory | `status` |
| `help` | Display available commands | `help` |
| `quit` | Exit the game | `quit` |

### Game World Map

```
    [Village] ──── [Dragon's Lair]
        │
    [Forest] ──── [Cave] ──── [Treasure Room]
        │                          │
    [Tower] ──── [Tower Interior]   │
                                   │
                              [Victory!]
```

### Winning Strategy

1. **Start in the Forest**: Pick up the stick (optional)
2. **Visit the Village**: Collect the key and healing potion
3. **Explore the Cave**: Get the sword and torch
4. **Use the Key**: Enter the Ancient Tower
5. **Gather Magic**: Collect the spellbook from inside the tower
6. **Face the Dragon**: Use your sword to defeat the dragon (requires sword!)
7. **Find the Treasure**: Navigate to the treasure room for victory!

### Important Items

- **Key**: Required to enter the Ancient Tower
- **Sword**: Essential for defeating the dragon
- **Potion**: Restores 25 health points
- **Torch**: Provides light in dark areas
- **Spellbook**: Contains powerful magical knowledge

## 🎯 Game Mechanics

### Health System
- Start with 100 health points
- Take damage from dangerous encounters
- Use potions to restore health
- Game ends if health reaches 0

### Win/Lose Conditions
- **Victory**: Reach the Hidden Treasure Room
- **Defeat**: Health drops to 0 (e.g., from dragon encounter)

### Special Encounters

1. **Dragon Fight**: Requires sword to survive and win
2. **Tower Access**: Needs key to unlock the door
3. **Treasure Discovery**: Automatic win condition

## 📚 Educational Value

This project demonstrates:

- **Object-Oriented Programming**: Classes, methods, inheritance
- **Data Structures**: Maps, Lists, Arrays
- **Control Structures**: Loops, conditionals, switch statements
- **String Processing**: Input parsing, text manipulation
- **Game Development Concepts**: State management, user interaction
- **Software Design**: Modular code organization

## 🔧 Customization & Extension

### Adding New Locations
```java
Location newLocation = new Location("Location Name", "Description");
locations.put("location_key", newLocation);
// Add connections to other locations
forest.addConnection("direction", newLocation);
```

### Creating New Items
```java
Item newItem = new Item("item name", "description", canTake, canUse, "use message");
location.addItem(newItem);
```

### Implementing New Commands
Add new cases to the `processCommand()` method in `Game.java`.

## 🐛 Troubleshooting

### Common Issues

1. **Compilation Errors**: Ensure all Java files are in the same directory
2. **Game Won't Start**: Check that `Game.java` has a proper main method
3. **Commands Not Working**: Use lowercase commands and check spelling
4. **Can't Enter Tower**: Make sure you have the key from the village
5. **Dragon Kills You**: Get the sword from the cave before facing the dragon

## 📈 Future Enhancements

Potential improvements:
- Save/Load game functionality
- More complex combat system
- Additional locations and storylines
- Graphics or sound effects
- Multiple character classes
- Multiplayer support

## 📝 Project Structure

```
TextAdventureGame/
│
├── src/
│   ├── Game.java            # Console-based game class and logic
│   ├── AdventureGameGUI.java # GUI version with Swing interface
│   ├── GameEngine.java      # GUI-adapted game engine
│   ├── Player.java          # Player management
│   ├── Location.java        # Location and world management
│   ├── Item.java            # Item properties and behavior
│   └── GameDemo.java        # Demo version for testing
│
├── run_game.bat             # Console version launcher
├── run_gui_game.bat         # GUI version launcher
└── README.md                # This documentation file
```

## 🎓 Learning Outcomes

After completing this project, you will have experience with:
- Java programming fundamentals
- Object-oriented design principles
- Interactive application development
- Text-based user interfaces
- Game state management
- String processing and command parsing

---

**Happy Adventuring!** 🗡️⚔️🏆

*Created as an educational Java programming project demonstrating OOP concepts, data structures, and interactive storytelling.*