
import javax.xml.crypto.Data;
import java.util.*;


public class Main {
    public static Server S = new Server();
    public static flight_server F=new flight_server();
    public static Collect C = new Collect();
    public static Database D;
    public static Admin A = new Admin();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        F.flight_info();
        S.pre();
	    System.out.println("Welcome to the Airline");
        System.out.println("Are you a new user or old user?\n1)Old user\n2)New user\n3)Admin");
        int n = scan.nextInt();
        scan.nextLine();
        if(n==1){
            System.out.println("Username: ");
            String name = scan.nextLine();
            if(S.check(name)){
                D= S.getobj(name);
                System.out.println("Password: ");
                String password = scan.nextLine();
                if(S.checkP(password, D)){
                    System.out.println("Menu:\n1)Book a seat\n2)Cancel a seat\n 3)Reprint Tickets");
                    int choice = scan.nextInt();
                    scan.nextLine();
                    if(choice==1) {
                        F.search(scan);
                    }
                    else if(choice==2){
                        List<Flight> objs = Main.D.getBooked();
                        if(objs.size()==0){
                            System.out.println("No Tickets booked.");
                            Main.main(null);
                        }
                        else {
                            for (int i = 0; i < objs.size(); i++) {
                                System.out.print(i + 1+")");
                                objs.get(i).print(objs.get(i));
                            }
                            System.out.println("Select the Flight you want to cancel?");
                            int o = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Successfully cancelled.Amount of " + objs.get(o - 1).getFair() + " will be refunded within 2 business days");
                            Main.D.cancel(o - 1);
                            Main.main(null);

                        }
                    }
                    else{
                        List<Flight> objs = Main.D.getBooked();
                        Ticket T = new Ticket();
                        if(objs.size()==0){
                            System.out.println("No tickets booked");
                            Main.main(null);
                        }
                        for(int i=0;i<objs.size();i++){
                            Flight obj = objs.get(i);
                            System.out.print((i+1)+") ");
                            System.out.println(obj.getFlight_number());
                        }
                        System.out.println("Select an option to print ticket.");
                        int h = scan.nextInt();
                        T.print_Ticket(objs.get(h-1));
                    }
                }
                else{
                    System.out.println("Password Incorrect");
                    Main.main(null);
                }

            }
            else{
                System.out.println("Username not found");
                Main.main(null);

            }
        }
        else if(n==2){
            C.data_collect(scan);
        }
        else if(n==3){
            System.out.print("Admin username: ");
            String s = scan.nextLine();
            if(s.compareTo(A.getUsername())==0){
                System.out.print("Password: ");
                String p = scan.nextLine();
                if(p.compareTo(A.getPassword())==0){
                    A.menu(scan);
                }
                else{
                    System.out.println("Incorrect password");
                    Main.main(null);
                }
            }
            else{
                System.out.println("Incorrect username");
                Main.main(null);
            }
        }
        else{
            System.out.println("Incorrect Input");
            Main.main(null);

        }

    }
}
class Admin{
    private String username="Bharadwaj";
    private String password="123";
    public void setUsername(String n){
        username=n;
    }
    public void setPassword(String n){
        password=n;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public void menu(Scanner scan){
        System.out.println("Welcome to admin menu: \n1)Add Flight\n2)Delete Flight\n3)Update Flight\n4)Change Password\n5)Logout");
        int n =scan.nextInt();
        List<Flight> Fly = Main.F.getFly();
        List<String> stopovers = new ArrayList<String>();
        scan.nextLine();
        if(n==1){
            System.out.print("Flight Number:");
            String flight_number = scan.nextLine();
            System.out.print("International status : 1)Yes  2)No");
            int I = scan.nextInt();
            boolean International;
            International = I == 1;
            System.out.print("Departure city:");
            String Dcity = scan.nextLine();
            System.out.print("Arrival city:");
            String Acity = scan.nextLine();
            System.out.print("Departure time(HH/MM):");
            String Ddate=scan.nextLine();
            Ddate=Ddate+" ";
            System.out.print("Departure Date(DD/MM/YYYY):");
            Ddate=Ddate+scan.nextLine();
            System.out.print("Arrival time(HH/MM):");
            String Adate=scan.nextLine();
            Adate=Adate+" ";
            System.out.print("Arrival Date(DD/MM/YYYY):");
            Adate=Adate+scan.nextLine();
            System.out.print("Flight Duration:");
            String flight_duration = scan.nextLine();
            System.out.print("Fare:");
            int fare = scan.nextInt();
            System.out.print("Number of stopovers:");
            int stop = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the stopovers");
            for(int i=0;i<stop;i++){
                stopovers.add(scan.nextLine());
            }
            System.out.print("Number of Economic class seats:");
            int eseats = scan.nextInt();
            System.out.print("Number of Business class seats:");
            int bseats = scan.nextInt();
            System.out.print("Two way? 1)Yes 2)No:");
            int k = scan.nextInt();
            boolean two_way;
            two_way= k == 1;
            Fly.add(new Flight(flight_number,International,Dcity,Acity,Ddate,Adate,fare,flight_duration,stopovers,eseats,bseats,two_way));
        }
        else if(n==2){
            System.out.println("Choose a flight you want to cancel");
            for(int i=0;i<Fly.size();i++){
                System.out.print(i+1+" ");
                Fly.get(i).print(Fly.get(i));
            }
            int choice = scan.nextInt();
            scan.nextLine();
            Fly.remove(choice-1);
            System.out.println("Flight has been removed.");
        }
        else if(n==3){
            System.out.println("Choose a flight you want to update");
            for(int i=0;i<Fly.size();i++){
                System.out.print(i+1+" ");
                Fly.get(i).print(Fly.get(i));
            }
            int choice = scan.nextInt();
            Fly.get(choice-1).update(Fly.get(choice-1),scan);
        }
        else if(n==4) {
            System.out.print("Enter Old password:");
            String p=scan.nextLine();
            if(password.compareTo(p)==0){
                System.out.println("Enter new password");
                password = scan.nextLine();
                System.out.println("Password updated");
                menu(scan);
            }
            else{
                System.out.println("Wrong password");
                Main.A.menu(scan);
            }
        }
        else{
            System.out.println("Logging out..");
            Main.main(null);
        }
    }
}
class Collect{
    public void data_collect(Scanner scan){
        Database obj = Main.S.add_user();
        String[] cities = new String[4];
        System.out.print("Name: ");
        obj.set_user_name(scan.nextLine());
        System.out.println("Set a password to your account");
        obj.setPassword(scan.nextLine());
        System.out.print("Age: ");
        obj.set_age(scan.nextInt());
        scan.nextLine();
        System.out.print("Gender: ");
        obj.set_gender(scan.nextLine());
        System.out.print("Permanent Address: ");
        obj.set_Paddress(scan.nextLine());
        System.out.print("Current Address: ");
        obj.set_Caddress(scan.nextLine());
        System.out.print("Phone number: ");
        obj.set_phone(scan.nextLine());
        System.out.print("Occupation: ");
        obj.set_occupation(scan.nextLine());
        System.out.print("Passport No: ");
        obj.set_passport(scan.nextLine());
        System.out.print("Aadhar No: ");
        obj.set_aadhar(scan.nextLine());
        System.out.print("Name on card: ");
        obj.set_nameOnCard(scan.nextLine());
        System.out.print("Card Number: ");
        obj.set_cardNumber(scan.nextLine());
        System.out.print("Expiry date(MM/YY): ");
        obj.set_expiry(scan.nextLine());
        System.out.print("Enter any 4 cities you visit frequently ");
        for(int i=0;i<4;i++) {
            cities[i] = scan.nextLine();
        }
        obj.set_cities(cities);
        System.out.print("Favorite seat 1)Window 2)Middle 3)Aisle) ");
        obj.set_seat(scan.nextInt());
        System.out.print("Favorite class:1)Business class 2)Economic class: ");
        obj.set_Sclass(scan.nextInt());
        System.out.print("Number of Passengers travel with you frequently: ");
        int p = scan.nextInt();
        for(int i=0;i<p;i++){
            System.out.print("Name of Passenger "+(i+1)+": ");
            obj.set_Onames(scan.nextLine());
            System.out.print("Age: ");
            obj.set_Oages(scan.nextInt());
            scan.nextLine();
            System.out.print("Gender: ");
            obj.set_Ogender(scan.nextLine());
            System.out.print("Aadhar No: ");
            obj.set_Oaadhar(scan.nextLine());
            System.out.print("Passport No: ");
            obj.set_Opassport(scan.nextLine());
        }
        System.out.println("Refreshing...");
        Main.main(null);




    }
}
class Server{
    private List<Database> Users=new ArrayList<Database>();
    public void pre(){
        Database p = new Database();
        Users.add(p);
        String[] cities ={"Hyderabad","Chennai","Mumbai","Delhi"};
        p.set_user_name("Bharadwaj");
        p.setPassword("123");
        p.set_age(19);
        p.set_gender("Male");
        p.set_phone("8500043112");
        p.set_Paddress("Hyderabad");
        p.set_Caddress("Tirupati");
        p.set_occupation("Student");
        p.set_passport("12345");
        p.set_aadhar("67891");
        p.set_nameOnCard("Vuppala Bharadwaj");
        p.set_cardNumber("123456789");
        p.set_expiry("0324");
        p.set_cities(cities);
        p.set_seat(1);
        p.set_Sclass(1);
        p.set_Onames("Vedavyas");
        p.set_Oages(13);
        p.set_Ogender("Male");
        p.set_Opassport("35412");
        p.set_Oaadhar("56241");

    }
    public Database add_user(){
        int size = Users.size();
        Users.add(new Database());
        return Users.get(size);
    }
    public boolean check(String num){
        for (Database obj : Users) {
            if (obj.getUser_name().compareTo(num) == 0) {
                return true;
            }
        }
        return false;
    }
    public Database getobj(String num){
        Database obj = new Database();
        for (Database user : Users) {
            obj = user;
            if (obj.getUser_name().compareTo(num) == 0) {
                return obj;
            }
        }
        return obj;
    }
    public boolean checkP(String num, Database obj){
        return obj.getPassword().compareTo(num) == 0;
    }
}
class flight_server{
    private List<Flight> Fly = new ArrayList<Flight>();
    public void flight_info() {
        List<String> stop1 = new ArrayList<String>();
        stop1.add("Hawaii");
        List<String> stop2 = new ArrayList<String>();
        stop2.add("South American");
        List<String> stop3 = new ArrayList<String>();
        stop3.add("Hyderabad");
        List<String> stop4 = new ArrayList<String>();
        stop4.add("Delhi");
        List<String> stop5 = new ArrayList<String>();
        Fly.add(new Flight("BD674",true,"Chennai","New York","2230 23112021","1020 24112021",100,"1150",stop1,40,30,true));
        Fly.add(new Flight("BA1326",true,"Hyderabad","Australia","1130 24112021","2100 25112021",200,"0930",stop2,60,20,true));
        Fly.add(new Flight("BA1476",false,"Tirupati","Haryana","1200 24112021","1800 24112021",150,"0600",stop3,30,12,false));
        Fly.add(new Flight("GF5232",false,"Tirupati","Haryana","1230 24112021","1700 24112021",200,"0430",stop4,40,23,true));
        Fly.add(new Flight("AA8017",false,"Hyderabad","Chennai","1530 25112021","1700 25112021",120,"0130",stop5,34,12,true));

    }
    public List<Flight> getFly(){
        return Fly;
    }
    public void search(Scanner scan){
        System.out.println("Do you want to search by 1)Date 2)Cities");
        int k = scan.nextInt();
        List<Flight> objs = new ArrayList<Flight>();
        flight_details fd = new flight_details();
        scan.nextLine();
        if(k==1){
            System.out.println("Enter the date(DD/MM/YYYY): ");
            String s=scan.nextLine();
            boolean status =false;
            int l=1;
            for (Flight obj : Fly) {
                String d = obj.getDdate().substring(5, 13);
                if (d.compareTo(s) == 0) {
                    if (!status) {
                        System.out.println("Sno  Flight Number  Departure city  Arrival city  Departure time(HH/MM)/date(DD/MM/YYYY)  Arrival time(HH/MM)/date(DD/MM/YYYY) ");
                    }
                    status = true;
                    System.out.println(l + "    " + obj.getFlight_number() + "         " + obj.getDcity() + "        " + obj.getAcity() + "  " + obj.getDdate() + "   " + obj.getAdate());
                    l++;
                    objs.add(obj);
                }
            }
            if(!status){
                System.out.println("No Flights found");
                Main.F.search(scan);
            }
            System.out.println("Please select on option");
            int op = scan.nextInt();
            fd.viewDetails(objs,op,scan);

        }
        else if(k==2){
            System.out.println("Enter the two cities:");
            System.out.print("Departure: ");
            String c1 = scan.nextLine();
            System.out.print("Arrival: ");
            String c2 = scan.nextLine();
            boolean status =false;
            int l=1;
            for (Flight obj : Fly) {
                if (c1.compareTo(obj.getDcity()) == 0 && c2.compareTo(obj.getAcity()) == 0) {
                    if (!status) {
                        System.out.println("Sno  Flight Number  Departure city  Arrival city  Departure time(HH/MM)/date(DD/MM/YYYY)  Arrival time(HH/MM)/date(DD/MM/YYYY) ");
                    }
                    status = true;
                    System.out.println(l + "    " + obj.getFlight_number() + "         " + obj.getDcity() + "        " + obj.getAcity() + "  " + obj.getDdate() + "   " + obj.getAdate());
                    l++;
                    objs.add(obj);
                }
            }
            if(!status){
                System.out.println("No Flights found");
                Main.F.search(scan);
            }
            System.out.println("Please select on option");
            int op = scan.nextInt();
            fd.viewDetails(objs,op,scan);

        }
        else{
            System.out.println("Wrong Input");
            Main.F.search(scan);
        }
    }

}
class flight_details{
    public void viewDetails(List<Flight> objs,int op,Scanner scan){
        flight_details fd = new flight_details();
        Flight obj = objs.get(op-1);
        obj.view(obj);
        Payment P = new Payment();
        Ticket T = new Ticket();
        booking B = new booking();
        System.out.println("Do you want to confirm the flight 1)Yes 2)No");
        int n = scan.nextInt();
        scan.nextLine();
        if(n==1){
                B.bookTicket(obj,scan);
                System.out.println("Please select any one of the payment options:1)Card\n2)UPI");
                int option=scan.nextInt();
                scan.nextLine();
                if(option==1){
                    P.paymentCard(Main.D,obj,scan);
                    T.print_Ticket(obj);
                }
                else if(option==2){
                    P.paymentUPI(Main.D,obj,scan);
                    T.print_Ticket(obj);
                }
                else{
                    System.out.println("Wrong option Selected");
                    fd.viewDetails(objs,op,scan);
                }
        }
        else{
            Main.F.search(scan);
        }

    }
}
class booking{
    public void bookTicket(Flight obj ,Scanner scan){
        System.out.println("Book a seat based on saved preferences\n1)Yes\n2)No");
        int choice = scan.nextInt();
        if(choice==1){
            System.out.println("Booking ticket based on your preferences...");
            Main.D.setFlights(obj);
            obj.seatBook(obj,1,scan);
        }
        else{
            Main.D.setFlights(obj);
            obj.seatBook(obj,2,scan);
            System.out.println("Please select type of seat\n1)Window\n2)Middle\n3)Aisle");
            int k=scan.nextInt();
            scan.nextLine();
            if(k==1){
                Main.D.setBookedSeat("Window");
            }
            else if(k==2){
                Main.D.setBookedSeat("Middle");
            }
            else{
                Main.D.setBookedSeat("Aisle");
            }
        }
    }
}
class Ticket{
    public void print_Ticket(Flight obj){
        System.out.println("Printing Ticket.");
        Main.D.print(obj.getInternational());
        System.out.println("Class: "+Main.D.getBookedSclass());
        System.out.println("Seat: "+Main.D.getBookedSeat());
        Main.main(null);
    }
}
interface PaymentMethods{
    void paymentCard(Database obj1, Flight obj2,Scanner scan);
    void paymentUPI(Database obj1,Flight obj2,Scanner scan);
}
/**
 * Payment class implements the PaymentMehods
 */
class Payment implements PaymentMethods {

