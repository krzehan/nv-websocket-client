class Person{
    String ii = null;
    String nn = null;

    public Person(String i, String n){
        this.ii = i;
        this.nn = n;

    }

    public void show(){
        System.out.println("constr: Person()");
        System.out.println("ii" + " " + "nn");
    }


}