package com.zdlh.email;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/12.
 */
public class MailHtmlModle {
    private ConnSql connSql = new ConnSql();

    public String htmlMoble() {
        String head = "<html>\n" +
                "<body>\n" +
                "<style>\n" +
                "\ttable{\n" +
                "\tborder: 1px solid #ddd;\n" +
                "\tborder-collapse: collapse;\n" +
                "    border-spacing: 0;\n" +
                "\n" +
                "\t}\n" +
                "\ttable tr th, table tr td{\n" +
                "\t    padding: 4px;\n" +
                "\t\tvertical-align: middle;\n" +
                "\t\ttext-align: left;\n" +
                "\t\tmax-width: 400px;\n" +
                "\t\tmin-width:50px;\n" +
                "\t\tword-wrap: break-word;\n" +
                "\t\tcolor:#333;\n" +
                "\t}\n" +
                "</style>\n" +
                "<h2>Hi ALL:</h2>\n" +
                "\n" +
                "<h3>现在项目测试概况如下：</h3>\n" +
                "\n" +
                "<p>测试人员：</p>\n" +
                "<h4>当前分支对应任务列表：</h4>\n" +
                "\t<table border='1'>\n" +
                "     <tr>  \n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>项目名称<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>版本号：<b></td>\n" +
                "        <td><font size=\"3\" color=\"blue\"><b>编号<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>模块<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>描述<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>经办人<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>报告人<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>状态<b></td>\n" +
                "    </tr>\n";

        List<Map<String, Object>> tab4List = connSql.getParameter(4);
        String tab4String = "";
        List<String> tab4ListSt = new ArrayList<String>();
        for (int i=0; i<tab4List.size(); i++) {
            String tabSt = "<tr> \n" +
                    "<td><b>" + tab4List.get(i).get("pname") + "</b></td> \n" +
                    "<td><b>" + tab4List.get(i).get("branch") + "</b></td> \n" +
                    "<td><b>" + tab4List.get(i).get("ID") + "</b></td> \n" +
                    "<td><font size=\"3\" color=\"black\">" + connSql.isNull(tab4List.get(i).get("model"))+ "</td> \n" +
                    "<td><font size=\"3\" color=\"black\">" + tab4List.get(i).get("title") + "</td> \n" +
                    "<td><b>" + tab4List.get(i).get("developer") + "</b></td> \n" +
                    "<td><b>" + tab4List.get(i).get("tester") + "</b></td> \n" +
                    "<td><b>" + tab4List.get(i).get("result") + "</b></td> \n" +
                    "</tr>\n";
            tab4ListSt.add(tabSt);
            tab4String += tab4ListSt.get(i);
        }

        String h1 = "<tr>  \n" +
                "        <td colspan=\"8\"><font size=\"3\" color=\"black\"><b><a href=http://jira.zdlhcar.com/issues/?filter=10900>本列表仅包含任务和EPIC，不含子任务、Bug等，点此进入Jira查看详情</a></b></td>  \n" +
                "    </tr>  \n" +
                "\t</table>"+
                "<h4>中大项目按照分支bug数统计情况如下：</h4>" +
                "<table border='1'>  \n" +
                "     <tr>  \n" +
                "        <td><font size=\"3\" color=\"blue\"><b>版本号：<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>数量</b></td>\n" +
                "    </tr> \n" +
                "<tr> \n";

        List<Map<String, Object>> personList = connSql.getParameter(1);
        String tab1String = "";
        List<String> tab1List = new ArrayList<String>();
        for (int i=0; i<personList.size(); i++) {
            String tabSt = "<tr> \n" +
                    "<td><b>" + personList.get(i).get("vname").toString()+ "</b></td> \n" +
                    "<td><font size=\"3\" color=\"black\"><b>" + personList.get(i).get("numb").toString() + "</b></td> \n" +
                    "</tr>\n";
            tab1List.add(tabSt);
            tab1String += tab1List.get(i);
        }

        String h2 = "\t</table> \n" +
                "\n" +
                "<h4>开发人员名下bug统计数情况如下：</h4>\n" +
                "\n" +
                "\t<table border='1'>  \n" +
                "     <tr>  \n" +
                "        <td><font size=\"3\" color=\"blue\"><b>版本号：<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>开发人员<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>数目<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>编号<b></td>\n" +
                "    </tr>\n";
        List<Map<String, Object>> tab2List = connSql.getParameter(2);
        String tab2String = "";
        List<String> tab2ListSt = new ArrayList<String>();
        for (int i=0; i<tab2List.size(); i++) {
            String tabSt = "<tr> \n" +
                    "<td><b>" + tab2List.get(i).get("vname").toString() + "</b></td>\n" +
                    "<td><font size=\"3\" color=\"black\"><b>"+tab2List.get(i).get("DISPLAY_NAME")+"</b></td>\n" +
                    "<td><font size=\"3\" color=\"black\">"+tab2List.get(i).get("numb").toString()+"</td>\n" +
                    "<td><font size=\"3\" color=\"black\">"+tab2List.get(i).get("id").toString()+"</td>\n" +
                    "</tr>\n";
            tab2ListSt.add(tabSt);
            tab2String += tab2ListSt.get(i);
        }

        String h3 = "</table>\n" +
                "\n" +
                "<h4>待验证bug情况如下：</h4>\n" +
                "\t\n" +
                "\t<table border='1'>\n" +
                "     <tr>  \n" +
                "        <td><font size=\"3\" color=\"blue\"><b>版本号：<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>提报人员<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>数目<b></td>\n" +
                "\t\t<td><font size=\"3\" color=\"blue\"><b>编号<b></td>\n" +
                "    </tr>\n";

        List<Map<String, Object>> tab3List = connSql.getParameter(3);
        String tab3String = "";
        List<String> tab3ListSt = new ArrayList<String>();
        for (int i=0; i<tab3List.size(); i++) {
            String tabSt = "<tr> \n" +
                    "<td><b>" + tab2List.get(i).get("vname").toString() + "</b></td>\n" +
                    "<td><font size=\"3\" color=\"black\"><b>"+tab3List.get(i).get("DISPLAY_NAME").toString()+"</b></td>\n" +
                    "<td><font size=\"3\" color=\"black\">"+tab3List.get(i).get("numb").toString()+"</td>\n" +
                    "<td><font size=\"3\" color=\"black\">"+tab3List.get(i).get("id").toString()+"</td>\n" +
                    "</tr>\n";
            tab3ListSt.add(tabSt);
            tab3String += tab3ListSt.get(i);
        }

        String tail = "</table>\n" +
                "<h3><font size=\"3\" color=\"blue\">希望开发人员能尽快着手解决名下的bug，测试人员及时验证名下已解决的bug 谢谢！</h3>\n" +
                "</body>\n" +
                "</html>";

        String htmlTxt = head + tab4String + h1 + tab1String + h2 + tab2String + h3 + tab3String + tail;

        return htmlTxt;
    }

    @Test
    public void test() {
        System.out.println(htmlMoble());
    }
}
