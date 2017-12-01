package database;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Custom build vars FILL EVERYTHING CORRECTLY
 * @date 20 of June of 2015
 */

public class BuildVars {
	public static String host = "localhost";
	public static String database = "stock";

	public static String linkDB = "jdbc:mysql://"+host+":3306/"+database+"?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String controllerDB = "com.mysql.jdbc.Driver";
    public static String userDB = "tbot";
    public static String password = "2236";


}
