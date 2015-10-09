package com.barganha.barganha.dao;

import com.barganha.barganha.config.TestDbConfiguration;
import com.barganha.barganha.model.User;
import com.barganha.barganha.util.DateTimeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDbConfiguration.class})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void shouldInsertWithoutError() {
        // GIVEN
        User user = new User();
        user.setEmail("user@test.com");
        user.setPassword("any password");
        user.setName("Any Name");
        user.setCreatedOn(DateTimeHelper.getCurrentDateTime());

        // WHEN
        user = userDao.saveAndFlush(user);

        // THEN
        User userDb = userDao.findOne(user.getId());
        assertNotNull(userDb);
        assertEquals(user.getEmail(), userDb.getEmail());
        assertEquals(user.getPassword(), userDb.getPassword());
        assertEquals(user.getName(), userDb.getName());
        assertEquals(user.getCreatedOn(), userDb.getCreatedOn());
    }

}
