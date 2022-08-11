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
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author sidney
 */
public class TaskController {

    public void save(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (idProject, name, description, "
                + "completed, notes, deadline, createdAt, updatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, task.getIdProject());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setBoolean(4, task.isIsCompleted());
            preparedStatement.setString(5, task.getNotes());
            preparedStatement.setDate(6, new Date(task.getDeadline().getTime()));
            preparedStatement.setDate(7, new Date(task.getCreatedAt().getTime()));
            preparedStatement.setDate(8, new Date(task.getUpdatedAt().getTime()));

            preparedStatement.execute();
            
            task.setMessage("Tarefa cadastrada com sucesso!");

        } catch (Exception e) {
            task.setMessage("Erro ao tentar salvar a tarefa");
            throw new RuntimeException("Erro ao tentar salvar a tarefa" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }

    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, task.getIdProject());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getNotes());
            preparedStatement.setBoolean(5, task.isIsCompleted());
            preparedStatement.setDate(6, new Date(task.getDeadline().getTime()));
            preparedStatement.setDate(7, new Date(task.getCreatedAt().getTime()));
            preparedStatement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            preparedStatement.setInt(9, task.getId());

            preparedStatement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar atualizar a tarefa" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar a tarefa" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }

    public List<Task> getAll(int projectId) {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<Task>();

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar buscar as tarefas" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement, resultSet);
        }
        return tasks;
    }
}
