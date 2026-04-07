import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User {
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public static boolean isValidUsername(String username) {
		return username != null && username.trim().length() >= 8;
	}
	
	public static boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) return false;
		boolean hasLetter = false;
		boolean hasDigit = false;
		for (char c : password.toCharArray()) {
			if (Character.isLetter(c)) hasLetter = true;
			if (Character.isDigit(c)) hasDigit = true;
		}
		return hasLetter && hasDigit;
	}
	
	public static boolean isUsernameTaken(String username) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("users.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username)) {
					br.close();
					return true;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error reading users file");
		}
		return false;
	}
	
	public static User authenticate(String username, String password) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("users.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts[0].equals(username) && parts[1].equals(password)) {
					br.close();
					return new User(username, password);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error reading users file");
		}
		return null;
	}
	
	public static void register(String username, String password) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
			bw.write(username + "#" + password);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("Error writing users file");
		}
	}
}
