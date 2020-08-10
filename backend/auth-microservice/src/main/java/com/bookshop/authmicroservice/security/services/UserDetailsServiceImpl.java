package com.bookshop.authmicroservice.security.services;

import com.bookshop.authmicroservice.Messages.MessageProvider;
import com.bookshop.authmicroservice.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MessageProvider<String> messageProvider;

    public UserDetailsServiceImpl(MessageProvider<String> messageProvider){
        this.messageProvider = messageProvider;
    }

    @Value("${kafka.request.topic}")
    String requestTopic;

    @Value("${kafka.reply.topic}")
    String requestReplyTopic;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    User user = null;

	    try {
            user = (User) messageProvider.sendAndReceived(username, 3).getData();
        } catch (Exception e){
            logger.info("Error load user by username [{}]", e.getLocalizedMessage());
        }

        assert user != null;
        return UserDetailsImpl.build(user);
	}

}
