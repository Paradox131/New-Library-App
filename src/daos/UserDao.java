package daos;

import business.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends Dao implements UserDaoInterface {


    public UserDao(String databaseName) {
        super(databaseName);
    }

    public UserDao(Connection con) {
        super(con);
    }


    /**
     * Method adds a new user in to the system
     *
     * @param userName,    user's name a maximum of 24 characters
     * @param password,    user's password a maximum of 128 characters
     * @param email,       user's email a maximum of 128 character
     * @param phoneNumber, user's phone number maximum of 24 characters
     * @param userType,    user type (1 for normal user 2 for admin)
     * @return the 1 if user was successfully registered  or returns -1 if the email is already registered
     **/
    @Override
    public int register(String userName, String password, String email, String phoneNumber, int userType) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "select * from users where email= ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                rowsAffected = -1;
            } else {

                String command = "INSERT INTO users(userName,password,email,phoneNumber,userType)   VALUES(?, ?, ?, ?, ?)";
                ps = con.prepareStatement(command);
                ps.setString(1, userName);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, phoneNumber);
                ps.setInt(5, userType);
                rowsAffected = ps.executeUpdate();


            }


        } catch (SQLException e) {
            System.out.println("Exception occurred in login() method:" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occurred in login() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

    /**
     * Method logs in an existing user in to the system
     *
     * @param email,    user's email
     * @param password, user's password
     * @return the userId if details are correct or returns -1 if details are wrong
     **/
    @Override
    public User logIn(String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User u = null;
        try {
            con = getConnection();

            String query = "Select * from users where email = ? and password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("phoneNumber"), rs.getInt("userType"), rs.getInt("disable"));
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the login() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the final section of the login() method: " + e.getMessage());
            }
        }
        return u;
    }

    /**
     * Disables a particular user
     *
     * @param userType, the userType of the person who is about to disable a user
     * @param disableId , the userId of user to be disabled
     * @return a boolean, true if the user was disabled and false for otherwise or if the userType is not 2
     **/
    @Override
    public boolean disAbleMember(int userType, int disableId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean state = false;
        int rowsAffected = 0;
        if (userType == 2) {
            try {
                con = getConnection();

                String command = "update users set disable=? where userId=? and userType!=2";
                ps = con.prepareStatement(command);
                ps.setInt(1, 2);
                ps.setInt(2, disableId);
                // ps.setInt(3, userType);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    state = true;
                }


            } catch (SQLException e) {
                System.out.println("Exception occured in  the disAbleMember() method: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        freeConnection(con);
                    }
                } catch (SQLException e) {
                    System.out.println("Exception occured in  the disAbleMember() method:  " + e.getMessage());
                }
            }


        }
        return state;
    }

    /**
     * method to delete a user
     *
     * @param id, the userId
     * @return 0 if user was not deleted, or 1 if user was deleted
     */
    public int deleteUser(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "delete from users where userId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception occured in  the disAbleMember() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in  the disAbleMember() method:  " + e.getMessage());
            }
        }

        return rowsAffected;
    }

    /**
     * method unsuspends a user
     *
     * @param id, the userId
     * @return 1 if user was deleted 0 if user wasn't deleted
     **/
    public int unsuspendUser(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "update users set disable=1 where userId=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, id);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception occured in  the unsuspendUser() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in  the unsuspendUser() method:  " + e.getMessage());
            }
        }


        return rowsAffected;
    }

    /**
     * gets a user id
     *
     * @param email, the user email
     * @return -1 if no user was found with that email or returns an in
     **/
    public int getUserId(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;

        try {
            con = getConnection();

            String query = "Select userId from users where email =? ";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("userId");

            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserId() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the final section of the getUserId() method: " + e.getMessage());
            }
        }
        return id;
    }

    /**get a User
     *@param userId
     * @return User
     * */
    public User getUser(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        User u = null;

        try {
            con = getConnection();

            String query = "Select * from users where userId =? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                u = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("phoneNumber"), rs.getInt("userType"), rs.getInt("disable"));

            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserId() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the final section of the getUserId() method: " + e.getMessage());
            }
        }
        return u;
    }

    /**
     * returns all Users
     *
     * @return an ArrayList of all users
     **/
    public ArrayList<User> getAllUsers() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        ArrayList<User> users = new ArrayList();
        try {
            con = getConnection();

            String query = "Select * from users ";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("phoneNumber"), rs.getInt("userType"), rs.getInt("disable"));
                users.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserId() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the final section of the getUserId() method: " + e.getMessage());
            }
        }
        return users;
    }

}
