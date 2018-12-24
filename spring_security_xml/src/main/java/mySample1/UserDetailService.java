package mySample1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

	@Resource(name="userDataDao")
	private UserDataDao userDataDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserData userData = null;
		try {
			userData = userDataDao.selectById(id);
			userData.setUserPassword(passwordEncoder.encode(userData.getUserPassword()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userData == null) {
			throw new UsernameNotFoundException(id);
		}
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		User user = new User(userData.getUserName(), userData.getUserPassword(), auths);
		return user;
	}
}
