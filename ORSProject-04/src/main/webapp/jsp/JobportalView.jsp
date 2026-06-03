<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.JobportalCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add budget</title>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
    <form action="<%=ORSView.JOBPORTAL_CTL%>" method="POST">
        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.JobportalBean" scope="request"></jsp:useBean>

        <div align="center">
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>Update<%
                    } else {
                %>Add<%
                
                    }
                %>
                Job Portal
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
                    <th align="left">Company Name<span style="color: red">*</span></th>
                    <td><input type="text" name="companyName" placeholder="Enter Company Name"  value="<%=DataUtility.getStringData(bean.getCompanyName())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("companyName", request)%>
                        </font>
                    </td>
                </tr>
                
                <tr>
                    <th align="left">Job Role<span style="color: red">*</span></th>
                    <td><input type="text" name="jobRole" placeholder="Enter job Role"  value="<%=DataUtility.getStringData(bean.getJobRole())%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("jobRole", request)%>
                        </font>
                    </td>
                </tr>

                <tr>
                    <th align="left">Salary Package<span style="color: red">*</span></th>
                    <td><input type="text" name="salaryPackage" placeholder="Enter salaryPackage" value="<%= (bean.getSalaryPackage() == 0) ? "" : bean.getSalaryPackage()%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("salaryPackage", request)%>                      </font>
                    </td>
                </tr>
 
                <tr>
                    <th align="left">Experience Required<span style="color: red">*</span></th>
                    <td><input type="text" name="experienceRequired" placeholder="Enter experienceRequired" value="<%= (bean.getExperienceRequired() == 0) ? "" : bean.getExperienceRequired()%>"></td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("experienceRequired", request)%>
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
                        <input type="submit" name="operation" value="<%=JobportalCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=JobportalCtl.OP_CANCEL%>">
                    <%
                        } else {
                    %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=JobportalCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=JobportalCtl.OP_RESET%>">
                    <%
                        }
                    %>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>