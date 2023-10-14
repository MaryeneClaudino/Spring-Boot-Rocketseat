package br.com.maryene.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUserName(String userName);
}