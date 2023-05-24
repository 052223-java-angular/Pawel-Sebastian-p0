package com.revature.ecommerce_cli;
import com.revature.ecommerce_cli.Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DBTest {
    @Test
    public void connectionTest() {
        try (Connection cnctTest = ConnectionFactory.getInstance().getConnection()) {
            System.out.println("postgres connection made");
            assertNotNull(cnctTest);
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("couldn't open connection");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver");
            System.out.println(e.getMessage());
        }
    }
}