    public void paymentCard(Database obj1,Flight obj2, Scanner scan) {
        System.out.println("Your saved card: ");
        obj1.printCard();
        System.out.println("Do you want to continue with your saved card?\n1)Yes  2)No");
        int k  =scan.nextInt();
        if(k==1){
            System.out.print("Enter CVV: ");
            scan.nextInt();
            System.out.println("Payment Successful");
        }
        else {
            scan.nextLine();
            System.out.print("Enter card number: ");
            scan.nextLine();
            System.out.print("Enter name on card: ");
            scan.nextLine();
            System.out.print("Enter Expiry Date(MM/YY): ");
            scan.nextLine();
            System.out.print("Enter CVV: ");
            scan.nextLine();
            System.out.println("Payment Successful.");

        }
    }

    public void paymentUPI(Database obj1,Flight obj2, Scanner scan) {
        System.out.print("Please enter your UPI ID");
        scan.nextLine();
        System.out.print("Please enter your UPI password");
        scan.nextLine();
        System.out.println("Payment successful.");
    }
}

class Database{
    private String user_name;
    private String password;
    private int age;
    private String gender;
    private String permanent_address;
    private String current_address;
    private String phone;
    private String occupation;
    private String passport;
    private String aadhar;
    private String name_on_card;
    private String card_number;
    private String expiry_date;
    private String[] cities = new String[4];
    private String seat;
    private String sclass;
    private String bookedSeat;
    private String bookedSclass;
    private List<String> Onames = new ArrayList<>();
    private List<Integer> Oages = new ArrayList<>();
    private List<String> Ogender = new ArrayList<>();
    private List<String> Oaadhar = new ArrayList<>();
    private List<String> Opassport = new ArrayList<>();
    private List<Flight> booked = new ArrayList<>();

