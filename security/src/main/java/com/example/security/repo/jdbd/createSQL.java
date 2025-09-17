package com.tpb.tpb_agency_admin.repo.jdbc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class RoleProcedure {
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        createPackageSpec();
        createPackageBody();
    }

    private void createPackageSpec() {
        String packageSpec = "BEGIN\n" +
                " EXECUTE IMMEDIATE '\n" +
                " CREATE OR REPLACE PACKAGE ROLE_ADVANCE AS\n" +
                " PROCEDURE get_employees_by_dept(\n" +
                " p_result OUT SYS_REFCURSOR,\n" +
                " p_fulltextsearch VARCHAR2,\n" +
                " p_roleId VARCHAR2,\n" +
                " p_roleName VARCHAR2,\n" +
                " p_status VARCHAR2,\n" +
                " p_roleGroup VARCHAR2,\n" +
                " p_page INT,\n" +
                " p_size INT\n" +
                " );\n" +
                " END ROLE_ADVANCE;';\n" +
                " EXCEPTION\n" +
                " WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;\n" +
                "END;";
        jdbcTemplate.execute(packageSpec);
    }

    private void createPackageBody() {
        String packageBody = "BEGIN\n" +
                " EXECUTE IMMEDIATE '\n" +
                " CREATE OR REPLACE PACKAGE BODY ROLE_ADVANCE AS\n" +
                " PROCEDURE get_employees_by_dept(\n" +
                " p_result OUT SYS_REFCURSOR,\n" +
                " p_fulltextsearch VARCHAR2,\n" +
                " p_roleId VARCHAR2,\n" +
                " p_roleName VARCHAR2,\n" +
                " p_status VARCHAR2,\n" +
                " p_roleGroup VARCHAR2,\n" +
                " p_page INT,\n" +
                " p_size INT\n" +
                " ) IS\n" +
                " BEGIN\n" +
                " OPEN p_result FOR\n" +
                " SELECT * FROM ROLE\n" +
                " WHERE\n" +
                " (p_roleId IS NULL OR ROLE_ID LIKE p_roleId)\n" +
                " AND (p_roleName IS NULL OR ROLE_NAME LIKE p_roleName)\n" +
                " AND (p_status IS NULL OR STATUS = p_status)\n" +
                " AND (p_roleGroup IS NULL OR ROLE_GROUP = p_roleGroup)\n" +
                " OFFSET NVL(p_page, 0) ROWS FETCH NEXT NVL(p_size, 10) ROWS ONLY;\n" +
                " END get_employees_by_dept;\n" +
                " END ROLE_ADVANCE;';\n" +
                " EXCEPTION\n" +
                " WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF;\n" + "END;";
        jdbcTemplate.execute(packageBody);
    }
}