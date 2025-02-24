package network;

// TODO: add more input data as abilities / functionalities are added
public class InputPacket {
	public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    public InputPacket(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
}
