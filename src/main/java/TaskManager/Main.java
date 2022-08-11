/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskManager;

import controller.ProjectController;
import controller.TaskController;
import java.sql.SQLException;
import model.Project;
import model.Task;

/**
 *
 * @author sidney
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        /*Project project = new Project();
        project.setId(2);
        project.setName("NOVO NOME");
        project.setDescription("NOVA descrição");
        
        ProjectController projectController = new ProjectController();
        
        projectController.update(project);
        projectController.removeById(1);                  
       System.out.println("número de projetos cadastrados.: "+projectController.getAll().size());*/
        
        Task task = new Task();
        
        task.setIdProject(2);
        task.setName("tarefa teste ATUALIZADA 2");
        task.setDescription("tarefa ainda sem descrição");
        task.setIsCompleted(false);
        task.setNotes("nenhuma nota por enquanto");
        
        
        TaskController taskController = new TaskController();
        //System.out.println("quantidade de tarefas cadastradas.: "+taskController.getAll(2).size());
        
        taskController.removeById(3);
        
        
        
    }
    
}
