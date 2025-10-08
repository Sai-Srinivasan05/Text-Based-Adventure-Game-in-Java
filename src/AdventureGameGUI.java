import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.HashMap;

/**
 * GUI version of the Text-Based Adventure Game
 * Provides a graphical interface with text areas, buttons, and interactive elements
 */
public class AdventureGameGUI extends JFrame {
    // Game components
    private GameEngine gameEngine;
    
    // GUI components
    private JTextArea gameDisplay;
    private JTextField commandInput;
    private JTextArea inventoryDisplay;
    private JTextArea statusDisplay;
    private JPanel buttonPanel;
    private JScrollPane gameScrollPane;
    
    // Color scheme - Improved for better readability
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color TEXT_COLOR = new Color(30, 30, 30);
    private static final Color INPUT_BACKGROUND = Color.WHITE;
    private static final Color BUTTON_COLOR = new Color(59, 89, 152);
    private static final Color ACCENT_COLOR = new Color(0, 102, 204);
    private static final Color PANEL_BACKGROUND = new Color(240, 240, 240);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    
    // Fonts - Improved for better readability
    private static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 12);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);
    
    public AdventureGameGUI() {
        gameEngine = new GameEngine(this);
        setupGUI();
        startGame();
    }
    
    private void setupGUI() {
        setTitle("Text-Based Adventure Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        createMainDisplay();
        createSidePanels();
        createInputPanel();
        createMenuBar();
        
        // Set window properties
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
    }
    
    private void createMainDisplay() {
        // Main game display area
        gameDisplay = new JTextArea();
        gameDisplay.setEditable(false);
        gameDisplay.setBackground(Color.WHITE);
        gameDisplay.setForeground(TEXT_COLOR);
        gameDisplay.setFont(MAIN_FONT);
        gameDisplay.setBorder(new EmptyBorder(15, 15, 15, 15));
        gameDisplay.setLineWrap(true);
        gameDisplay.setWrapStyleWord(true);
        
        // Auto-scroll to bottom
        DefaultCaret caret = (DefaultCaret) gameDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        gameScrollPane = new JScrollPane(gameDisplay);
        gameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gameScrollPane.setBackground(Color.WHITE);
        gameScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        
        add(gameScrollPane, BorderLayout.CENTER);
    }
    
    private void createSidePanels() {
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBackground(PANEL_BACKGROUND);
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(PANEL_BACKGROUND);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), 
            "Player Status", 
            0, 0, TITLE_FONT, TEXT_COLOR));
        
        statusDisplay = new JTextArea(4, 20);
        statusDisplay.setEditable(false);
        statusDisplay.setBackground(Color.WHITE);
        statusDisplay.setForeground(TEXT_COLOR);
        statusDisplay.setFont(MAIN_FONT);
        statusDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(8, 8, 8, 8)));
        
        statusPanel.add(new JScrollPane(statusDisplay), BorderLayout.CENTER);
        
        // Inventory panel
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(PANEL_BACKGROUND);
        inventoryPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), 
            "Inventory", 
            0, 0, TITLE_FONT, TEXT_COLOR));
        
        inventoryDisplay = new JTextArea(8, 20);
        inventoryDisplay.setEditable(false);
        inventoryDisplay.setBackground(Color.WHITE);
        inventoryDisplay.setForeground(TEXT_COLOR);
        inventoryDisplay.setFont(MAIN_FONT);
        inventoryDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(8, 8, 8, 8)));
        
        inventoryPanel.add(new JScrollPane(inventoryDisplay), BorderLayout.CENTER);
        
        // Quick action buttons
        createQuickActionButtons();
        
        sidePanel.add(statusPanel, BorderLayout.NORTH);
        sidePanel.add(inventoryPanel, BorderLayout.CENTER);
        sidePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(sidePanel, BorderLayout.EAST);
    }
    
    private void createQuickActionButtons() {
        buttonPanel = new JPanel(new GridLayout(3, 2, 8, 5));
        buttonPanel.setBackground(PANEL_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR), 
            "Movement", 
            0, 0, TITLE_FONT, TEXT_COLOR));
        
        // Direction buttons
        String[] directions = {"North", "South", "East", "West", "In", "Out"};
        for (String direction : directions) {
            JButton btn = createStyledButton(direction);
            final String dir = direction.toLowerCase();
            btn.addActionListener(e -> executeCommand("go " + dir));
            buttonPanel.add(btn);
        }
    }
    
    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Command input field
        commandInput = new JTextField();
        commandInput.setBackground(INPUT_BACKGROUND);
        commandInput.setForeground(TEXT_COLOR);
        commandInput.setFont(INPUT_FONT);
        commandInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            new EmptyBorder(8, 12, 8, 12)));
        commandInput.setPreferredSize(new Dimension(0, 35));
        
        // Enter key listener
        commandInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    executeCommand(commandInput.getText().trim());
                    commandInput.setText("");
                }
            }
        });
        
        // Submit button
        JButton submitButton = createStyledButton("Execute");
        submitButton.addActionListener(e -> {
            executeCommand(commandInput.getText().trim());
            commandInput.setText("");
        });
        
        // Action buttons panel
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionButtonsPanel.setBackground(BACKGROUND_COLOR);
        
        String[] actions = {"Look", "Inventory", "Status", "Help"};
        for (String action : actions) {
            JButton btn = createStyledButton(action);
            btn.addActionListener(e -> executeCommand(action.toLowerCase()));
            actionButtonsPanel.add(btn);
        }
        
        inputPanel.add(new JLabel("Command:") {{ 
            setForeground(TEXT_COLOR); 
            setFont(MAIN_FONT); 
        }}, BorderLayout.WEST);
        inputPanel.add(commandInput, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(actionButtonsPanel, BorderLayout.SOUTH);
        
        add(inputPanel, BorderLayout.SOUTH);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PANEL_BACKGROUND);
        menuBar.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        
        // Game menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setForeground(TEXT_COLOR);
        gameMenu.setFont(BUTTON_FONT);
        
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> restartGame());
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        
        gameMenu.add(newGame);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(TEXT_COLOR);
        helpMenu.setFont(BUTTON_FONT);
        
        JMenuItem commands = new JMenuItem("Commands");
        commands.addActionListener(e -> showHelpDialog());
        
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(commands);
        helpMenu.add(about);
        
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(8, 16, 8, 16)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    private void executeCommand(String command) {
        if (command.isEmpty()) return;
        
        displayText("\n> " + command + "\n", new Color(75, 0, 130));
        gameEngine.processCommand(command);
        commandInput.requestFocus();
    }
    
    public void displayText(String text, Color color) {
        SwingUtilities.invokeLater(() -> {
            gameDisplay.setForeground(color);
            gameDisplay.append(text);
            gameDisplay.setCaretPosition(gameDisplay.getDocument().getLength());
        });
    }
    
    public void displayText(String text) {
        displayText(text, TEXT_COLOR);
    }
    
    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> statusDisplay.setText(status));
    }
    
    public void updateInventory(String inventory) {
        SwingUtilities.invokeLater(() -> inventoryDisplay.setText(inventory));
    }
    
    public void clearDisplay() {
        SwingUtilities.invokeLater(() -> gameDisplay.setText(""));
    }
    
    private void startGame() {
        displayText("═══════════════════════════════════════════\n", ACCENT_COLOR);
        displayText("    Welcome to the Adventure Game!\n", ACCENT_COLOR);
        displayText("═══════════════════════════════════════════\n\n", ACCENT_COLOR);
        
        displayText("You are an adventurer seeking legendary treasure hidden\n");
        displayText("in these mystical lands. Use commands or buttons to play!\n\n");
        
        displayText("Available commands: go <direction>, look, take <item>,\n");
        displayText("drop <item>, use <item>, inventory, status, help, quit\n\n");
        
        gameEngine.startGame();
    }
    
    private void restartGame() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to start a new game?",
            "New Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            clearDisplay();
            gameEngine = new GameEngine(this);
            startGame();
        }
    }
    
    private void showHelpDialog() {
        String helpText = "GAME COMMANDS:\n\n" +
            "Movement:\n" +
            "• go <direction> - Move in a direction (north, south, east, west, in, out)\n\n" +
            "Items:\n" +
            "• look - Examine your surroundings\n" +
            "• look <item> - Examine a specific item\n" +
            "• take <item> - Pick up an item\n" +
            "• drop <item> - Drop an item\n" +
            "• use <item> - Use an item from inventory\n\n" +
            "Information:\n" +
            "• inventory - Show your items\n" +
            "• status - Show health and inventory\n" +
            "• help - Show this help\n" +
            "• quit - Exit the game\n\n" +
            "TIP: You can also use the buttons for quick actions!";
        
        JOptionPane.showMessageDialog(this, helpText, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAboutDialog() {
        String aboutText = "Text-Based Adventure Game\n\n" +
            "A Java-based adventure game featuring:\n" +
            "• Interactive storytelling\n" +
            "• Inventory management\n" +
            "• Branching storylines\n" +
            "• Multiple endings\n\n" +
            "Created as an educational programming project\n" +
            "demonstrating OOP concepts and GUI development.";
        
        JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showGameEndDialog(String message, String title) {
        SwingUtilities.invokeLater(() -> {
            int option = JOptionPane.showOptionDialog(
                this,
                message + "\n\nWould you like to play again?",
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"New Game", "Exit"},
                "New Game"
            );
            
            if (option == 0) {
                restartGame();
            } else {
                System.exit(0);
            }
        });
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new AdventureGameGUI().setVisible(true);
        });
    }
}