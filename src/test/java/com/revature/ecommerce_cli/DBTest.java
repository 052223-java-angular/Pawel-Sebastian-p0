package com.revature.ecommerce_cli;
import com.revature.ecommerce_cli.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DBTest {
    @Test
    public void connectionTest() throws IOException, SQLException, ClassNotFoundException {
        try (Connection cnctTest = ConnectionFactory.getInstance().getConnection()) {
            System.out.println("postgres connection made");
            assertNotNull(cnctTest);
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw e;
        } catch (SQLException e) {
            System.out.println("couldn't open connection");
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver");
            throw e;
        }
    }
}
