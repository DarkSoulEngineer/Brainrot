import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;

// Main class with the BrainrotVirus
public class BrainrotVirus {
    private static final List<BrainrotPopUpWindow> infectedWindows = new ArrayList<>();
    private static Timer infectionSpreadTimer;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CorruptedBackgroundWindow infectionWindow = new CorruptedBackgroundWindow();
            infectionWindow.setVisible(true);
        });

        createNewPopUpWindow();
        infectionSpreadTimer = new Timer(100, e -> EventQueue.invokeLater(BrainrotVirus::createNewPopUpWindow));
        infectionSpreadTimer.start();

        playBackgroundMusic("/assets/brainrot_noise.wav");

        // Global Key Event Dispatcher
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_X) {
                terminateVirusInfection();
                return true; // Consumed the event
            }
            return false; // Not consumed
        });
    }

    // New method to handle error messages
    public static void showError(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        resetApplication(); // Optional: Reset application state after error
    }

    private static void resetApplication() {
        // Implement any necessary reset logic here, if applicable
        System.out.println("Application state reset.");
    }

    private static void playBackgroundMusic(String filePath) {
        try {
            // Get the URL of the audio file from the JAR
            URL url = BrainrotVirus.class.getResource(filePath);
            if (url == null) {
                throw new FileNotFoundException("Audio file not found: " + filePath);
            }
    
            // Get the audio input stream from the URL
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
    
            // Obtain a clip to play the audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the audio continuously
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            showError("Unsupported audio file format: " + e.getMessage(), "Audio Format Error");
        } catch (IOException e) {
            showError("Error loading audio file: " + e.getMessage(), "Audio Load Error");
        } catch (LineUnavailableException e) {
            showError("Audio line unavailable: " + e.getMessage(), "Audio Line Error");
        }
    }  

    private static void createNewPopUpWindow() {
        try {
            BrainrotPopUpWindow infectedPopUp = new BrainrotPopUpWindow();
            infectedPopUp.setVisible(true);
            infectedPopUp.setAlwaysOnTop(true);
            infectedWindows.add(infectedPopUp);
        } catch (SecurityException e) {
            showError("Error creating pop-up window: " + e.getMessage(), "Window Error");
        }
    }

    private static void terminateVirusInfection() {
        // Stop the infection spread timer
        if (infectionSpreadTimer != null) {
            infectionSpreadTimer.stop(); // Stop the timer
        }

        // Stop and dispose of all infected windows
        for (BrainrotPopUpWindow infectedWindow : infectedWindows) {
            infectedWindow.stopErraticMotion();
            infectedWindow.dispose();
        }
        infectedWindows.clear(); // Clear the list of infected windows

        // Show a message to the user indicating the system has been restored
        JOptionPane.showMessageDialog(null,
                "System restored. Brainrot virus stopped!",
                "Safe Mode",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0); // Exit the application
    }
}

// Pop-Up Window Class
final class BrainrotPopUpWindow extends JFrame {
    private static final List<ImageIcon> memeImages = MemeManager.loadMemeImages("assets/memes");
    private int deltaX = 8;
    private int deltaY = 4;
    private Timer moveTimer;

    public BrainrotPopUpWindow() {
        setUndecorated(true);
        setSize(new Random().nextInt(200) + 350, new Random().nextInt(250) + 200);
        setLocation(new Random().nextInt(1000), new Random().nextInt(700));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Set an invisible cursor for the pop-up window
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                Toolkit.getDefaultToolkit().createImage(new byte[0]), new Point(0, 0), "Invisible Cursor"));

        setContentPane(new JPanel());
        getContentPane().setBackground(Color.BLACK);

        try {
            if (!memeImages.isEmpty()) {
                JLabel imageLabel = new JLabel(memeImages.get(new Random().nextInt(memeImages.size())));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                add(imageLabel, BorderLayout.CENTER);
            } else {
                throw new Exception("No meme images found.");
            }
        } catch (Exception e) {
            BrainrotVirus.showError(e.getMessage(), "Image Load Error");
            JLabel label = new JLabel("NO IMAGES FOUND");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Courier New", Font.BOLD, 18));
            label.setForeground(Color.WHITE);
            add(label, BorderLayout.CENTER);
        }

        // Override mouse events to do nothing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Do nothing on mouse click
            }
        });

        FilesManager.writeToFile("log.txt", "New pop-up created at " + new Date());
        startErraticMotion();
    }

    public void startErraticMotion() {
        moveTimer = new Timer(10, e -> {
            Point location = getLocation();
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

            int x1 = location.x + deltaX;
            int y1 = location.y + deltaY;

            // Randomly change direction
            if (new Random().nextInt(100) < 5) {
                deltaX = new Random().nextInt(21) - 10;
                deltaY = new Random().nextInt(21) - 10;
            }

            // Bounce off the edges of the screen
            if (x1 < 0 || x1 + getWidth() > screenWidth) {
                deltaX = -deltaX;
            }
            if (y1 < 0 || y1 + getHeight() > screenHeight) {
                deltaY = -deltaY;
            }

            setLocation(x1, y1);
        });
        moveTimer.start();
    }

    public void stopErraticMotion() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }
}

// Background Window Class
class CorruptedBackgroundWindow extends JFrame {
    private Image backgroundImage;

    public CorruptedBackgroundWindow() {
        backgroundImage = loadBackgroundImage("assets/backgrounds/brainrot.png");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(false);

        // Create a panel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);
        setVisible(true); // Set the frame to be visible after adding components
    }

    // Method to load the background image
    private Image loadBackgroundImage(String filePath) {
        Image img;
        try {
            InputStream imageStream = CorruptedBackgroundWindow.class.getResourceAsStream(filePath);
            if (imageStream == null) {
                throw new FileNotFoundException("Background image not found: " + filePath);
            }
            img = ImageIO.read(imageStream);
            System.out.println("Background image loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
            img = createFallbackImage();
        }
        return img;
    }

    // Method to create a fallback image (black square in this case)
    private Image createFallbackImage() {
        BufferedImage fallback = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = fallback.createGraphics();
        g2d.setColor(Color.BLACK); // You can change this to any color
        g2d.fillRect(0, 0, 1, 1);
        g2d.dispose();
        return fallback;
    }
}

// Meme Manager Class
class MemeManager {
    public static List<ImageIcon> loadMemeImages(String dirPath) {
        List<ImageIcon> memeImages = new ArrayList<>();
        try {
            // Get the path of the JAR file
            String jarPath = MemeManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // Iterate over the entries in the JAR
            try ( // Open the JAR file
                    JarFile jarFile = new JarFile(jarPath)) {
                // Iterate over the entries in the JAR
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    // Check if the entry is in the specified directory and has the correct file extension
                    if (entry.getName().startsWith(dirPath) &&
                            (entry.getName().endsWith(".jpg") || entry.getName().endsWith(".png"))) {
                        // Load the image
                        try (InputStream is = jarFile.getInputStream(entry)) {
                            BufferedImage image = ImageIO.read(is);
                            memeImages.add(new ImageIcon(image));
                        } catch (IOException e) {
                            BrainrotVirus.showError("Error loading meme image: " + entry.getName(), "Image Load Error");
                        }
                    }
                }
                // Close the JAR file
            }
        } catch (IOException e) {
            BrainrotVirus.showError("Error loading meme images: " + e.getMessage(), "Image Load Error");
        }
        return memeImages;
    }
}

// File Manager Class for logging
class FilesManager {
    public static void writeToFile(String fileName, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}