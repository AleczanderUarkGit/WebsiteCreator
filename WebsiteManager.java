import java.sql.*;

public class WebsiteManager
{
    private Connection connection;
    private Statement statement;

    public WebsiteManager(){
        connection = null;
        statement = null;
    }

    public static void main(String[] args) throws SQLException
    {
        String username = "ajj004";
        String password = "ahho2eiG";
        WebsiteManager manager = new WebsiteManager();
        manager.connect(username, password);
        manager.displayTable();
    }

    public void connect(String username, String mysqlpassword) throws SQLExcept$
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" $
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

    public void printHeader(ResultSetMetaData metaData, int numColumns) throws $
        for(int i = 1; i <= numColumns; i++){
            if(i > 1)
                System.out.print(", ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }
    
    public void printRecords(ResultSet resultSet, int numColumns) throws SQLExc$
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
