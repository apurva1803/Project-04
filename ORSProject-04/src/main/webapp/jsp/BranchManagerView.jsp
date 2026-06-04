<%@page import="in.co.rays.proj4.bean.BranchManagerBean"%>
<%@page import="in.co.rays.proj4.controller.BranchManagerCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add </title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.BRANCHMANAGER_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.BranchManagerBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
               Branch Manager
            </h1>

		<%
			List<BranchManagerBean> artList = (List<BranchManagerBean>) request.getAttribute("artList");
		%>

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
                    <th align="left">Manager Name<span style="color: red">*</span></th>
                    <td><input type="text" name="managerName" placeholder="Enter Manager Name" value="<%=DataUtility.getStringData(bean.getManagerName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("managerName", request)%>                      </font>
                    </td>
                </tr>
           
 				 <tr>
                    <th align="left">Branch Name<span style="color: red">*</span></th>
                    <td><input type="text" name="branchName" placeholder="Enter Branch Name" value="<%=DataUtility.getStringData(bean.getBranchName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("branchName", request)%>                      </font>
                    </td>
                </tr>
                
                 <tr>
                    <th align="left">Contact Number<span style="color: red">*</span></th>
                    <td><input type="text" name="contactNumber" placeholder="Enter Contact Number" value="<%=DataUtility.getStringData(bean.getContactNumber())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("contactNumber", request)%>                      </font>
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
                        <input type="submit" name="operation" value="<%=BranchManagerCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=BranchManagerCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=BranchManagerCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=BranchManagerCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>