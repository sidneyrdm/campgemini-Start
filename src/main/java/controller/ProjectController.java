/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Project;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author sidney
 */
public class ProjectController {

    public void save(Project project) throws SQLException {
        String sql = "INSERT INTO projects (name, description, "
                + "createdAt, updatedAt) "
                + "VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, new Date(project.getCreatedAt().getTime()));
            preparedStatement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            preparedStatement.execute();
            
            project.setMessage("Projeto cadastrado com sucesso!");

        } catch (Exception e) {
            project.setMessage("Erro ao tentar salvar o projeto");
            throw new RuntimeException("Erro ao tentar salvar o projeto" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }

    }

    public void update(Project project) {
        String sql = "UPDATE projects SET "                
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            System.out.println("criou o prepared statement");
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, new Date(project.getCreatedAt().getTime()));
            preparedStatement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            preparedStatement.setInt(5, project.getId());

            System.out.println("vai executar o prepared statement");
            preparedStatement.execute();
            System.out.println("executou o prepared statement");
            

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar atualizar o projeto" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }

    public void removeById(int projectId) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar o projeto" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Project> projects = new ArrayList<Project>();

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
            }          

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar buscar os projetos" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement, resultSet);
        }
          return projects;
    }
}
