package com.example.security.repo.jdbd;

public class test {
    public List<Role> getRolesByDepartment(RoleSearchRequest request) {
        String sql = "{ call ROLE_ADVANCE.get_employees_by_dept(?, ?, ?, ?, ?, ?, ?, ?) }";

        List<Role> roles = new ArrayList<>();

        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            try (CallableStatement cs = connection.prepareCall(sql)) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.setString(2, request.getFulltext());
                cs.setString(3, request.getRoleId());
                cs.setString(4, request.getRoleName());
                cs.setString(5, request.getStatus());
                cs.setString(6, request.getRoleGroup());
                cs.setInt(7, request.getPage());
                cs.setInt(8, request.getSize());

                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        roles.add(mapResultSetToRole(rs));
                    }
                }
            }
            return null;
        });

        return roles;
    }

}
