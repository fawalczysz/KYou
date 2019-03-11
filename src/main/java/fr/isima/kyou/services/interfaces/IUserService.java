package fr.isima.kyou.services.interfaces;

import org.springframework.stereotype.Service;

import fr.isima.kyou.beans.User;

@Service
public interface IUserService {

	User getUser(String email);
}
