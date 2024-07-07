package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/**
 * A repository for Capers
 *
 * @author jalvlue
 * The structure of a Capers Repository is as follows:
 * <p>
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 * - dogs/ -- folder containing all of the persistent data for dogs
 * - story -- file containing the current story
 * <p>
 */
public class CapersRepository {
    /**
     * Current Working Directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));
    static final String dotCapers = ".capers";
    static final String subFileStory = "story";

    /**
     * Main metadata folder.
     */
    static final File CAPERS_FOLDER = join(CWD, dotCapers);

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * <p>
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     * - dogs/ -- folder containing all of the persistent data for dogs
     * - story -- file containing the current story
     */
    public static void setupPersistence() {
        File dotCapers = CAPERS_FOLDER;
        if (!dotCapers.exists()) {
            if (dotCapers.mkdir()) {
//                System.out.printf("dir created: %s\n", dotCapers.getAbsoluteFile());
            }
        }

        File dogs = Dog.DOG_FOLDER;
        if (!dogs.exists()) {
            if (dogs.mkdir()) {
//                System.out.printf("dir created: %s\n", dogs.getAbsoluteFile());
            }
        }

        File story = join(CapersRepository.CAPERS_FOLDER, subFileStory);
        if (!story.exists()) {
            try {
                if (story.createNewFile()) {
//                    System.out.printf("file created: %s\n", story.getAbsoluteFile());
                }
            } catch (IOException e) {
                System.out.printf("error {%s} occurred while creating file {%s}\n", e.getMessage(), story.getAbsoluteFile());
            }
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     *
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File story = join(CAPERS_FOLDER, subFileStory);

        String newContent = readContentsAsString(story) + text + "\n";
        System.out.println(newContent);

        writeContents(story, newContent);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog savedDog = new Dog(name, breed, age);

        savedDog.saveDog();
        System.out.println(savedDog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     *
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog celeDog = Dog.fromFile(name);

        celeDog.haveBirthday();
        celeDog.saveDog();
    }
}
