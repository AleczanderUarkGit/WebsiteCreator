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
        CommonUserValidator userValidator = new CommonUserValidator();
        userValidator.connect(username, password);
        System.out.println(args[1]);
	//checkDB(args[0], args[1]);
        userValidator.disconnect();
    }

    public void connect(String username, String mysqlpassword) throws SQLException{
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + username + "?" + "user=" + username + "&password=" + mysqlpassword);
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


}
