/**
 * A simple implementation of a HashMap for the KPCB Fellows Application.
 * Hashing Method: Own Naive method, zeroing sign bit and using modulus
 * Insertion/Retrieval Method: Linear Probing (https://en.wikipedia.org/wiki/Linear_probing)
 */
public class HashMap {
    private Object map[][]; //the Array which represents the HashMap -- map[value][key]
    private int size; //the maximum size of the HashMap
    private int filled; //the amount of indices of the HashMap which are filled

    /**
     * Creates a new HashMap
     * @param size The number of objects for the HashMap
     */
    public HashMap(int size){
        this.map = new Object[size][2];
        this.size = size;
        this.filled = 0;
    }

    /**
     * Gets the size of the HashMap
     * @return The size of the HashMap
     */
    int getSize(){
        return size;
    }

    /**
     * Hashes a key for the hashmap
     * @param key The key to be hashed
     * @return The hashed key, equivalent to the index where the object should be placed into the Hashmap
     */
    public int hash(String key){
        return (key.hashCode() & 0x7FFFFFFF) % this.size; //hash code & 0x7FFFFFFF is just the hash code with the sign bit zeroed
    }

    /**
     * Gets the load value for the HashMap(equal to amount_of_map_filled/total_capacity_of_map)
     * @return The load value of the HashMap
     */
    public float load(){
        float loadFactor = (float)filled / size;
        return loadFactor;
    }

    /**
     * Helper function to search through the HashMap for a key value
     * @param key The value to search for
     * @return The index returned by the helper function
     */
    public int search(String key){
        int startIndex = hash(key);
        int currentIndex = (startIndex + 1) % this.size; //start searching at the next index

        while((map[currentIndex][0] != null) && !(map[currentIndex][1].equals(key)) && (currentIndex != startIndex)){ //while we haven't hit a null index, haven't found the correct value, and haven't looped around yet
            currentIndex = (currentIndex + 1) % this.size;
        }

        return currentIndex; //return the index of whatever we've found
    }

    /**
     * Sets a key in the HashMap to a value
     * @param key The key to assign the value to
     * @param value The value to be assigned to the key
     * @return Whether the set operation is successful or not
     */
    public boolean set(String key, Object value){
        int currentIndex = search(key);

        if(map[currentIndex][0] == null){ //if we've found an empty index, write to it
            map[currentIndex][0] = value;
            map[currentIndex][1] = key;
            //System.out.println("Writing to empty index");
            filled++;
            return true;
        }else if(map[currentIndex][1].equals(key)){ //if we've found a filled index, overwrite it
            map[currentIndex][0] = value;
            map[currentIndex][1] = key;
            //System.out.println("Overwriting filled index");
            return true;
        }else{ //otherwise, our HashMap is full
            //System.out.println("No empty slot");
            return false;
        }
    }

    /**
     * Gets a value from the HashMap, according to a key
     * @param key The key to search the HashMap for a value with
     * @return The value in the HashMap if one is a associated with the passed key, null otherwise
     */
    public Object get(String key){
        int currentIndex = search(key);

        if(map[currentIndex][0] == null) {
            //System.out.println("Value not found.");
            return null;
        }else if(map[currentIndex][1].equals(key)){
            //System.out.println("Value found");
            return map[currentIndex][0];
        }else{
            //System.out.println("No value found");
            return null;
        }
    }

    /**
     * Delete a key from the HashMap, being careful to avoid messing things up in the HashMap
     * @param key The key to delete the object from
     * @return The object if deletion is successful, otherwise null
     */
    public Object delete(String key){
        int currentIndex = search(key);
        Object objectToReturn = null; //save the object which we're deleting so we can return it later
        if(map[currentIndex][0] == null){
            //System.out.println("Value not found");
            return null;
        }else if(map[currentIndex][1].equals(key)){
            int nextIndex = (currentIndex + 1) % this.size;
            while(map[nextIndex][0] != null){
                if (hash(map[nextIndex][1].toString()) >= hash(key)) { //if the hash of the slot we're checking is greater or equal to the hash of what we're deleting
                    nextIndex = (nextIndex + 1) % this.size; //we need to moving our checking index up 1
                    if(nextIndex == currentIndex){ //if we've gotten all the way around without finding anything to switch, we must be good all the way through
                        if(objectToReturn == null){ //save the object we need to return before deleting it
                            objectToReturn = map[currentIndex][0];
                        }
                        map[currentIndex][0] = null;
                        map[currentIndex][1] = null;
                        return objectToReturn;
                    }
                }else { //otherwise, we have an out of order error. fix this, move our index back to where we just deleted and rinse and repeat
                    if(objectToReturn == null){
                        objectToReturn = map[currentIndex][0];
                    }
                    map[currentIndex][0] = map[nextIndex][0];
                    map[currentIndex][1] = map[nextIndex][1];
                    map[nextIndex][0] = null;
                    map[nextIndex][1] = null;
                    currentIndex = nextIndex;
                }
            }
        }else{
            //System.out.println("Value not found");
            return null;
        }

        if(objectToReturn == null){
            objectToReturn = map[currentIndex][0];
        }
        map[currentIndex][0] = null; //if we've reached this point, we must have successfully broken out of our loop. set our current index to null, and return
        map[currentIndex][1] = null;
        return objectToReturn;
    }

    /**
     * Returns a representation of the HashMap in the form {key : value}
     * @return A string representation of the HashMap
     */
    public String toString(){
        String result = "{\n";
        for(int i = 0; i < map.length; i++){
            if(map[i][0] != null){
                result += "\t" + map[i][1] + " : " + map[i][0].toString() + "\n";
            }
        }
        result += "}";
        return result;
    }
}
