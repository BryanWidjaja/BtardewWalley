# Btardew Walley

Welcome to **Btardew Walley**, a terminal-based farming and animal husbandry simulator! Cultivate crops, raise livestock, and build a booming agricultural empire straight from your console.

## The World

The game world is split into three interconnected maps.

1. **Home Map**: Where you sleep, buy tools, and access the animal shop.
2. **Plant Farm Map**: Located to the North, this is where you buy seeds and plant your crops.
3. **Animal Farm Map**: Located to the East, this acts as the grazing pasture for your livestock.

## Controls

Interaction revolves around moving your character (`P`) onto points of interest using your keyboard.

- `w` / `a` / `s` / `d`: Move Up, Left, Down, and Right.
- `e`: Open the Inventory Menu.
- Press `Enter` on blank prompts to continue dialogues.

---

## Game Mechanics

### 1. Plant Farming

Head North to the **Plant Farm** map.

- **Farm Shop**: Step on the shop at `(x=4, y=23)` to access the menu. You can buy seeds (like Wheat and Beetroot) or sell harvested Farm Products here.
- **Planting**: Step onto any empty dirt tile (`.`) to open the planting menu. Select a seed from your inventory to plant it.
- **Harvesting**: Plants take time to grow. Once their `growthTime` expires, the lowercase crop symbol will mature into an **UPPERCASE** letter. Step on the mature crop to automatically harvest it!

### 2. Animal Husbandry

Head to the **Home Map** to find the **Animal Shop** at `(x=15, y=16)`. Here, you can buy or sell animals such as Chickens (`c`), Cows (`C`), and Sheep (`S`).

- Purchased animals will automatically spawn in the **Animal Farm** map located to the East.
- **Collecting Goods**: Animals produce products over time. When an animal is ready to be harvested, stepping on it will prompt you to collect its goods.
  - **Chickens**: Produce Eggs. No tools required.
  - **Cows**: Produce Milk. Requires a `Bucket` in your inventory.
  - **Sheep**: Produce Wool. Requires `Shears` in your inventory.

### 3. Buying Tools

You can purchase essential equipment from the **Tool Shop** located on the **Home Map** at `(x=17, y=31)`. Make sure to buy a Bucket and Shears as early as possible if you plan on raising Cows and Sheep!

### 4. Sleeping & Time Progression

Time in Btardew Walley progresses when you sleep. Head to your bed on the **Home Map** at `(x=7, y=21)` and confirm to sleep.
Sleeping advances the required "days" for animal harvest cycles and crop maturation.

---

## Advanced Economics

### Freshness (Farm Products)

Farm products don't last forever. As days pass, the freshness of your harvested crops deteriorates.

- High Freshness: Sells at full price.
- Medium Freshness: Sells at 50% loss.
- Low Freshness: Sells at a massive discount (25% of base price).
- **0 Freshness**: The item rots and disappears entirely from your inventory. Sell your crops quickly!

### Grade Quality (Animal Products)

When harvesting animal products, they are assigned a random Grade (1, 2, or 3). The longer you play (higher day count), the better your chances are of securing high-grade products!

- Grade 1: Sells at 1x base price.
- Grade 2: Sells at 2x base price.
- Grade 3: Sells at 5x base price.

---

## Developer Mode

Type `devmode` instead of a directional movement to toggle developer privileges. This grants you $1,000,000 and the following hotkeys:

- `1`, `2`, `3`: Quick teleport to the Home Map, Plant Map, and Animal Map respectively.
- `r`: Sleep instantly anywhere.
- `g`: Sleep 20 days instantly.
- `u`: Mass spawn animals into your pasture.
- `t`: Acquire all standard tools instantly.
- `p`: Force-spawn a stack of animal products depending on current RNG grade.
- `k`: Acquire a large stack of Wheat seeds instantly.
