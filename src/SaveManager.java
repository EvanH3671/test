// Last updated: 05/09/23

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**Class containing static methods which manage the save files for this project. */
public class SaveManager {

    /**A string representation of the location of the directory containing every save file. */
    private final static String FILE_DIRECTORY = "Save Data";

    /**A string array containing the names of each file which can be created & written to/from. */
    private final static String[] FILE_LIST = {
        "Save One.ser",
        "Save Two.ser",
        "Save Three.ser",
        "Settings.ser",
    };

    /**Creates the directory for save files as well as the file containing the save data used by the settings menu
     * if they don't exist. */
    public static void createSaveDirectory() {
        
        try { 
            Files.createDirectory(Paths.get(FILE_DIRECTORY)); 
        } catch (Exception e) {/* do nothing */}

        try { 
            Files.createFile(Paths.get(FILE_DIRECTORY, FILE_LIST[3])); 

            SettingsRecord defaultSettings = new SettingsRecord(6);
            setFileData(defaultSettings, 3);
        } catch (Exception e) {/* do nothing */}            
    }

    /**Returns a {@code Path} to a file contained in the {@link #FILE_LIST file list}.
     * 
     * @param fileIndex The key used to acces the file in the <i>file list</i>.
     * @return The {@code Path} corresponding to the input key.
     * 
     * @see java.nio.file.Path */
    public static Path getPath(int fileIndex) {
        return Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]);
    }

    /**Checks if a {@code Path} in the {@link #FILE_LIST file list} has a valid file attached to it.
     * 
     * @param fileIndex The key used to acces the file in the <i>file list</i>.
     * @return True if the file exists, false otherwise.
     * 
     * @see java.nio.file.Path */
    public static boolean isPath(int fileIndex) {
        return Files.isRegularFile(Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]));
    }

    /**Creats a file in a location included in the {@link #FILE_LIST file list} {@code HashMap}.
     * 
     * @param fileIndex The key used to acces the file in the <i>file list</i>.
     * @throws FileAlreadyExistsException If the file already exists. */
    public static void addFile(int fileIndex) throws FileAlreadyExistsException {
        try {
            Files.createFile(Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]));
        } catch (FileAlreadyExistsException faee) {
            throw new FileAlreadyExistsException(FILE_LIST[fileIndex]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Deletes a file in a location included in the {@link #FILE_LIST file list} {@code HashMap}.
     * 
     * @param fileIndex The key used to acces a specific file in the <i>file list</i>.
     * @throws NoSuchFileException If the file does not exist. */
    public static void removeFile(int fileIndex) throws NoSuchFileException {
        try {
            Files.delete(Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]));
        } catch (NoSuchFileException nsfe) {
            throw new NoSuchFileException(FILE_LIST[fileIndex]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    /**Returns the object stored in a file in a location included in the {@link #FILE_LIST file list} {@code HashMap}.
     * 
     * @param <T> The {@code Record} object retrieved from the file.
     * @param fileIndex The key used to acces a specific file in the <i>file list</i>.
     * @return The {@code Object} stored in the file.
     * @throws NoSuchFileException If the file does not exist. */
    @SuppressWarnings("unchecked")
    public static <T> T getFileData(int fileIndex) throws NoSuchFileException {
        Object saveData = null;
        Path savePath = Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]);

        try (ObjectInputStream objInput = new ObjectInputStream(Files.newInputStream(savePath))) {
            saveData = objInput.readObject();
        } catch (NoSuchFileException nsfe) {
            throw new NoSuchFileException(FILE_LIST[fileIndex]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) saveData;
    }

    /**Saves an object to a file in a location included in the {@link #FILE_LIST file list} {@code HashMap}.
     * 
     * @param saveData The {@code Object} to save.
     * @param fileIndex The key used to acces a specific file in the <i>file list</i>.
     * @throws NoSuchFileException If the file does not exist. */
    public static void setFileData(Object saveData, int fileIndex) throws NoSuchFileException {
        Path savePath = Paths.get(FILE_DIRECTORY, FILE_LIST[fileIndex]);

        try (ObjectOutputStream objOutput = new ObjectOutputStream(Files.newOutputStream(savePath))) {
            objOutput.writeObject(saveData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**An {@code Object} which allows all the information contained in the settings menu to be saved to a file and loaded 
 * in one Object. <p>
 * This Object is a {@code record}, so it cannot be modified after creation. */
record SettingsRecord(int number) implements Serializable {}

/**An {@code Object} which allows all the information stored by a player to be saved to a file and loaded in one 
 * Object. <p>
 * This Object is a {@code record}, so it cannot be modified after creation. */
record PlayerStats(Appearance appearance) implements Serializable {}