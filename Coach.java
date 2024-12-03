public class Coach extends Human {
    private int yearsOfExp;

    //Method
    public void setExp(int yearsOfExp){
        this.yearsOfExp = yearsOfExp;
    }
    public int getExp(){
        return yearsOfExp;
    }
    
    //Constructor
    public Coach(){
        super();
        yearsOfExp = 0;
    } 
    public Coach(String name, int age, String nationality, int yearsOfExp){
        super(name, age, nationality);
        this.yearsOfExp = yearsOfExp;
    }
}
