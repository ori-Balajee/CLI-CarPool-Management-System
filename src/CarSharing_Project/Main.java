package CarSharing_Project;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println("Package working");

        String url = "jdbc:mysql://localhost:3306/carsharing";
        String user = "root";
        String password = "BAKAJEE";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(true);
            System.out.println("Database connected");

            dropTables(connection);


            createCompanyTable(connection);
            createCarTable(connection);

            Scanner scanner = new Scanner(System.in);

            while (true){
                System.out.println();
                System.out.println("1. Login as a manager");
                System.out.println("0. Exit");
                System.out.print("> ");

                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0){
                    System.out.println("Exiting the application.");
                    break;
                } else if(choice == 1){
                    managerMenu(scanner, connection);
                } else{
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("COMPANY table created or already exists");

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropTables(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS CAR");
        stmt.execute("DROP TABLE IF EXISTS COMPANY");
        stmt.close();
    }


    // CREATE COMPANY TABLE IF NOT EXISTS
    private static void createCompanyTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS COMPANY(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(250) UNIQUE NOT NULL" +
                ")");
        statement.close();
    }

    // CREATE CAR TABLE IF NOT EXISTS
    private static void createCarTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
            "CREATE TABLE IF NOT EXISTS CAR (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME VARCHAR(255) UNIQUE NOT NULL, " +
                        "COMPANY_ID INT NOT NULL, " +
                        "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                        ")"
        );
        statement.close();
    }


    // MANAGER MENU
    private static void managerMenu(Scanner scanner, Connection connection) throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            System.out.print("> ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                return;
            } else if (choice == 1) {
                int companyId = ChooseCompanies(connection, scanner);
                if (companyId != 0) companyMenu(companyId, connection, scanner);
            } else if (choice == 2) {
                createCompany(scanner, connection);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    // CREATE A COMPANY
    private static void createCompany(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine();
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO COMPANY (NAME) VALUES (?)"
        );
        ps.setString(1, companyName);
        ps.execute();
        ps.close();
        System.out.println("The company was created!");

    }

    // PRINT COMPANIES and CHOOSE ONE
    private static int ChooseCompanies(Connection connection,Scanner scanner) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT ID, NAME FROM COMPANY ORDER BY ID");
        if(!rs.next()) {
            System.out.println();
            System.out.println("The company list is empty!");
            
            rs.close();
            statement.close();
            return 0;
        } 

        System.out.println("choose a company:");
        int index = 1;
        int[] companyIds = new int[100];
        while(rs.next()){
            System.out.println(index + ". " + rs.getString("NAME"));
            companyIds[index - 1] = rs.getInt("ID");
            index++;
        }

        System.out.println("0. Back");
        System.out.print("> ");
        int choice = Integer.parseInt(scanner.nextLine());

        rs.close();
        statement.close();
        if (choice == 0) return 0;
        return companyIds[choice - 1];
    }

    // PRINT COMPANY MENU
    private static void companyMenu(int companyId, Connection connection, Scanner scanner) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
            "SELECT NAME FROM COMPANY WHERE ID = ?"
        );
        ps.setInt(1, companyId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String companyName = rs.getString("NAME");
        rs.close();
        ps.close();

        while (true) {
            System.out.println("'" + companyName + "' company:");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            System.out.print("> ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                return;
            } else if (choice == 1) {
                printCarList(companyId, connection);
            } else if (choice == 2) {
                createCar(companyId, connection, scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // PRINT CAR LIST
    private static void printCarList(int companyId, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT NAME FROM CAR WHERE COMPANY_ID = ? ORDER BY ID"
        );
        ps.setInt(1, companyId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            int index = 1;
            do {
                System.out.println(index + ". " + rs.getString("NAME"));
                index++;
            } while (rs.next());
        }

        rs.close();
        ps.close();
    }

    // CREATE A CAR in the car table
    private static void createCar(int companyId, Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)"
        );
        ps.setString(1, carName);
        ps.setInt(2, companyId);
        ps.execute();
        ps.close();
        System.out.println("The car was created!");

    }





}
