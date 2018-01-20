package com.mavenMVC.service;

import com.mavenMVC.dao.IAdminDao;
import com.mavenMVC.entity.Admin;
import com.mavenMVC.entity.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional(readOnly=true)
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private IAdminDao adminDao;

	protected final Logger logger = Logger.getLogger(String.valueOf(MyUserDetailsService.class));

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.info("start security check");
		String userName = "";
		try {
			userName =  new String(username.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Admin admin = null;
		if(userName!=null && !userName.trim().equals("")){
			admin = adminDao.getAdminByName(userName);
			if(admin == null){
				return new User(userName,userName,new ArrayList<GrantedAuthority>());
			}
		}

		List<GrantedAuthority> authorities = buildUserAuthority(admin);
		logger.info("finish security check");
		return buildUserForAuthentication(admin, authorities);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(Admin user, List<GrantedAuthority> authorities) {
		if(user!=null){
			UserInfo userInfo = new UserInfo(user.getAdminName(), user.getAdminPassword(), true, true, true, true, authorities);
			userInfo.setUserId(user.getAdminId());
			return userInfo;
		}
		return null;
	}

	private List<GrantedAuthority> buildUserAuthority(Admin user) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		if(user!=null){
			// Build user's authorities
//			if(user.getUserType() == 2){
				setAuths.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
				setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//			}else{
//				setAuths.add(new SimpleGrantedAuthority("ROLE_ORG"));
//				setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//			}
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}