    public void print(boolean n){
        System.out.println("Name: "+Main.D.user_name);
        System.out.println("Age: "+Main.D.age);
        System.out.println("Gender: "+Main.D.gender);
        System.out.println("Phone: "+Main.D.phone);
        System.out.println("Permanent address: "+Main.D.permanent_address);
        System.out.println("Current address: "+Main.D.current_address);
        if(n) {
            System.out.println("Passport number: " +Main.D.passport);
        }
        System.out.println("Aadhar number: "+Main.D.aadhar);

    }
    public List<Flight> getBooked(){
        return Main.D.booked;
    }
    public void cancel(int i){
        booked.remove(i);
    }
    public String getUser_name(){
        return user_name;
    }
    public String getPassword(){
        return password;
    }
    public String getSeat(){return seat;}
    public String getSclass(){return sclass;}
    public void setBookedSeat(String n){this.bookedSeat=n;}
    public void setBookedSclass(String n){this.bookedSclass=n;}
    public String getBookedSeat(){return bookedSeat;}
    public String getBookedSclass(){return bookedSclass;}
    public void set_user_name(String n){
        this.user_name=n;
    }
    public void setPassword(String n){this.password=n;}
    public void set_age(int n){
        this.age=n;
    }
    public void set_gender(String n){
        this.gender=n;
    }
    public void set_Paddress(String n){
        this.permanent_address=n;
    }
    public void set_Caddress(String n){
        this.current_address=n;
    }
    public void set_phone(String n){
        this.phone=n;
    }
    public void set_occupation(String n){
        this.occupation=n;
    }
    public void set_passport(String n){
        this.passport=n;
    }
    public void set_aadhar(String n){
        this.aadhar=n;
    }
    public void set_nameOnCard(String n){
        this.name_on_card=n;
    }
    public void set_cardNumber(String n){
        this.card_number=n;
    }
    public void set_expiry(String n){
        this.expiry_date=n;
    }
    public void set_cities(String[] n){
        this.cities=n;
    }
    public void set_seat(int n){
        if(n==1){
            this.seat="Window";
        }
        else if(n==2){
            this.seat="Middle";
        }
        else{
            this.seat="Aisel";
        }
    }
    public void set_Sclass(int n){
        if(n==1){
            this.sclass="Business";
        }
        else{
            this.sclass="Economic";
        }
    }
    public void set_Onames(String n){
        Onames.add(n);
    }
    public void set_Oages(int n){
        Oages.add(n);
    }
    public void set_Ogender(String n){
        Ogender.add(n);
    }
    public void set_Oaadhar(String n){
        Oaadhar.add(n);
    }
    public void set_Opassport(String n){
        Opassport.add(n);
    }
    public void setFlight(Flight obj){booked.add(obj);}
    public void printCard(){
        System.out.println("Name on Card: "+name_on_card);
        System.out.println("Card number: "+card_number);
        System.out.println("Expiry date(MM/YY): "+expiry_date);
    }
    public void print_info(Database D){
        System.out.print("Name: "+D.getUser_name());
        System.out.println();// Complete this part.
    }
    public void setFlights(Flight obj){
        Main.D.booked.add(obj);
    }
}
class Flight{
    private String flight_number;
    private boolean International;
    private String Dcity;
    private String Acity;
    private String Ddate;
    private String Adate;
    private String duration;
    private int fare;
    private List<String> stopovers = new ArrayList<>();
    private int eseats;
    private int bseats;
    private boolean two_way;
    Flight(String flight_number, boolean International, String Dcity, String Acity, String Ddate, String Adate,int Fare, String Duration,List<String> stopovers, int eseats,int bseats,boolean Two_way ){
        this.flight_number = flight_number;
        this.International=International;
        this.Dcity=Dcity;
        this.Acity=Acity;
        this.Ddate=Ddate;
        this.Adate=Adate;
        this.duration=Duration;
        this.fare=Fare;
        this.stopovers=stopovers;
        this.eseats=eseats;
        this.bseats=bseats;
        this.two_way=Two_way;
    }
    public void update(Flight obj,Scanner scan){
        System.out.println("Please select what do you want to update");
        System.out.println("1)Flight number\n2)International status\n3)Departure city\n4)Arrival city\n5)Departure date\n6)Arrival date\n7)Duration\n8)Fare\n9)Stopovers\n10)Number of seats\n11)Two way status\n12)Back");
        int choice = scan.nextInt();
        scan.nextLine();
        if(choice ==1){
            System.out.print("Enter the new Flight number");
            obj.flight_number=scan.nextLine();
            System.out.println(" Successfully updated.");
            update(obj,scan);
        }
        else if(choice==2){
            System.out.print("Select a choice for International status\n1)Yes\n2)No");
            int k = scan.nextInt();
            scan.nextLine();
            if(k==1){
                obj.International=true;
            }
            else{
                obj.International=false;
            }
            System.out.println(" Successfully updated.");
            update(obj,scan);
        }
        else if(choice==3){
            System.out.print("Enter the new Departure city");
            obj.Dcity=scan.nextLine();
            System.out.println(" Successfully updated.");
            update(obj,scan);
        }
        else if(choice==4){
            System.out.print("Enter the new Arrival city");
            obj.Acity=scan.nextLine();
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==5){
            System.out.print("Enter the new Departure time(HH/MM)");
            String s = scan.nextLine();
            System.out.print("Enter the new Departure date(DD/MM/YYYY)");
            s=s+" "+scan.nextLine();
            obj.Ddate=s;
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==6){
            System.out.print("Enter the new Arrival time(HH/MM)");
            String s = scan.nextLine();
            System.out.print("Enter the new Arrival date(DD/MM/YYYY)");
            s=s+" "+scan.nextLine();
            obj.Adate=s;
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==7){
            System.out.print("Enter the new Duration");
            obj.duration=scan.nextLine();
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==8){
            System.out.print("Enter the new Fare");
            obj.fare=scan.nextInt();
            scan.nextLine();
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==9){

            System.out.print("Select a choice:\n1)Add a stopover \n 2)Remove a stopover\n 3)Remove all");
            int j = scan.nextInt();
            for(int i=0;i<obj.stopovers.size();i++){
                System.out.print(i+1+")");
                System.out.println(obj.stopovers.get(i));
            }
            if(j==1){
                System.out.print("Enter the new stopover to be added");
                scan.nextLine();
                obj.stopovers.add(scan.nextLine());
                System.out.println("Stopover is added");
            }
            else if(j==2){
                System.out.print("Select a stopover to be removed.");
                    int p = scan.nextInt();
                    obj.stopovers.remove(p-1);
                    System.out.println("Stopover is removed");
            }
            else if(j==3){
                obj.stopovers.clear();
                System.out.println("All stopovers removed.");

            }
            update(obj,scan);

        }
        else if(choice==10){
            System.out.print("Which seats do you want to update\n1)Business class seats\n2)Economic seats");
            int k = scan.nextInt();
            if(k==1){
                System.out.println("Enter the new number of seats: ");
                obj.bseats=scan.nextInt();
                scan.nextLine();
            }
            else if(k==2){
                System.out.println("Enter the new number of seats: ");
                obj.eseats=scan.nextInt();
                scan.nextLine();
            }
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else if(choice==11){
            System.out.print("Select a choice for Two-way status\n1)Yes\n2)No");
            int k = scan.nextInt();
            scan.nextLine();
            if(k==1){
                obj.two_way=true;
            }
            else{
                obj.two_way=false;
            }
            System.out.println(" Successfully updated.");
            update(obj,scan);

        }
        else{
            Main.A.menu(scan);
        }
    }
    public boolean getInternational(){return International;}
    public String getDdate(){return Ddate;}
    public String getFlight_number(){return flight_number;}
    public String getDcity(){return Dcity;}
    public String getAcity(){return  Acity;}
    public String getAdate(){return Adate;}
    public int getFair(){return fare;}
    public void view(Flight obj){
        System.out.println("Flight number: "+obj.flight_number);
        System.out.println("International status: "+obj.International);
        System.out.println("Departure city: "+obj.Dcity);
        System.out.println("Arrival city: "+obj.Acity);
        System.out.println("Departure date: "+obj.Ddate);
        System.out.println("Arrival date: "+obj.Adate);
        System.out.println("Duration: "+obj.duration);
        System.out.println("Fare: "+obj.fare);
        System.out.println("Stopovers: "+obj.stopovers);
        System.out.println("Number of Economic seats: "+obj.eseats);
        System.out.println("Number of Business seats: "+obj.bseats);
        System.out.println("Two way status: "+obj.two_way);

    }
    public void print(Flight obj){
        System.out.println("Flight number: "+obj.flight_number);
        System.out.println("Departure city: "+obj.Dcity);
        System.out.println("Arrival city: "+obj.Acity);
        System.out.println("Departure date: "+obj.Ddate);
        System.out.println("Arrival date: "+obj.Adate);
        System.out.println("Duration: "+obj.duration);
        System.out.println("Stopovers: "+obj.stopovers);
    }
    public void seatBook(Flight obj,int n,Scanner scan){
        if(n==1){
            Main.D.setBookedSclass(Main.D.getSclass());
            Main.D.setBookedSeat(Main.D.getSeat());
            if(Main.D.getSclass().compareTo("Business")==0){
                obj.bseats--;
            }
            else{
                obj.eseats--;
            }
        }
        else{
            System.out.println("Which class you want to travel\n1)Business class\n2)Economic class");
            int s = scan.nextInt();
            scan.nextLine();
            if(s==1){
                Main.D.setBookedSclass("Business");
                obj.bseats--;
            }
            else{
                Main.D.setBookedSclass("Economic");
                obj.eseats--;
            }
        }
    }

}