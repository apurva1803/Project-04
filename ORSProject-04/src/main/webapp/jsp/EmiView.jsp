<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EmiCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add EMI</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<form action="<%=ORSView.EMI_CTL%>" method="POST">

<%@ include file="Header.jsp" %>

<jsp:useBean id="bean"
    class="in.co.rays.proj4.bean.EmiBean"
    scope="request" />

<div align="center">
<h1 style="color: navy">
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
				EMI
			</h1>

    <h3><font color="green">
        <%=ServletUtility.getSuccessMessage(request)%>
    </font></h3>

    <h3><font color="red">
        <%=ServletUtility.getErrorMessage(request)%>
    </font></h3>

    <!-- Hidden Fields -->
    <input type="hidden" name="id"
        value="<%=DataUtility.getStringData(bean.getId())%>">

    <table>

        <!-- Amount -->
        <tr>
            <th align="left">Amount<span style="color:red">*</span></th>
            <td>
                <input type="text" name="amount" placeholder="enter amount"
             value="<%= (bean.getAmount() == 0) ? "" : bean.getAmount()%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("amount", request)%>
                </font>
            </td>
        </tr>

        <!-- Due Date -->
        <tr>
            <th align="left">Due Date<span style="color:red">*</span></th>
            <td>
                <input type="text" name="dueDate" id="udate" placeholder="enter date"
                    value="<%=DataUtility.getDateString(bean.getDueDate())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("dueDate", request)%>
                </font>
            </td>
        </tr>

        <!-- Status -->
        <tr>
            <th align="left">Status<span style="color:red">*</span></th>
            <td>
                <input type="text" name="status" placeholder="enter status"
                    value="<%=DataUtility.getStringData(bean.getStatus())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("status", request)%>
                </font>
            </td>
        </tr>

        <tr><th></th><td></td></tr>

        <!-- Buttons -->
        <tr>
            <th></th>
            <td colspan="2">
            <% if (bean != null && bean.getId() > 0) { %>
                <input type="submit" name="operation"
                    value="<%=EmiCtl.OP_UPDATE%>">
                <input type="submit" name="operation"
                    value="<%=EmiCtl.OP_CANCEL%>">
            <% } else { %>
                <input type="submit" name="operation"
                    value="<%=EmiCtl.OP_SAVE%>">
                <input type="submit" name="operation"
                    value="<%=EmiCtl.OP_RESET%>">
            <% } %>
            </td>
        </tr>

    </table>

</div>
</form>
</body>
</html>