package com.araproje.OgrenciBilgiSistemi.repository;

import java.util.List;

import com.araproje.OgrenciBilgiSistemi.model.User;

public interface UserRepository {
	public List<User> retrieve();
	public String create(User u);
	public String update(User u);
	public String remove(String userId);
	public User find(String userId);
}
