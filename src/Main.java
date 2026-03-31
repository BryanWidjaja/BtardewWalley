import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	private Player player;
	private ArrayList<char[][]> maps;
	
    private ArrayList<Tool> availableTools;
    private ArrayList<Animal> availableAnimals;
    private ArrayList<PlantSeed> availableSeeds;
	
	private int currMapIndex = 1;
	private char currTile = ' ';
	
	private Scanner sc;
	private Random rand;
	
	private boolean devMode = false;

	public static void main(String[] args) {
		new Main().init();
	}
	
	private void init () {
		maps = new ArrayList<>();

		maps.add(GameMap.PLANT_FARM_MAP);
		maps.add(GameMap.HOME_MAP);
		maps.add(GameMap.ANIMAL_FARM_MAP);
		
		player = new Player("Tester", "Nigger");
		
		availableTools = new ArrayList<>();
		loadAvailableTools();
		
		availableAnimals = new ArrayList<>();
		loadAvailableAnimals();
		
		availableSeeds = new ArrayList<>();
		loadAvailableSeeds();
		
		sc = new Scanner(System.in);
		rand = new Random();
		
		run();
	}
	
	private void loadAvailableTools () {
		try {
	    	FileReader file = new FileReader("tools.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int price = Integer.parseInt(parts[1].trim());
            	
            	availableTools.add(new Tool(name, price));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void loadAvailableSeeds () {
		try {
	    	FileReader file = new FileReader("plants.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int growthTime = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
            	price = (double) price;
            	
                availableSeeds.add(new PlantSeed(name, price, Character.toLowerCase(name.charAt(0)), growthTime));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void loadAvailableAnimals () {
		try {
	    	FileReader file = new FileReader("animals.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String type = parts[0].trim();
            	
            	int harvestRate = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
            	price = (double) price;
            	
                availableAnimals.add(new Animal(' ', "", type, "", harvestRate, 0, 0, price, false));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void run () {
		while (true) {
			spaceConsole();
			displayMap();
			move();
		}
	}
	
	private void spaceConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
	
	private void pause () {
		System.out.print("Press ENTER to continue...");
		sc.nextLine();
	}
	
	private void displayMap() {
	    char[][] currMap = maps.get(currMapIndex);

	    currMap[player.getX().getCoordinate()]
	           [player.getY().getCoordinate()] = 'P';

	    char[][] infoUI = new char[GameUI.PLAYER_INFO_UI.length][];
	    for (int i = 0; i < GameUI.PLAYER_INFO_UI.length; i++) {
	        infoUI[i] = GameUI.PLAYER_INFO_UI[i].clone();
	    }

	    char[][] keybindUI = GameUI.PLAYER_KEYBINDS_UI;

	    for (int i = 1; i <= 2; i++) {
	        String line = new String(infoUI[i]);
	        int colon = line.indexOf(":");
	        String value = i == 1 ? String.valueOf(player.getDay()) : String.valueOf(player.getMoney());
	        int width = 16;
	        line = line.substring(0, colon + 2) + String.format("%-" + width + "s", value) + "#";
	        infoUI[i] = line.toCharArray();
	    }

	    int mapHeight = currMap.length;
	    int mapWidth = currMap[0].length;
	    int uiHeight = infoUI.length + 1 + keybindUI.length;
	    int totalRows = Math.max(mapHeight, uiHeight);

	    for (int row = 0; row < totalRows; row++) {
	        StringBuilder line = new StringBuilder();

	        if (row < mapHeight) {
	            line.append(new String(currMap[row]));
	        } else {
	            line.append(" ".repeat(mapWidth));
	        }

	        line.append("  ");

	        if (row < infoUI.length) {
	            line.append(new String(infoUI[row]));
	        } else if (row == infoUI.length) {
	            line.append(" ".repeat(infoUI[0].length));
	        } else if (row < infoUI.length + 1 + keybindUI.length) {
	            line.append(new String(keybindUI[row - infoUI.length - 1]));
	        } else {
	            line.append(" ".repeat(keybindUI[0].length));
	        }

	        System.out.println(line);
	    }
	}

    private void move() {
        String input = sc.nextLine().toLowerCase().trim();
        if (input.isEmpty()) return;
        
        if (input.equals("devmode")) {
        	devMode = true;
        	player.setMoney(1000000);
        	return;
        }

        char key = input.charAt(0);

        char[][] currMap = maps.get(currMapIndex);

        int newX = player.getX().getCoordinate();
        int newY = player.getY().getCoordinate();
        
        switch (key) {
            case 'w': 
            	newX--; 
            	break;
            case 'a': 
            	newY--; 
            	break;
            case 's': 
            	newX++; 
            	break;
            case 'd': 
            	newY++; 
            	break;
            case 'e':
            	openInventory();
                return;
            case 'r':
            	if (devMode)
            		initSleep();
            	return;
            case 'g':
            	if (devMode) {
            		for (int i = 0; i < 20; i++) {
            			sleep();
            		}
            	}
            	return;
            case 'u':
            	if (devMode) {
            		insertAnimal("Chicken", "Chicken1");
            		insertAnimal("Chicken", "Chicken2");
            		insertAnimal("Chicken", "Chicken3");
            		insertAnimal("Sheep", "Sheep1");
            		insertAnimal("Sheep", "Sheep2");
            		insertAnimal("Sheep", "Sheep3");
            		insertAnimal("Cow", "Cow1");
            		insertAnimal("Cow", "Cow2");
            		insertAnimal("Cow", "Cow3");
            	}
            	return;
            case 't':
            	if (devMode) {
            		for (Tool tool : availableTools) {
                        player.getInventory().add(
                            new PlayerItem(new Tool(tool.getName(), (int) tool.getPrice(tool.getName())), 1)
                        );
                    }
                    availableTools.clear();
            	}
            	return;
            case 'p':
            	if (devMode) {
            		
            		int grade = getGrade();
            		
            		AnimalProduct egg = new AnimalProduct(
							"Egg", 
							(int) (100 * getGradeMultiplier(grade)), 
							grade
					);
            		
            		AnimalProduct milk = new AnimalProduct(
            				"Milk", 
            				(int) (300 * getGradeMultiplier(grade)), 
            				grade
    				);
            		
            		AnimalProduct wool = new AnimalProduct(
            				"Wool", 
            				(int) (900 * getGradeMultiplier(grade)), 
            				grade
    				);

            		ArrayList<AnimalProduct> tempProducts = new ArrayList<>();
            		
            		tempProducts.add(egg);
            		tempProducts.add(milk);
            		tempProducts.add(wool);
            		
            		for (AnimalProduct tempProduct : tempProducts) {
            			boolean found = false;
            			
            			for (PlayerItem item : player.getInventory()) {
        					if (item.getItem().getName().equals(tempProduct.getName()) &&
        						((AnimalProduct) item.getItem()).getGrade() == grade
        					) {
        						item.setQuantity(item.getQuantity() + 1);
        						found = true;
        					}
        				}
        				
        				if (!found) {
        					player.getInventory().add(new PlayerItem(tempProduct, 1));
        				}
            		}
            	}
            	return;
            case 'k':
            	if (devMode) {
            		PlantSeed wheat = new PlantSeed("Wheat", 50, 'w', 3);
            		
            		ArrayList<PlantSeed> tempSeeds = new ArrayList<>();
            		
            		tempSeeds.add(wheat);
            		
            		for (PlantSeed tempSeed : tempSeeds) {
            			boolean found = false;
            			
            			for (PlayerItem item : player.getInventory()) {
        					if (item.getItem().getName().equals(tempSeed.getName())) {
        						item.setQuantity(item.getQuantity() + 10);
        						found = true;
        					}
        				}
        				
        				if (!found) {
        					player.getInventory().add(new PlayerItem(tempSeed, 10));
        				}
            		}
            	}
            	return;
            case '1':
            	if (devMode) {
            		currMapIndex = 0;
                    player.getX().setCoordinate(10);  
                    player.getY().setCoordinate(21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            case '2':
            	if (devMode) {
            		currMapIndex = 1;
                    player.getX().setCoordinate(10);  
                    player.getY().setCoordinate(21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            case '3':
            	if (devMode) {
            		currMapIndex = 2;
                    player.getX().setCoordinate(10);  
                    player.getY().setCoordinate(21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            default: 
            	return;
        }

        int width = currMap.length;
        int height = currMap[0].length;

        if (newX < 0 || newX >= width ||
            newY < 0 || newY >= height) {
            return;
        }

        if (checkWall(currMap, newX, newY)) 
        	return;

        currMap[player.getX().getCoordinate()]
               [player.getY().getCoordinate()] = currTile;

        currTile = currMap[newX][newY];

        player.getX().setCoordinate(newX);
        player.getY().setCoordinate(newY);

        currMap[newX][newY] = 'P';
        
//        System.out.println("X: " + newX + " Y: " + newY);

        triggerEvent(newX, newY);
    }
    
    private void devMode_ClearAllPlayers() {
        for (char[][] map : maps) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 'P') {
                        map[i][j] = ' ';
                    }
                }
            }
        }
    }
    
    private boolean checkWall(char[][] map, int x, int y) {
        String walls = "#+-_|/\\\"'`':,";

        char tile = map[x][y];

        for (int i = 0; i < walls.length(); i++) {
            if (tile == walls.charAt(i)) {
                return true;
            }
        }

        return false;
    }
    
    private void triggerEvent(int x, int y) {
//    	char[][] currMap = maps.get(currMapIndex);
    	
    	if (currMapIndex == 1) {
            if (x == 10 && y == 0) {
                currMapIndex = 0;
                player.getX().setCoordinate(11);  
                player.getY().setCoordinate(43);
            } else if (x == 16 && y == 43) {
                currMapIndex = 2;
                player.getX().setCoordinate(5);
                player.getY().setCoordinate(0);
            } else if (x == 7 && (y == 21 || y == 22)) {
                initSleep();
            } else if (x == 15 && (y == 16 || y == 17)) {
                initAnimalStore();
            } else if (x == 17 && (y == 31 || y == 32)) {
	    		initToolStore();
	    	}
        } else if (currMapIndex == 0) {
        	if (x == 11 && y == 43) {
        		currMapIndex = 1;
                player.getX().setCoordinate(10);  
                player.getY().setCoordinate(0);
        	} else if (x == 4 && (y == 23 || y == 24)) {
                initFarmStore();
            } else if (currTile == '.') {
                triggerFarmTile(x, y, true);
            } else if (Character.isUpperCase(currTile) && currTile != 'P') {
                collectPlant(x, y);
            }
        } else if (currMapIndex == 2) {
        	if (x == 5 && y == 0) {
        		currMapIndex = 1;
                player.getX().setCoordinate(16);  
                player.getY().setCoordinate(43);
        	} else if (currTile == 'C' || currTile == 'c' || currTile == 'S') {
                collectAnimal(x, y);
            }
        }
    }
    
    private void triggerFarmTile (int x, int y, boolean planting) {
    	if (planting) {
    		int counter = 1;
        	ArrayList<PlantSeed> playerSeeds = new ArrayList<>();
        	
        	for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof PlantSeed) {
        			playerSeeds.add((PlantSeed) item.getItem());
        			System.out.printf("%d. %s Seed - %d\n", counter++, item.getItem().getName(), item.getQuantity());
        		}
        	}
        	
        	if (playerSeeds.isEmpty()) {
        		System.out.println("No seeds in your inventory!");
        		pause();
        		return;
        	}
        	
        	int choice = -1;
        	
        	do {
        		System.out.printf("Which seed do you want to plant [1-%d]: \n", playerSeeds.size());
        		try {
					choice = sc.nextInt();
					sc.nextLine();
				} catch (Exception e) {
					sc.nextLine();
				}
        	} while (choice < 1 || choice > playerSeeds.size());
        	
        	PlantSeed selectedSeed = playerSeeds.get(choice - 1);
        	
        	insertPlant(selectedSeed, x, y);
        	
        	currTile = selectedSeed.getSymbol();
        	
        	Iterator<PlayerItem> it = player.getInventory().iterator();
        	while (it.hasNext()) {
        		PlayerItem item = it.next();
        		if (item.getItem() instanceof PlantSeed) {
        			if (item.getItem().getName().equals(selectedSeed.getName())) {
        				item.setQuantity(item.getQuantity() - 1);
        				
        				if (item.getQuantity() == 0) {
        					it.remove();
        				}
        				break;
        			}
        		}
        	}
    	}
    }
    
    private void initSleep () {
        char choice = 0;
        
        do {
            spaceConsole();
            System.out.print("Do you want to sleep? [y/n]: ");
            
            String input = sc.nextLine().trim().toLowerCase();
            
            if (input.length() == 0) {
                System.out.println("Invalid input!");
                pause();
                continue;
            }
            
            choice = input.charAt(0);
            
            if (choice != 'y' && choice != 'n') {
                System.out.println("Invalid input!");
                pause();
            }
            
        } while (choice != 'y' && choice != 'n');
        
        if (choice == 'y') {
            sleep();
        }
    }
    
    private void sleep () {
    	player.setDay(player.getDay() + 1);
		updateHarvest();
		updateGrowthTime();
		updateFreshness();
    }
    
    private void updateHarvest () {
		for (Animal animal : player.getAnimals()) {
			if (animal.isHarvestable()) continue;

			animal.setHarvestRate(animal.getHarvestRate() - 1);
			
			if (animal.getHarvestRate() == 0) {
				animal.setHarvestable(true);
				
				if (animal.getSymbol() == 'c') 
					animal.setHarvestRate(1);
				else if (animal.getSymbol() == 'C') 
					animal.setHarvestRate(2);
				else if (animal.getSymbol() == 'S') 
					animal.setHarvestRate(5);
			}
		}
	}
    
    private void updateGrowthTime () {
    	for (Plant plant : player.getPlants()) {
    		if (plant.isHarvestable()) continue;

    		plant.setGrowthTime(plant.getGrowthTime() - 1);
    		
    		if (plant.getGrowthTime() == 0) {
    			plant.setHarvestable(true);
    			
    			GameMap.PLANT_FARM_MAP[plant.getPlantX()][plant.getPlantY()] = Character.toUpperCase(plant.getSymbol());
    		}
    	}
    }
    
    private void updateFreshness () {
        Iterator<PlayerItem> it = player.getInventory().iterator();

        while (it.hasNext()) {
            PlayerItem item = it.next();

            if (item.getItem() instanceof FarmProduct) {

                FarmProduct farmProduct = (FarmProduct) item.getItem();

                farmProduct.setFreshness(farmProduct.getFreshness() - 1);

                if (farmProduct.getFreshness() <= 0) {
                    it.remove();
                }
            }
        }
    }
     
    private void initAnimalStore () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Animal Shop");
    		System.out.printf("Money: %.2f$\n", player.getMoney());
            System.out.println("1. Buy Farm Animals");
            System.out.println("2. Sell Farm Animals");
            System.out.println("3. Sell Animal Products");
            System.out.println("4. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initBuyAnimal();
                    	break;
                    case 2:
                    	initSellAnimal();
                        break;
                    case 3:
                    	initSellAnimalProduct();
                    	break;
                    case 4:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initBuyAnimal () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Buy Farm Animals");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("================================================");
    		System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Harvest Rate", "Price");
    		System.out.println("================================================");
            
            for (Animal animal : availableAnimals) {
            	System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n", counter++, animal.getType(), animal.getHarvestRate(), animal.getPrice());
            }
            
            System.out.println("================================================");
            System.out.print("Choose Farm Animals [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    Animal selectedAnimal = availableAnimals.get(choice - 1);

                    if (player.getMoney() >= selectedAnimal.getPrice()) {
                        player.setMoney(player.getMoney() - selectedAnimal.getPrice());
                        
                        String name = null;
                        
                        do {
                        	System.out.print("Input new farm animal's name [<= 15 characters]: ");
                        	name = sc.nextLine();
                        } while (name.length() > 15 || name.trim().isEmpty());
                        
                        insertAnimal(selectedAnimal.getType(), name);

                        System.out.println("Successfully bought a farm animal");
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                
                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initSellAnimal () {
    	if (player.getAnimals().isEmpty()) {
    		System.out.println("No Animals obtained yet!");
    		pause();
    		return;
    	}
    	
		while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Sell Farm Animals");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("===================================================");
    		System.out.printf("| %-3s | %-10s | %-15s | %-10s |\n", "No.", "Type", "Name", "Price");
    		System.out.println("===================================================");
    		
            for (Animal animal : player.getAnimals()) {
            	System.out.printf(
            			"| %-3d | %-10s | %-15s | %-10.1f |\n", 
            			counter++,
            			animal.getType(),
            			animal.getName(), 
            			animal.getPrice()
            	);
            }
            
            System.out.println("===================================================");
            System.out.print("Choose Farm Animal to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	Animal selectedAnimal = player.getAnimals().get(choice - 1);
                    player.setMoney(player.getMoney() + selectedAnimal.getPrice());
                    GameMap.ANIMAL_FARM_MAP[selectedAnimal.getAnimalX()][selectedAnimal.getAnimalY()] = ' ';
                    player.getAnimals().remove(selectedAnimal);

                    System.out.println("Sucessfully sold a farm animal!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initSellAnimalProduct () {
    	
    	ArrayList<AnimalProduct> playerAnimalProduct = new ArrayList<>();
    	
    	for (PlayerItem item : player.getInventory()) {
    		if (item.getItem() instanceof AnimalProduct) {
    			playerAnimalProduct.add((AnimalProduct) item.getItem());
    		}
    	}
    	
    	if (playerAnimalProduct.isEmpty()) {
    		System.out.println("No Animal Products obtained yet!");
    		pause();
    		return;
    	}
    	
		while (true) {
			int counter = 1;
    		
			spaceConsole();
			
			System.out.println("Sell Animal Products");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
			
			System.out.println("===========================================================");
    		System.out.printf("| %-3s | %-10s | %-10s | %-10s | %-10s |\n", "No.", "Name", "Grade", "Quantity", "Price");
    		System.out.println("===========================================================");
    		
    		for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof AnimalProduct) {
                    AnimalProduct animalProduct = ((AnimalProduct) item.getItem());
                    System.out.printf(
                    		"| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n", 
                    		counter++, 
                    		animalProduct.getName(), 
                    		animalProduct.getGrade(), 
                    		item.getQuantity(),
                    		(double) (animalProduct.getPrice(animalProduct.getName()) * getGradeMultiplier(animalProduct.getGrade()))
                    );
                }
            }
            
            System.out.println("===========================================================");
            System.out.print("Choose Farm Animal to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	int selectedIndex = findAnimalProduct(choice);
                	
                	PlayerItem selectedPlayerItem = player.getInventory().get(selectedIndex);
                	AnimalProduct selectedAnimalProduct = (AnimalProduct) selectedPlayerItem.getItem();
                	
                	int quantityToSell = -1;
                	
                	do {
                		System.out.printf("How many items do you want to sell [%d-%d]: ", 1, selectedPlayerItem.getQuantity());
                		try {
							quantityToSell = sc.nextInt();
							sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                	} while(quantityToSell < 1 || quantityToSell > selectedPlayerItem.getQuantity());

                	player.setMoney(
                			player.getMoney() + 
                			(selectedAnimalProduct.getPrice(selectedAnimalProduct.getName()) * getGradeMultiplier(selectedAnimalProduct.getGrade()) * quantityToSell)
                	);
                	
                	player.getInventory().get(selectedIndex).setQuantity(player.getInventory().get(selectedIndex).getQuantity() - quantityToSell);
                	
                	if (player.getInventory().get(selectedIndex).getQuantity() == 0) {
                		player.getInventory().remove(selectedIndex);
                	}
                	
                    System.out.println("Sucessfully sold an animal product!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private int findAnimalProduct(int choice) {
		int counter = 1;
		for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof AnimalProduct) {
                if (counter == choice) {
                	return player.getInventory().indexOf(item);
                }
                
                counter++;
            }
        }
		
		return -1;
	}
    
    private void initFarmStore () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Farm Shop");
    		System.out.printf("Money: %.2f$\n", player.getMoney());
            System.out.println("1. Buy Seeds");
            System.out.println("2. Sell Farm Products");
            System.out.println("3. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initBuySeed();
                    	break;
                    case 2:
                    	initSellFarmProduct();
                    	break;
                    case 3:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initBuySeed () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Buy Seeds");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("================================================");
    		System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Growth Time", "Price");
    		System.out.println("================================================");
            
            for (PlantSeed seed : availableSeeds) {
            	System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n", counter++, seed.getName(), seed.getGrowthTime(), seed.getPrice(seed.getName()));
            }
            
            System.out.println("================================================");
            System.out.print("Choose Seed [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    PlantSeed selectedSeed = availableSeeds.get(choice - 1);
                    
                    int quantity = -1;

                    do {
                    	try {
                    		System.out.printf("How many %s seeds would you like to purchase: ", selectedSeed.getName());
                    		quantity = sc.nextInt();
                    		sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                    } while(quantity <= 0);
                    
                    double totalPrice = selectedSeed.getPrice(selectedSeed.getName()) * quantity;

                    if (player.getMoney() >= totalPrice) {
                        player.setMoney(player.getMoney() - totalPrice);
                        
                        boolean found = false;
                        
                        for (PlayerItem item : player.getInventory()) {
                        	if (item.getItem() instanceof PlantSeed &&
                        		item.getItem().getName().equals(selectedSeed.getName())
                        	) {
                        		item.setQuantity(item.getQuantity() + quantity);
                        		found = true;
                        	}
                        }
                        
                        if (!found) {
                        	player.getInventory().add(
                        			new PlayerItem(
                        					selectedSeed,
                        					quantity
                        			)
                        	);
                        }

                        System.out.printf("Successfully bought %d %s Seeds\n", quantity, selectedSeed.getName());
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                
                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initSellFarmProduct () {
    	ArrayList<FarmProduct> playerFarmProduct = new ArrayList<>();
    	
    	for (PlayerItem item : player.getInventory()) {
    		if (item.getItem() instanceof FarmProduct) {
    			playerFarmProduct.add((FarmProduct) item.getItem());
    		}
    	}
    	
    	if (playerFarmProduct.isEmpty()) {
    		System.out.println("No Farm Products obtained yet!");
    		pause();
    		return;
    	}
    	
		while (true) {
			int counter = 1;
    		
			spaceConsole();
			
			System.out.println("Sell Farm Products");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
			
			System.out.println("===========================================================");
    		System.out.printf("| %-3s | %-10s | %-10s | %-10s | %-10s |\n", "No.", "Name", "Grade", "Quantity", "Price");
    		System.out.println("===========================================================");
    		
    		for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof FarmProduct) {
        			FarmProduct farmProduct = ((FarmProduct) item.getItem());
                    System.out.printf(
                    		"| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n", 
                    		counter++, 
                    		farmProduct.getName(), 
                    		farmProduct.getFreshness(), 
                    		item.getQuantity(),
                    		(double) (farmProduct.getPrice(farmProduct.getName()) * getFreshnessMultiplier(farmProduct.getFreshness()) * 2)
                    );
                }
            }
            
            System.out.println("===========================================================");
            System.out.print("Choose Farm Product to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	int selectedIndex = findFarmProduct(choice);
                	
                	PlayerItem selectedPlayerItem = player.getInventory().get(selectedIndex);
                	FarmProduct selectedFarmProduct = (FarmProduct) selectedPlayerItem.getItem();
                	
                	int quantityToSell = -1;
                	
                	do {
                		System.out.printf("How many items do you want to sell [%d-%d]: ", 1, selectedPlayerItem.getQuantity());
                		try {
							quantityToSell = sc.nextInt();
							sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                	} while(quantityToSell < 1 || quantityToSell > selectedPlayerItem.getQuantity());

                	player.setMoney(
                			player.getMoney() + 
                			(selectedFarmProduct.getPrice(selectedFarmProduct.getName()) * getFreshnessMultiplier(selectedFarmProduct.getFreshness()) * 2 * quantityToSell)
                	);
                	
                	player.getInventory().get(selectedIndex).setQuantity(player.getInventory().get(selectedIndex).getQuantity() - quantityToSell);
                	
                	if (player.getInventory().get(selectedIndex).getQuantity() == 0) {
                		player.getInventory().remove(selectedIndex);
                	}
                	
                    System.out.println("Sucessfully sold a farm product!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private int findFarmProduct(int choice) {
		int counter = 1;
		for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof FarmProduct) {
                if (counter == choice) {
                	return player.getInventory().indexOf(item);
                }
                
                counter++;
            }
        }
		
		return -1;
	}
    
    private void initToolStore () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
    		System.out.println("Buy Tools");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		if (availableTools.isEmpty()) {
    			System.out.println("You already buy all tools!");
    			pause();
    			return;
    		}
            System.out.println("=================================");
            System.out.printf("| %-3s | %-10s | %-10s |\n", "No.", "Name", "Price");
            System.out.println("=================================");
            
            for (Tool tool : availableTools) {
            	System.out.printf("| %-3s | %-10s | %-10s |\n", counter++, tool.getName(), tool.getPrice(tool.getName()));
            }
            
            System.out.println("=================================");
            System.out.print("Choose Tool [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    Tool selectedTool = availableTools.get(choice - 1);
                    int price = (int) selectedTool.getPrice(selectedTool.getName());

                    if (player.getMoney() >= price) {
                        player.setMoney(player.getMoney() - price);
                        player.getInventory().add(new PlayerItem(new Tool(selectedTool.getName(), price), 1));
                        availableTools.remove(selectedTool);

                        System.out.println("Successfully buy a tool");
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void insertAnimal(String type, String name) {
	    int height = GameMap.ANIMAL_FARM_MAP.length;
	    int width = GameMap.ANIMAL_FARM_MAP[0].length;
	    
	    int animalX, animalY;
	    
	    char symbol = 0;
	    String animalProduct = null;
	    int harvestRate = 0;
	    double price = 0;
	    
	    if (type.equals("Chicken")) {
	    	symbol = 'c';
	        animalProduct = "Egg";
	        harvestRate = 1;
	        price = 200.0;
	    } else if (type.equals("Cow")) {
	    	symbol = 'C';
	        animalProduct = "Milk";
	        harvestRate = 2;
	        price = 300.0;
	    } else if (type.equals("Sheep")) {
	    	symbol = 'S';
	        animalProduct = "Wool";
	        harvestRate = 5;
	        price = 500.0;
	    }

	    while (true) {
	        animalX = rand.nextInt(height);
	        animalY = rand.nextInt(width);

	        if (GameMap.ANIMAL_FARM_MAP[animalX][animalY] != ' ') continue;

	        boolean occupied = false;
	        for (Animal animal : player.getAnimals()) {
	            if (animal.getAnimalX() == animalX && animal.getAnimalY() == animalY) {
	                occupied = true;
	                break;
	            }
	        }

	        if (!occupied) {
	            Animal animal = new Animal(symbol, name, type, animalProduct, harvestRate, animalX, animalY, price, true);
	            player.getAnimals().add(animal);
	            GameMap.ANIMAL_FARM_MAP[animalX][animalY] = symbol;
	            break;
	        }
	    }
	}
    
    private void insertPlant(PlantSeed selectedSeed, int x, int y) {
	    Plant plant = new Plant(selectedSeed.getSymbol(), selectedSeed.getName(), x, y, selectedSeed.getGrowthTime(), selectedSeed.getPrice(selectedSeed.getName()), false);
	    player.getPlants().add(plant);
	}
    
    private void openInventory () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Inventory Menu");
            System.out.println("1. View Animal Products");
            System.out.println("2. View Farm Products");
            System.out.println("3. View Animals");
            System.out.println("4. View Tools");
            System.out.println("5. View Plant Seeds");
            System.out.println("6. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initViewAnimalProducts();
                        break;
                    case 2:
                    	initViewFarmProducts();
                    	break;
                    case 3:
                    	initViewAnimals();
                        break;
                    case 4:
                    	initViewTools();
                    	break;
                    case 5:
                    	initViewPlantSeeds();
                    	break;
                    case 6:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initViewAnimalProducts () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof AnimalProduct) {
    			System.out.printf(
    					"%d. %s(%d) - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					((AnimalProduct) playerItem.getItem()).getGrade(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No animal products in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewFarmProducts () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof FarmProduct) {
    			System.out.printf(
    					"%d. %s(%d) - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					((FarmProduct) playerItem.getItem()).getFreshness(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No farm products in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewAnimals () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (Animal animal : player.getAnimals()) {
			System.out.printf(
					"%d. %s(%s)\n",
					counter++,
					animal.getName(),
					animal.getType()
			);
    	}
    	
    	if (counter == 1) {
    		System.out.println("No animals in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewTools () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof Tool) {
    			System.out.printf(
    					"%d. %s\n",
    					counter++,
    					playerItem.getItem().getName()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No tools in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewPlantSeeds () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof PlantSeed) {
    			System.out.printf(
    					"%d. %s - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No plant seeds in inventory!");
    	}
    	
    	pause();
    }
    
    private void collectAnimal (int animalX, int animalY) {
		for (Animal animal : player.getAnimals()) {
	        if (animal.getAnimalX() == animalX && animal.getAnimalY() == animalY) {
	        	if (!animal.isHarvestable()) break;
	        	
	        	int choice = 0;
	        	int grade = getGrade();
	        	
	        	while (true) {
	        		System.out.printf("Want to take %s's %s?\n", animal.getName(), animal.getAnimalProduct());
	        		System.out.println("1. Take");
	        		System.out.println("2. Don't take");
	        		System.out.print(">> ");
	        		try {
	                    choice = sc.nextInt();
	                    sc.nextLine();

	                    if (choice == 1) {
		        			if (animal.getSymbol() == 'C' && !checkInventory("Bucket")) {
		        				System.out.println("You don't have a bucket to get " + animal.getName() + "'s " + animal.getAnimalProduct());
		        				pause();
		        				return;
		        			} else if (animal.getSymbol() == 'S' && !checkInventory("Shears")) {
		        				System.out.println("You don't have shears to get " + animal.getName() + "'s " + animal.getAnimalProduct());
		        				pause();
		        				return;
		        			} else {
		        				
		        				AnimalProduct newProduct = new AnimalProduct(
        								animal.getAnimalProduct(), 
        								(int) (animal.getPrice() * getGradeMultiplier(grade)), 
        								grade
        						);
		        				
		        				boolean found = false;
		        				
		        				for (PlayerItem item : player.getInventory()) {
		        					if (item.getItem().getName().equals(newProduct.getName()) &&
		        						((AnimalProduct) item.getItem()).getGrade() == grade
		        					) {
		        						item.setQuantity(item.getQuantity() + 1);
		        						found = true;
		        					}
		        				}
		        				
		        				if (!found) {
		        					player.getInventory().add(new PlayerItem(newProduct, 1));
		        				}
		        							
		        				animal.setHarvestable(false);
		        				break;
		        			}
		        		}
	                    
	                    if (choice == 2) break;
	                    
	                    if (choice < 1 || choice > 2) {
	                    	System.out.println("Invalid range number input!");
	                    	pause();
	                    }
	                } catch (Exception e) {
	                    sc.nextLine();
	                }
	        	}
	        }
	    }
	}
    
    private void collectPlant(int plantX, int plantY) {

        Iterator<Plant> it = player.getPlants().iterator();

        while (it.hasNext()) {
            Plant plant = it.next();

            if (plant.getPlantX() == plantX && plant.getPlantY() == plantY) {

                int freshness = 5;

                FarmProduct newProduct = new FarmProduct(
                        plant.getName(),
                        (int) (plant.getPrice() * getFreshnessMultiplier(freshness)),
                        freshness
                );

                boolean found = false;

                for (PlayerItem item : player.getInventory()) {

                    if (!(item.getItem() instanceof FarmProduct)) continue;

                    FarmProduct existing = (FarmProduct) item.getItem();

                    if (existing.getName().equals(newProduct.getName()) &&
                        existing.getFreshness() == freshness) {

                        item.setQuantity(item.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    player.getInventory().add(new PlayerItem(newProduct, 1));
                }

                it.remove();

                GameMap.PLANT_FARM_MAP[plantX][plantY] = '.';

                currTile = '.';

                break;
            }
        }
    }
    
    private boolean checkInventory (String itemName) {
    	for (PlayerItem item : player.getInventory()) {
    		if (item.getItem().getName().equals(itemName)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private int getGrade() {
        int gradeRand = rand.nextInt(100) + 1;
        int day = player.getDay();

        int grade2Chance = day;
        int grade3Chance = day / 2;

        if (gradeRand <= grade2Chance) {
            return 2;
        } else if (gradeRand <= grade2Chance + grade3Chance) {
            return 3;
        } else {
            return 1;
        }
    }
	
	private int getGradeMultiplier (int grade) {
		if (grade == 1) 
			return 1;
		else if (grade == 2) 
			return 2;
		else 
			return 5;
	}
	
	private double getFreshnessMultiplier (int freshness) {
		if (freshness == 1) 
			return 0.25;
		else if (freshness == 3) 
			return 0.5;
		else 
			return 1;
	}
}
