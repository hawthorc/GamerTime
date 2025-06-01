package network;

// TODO: add more input data as abilities / functionalities are added
public class InputPacket {
	public boolean up, down, left, right, pause;


    public InputPacket(boolean up, boolean down, boolean left, boolean right, boolean pause) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.pause = pause;
    }
}
