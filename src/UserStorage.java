import java.io.*;

public class UserStorage {

    private final static String fileName = "users.dat";
    private StorageMap storageMap = new StorageMap();

    public boolean addUser(User user) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        if(file == null){
            return false;
        }
        if (!file.exists()) {
            file.createNewFile();
        } else {
            if(file.length() > 0){
                storageMap = loadStorage(file);
                if (storageMap.users.containsKey(user.getUsername())) {
                    return false;
                }
            }
        }
        user.setId(storageMap.users.size()+1);
        storageMap.users.put(user.getUsername(),user);
        saveStorage(file);
        return true;
    }

    public User getUser(String username, String password) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        if(file == null || !file.exists()){
            return  null;
        }
        storageMap = loadStorage(file);
        if(!storageMap.users.containsKey(username)){
            return null;
        }
        User user = storageMap.users.get(username);
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

    private StorageMap loadStorage(File file) throws IOException, ClassNotFoundException {
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ){
            storageMap.users.clear();
            storageMap = (StorageMap) objectInputStream.readObject();
            return storageMap;
        }
    }

    private void saveStorage(File file) throws IOException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file,false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                ){
            objectOutputStream.writeObject(storageMap);
        }
    }

}
