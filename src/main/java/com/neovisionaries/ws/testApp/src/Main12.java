public class Main12 {

    public static void main(String[] args) {
        Point3D point1 = new Point3D( 10, 20, 30);
        Point3D point2 = new Point3D( 10, 20, 30);
        Point3D point3 = new Point3D( 10,  0, 30);
        Point point4 = new Point(10);

        System.out.println(
                point1.equals(point2) + "\n" +
                        point3.equals(point1) + "\n" +
                        point4.equals(point3)
        );
    }

}
