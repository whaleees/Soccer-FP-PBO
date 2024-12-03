public class Human {
    private String name, nationality;
    private int age;

    public void setName(String name){
        this.name = name;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }
    public void setAge(int age){
        this.age = age;
    }

    public String getName(){
        return name;
    }
    public String getNationality(){
        return nationality;
    }
    public int getAge(){
        return age;
    }
    
    //Constructor
    public Human(){
        name = "";
        age = 0;
        nationality = "";
    }
    public Human(String name, int age, String nationality){
        this.name = name;
        this.age = age;
        this.nationality = nationality;
    }
    public void introduce(){
        System.out.println("Halo nama saya adalah "+ name + " umur saya "+ age + " sy berasal dri "+ nationality);
    }
}
