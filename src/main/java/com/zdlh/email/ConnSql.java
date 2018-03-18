package com.zdlh.email;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/5.
 */
public class ConnSql {
    static Connection conn;

    static String tab1Sql = "SELECT a.vname, COUNT(1) numb "
            + "FROM "
            + "(\n" +
            "jiraissue c\n" +
            "LEFT JOIN nodeassociation b ON c.ID = b.SOURCE_NODE_ID\n" +
            "AND b.SINK_NODE_ENTITY = 'Version'\n" +
            "AND b.SOURCE_NODE_ENTITY = 'Issue'\n" +
            "AND ASSOCIATION_TYPE = 'IssueVersion'\n" +
            ")\n" +
            "LEFT JOIN projectversion a ON a.ID = b.SINK_NODE_ID\n" +
            "WHERE\n" +
            "c.issuestatus IN (1, 3, 4)\n" +
            "GROUP BY\n" +
            "a.vname;";
    //每个开发身上未解决的任务和改进以及bug
    static String tab2Sql = "SELECT\n" +
            "\ta.vname,\n" +
            "\te.DISPLAY_NAME,\n" +
            "\tCOUNT(1) numb,\n" +
            "\tGROUP_CONCAT(\n" +
            "\t\tCONCAT_WS(\n" +
            "\t\t\t'-',\n" +
            "\t\t\tf.PROJECT_KEY,\n" +
            "\t\t\tc.issuenum\n" +
            "\t\t) SEPARATOR ' '\n" +
            "\t) id\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tjiraissue c\n" +
            "\t\tLEFT JOIN nodeassociation b ON c.ID = b.SOURCE_NODE_ID\n" +
            "\t\tAND b.SINK_NODE_ENTITY = 'Version'\n" +
            "\t\tAND b.SOURCE_NODE_ENTITY = 'Issue'\n" +
            "\t\tAND ASSOCIATION_TYPE = 'IssueVersion'\n" +
            "\t)\n" +
            "LEFT JOIN projectversion a ON a.ID = b.SINK_NODE_ID\n" +
            "LEFT JOIN (\n" +
            "\tSELECT DISTINCT\n" +
            "\t\tUSER_KEY,\n" +
            "\t\tDISPLAY_NAME\n" +
            "\tFROM\n" +
            "\t\tAO_2D3BEA_USER_INDEX\n" +
            ") e ON e.USER_KEY = c.ASSIGNEE\n" +
            "LEFT JOIN project_key f ON f.PROJECT_ID = a.PROJECT\n" +
            "WHERE\n" +
            "\tc.issuestatus IN (1, 3, 4)\n" +
            "GROUP BY\n" +
            "\ta.vname,\n" +
            "\te.DISPLAY_NAME;";
    //每个报告者对应的待验证的任务和改进
    static String tab3Sql = "SELECT\n" +
            "   \n" +
            "             a.vname,    \n" +
            "             e.DISPLAY_NAME,    \n" +
            "             COUNT(1) numb,    \n" +
            "             GROUP_CONCAT(CONCAT_WS('-',f.PROJECT_KEY,c.issuenum) separator ' ') id    \n" +
            "             FROM    \n" +
            "             (    \n" +
            "             jiraissue c    \n" +
            "             LEFT JOIN nodeassociation b ON c.ID = b.SOURCE_NODE_ID    \n" +
            "             AND b.SINK_NODE_ENTITY = 'Version'    \n" +
            "             AND b.SOURCE_NODE_ENTITY = 'Issue'    \n" +
            "             AND ASSOCIATION_TYPE = 'IssueVersion'    \n" +
            "             )    \n" +
            "             LEFT JOIN projectversion a ON a.ID = b.SINK_NODE_ID    \n" +
            "             LEFT JOIN (    \n" +
            "              SELECT DISTINCT    \n" +
            "               USER_KEY,    \n" +
            "               DISPLAY_NAME    \n" +
            "              FROM    \n" +
            "               AO_2D3BEA_USER_INDEX    \n" +
            "             ) e ON e.USER_KEY = c.CREATOR  \n" +
            "              LEFT JOIN project_key f ON f.PROJECT_ID=a.PROJECT  \n" +
            "             WHERE    \n" +
            "             c.issuestatus = 5     \n" +
            "             GROUP BY    \n" +
            "             a.vname,    \n" +
            "             e.DISPLAY_NAME;";
    //
    static String tab4Sql = "SELECT * FROM (SELECT\n" +
            "\ti.pname,\n" +
            "\ta.vname AS branch,\n" +
            "\tCONCAT_WS(\n" +
            "\t\t'-',\n" +
            "\t\tj.PROJECT_KEY,\n" +
            "\t\tc.issuenum\n" +
            "\t) AS ID,\n" +
            "\tc.SUMMARY AS title,\n" +
            "\th.model AS model,\n" +
            "\te.DISPLAY_NAME AS developer,\n" +
            "\td.DISPLAY_NAME AS tester,\n" +
            "\tCASE c.issuestatus\n" +
            "WHEN 1 THEN\n" +
            "\t'开发中'\n" +
            "WHEN 3 THEN\n" +
            "\t'开发中'\n" +
            "WHEN 5 THEN\n" +
            "\t'测试中'\n" +
            "WHEN 6 THEN\n" +
            "\t'测试完成'\n" +
            "WHEN 4 THEN\n" +
            "\t'*重新打开*'\n" +
            "END AS 'result'\n" +
            "FROM\n" +
            "\t`jiraissue` c\n" +
            "LEFT JOIN nodeassociation b ON (\n" +
            "\tc.ID = b.SOURCE_NODE_ID\n" +
            "\tAND b.SINK_NODE_ENTITY = 'Version'\n" +
            "\tAND b.SOURCE_NODE_ENTITY = 'Issue'\n" +
            "\tAND b.ASSOCIATION_TYPE = 'IssueVersion'\n" +
            ")\n" +
            "LEFT JOIN projectversion a ON a.ID = b.SINK_NODE_ID\n" +
            "LEFT JOIN (\n" +
            "\tSELECT DISTINCT\n" +
            "\t\tUSER_KEY,\n" +
            "\t\tDISPLAY_NAME\n" +
            "\tFROM\n" +
            "\t\tAO_2D3BEA_USER_INDEX\n" +
            ") d ON d.USER_KEY = c.REPORTER\n" +
            "LEFT JOIN (\n" +
            "\tSELECT DISTINCT\n" +
            "\t\tUSER_KEY,\n" +
            "\t\tDISPLAY_NAME\n" +
            "\tFROM\n" +
            "\t\tAO_2D3BEA_USER_INDEX\n" +
            ") e ON e.USER_KEY = c.ASSIGNEE\n" +
            "LEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tf.SOURCE_NODE_ID,\n" +
            "\t\tGROUP_CONCAT(g.cname separator '<BR>') AS model\n" +
            "\tFROM\n" +
            "\t\tnodeassociation f\n" +
            "\tLEFT JOIN component g ON f.SINK_NODE_ID = g.ID\n" +
            "\tWHERE\n" +
            "\t\tf.SINK_NODE_ENTITY = 'Component'\n" +
            "\tAND f.SOURCE_NODE_ENTITY = 'Issue'\n" +
            "\tAND f.ASSOCIATION_TYPE = 'IssueComponent'\n" +
            "\tGROUP BY\n" +
            "\t\tf.SOURCE_NODE_ID\n" +
            ") h ON c.ID = h.SOURCE_NODE_ID\n" +
            "LEFT JOIN project i ON c.PROJECT = i.ID\n" +
            "LEFT JOIN project_key j ON i.ID = j.PROJECT_ID\n" +
            "WHERE\n" +
            "\tc.issuetype IN (10000, 10003, 10004)\n" +
            "AND (\n" +
            "\t(\n" +
            "\t\ta.vname = 'branch0.0.8.1'\n" +
            "\t\tAND i.ID = 10000\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\ta.vname = 'dev2.3.7'\n" +
            "\t\tAND i.ID = 10205\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\ta.vname = '1.0.4'\n" +
            "\t\tAND i.ID = 10200\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\ta.vname = '1.0.7'\n" +
            "\t\tAND i.ID = 10201\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\ta.vname = '1.1.2'\n" +
            "\t\tAND i.ID = 10202\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\ta.vname = '1.1.3'\n" +
            "\t\tAND i.ID = 10203\n" +
            "\t)\n" +
            ") \n" +
            "ORDER BY\n" +
            "\tFIELD(i.ID,10000,10205,10202,10203,10200,10201),\n" +
            "\ta.vname,\n" +
            "\tc.REPORTER,\n" +
            "\tc.issuenum) AS T;";
    /**
     * 改版本号参照用
     * 10000 PHP
     * 10205 JAVA
     * 10200 钢镚.iOS
     * 10201 钢镚.安卓
     * 10202 中大.iOS
     * 10203 中大.安卓
     */

