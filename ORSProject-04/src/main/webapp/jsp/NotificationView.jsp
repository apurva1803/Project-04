<%@page import="in.co.rays.proj4.controller.NotificationCtl"%>
<%@page import="in.co.rays.proj4.bean.NotificationBean"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add Notification</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.NOTIFICATION_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.NotificationBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Notification
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
                    <th align="left">Code<span style="color: red">*</span></th>
                    <td><input type="text" name="code" placeholder="Enter code" value="<%=DataUtility.getStringData(bean.getCode())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("code", request)%>
                        </font>
                    </td>
                </tr>
                
                
  <tr>
                    <th align="left">Type<span style="color: red">*</span></th>
                    <td><input type="text" name="type" placeholder="Enter type" value="<%=DataUtility.getStringData(bean.getType())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("type", request)%>
                        </font>
                    </td>
                </tr>
            
           <%--       <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("name", request)%>                      </font>
                    </td>
                </tr>
           --%>  

              <tr>
					<th align="left">Time
					<span style="color: red">*</span></th>
					<td><input type="text" id="udate" name="time" placeholder="enter time" value="<%=DataUtility.getDateString(bean.getTime())%>" style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("time", request)%></font></td>
				</tr>
				
                
                <tr>
            <th align="left">Status<span style="color: red">*</span></th>
            <% HashMap<String, String> map = new HashMap<String, String>();
            map.put("pending","pending");
            map.put("successfully","successfully");
            map.put("Fail","Fail");
            
            String htmlutility= HTMLUtility.getList("status", bean.getStatus(), map);
      
            
            
            %>
               <td>  <%=htmlutility%></td>
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
                        <input type="submit" name="operation" value="<%=NotificationCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=NotificationCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=NotificationCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=NotificationCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>