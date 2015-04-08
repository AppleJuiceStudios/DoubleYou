package game.staging;

import game.main.GameCanvas;
import game.main.GameFrame;
import game.res.Button;
import game.res.ResourceManager;
import game.res.SaveGame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import util.Log;

public class StageCustomMaps extends Stage {

	// Buttons
	private Button[] btns;
	private int selectedButton;

	// Images
	private BufferedImage imgBackground;

	private List<CustomMap> maps;

	public StageCustomMaps(StageManager stageManager, Map<String, String> data) {
		super(stageManager, data);
		maps = new ArrayList<>();
		loadFromFilesystem();
		initMouse();
		initKey();
		initButtons();
		loadTextures();
	}

	private void loadFromFilesystem() {

		File folder = new File(SaveGame.getDocumentPath());
		deleteDirectory(new File(SaveGame.getPath() + "/temp/"));

		for (int i = 0; i < listFiles(folder).size(); i++) {
			String file = listFiles(folder).get(i);
			if (file.endsWith(".lvl")) {
				Log.info("Processing: " + file);
				CustomMap map = new CustomMap(100, i * 100 + 100);
				ZipFile zipFile = null;
				try {
					zipFile = new ZipFile(file);

					Enumeration<? extends ZipEntry> entries = zipFile.entries();

					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (entry.getName().toLowerCase().endsWith(".xml")) {
							map.setUuid(UUID.randomUUID().toString().replace("-", ""));
							File tempFile = new File(SaveGame.getPath() + "/temp/level/" + map.getUuid() + ".xml");
							tempFile.mkdirs();
							try {
								Files.copy(zipFile.getInputStream(entry), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else if (entry.getName().toLowerCase().endsWith(".lvl-info")) {
							Properties props = new Properties();
							props.load(zipFile.getInputStream(entry));
							map.setName(props.getProperty("name"));
							map.setCreator(props.getProperty("creator"));
							map.setComment(props.getProperty("comment"));

						} else if (entry.getName().toLowerCase().endsWith(".jpg")) {
							map.setThumbnail(ImageIO.read(zipFile.getInputStream(entry)));
						}
					}

					if (map.getUuid() != null)
						maps.add(map);

				} catch (Exception e) {
					e.printStackTrace();
					Log.warning("Processing Custom Map failed: " + e.getMessage());
				} finally {
					if (zipFile != null) {
						try {
							zipFile.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	private boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	private List<String> listFiles(File folder) {
		List<String> files = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				files.addAll(listFiles(fileEntry));
			} else {
				files.add(fileEntry.getAbsolutePath());
			}
		}
		return files;
	}

	private void initMouse() {
		getStageManager().setMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				for (int i = 0; i < btns.length; i++) {
					if (btns[i].contains(point)) {
						btns[i].setHighlighted(true);
						selectedButton = i;
					} else
						btns[i].setHighlighted(false);
				}

				for (CustomMap customMap : maps) {
					if (customMap.getBtnPlay().contains(point)) {
						customMap.getBtnPlay().highlight();
					} else {
						customMap.getBtnPlay().deHighlight();
					}
				}

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		getStageManager().setMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point point = new Point(x, y);

				if (btns[0].contains(point)) {
					back();
				} else if (btns[1].contains(point)) {
					play();
				}
				for (CustomMap customMap : maps) {
					if (customMap.getBtnPlay().contains(point)) {
						Map<String, String> send = new HashMap<String, String>();

						send.put("level", SaveGame.getPath() + "/temp/level/" + customMap.getUuid() + ".xml");
						getStageManager().setStage(StageManager.STAGE_LEVEL, send);
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	private void initKey() {
		getStageManager().setKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (selectedButton <= 0) {
						back();
					} else if (selectedButton == 1) {
						play();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					selectedButton++;
					selectedButton %= 2;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					selectedButton--;
					if (selectedButton < 0)
						selectedButton = 1;
				}
				for (Button button : btns) {
					button.setHighlighted(false);
				}
				if (selectedButton > -1)
					btns[selectedButton].setHighlighted(true);
			}
		});
	}

	private void initButtons() {
		selectedButton = -1;
		btns = new Button[2];
		btns[0] = new Button(ResourceManager.getString("gui.back"), 190, 500);
		btns[1] = new Button(ResourceManager.getString("gui.play"), 410, 500);

		for (Button button : btns)
			button.setHighlighted(false);
	}

	private void loadTextures() {
		imgBackground = ResourceManager.getImage("/backgrounds/Menu-Background.png");
	}

	public void draw(Graphics2D g2) {
		drawBackground(g2, imgBackground);
		g2.setColor(new Color(1f, 1f, 1f, 0.2f));
		g2.fillRect(0, 0, GameCanvas.WIDTH, GameCanvas.HEIGHT);

		for (int i = 0; i < maps.size(); i++)
			maps.get(i).draw(g2);

		for (Button button : btns)
			button.draw(g2);
	}

	// Actions
	private void back() {
		getStageManager().setStage(StageManager.STAGE_MAIN_MENUE);
	}

	private void play() {
		URL url = null;
		try {
			url = new URL(GameFrame.GAME_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(url.toURI());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {

	}

	public void stop() {

	}
}

class CustomMap {

	private String uuid;

	private BufferedImage thumbnail;
	private String name;
	private String creator;
	private String comment;
	private Button btnPlay;
	private int x;
	private int y;

	CustomMap(int x, int y) {
		this.x = x;
		this.y = y;
		setBtnPlay(new Button("PLAY", x + 400, y));
	}

	void draw(Graphics2D g2) {
		g2.drawImage(thumbnail, x, y, 100, 100, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, 20));

		if (name == null)
			name = "Unnamed!";
		g2.drawString(name, x + 120, y + 30);

		if (creator == null)
			creator = "Unknown!";
		g2.drawString(creator, x + 120, y + 50);

		if (comment == null)
			comment = "Unimportant!";
		g2.drawString(comment, x + 120, y + 70);

		btnPlay.draw(g2);
	}

	String getUuid() {
		return uuid;
	}

	void setUuid(String uuid) {
		this.uuid = uuid;
	}

	BufferedImage getThumbnail() {
		return thumbnail;
	}

	void setThumbnail(BufferedImage thumbnail) {
		this.thumbnail = thumbnail;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getCreator() {
		return creator;
	}

	void setCreator(String creator) {
		this.creator = creator;
	}

	String getComment() {
		return comment;
	}

	void setComment(String coment) {
		this.comment = coment;
	}

	Button getBtnPlay() {
		return btnPlay;
	}

	void setBtnPlay(Button btnPlay) {
		this.btnPlay = btnPlay;
	}

	int getX() {
		return x;
	}

	void setX(int x) {
		this.x = x;
	}

	int getY() {
		return y;
	}

	void setY(int y) {
		this.y = y;
	}

}
