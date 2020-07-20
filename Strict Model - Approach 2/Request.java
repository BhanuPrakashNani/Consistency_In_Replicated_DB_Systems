public class Request implements java.io.Serializable{
    private Student student;
    private String operation;
    private int row;
    private String nickname;
    public Request( String op, Student s){
        this.student = s;
        this.operation = op;

    }
    public Request( String op,String nickname, int x){
        this.row = x;
        this.operation = op;
        this.nickname = nickname;
    }

    public String getOperation() { 
        return operation; 
    }
    
    public Student getStudent(){
        return student;
    }

    public int getRow(){
        return row;
    }

    public String getNickName() {
        return nickname;
    }

}