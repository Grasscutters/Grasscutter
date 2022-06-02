class Vehicle {
  protected String brand = "Ford";
  public void honk() {
    System.out.println("poamap poamap");
  }
}
class Truck extends Vehicle {
  public String modelName = "TATA"; 
    public void honk1() {
    System.out.println("Horn please poamap poopopop ");
  }
}

class Car extends Truck {
  private String modelName1 = "Maruti";
  public static void main(String[] args) {
    Car myFastCar = new Car();
   myFastCar.honk();
    myFastCar.honk1();
    System.out.println("Model name" + myFastCar.brand);
    System.out.println("Model name" + myFastCar.modelName);
     System.out.println("Model name" + myFastCar.modelName1);
  }

}
