package com.gerenciarvestidos;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebService(
    name = "GerenciarVestido",
    serviceName = "GerenciarVestidosService"
)
public class GerenciarVestidos {
    private static final String URL = "jdbc:derby://localhost:1527/laura";
    private static final String USUARIO = "laura";
    private static final String SENHA = "laura";
    private Connection conexao;

    @WebMethod(operationName = "verificarEmprestimo")
    public String verificarEmprestimo(
        @WebParam(name = "id") int id)
            {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "SELECT alugado FROM vestidos WHERE id = ?";
            PreparedStatement localizar = conexao.prepareStatement(sql);
            localizar.setInt(1, id);
            ResultSet resultado = localizar.executeQuery();
            String estaAlugado = "false";
            if (resultado.next()) {
                estaAlugado = resultado.getString("alugado");
            }
            resultado.close();
            localizar.close();
            conexao.close();
            return "Augado";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Não alugado";
        }
    }


    @WebMethod(operationName = "incluirVestidos")
    public boolean incluirVestido(
        @WebParam(name = "id") int id,
        @WebParam(name = "cor") String cor,
        @WebParam(name = "tamanho") String tamanho,
        @WebParam(name = "modelo") String modelo,
        @WebParam(name = "alugado") boolean alugado) {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "INSERT INTO vestidos (id,cor,tamanho,modelo,alugado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement incluir = conexao.prepareStatement(sql);
            incluir.setInt(1,id);
            incluir.setString(2, cor);
            incluir.setString(3, tamanho);
            incluir.setString(4, modelo);
            incluir.setBoolean(5, alugado);
            int linhasAfetadas = incluir.executeUpdate();
            incluir.close();
            conexao.close();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "alterarVestidos")
public boolean alterarVestido(
    
    
   
        @WebParam(name = "id") int id,
           @WebParam(name = "alugado") int alugado){
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "UPDATE  vestidos SET alugado=? WHERE id=?";
            PreparedStatement alterar = conexao.prepareStatement(sql);
            alterar.setInt(1,alugado);
            alterar.setInt(2, id);
            int linhasAfetadas = alterar.executeUpdate();
            alterar.close();
            conexao.close();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}