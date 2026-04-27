<%@page import="in.co.rays.proj4.controller.EscalationRuleCtl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=ORSView.ESCALATIONRULE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.EscalationRuleBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                if (bean != null && bean.getId() > 0) {
                %>
                    Update
                <%
                    } else {
                %>
                    Add
                <%
                    }
                %>
                EscalationRule
            </h1>

            <div style="height: 15px; margin-bottom: 12px">
                <h3 align="center">
                    <font color="green">
                        <%=ServletUtility.getSuccessMessage(request)%>
                    </font>
                </h3>
                <h3 align="center">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage(request)%>
                    </font>
                </h3>
            </div>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <th align="left">Rule Code<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="ruleCode" placeholder="Enter Rule Code" value="<%=DataUtility.getStringData(bean.getRuleCode())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("ruleCode", request)%>
                        </font>
                    </td>
                </tr>

                

                <tr>
                    <th align="left">Level<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="level" placeholder="Enter level" value="<%=DataUtility.getStringData(bean.getLevel())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("level", request)%>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="left">Assigned To<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="assignedTo" placeholder="Enter Assigned To" value="<%=DataUtility.getStringData(bean.getAssignedTo())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("assignedTo", request)%>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="left">status<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="status" placeholder="Enter status" value="<%=DataUtility.getStringData(bean.getStatus())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("status", request)%>
                        </font>
                    </td>
                </tr>
                
                

                <tr>
                    <th></th>
                    <td></td>
                </tr>

                <tr>
                    <th></th>
                    <%
                        if (bean != null && bean.getId() > 0) {
                    %>
                        <td align="left" colspan="2">
                            <input type="submit" name="operation" value="<%=EscalationRuleCtl.OP_UPDATE%>">
                            <input type="submit" name="operation" value="<%=EscalationRuleCtl.OP_CANCEL%>">
                        </td>
                    <%
                        } else {
                    %>
                        <td align="left" colspan="2">
                            <input type="submit" name="operation" value="<%=EscalationRuleCtl.OP_SAVE%>">
                            <input type="submit" name="operation" value="<%=EscalationRuleCtl.OP_RESET%>">
                        </td>
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>