package com.araproje.OgrenciBilgiSistemi.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.User;
import com.araproje.OgrenciBilgiSistemi.repository.UserRepository;

@Service
public class UserService implements UserRepository {

	public static final String BASE_DN = "dc=araproje,dc=com";

	@Autowired
	private LdapTemplate ldapTemplate;

	@Override
	public String create(User u) {
		Name dn = buildDn(u.getUserId());
		ldapTemplate.bind(dn, null, buildAttributes(u));
		return u.getUserId() + " created successfully";
	}

	@Override
	public String update(User u) {
		Name dn = buildDn(u.getUserId());
		ldapTemplate.rebind(dn, null, buildAttributes(u));
		return u.getUserId() + " updated successfully";
	}

	@Override
	public String remove(String userId) {
		Name dn = buildDn(userId);
		ldapTemplate.unbind(dn);
		return userId + " removed successfully";
	}

	private Attributes buildAttributes(User u) {

		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("user");

		Attributes attrs = new BasicAttributes();
		attrs.put(ocattr);
		attrs.put("uid", u.getUserId());
		attrs.put("cn", u.getFullName());
		attrs.put("sn", u.getLastName());
		attrs.put("role", u.getRole());
		return attrs;
	}

	public Name buildDn(String userId) {
		return LdapNameBuilder.newInstance(BASE_DN).add("ou", "people").add("uid", userId).build();
	}

	public Name buildBaseDn() {
		return LdapNameBuilder.newInstance(BASE_DN).add("ou", "people").build();
	}

	@Override
	public User find(String userId) {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List<User> users = ldapTemplate.search(query().where("uid").is(userId),
				new PersonAttributeMapper());
		 if (users != null && ! users.isEmpty()) {
			 User user = (User)users.iterator().next();
			 return user;
		 }
		 else return null;
	}
	
	@Override
	public List<User> retrieve() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List<User> users = ldapTemplate.search(query().where("objectclass").is("user"),
				new PersonAttributeMapper());
		return users;
	}

	private class PersonAttributeMapper implements AttributesMapper<User> {

		@Override
		public User mapFromAttributes(Attributes attributes) throws NamingException {
			User user = new User();
			user.setUserId(null != attributes.get("uid") ? attributes.get("uid").get().toString() : null);
			user.setFullName(null != attributes.get("cn") ? attributes.get("cn").get().toString() : null);
			user.setLastName(null != attributes.get("sn") ? attributes.get("sn").get().toString() : null);
			user.setRole(
					null != attributes.get("role") ? attributes.get("role").get().toString() : null);
			return user;
		}
	}
	
}