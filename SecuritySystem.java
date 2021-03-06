import java.sql.*;

public class SecuritySystem 
{
    private Connection connection;
    private Statement statement;

    public SecuritySystem(){
        connection = null;
        statement = null;
    }

    public static void main(String[] args) throws SQLException
    {
        String username = "ajj004";
        String password = "ahho2eiG";
        SecuritySystem system = new SecuritySystem();
        system.connect(username, password);
	if(!system.checkDB(args[0], args[1])){
            System.out.println("Either username or password is incorrect, please verify credentials");
            System.exit(1);
        }
        else{
            System.out.println("<br><br>Welcome args[0]<br><br>");
        }
        system.disconnect();
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
   
    public void query(String q){
        try{
            ResultSet resultSet = statement.executeQuery(q);
            print(resultSet);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void print(ResultSet resultSet) throws SQLException{
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();
        printHeader(metaData, numColumns);
        printRecords(resultSet, numColumns);
    }

    public void printHeader(ResultSetMetaData metaData, int numColumns) throws SQLException{
        for(int i = 1; i <= numColumns; i++){
            if(i > 1)
                System.out.print(", ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }
    
    public void printRecords(ResultSet resultSet, int numColumns) throws SQLException{
        String columnValue;
        while(resultSet.next()){
            for(int i = 1; i <= numColumns; i++){
                if(i > 1)
                    System.out.print(",  ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println();
        }
    }

    public boolean checkDB(String userName, String password){
        String query = "SELECT * FROM User";
        String pass = password.substring(0,15);
        System.out.println("<br><br>" + pass + "<br><br>");
        try{
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + "<br>");
                if(userName.equals(resultSet.getString(1))&&pass.equals(resultSet.getString(2)))
                    return true;
            }
        }
        catch(SQLException e){
            System.out.println("Failure");
            e.printStackTrace();
        }
        return false;
    }


}
