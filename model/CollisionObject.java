package model;


public abstract class CollisionObject extends GameObject{

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction){
        if (direction==Direction.LEFT && this.getX() - Model.FIELD_CELL_SIZE == gameObject.getX() && this.getY()==gameObject.getY())
            return true;
        if (direction==Direction.RIGHT && this.getX() + Model.FIELD_CELL_SIZE == gameObject.getX() && this.getY()==gameObject.getY())
            return true;
        if (direction==Direction.UP && this.getY() - Model.FIELD_CELL_SIZE == gameObject.getY() && this.getX()==gameObject.getX())
            return true;
        return direction == Direction.DOWN && this.getY() + Model.FIELD_CELL_SIZE == gameObject.getY() && this.getX() == gameObject.getX();
    }
}
