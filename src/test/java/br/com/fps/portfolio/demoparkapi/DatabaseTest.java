package br.com.fps.portfolio.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void verificarBancoDeDados() throws Exception {
      System.out.println("➡️ Banco de Dados em uso: " + dataSource.getConnection().getMetaData().getURL());
      System.out.println("➡️ Driver: " + dataSource.getConnection().getMetaData().getDriverName());
      System.out.println("➡️ Usuário: " + dataSource.getConnection().getMetaData().getUserName());
    }

}
