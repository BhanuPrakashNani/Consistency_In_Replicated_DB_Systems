import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
        replicatedDatabase dao = new replicatedDatabase();
        replicatedDatabase2 dao2 = new replicatedDatabase2();
        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        System.out.println("Enter sno: \n");
        int sno = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name: \n");
        String name = sc.nextLine();
        if(dao.database(sno, name))
        {
        	dao2.data(sno, name);
        }
    }
}
