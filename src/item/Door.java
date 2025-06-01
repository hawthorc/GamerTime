package item;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends Item {
	
	
	public Door() {
		
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
