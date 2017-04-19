import java.sql.*;

public class CommonUserChecker 
{
    private Connection connection;
    private Statement statement;

    public CommonUserChecker(){
        connection = null;
        statement = null;
    }

    public static void main(String[] args) throws SQLException
    {
        String username = "ajj004";
        String password = "ahho2eiG";
        CommonUserChecker userValidator = new CommonUserChecker();
        userValidator.connect(username, password);
        if(!userValidator.checkDB(args[0])){
            System.out.println("Username is taken, please select another");
            System.exit(1);
        }
        else{
            System.out.println("Welcome " + args[0]);
            userValidator.insertUser(args[0], args[1]);
        }
        userValidator.disconnect();
    }

    public void connect(String username, String mysqlpassword) throws SQLException{
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + username + "?" + "user=" + username + "&password=" + mysqlpassword);
	    statement = connection.createStatement();
	}
        catch(Exception e){
            throw e;
        }
    }

    public void disconnect() throws SQLException{
        connection.close();
        statement.close();
    }
    
    public void insert(String table, String values) {
        String query = "INSERT into " + table + " values (" + values + ")" ;
        System.out.println(query);
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDB(String userName){
        String query = "SELECT userName FROM User";
        try{
            ResultSet resultSet = statement.executeQuery(query);
	    while(resultSet.next()){
                if(userName.equals(resultSet.getString(1)))
                    return false;
            }
        }
        catch(SQLException e){
            System.out.println("Failure");
            e.printStackTrace();
        }
        return true;
    }

    public void insertUser(String userName, String password){
        String newPass = password.substring(0, 15);
        insert("User","'" + userName + "','" + newPass + "'");
    }
}
