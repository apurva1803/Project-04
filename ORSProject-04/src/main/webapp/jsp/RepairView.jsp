<%@page import="in.co.rays.proj4.controller.RepairCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<form action="<%=ORSView.REPAIR_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.RepairBean" scope="request"></jsp:useBean>

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
                Repair
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
                        <input type="text" name="deviceName" placeholder="Enter Device Name" value="<%=DataUtility.getStringData(bean.getDeviceName())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("deviceName", request)%>
                        </font>
                    </td>
                </tr>

                

                <tr>
                    <th align="left">Level<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="repairDate" placeholder="Enter repair Date" value="<%=DataUtility.getStringData(bean.getRepairDate())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("repairDate", request)%>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="left">Assigned To<span style="color: red">*</span></th>
                    <td align="center">
                        <input type="text" name="cost" placeholder="Enter cost" value="<%=DataUtility.getStringData(bean.getCost())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("cost", request)%>
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
                            <input type="submit" name="operation" value="<%=RepairCtl.OP_UPDATE%>">
                            <input type="submit" name="operation" value="<%=RepairCtl.OP_CANCEL%>">
                        </td>
                    <%
                        } else {
                    %>
                        <td align="left" colspan="2">
                            <input type="submit" name="operation" value="<%=RepairCtl.OP_SAVE%>">
                            <input type="submit" name="operation" value="<%=RepairCtl.OP_RESET%>">
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