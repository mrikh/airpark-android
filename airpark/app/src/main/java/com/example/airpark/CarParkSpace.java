public interface CarParkSpace {
    public int getSpaceID();
    public int getNoOfSpaces();
    public void removeSpace();
    public void addSpace();
    public void addVehicle(String reg);
    public void removeVehicle(String reg);
    public boolean isFree();
}
