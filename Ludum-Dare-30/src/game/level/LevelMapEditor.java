package game.level;

import game.level.mapobject.MapObject;
import game.level.mapobject.MapObjectAction;
import game.level.mapobject.MapObjectLogic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "levelMap")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LevelMapEditor extends LevelMap {

	private Map<Integer, MapObject> objectsMap;

	// Save
	private boolean isSaving = false;
	private MapObject[] sortedMapObjects;

	// region MapEdit

	public void setTileID(int x, int y, byte tileID) {
		spritesheet[x][y] = tileID;
	}

	public void setTile(int x, int y) {
		spritesheet[x][y] = TileSet.TILE_CENTER;
		updateTile(x, y);
		updateTile(x + 1, y);
		updateTile(x - 1, y);
		updateTile(x, y + 1);
		updateTile(x, y - 1);
	}

	public void removeTile(int x, int y) {
		spritesheet[x][y] = TileSet.TILE_AIR;
		updateTile(x + 1, y);
		updateTile(x - 1, y);
		updateTile(x, y + 1);
		updateTile(x, y - 1);
	}

	public void fillRect(int x1, int y1, int x2, int y2) {
		int startX = Math.min(x1, x2);
		int startY = Math.min(y1, y2);
		int endX = Math.max(x1, x2);
		int endY = Math.max(y1, y2);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				spritesheet[x][y] = TileSet.TILE_CENTER;
			}
		}
		for (int x = startX; x <= endX; x++) {
			updateTile(x, startY);
			updateTile(x, endY);
			updateTile(x, startY - 1);
			updateTile(x, endY + 1);
		}
		for (int y = startY; y <= endY; y++) {
			updateTile(startX, y);
			updateTile(endX, y);
			updateTile(startX - 1, y);
			updateTile(endX + 1, y);
		}
	}

	public void clearRect(int x1, int y1, int x2, int y2) {
		int startX = Math.min(x1, x2);
		int startY = Math.min(y1, y2);
		int endX = Math.max(x1, x2);
		int endY = Math.max(y1, y2);
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				spritesheet[x][y] = TileSet.TILE_AIR;
			}
		}
		for (int x = startX; x <= endX; x++) {
			updateTile(x, startY - 1);
			updateTile(x, endY + 1);
		}
		for (int y = startY; y <= endY; y++) {
			updateTile(startX - 1, y);
			updateTile(endX + 1, y);
		}
	}

	public void updateTile(int x, int y) {
		if (isBlock(x, y) && (x >= 0 & x < getWidth()) && (y >= 0 & y < getHeight())) {
			boolean north = isBlock(x, y - 1);
			boolean south = isBlock(x, y + 1);
			boolean west = isBlock(x - 1, y);
			boolean east = isBlock(x + 1, y);
			if (!north && !south && !west && !east)
				spritesheet[x][y] = TileSet.TILE_CENTER;
			if (!north && !west && east)
				spritesheet[x][y] = TileSet.TILE_NORTH_WEST;
			if (!north && west && !east)
				spritesheet[x][y] = TileSet.TILE_NORTH_EAST;
			if (!north && west && east)
				spritesheet[x][y] = TileSet.TILE_NORTH;
			if (!north && south && !west && !east)
				spritesheet[x][y] = TileSet.TILE_NORTH;
			if (north && !south && west == east)
				spritesheet[x][y] = TileSet.TILE_SOUTH;
			if (north && !south && !west && east)
				spritesheet[x][y] = TileSet.TILE_SOUTH_WEST;
			if (north && !south && west && !east)
				spritesheet[x][y] = TileSet.TILE_SOUTH_EAST;
			if (north && south && west == east)
				spritesheet[x][y] = TileSet.TILE_CENTER;
			if (north && south && !west && east)
				spritesheet[x][y] = TileSet.TILE_WEST;
			if (north && south && west && !east)
				spritesheet[x][y] = TileSet.TILE_EAST;
		}
	}

	public void expandMapX(int expandX) {
		byte[][] spritesheet = new byte[getWidth() + expandX][getHeight()];
		for (int x = 0; x < this.spritesheet.length; x++) {
			for (int y = 0; y < this.spritesheet[0].length; y++) {
				spritesheet[x][y] = this.spritesheet[x][y];
			}
		}
		setSpritesheet(spritesheet);
	}

	public void expandMapY(int expandY) {
		byte[][] spritesheet = new byte[getWidth()][getHeight() + expandY];
		for (int x = 0; x < this.spritesheet.length; x++) {
			for (int y = 0; y < this.spritesheet[0].length; y++) {
				spritesheet[x][y + expandY] = this.spritesheet[x][y];
			}
		}
		Object[] keys = objectsMap.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			MapObject o = objectsMap.get(keys[i]);
			o.setY(o.getY() + expandY);
		}
		setPlayerSpawnY(getPlayerSpawnY() + expandY * TileSet.SPRITE_SIZE);
		setSpritesheet(spritesheet);
	}

	public void rescaleMap() {
		int width = getWidth();
		int height = getHeight();
		while (isColumEmpty(width - 1)) {
			width--;
		}
		while (isRowEmpty(getHeight() - height)) {
			height--;
		}
		if (width < 1) {
			width = 1;
		}
		if (height < 1) {
			height = 1;
		}
		int heightDif = getHeight() - height;
		byte[][] spritesheet = new byte[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				spritesheet[x][y] = this.spritesheet[x][y + heightDif];
			}
		}
		Object[] keys = objectsMap.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			MapObject o = objectsMap.get(keys[i]);
			o.setY(o.getY() - heightDif);
		}
		setPlayerSpawnY(getPlayerSpawnY() - heightDif * TileSet.SPRITE_SIZE);
		setSpritesheet(spritesheet);
	}

	private boolean isColumEmpty(int x) {
		for (int y = 0; y < getHeight(); y++) {
			if (isBlock(x, y)) {
				return false;
			}
		}
		return true;
	}

	private boolean isRowEmpty(int y) {
		for (int x = 0; x < getWidth(); x++) {
			if (isBlock(x, y)) {
				return false;
			}
		}
		return true;
	}

	// endregion MapEdit

	// region ObjectEdit

	public void addMapObject(MapObject mapObject) {
		objectsMap.put(mapObject.getId(), mapObject);
	}

	public void removeMapObject(MapObject mapObject) {
		objectsMap.remove(mapObject.getId());
	}

	// endregion ObjectEdit

	// region LevelMap

	public void init() {
		objectsMap = new HashMap<Integer, MapObject>();
	}

	public void drawObjects(Graphics2D g2, int size) {
		Object[] keys = objectsMap.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			objectsMap.get(keys[i]).draw(g2, size);
		}
	}

	public void drawLogicObjects(Graphics2D g2, int size) {
		Object[] keys = objectsMap.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			objectsMap.get(keys[i]).drawLogic(g2, size);
		}
	}

	public void drawLogicConnections(Graphics2D g2, int size) {
		Object[] keys = objectsMap.keySet().toArray();
		g2.setColor(Color.BLUE);
		for (int i = 0; i < keys.length; i++) {
			MapObject object = objectsMap.get(keys[i]);
			if (object.hasOutput()) {
				MapObject target = objectsMap.get(object.getOutput());
				if (target != null) {
					int x1 = object.getX() * size + (object.getWidth() != 0 ? object.getWidth() * size / 2 : size / 2);
					int y1 = (object.getY() + object.getHeight()) * size + (object.getHeight() != 0 ? 0 : size);
					int x2;
					int y2 = target.getY() * size;
					if (target.inputCount() == 1 && !target.moreInputs()) {
						x2 = target.getX() * size + (target.getWidth() != 0 ? target.getWidth() * size / 2 : size / 2);
					} else {
						int inputs = target.moreInputs() ? target.inputCount() + 1 : target.inputCount();
						int input = 0;
						for (int j = 0; j < target.getInputs().length; j++) {
							if (target.getInputs()[j] == object.getId()) {
								input = j;
							}
						}
						x2 = target.getX() * size + (target.getWidth() != 0 ? target.getWidth() : 1) * size / (inputs * 2) * (input * 2 + 1);
					}
					g2.drawLine(x1, y1, x2, y2);
					g2.drawLine(x1 + 1, y1, x2 + 1, y2);
					g2.drawLine(x1 - 1, y1, x2 - 1, y2);
					g2.drawLine(x1, y1 + 1, x2, y2 + 1);
					g2.drawLine(x1, y1 - 1, x2, y2 - 1);
				}
			}
		}
	}

	public MapObject getMapObject(int id) {
		return objectsMap.get(id);
	}

	public MapObject[] getMapObjects() {
		if (isSaving) {
			return sortedMapObjects;
		} else {
			MapObject[] objectsArray = new MapObject[objectsMap.size()];
			Object[] keys = objectsMap.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				objectsArray[i] = objectsMap.get(keys[i]);
			}
			return objectsArray;
		}
	}

	public void setMapObjects(MapObject[] mapObjects) {
		objectsMap = new HashMap<Integer, MapObject>();
		for (int i = 0; i < mapObjects.length; i++) {
			objectsMap.put(mapObjects[i].getId(), mapObjects[i]);
		}
	}

	public static LevelMap loadLevel(File level) {
		LevelMap map = JAXB.unmarshal(level, LevelMapEditor.class);
		return map;
	}

	// endregion LevelMap

	// region Save

	public void save(File file) {
		// MapObjects sortieren
		sortedMapObjects = new MapObject[objectsMap.size()];
		int index = 0;
		for (Integer i : objectsMap.keySet()) {
			if (!(objectsMap.get(i) instanceof MapObjectLogic || objectsMap.get(i) instanceof MapObjectAction)) {
				sortedMapObjects[index] = objectsMap.get(i);
				index++;
			}
		}
		for (Integer i : objectsMap.keySet()) {
			if (objectsMap.get(i) instanceof MapObjectLogic || objectsMap.get(i) instanceof MapObjectAction) {
				sortedMapObjects[index] = objectsMap.get(i);
				index++;
			}
		}
		// Map überarbeiten // ID zuordnung erstellen
		Map<Integer, Integer> ids = new HashMap<>();
		for (int i = 0; i < sortedMapObjects.length; i++) {
			MapObject object = sortedMapObjects[i];
			ids.put(object.getId(), i + 32);
			for (int y = 0; y < object.getHeight(); y++) {
				for (int x = 0; x < object.getWidth(); x++) {
					setTileID(object.getX() + x, object.getY() + y, (byte) (i + 32));
				}
			}
		}
		// Speichern
		isSaving = true;
		File tempFile = new File(file.getPath() + ".temp");
		JAXB.marshal(this, tempFile);
		isSaving = false;
		// IDs ersätzen
		try {
			Scanner scanner = new Scanner(tempFile);
			PrintStream out = new PrintStream(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.matches("\\s*<(id)?(in)?(targetID)?>\\d+</(id)?(in)?(targetID)?>\\s*")) {
					String idString = line.trim().replaceAll("</?(id)?(in)?(targetID)?>", "");
					Integer id = ids.get(Integer.parseInt(idString));
					if (id == null) {
						id = -1;
					}
					line = line.replaceAll("\\d+", id.toString());
				}
				out.println(line);
			}
			scanner.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		tempFile.delete();
	}

	// endregion Save

}
