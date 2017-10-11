/**
 * Tester class for the HashMap class. Tests setting, retrieving, overwriting, deletion, and makes sure that deleting values properly re-arranges HashMap afterwards
 */
public class TestHashMap {
    //F -- 0
    //P -- 0
    //H -- 2
    //I -- 3
    //G -- 1
    //L -- 6
    public static void main(String[] args) {
        System.out.println("Beginning Test of HashMap\n-------------------------");
        HashMap map = new HashMap(100); //constructor for a new HashMap

        map.set("Aidan", "Beggs"); //set an index of the HashMap
        map.set("a", "b");

        System.out.println("Check One: " + (map.load() == 2.0f/100.0f));

        map.set("William", "Aidan");
        map.set("KPCB", "Fellows");

        System.out.println("Check Two: " + (map.load() == 4.0f/100.0f));
        System.out.println("Check Three: " + (map.get("William").equals("Aidan"))); //make sure that we can get an index of the map
        System.out.println("Check Four: " + (map.get("a").equals("b")));

        map.set("a", "abcd");

        System.out.println("Check Five: " + (map.load() == 4.0f/100.0f));
        System.out.println("Check Six: " + (map.get("a").equals("abcd")));

        Object foo = new Object();

        map.set("Williams", foo); //check actually storing an object

        System.out.println("Check Seven: " + (map.get("Williams") == foo));

        System.out.println("Check Eight: " + map.delete("Aidan").equals("Beggs")); //delete Aidan from the table

        System.out.println("Check Nine: " + (map.get("Aidan") == null)); //make sure that deleting Aidan worked

        System.out.println("Check Ten: " + (map.delete("RandomKey") == null)); //make sure trying to delete a random key returns null

        System.out.println("Check Eleven: " + (map.get("RandomKey") == null)); //make sure trying to get a random key returns null too;



        map = new HashMap(10); //the hashed values for the keys are displayed at the top in comments
        map.set("F", "0");
        map.set("P", "0");
        map.set("H", "2");
        map.set("I", "3");
        map.set("G", "1");
        map.set("L", "6");

        System.out.println("\nTesting that deleting keys re-arranges table correctly -- see deleting section in Wikipedia article for Linear Probing\n");
        System.out.println(map);
        System.out.println("\nDELETING H\n");
        map.delete("H");
        System.out.println(map);
    }
}
