package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.model.Role;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class RoleServiceTest {

    private static final Role role1 = new Role("ROLE_ADMIN");
    private static final Role role2 = new Role("ROLE_MODERATOR");
    private static final Role role3 = new Role("ROLE_USER");

    private RoleService roleService = getRoleService();

    public RoleService getRoleService() {
        RoleService mock = Mockito.mock(RoleService.class);
        Mockito.when(mock.findAll()).thenReturn(Lists.list(role1,role2,role3));

        return mock;
    }


    @Test
    public void shouldReturnAllRoles_findAll() {
        List<Role> actual = roleService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(role1,role2,role3);
    }
}