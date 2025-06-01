/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package item;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends Item {
	
	
	public Key() {
		
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
