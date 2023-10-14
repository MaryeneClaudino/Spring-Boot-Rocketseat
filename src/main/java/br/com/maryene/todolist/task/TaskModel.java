package br.com.maryene.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

/*
  * Id
  * Usuário_id
  * Descrição
  * Título
  * Data de início
  * Data de término
  * Prioridade
  */

@Data
@Entity(name = "td_task")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O campo do título deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }
}