    @Test
    public void setUp() {

        String sqlURL = "";
        String sqlUSER = "";
        String sqlPASS = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(sqlURL, sqlUSER, sqlPASS);

        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }

    }

    public void tearDown() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询结果转成List数据
     *
     * @return
     */
    public List<Map<String, Object>> getParameter(int tabKey) {
        setUp();
        String sql = "";
        switch (tabKey) {
            case 1:
                sql = tab1Sql;
                break;
            case 2:
                sql = tab2Sql;
                break;
            case 3:
                sql = tab3Sql;
                break;
            case 4:
                sql = tab4Sql;
                break;
            default:
                break;
        }

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {
            Statement st = conn.createStatement();
            ResultSet resultSet;
            /*if (tabKey == 4) {
                String setSql = "set sql_mode ='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';";
                resultSet = st.executeQuery(setSql);
            }*/
            resultSet = st.executeQuery(sql);

            ResultSetMetaData md = resultSet.getMetaData();
            //取得列数
            int columnCount = md.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowDate = new HashMap<>();
                for (int i = 1; i <=columnCount; i++) {
                    //System.out.println(md.getColumnLabel(i));
                    rowDate.put(md.getColumnName(i), resultSet.getString(i)); //getColumnName(i)获取列名
                }
                list.add(rowDate);
            }

        } catch (SQLException e) {
            System.out.println("查询数据失败" + e.getMessage());
        }

        tearDown();
        return list;

    }

    /**
     *
     * @return
     */
    public Object isNull(Object ob) {
        String str = "";
        if (ob == null) {
            return str;
        }else {
            return ob;
        }
    }

    @Test
    public void tests() {
        List<Map<String, Object>> personList = getParameter(4);
        System.out.println("SQL :" + personList.size());

        for (int i = 0; i < personList.size(); i++) {
            System.out.println("SQL结果:" + personList.get(i).get("developer"));
            System.out.println("SQL结果:" + personList.get(i).get("ID"));
            System.out.println("SQL结果:" + personList.get(i).get("model"));
            System.out.println("SQL结果:" + personList.get(i).get("result"));
        }

    }


}
