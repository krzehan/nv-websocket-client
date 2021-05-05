import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.lang.reflect.Field;

public class Main2 {
        /*
        Zawartość metody main jest tylko kodem testowym, zgodnie z
        treścią zadania nie należało go tworzyć
         */
        public static void main(String[] args) {

            // Prog. testowy zestaw A
            {
                Student[] students = {
                        new Student("Stanislaw", "Moczulski", 23),
                        new Student("Zuzanna", "Kasztany", 1944)
                };

                try {
                    FileOutputStream fos = new FileOutputStream(".//wyniki.txt");
                    Student.putStudentsToFileZestawA(fos, students);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Prog. testowy zestaw B
            {
                Student[] students = null;
                try {
                    FileInputStream fis = new FileInputStream(".//wyniki.txt");
                    students = Student.getStudentsFromFileZestawB(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Prog. testowy - poza zestawami
            {
                Student[] students = {
                        new Student("Stanislaw", "Moczulski", 23),
                        new Student("Zuzanna", "Kasztany", 1944)
                };
                try {
                    FileOutputStream fos = new FileOutputStream(".//wyniki.bin");
                    Student.putStudentsToBinFile(fos, students);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Prog. testowy zestaw C
            {
                Student[] students = null;
                try {
                    FileInputStream fis = new FileInputStream(".//wyniki.bin");

                    students = Student.getStudentsFromFileZestawC(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Prog. testowy zestaw D
            {
                Student[] students = null;
                try {
                    FileInputStream fis = new FileInputStream(".//wyniki.bin");
                    students = Student.getStudentsFromFileZestawD(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Osoba{
        private String fName;
        private String sName;

        protected Osoba(String fName, String sName){
            this.fName = fName;
            this.sName = sName;
        }
        protected String getFName(){
            return fName;
        }
        protected String getSName(){
            return sName;
        }
        public String toString(){
            return super.toString()+" "+fName+" "+sName;
        }
    }

    class Student
            extends Osoba {

        protected int indexNumber;

        public Student(String fName, String sName, int indexNumber){
            super( fName, sName);
            this.indexNumber = indexNumber;
        }

        public static void putStudentsToFileZestawA(FileOutputStream fos, Student[] students) throws IOException {
            String output;
            for(int i = 0; i < students.length; i++){
                output  = students[i].getFName() + " ";
                output += students[i].getSName() + " ";
                output += students[i].indexNumber + "\n";

                for(int j=0; j<output.length(); j++) {
                    fos.write(output.charAt(j));
                }
            }
        }

        public static Student[] getStudentsFromFileZestawB(FileInputStream fis) throws IOException {
            StringBuffer sb = new StringBuffer();
            int data = fis.read();
            while( data != -1){
                sb.append((char)data);
                data = fis.read();
            }
            String[] studentStrings = sb.toString().split("\n");
            if(studentStrings.length > 8)
                throw new RuntimeException("Too many students");
            Student[] students = new Student[8];
            for(int i=0; i<studentStrings.length; i++){
                String[] parts = studentStrings[i].split(" ");
                students[i] = new Student(
                        parts[0], parts[1], Integer.parseInt(parts[2])
                );
            }
            return students;
        }

        public static Student[] getStudentsFromFileZestawC(FileInputStream fis) throws IOException {
            Student[] students = new Student[16];
            int counter = 0;
            int data = fis.read();
            while( data != -1) {
                int indexNumber = 0;
                for (int i = 0; i < 4; i++) {
                    indexNumber = indexNumber | (data << (i * 8));
                    data = fis.read();
                }
                data = fis.read();
                StringBuffer sb = new StringBuffer();
                while (data != '\n'){
                    sb.append((char)data);
                    data = fis.read();
                }
                String[] names = sb.toString().split(" ");
                if(counter < students.length)
                    students[counter++] = new Student(
                            names[0], names[1], indexNumber
                    );
                else
                    throw new RuntimeException("Too many students");
                data = fis.read();
            }
            return students;
        }

        public static Student[] getStudentsFromFileZestawD(FileInputStream fis) throws IOException {
            Student[] students = new Student[16];
            int counter = 0;
            int data = fis.read();
            while( data != -1) {
                int indexNumber = 0;
                for (int i = 0; i < 4; i++) {
                    indexNumber = (indexNumber << 8) | data;
                    data = fis.read();
                }
                data = fis.read();
                StringBuffer sb = new StringBuffer();
                while (data != '\n'){
                    sb.append((char)data);
                    data = fis.read();
                }
                String[] names = sb.toString().split(" ");
                if(counter < students.length)
                    students[counter++] = new Student(
                            names[0], names[1], indexNumber
                    );
                else
                    throw new RuntimeException("Too many students");
                data = fis.read();
            }
            return students;
        }

        public static void putStudentsToBinFile(FileOutputStream fos, Student[] students) throws IOException {
            for(Student student : students){
                fos.write(student.indexNumber >>24);
                fos.write(student.indexNumber >>16);
                fos.write(student.indexNumber >> 8);
                fos.write(student.indexNumber >> 0);
                fos.write(' ');
                String str = student.getFName() + " " + student.getSName();
                for(char chr : str.toCharArray())
                    fos.write(chr);
                fos.write('\n');
            }
        }
    }



