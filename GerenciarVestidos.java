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
    private static final String URL = "jdbc:derby://localhost:1527/LAURA";
    private static final String USUARIO = "laura";
    private static final String SENHA = "laura";
    private Connection conexao;

    @WebMethod(operationName = "verificarEmprestimo")
    public boolean verificarEmprestimo(
        @WebParam(name = "cor") String cor,
        @WebParam(name = "tamanho") String tamanho,
        @WebParam(name = "modelo") String modelo,
        @WebParam(name = "id") int id,
        @WebParam(name = "alugado") boolean alugado) {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "SELECT alugado FROM vestido WHERE cor = ? AND tamanho = ? AND modelo = ? AND id = ?  AND alugado = ?";
            PreparedStatement localizar = conexao.prepareStatement(sql);
            localizar.setString(1, cor);
            localizar.setString(2, tamanho);
            localizar.setString(3, modelo);
            localizar.setInt(4, id);
            localizar.setBoolean(5, alugado);
            ResultSet resultado = localizar.executeQuery();
            boolean estaAlugado = false;
            if (resultado.next()) {
                estaAlugado = resultado.getBoolean("alugado");
            }
            resultado.close();
            localizar.close();
            conexao.close();
            return estaAlugado;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "incluirVestido")
    public boolean incluirVestido(
        @WebParam(name = "id") int id,
        @WebParam(name = "cor") String cor,
        @WebParam(name = "tamanho") String tamanho,
        @WebParam(name = "modelo") String modelo,
        @WebParam(name = "alugado") int alugado) {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "INSERT INTO vestido (id, cor, tamanho, modelo,alugado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement incluir = conexao.prepareStatement(sql);
            incluir.setInt(1,id);
            incluir.setString(2, cor);
            incluir.setString(3, tamanho);
            incluir.setString(4, modelo);
            incluir.setInt(5, alugado);
            int linhasAfetadas = incluir.executeUpdate();
            incluir.close();
            conexao.close();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "alterarVestido")
    public boolean alterarVestido(
        @WebParam(name = "id") int id,
        @WebParam(name = "cor") String cor,
        @WebParam(name = "tamanho") String tamanho,
        @WebParam(name = "modelo") String modelo,
        @WebParam(name = "alugado") int alugado) {
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            String sql = "UPDATE vestido SET cor = ?, tamanho = ?, modelo = ?, alugado = ? WHERE id = ?";
            PreparedStatement alterar = conexao.prepareStatement(sql);
            alterar.setString(1, cor);
            alterar.setString(2, tamanho);
            alterar.setString(3, modelo);
            alterar.setInt(4, alugado);
         

            int linhasAfetadas = alterar.executeUpdate();
            alterar.close();
            conexao.close();
            return linhasAfetadas > 0; // true se alterou algo
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


           
}
