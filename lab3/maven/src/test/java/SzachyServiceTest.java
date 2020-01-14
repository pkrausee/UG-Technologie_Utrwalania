import domain.Szachy;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import services.SzachyService;

import java.sql.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SzachyServiceTest {

    private final String SELECT_QUERY = "SELECT id, nazwa, gracz, stanowisko FROM Szachy";
    private final String INSERT_QUERY = "INSERT INTO Szachy(nazwa, gracz, stanowisko) VALUES(?, ?, ?)";
    private final String UPDATE_QUERY = "UPDATE Szachy SET nazwa = ?, gracz = ?, stanowisko = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM Szachy WHERE id = ?";

    @InjectMocks
    private SzachyService szachyService;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @org.junit.Test
    public void GivenCreate_WhenCannotExecuteStatement_ReturnsSQLException() throws SQLException {

        when(connection.prepareStatement(INSERT_QUERY)).thenReturn(preparedStatement);

        when(preparedStatement.execute()).thenThrow(new SQLException());

        szachyService.create(new Szachy("Mock", "Mock", 0));

    }

    @org.junit.Test
    public void GivenCreate_WhenGivenValidResources_ReturnsSuccess() throws SQLException {

        when(connection.prepareStatement(INSERT_QUERY)).thenReturn(preparedStatement);

        szachyService.create(new Szachy("Mock", "Mock", 0));

    }

    @org.junit.Test
    public void GivenRead_WhenCannotExecuteQuery_ReturnsSQLException() throws SQLException {

        when(statement.executeQuery(SELECT_QUERY)).thenThrow(new SQLException());

        szachyService.read();

    }

    @org.junit.Test
    public void GivenRead_WhenGivenValidStatement_ReturnsSuccess() throws SQLException {

        when(statement.executeQuery(SELECT_QUERY)).thenReturn(resultSet);

        szachyService.read();

    }

    @org.junit.Test
    public void GivenUpdate_WhenCannotExecuteStatement_ReturnsSQLException() throws SQLException {

        when(connection.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);

        when(preparedStatement.execute()).thenThrow(new SQLException());

        szachyService.update(1, new Szachy("Mock", "Mock", 0));

    }

    @org.junit.Test
    public void GivenUpdate_WhenGivenValidResources_ReturnsSuccess() throws SQLException {

        when(connection.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);

        szachyService.update(1, new Szachy("Mock", "Mock", 0));

    }

    @org.junit.Test
    public void GivenDelete_WhenCannotExecuteStatement_ReturnsSQLException() throws SQLException {

        when(connection.prepareStatement(DELETE_QUERY)).thenReturn(preparedStatement);

        when(preparedStatement.execute()).thenThrow(new SQLException());

        szachyService.delete(1);

    }

    @org.junit.Test
    public void GivenDelete_WhenGivenValidResources_ReturnsSuccess() throws SQLException {

        when(connection.prepareStatement(DELETE_QUERY)).thenReturn(preparedStatement);

        szachyService.delete(1);

    }

}
