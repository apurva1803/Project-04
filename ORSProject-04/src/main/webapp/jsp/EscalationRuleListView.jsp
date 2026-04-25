<%@page import="in.co.rays.proj4.controller.EscalationRuleListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.bean.EscalationRuleBean"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<%@include file="Header.jsp"%>
    <jsp:useBean id="bean" class="in.co.rays.proj4.bean.EscalationRuleBean" scope="request"></jsp:useBean>

    <div align="center">
        <h1 align="center" style="margin-bottom: -15; color: navy;">Course List</h1>

        <div style="height: 15px; margin-bottom: 12px">
            <h3>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
            </h3>
            <h3>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
        </div>

        <form action="<%=ORSView.ESCALATIONRULE_LIST_CTL%>" method="post">
            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

                @SuppressWarnings("unchecked")
                List<EscalationRuleBean> courseList = (List<EscalationRuleBean>) request.getAttribute("courseList");

                @SuppressWarnings("unchecked")
                List<EscalationRuleBean> list = (List<EscalationRuleBean>) ServletUtility.getList(request);
                Iterator<EscalationRuleBean> it = list.iterator();

                if (list.size() != 0) {
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>Course Name :</b></label>
                        <%=HTMLUtility.getList("courseId", String.valueOf(bean.getId()), courseList)%>
                        &emsp;&nbsp;
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_SEARCH%>">&nbsp;
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="25%">Name</th>
                    <th width="15%">Duration</th>
                    <th width="45%">Description</th>
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        bean = (EscalationRuleBean) it.next();
                %>
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>">
                    </td>
                    <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getRuleCode()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getLevel()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getAssignedTo()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getStatus()%></td>
                    
                    <td style="text-align: center;">
                        <a href="CourseCtl?id=<%=bean.getId()%>">Edit</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_NEXT%>" <%=nextPageSize != 0 ? "" : "disabled"%>>
                    </td>
                </tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=EscalationRuleListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
        </form>
    </div>

</body>
</html>