package bonn2.BlockReference.math;

public class Vector3 {
    int xVal, yVal, zVal;
    public Vector3(int x, int y, int z) { xVal = x; yVal = y; zVal = z; }

    public int getX() { return xVal; }
    public int getY() { return yVal; }
    public int getZ() { return zVal; }

    public void setX(int x) { xVal = x; }
    public void setY(int y) { yVal = y; }
    public void setZ(int z) { zVal = z; }

}
