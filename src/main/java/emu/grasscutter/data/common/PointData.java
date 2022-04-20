package emu.grasscutter.data.common;

public class PointData {
    private pos tranPos;

    public pos getTranPos() {
        return tranPos;
    }

    public void setTranPos(pos tranPos) {
        this.tranPos = tranPos;
    }

    public class pos {
        private float x;
        private float y;
        private float z;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getZ() {
            return z;
        }

        public void setZ(float z) {
            this.z = z;
        }
    }
}
